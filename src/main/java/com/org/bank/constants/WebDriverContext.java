package com.org.bank.constants;

import java.util.Hashtable;

import org.openqa.selenium.WebDriver;

public class WebDriverContext {

	private static final Hashtable<String, WebDriver> webdriverContext = new Hashtable<String, WebDriver>();

	public static void setWebDriverContext(String className, WebDriver driver) {
		webdriverContext.put(className, driver);
	}

	public static WebDriver getWebDriverContext(String className) {
		return webdriverContext.get(className);
	}
}
