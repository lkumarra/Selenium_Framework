package com.org.bank.driverFactory;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * This class contains the method related ot chrome class
 * @author Lavendra Kumar Rajput
 *
 * @Date 09/02/2023
 */
public final class ChromerDriverManager implements DriverManager {

	/**
	 * This method will return the instance of chrome driver class
	 */
	@Override
	public ChromeDriver getWebDriver() {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--remote-allow-origins=*");
		return new ChromeDriver(chromeOptions);
	}
	
}
