package com.org.bank.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.org.bank.driverFactory.DriverFactory;
import com.org.bank.listners.Listners;
import com.org.bank.pages.BasePage;
import com.org.bank.pages.CredPage;
import com.org.bank.pages.LoginPage;

@Listeners(Listners.class)
public class LoginPageTest extends BasePageTest {

	private CredPage credPage;
	private LoginPage loginPage;
	private BasePage basePage;
	private DriverFactory driverFactory;
	private SoftAssert softAssert;

	@BeforeClass(alwaysRun = true)
	protected void initialization() {
		driverFactory = new DriverFactory();
		basePage = new BasePage(driverFactory.getWebDriver());
		loginPage = new LoginPage(driverFactory.getWebDriver());
		credPage = new CredPage(driverFactory.getWebDriver());
		softAssert = new SoftAssert();
		basePage.initialization();
		credPage.enterEmailIdAndSubmit().getAndUpdateCredInDb().navigateToLoginPage();
	}

	@AfterClass(alwaysRun = true)
	protected void tearDown() {
		basePage.teadDown();
		softAssert.assertAll();
	}

	@Test(description = "Verify the title of login page", priority = 1, groups = { "loginPage" })
	private void test_verify_title_on_login_page() {
		String actualTitle = loginPage.getTitleOfLoginPage().trim();
		String expectedTitle = loginPage.getLoginPageLabelsText().getTitle().trim();
		Assert.assertEquals(actualTitle, expectedTitle,
				String.format("Actual Title is : %s but expected is : %s", actualTitle, expectedTitle));
	}

	@Test(description = "Verify the userId label on login page", priority = 2, groups = {
			"loginPage" }, dependsOnMethods = { "test_verify_title_on_login_page" })
	private void test_verify_userId_label() {
		String actualLabel = loginPage.getUserIDLabelText().trim();
		String expectedText = loginPage.getLoginPageLabelsText().getUsedidlabel().trim();
		softAssert.assertEquals(actualLabel, expectedText,
				String.format("Actual lable is : %s and expected is : %s", actualLabel, expectedText));
	}

	@Test(description = "Verify password label text on login page", priority = 3, groups = {
			"loginPage" }, dependsOnMethods = { "test_verify_title_on_login_page" })
	private void test_verify_password_test() {
		String actualText = loginPage.getPasswordLabelText();
		String expectedText = loginPage.getLoginPageLabelsText().getPasswordlabel();
		softAssert.assertEquals(actualText, expectedText,
				String.format("Actual lable is : %s and expected is : %s", actualText, expectedText));
	}

	@Test(description = "Verify submit button text on login page", priority = 4, groups = {
			"loginPage" }, dependsOnMethods = { "test_verify_title_on_login_page" })
	private void test_verify_submit_button_label() {
		String actualText = loginPage.getLoginButtonText();
		String expectedText = loginPage.getLoginPageLabelsText().getSubmitbuttonlabel();
		softAssert.assertEquals(actualText, expectedText,
				String.format("Actual lable is : %s and expected is : %s", actualText, expectedText));
	}

	@Test(description = "Verify reset button text on login page", priority = 5, groups = {
			"loginPage" }, dependsOnMethods = { "test_verify_title_on_login_page" })
	private void test_verify_reset_button_label() {
		String actualText = loginPage.getResetButtonText();
		String expectedText = loginPage.getLoginPageLabelsText().getResetbuttonlabel();
		softAssert.assertEquals(actualText, expectedText,
				String.format("Actual lable is : %s and expected is : %s", actualText, expectedText));
	}

	@Test(description = "Verify error alert message by entering invalid credentials", priority = 6, groups = {
			"loginPage", "smoke" }, dependsOnMethods = { "test_verify_title_on_login_page" })
	private void test_verify_invalid_cred_error_message() {
		List<String> actualMessages = loginPage.getErrorAlertMessagesForInvalidCredentials();
		List<String> expectedMessage = loginPage.getExpectedErrorMessages();
		softAssert.assertEquals(actualMessages, expectedMessage);
	}

}
