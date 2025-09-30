package com.orangehrm.test;

import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;

public class DummyClass2 extends BaseClass{
	@Test	
	public void dummytest() {
			String title = driver.getTitle();		
			assert title.equals("OrangeHRM") : "Test failed - Test is not matching"; 
			System.out.println("Test Passed - title is matching");
			
		}
}
