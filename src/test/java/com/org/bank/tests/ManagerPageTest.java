package com.org.bank.tests;

import java.util.List;

import com.epam.reportportal.testng.ReportPortalTestNGListener;
import com.org.bank.constants.CredModalContext;
import com.org.bank.listeners.CustomListeners;
import com.org.bank.models.CredModal;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import com.org.bank.constants.WebDriverContext;
import com.org.bank.driverfactory.DriverFactory;
import com.org.bank.pages.BasePage;
import com.org.bank.pages.CredPage;
import com.org.bank.pages.LoginPage;
import com.org.bank.pages.ManagerPage;

@Listeners({CustomListeners.class, ReportPortalTestNGListener.class})
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
            "@all", "@smoke", "@manager"}, priority = 7)
    protected void test_verify_Welcome_Message() {
        if (!isLoginSuccessful) {
            Assert.fail("Login is not successful so can not navigate to manager page");
            return;
        }
        String actualMessage = managerPage.getWelcomeMessageText();
        String expectedMessage = managerPage.getWelcomeMessage();
        softAssert.assertEquals(actualMessage, expectedMessage,
                String.format("Actual welcome message is : %s and expected welcome message is : %s", actualMessage, expectedMessage));
    }

    @Test(testName = "Verify managerId message", description = "Verify manager id message after successful login", groups = {
            "@all", "@sanity", "@manager"}, priority = 8)
    protected void test_verify_ManagerId_Message() {
        if (!isLoginSuccessful) {
            Assert.fail("Login is not successful so can not navigate to manager page");
            return;
        }
        String actualMessage = managerPage.getManagerIdText();
        String expectedMessage = managerPage.getManagerIdMessage(userName);
        softAssert.assertEquals(actualMessage, expectedMessage,
                String.format("Actual managerId is : %s and expected welcome message is : %s", actualMessage, expectedMessage));
    }

    @Test(testName = "Verify menu options", description = "Verify menu options on manager page", groups = {"@all",
            "@smoke", "@manager"}, priority = 9)
    protected void test_verify_menu_options() {
        if (!isLoginSuccessful) {
            Assert.fail("Login is not successful so can not navigate to manager page");
            return;
        }
        List<String> actualMenuList = managerPage.getAllMenuText();
        List<String> expectedMenuOptions = managerPage.getAllMenuOptions();
        softAssert.assertEquals(actualMenuList, expectedMenuOptions,
                String.format("Actual menu options are : %s and expected menu options are : %s", actualMenuList, expectedMenuOptions));
    }

}
