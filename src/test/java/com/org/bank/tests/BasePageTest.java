package com.org.bank.tests;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import com.org.bank.listners.Listners;

@Listeners(Listners.class)
public abstract class BasePageTest {

	

	@BeforeSuite
	public void softAssertInit() {
		
	}

	@AfterSuite
	public void softAssertCollect() {
		
	}

}
