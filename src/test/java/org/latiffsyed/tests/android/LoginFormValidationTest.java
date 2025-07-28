package org.latiffsyed.tests.android;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.latiffsyed.testUtils.AndroidBaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.android.Activity;

public class LoginFormValidationTest extends AndroidBaseTest{
	
	
	@BeforeMethod
	public void preSetup() {
		System.out.println("Executing @BeforeMethod: preSteup()");
		if (driver == null) {
			
			System.out.println("Driver is null, check @BeforeClass in BeseTest");
		}
		try {
			
			Activity activity = new Activity("com.androidsample.generalstore", "com.androidsample.generalstore.SplashActivity");

			((JavascriptExecutor) driver).executeScript("mobile: startActivity", ImmutableMap.of(
				    "intent", "com.androidsample.generalstore/com.androidsample.generalstore.SplashActivity"));
			
		}catch (Exception e) {
			
		}
	
	}

	@Test
	public void FormLogin_PositiveFlowTest() throws InterruptedException
	{
		formPage.setNameField("Latiff Syed");
		formPage.setGender("Male");
		formPage.setCountrySelection("Australia");
		formPage.submitForm();		
		AssertJUnit.assertTrue(formPage.getErrorMessageCount()<1);	
			
	}
	
	@Test
	public void FormLogin_ErrorValidationTest() throws InterruptedException
	{
		formPage.setGender("Female");
		formPage.setCountrySelection("Australia");
		formPage.submitForm();	
		formPage.waitForSeconds(3);
		String toastMessage = formPage.getErrorMessage();
		AssertJUnit.assertEquals(toastMessage,"Please enter your name");	
			
	}

}
