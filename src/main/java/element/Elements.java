package element;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.$x;

public class Elements {
    public static boolean isFormattedElementDisplayed(String xpath, String replaceText){
        return $x(String.format(xpath, replaceText)).shouldBe(Condition.visible).isDisplayed();
    }

    public static void clickFormattedElement(String xpath, String... replaceText){
        $x(String.format(xpath, replaceText)).shouldBe(Condition.visible).click();
    }
}
