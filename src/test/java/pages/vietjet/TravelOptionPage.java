package pages.vietjet;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import common.Log;

import java.util.Comparator;

import static com.codeborne.selenide.Selenide.$$;

public class TravelOptionPage extends BasePage{
    private final String ticketPriceOptions = "//div/p[text()='000 VND']/preceding-sibling::p";

    public void selectLowestTicket(){
        ElementsCollection ticketOptions = $$(ticketPriceOptions);

        SelenideElement lowestTicket = ticketOptions.stream()
                .min(Comparator.comparingDouble(ticketElement -> Double.parseDouble(ticketElement.getText())))
                .orElseThrow(() -> new RuntimeException("[Travel Option] No lowest ticket found"));

        if (lowestTicket.shouldBe(Condition.appear).isDisplayed()){
            Log.info(lowestTicket.getText());
        }


    }
}
