package steps.overview;

import cotext.ScenarioContext;
import cucumber_setup.Hooks;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pom.SidebarPanelPom;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultiFeatureAddRecordTest {
    private WebDriver driver = Hooks.getDriver();
    private SidebarPanelPom sidebarPanelPom = new SidebarPanelPom(driver);
    private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    private final String LOGO_SELECTOR = "div.Sidebar_logo__v4crW";
    private final String TABLE_SELECTOR = "//div[@class='ant-table-content']//tbody";
    private By submitButton = By.xpath("//span[text()='Zapisz']");
    private By pagination = By.xpath("//ul[contains(@class,'ant-pagination')]//a");
    private ScenarioContext scenarioContext;

    public MultiFeatureAddRecordTest() {
        this.scenarioContext = new ScenarioContext();
    }

    public int countAllRows() {
        int totalRows = 0;
        List<WebElement> pages = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(pagination));
        int countPages = pages.size();

        if (countPages > 1) {
            for (WebElement page : pages) {
                page.click();
                List<WebElement> rows = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(TABLE_SELECTOR + "//tr")));
                totalRows += rows.size();
            }
        } else {
            List<WebElement> table = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(TABLE_SELECTOR + "//tr")));
            totalRows = table.size();
        }
        return totalRows;
    }

    @Given("the user opens the {string} feature page")
    public void the_user_opens_the_feature_page(String featureName) {
        By locator = sidebarPanelPom.getSidebarFeatureLocators().get(featureName);
        sidebarPanelPom.clickSidebarItem(locator);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LOGO_SELECTOR)));
    }

    @When("the user notes the initial data in the table")
    public void the_user_notes_the_initial_data_in_the_table() {
        countAllRows();
        scenarioContext.set("baseTable", countAllRows());
    }

    @When("the user clicks the Submit button")
    public void the_user_clicks_the_submit_button() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }

    @Then("the table size should increase by one")
    public void the_table_size_should_increase_by_one() {
        int baseTableSize = scenarioContext.get("baseTable", Integer.class);
        int resultTableSize = countAllRows();

        assertAll(
                () -> assertThat(baseTableSize).isLessThan(resultTableSize),
                () -> assertEquals(baseTableSize, resultTableSize - 1)
        );
    }
}
