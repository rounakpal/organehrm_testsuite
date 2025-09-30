package com.orangehrm.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.utilities.LoggerManager;

public class BaseClass {
	protected static Properties prop;
	protected static WebDriver driver;
	protected static ActionDriver actionDriver; 
	public static final Logger logger = LoggerManager.getLogger(BaseClass.class);  
	
	@BeforeSuite
	public void loadConfig() throws IOException {
		// Load the configration file
				prop = new Properties();
				FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
				prop.load(fis);
				logger.info("config.properties file loaded");
				logger.trace("This is the trace message"); 
				logger.error("This is the error message"); 
				logger.debug("This is the debug message"); 
				logger.fatal("This is the fatal message"); 
				logger.warn("This is the warn message");
	}
	
	@BeforeMethod
	public void setup() throws IOException {
		System.out.println("Setting up webdriver for"+ this.getClass().getSimpleName()); 
		launchBrowser(); 
		configureBrowser(); 
		staticWait(2);
		logger.info("Webdriver Intialized amd browser maximized"); 
		
		//Intialize the actionDriver only once 
		if(actionDriver == null) {
			actionDriver = new ActionDriver(driver); 
			logger.info("Webdriver insatnce is created"); 
		}
		
	}
	
	// Initialise the Webdriver based on browser defined in config.properties file
	private void launchBrowser() {
				String browser = prop.getProperty("browser");

				if (browser.equalsIgnoreCase("chrome")) {
					driver = new ChromeDriver();
					logger.info("Chrome browser instance is created");
				}

				else if (browser.equalsIgnoreCase("firefox")) {
					driver = new FirefoxDriver();
					logger.info("firefox instance is created");
				}

				else if (browser.equalsIgnoreCase("edge")) {
					driver = new EdgeDriver();
					logger.info("edge instance is created");
				}

				else {
					throw new IllegalArgumentException("Browser Not Supported" + browser);
				}
	}
	
	private void configureBrowser() {
		// Implicit Wait
				int implicitWait = Integer.parseInt(prop.getProperty("implicitWait")); // convert the time into integer

				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

				// maximize the browser
				driver.manage().window().maximize();

				// Navigate to the url
				try {
					driver.get(prop.getProperty("url"));
				} catch (Exception e) {
					System.out.print("Failed to navigate to the url"+e.getMessage());
				}
	}
	
	@AfterMethod
	public void tearDown() {
		if(driver!=null) {
			try {
				driver.quit();
			} catch (Exception e) {
				System.out.print("Failed to quit to the url"+e.getMessage());
			}
		}
		logger.info("Webdriver instance closed successfully.");
		driver=null; 
		actionDriver=null; 
	}
		
	// Getter method for prop 
	public static Properties getProp() {
		return prop;
	}
	
	// Getter Method for WebDriver
	public static WebDriver getDriver() {
		if(driver == null) {
			System.out.println("Webdriver is not initialized"); 
			throw new IllegalStateException("Webdriver is not imitalized"); 
		}
		return driver;
	}
	
	 // Getter Method for Action Driver
	public static ActionDriver getActionDriver() {
		if(actionDriver == null) {
			System.out.println("Actiondriver is not initialized"); 
			throw new IllegalStateException("Actiondriver is not imitalized"); 
		}
		return actionDriver;
	}
	
	// Driver getter method
//	public WebDriver getDriver() {
//		return driver;
//	}
	// Driver setter method
	public void setDriver(WebDriver driver) {
		this.driver = driver; 
	}
	
//	 Static Wait for pause 
	public void staticWait(int seconds) {
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
	}
	
	

}
