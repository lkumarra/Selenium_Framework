package com.org.bank.tests;

import java.util.Hashtable;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.org.bank.constants.WebDriverContext;
import com.org.bank.driverFactory.DriverFactory;
import com.org.bank.pages.BasePage;
import com.org.bank.pages.CredPage;
import com.org.bank.pages.LoginPage;
import com.org.bank.pages.ManagerPage;
import com.org.bank.pages.NewCustomerPage;

public class NewCustomerPageTest {

	private CredPage credPage;
	private LoginPage loginPage;
	private BasePage basePage;
	private ManagerPage managerPage;
	private NewCustomerPage newCustomerPage;
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
		newCustomerPage = NewCustomerPage.newCustomerPage(driverFactory.getWebDriver());
		softAssert = new SoftAssert();
		basePage.initialization();
		credPage.enterEmailIdAndSubmit().getAndUpdateCredInDb().navigateToLoginPage();
		userName = System.getProperty("userId");
		password = System.getProperty("password");
		isLoginSuccessfull = loginPage.isLoginSuccessfull(userName, password);
		if (isLoginSuccessfull) {
			managerPage.clickOnNewCustomerPage();
		}
	}

	@AfterClass(alwaysRun = true)
	protected void tearDown() {
		basePage.teadDown();
		softAssert.assertAll();
	}

	@Test(testName = "Verify New Customer Heading", description = "Verify Add new customer heading", groups = { "all",
			"smoke", "newcustomerpage" }, priority = 10)
	protected void test_add_customer_heading_message() {
		if (isLoginSuccessfull) {
			String actualHeading = newCustomerPage.getAddNewCustomerHeading();
			String expectedHeading = newCustomerPage.getExpectedCustomerHeading();
			String message = String.format("Actual heading is : %s and expected is : %s", actualHeading,
					expectedHeading);
			softAssert.assertEquals(actualHeading, expectedHeading, message);
		} else {
			Assert.assertTrue(isLoginSuccessfull, "Login is not successfull");
		}

	}

	@Test(testName = "Verify New Customer Lables", description = "Verify lables on new customer page", groups = { "all",
			"sanity", "newcustomerpage" }, priority = 11)
	protected void test_new_customer_page_fields_lables() {
		if (isLoginSuccessfull) {
			List<String> actualLabel = newCustomerPage.getFieldsLabelsText();
			List<String> expectedLable = newCustomerPage.getExpectedLabelMessages();
			String message = String.format("Actual fields label are : %s and expected fields labels are : %s",
					actualLabel, expectedLable);
			softAssert.assertEquals(actualLabel, expectedLable, message);
		} else {
			Assert.assertTrue(isLoginSuccessfull, "Login is not successfull");
		}
	}

	@Test(testName = "Verify customer name field", description = "Verify customer label fields validations", groups = {
			"all", "smoke", "newcustomerpage" }, priority = 12)
	protected void test_customer_field_validations() {
		if (isLoginSuccessfull) {
			List<String> actualLabel = newCustomerPage.getCustomerNameValidationMessages();
			List<String> expectedLable = newCustomerPage.getCustomerNameExpectedMessages();
			String message = String.format("Actual messages are : %s and expected messages are : %s", actualLabel,
					expectedLable);
			Assert.assertEquals(actualLabel, expectedLable, message);
		} else {
			Assert.assertTrue(isLoginSuccessfull, "Login is not successfull");
		}
	}

	@Test(testName = "Verify dob field", description = "Verify dob field validations", groups = { "all", "sanity",
			"newcustomerpage" }, priority = 13)
	protected void test_dob_field_validations() {
		if (isLoginSuccessfull) {
			List<String> actualLabel = newCustomerPage.getDateOfBirthValidationMessages();
			List<String> expectedLable = newCustomerPage.getDateOfBirthExpectedMessages();
			String message = String.format("Actual messages are : %s and expected messages are : %s", actualLabel,
					expectedLable);
			Assert.assertEquals(actualLabel, expectedLable, message);
		} else {
			Assert.assertTrue(isLoginSuccessfull, "Login is not successfull");
		}
	}

	@Test(testName = "Verify address field", description = "Verify address field validations", groups = { "all",
			"smoke", "newcustomerpage" }, priority = 14)
	protected void test_address_field_validations() {
		if (isLoginSuccessfull) {
			List<String> actualLabel = newCustomerPage.getAddressValidationMessages();
			List<String> expectedLable = newCustomerPage.getAddressFieldExpectedMessages();
			String message = String.format("Actual messages are : %s and expected messages are : %s", actualLabel,
					expectedLable);
			Assert.assertEquals(actualLabel, expectedLable, message);
		} else {
			Assert.assertTrue(isLoginSuccessfull, "Login is not successfull");
		}
	}

	@Test(testName = "Verify city field", description = "Verify city field validations", groups = { "all", "sanity",
			"newcustomerpage" }, priority = 15)
	protected void test_city_field_validations() {
		if (isLoginSuccessfull) {
			List<String> actualLabel = newCustomerPage.getCityFieldValidationMessages();
			List<String> expectedLable = newCustomerPage.getCityFieldExpectedMessages();
			String message = String.format("Actual messages are : %s and expected messages are : %s", actualLabel,
					expectedLable);
			Assert.assertEquals(actualLabel, expectedLable, message);
		} else {
			Assert.assertTrue(isLoginSuccessfull, "Login is not successfull");
		}
	}

	@Test(testName = "Verify state field", description = "Verify state field validations", groups = { "all", "smoke",
			"newcustomerpage" }, priority = 16)
	protected void test_state_field_validations() {
		if (isLoginSuccessfull) {
			List<String> actualLabel = newCustomerPage.getStateValidationMessages();
			List<String> expectedLable = newCustomerPage.getStateFieldExpectedMessages();
			String message = String.format("Actual messages are : %s and expected messages are : %s", actualLabel,
					expectedLable);
			Assert.assertEquals(actualLabel, expectedLable, message);
		} else {
			Assert.assertTrue(isLoginSuccessfull, "Login is not successfull");
		}
	}

	@Test(testName = "Verify pin field", description = "Verify pin field validations", groups = { "all", "sanity",
			"newcustomerpage" }, priority = 17)
	protected void test_pin_field_validations() {
		if (isLoginSuccessfull) {
			List<String> actualLabel = newCustomerPage.getPincodeValidationMessages();
			List<String> expectedLable = newCustomerPage.getPinCodeFieldExpectedMessages();
			String message = String.format("Actual messages are : %s and expected messages are : %s", actualLabel,
					expectedLable);
			Assert.assertEquals(actualLabel, expectedLable, message);
		} else {
			Assert.assertTrue(isLoginSuccessfull, "Login is not successfull");
		}
	}

	@Test(testName = "Verify mobile field", description = "Verify mobile field validations", groups = { "all", "smoke",
			"newcustomerpage" }, priority = 18)
	protected void test_mobiles_field_validations() {
		if (isLoginSuccessfull) {
			List<String> actualLabel = newCustomerPage.getMobileValidationMessages();
			List<String> expectedLable = newCustomerPage.getMobileFieldExpectedMessages();
			String message = String.format("Actual messages are : %s and expected messages are : %s", actualLabel,
					expectedLable);
			Assert.assertEquals(actualLabel, expectedLable, message);
		} else {
			Assert.assertTrue(isLoginSuccessfull, "Login is not successfull");
		}
	}

	@Test(testName = "Verify password field", description = "Verify password field validations", groups = { "all",
			"sanity", "newcustomerpage" }, priority = 19)
	protected void test_password_field_validations() {
		if (isLoginSuccessfull) {
			List<String> actualLabel = newCustomerPage.getPasswordValidationMessages();
			List<String> expectedLable = newCustomerPage.getPasswordFieldExpectedMessages();
			String message = String.format("Actual messages are : %s and expected messages are : %s", actualLabel,
					expectedLable);
			Assert.assertEquals(actualLabel, expectedLable, message);
		} else {
			Assert.assertTrue(isLoginSuccessfull, "Login is not successfull");
		}
	}

	@Test(testName = "Verify email field", description = "Verify email field validations", groups = { "all", "smoke",
			"newcustomerpage" }, priority = 20)
	protected void test_email_field_validations() {
		if (isLoginSuccessfull) {
			List<String> actualLabel = newCustomerPage.getEmailValidationMessages();
			List<String> expectedLable = newCustomerPage.getEmailFieldExpectedMessages();
			String message = String.format("Actual messages are : %s and expected messages are : %s", actualLabel,
					expectedLable);
			Assert.assertEquals(actualLabel, expectedLable, message);
		} else {
			Assert.assertTrue(isLoginSuccessfull, "Login is not successfull");
		}
	}

	@Test(testName = "Verify customer name field with data provider", description = "Verify customer label fields validations with data provider", groups = {
			"all", "smoke", "newcustomerpage" }, priority = 21, dataProvider = "customerField")
	protected void test_customer_field_validations_with_dataprovider(Hashtable<String, String> dataTable) {
		if (isLoginSuccessfull) {
			String actualLabel = newCustomerPage.getCustomerNameValidationMessages(dataTable.get("input"));
			String expectedLable = dataTable.get("expectedMessage");
			String message = String.format("Actual messages are : %s and expected messages are : %s", actualLabel,
					expectedLable);
			Assert.assertEquals(actualLabel, expectedLable, message);
		} else {
			Assert.assertTrue(isLoginSuccessfull, "Login is not successfull");
		}
	}

	@DataProvider(name = "customerField")
	public Object[][] customerFieldDataProvider() {
		return newCustomerPage.getCustomerFieldDataProvider();
	}
}
