package com.org.bank.driverFactory;

import java.util.Objects;

import org.openqa.selenium.WebDriver;

public final class DriverManager extends DriverFactory {

	private static ThreadLocal<WebDriver> threadLocal = new ThreadLocal<WebDriver>();

	private static DriverManager driverManager;

	private DriverManager() {
		super();
		threadLocal.set(driver.getDriver());
	}

	public static WebDriver getWebDriver() {
		if (Objects.isNull(driverManager)) {
			driverManager = new DriverManager();
		}
		return threadLocal.get();
	}

}
