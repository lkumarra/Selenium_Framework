package com.org.bank.tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.org.bank.constants.WebDriverContext;
import com.org.bank.driverFactory.DriverFactory;
import com.org.bank.listners.Listners;
import com.org.bank.pages.BasePage;
import com.org.bank.pages.CredPage;

@Listeners(Listners.class)
public class CredPageTest extends BasePageTest {

	private BasePage basePage;
	private CredPage credPage;
	public DriverFactory driverFactory;

	@BeforeClass(alwaysRun = true)
	protected void initialization() {
		driverFactory = new DriverFactory();
		WebDriverContext.setWebDriverContext(CredPageTest.class.getName(), driverFactory.getWebDriver());
		basePage = new BasePage(driverFactory.getWebDriver());
		credPage = new CredPage(driverFactory.getWebDriver());
		basePage.initialization();
	}

	@Test(testName = "Verify Latest Credentials", description = "Verifying the credentials from DB and updating if outdated", priority = 0, groups = {
			"Credentials", "all", "sanity" })
	public void test_credentials_copy() {
		credPage.enterEmailIdAndSubmit().getAndUpdateCredInDb().navigateBack();
	}

	@AfterClass(alwaysRun = true)
	protected void tearDown() {
		basePage.teadDown();
	}

}
