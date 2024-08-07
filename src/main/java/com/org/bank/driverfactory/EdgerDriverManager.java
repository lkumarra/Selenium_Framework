package com.org.bank.driverfactory;

import org.openqa.selenium.edge.EdgeDriver;

/**
 * This class contains the method related to edge driver 
 * @author Lavendra Kumar Rajput
 *
 * @Date 09/02/2023
 */
public final class EdgerDriverManager implements DriverManager{

	/**
	 * This method will return the instance of Edge Driver Class
	 */
	@Override
	public EdgeDriver getWebDriver() {
		return new EdgeDriver();
	}

}
