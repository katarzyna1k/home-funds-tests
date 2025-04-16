package pom;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class SidebarPanelPom {
    private WebDriver driver;
    private final String SIDEBAR_PANEL_SELECTOR = "//div[contains(@class, 'Sidebar_nav_container')]//a[text()='";

    public Map<String, By> getSidebarFeatureLocators() {
        return sidebarFeatureLocators;
    }

    private final Map<String, By> sidebarFeatureLocators = Map.of(
            "Przegląd", By.xpath(SIDEBAR_PANEL_SELECTOR + "Przegląd" + "']"),
            "Bieżące wydatki", By.xpath(SIDEBAR_PANEL_SELECTOR + "Bieżące wydatki" + "']"),
            "Bilans", By.xpath(SIDEBAR_PANEL_SELECTOR + "Bilans" + "']"),
            "Przychody", By.xpath(SIDEBAR_PANEL_SELECTOR + "Przychody" + "']"),
            "Stałe wydatki", By.xpath(SIDEBAR_PANEL_SELECTOR + "Stałe wydatki" + "']"),
            "Skarbonka", By.xpath(SIDEBAR_PANEL_SELECTOR + "Skarbonka" + "']"),
            "Kalendarz", By.xpath(SIDEBAR_PANEL_SELECTOR + "Kalendarz" + "']")
    );

    public SidebarPanelPom(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getSidebarItem(String key) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        return wait.until(driver -> {
            try {
                WebElement element = driver.findElement(sidebarFeatureLocators.get(key));
                if (element.isDisplayed() && element.isEnabled()) {
                    return element;
                }
                return null;
            } catch (StaleElementReferenceException e) {
                return null;
            }
        });
    }

    public void clickSidebarItem(By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(6)).until(driver -> {
            try {
                WebElement element = driver.findElement(locator);
                if (element.isDisplayed() && element.isEnabled()) {
                    element.click();
                    return true;
                }
                return false;
            } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                return false;
            }
        });
    }
}