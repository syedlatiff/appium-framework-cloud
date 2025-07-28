package org.latiffsyed.tests.android;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.google.common.collect.ImmutableMap;

import org.testng.AssertJUnit;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.latiffsyed.pageobjects.android.CartPage;
import org.latiffsyed.pageobjects.android.FormPage;
import org.latiffsyed.pageobjects.android.ProductCatalogue;
import org.latiffsyed.testUtils.AndroidBaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class PurchaseOrderFlowTest extends AndroidBaseTest{
	
	
	@Test(dataProvider="getData", groups={"Smoke"})
	public void EndToEndOrderFlowTest(HashMap<String, String> input) throws InterruptedException
	{
		//when this step triggers FormPage constructor method gets executed which is in FormPage.java
		//In the process of execution this driver is send to constructor method 
		//FormPage formPage = new FormPage(driver);
		formPage.setNameField(input.get("name"));
		formPage.setGender(input.get("gender"));
		formPage.setCountrySelection(input.get("country"));
		ProductCatalogue productCatalogue = formPage.submitForm();		
		productCatalogue.clickAddToCartButton();			
		CartPage cartPage = productCatalogue.goToCartPage();
		
		cartPage.waitForCartPageTitle(5);
						
		double totalSum = cartPage.getProductSum();
		double totalPurchaseAmountDisplayed = cartPage.getTotalAmountDisplayed();
		AssertJUnit.assertEquals(totalSum, totalPurchaseAmountDisplayed);		
		cartPage.longPressTermsButton();
		cartPage.waitForTermsofConditionsText(5);	
		cartPage.clickCloseButton();
		cartPage.acceptTermsCheckbox();
		cartPage.submitOrder();						
	}
	
	@BeforeMethod(alwaysRun=true)
	public void preSetup() {
		formPage.setActivity();
	}
		
	@DataProvider
	public Object[][] getData() throws IOException{
		
		List<HashMap<String, String>> data = getJsonData(System.getProperty("user.dir")+ "//src//test//java//org//latiffsyed//testData//eCommerce.json");
		//return new Object[][] { {"Nadu Syed", "Female", "Australia"},{"Latiff Syed", "Male", "Argentina"} };
		return new Object[][] {{data.get(0)}};
		//return new Object[][] {{data.get(0)}, {data.get(1)}};
	
	}
	
}
