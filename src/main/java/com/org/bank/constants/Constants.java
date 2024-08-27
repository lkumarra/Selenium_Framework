package com.org.bank.constants;

public final class Constants {

    private Constants() {
    }

    private static final String CURRENT_USER_DIR = System.getProperty("user.dir");
    public static final String CONFIG_FILE_PATH = String.format("%s/src/main/resources/Configuration.properties",
            CURRENT_USER_DIR);
    public static final String EXCEL_FILE_PATH = String.format("%s/src/test/resources/testData/Guru99BankTestData.xlsx",
            CURRENT_USER_DIR);
    public static final String TEXT_FILE_PATH = String.format("%s/src/main/resources/Guru99Framework.txt",
            CURRENT_USER_DIR);
    public static final String REPORT_PATH = String.format("%s/src/test/resources/executionArtifacts/reports",
            CURRENT_USER_DIR);
    public static final String SCREEN_SHOT_DIR = String.format("%s/src/test/resources/executionArtifacts/screenshots",
            CURRENT_USER_DIR);
    public static final String RETRY_FILE_PATH = String.format("%s/src/test/resources/retry-tests.txt", CURRENT_USER_DIR);
}
