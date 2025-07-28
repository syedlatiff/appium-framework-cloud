package org.latiffsyed.pageobjects.iOS;

import org.latiffsyed.core.utils.IOSActions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class AlertViews extends IOSActions{
	
	IOSDriver driver;
	

	public AlertViews(IOSDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	
	//driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeStaticText[`name == 'Text Entry'`]")).click();
	@iOSXCUITFindBy(iOSClassChain= "**/XCUIElementTypeStaticText[`name == 'Text Entry'`]")
	private WebElement textEntryMenu;
	
	//driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeTextField")).sendKeys("Hello World!");
	@iOSXCUITFindBy(iOSClassChain= "**/XCUIElementTypeTextField")
	private WebElement textField;
	
	//driver.findElement(AppiumBy.accessibilityId("OK")).click();
	@iOSXCUITFindBy(accessibility= "OK")
	private WebElement okButton;
	
	//driver.findElement(AppiumBy.iOSNsPredicateString("name == 'Confirm / Cancel'")).click();
	 @iOSXCUITFindBy(iOSNsPredicate = "name == 'Confirm / Cancel'")
	 private WebElement confirmMenu;
	 
	 //driver.findElement(AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeStaticText' AND name BEGINSWITH[c] 'A message'")).getText();
	 @iOSXCUITFindBy(iOSNsPredicate = "type == 'XCUIElementTypeStaticText' AND name BEGINSWITH[c] 'A message'")
	 private WebElement confirmTextMessage;
	 
	 //driver.findElement(AppiumBy.iOSNsPredicateString("label == 'Confirm'")).click();
	 @iOSXCUITFindBy(iOSNsPredicate = "label == 'Confirm'")
	 private WebElement confirmButton;
	
	public void selecttextEntry() {
		textEntryMenu.click();
	}
	
	public void typeTextField(String text) {
		textField.sendKeys(text);
	}
	
	public void selectOKButton() {
		okButton.click();
	}
	
	public void selectConfirmMenu() {
		confirmMenu.click();
	}
	
	public String getConfirmTextMessage() {
		return confirmTextMessage.getText();
	}
	
	public void selectConfirmButton() {
		confirmButton.click();
	}
}
