package com.orangehrm.actiondriver;

import java.time.Duration;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.LoggerManager;

public class ActionDriver {
	private WebDriver driver;
	private WebDriverWait wait;
	public static final Logger logger = BaseClass.logger; 

	public ActionDriver(WebDriver driver) {
		this.driver = driver;
		int explicitWait = Integer.parseInt(BaseClass.getProp().getProperty("explicitWait"));
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
		logger.info("Webdriver instances is created");
	}

	// Method to click element
	public void click(By by) {
		String elementDescription = getElementDescription(by);
		try {
			waitForElementToBeClickable(by);
			driver.findElement(by).click();
			logger.info("clicked an element -->" + elementDescription);
		} catch (Exception e) {
			System.out.println("Unable to click the element" + e.getMessage());
			logger.error("unable to clicked an element");
		}
	}

	public void enterText(By by, String value) {
		try {
			waitForElementToBeVisible(by);
			WebElement element = driver.findElement(by);
			element.clear();
			element.sendKeys(value);
			logger.info("Enter Text on:" +getElementDescription(by)+" " +value);
		} catch (Exception e) {
			logger.error("unable to enter value" + e.getMessage());
		}
	}

	// Method to get the text from in input field
	public String getText(By by) {
		try {
			waitForElementToBeVisible(by);
			return driver.findElement(by).getText();
		} catch (Exception e) {
			logger.error("Unable to get the text" + e.getMessage());
			return "";
		}
	}

	// Method to compare two text -- change the return type
	public boolean compareText(By by, String expectedText) {
		try {
			waitForElementToBeVisible(by);
			String actualText = driver.findElement(by).getText();
			if (expectedText.equals(actualText)) {
				logger.info("Text are matching:" + actualText + "equals" + expectedText);
				return true; 
			} else {
				logger.info("Text are not matching:" + actualText + "equals" + expectedText);
				return false;
			}
		} catch (Exception e) {
			logger.error("Unable to compare text:" + e.getMessage());
		}
		return false;
	}

	// Method to check if an element is displayed
	public boolean isDisplayed(By by) {
		try {
			waitForElementToBeVisible(by);
			logger.info("Element is displayed:"+ getElementDisplayed(by));
			return driver.findElement(by).isDisplayed();
		} catch (Exception e) {
			logger.error("Elemnt is not displayed" + e.getMessage());
			return false;
		}
	}

	// Wait for page load
	public void waitForPageLoad(int timeOutInSec){
		try {
			wait.withTimeout(Duration.ofSeconds(timeOutInSec)).until(WebDriver ->((JavascriptExecutor) WebDriver).executeScript("return document.readyState")
			.equals("complete"));
			logger.info("Page load successfully");
		} catch (Exception e) {
			logger.error("Page does not load successfully"+timeOutInSec+ "Seconds Exception" + e.getMessage());
		}
	}
	
	public void scrollToElement(By by) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(by);
			js.executeScript("argument[0],scrollInToView(true);", element);
		} catch (Exception e) {
			logger.error("Unable to locate Element" +e.getMessage());
		}
	}
	

	// Wait for element to be clickable
	private void waitForElementToBeClickable(By by) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("element is not clickable" + e.getMessage());
		}
	}

	// Wait for element to be visible
	private void waitForElementToBeVisible(By by) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			logger.error("element is not visible" + e.getMessage());
		}
	}
	
	// Method to get the description of an element using by locator
	
	public String getElementDescription(By locator) {
		// Check for null driver or locator to avoid null pointer exception
		if(driver==null) 
			return "driver is null"; 
		
		if(locator==null) 
			return "locator is null"; 
		
		
		try {
			// find the elemnent using locator 
			WebElement element = driver.findElement(locator); 
			
			// get element Description 
			String name = element.getDomAttribute("name");
			String id = element.getDomAttribute("id");
			String text = element.getText();
			String className = element.getDomAttribute("class");
			String placeHolder = element.getDomAttribute("placeholder");
			
			// return the descrition based on element attribute
			if(isNotEmpty(name)) {
				return "Element with name:" +name;
			}
			else if(isNotEmpty(id)) {
				return  "Element with id:" +id;
			}
			else if(isNotEmpty(text)) {
				return  "Element with text:" + truncate(text,50);
			}
			
			else if(isNotEmpty(className)) {
				return  "Element with className:" + className;
			}
			
			else if(isNotEmpty(placeHolder)) {
				return  "Element with placeholder:" + placeHolder;
			}
		} catch (Exception e) {
			logger.info("Unable to describe the element"+e.getMessage());
		}
		return "Unable to describe the element"; 
		
	}
		
		// utility method to check a string is not null or empty 
				private boolean isNotEmpty(String value) {
					return value!=null && !value.isEmpty(); 
	}
				
		// utility method to trancate long string 
				private String truncate(String value,int maxLength) {
					if(value==null || value.length()<=maxLength) {
						return value; 
					}
					return value.substring(0,maxLength)+"..."; 
				}

}
