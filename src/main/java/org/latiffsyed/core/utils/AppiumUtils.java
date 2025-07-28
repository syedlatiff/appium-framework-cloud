package org.latiffsyed.core.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class AppiumUtils {
	
	public AppiumDriverLocalService service;

	
	public AppiumDriverLocalService startAppiumServer(String ipAddress, int port) {
		
		service = new AppiumServiceBuilder()
				.withAppiumJS(new File("//usr//local//lib//node_modules//appium//build//lib//main.js"))
				.withIPAddress(ipAddress)
				.usingPort(port)
				.build();
		service.start();
		return service;
	}
	
	public Double getFormettedAmount(String amount) {
		Double price = Double.parseDouble(amount.substring(1));
		return price;
	}
	
	public void waitForTextToBePresent(WebElement element, String text, int timeoutSeconds, AppiumDriver driver) {
		new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
		.until(ExpectedConditions.textToBePresentInElement(element, text));
	}
	
	public void waitForSeconds(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public List<HashMap<String, String>> getJsonData(String jsonFilePath) throws IOException{
		
		String jsonContent = FileUtils.readFileToString(new File(jsonFilePath), StandardCharsets.UTF_8);
		
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, 
				new TypeReference<List<HashMap<String, String>>>(){
			
		});
		return data;
	}	
	
	public String getScreenShotPath(String testCaseName, AppiumDriver driver) throws IOException {
		
		//1. capture and place in the folder and return the file path to extent report
		//2. extent report pick file and attach to report. this is done in the listener onTestFailure.
		File source = driver.getScreenshotAs(OutputType.FILE);
		String destinationFile = System.getProperty("user.dir")+"//reports"+testCaseName+".png";
		FileUtils.copyFile(source, new File(destinationFile));
		return destinationFile;
		
		
		
		
		
		
		
		
		
	}
	

}
