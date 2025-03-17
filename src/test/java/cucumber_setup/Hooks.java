package cucumber_setup;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import utils.AppManager;
import webdriver_service.WebDriverSetup;

public class Hooks {
    private static WebDriver driver;

    @BeforeAll
    public static void startAppOnce(){
        AppManager.startApp();
    }

    @Before
    public void setUpDriver() {
        driver = WebDriverSetup.getDriver();
    }

    @After
    public void quitDriver() {
        WebDriverSetup.quitDriver();
    }

    @AfterAll
    public static void stopAppOnce() {
        AppManager.stopApp();
    }

    public static WebDriver getDriver() {
        return driver;
    }
}
