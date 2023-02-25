package com.org.bank.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.org.bank.constants.WebDriverContext;
import com.org.bank.driverFactory.DriverFactory;
import com.org.bank.pages.BasePage;
import com.org.bank.pages.CredPage;
import com.org.bank.pages.LoginPage;
import com.org.bank.pages.ManagerPage;

public class ManagerPageTest {

	private CredPage credPage;
	private LoginPage loginPage;
	private ManagerPage managerPage;
	private BasePage basePage;
	private DriverFactory driverFactory;
	private SoftAssert softAssert;
	private boolean isLoginSuccessfull;
	private String userName;
	private String password;

	@BeforeClass(alwaysRun = true)
	protected void initialization() {
		driverFactory = DriverFactory.newDriverFactory();
		WebDriverContext.setWebDriverContext(ManagerPageTest.class.getName(), driverFactory.getWebDriver());
		basePage = BasePage.newBasePage(driverFactory.getWebDriver());
		loginPage = LoginPage.newLoginPage(driverFactory.getWebDriver());
		credPage = CredPage.newCredPage(driverFactory.getWebDriver());
		managerPage = ManagerPage.newManagerPage(driverFactory.getWebDriver());
		softAssert = new SoftAssert();
		basePage.initialization();
		credPage.enterEmailIdAndSubmit().getAndUpdateCredInDb().navigateToLoginPage();
		userName = System.getProperty("userId");
		password = System.getProperty("password");
		isLoginSuccessfull = loginPage.isLoginSuccessfull(userName, password);
	}

	@AfterClass(alwaysRun = true)
	protected void tearDown() {
		basePage.teadDown();
		softAssert.assertAll();
	}

	@Test(testName = "Verify welcome message", description = "Verify welcome messge after successfull login", groups = {
			"all", "smoke", "managerpage" }, priority = 7)
	protected void test_verify_Welcome_Message() {
		if (isLoginSuccessfull) {
			String actualMessage = managerPage.getWelcomeMessageText();
			String expectedMessage = managerPage.getWelcomeMessage();
			String message = String.format("Actual welcome message is : %s and expected welcome message is : %s",
					actualMessage, expectedMessage);
			softAssert.assertEquals(actualMessage, expectedMessage, message);
		} else {
			Assert.assertTrue(isLoginSuccessfull, "Login is not successfully so can not navigate to manager page");
		}
	}

	@Test(testName = "Verify managerId message", description = "Verify manager id message after successfull login", groups = {
			"all", "sanity", "managerpage" }, priority = 8)
	protected void test_verify_ManagerId_Message() {
		if (isLoginSuccessfull) {
			String actualMessage = managerPage.getManagerIdText();
			String expectedMessage = managerPage.getManagerIdMessage(userName);
			String message = String.format("Actual managerId is : %s and expected welcome message is : %s",
					actualMessage, expectedMessage);
			softAssert.assertEquals(actualMessage, expectedMessage, message);
		} else {
			Assert.assertTrue(isLoginSuccessfull, "Login is not successfully so can not navigate to manager page");
		}
	}

	@Test(testName = "Verify menu options", description = "Verfify menu options on manager page", groups = { "all",
			"smoke", "managerpage" }, priority = 9)
	protected void test_verify_menu_options() {
		if (isLoginSuccessfull) {
			List<String> actualMenuList = managerPage.getAllMenuText();
			List<String> expectedMenuOptions = managerPage.getAllMenuOptions();
			String message = String.format("Actual menuoptions are : %s and expected menu options are : %s",
					actualMenuList, expectedMenuOptions);
			softAssert.assertEquals(actualMenuList, expectedMenuOptions, message);
		} else {
			Assert.assertTrue(isLoginSuccessfull, "Login is not successfully so can not navigate to manager page");
		}
	}

}
