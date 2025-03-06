package cucumber_setup;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import webdriver_service.WebDriverSetup;

public class Hooks extends WebDriverSetup {

    @Before
    public void setUp() throws InterruptedException {
        setUpDriver();
    }

    @After
    public void quitDriver(){
        tearDown();
    }
}
