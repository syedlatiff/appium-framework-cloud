package org.latiffsyed.pageobjects.iOS;

import org.latiffsyed.core.utils.IOSActions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class HomePage extends IOSActions{
	
	IOSDriver driver;
	

	public HomePage(IOSDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	//driver.findElement(AppiumBy.accessibilityId("Alert Views")).click();
	@iOSXCUITFindBy(accessibility= "Alert Views")
	private WebElement alertView;
	
	
	public AlertViews selectAlertViews() {
		alertView.click();
		return new AlertViews(driver);
	}
	
}
