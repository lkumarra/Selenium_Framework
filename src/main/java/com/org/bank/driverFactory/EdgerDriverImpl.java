package com.org.bank.driverFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class EdgerDriverImpl implements Driver{

	@Override
	public WebDriver getDriver() {
		return new EdgeDriver();
	}

}
