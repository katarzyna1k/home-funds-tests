package steps.transactions;

import cucumber_setup.Hooks;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pom.SidebarPanelPom;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SelectFromDropdownTest {
    private WebDriver driver = Hooks.getDriver();
    private SidebarPanelPom sidebarPanelPom = new SidebarPanelPom(driver);
    private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    private final String LOGO_SELECTOR = "div.Sidebar_logo__v4crW";
    private By dropdownSelector = By.className("ant-select-selector");
    private final String CATEGORY_OPTIONS_SELECTOR = "//div[@class='ant-select-item-option-content' and text()='";
    private final String SELECTED_CATEGORY_SELECTOR = "//span[@class='ant-select-selection-item-content' and text()='";
    private final String TABLE_SELECTOR = "//div[@class='ant-table-content']//tbody//tr";
    private By categoryFieldSelector = By.cssSelector("div.ant-select-selector");
    private By nextPageSelector = By.cssSelector("li[title='Next Page']");

    @Given("the user opens the Transactions feature page")
    public void the_user_opens_the_transactions_feature_page() {
        By locator = sidebarPanelPom.getSidebarFeatureLocators().get("Bieżące wydatki");
        sidebarPanelPom.clickSidebarItem(locator);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LOGO_SELECTOR)));
    }

    @Then("the user clicks the dropdown arrow for category")
    public void the_user_clicks_the_dropdown_arrow_for_category() {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownSelector));
        dropdown.click();
    }

    @Then("the user selects category {string}")
    public void the_user_selects_category(String category) {
        WebElement optionToSelect = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CATEGORY_OPTIONS_SELECTOR + category + "']")));
        optionToSelect.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SELECTED_CATEGORY_SELECTOR + category + "']")));
        WebElement categoryField = wait.until(ExpectedConditions.visibilityOfElementLocated(categoryFieldSelector));
        categoryField.click();
    }

    @Then("the user sees a new record with data field {word} description field {string} amount field {int} and  category field {string}")
    public void the_user_sees_a_new_record_with_data_field_description_field_amount_field_and_category_field(String date, String description, Integer amount, String category) {
        List<List<String>> allRows = new ArrayList<>();

        while (true) {
            List<WebElement> tableRows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(TABLE_SELECTOR)));

            for (WebElement row : tableRows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));

                if (cells.size() >= 4) {
                    List<String> rowData = List.of(cells.get(0).getText(), cells.get(1).getText(), cells.get(2).getText(), cells.get(3).getText());
                    allRows.add(rowData);
                }
            }

            List<WebElement> nextButtons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(nextPageSelector));

            if (nextButtons.isEmpty())
                break;

            WebElement nextButton = nextButtons.get(0);

            if (!nextButton.isEnabled() || nextButton.getDomAttribute("aria-disabled").contains("true")) {
                break;
            }
            nextButton.click();
        }

        String expectedAmount = String.valueOf(amount);
        List<String> expectedRow = List.of(date, description, expectedAmount, category);
        assertTrue(allRows.contains(expectedRow));
    }
}