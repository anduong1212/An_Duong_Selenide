package pages.vietjet;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import common.Log;

import java.time.Duration;
import java.util.Comparator;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Selenide.$$;

public class TravelOptionPage extends BasePage{
    private final String ticketPriceOptions = "//p[contains(@class, 'MuiTypography-h4')]";
    private final String lblTicketPrice = "p.MuiTypography-h4";


    public void selectLowestTicket(){
        ElementsCollection ticketOptions =  $$(ticketPriceOptions).shouldBe(sizeGreaterThan(0), Duration.ofSeconds(Configuration.timeout));

//        SelenideElement lowestTicket = ticketOptions.stream()
//                .min(Comparator.comparingDouble(ticketElement -> Double.parseDouble(ticketElement.getText())))
//                .orElseThrow(() -> new RuntimeException("[Travel Option] No lowest ticket found"));
//
//        lowestTicket.scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}").click();

        ticketOptions.stream().forEach(ticketElement -> Log.info(ticketElement.getText()));

    }
}
