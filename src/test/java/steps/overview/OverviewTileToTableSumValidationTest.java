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

import java.text.DecimalFormat;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class OverviewTileToTableSumValidationTest {
    private WebDriver driver = Hooks.getDriver();
    private ScenarioContext scenarioContext;
    private final String OVERVIEW_ENDPOINT_SELECTOR = "//a[text()='Przegląd']";
    private final String TILE_SELECTOR = "div.ant-card-body";
    private final String BUTTON_SELECTOR = "button[type='button']";
    private final String SUBPAGE_SELECTOR = "//h1[text()='";
    public WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    private final String TABLE_SELECTOR = "//table";
    private final String TABLE_CELL_SELECTOR = "//tbody//tr//td";


    public OverviewTileToTableSumValidationTest() {
        this.scenarioContext = new ScenarioContext();
    }

    @Given("feature overview page is loaded")
    public void feature_overview_page_is_loaded() {
        driver.findElement(By.xpath(OVERVIEW_ENDPOINT_SELECTOR)).click();
        WebElement result = driver.findElement(By.xpath("//h1[text()='Przegląd']"));
        String resultText = result.getText();
        assertAll(
                () -> assertThat(driver.getCurrentUrl()).contains("/overview"),
                () -> assertTrue(resultText.contains("Przegląd"))
        );
    }

    @When("user note the number displayed on the tile")
    public void user_note_the_number_displayed_on_the_tile() {
        List<WebElement> tile = driver.findElements(By.cssSelector(TILE_SELECTOR));
        List<String> tileSumNumber = tile.stream()
                .map(WebElement::getText)
                .toList();
        scenarioContext.set("overviewTiles", tileSumNumber);
    }

    @When("click on the details button {int}")
    public void click_on_the_details_button(Integer index) {
        List<WebElement> button = driver.findElements(By.cssSelector(BUTTON_SELECTOR));
        button.get(index).click();
    }

    @Then("user is redirected to the {string} table page")
    public void user_is_redirected_to_the_table_page(String tileName) {
        WebElement subpage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SUBPAGE_SELECTOR + tileName + "']")));
        WebElement table = driver.findElement(By.xpath(TABLE_SELECTOR));

        assertAll(
                () -> assertTrue(table.isDisplayed()),
                () -> assertThat(subpage.getText()).contains(tileName)
        );
    }

    @Then("user sum the values in the {string} column of the table")
    public void user_sum_the_values_in_the_column_of_the_table(String column) {
        List<WebElement> tableHeaders = driver.findElements(By.xpath(TABLE_SELECTOR + "//th"));
        int kwotaIndex = -1;
        for (int i = 0; i < tableHeaders.size(); i++) {
            if (tableHeaders.get(i).getText().equals(column)) {
                kwotaIndex = i + 1;
                break;
            }
        }

        List<WebElement> tableCells = driver.findElements(By.xpath(TABLE_CELL_SELECTOR + "[" + kwotaIndex + "]"));
        double sum = tableCells.stream()
                .map(WebElement::getText)
                .mapToDouble(Double::parseDouble).sum();
        scenarioContext.set("sumOfCells", sum);
    }

    @Then("the sum of the {int} column should equal the number displayed on the tile")
    public void the_sum_of_the_column_should_equal_the_number_displayed_on_the_tile(Integer index) {
        List<String> expectedNumber = scenarioContext.get("overviewTiles", List.class);
        double sum = scenarioContext.get("sumOfCells", Double.class);
        DecimalFormat df = new DecimalFormat("0.##");
        String actualNumber = df.format(sum);

        assertEquals(expectedNumber.get(index), actualNumber);
    }

    @Then("the sum of income, recurring-bills and transactions should equals balance-sheet")
    public void the_sum_of_income_recurring_bills_and_transactions_should_equals_balance_sheet() {
        List<String> tileSumNumbers = scenarioContext.get("overviewTiles", List.class);
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        double sum = 0;
        for (int i = 1; i <= 2; i++) {
            sum += Double.parseDouble(tileSumNumbers.get(i));
        }

        double income = Double.parseDouble(tileSumNumbers.getFirst());
        double expectedBalance = income - sum;
        String formattedExpectedBalance = decimalFormat.format(expectedBalance);

        double actualBalanceValue = Double.parseDouble(tileSumNumbers.getLast());
        String formattedActualBalance = decimalFormat.format(actualBalanceValue);

        assertEquals(formattedExpectedBalance, formattedActualBalance);
    }

}