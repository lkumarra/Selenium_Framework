package com.org.bank.tests;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import com.org.bank.pages.BasePage;

public class BasePageTest {

	private BasePage basePage;
	SoftAssert softAssert;
	
	public BasePageTest() {
		this.basePage = new BasePage();
		this.softAssert = new SoftAssert();
	}

	@BeforeSuite
	public void startInitialization() {
		basePage.initialization();
	}

	@AfterSuite
	public void tearDown() {
		basePage.teadDown();
		softAssert.assertAll();
	}

}
