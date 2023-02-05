package com.org.bank.constants;

public final class Constants {

	private final static String CurrentUserDirectory = System.getProperty("user.dir");
	public final static String ConfigurationFile = String.format("%s/src/main/resources/Configuration.properties",
			CurrentUserDirectory);
	public final static String ExcelFile = String.format("%s\\src\\test\\resources\\testData\\Guru99BankTestData.xlsx", CurrentUserDirectory);
}
