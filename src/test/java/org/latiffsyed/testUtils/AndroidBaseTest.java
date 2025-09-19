package org.latiffsyed.testUtils;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.latiffsyed.core.utils.AppiumUtils;
import org.latiffsyed.pageobjects.android.FormPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class AndroidBaseTest extends AppiumUtils {

    protected AndroidDriver driver;
    protected AppiumDriverLocalService service;
    protected FormPage formPage;

    
    private boolean runOnBS() {
        return "true".equalsIgnoreCase(System.getProperty("RUN_ON_BS"))
                || System.getenv("BROWSERSTACK_USERNAME") != null;
    }

    @BeforeClass(alwaysRun = true)
    public void configureAppium() throws IOException {

        // --- load test config ---
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream(
                System.getProperty("user.dir")
                        + "//src//main//java//org//latiffsyed//resources//data.properties")) {
            prop.load(fis);
        }

        String ipAddress = System.getProperty("ipAddress", prop.getProperty("ipAddress"));
        String port      = prop.getProperty("port");

        // --- common driver options ---
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName(prop.getProperty("AndroidDeviceName"));
        // add any other common caps you need here

        if (runOnBS()) {
            // ===== BrowserStack run =====
            // DO NOT start a local Appium server.
            // The BrowserStack Java SDK (enabled by your POM profile) reads browserstack.yml
            // and routes this session to BS when you construct the driver *without* a URL.
            driver = new AndroidDriver(options);

        } else {
            // ===== Local run on this Mac =====
            service = startAppiumServer(ipAddress, Integer.parseInt(port));

            // local-only paths (keep/adjust for your machine)
            options.setChromedriverExecutable("//Users//administrator//Documents//Chromedriver 138.0.7204.92//chromedriver-mac-x64//chromedriver");
            options.setApp(System.getProperty("user.dir") + "//src//test//java//org//latiffsyed//resources//General-Store.apk");

            driver = new AndroidDriver(service.getUrl(), options);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        formPage = new FormPage(driver);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) driver.quit();
        if (!runOnBS() && service != null) service.stop();
    }
}