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

    public AndroidDriver driver;
    public AppiumDriverLocalService service;
    public FormPage formPage;

    //Turn on with -DRUN_ON_BS=true (or if BS creds exist in env).
    private boolean runOnBS() {
        return "true".equalsIgnoreCase(System.getProperty("RUN_ON_BS"))
                || System.getenv("BROWSERSTACK_USERNAME") != null;
    }

    @BeforeClass(alwaysRun = true)
    public void configureAppium() throws IOException {

        // load properties first
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream(
                System.getProperty("user.dir") + "//src//main//java//org//latiffsyed//resources//data.properties")) {
            prop.load(fis);
        }

        String ipAddress = System.getProperty("ipAddress") != null
                ? System.getProperty("ipAddress")
                : prop.getProperty("ipAddress");
        String port = prop.getProperty("port");

        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName(prop.getProperty("AndroidDeviceName"));

        if (runOnBS()) {
            // -------- BrowserStack run --------
            // DO NOT start local Appium; BrowserStack Java SDK + browserstack.yml will route the session.
            // (browserstack.yml should have: username, accessKey, app: bs://<id>, devices, etc.)
            driver = new AndroidDriver(options);  // no URL here

        } else {
            // -------- Local run on this Mac --------
            service = startAppiumServer(ipAddress, Integer.parseInt(port));

            // local-only paths
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
