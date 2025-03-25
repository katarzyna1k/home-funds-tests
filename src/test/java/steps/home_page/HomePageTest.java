package steps.home_page;

import configuration.ConfigurationManager;
import cucumber_setup.Hooks;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomePageTest {
    private WebDriver driver = Hooks.getDriver();
    private static final String LOGO_SELECTOR = "div.Sidebar_logo__v4crW";

    @Given("the user navigates to front page of the application")
    public void the_user_navigates_to_front_page_of_the_application() {
        String url = driver.getCurrentUrl();
        assertThat(url).contains(ConfigurationManager.getUrl());
    }

    @Then("the front page should be displayed correctly")
    public void the_front_page_should_be_displayed_correctly() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement logo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LOGO_SELECTOR)));
        String logoName = "HomeFunds";
        assertAll(
                () -> assertTrue(logo.isDisplayed()),
                () -> assertThat(logo.getText()).contains(logoName)
        );
    }
}