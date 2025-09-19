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
            System.out.println("Running on BrowserStack - using remote capabilities");
            
            // BrowserStack capabilities
            String bsUsername = System.getProperty("BROWSERSTACK_USERNAME") != null ? 
                               System.getProperty("BROWSERSTACK_USERNAME") : System.getenv("BROWSERSTACK_USERNAME");
            String bsAccessKey = System.getProperty("BROWSERSTACK_ACCESS_KEY") != null ? 
                                System.getProperty("BROWSERSTACK_ACCESS_KEY") : System.getenv("BROWSERSTACK_ACCESS_KEY");
            String bsAppUrl = System.getProperty("BROWSERSTACK_APP_URL") != null ? 
                             System.getProperty("BROWSERSTACK_APP_URL") : System.getenv("BROWSERSTACK_APP_URL");
            String bsDevice = System.getProperty("bs.device") != null ? 
                             System.getProperty("bs.device") : "Google Pixel 7";
            String bsOsVersion = System.getProperty("bs.os_version") != null ? 
                                System.getProperty("bs.os_version") : "13.0";
            
            // Set BrowserStack specific capabilities
            options.setCapability("bstack:options", new java.util.HashMap<String, Object>() {{
                put("userName", bsUsername);
                put("accessKey", bsAccessKey);
                put("projectName", System.getProperty("bs.project", "AppiumFrameworkCloud"));
                put("buildName", System.getProperty("bs.build", "Local Build"));
                put("sessionName", System.getProperty("bs.name", "Android Test"));
                put("debug", true);
                put("networkLogs", true);
                put("consoleLogs", "info");
            }});
            
            // Device capabilities
            options.setDeviceName(bsDevice);
            options.setPlatformVersion(bsOsVersion);
            options.setApp(bsAppUrl);
            
            // BrowserStack hub URL
            String hubUrl = String.format("https://%s:%s@hub-cloud.browserstack.com/wd/hub", 
                                         bsUsername, bsAccessKey);
            
            try {
                driver = new AndroidDriver(new URL(hubUrl), options);
            } catch (MalformedURLException e) {
                throw new RuntimeException("Invalid BrowserStack URL", e);
            }
            
        } else {
            // -------- Local run --------
            System.out.println("Running locally - starting Appium server");
            
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