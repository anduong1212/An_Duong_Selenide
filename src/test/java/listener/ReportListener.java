package listener;

import io.qameta.allure.Allure;
import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.*;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ReportListener implements TestLifecycleListener {
    private static final Map<String, Integer> retryCount = new ConcurrentHashMap<>();

    @Override
    public void beforeTestSchedule(TestResult result){
        String uuid = UUID.randomUUID().toString();
        result.setUuid(uuid);
        result.setStart(System.currentTimeMillis());
        retryCount.put(result.getFullName(), 0);
    }

    @Override
    public void beforeTestStart(TestResult result){
        int count = retryCount.compute(result.getFullName(), (k, v) -> (v == null) ? 1 : v + 1) - 1;
        String testName = result.getName();
        if (count > 0){
            testName += " (Retry " + count + ")";
        }

        String finalTestName = testName;

        Allure.getLifecycle().updateTestCase(testCase -> {
            testCase.setName(finalTestName);
            Optional.ofNullable(result.getFullName()).ifPresent(testCase::setFullName);
            getDescription(result).ifPresent(testCase::setDescription);
        });
    }

    @Override
    public void beforeTestStop(TestResult result){
        getTestResultFromContext(result).ifPresentOrElse(testResult -> {
            boolean isRetryConfigured = testResult.getMethod().getRetryAnalyzer(testResult) != null;
            int currentRetry = retryCount.getOrDefault(result.getFullName(), 0);

            if (testResult.isSuccess()) {
                setTestStatus(result, Status.PASSED);
            } else if (testResult.getStatus() == ITestResult.FAILURE) {
                if (isRetryConfigured && currentRetry <= getMaxRetryCount(testResult)) {
                    Allure.getLifecycle().updateTestCase(testCase -> {
                        testCase.setStatus(Status.SKIPPED);
                        testCase.setStatusDetails(new StatusDetails().setMessage("Retrying test..."));
                    });
                } else {
                    setTestStatus(result, Status.FAILED, testResult);
                }
            } else if (testResult.getStatus() == ITestResult.SKIP) {
                setTestStatus(result, Status.SKIPPED, testResult);
            }
        }, () -> setTestStatus(result, Optional.ofNullable(result.getStatus()).orElse(Status.SKIPPED)));
    }

    private void setTestStatus(TestResult allureResult, Status status) {
        allureResult.setStatus(status);
        allureResult.setStop(System.currentTimeMillis());
    }

    private void setTestStatus(TestResult allureResult, Status status, ITestResult testResult) {
        allureResult.setStatus(status);
        allureResult.setStop(System.currentTimeMillis());
        if (status == Status.FAILED || status == Status.SKIPPED) {
            allureResult.setStatusDetails(getStatusDetails(testResult));
        }
    }

    private int getMaxRetryCount(ITestResult testResult) {
        if (testResult.getMethod().getRetryAnalyzer(testResult) instanceof RetryAnalyzer) {
            return RetryAnalyzer.maxRetryCount;
        }
        return 0;
    }

    private Optional<String> getDescription (TestResult result){
        return getTestResultFromContext(result)
                .map(iTestResult -> iTestResult.getMethod().getDescription());
    }

    private StatusDetails getStatusDetails(ITestResult result) {
        Throwable throwable = result.getThrowable();
        return Objects.nonNull(throwable) ? new StatusDetails()
                .setMessage(throwable.getMessage())
                .setTrace(getStackTraceAsString(throwable)) : new StatusDetails();
    }

    private String getStackTraceAsString(Throwable throwable) {
        StringBuilder sb = new StringBuilder();
        sb.append(throwable.toString()).append("\n");
        StackTraceElement[] stackTrace = throwable.getStackTrace();
        for (StackTraceElement element : stackTrace) {
            sb.append("\tat ").append(element).append("\n");
        }
        return sb.toString();
    }

    private Optional<ITestResult> getTestResultFromContext(TestResult result){
        try {
            String methodName = result.getFullName().substring(result.getFullName().lastIndexOf(".") + 1);
            String className = result.getFullName().substring(0, result.getFullName().lastIndexOf("."));

            Class<?> testClass = Class.forName(className);
            ITestContext context = Objects.requireNonNull(org.testng.Reporter.getCurrentTestResult().getTestContext());

            for (ITestNGMethod method : context.getAllTestMethods()){
                if (method.getConstructorOrMethod().getMethod().getName().equals(methodName)
                && method.getRealClass().equals(testClass)){
                    return findITestResult(context, method);
                }
            }
        } catch (ClassNotFoundException e){
            throw new RuntimeException("Methods is not exist - " + e);
        }

        return Optional.empty();
    }

    private Optional<ITestResult> findITestResult(ITestContext context, ITestNGMethod testNGMethod){
        return context.getFailedConfigurations().getAllResults().stream().filter(r -> r.getMethod().equals(testNGMethod)).findFirst()
                .or(() -> context.getFailedTests().getAllResults().stream().filter(r -> r.getMethod().equals(testNGMethod)).findFirst())
                .or(() -> context.getSkippedTests().getAllResults().stream().filter(r -> r.getMethod().equals(testNGMethod)).findFirst())
                .or(() -> context.getPassedTests().getAllResults().stream().filter(r -> r.getMethod().equals(testNGMethod)).findFirst());
    }

}
