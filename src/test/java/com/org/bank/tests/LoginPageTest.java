package com.org.bank.tests;

import com.org.bank.listeners.CustomListeners;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.org.bank.constants.WebDriverContext;
import com.org.bank.driverfactory.DriverFactory;
import com.org.bank.pages.BasePage;
import com.org.bank.pages.CredPage;
import com.org.bank.pages.LoginPage;

@Listeners(CustomListeners.class)
public class LoginPageTest {

    private LoginPage loginPage;
    private BasePage basePage;
    public DriverFactory driverFactory;
    private SoftAssert softAssert;

    @BeforeClass(alwaysRun = true)
    protected void initialization() {
        driverFactory = DriverFactory.newDriverFactory();
        WebDriverContext.setWebDriverContext(LoginPageTest.class.getName(), driverFactory.getWebDriver());
        basePage = BasePage.newBasePage(driverFactory.getWebDriver());
        loginPage = LoginPage.newLoginPage(driverFactory.getWebDriver());
        CredPage credPage = CredPage.newCredPage(driverFactory.getWebDriver());
        softAssert = new SoftAssert();
        basePage.initialization();
        credPage.enterEmailIdAndSubmit().getAndUpdateCredInDb().navigateToLoginPage();
    }

    @AfterClass(alwaysRun = true)
    protected void tearDown() {
        basePage.tearDown();
        softAssert.assertAll();
    }

    @Test(testName = "Verify Login Page Title", description = "Verify the title of login page", priority = 1, groups = {
            "@login", "@all", "@smoke"})
    private void test_verify_title_on_login_page() {
        Assert.assertEquals(
                loginPage.getTitleOfLoginPage().trim(),
                loginPage.getLoginPageLabelsText().getTitle().trim(),
                String.format(
                        "Actual Title is : %s but expected is : %s",
                        loginPage.getTitleOfLoginPage().trim(),
                        loginPage.getLoginPageLabelsText().getTitle().trim()
                )
        );
    }

    @Test(testName = "Verify UserId Label", description = "Verify the userId label on login page", priority = 2, groups = {
            "@login", "@sanity", "@all"}, dependsOnMethods = {"test_verify_title_on_login_page"})
    private void test_verify_userId_label() {
        softAssert.assertEquals(
                loginPage.getUserIDLabelText().trim(),
                loginPage.getLoginPageLabelsText().getUsedidlabel().trim(),
                String.format(
                        "Actual label is : %s and expected is : %s",
                        loginPage.getUserIDLabelText().trim(),
                        loginPage.getLoginPageLabelsText().getUsedidlabel().trim()
                )
        );
    }

    @Test(testName = "Verify Password Label", description = "Verify password label text on login page", priority = 3, groups = {
            "@login", "@all", "@smoke"}, dependsOnMethods = {"test_verify_title_on_login_page"})
    private void test_verify_password_test() {
        softAssert.assertEquals(
                loginPage.getPasswordLabelText(),
                loginPage.getLoginPageLabelsText().getPasswordlabel(),
                String.format(
                        "Actual label is : %s and expected is : %s",
                        loginPage.getPasswordLabelText(),
                        loginPage.getLoginPageLabelsText().getPasswordlabel()
                )
        );
    }

    @Test(testName = "Verify Submit Button Label", description = "Verify submit button text on login page", priority = 4, groups = {
            "@login", "@all", "@sanity"}, dependsOnMethods = {"test_verify_title_on_login_page"})
    private void test_verify_submit_button_label() {
        softAssert.assertEquals(
                loginPage.getLoginButtonText(),
                loginPage.getLoginPageLabelsText().getSubmitbuttonlabel(),
                String.format(
                        "Actual label is : %s and expected is : %s",
                        loginPage.getLoginButtonText(),
                        loginPage.getLoginPageLabelsText().getSubmitbuttonlabel()
                )
        );
    }

    @Test(testName = "Verify Reset Button Label", description = "Verify reset button text on login page", priority = 5, groups = {
            "@login", "@all", "@smoke"}, dependsOnMethods = {"test_verify_title_on_login_page"})
    private void test_verify_reset_button_label() {
        softAssert.assertEquals(
                loginPage.getResetButtonText(),
                loginPage.getLoginPageLabelsText().getResetbuttonlabel(),
                String.format(
                        "Actual label is : %s and expected is : %s",
                        loginPage.getResetButtonText(),
                        loginPage.getLoginPageLabelsText().getResetbuttonlabel()
                )
        );
    }

    @Test(testName = "Verify Login Functionality", description = "Verify error alert message by entering invalid credentials", priority = 6, groups = {
            "@login", "@smoke", "@all"}, dependsOnMethods = {"test_verify_title_on_login_page"})
    private void test_verify_invalid_cred_error_message() {
        softAssert.assertEquals(
                loginPage.getErrorAlertMessagesForInvalidCredentials(),
                loginPage.getExpectedErrorMessages()
        );
    }

}
