package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class LoginPage {
		private ActionDriver actionDriver;
		
		// Define Locatore Using by class
		private By userNameField = By.name("username");
		private By passwordField = By.cssSelector("input[type='password']");
		private By loginButton = By.xpath("//button[text()=' Login ']");
		private By errorMessage = By.xpath("//p[text()='Invalid credentials']");
		
		// Initialize the ActionDriver object by passing webdriver instance
	/*	public LoginPage(WebDriver driver) {
			this.actionDriver = new ActionDriver(driver);  
		}  */
		
		public LoginPage(WebDriver driver) {
			this.actionDriver = BaseClass.getActionDriver(); 
		}
		
		//Method to perform Login 
		public void login(String userName,String Password) {
			actionDriver.enterText(userNameField, userName);
			actionDriver.enterText(passwordField, Password);
			actionDriver.click(loginButton);
		}
		
		// Method for error message 
		public boolean getErrorMessageDisplayed(){
			return actionDriver.isDisplayed(errorMessage); 
		}
		
		// Method to get the error message 
		public String getErrorMessageText() {
			return actionDriver.getText(errorMessage);
		}
		
		// Method to check the error message
		public boolean verifyErrorMessage(String expecetedError) {
			return actionDriver.compareText(errorMessage, expecetedError);
		}
		
}
