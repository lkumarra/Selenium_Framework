package com.org.bank.tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.org.bank.constants.WebDriverContext;
import com.org.bank.driverFactory.DriverFactory;
import com.org.bank.listeners.CustomListeners;
import com.org.bank.pages.BasePage;
import com.org.bank.pages.CredPage;

@Listeners(CustomListeners.class)
public class CredPageTest {

	private BasePage basePage;
	private CredPage credPage;
	public DriverFactory driverFactory;

	@BeforeClass(alwaysRun = true)
	protected void initialization() {
		driverFactory = DriverFactory.newDriverFactory();
		WebDriverContext.setWebDriverContext(CredPageTest.class.getName(), driverFactory.getWebDriver());
		basePage = BasePage.newBasePage(driverFactory.getWebDriver());
		credPage = CredPage.newCredPage(driverFactory.getWebDriver());
		basePage.initialization();
	}
	
	@Test(testName = "Verify Latest Credentials", description = "Verifying the credentials from DB and updating if outdated", priority = 0, groups = {
			"Credentials", "all", "sanity" })
	public void test_credentials_copy() {
		credPage.enterEmailIdAndSubmit().getAndUpdateCredInDb().navigateBack();
	}

	@AfterClass(alwaysRun = true)
	protected void tearDown() {
		basePage.tearDown();
	}

}
