package com.org.bank.driverfactory;

import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * This class contains the method related to Firefox driver class
 * @author Lavendra Kumar Rajput
 *
 * @Date 09/02/2023
 */
public final class FirefoxDriverManager implements DriverManager {

	/**
	 * This method will return the instance of FirefoxDriver class
	 */
	@Override
	public FirefoxDriver getWebDriver() {
		return new FirefoxDriver();
	}

}
