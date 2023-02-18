package com.org.bank.constants;

public final class Constants {

	private final static String CurrentUserDirectory = System.getProperty("user.dir");
	public final static String ConfigurationFile = String.format("%s/src/main/resources/Configuration.properties",
			CurrentUserDirectory);
	public final static String ExcelFile = String.format("%s/src/test/resources/testData/Guru99BankTestData.xlsx",
			CurrentUserDirectory);
	public final static String TextFile = String.format("%s/src/main/resources/Guru99Framework.txt",
			CurrentUserDirectory);
	public final static String ReportPath = String.format("%s/src/test/resources/executionArtifacts/reports",
			CurrentUserDirectory);
	public final static String ScreenShotDirectory = String.format("%s/src/test/resources/executionArtifacts/screenshots",
			CurrentUserDirectory);
}
