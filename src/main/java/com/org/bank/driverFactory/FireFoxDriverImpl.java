package com.org.bank.driverFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FireFoxDriverImpl implements Driver {

	@Override
	public WebDriver getDriver() {
		return new FirefoxDriver();
	}

}
