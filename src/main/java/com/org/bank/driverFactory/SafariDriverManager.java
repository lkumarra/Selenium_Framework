package com.org.bank.driverFactory;

import org.openqa.selenium.safari.SafariDriver;

/**
 * This class contains the methods related to safari driver
 * 
 * @author Lavendra Kumar Rajput
 *
 * @Date 09/02/2023
 */
public class SafariDriverManager implements DriverManager {

	/**
	 * This method will return the instance of SafariDriver class
	 */
	@Override
	public SafariDriver getWebDriver() {
		return new SafariDriver();
	}

}
