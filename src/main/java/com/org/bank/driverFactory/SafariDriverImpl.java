package com.org.bank.driverFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class SafariDriverImpl implements Driver {

	@Override
	public WebDriver getDriver() {
		return new SafariDriver();
	}

}
