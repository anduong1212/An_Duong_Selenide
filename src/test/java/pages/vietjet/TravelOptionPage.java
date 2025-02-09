package pages.vietjet;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class TravelOptionPage extends BasePage {
    private final Supplier<ElementsCollection> ticketPriceElements = () -> $$("p.MuiTypography-h4");
    private final Supplier<ElementsCollection> currentLowestPriceElements = () -> $$("div.slick-current p.MuiTypography-subtitle1 span");
    private final Supplier<SelenideElement> vjFlightIconElement = () -> $x("//img[@alt='vietjet flight']");
    private final Supplier<SelenideElement> continueButton = () -> $("button.MuiButton-contained");
    private static final String lblPriceElementFormat = "(//p[contains(@class, 'MuiTypography-h4')])[%d]";

    public TravelOptionPage() {
        super();
        logger.info("Initializing Travel Option Page...");
    }

    @Step("Wait for ticket price options")
    public void waitForTicketPriceOptions() {
        ticketPriceElements.get().shouldBe(sizeGreaterThan(0), Duration.ofSeconds(Configuration.timeout));
        logger.info("Ticket price options are loaded successfully");
    }

    public void selectLowestPrice() {
        waitForTicketPriceOptions();
        logger.info("Selecting the lowest price");
        getSuggestedPrice().
                ifPresentOrElse(this::selectTicketByPrice, () -> {
                    logger.warn("[Travel Option Page] No suggested price available");
                });
    }

    //Optional is a container object which may or may not contain a non-null value.
    // If a value is present, isPresent() will return true and get() will return the value.
    public Optional<String> getSuggestedPrice() {
        currentLowestPriceElements.get().shouldBe(sizeGreaterThan(0), Duration.ofSeconds(Configuration.timeout));
        String suggestedPrice = String.join("", currentLowestPriceElements.get().texts());
        String formattedPrice = formatPrice(suggestedPrice);
        if (!formattedPrice.isEmpty()) {
            logger.info("Suggested price is: " + formattedPrice);
            return Optional.of(formattedPrice);
        } else {
            logger.warn("Suggested price is not available");
            return Optional.empty();
        }

    }

    public void selectTicketByPrice(String suggestedPrice) {
        logger.info("[Travel Option Page] Selecting ticket by price: {}", suggestedPrice);
        scrollAndFindTicketByPrice(suggestedPrice)
                .ifPresentOrElse(this::clickPriceElement, () -> {
                    logger.warn("[Travel Option Page] Ticket with price {} not found", suggestedPrice);
                });

    }

    private Optional<SelenideElement> scrollAndFindTicketByPrice(String suggestedPrice) {
        int maxScroll = 20;
        for (int i = 0; i < maxScroll; i++) {
            logger.info("Scroll attempt {}/{}", i + 1, maxScroll);
            Optional<SelenideElement> priceElement = findTicketByPrice(suggestedPrice);
            if (priceElement.isPresent()) {
                return priceElement;
            }
            scrollToBottom();
        }
        return Optional.empty();
    }

    private Optional<SelenideElement> findTicketByPrice(String suggestedPrice) {
        ElementsCollection priceElements = ticketPriceElements.get();
        priceElements.shouldBe(sizeGreaterThan(0));
        List<String> allPrices = priceElements.texts().stream()
                .map(this::formatPrice)
                .toList();
        logger.info("Attempt {} - Found {} new prices: {} ", getScrollAttemptCounter(), allPrices.size(), allPrices);

        for (int i = 0; i < allPrices.size(); i++) {
            if (allPrices.get(i).equals(suggestedPrice)) {
                SelenideElement element = $x(String.format(lblPriceElementFormat, i + 1))
                        .scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}");
                logger.info("Found and returning price element with price: {}", suggestedPrice);
                return Optional.of(priceElements.get(i));
            }
        }

        logger.info("Suggested price {} not found", suggestedPrice);
        return Optional.empty();
    }

    private void clickPriceElement(SelenideElement priceElement) {
        logger.info("Selecting ticket with price element: {}", priceElement.getText());
        priceElement.click();
        logger.info("Clicked on the price element");
        selectContinue();
    }

    @Step("Select 'Continue' button")
    public void selectContinue() {
        logger.info("Selecting 'Continue' button.");
        continueButton.get().shouldBe(visible).click();
    }

    @Step("Scroll to the bottom of the page")
    private void scrollToBottom() {
        logger.info("Scrolling to the bottom of the page.");
        vjFlightIconElement.get().shouldBe(exist).scrollTo();
    }


    private String formatPrice(String priceText) {
        if (priceText == null || priceText.isEmpty()) {
            return "";
        }
        String cleanedPrice = priceText.replace(",", "").replace(" ", "").replaceAll("\\D", "");
        return removeTrailingZeros(cleanedPrice);
    }

    private String removeTrailingZeros(String price) {
        if (price.endsWith("000")) {
            return price.substring(0, price.length() - 3);
        }
        return price;
    }

    private int getScrollAttemptCounter() {
        int scrollAttemptCounter = 0;
        return ++scrollAttemptCounter;
    }


}
