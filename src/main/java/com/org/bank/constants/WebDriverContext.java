package com.org.bank.constants;

import java.util.Hashtable;

import org.openqa.selenium.WebDriver;

public class WebDriverContext {

	private static final Hashtable<String, WebDriver> webDriverContext = new Hashtable<String, WebDriver>();

	public static void setWebDriverContext(String className, WebDriver driver) {
		webDriverContext.put(className, driver);
	}

	public static WebDriver getWebDriverContext(String className) {
		return webDriverContext.get(className);
	}
}
