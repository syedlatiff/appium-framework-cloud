package org.latiffsyed.testUtils;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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

    // Turn on with -DRUN_ON_BS=true (or if BS creds exist in env).
    private boolean runOnBS() {
        return "true".equalsIgnoreCase(System.getProperty("RUN_ON_BS")) || 
               System.getenv("BROWSERSTACK_USERNAME") != null;
    }

    @BeforeClass(alwaysRun = true)
    public void configureAppium() throws IOException {
        
        System.out.println("=== ANDROID TEST DEBUG INFO ===");
        System.out.println("RUN_ON_BS property: " + System.getProperty("RUN_ON_BS"));
        System.out.println("PLATFORM property: " + System.getProperty("PLATFORM"));
        System.out.println("BROWSERSTACK_USERNAME: " + (System.getProperty("BROWSERSTACK_USERNAME") != null ? "SET" : "NOT SET"));
        System.out.println("runOnBS() result: " + runOnBS());
        System.out.println("================================");
        
        // Explicit platform check
        String platform = System.getProperty("PLATFORM", "android").toLowerCase();
        if (!platform.equals("android")) {
            throw new RuntimeException("This test is configured for Android only! Current platform: " + platform);
        }
        
        // load properties first
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream(
                System.getProperty("user.dir") + "//src//main//java//org//latiffsyed//resources//data.properties")) {
            prop.load(fis);
        }

        String ipAddress = System.getProperty("ipAddress") != null ? 
                          System.getProperty("ipAddress") : prop.getProperty("ipAddress");
        String port = prop.getProperty("port");

        UiAutomator2Options options = new UiAutomator2Options();
        
        if (runOnBS()) {
            // -------- BrowserStack run --------
            System.out.println("Running ANDROID tests on BrowserStack - using remote capabilities");
            
            // Get BrowserStack credentials from system properties
            String bsUsername = System.getProperty("BROWSERSTACK_USERNAME");
            String bsAccessKey = System.getProperty("BROWSERSTACK_ACCESS_KEY");
            String bsAppUrl = System.getProperty("BROWSERSTACK_APP_URL");
            String bsDevice = System.getProperty("bs.device", "Google Pixel 7");
            String bsOsVersion = System.getProperty("bs.os_version", "13.0");
            
            if (bsUsername == null || bsAccessKey == null) {
                throw new RuntimeException("BrowserStack credentials not found! Please set BROWSERSTACK_USERNAME and BROWSERSTACK_ACCESS_KEY");
            }
            
            // Ensure we're explicitly setting Android platform
            options.setPlatformName("Android");
            
            // Set BrowserStack specific capabilities
            options.setCapability("bstack:options", new java.util.HashMap<String, Object>() {{
                put("userName", bsUsername);
                put("accessKey", bsAccessKey);
                put("projectName", "AppiumFrameworkCloud");
                put("buildName", System.getProperty("bs.build", "Android Only Build"));
                put("sessionName", System.getProperty("bs.name", "Android Test"));
                put("debug", true);
                put("networkLogs", true);
                put("consoleLogs", "info");
            }});
            
            // Android Device capabilities
            options.setDeviceName(bsDevice);
            options.setPlatformVersion(bsOsVersion);
            options.setApp(bsAppUrl);
            
            // BrowserStack hub URL
            String hubUrl = String.format("https://%s:%s@hub-cloud.browserstack.com/wd/hub", 
                                         bsUsername, bsAccessKey);
            
            try {
                driver = new AndroidDriver(new java.net.URL(hubUrl), options);
                System.out.println("Successfully connected Android driver to BrowserStack!");
                System.out.println("Session ID: " + driver.getSessionId());
            } catch (java.net.MalformedURLException e) {
                throw new RuntimeException("Invalid BrowserStack URL", e);
            }
            
        } else {
            // -------- Local run --------
            System.out.println("Running Android tests locally - starting Appium server");
            
            service = startAppiumServer(ipAddress, Integer.parseInt(port));
            
            // Local device capabilities
            options.setDeviceName(prop.getProperty("AndroidDeviceName"));
            options.setChromedriverExecutable("//Users//administrator//Documents//Chromedriver 138.0.7204.92//chromedriver-mac-x64//chromedriver");
            options.setApp(System.getProperty("user.dir") + "//src//test//java//org//latiffsyed//resources//General-Store.apk");
            
            driver = new AndroidDriver(service.getUrl(), options);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        formPage = new FormPage(driver);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        if (!runOnBS() && service != null) {
            service.stop();
        }
    }
}