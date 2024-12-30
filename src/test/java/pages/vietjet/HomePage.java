package pages.vietjet;

import com.codeborne.selenide.SelenideElement;
import element.LocaleManager;

import static com.codeborne.selenide.Selenide.$x;

public class HomePage extends BasePage {
    private final String txtDepartDestination = "//label[text()='Điểm khởi hành']/..//input";

    public void inputDepartDestination(String departDestination){
        $x(String.format(txtDepartDestination, LocaleManager.getLocalizedText("homepage.destination.input"))).setValue(departDestination);
    }

}
