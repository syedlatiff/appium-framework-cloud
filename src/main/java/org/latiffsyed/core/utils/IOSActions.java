package org.latiffsyed.core.utils;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;

public class IOSActions extends AppiumUtils{
	
	IOSDriver driver;
	
	public IOSActions(IOSDriver driver) {
		
		//super(driver);
		this.driver = driver;
	}
	
	
	public void longPressAndHold(WebElement ele, int durationSeconds) {
		
		Map <String, Object> params= new HashMap<>();	
		params.put("element", ((RemoteWebElement)ele).getId());
		params.put("duration", durationSeconds);
		driver.executeScript("mobile:touchAndHold", params);
	}
	
	
	public void swipe(/*WebElement ele ,*/ String direction) {
		Map<String, Object> params = new HashMap<>();
		//params.put("element", ((RemoteWebElement) ele).getId());
		params.put("direction", direction);
		driver.executeScript("mobile:swipe", params);
	}
	
	public void launchApp(String bundleId) {
		Map<String, String> params = new HashMap<>();
		params.put("bundleId", bundleId);
		driver.executeScript("mobile:launchApp", params);
	}
	
	
	public void scrollToWebElement(WebElement ele, String direction) {
		
		Map<String, Object> params = new HashMap<>();
		params.put("element", ((RemoteWebElement)ele).getId());
		params.put("direction", direction);
		driver.executeScript("mobile:scroll", params);

		
	}
	
}
