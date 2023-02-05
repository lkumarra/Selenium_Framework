package com.org.bank.driverFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeDriverImpl implements Driver {

	@Override
	public WebDriver getDriver() {
		return new ChromeDriver();
	}

}
