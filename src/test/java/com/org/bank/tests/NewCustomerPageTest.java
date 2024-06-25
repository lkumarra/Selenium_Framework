package com.org.bank.tests;

import java.util.Hashtable;
import java.util.List;

import com.org.bank.constants.CredModalContext;
import com.org.bank.modals.CredModal;
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

    private BasePage basePage;
    private NewCustomerPage newCustomerPage;
    private SoftAssert softAssert;
    private boolean isLoginSuccessful;

    @BeforeClass(alwaysRun = true)
    protected void initialization() {
        DriverFactory driverFactory = DriverFactory.newDriverFactory();
        WebDriverContext.setWebDriverContext(ManagerPageTest.class.getName(), driverFactory.getWebDriver());
        basePage = BasePage.newBasePage(driverFactory.getWebDriver());
        LoginPage loginPage = LoginPage.newLoginPage(driverFactory.getWebDriver());
        CredPage credPage = CredPage.newCredPage(driverFactory.getWebDriver());
        ManagerPage managerPage = ManagerPage.newManagerPage(driverFactory.getWebDriver());
        newCustomerPage = NewCustomerPage.newCustomerPage(driverFactory.getWebDriver());
        softAssert = new SoftAssert();
        basePage.initialization();
        credPage.enterEmailIdAndSubmit().getAndUpdateCredInDb().navigateToLoginPage();
        CredModal credModal = CredModalContext.getCredModal();
        isLoginSuccessful = loginPage.isLoginSuccessful(credModal.getUserName(), credModal.getPassword());
        if (isLoginSuccessful) {
            managerPage.clickOnNewCustomerPage();
        }
    }

    @AfterClass(alwaysRun = true)
    protected void tearDown() {
        basePage.tearDown();
        softAssert.assertAll();
    }

    @Test(testName = "Verify new customer heading", description = "Verify Add new customer heading", groups = {"@all",
            "@smoke", "@newCustomer"}, priority = 10)
    protected void test_add_customer_heading_message() {
        if (!isLoginSuccessful) {
            Assert.fail("Login is not successfully");
            return;
        }
        String actualHeading = newCustomerPage.getAddNewCustomerHeading();
        String expectedHeading = newCustomerPage.getExpectedCustomerHeading();
        softAssert.assertEquals(actualHeading, expectedHeading,
                String.format("Actual heading is : %s and expected is : %s", actualHeading, expectedHeading));
    }

    @Test(testName = "Verify new customer label's", description = "Verify label's on new customer page", groups = {"@all",
            "@sanity", "@newCustomer"}, priority = 11)
    protected void test_new_customer_page_fields_labels() {
        if (!isLoginSuccessful) {
            Assert.fail("Login is not successful");
            return;
        }

        List<String> actualLabel = newCustomerPage.getFieldsLabelsText();
        List<String> expectedLabel = newCustomerPage.getExpectedLabelMessages();
        softAssert.assertEquals(actualLabel, expectedLabel,
                String.format("Actual fields label are : %s and expected fields labels are : %s", actualLabel, expectedLabel));
    }

    @Test(testName = "Verify customer name field", description = "Verify customer label fields validations", groups = {
            "@all", "@smoke", "@newCustomer"}, priority = 12)
    protected void test_customer_field_validations() {
        if (!isLoginSuccessful) {
            Assert.fail("Login is not successful");
            return;
        }

        List<String> actualLabel = newCustomerPage.getCustomerNameValidationMessages();
        List<String> expectedLabel = newCustomerPage.getCustomerNameExpectedMessages();
        Assert.assertEquals(actualLabel, expectedLabel,
                String.format("Actual messages are : %s and expected messages are : %s", actualLabel, expectedLabel));
    }

    @Test(testName = "Verify dob field", description = "Verify dob field validations", groups = {"@all", "@sanity",
            "@newCustomer"}, priority = 13)
    protected void test_dob_field_validations() {
        if (!isLoginSuccessful) {
            Assert.fail("Login is not successful");
            return;
        }

        List<String> actualLabel = newCustomerPage.getDateOfBirthValidationMessages();
        List<String> expectedLabel = newCustomerPage.getDateOfBirthExpectedMessages();
        Assert.assertEquals(actualLabel, expectedLabel,
                String.format("Actual messages are : %s and expected messages are : %s", actualLabel, expectedLabel));
    }

    @Test(testName = "Verify address field", description = "Verify address field validations", groups = {"@all",
            "@smoke", "@newCustomer"}, priority = 14)
    protected void test_address_field_validations() {
        if (!isLoginSuccessful) {
            Assert.fail("Login is not successful");
            return;
        }

        List<String> actualLabel = newCustomerPage.getAddressValidationMessages();
        List<String> expectedLabel = newCustomerPage.getAddressFieldExpectedMessages();
        Assert.assertEquals(actualLabel, expectedLabel,
                String.format("Actual messages are : %s and expected messages are : %s", actualLabel, expectedLabel));
    }

    @Test(testName = "Verify city field", description = "Verify city field validations", groups = {"@all", "@sanity",
            "@newCustomer"}, priority = 15)
    protected void test_city_field_validations() {
        if (!isLoginSuccessful) {
            Assert.fail("Login is not successful");
            return;
        }

        List<String> actualLabel = newCustomerPage.getCityFieldValidationMessages();
        List<String> expectedLabel = newCustomerPage.getCityFieldExpectedMessages();
        Assert.assertEquals(actualLabel, expectedLabel,
                String.format("Actual messages are : %s and expected messages are : %s", actualLabel, expectedLabel));
    }

    @Test(testName = "Verify state field", description = "Verify state field validations", groups = {"@all", "@smoke",
            "@newCustomer"}, priority = 16)
    protected void test_state_field_validations() {
        if (!isLoginSuccessful) {
            Assert.fail("Login is not successful");
            return;
        }

        List<String> actualLabel = newCustomerPage.getStateValidationMessages();
        List<String> expectedLabel = newCustomerPage.getStateFieldExpectedMessages();
        Assert.assertEquals(actualLabel, expectedLabel,
                String.format("Actual messages are : %s and expected messages are : %s", actualLabel, expectedLabel));
    }

    @Test(testName = "Verify pin field", description = "Verify pin field validations", groups = {"@all", "@sanity",
            "@newCustomer"}, priority = 17)
    protected void test_pin_field_validations() {
        if (!isLoginSuccessful) {
            Assert.fail("Login is not successful");
            return;
        }

        List<String> actualLabel = newCustomerPage.getPinCodeValidationMessages();
        List<String> expectedLabel = newCustomerPage.getPinCodeFieldExpectedMessages();
        Assert.assertEquals(actualLabel, expectedLabel,
                String.format("Actual messages are : %s and expected messages are : %s", actualLabel, expectedLabel));
    }

    @Test(testName = "Verify mobile field", description = "Verify mobile field validations", groups = {"@all", "@smoke",
            "@newCustomer"}, priority = 18)
    protected void test_mobiles_field_validations() {
        if (!isLoginSuccessful) {
            Assert.fail("Login is not successful");
            return;
        }

        List<String> actualLabel = newCustomerPage.getMobileValidationMessages();
        List<String> expectedLabel = newCustomerPage.getMobileFieldExpectedMessages();
        Assert.assertEquals(actualLabel, expectedLabel,
                String.format("Actual messages are : %s and expected messages are : %s", actualLabel, expectedLabel));
    }

    @Test(testName = "Verify password field", description = "Verify password field validations", groups = {"@all",
            "@sanity", "@newCustomer"}, priority = 19)
    protected void test_password_field_validations() {
        if (!isLoginSuccessful) {
            Assert.fail("Login is not successful");
            return;
        }

        List<String> actualLabel = newCustomerPage.getPasswordValidationMessages();
        List<String> expectedLabel = newCustomerPage.getPasswordFieldExpectedMessages();
        Assert.assertEquals(actualLabel, expectedLabel,
                String.format("Actual messages are : %s and expected messages are : %s", actualLabel, expectedLabel));
    }

    @Test(testName = "Verify email field", description = "Verify email field validations", groups = {"@all", "@smoke",
            "@newCustomer"}, priority = 20)
    protected void test_email_field_validations() {
        if (!isLoginSuccessful) {
            Assert.fail("Login is not successful");
            return;
        }

        List<String> actualLabel = newCustomerPage.getEmailValidationMessages();
        List<String> expectedLabel = newCustomerPage.getEmailFieldExpectedMessages();
        Assert.assertEquals(actualLabel, expectedLabel,
                String.format("Actual messages are : %s and expected messages are : %s", actualLabel, expectedLabel));
    }

    @Test(testName = "Verify customer name field with data provider", description = "Verify customer label fields validations with data provider", groups = {
            "@all", "@smoke", "@newCustomer"}, priority = 21, dataProvider = "customerField")
    protected void test_customer_field_validations_with_data_provider(Hashtable<String, String> dataTable) {
        if (!isLoginSuccessful) {
            Assert.fail("Login is not successful");
            return;
        }

        String actualLabel = newCustomerPage.getCustomerNameValidationMessages(dataTable.get("input"));
        String expectedLabel = dataTable.get("expectedMessage");
        Assert.assertEquals(actualLabel, expectedLabel,
                String.format("Actual messages are : %s and expected messages are : %s", actualLabel, expectedLabel));
    }

    @DataProvider(name = "customerField")
    public Object[][] customerFieldDataProvider() {
        return newCustomerPage.getCustomerFieldDataProvider();
    }
}
