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
import utils.TablePages;

import java.text.DecimalFormat;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class OverviewTileToTableSumValidationTest {
    private WebDriver driver = Hooks.getDriver();
    private ScenarioContext scenarioContext;
    private TablePages tablePages = new TablePages(driver);
    public WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    private final String OVERVIEW_ENDPOINT_SELECTOR = "//a[text()='Przegląd']";
    private final String TILE_SELECTOR = "div.ant-card-body";
    private final String DETAILS_BUTTON_SELECTOR = "button[type='button']";
    private final String SUBPAGE_SELECTOR = "//h1[contains(text(),'";
    private final String TABLE_SELECTOR = "//table";
    private final String TABLE_CELL_SELECTOR = "//tbody//tr//td";

    public OverviewTileToTableSumValidationTest() {
        this.scenarioContext = new ScenarioContext();
    }

    @Given("feature overview page is loaded")
    public void feature_overview_page_is_loaded() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(OVERVIEW_ENDPOINT_SELECTOR))).click();
        WebElement result = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h1[contains(text(),'Przegląd')]")));
        String resultText = result.getText();
        assertAll(
                () -> assertThat(driver.getCurrentUrl()).contains("/overview"),
                () -> assertTrue(resultText.contains("Przegląd"))
        );
    }

    @When("user note the number displayed on the tile")
    public void user_note_the_number_displayed_on_the_tile() {
        List<WebElement> tile = driver.findElements(By.cssSelector(TILE_SELECTOR));
        List<String> tileSumNumbers = tile.stream()
                .map(WebElement::getText)
                .toList();
        scenarioContext.set("overviewTiles", tileSumNumbers);
    }

    @When("click on the details button {int}")
    public void click_on_the_details_button(Integer index) {
        List<WebElement> button = driver.findElements(By.cssSelector(DETAILS_BUTTON_SELECTOR));
        button.get(index).click();
    }

    @Then("user is redirected to the {string} table page")
    public void user_is_redirected_to_the_table_page(String tileName) {
        WebElement subpage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SUBPAGE_SELECTOR + tileName + "')]")));
        WebElement table = driver.findElement(By.xpath(TABLE_SELECTOR));

        assertAll(
                () -> assertTrue(table.isDisplayed()),
                () -> assertThat(subpage.getText()).contains(tileName)
        );
    }

    @Then("user sum the values in the {string} column of the table")
    public void user_sum_the_values_in_the_column_of_the_table(String column) {
        List<String> tableCells = tablePages.tableAmount(TABLE_SELECTOR + "//th", column, TABLE_CELL_SELECTOR);
        double sum = tableCells.stream()
                .mapToDouble(Double::parseDouble).sum();
        scenarioContext.set("sumOfCells", sum);
    }

    @Then("the sum of the {int} column should equal the number displayed on the tile")
    public void the_sum_of_the_column_should_equal_the_number_displayed_on_the_tile(Integer index) {
        DecimalFormat df = new DecimalFormat("#.00");

        List<String> tileNumbers = scenarioContext.get("overviewTiles", List.class);
        List<Double> expectedNumbers = tileNumbers.stream().map(Double::parseDouble).toList();

        double sum = scenarioContext.get("sumOfCells", Double.class);
        String actualNumber = df.format(sum);

        assertEquals(df.format(expectedNumbers.get(index)), actualNumber);
    }

    @Then("the sum of income, recurring-bills and transactions should equals balance-sheet")
    public void the_sum_of_income_recurring_bills_and_transactions_should_equals_balance_sheet() {
        List<String> tileSumNumbers = scenarioContext.get("overviewTiles", List.class);

        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        double sum = 0;
        for (int i = 1; i <= 2; i++) {
            sum += Double.parseDouble(tileSumNumbers.get(i));
        }

        double income = Double.parseDouble(tileSumNumbers.getFirst());
        double expectedBalance = income - sum;
        String formattedExpectedBalance = decimalFormat.format(expectedBalance);

        double actualBalanceValue = Double.parseDouble(tileSumNumbers.get(3));
        String formattedActualBalance = decimalFormat.format(actualBalanceValue);

        assertEquals(formattedExpectedBalance, formattedActualBalance);
    }
}