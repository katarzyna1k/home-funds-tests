package home_page;

import configuration.ConfigurationManager;
import cotext.ScenarioContext;
import cucumber_setup.Hooks;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class FrontPageStructureTest {
    private WebDriver driver = Hooks.getDriver();
    private static final String SIDEBAR_NAVIGATION_PANEL_SELECTOR = "div.Sidebar_nav_container__dAWju a";
    private ScenarioContext scenarioContext;

    public FrontPageStructureTest() {
        this.scenarioContext = new ScenarioContext();
    }

    @Given("the user opens the front page")
    public void the_user_opens_the_front_page() {
        driver.navigate().to(ConfigurationManager.getUrl());
    }

    @When("user checks the functions panel on the left side")
    public void user_checks_the_functions_panel_on_the_left_side() {
        List<WebElement> navigationPanel = driver.findElements(By.cssSelector(SIDEBAR_NAVIGATION_PANEL_SELECTOR));
        List<String> navigationElement = navigationPanel.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        scenarioContext.set("sidebarItemsTexts", navigationElement);
    }

    @Then("the panel should contains features {string} in correct order {int}")
    public void the_panel_should_contains_features_in_correct_order(String expected, Integer index) {
        List<String> resultList = scenarioContext.get("sidebarItemsTexts", List.class);
        assertEquals(expected, resultList.get(index));
    }
}