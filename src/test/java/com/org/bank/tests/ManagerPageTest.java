package com.org.bank.tests;

import java.util.List;

import com.org.bank.constants.CredModalContext;
import com.org.bank.modals.CredModal;
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

	private ManagerPage managerPage;
	private BasePage basePage;
	private SoftAssert softAssert;
	private boolean isLoginSuccessful;

	private String userName;
	@BeforeClass(alwaysRun = true)
	protected void initialization() {
		DriverFactory driverFactory = DriverFactory.newDriverFactory();
		WebDriverContext.setWebDriverContext(ManagerPageTest.class.getName(), driverFactory.getWebDriver());
		basePage = BasePage.newBasePage(driverFactory.getWebDriver());
		LoginPage loginPage = LoginPage.newLoginPage(driverFactory.getWebDriver());
		CredPage credPage = CredPage.newCredPage(driverFactory.getWebDriver());
		managerPage = ManagerPage.newManagerPage(driverFactory.getWebDriver());
		softAssert = new SoftAssert();
		basePage.initialization();
		credPage.enterEmailIdAndSubmit().getAndUpdateCredInDb().navigateToLoginPage();
		CredModal credModal = CredModalContext.getCredModal();
		userName = credModal.getUserName();
		isLoginSuccessful = loginPage.isLoginSuccessful(credModal.getUserName(), credModal.getPassword());
	}

	@AfterClass(alwaysRun = true)
	protected void tearDown() {
		basePage.tearDown();
		softAssert.assertAll();
	}

	@Test(testName = "Verify welcome message", description = "Verify welcome message after successful login", groups = {
			"@all", "@smoke", "@manager" }, priority = 7)
	protected void test_verify_Welcome_Message() {
		if (isLoginSuccessful) {
			String actualMessage = managerPage.getWelcomeMessageText();
			String expectedMessage = managerPage.getWelcomeMessage();
			String message = String.format("Actual welcome message is : %s and expected welcome message is : %s",
					actualMessage, expectedMessage);
			softAssert.assertEquals(actualMessage, expectedMessage, message);
		} else {
			Assert.fail("Login is not successfully so can not navigate to manager page");
		}
	}

	@Test(testName = "Verify managerId message", description = "Verify manager id message after successfull login", groups = {
			"@all", "@sanity", "@manager" }, priority = 8)
	protected void test_verify_ManagerId_Message() {
		if (isLoginSuccessful) {
			String actualMessage = managerPage.getManagerIdText();
			String expectedMessage = managerPage.getManagerIdMessage(userName);
			String message = String.format("Actual managerId is : %s and expected welcome message is : %s",
					actualMessage, expectedMessage);
			softAssert.assertEquals(actualMessage, expectedMessage, message);
		} else {
			Assert.fail("Login is not successfully so can not navigate to manager page");
		}
	}

	@Test(testName = "Verify menu options", description = "Verify menu options on manager page", groups = { "@all",
			"@smoke", "@manager" }, priority = 9)
	protected void test_verify_menu_options() {
		if (isLoginSuccessful) {
			List<String> actualMenuList = managerPage.getAllMenuText();
			List<String> expectedMenuOptions = managerPage.getAllMenuOptions();
			String message = String.format("Actual menu options are : %s and expected menu options are : %s",
					actualMenuList, expectedMenuOptions);
			softAssert.assertEquals(actualMenuList, expectedMenuOptions, message);
		} else {
			Assert.fail("Login is not successfully so can not navigate to manager page");
		}
	}

}
