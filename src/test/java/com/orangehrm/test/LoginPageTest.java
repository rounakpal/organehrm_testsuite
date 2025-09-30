package com.orangehrm.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;

public class LoginPageTest extends BaseClass {
	
	
		private LoginPage loginPage;
		private HomePage homePage; 
		
		@BeforeMethod
		public void setupPage() {
			loginPage = new LoginPage(getDriver()); 
			homePage = new HomePage(getDriver());
		}
		
		@Test
		public void verifyValidLoginTest() {
			loginPage.login("Admin","admin123");
			Assert.assertTrue(homePage.isAdminTabVisible(),"Admin tab should be visible after successfull Login");
			homePage.logout();
			staticWait(2); 
		}
		
		@Test
		public void invalidLoginTest() {
			loginPage.login("Admin","admin");
			String expectedErrorMessage = "Invalid credentials"; 
			Assert.assertTrue(loginPage.verifyErrorMessage(expectedErrorMessage),"Text message is not matching");
			
		}
}
