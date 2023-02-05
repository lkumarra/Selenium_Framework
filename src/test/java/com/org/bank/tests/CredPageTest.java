package com.org.bank.tests;

import org.testng.annotations.Test;

import com.org.bank.pages.CredPage;

public class CredPageTest extends BasePageTest {

	private CredPage credPage;

	private CredPageTest() {
		this.credPage = new CredPage();
	}

	@Test(description = "Verifying the credentials from DB and updating if outdated", priority = 0, groups = {
			"Credentials" })
	public void test_credentials_copy() {
		credPage.enterEmailIdAndSubmit().getAndUpdateCredInDb().navigateBack();
	}

}
