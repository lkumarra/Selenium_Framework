package com.org.bank.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.org.bank.pages.CredPage;
import com.org.bank.pages.LoginPage;

public class LoginPageTest extends BasePageTest {

	private CredPage credPage;
	private LoginPage loginPage;

	private LoginPageTest() {
		this.credPage = new CredPage();
	}

	@BeforeClass
	private void navigateToLoginPage() {
		loginPage = credPage.enterEmailIdAndSubmit().getAndUpdateCredInDb().navigateToLoginPage();
	}

	@Test(description = "Verify the title of login page", priority = 1, groups = { "loginPage" })
	private void test_verify_title_on_login_page() {
		String actualTitle = loginPage.getTitleOfLoginPage();
		String expectedTitle = loginPage.getLabelText().get(0).get("title").toString();;
		softAssert.assertEquals(loginPage.getTitleOfLoginPage(), "",
				String.format("Actual Title is : %s but expected is : %s", actualTitle, expectedTitle));
	}

	@Test(description = "Verify the userId label on login page", priority = 2, groups = {
			"loginPage" }, dependsOnMethods = { "test_verify_title_on_login_page" })
	private void test_verify_userId_label() {
		String actualLabel = loginPage.getUserIDLabelText();
		String expectedText = loginPage.getLabelText().get(0).get("usedidlabel").toString();
		softAssert.assertEquals(actualLabel, expectedText,
				String.format("Actual lable is : %s and expected is : %s", actualLabel, expectedText));
	}

	@Test(description = "Verify password label text on login page", priority = 3, groups = {
			"loginPage" }, dependsOnMethods = { "test_verify_title_on_login_page" })
	private void test_verify_password_test() {
		String actualText = loginPage.getPasswordLabelText();
		String expectedText = loginPage.getLabelText().get(0).get("passwordlabel").toString();
		softAssert.assertEquals(actualText, expectedText,
				String.format("Actual lable is : %s and expected is : %s", actualText, expectedText));
	}
	
	@Test(description = "Verify submit button text on login page", priority = 4, groups = {
	"loginPage" }, dependsOnMethods = { "test_verify_title_on_login_page" })
	private void test_verify_submit_button_label() {
		String actualText = loginPage.getLoginButtonText();
		String expectedText = loginPage.getLabelText().get(0).get("submitbuttonlabel").toString();
		softAssert.assertEquals(actualText, expectedText,
				String.format("Actual lable is : %s and expected is : %s", actualText, expectedText));
	}

	@Test(description = "Verify reset button text on login page", priority = 5, groups = {
	"loginPage" }, dependsOnMethods = { "test_verify_title_on_login_page" })
	private void test_verify_reset_button_label() {
		String actualText = loginPage.getResetButtonText();
		String expectedText = loginPage.getLabelText().get(0).get("resetbuttonlabel").toString();
		softAssert.assertEquals(actualText, expectedText,
				String.format("Actual lable is : %s and expected is : %s", actualText, expectedText));
	}
}
