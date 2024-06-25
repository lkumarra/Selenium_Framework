package com.org.bank.constants;

import java.util.Hashtable;

import org.openqa.selenium.WebDriver;

public final class WebDriverContext {

    private static final Hashtable<String, WebDriver> webDriverContext = new Hashtable<String, WebDriver>();

    /**
     * This static method is used to set the WebDriver context for a specific class.
     * It takes the class name and a WebDriver instance as parameters.
     * The method stores the WebDriver instance in a Hashtable with the class name as the key.
     * This allows for different WebDriver instances to be associated with different classes.
     *
     * @param className The name of the class for which the WebDriver context is to be set.
     * @param driver    The WebDriver instance to be set for the class.
     */
    public static void setWebDriverContext(String className, WebDriver driver) {
        webDriverContext.put(className, driver);
    }

    /**
     * This static method is used to get the WebDriver context for a specific class.
     * It takes the class name as a parameter.
     * The method retrieves the WebDriver instance from a Hashtable using the class name as the key.
     * This allows for different WebDriver instances to be retrieved for different classes.
     *
     * @param className The name of the class for which the WebDriver context is to be retrieved.
     * @return The WebDriver instance associated with the class.
     */
    public static WebDriver getWebDriverContext(String className) {
        return webDriverContext.get(className);
    }
}
