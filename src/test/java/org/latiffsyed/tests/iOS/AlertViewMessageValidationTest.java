package org.latiffsyed.tests.iOS;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.latiffsyed.pageobjects.android.ProductCatalogue;
import org.latiffsyed.pageobjects.iOS.AlertViews;
import org.latiffsyed.testUtils.IOSBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;

public class AlertViewMessageValidationTest extends IOSBaseTest{
	
	
	@Test
	public void ValidateiOSAlertMessageTest() {
		
		
		//xapth.className, IOS, iosClassChain, iOSNPredicateString, accessibilityId, id
		AlertViews alertView = homePage.selectAlertViews();
		alertView.selecttextEntry();
		alertView.typeTextField("Hello World");
		alertView.selectOKButton();
		alertView.selectConfirmMenu();	
		String actualText = alertView.getConfirmTextMessage();
		AssertJUnit.assertEquals(actualText, "A message should be a short, complete sentence.");
		//System.out.println(actualText);
		alertView.selectConfirmButton();
	}
}
