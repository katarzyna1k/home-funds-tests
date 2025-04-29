package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TablePages {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public TablePages(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public int countAllRows(By pagination, String tableRowSelector) {
        int totalRows = 0;
        List<WebElement> pages = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(pagination));
        int countPages = pages.size();

        if (countPages > 1) {
            for (WebElement page : pages) {
                page.click();
                List<WebElement> rows = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(tableRowSelector)));
                totalRows += rows.size();
            }
        } else {
            List<WebElement> table = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(tableRowSelector)));
            totalRows = table.size();
        }
        return totalRows;
    }

    public List<String> tableAmount(String tableRowSelector, String column, String tableCellSelector) {
        List<String> allCells = new ArrayList<>();

        List<WebElement> tableHeaders = driver.findElements(By.xpath(tableRowSelector));
        int kwotaIndex = -1;
        for (int i = 0; i < tableHeaders.size(); i++) {
            if (tableHeaders.get(i).getText().equals(column)) {
                kwotaIndex = i + 1;
                break;
            }
        }

        while (true) {
            List<WebElement> tableCells = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(tableCellSelector + "[" + kwotaIndex + "]")));
            for (WebElement cell : tableCells) {
                allCells.add(cell.getText());
            }
            List<WebElement> nextButtons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("li[title='Next Page']")));

            if (nextButtons.isEmpty()) {
                break;
            }
            WebElement nextButton = nextButtons.get(0);

            if (!nextButton.isEnabled() || nextButton.getDomAttribute("aria-disabled").contains("true")) {
                break;
            }
            nextButton.click();
        }
        return allCells;
    }
}