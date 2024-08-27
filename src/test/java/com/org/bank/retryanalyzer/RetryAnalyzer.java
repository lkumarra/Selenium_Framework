package com.org.bank.retryanalyzer;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    public int totalRetry = 0;
    public static int maxRetry = 3;

    @Override
    public boolean retry(ITestResult iTestResult) {
        while (totalRetry < maxRetry) {
            totalRetry++;
            System.out.println("Retrying : " + totalRetry);
            return true;
        }
        return false;
    }
}
