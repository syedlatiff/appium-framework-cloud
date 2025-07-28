package org.latiffsyed.pageobjects.android;

import java.util.List;

import org.latiffsyed.core.utils.AndroidActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class CartPage extends AndroidActions {
	
	AndroidDriver driver;
	

	public CartPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/toolbar_title")
	private WebElement pageTitle;
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/productPrice")
	private List<WebElement> productPrice;
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/totalAmountLbl")
	private WebElement totalAmountLabel;
	//driver.findElement(By.id("com.androidsample.generalstore:id/totalAmountLbl")).getText();
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/termsButton")
	private WebElement termsButton;
	//WebElement ele = driver.findElement(By.id("com.androidsample.generalstore:id/termsButton"));
	//longPressAction(ele);
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Terms Of Conditions']")
	private WebElement termsAndConditions;
	//wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//android.widget.TextView[@text='Terms Of Conditions']"), "Terms Of Conditions"));

	@AndroidFindBy(xpath = "//android.widget.Button[@text='CLOSE']")
	private WebElement closeButton;
	//driver.findElement(By.xpath("//android.widget.Button[@text='CLOSE']")).click();
	
	
	@AndroidFindBy(className = "android.widget.CheckBox")
	private WebElement termsCheckbox;
	//driver.findElement(AppiumBy.className("android.widget.CheckBox")).click();
	
	
	@AndroidFindBy(xpath = "//android.widget.Button[@text='Visit to the website to complete purchase']")
	private WebElement visitWebsiteButton;
	//driver.findElement(By.xpath("//android.widget.Button[@text='Visit to the website to complete purchase']")).click();

	
	public void waitForCartPageTitle(int timeoutSeconds) {
		
		waitForTextToBePresent(pageTitle, "Cart", timeoutSeconds, driver);
		
	}
	
	public List<WebElement> getProductPrices() {
		return productPrice;
	}
	
	public double getProductSum() {
		
		//List<WebElement> productPrices = cartPage.getProductPrices();
		int count = productPrice.size();
		double totalSum = 0;
		for (int i=0; i<count; i++) {
			
			String amount = productPrice.get(i).getText();
			Double price = getFormettedAmount(amount);
			totalSum = totalSum + price;
			
		}
		return totalSum;
		
	}
	
	public Double getTotalAmountDisplayed() {
		return getFormettedAmount(totalAmountLabel.getText());
	}
	
	public void longPressTermsButton() {
		
		longPressAction(termsButton);
		
	}
	
	public void waitForTermsofConditionsText(int timeoutSeconds) {
		
		waitForTextToBePresent(termsAndConditions, "Terms Of Conditions", timeoutSeconds, driver);
		
	}

	public void clickCloseButton() {
		closeButton.click();
	}
	
	public void acceptTermsCheckbox() {
		termsCheckbox.click();
	}
	
	public void submitOrder() {
		visitWebsiteButton.click();
	}
}
