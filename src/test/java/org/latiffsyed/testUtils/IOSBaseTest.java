package org.latiffsyed.testUtils;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.latiffsyed.core.utils.AppiumUtils;
import org.latiffsyed.pageobjects.iOS.HomePage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class IOSBaseTest extends AppiumUtils {
	
	public IOSDriver driver;
	public AppiumDriverLocalService service;
	public HomePage homePage;
	
	@BeforeClass(alwaysRun=true)
	public void configureAppium() throws URISyntaxException, IOException
	{
		
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//org//latiffsyed//resources//data.properties");
		String ipAddress = System.getProperty("ipAddress") !=null ? System.getProperty("ipAddress") : prop.getProperty("ipAddress");
		//System.out.println(ipAddress);
		prop.load(fis);
		//String ipAddress = prop.getProperty("ipAddress");
		String port = prop.getProperty("port");	
		
		
		//Appium code -> Appium Server -> Mobile
		
		service = startAppiumServer(ipAddress, Integer.parseInt(port));
		
		XCUITestOptions options = new XCUITestOptions();
		options.setApp(System.getProperty("user.dir")+"/src/test/java/org/latiffsyed/resources/UIKitCatalog.app");
		//options.setApp("/Users/administrator/eclipse-workspace/Appium/src/test/java/resources/TestApp 3.app");
		options.setDeviceName(prop.getProperty("iOSDeviceName"));
		options.setPlatformVersion(prop.getProperty("iOSPlatformVersion"));
		//Appium - Webdriver Aget -> IOS Apps
		options.setWdaLaunchTimeout(Duration.ofSeconds(30));
	
		driver = new IOSDriver(service.getUrl(), options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		homePage = new HomePage(driver);
		
	}
	
	
	@AfterClass(alwaysRun=true)
	public void tearDown() {
			
		driver.quit();
		service.stop();	
		
	}

}
