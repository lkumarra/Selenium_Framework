package com.org.bank.driverFactory;

import org.openqa.selenium.chrome.ChromeDriver;

/**
 * This class contains the method related ot chrome class
 * @author Lavendra Kumar Rajput
 *
 * @Date 09/02/2023
 */
public class ChromerDriverManager implements DriverManager {

	/**
	 * This method will return the instance of chrome driver class
	 */
	@Override
	public ChromeDriver getWebDriver() {
		return new ChromeDriver();
	}
	
}
