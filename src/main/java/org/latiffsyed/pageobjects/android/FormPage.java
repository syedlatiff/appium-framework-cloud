package org.latiffsyed.pageobjects.android;

import org.jspecify.annotations.Nullable;
import org.latiffsyed.core.utils.AndroidActions;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class FormPage extends AndroidActions{
	
	AndroidDriver driver;
	
	//constructor is some which is called when we create a object of this class
	//rule of constructor is that whenever you create a object a constructor gets executed 
	public FormPage(AndroidDriver driver) {
		super(driver);//tells Java to call parent constructor and pass driver
		//this represents current class(FormPage) instance/global variable which is (AndroidDriver driver)
		this.driver = driver;
		//initElements  method does the construction of element and 
		//this is given in the constructor method because it is being called as soon as we defined object
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		
	}
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/nameField")
	private WebElement nameField;
	//driver.findElement(By.id("com.androidsample.generalstore:id/nameField"))
	
	@AndroidFindBy(xpath = "//android.widget.RadioButton[@text='Female']")
	private WebElement femaleOption;
	//driver.findElement(By.xpath("//android.widget.RadioButton[@text='Female']"))
	
	@AndroidFindBy(xpath = "//android.widget.RadioButton[@text='Male']")
	private WebElement maleOption;
	//driver.findElement(By.xpath("//android.widget.RadioButton[@text='Male']"))
	
	@AndroidFindBy(id = "android:id/text1")
	private WebElement countrySelection;
	
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/btnLetsShop")
	private WebElement submitForm;
	
	//driver.findElement(By.xpath("(//android.widget.Toast)[1]")).getAttribute("name");
	//@AndroidFindBy(xpath = "(//android.widget.Toast)[1]")
	//private WebElement errorMessage;
	
	By errorMessageLocator = By.xpath("(//android.widget.Toast)[1]");
	
	public void setActivity() {
		Activity activity = new Activity("com.androidsample.generalstore", "com.androidsample.generalstore.SplashActivity");

		((JavascriptExecutor) driver).executeScript("mobile: startActivity", ImmutableMap.of(
			    "intent", "com.androidsample.generalstore/com.androidsample.generalstore.SplashActivity"));
	}
	
	public void setNameField(String name) {
		
		nameField.sendKeys(name);
		driver.hideKeyboard();
				
	}
	
	public void setGender(String gender) {
		
		if (gender.contains("Female")) 
			femaleOption.click();
		else 
			maleOption.click();
		
	}
	
	public void setCountrySelection(String countryName) {
		
		countrySelection.click();
		scrollToElementAction(countryName);
		driver.findElement(By.xpath("//android.widget.TextView[@text='"+countryName+"']")).click();
	}
	
	public String getErrorMessage() {
		return driver.findElement(errorMessageLocator).getAttribute("name");
	}
	
	public int getErrorMessageCount() {
		
		return driver.findElements(errorMessageLocator).size();
		
	}
	
	public ProductCatalogue submitForm() {
		submitForm.click();
		return new ProductCatalogue(driver);
	}

}
