package steps.overview;

import cotext.ScenarioContext;
import cucumber_setup.Hooks;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pom.SidebarPanelPom;
import utils.TablePages;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class DismissPopupAndTableUnchangedTest {
    private WebDriver driver = Hooks.getDriver();
    private ScenarioContext scenarioContext;
    public WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    private final String LOGO_SELECTOR = "div.Sidebar_logo__v4crW";
    private final String TABLE_SELECTOR = "//div[@class='ant-table-content']//tbody//tr";
    private final String ADD_BUTTON_SELECTOR = "//span[text()='Dodaj']";
    private final String DISMISS_BUTTON_SELECTOR = "//span[text()='Anuluj']";
    private final String POPUP_SELECTOR = "div.ant-modal-content";
    private By pagination = By.xpath("//ul[contains(@class,'ant-pagination')]//a");
    private SidebarPanelPom sidebarPanelPom = new SidebarPanelPom(driver);
    private TablePages tablePages = new TablePages(driver);


    public DismissPopupAndTableUnchangedTest() {
        this.scenarioContext = new ScenarioContext();
    }

    @Given("the user open the {string} feature page")
    public void the_user_open_the_feature_page(String featureName) {
        By locator = sidebarPanelPom.getSidebarFeatureLocators().get(featureName);
        sidebarPanelPom.clickSidebarItem(locator);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LOGO_SELECTOR)));
        WebElement subPage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div h1")));
        assertThat(subPage.getText()).contains(featureName);
    }

    @When("the table is displayed")
    public void the_table_is_displayed() {
        WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TABLE_SELECTOR)));
        assertTrue(table.isDisplayed());
    }

    @When("the user note the initial data in the table")
    public void the_user_note_the_initial_data_in_the_table() {
        int tableRows = tablePages.countAllRows(pagination, TABLE_SELECTOR);
        scenarioContext.set("tableRows", tableRows);
    }

    @When("the user clicks the Add button in the subpage")
    public void the_user_clicks_the_add_button_in_the_page() {
        WebElement addButton = driver.findElement(By.xpath(ADD_BUTTON_SELECTOR));
        addButton.click();
    }

    @When("the popup appears")
    public void the_popup_appears() {
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(POPUP_SELECTOR)));
        assertTrue(popup.isDisplayed());
    }

    @When("the user fills the following inputs:")
    public void the_user_fills_the_following_inputs(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> maps = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> input : maps) {
            String fieldName = input.get("field");
            String value = input.get("value");

            if (fieldName == null || fieldName.isEmpty()) {
                continue;
            }
            WebElement inputField = driver.findElement(By.id(fieldName));
            inputField.sendKeys(value);
            inputField.sendKeys(Keys.TAB);
        }
    }

    @When("the user clicks the Dismiss button")
    public void the_user_clicks_the_dismiss_button() {
        WebElement dismissButton = driver.findElement(By.xpath(DISMISS_BUTTON_SELECTOR));
        dismissButton.click();
    }

    @Then("the popup should close")
    public void the_popup_should_close() {
        boolean inPopupGone = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(POPUP_SELECTOR)));
        assertTrue(inPopupGone);
    }

    @Then("the table should remain unchanged")
    public void the_table_should_remain_unchanged() {
        int actualTable = tablePages.countAllRows(pagination, TABLE_SELECTOR);
        int expectedTable = scenarioContext.get("tableRows", Integer.class);
        assertEquals(expectedTable, actualTable);
    }
}
