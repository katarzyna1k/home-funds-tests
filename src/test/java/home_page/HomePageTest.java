package home_page;

import configuration.ConfigurationManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import webdriver_service.WebDriverSetup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomePageTest extends WebDriverSetup {
    private static final String LOGO_SELECTOR = "div.Sidebar_logo__v4crW";

    @Given("the user navigates to front page of the application")
    public void the_user_navigates_to_front_page_of_the_application() {
        driver.navigate().to(ConfigurationManager.getUrl());
    }

    @Then("the front page should be displayed correctly")
    public void the_front_page_should_be_displayed_correctly() {
        WebElement logo = driver.findElement(By.cssSelector(LOGO_SELECTOR));
        String logoName = "HomeFunds";
        assertAll(
                () -> assertTrue(logo.isDisplayed()),
                () -> assertThat(logo.getText()).contains(logoName)
        );

    }
}