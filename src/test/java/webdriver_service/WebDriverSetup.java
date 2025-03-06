package webdriver_service;

import configuration.ConfigurationManager;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.commons.lang3.StringUtils;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public abstract class WebDriverSetup {

    protected static final String SELECTED_BROWSER_SYSTEM_PROPERTY = "selenium.browser";
    protected static final String BROWSER_CHROME = "chrome";
    protected static final String BROWSER_FIREFOX = "firefox";
    protected static final String BROWSER_EDGE = "edge";
    protected static WebDriver driver;

    public static void setUpDriver() throws InterruptedException {
        if (!isAppRunning()) {
            startApp();
        }
        String browser = System.getProperty(SELECTED_BROWSER_SYSTEM_PROPERTY, BROWSER_CHROME);
        if (driver == null) {
            driver = createWebDriver(browser);
        }
    }

    protected static WebDriver createWebDriver(String type) {
        if (StringUtils.isBlank(type)) {
            throw new IllegalArgumentException("Browser type must be specified and cannot be null or empty");
        }

        return switch (type) {
            case BROWSER_FIREFOX -> createFirefoxDriver();
            case BROWSER_CHROME -> createChromeDriver();
            case BROWSER_EDGE -> createEdgeDriver();
            default -> throw new IllegalArgumentException("Unknown browser type: " + type);
        };
    }

    @NotNull
    private static FirefoxDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver();
    }

    @NotNull
    private static EdgeDriver createEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        return new EdgeDriver();
    }

    @NotNull
    private static ChromeDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }

    private static boolean isAppRunning() {
        try {
            URL url = URI.create(ConfigurationManager.getUrl()).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(2000);
            int responseCode = connection.getResponseCode();
            return (responseCode >= 200 && responseCode < 400);
        } catch (IOException e) {
            return false;
        }
    }

    private static void startApp() throws InterruptedException {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "npm run start");
            processBuilder.directory(new java.io.File("../home-funds"));
            processBuilder.start();
            Thread.sleep(10000);
        } catch (Exception e) {
            throw new RuntimeException("Failed to start the app", e);
        }
    }

    private static void stopApp() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "taskkill /F /IM node.exe");
            processBuilder.start();
        } catch (Exception e) {
            System.out.println("Failed to stop the app" + e.getMessage());
        }
    }

    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        stopApp();
    }
}
