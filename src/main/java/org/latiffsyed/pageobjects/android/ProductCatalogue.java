package org.latiffsyed.pageobjects.android;

import java.time.Duration;
import java.util.List;

import org.latiffsyed.core.utils.AndroidActions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class ProductCatalogue extends AndroidActions{
	
	AndroidDriver driver;
	

	public ProductCatalogue(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/productAddCart")
	private List<WebElement> addToCartButtons;
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/appbar_btn_cart")
	private WebElement cart;
		
	
	public List<WebElement> getAddToCartButtons(){
		return addToCartButtons;
	}
	
	
	public void clickAddToCartButton() {
			
		for (int i = 0; i<addToCartButtons.size(); i++) {		
			if (addToCartButtons.get(i).getText().equalsIgnoreCase("ADD TO CART")){		
			addToCartButtons.get(i).click();
			}
		}
		
	}
	
	public CartPage goToCartPage() {
		cart.click();
		return new CartPage(driver);
	}
	
}
