package org.latiffsyed.testUtils;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Properties;

import org.latiffsyed.core.utils.AppiumUtils;
import org.latiffsyed.pageobjects.android.FormPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class AndroidBaseTest extends AppiumUtils{
	
	public AndroidDriver driver;
	public AppiumDriverLocalService service;
	public FormPage formPage;
	
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
		
		UiAutomator2Options options = new UiAutomator2Options();
		options.setDeviceName(prop.getProperty("AndroidDeviceName"));
		options.setChromedriverExecutable("//Users//administrator//Documents//Chromedriver 138.0.7204.92//chromedriver-mac-x64//chromedriver");
		//options.setApp("//Users//administrator//eclipse-workspace//Appium//src//test//java//resources//ApiDemos-debug.apk");
		options.setApp(System.getProperty("user.dir")+"//src//test//java//org//latiffsyed//resources//General-Store.apk");
		
		
		driver = new AndroidDriver(service.getUrl(), options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		formPage = new FormPage(driver);
		
	}

	
	
	@AfterClass(alwaysRun=true)
	public void tearDown() {
			
		driver.quit();
		service.stop();	

	}

}
