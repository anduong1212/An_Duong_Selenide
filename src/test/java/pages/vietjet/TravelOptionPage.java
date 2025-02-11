package pages.vietjet;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;
import java.util.AbstractMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.IntStream;

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
    private static final int MAX_SCROLL_ATTEMPT = Integer.parseInt(System.getProperty("max.scroll.attempt", "20"));

    public TravelOptionPage() {
        super();
        logger.info("Initializing Travel Option Page...");
    }

    @Step("Wait for ticket price options")
    private void waitForTicketPriceOptions() {
        ticketPriceElements.get().shouldBe(sizeGreaterThan(0), Duration.ofSeconds(Configuration.timeout));
        logger.info("Ticket price options are loaded successfully");
    }

    /**
     * Get the suggested price from the page
     * If the price is not available, return an empty Optional
     * @return Optional<String> suggested price
     */
    @Step("Get suggested price")
    private Optional<String> getSuggestedPrice() {
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

    /**
     * Find the suggested price in the current DOM, which show on the current view
     * @param suggestedPrice the price to find
     * @return Optional<SelenideElement> the ticket element
     */
    private Optional<SelenideElement> findTicketByPrice(String suggestedPrice, String flightLeg) {
        ElementsCollection priceElements = ticketPriceElements.get();
        priceElements.shouldBe(sizeGreaterThan(0));

        List<String> allPrices = priceElements.texts().stream()
                .map(this::formatPrice)
                .toList();
        logger.info("Attempt {} - Found {} new prices for {} flight leg: {} ", getScrollAttemptCounter(), allPrices.size(), flightLeg ,allPrices);

        //Iterate through all the prices and find the element with the suggested price
        return IntStream.range(0, allPrices.size())  //Create IntStream from 0 to allPrices.size()
                .mapToObj(index -> {          //Convert IntStream to Stream<IndexedPrice> (index, price) is index-value
                    String price = allPrices.get(index);  // Get current index
                    return new AbstractMap.SimpleEntry<>(index, price);  //Return the index and price as a pair
                })
                .filter(indexedPrice -> indexedPrice.getValue().equals(suggestedPrice))  //Filter the pair with the suggested price
                .map(indexPrice -> {         //Map: convert index-value to SelenideElement
                    SelenideElement element = $x(String.format(lblPriceElementFormat, indexPrice.getKey() + 1))
                            .scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}");
                    logger.info("Found and returning price element with price '{}' for flight leg '{}'", suggestedPrice, flightLeg);
                    return element;
                })
                .findFirst(); //Return the first element found if not return empty Optional
    }

    /**
     * Scroll and find the ticket with the suggested price
     * If the price is not found after MAX_SCROLL_ATTEMPT, return an empty Optional
     * @param suggestedPrice the price to find
     * @return Optional<SelenideElement> the ticket element
     */
    @Step("Scroll and find ticket with suggested price")
    private Optional<SelenideElement> scrollAndFindTicketByPrice(String suggestedPrice, String flightLeg) {
        for (int i = 0; i < MAX_SCROLL_ATTEMPT; i++) {
            logger.info("Scroll attempt {}/{} for {} flight leg", i + 1, MAX_SCROLL_ATTEMPT, flightLeg);
            Optional<SelenideElement> priceElement = findTicketByPrice(suggestedPrice, flightLeg);
            if (priceElement.isPresent()) {
                return priceElement;
            }
            scrollToBottom();
        }
        logger.warn("Target price '{}' not found after {} scroll attempts.", suggestedPrice, MAX_SCROLL_ATTEMPT);
        return Optional.empty();
    }

    /**
     * Select the ticket with the given price
     */
    private void selectTicketByPrice(String suggestedPrice, String flightLeg) {
        logger.info("[Travel Option Page] Selecting ticket by price: {}", suggestedPrice);
        scrollAndFindTicketByPrice(suggestedPrice, flightLeg)
                .ifPresentOrElse(priceElement -> clickPriceElement(priceElement, flightLeg), () -> {
                    logger.warn("[Travel Option Page] Ticket with price {} not found", suggestedPrice);
                });
    }

    @Step("Click on the price element")
    private void clickPriceElement(SelenideElement priceElement, String flightLeg) {
        logger.info("Selecting ticket with price element: {}", priceElement.getText());
        priceElement.click();
        logger.info("Clicked on price element for {} flight leg.", flightLeg);
        selectContinue();
    }

    @Step("Select 'Continue' button")
    private void selectContinue() {
        logger.info("Selecting 'Continue' button.");
        continueButton.get().shouldBe(visible).click();
    }

    @Step("Scroll to the bottom of the page")
    private void scrollToBottom() {
        logger.info("Scrolling to the bottom of the page.");
        vjFlightIconElement.get().shouldBe(exist).scrollTo();
    }

    @Step("Select the lowest price for {flightLeg}")
    private void selectLowestPrice(String flightLeg) {
        waitForTicketPriceOptions();
        logger.info("Selecting the lowest price");
        getSuggestedPrice().
                ifPresentOrElse(lowestPrice -> selectTicketByPrice(lowestPrice, flightLeg), () -> {
                    logger.warn("[Travel Option Page] No suggested price available");
                });
    }

    /**
     * Select tickets for the given flight type
     */
    @Step("Choose tickets for {flightType} flight") // Dynamic step name using String Templates (Java 21)
    public void selectTicketsForFlight(String flightType) { // Methods selectTicketsForFlight receive flightType as parameter
        closePopupAds(); // Close popup ads

        logger.info("Selecting tickets for {} flight.", flightType);
        if ("roundTrip".equalsIgnoreCase(flightType)) {
            logger.info("Selecting tickets for both departure and arrival for return flight.");
            selectLowestPrice("departure"); // Indicate departure flight
            selectLowestPrice("arrival");   // Indicate arrival flight
        } else if ("oneway".equalsIgnoreCase(flightType)) {
            logger.info("Selecting ticket for oneway flight.");
            selectLowestPrice("oneway");      // Indicate oneway flight
        } else {
            logger.warn("Unknown flight type: {}. Selecting ticket as if it's oneway.", flightType);
            selectLowestPrice("unknown"); // Handle unknown type gracefully
        }
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
