package com.org.bank.utils;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This Class contains the method related to selenium utility
 *
 * @author Lavendra Kumar Rajput
 * @Date 08/02/2023
 */
@Slf4j
public final class SeleniumUtils {

    private int defaultTime = 30;

    private final WebDriver driver;

    private SeleniumUtils(WebDriver driver) {
        this.driver = driver;
    }

    private SeleniumUtils(WebDriver driver, int waitTimeInSecond) {
        this.driver = driver;
        defaultTime = waitTimeInSecond;
    }

    public static SeleniumUtils newSeleniumUtils(WebDriver driver) {
        return new SeleniumUtils(driver);
    }

    public static SeleniumUtils newSeleniumUtils(WebDriver driver, int waitTimeInSecond) {
        return new SeleniumUtils(driver, waitTimeInSecond);
    }

    /**
     * Creates a new WebDriverWait instance with a specified wait time.
     * <p>
     * This method creates a new WebDriverWait instance that will wait for a maximum of the specified number of seconds
     * before throwing a TimeoutException. The WebDriverWait instance is associated with the WebDriver instance of this class.
     *
     * @param waitTimeInSecond The maximum time to wait in seconds.
     * @return A new WebDriverWait instance.
     */
    private WebDriverWait getWebdriverWait(int waitTimeInSecond) {
        return new WebDriverWait(driver, Duration.ofSeconds(waitTimeInSecond));
    }

    /**
     * This method creates and returns an instance of the Actions class.
     * The Actions class in Selenium is used for performing complex user gestures including right clicking, double clicking, dragging and dropping etc.
     * The instance is created using the WebDriver instance of this class.
     *
     * @return An instance of the {@link Actions} class.
     */
    private Actions getActions() {
        return new Actions(driver);
    }

    /**
     * Extracts the locator from a WebElement.
     * <p>
     * This method takes a WebElement as an argument, converts it to a string, and then splits it based on "->".
     * It then takes the second part of the split string (index 1), and removes the last character "]" from it.
     * The resulting string is the locator of the WebElement.
     *
     * @param element The WebElement from which to extract the locator.
     * @return The locator of the WebElement as a String.
     */
    private String getLocatorFromWebElement(WebElement element) {
        return element.toString().split("->")[1].replaceFirst("(?s)(.*)\\]", "$1" + "");
    }


    /**
     * Waits for a WebElement to become visible.
     * <p>
     * This method uses WebDriverWait to wait for the specified WebElement to become visible on the page.
     * The wait time is determined by the defaultTime variable. If the element does not become visible within this time,
     * a TimeoutException will be thrown.
     *
     * @param element The WebElement to wait for.
     */
    public void waitForElementVisibility(WebElement element) {
        getWebdriverWait(defaultTime).until(ExpectedConditions.visibilityOf(element));
        log.info("Waiting for element to be visible for : {} seconds", defaultTime);
    }

    /**
     * Waits for a WebElement to become visible for a specified amount of time.
     * <p>
     * This method uses WebDriverWait to wait for the specified WebElement to become visible on the page.
     * The wait time is determined by the time parameter. If the element does not become visible within this time,
     * a TimeoutException will be thrown.
     *
     * @param element The WebElement to wait for.
     * @param time    The maximum time to wait in seconds.
     */
    public void waitForElementVisibility(WebElement element, int time) {
        getWebdriverWait(time).until(ExpectedConditions.visibilityOf(element));
        log.info("Waiting for element to be visible for : {} seconds", time);
    }

    /**
     * Waits for a WebElement to become clickable.
     * <p>
     * This method uses WebDriverWait to wait for the specified WebElement to become clickable on the page.
     * The wait time is determined by the defaultTime variable. If the element does not become clickable within this time,
     * a TimeoutException will be thrown.
     *
     * @param element The WebElement to wait for.
     */
    public void waitForElementToBeClickable(WebElement element) {
        getWebdriverWait(defaultTime).until(ExpectedConditions.elementToBeClickable(element));
        log.info("Waiting for element to be clickable for : {} seconds", defaultTime);
    }

    /**
     * Waits for a WebElement to become clickable for a specified amount of time.
     * <p>
     * This method uses WebDriverWait to wait for the specified WebElement to become clickable on the page.
     * The wait time is determined by the time parameter. If the element does not become clickable within this time,
     * a TimeoutException will be thrown.
     *
     * @param element The WebElement to wait for.
     * @param time    The maximum time to wait in seconds.
     */
    public void waitForElementToBeClickable(WebElement element, int time) {
        getWebdriverWait(time).until(ExpectedConditions.elementToBeClickable(element));
        log.info("Waiting for element to be clickable for : {} seconds", time);
    }

    /**
     * Waits for an alert to be present.
     * <p>
     * This method uses WebDriverWait to wait for an alert to become present on the page.
     * The wait time is determined by the defaultTime variable. If the alert does not become present within this time,
     * a TimeoutException will be thrown.
     */
    public void waitUntilAlertIsPresent() {
        getWebdriverWait(defaultTime).until(ExpectedConditions.alertIsPresent());
        log.info("Waiting for alert to be present for  : {} seconds", defaultTime);
    }


    /**
     * Waits for an alert to be present for a specified amount of time.
     * <p>
     * This method uses WebDriverWait to wait for an alert to become present on the page.
     * The wait time is determined by the time parameter. If the alert does not become present within this time,
     * a TimeoutException will be thrown.
     *
     * @param time The maximum time to wait in seconds.
     */
    public void waitUntilAlertIsPresent(int time) {
        getWebdriverWait(time).until(ExpectedConditions.alertIsPresent());
        log.info("Waiting for alert to be present for  : {} seconds", time);
    }

    /**
     * Sets the implicit wait time for the WebDriver instance.
     * <p>
     * This method sets the implicit wait time for the WebDriver instance of this class.
     * The implicit wait time is the amount of time the WebDriver instance should wait when searching for elements.
     * If the element is not found within this time, a NoSuchElementException will be thrown.
     *
     * @param timeInSecond The time to wait in seconds.
     */
    public void setImplicitWait(int timeInSecond) {
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeInSecond));
            log.info("Implicit wait is set for : {} seconds", timeInSecond);
        } catch (Exception e) {
            log.error("Error occurred while setting the implicit wait");
        }
    }

    /**
     * Waits for a WebElement's text to match the expected text.
     * <p>
     * This method uses WebDriverWait to wait for the text of the specified WebElement to match the expected text.
     * The wait time is determined by the defaultTime variable. If the text does not match within this time,
     * a TimeoutException will be thrown.
     *
     * @param element      The WebElement to wait for.
     * @param expectedText The text that is expected to be in the WebElement.
     */
    public void waitForExpectedText(WebElement element, String expectedText) {
        getWebdriverWait(defaultTime).until(ExpectedConditions.textToBePresentInElement(element, expectedText));
        log.info("Waiting for element until text is : {} for  : {} seconds", expectedText, defaultTime);
    }


    /**
     * Performs a click action on a WebElement using the Actions class.
     * <p>
     * This method first waits for the specified WebElement to become clickable on the page.
     * It then performs a click action on the WebElement using the Actions class.
     * If an error occurs during this process, an error message is logged.
     *
     * @param element The WebElement to perform the click action on.
     */
    public void performClickUsingActionClass(WebElement element) {
        waitForElementToBeClickable(element);
        String locator = getLocatorFromWebElement(element);
        try {
            getActions().click(element).build().perform();
            log.info("Clicking on {} using action class", locator);
        } catch (Exception e) {
            log.error("Error occurred while clicking on element : {} with error message : {}", locator,
                    e.getMessage());
        }
    }

    /**
     * Performs a click action on a WebElement using JavaScript Executor.
     * <p>
     * This method first waits for the specified WebElement to become clickable on the page.
     * It then performs a click action on the WebElement using JavaScript Executor.
     * If an error occurs during this process, an error message is logged.
     *
     * @param element The WebElement to perform the click action on.
     */
    public void performClickUsingJSExecutor(WebElement element) {
        waitForElementToBeClickable(element);
        String locator = getLocatorFromWebElement(element);
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", element);
            log.info("Clicking on {} using javascript executor", locator);
        } catch (Exception e) {
            log.error("Error occurred while clicking on element : {} with error message : {}", locator,
                    e.getMessage());
        }
    }


    /**
     * Performs a click action on a WebElement.
     * <p>
     * This method first waits for the specified WebElement to become clickable on the page.
     * It then attempts to perform a click action on the WebElement using three different methods in the following order:
     * 1. The WebElement's click() method.
     * 2. The Actions class's click() method.
     * 3. The JavascriptExecutor's click() method.
     * <p>
     * If an error occurs during any of these processes, it will try the next method. If all methods fail, an error message is logged.
     *
     * @param element The WebElement to perform the click action on.
     */
    public void performClick(WebElement element) {
        waitForElementToBeClickable(element);
        String locator = getLocatorFromWebElement(element);
        try {
            element.click();
            log.info("Clicked on {} ", locator);
        } catch (Exception e) {
            log.warn("Unable to click on {} using element.click() method trying anther way", locator);
            try {
                performClickUsingActionClass(element);
            } catch (Exception e1) {
                try {
                    performClickUsingJSExecutor(element);
                } catch (Exception e2) {
                    log.error("Error occurred while clicking on : {} with error message : {}", locator, e2.getMessage());
                }
            }
        }
    }


    /**
     * Retrieves the text of a WebElement.
     * <p>
     * This method first waits for the specified WebElement to become visible on the page.
     * It then attempts to retrieve the text of the WebElement.
     * If an error occurs during this process, an error message is logged and "No text is found" is returned.
     *
     * @param element The WebElement to retrieve the text from.
     * @return The text of the WebElement as a String, or "No text is found" if an error occurs.
     */
    public String getTextOfElement(WebElement element) {
        waitForElementVisibility(element);
        String locator = getLocatorFromWebElement(element);
        try {
            String text = element.getText();
            log.info("Text of {} is {}", locator, text);
            return text;
        } catch (Exception exception) {
            log.error("Error occurred while getting the text for {} with error message {}", locator,
                    exception.getMessage());
        }
        return "No text is found";
    }

    /**
     * Retrieves the attribute value of a WebElement.
     * <p>
     * This method first waits for the specified WebElement to become visible on the page.
     * It then attempts to retrieve the attribute value of the WebElement using the provided attribute name.
     * If an error occurs during this process, an error message is logged and "No attribute value found" is returned.
     *
     * @param element       The WebElement to retrieve the attribute value from.
     * @param attributeName The name of the attribute to retrieve the value of.
     * @return The value of the attribute as a String, or "No attribute value found" if an error occurs.
     */
    public String getAttribute(WebElement element, String attributeName) {
        waitForElementVisibility(element);
        String locator = getLocatorFromWebElement(element);
        try {
            String text = element.getAttribute(attributeName);
            log.info("Attribute of {} with attribute name {} is {}", locator, attributeName, text);
            return text;
        } catch (Exception exception) {
            log.error("Error occurred while getting the attribute for {} with error message {}", locator,
                    exception.getMessage());
        }
        return "No attribute value found";
    }

    /**
     * Checks if a WebElement is present on the page.
     * <p>
     * This method first waits for the specified WebElement to become visible on the page.
     * It then checks if the WebElement is displayed on the page.
     * If the WebElement is displayed, it returns true. If the WebElement is not displayed or an error occurs, it returns false.
     *
     * @param element The WebElement to check for presence.
     * @return true if the WebElement is displayed, false otherwise.
     */
    public boolean isWebElementPresent(WebElement element) {
        boolean isElementVisible = true;
        try {
            waitForElementVisibility(element);
            isElementVisible = element.isDisplayed();
        } catch (Exception e) {
            isElementVisible = false;
        }
        return isElementVisible;
    }

    /**
     * Checks if a WebElement is enabled on the page.
     * <p>
     * This method first waits for the specified WebElement to become visible on the page.
     * It then checks if the WebElement is enabled on the page.
     * If the WebElement is enabled, it returns true. If the WebElement is not enabled or an error occurs, it returns false.
     *
     * @param element The WebElement to check for enabled state.
     * @return true if the WebElement is enabled, false otherwise.
     */
    public boolean isWebElementEnabled(WebElement element) {
        waitForElementVisibility(element);
        String locator = getLocatorFromWebElement(element);
        boolean isEnabled = true;
        try {
            isEnabled = element.isEnabled();
            log.info("{} is in enabled state {}", locator, isEnabled);
        } catch (Exception e) {
            log.error("Error occurred while getting the element state for locator {} ", locator);
        }
        return isEnabled;
    }

    /**
     * Takes a screenshot of a WebElement and saves it to a specified location.
     * <p>
     * This method first checks if the specified WebElement is present on the page and if the destination is not null.
     * If both conditions are met, it takes a screenshot of the WebElement and saves it as a File.
     * The screenshot is then copied to the specified destination.
     * If an error occurs during this process, an error message is logged.
     *
     * @param element     The WebElement to take a screenshot of.
     * @param destination The location to save the screenshot to.
     */
    public void takeScreenShotOfWebElement(WebElement element, String destination) {
        try {
            if (isWebElementPresent(element) && Objects.nonNull(destination)) {
                File sourceFile = element.getScreenshotAs(OutputType.FILE);
                File destinationFile = new File(destination);
                FileUtils.copyFile(sourceFile, destinationFile);
                log.info("Successfully copied the screenshot on {} ", destinationFile);
            }
        } catch (Exception e) {
            log.error("Error occurred while taking the screenshot with error message {}", e.getMessage());
        }
    }

    /**
     * Maximizes the current browser window.
     * <p>
     * This method uses the WebDriver's manage() method to get the browser's window interface and then calls its maximize() method.
     * This will maximize the current browser window that the WebDriver is controlling.
     */
    public void maximizeWindow() {
        driver.manage().window().maximize();
    }

    /**
     * Sets the current browser window to full screen.
     * <p>
     * This method uses the WebDriver's manage() method to get the browser's window interface and then calls its fullscreen() method.
     * This will set the current browser window that the WebDriver is controlling to full screen.
     */
    public void fullScreenWindow() {
        driver.manage().window().fullscreen();
    }

    /**
     * Navigates to a specified URL.
     * <p>
     * This method uses the WebDriver's navigate() method to navigate to the specified URL.
     * It then logs a message indicating that the URL was successfully launched.
     *
     * @param url The URL to navigate to.
     */
    public void launchUrl(String url) {
        driver.navigate().to(url);
        log.info("Successfully launched the url : {}", url);
    }

    /**
     * Retrieves the title of the current page.
     * <p>
     * This method uses the WebDriver's getTitle() method to retrieve the title of the current page.
     * It then logs a message indicating the title of the page.
     *
     * @return The title of the current page as a String.
     */
    public String getTitle() {
        String title = driver.getTitle();
        log.info("Current page title is : {} ", title);
        return title;
    }

    /**
     * Refreshes the current page.
     * <p>
     * This method uses the WebDriver's navigate() method to refresh the current page.
     * It then logs a message indicating that the page was successfully refreshed.
     */
    public void refresh() {
        driver.navigate().refresh();
        log.info("{} : Page is successfully refreshed", getTitle());
    }

    /**
     * Navigates forward in the browser's history.
     * <p>
     * This method uses the WebDriver's navigate() method to move forward in the browser's history.
     * It then logs a message indicating that the navigation was successful and displays the title of the current page.
     */
    public void navigateForward() {
        driver.navigate().forward();
        log.info("Successfully navigated forward from page : {} ", driver.getTitle());
    }

    /**
     * Navigates backward in the browser's history.
     * <p>
     * This method uses the WebDriver's navigate() method to move backward in the browser's history.
     * It then logs a message indicating that the navigation was successful and displays the title of the current page.
     */
    public void navigateBackward() {
        driver.navigate().back();
        log.info("Successfully navigated backward from page : {} ", driver.getTitle());
    }

    /**
     * Retrieves the text of the current alert and accepts it.
     * <p>
     * This method waits for an alert to be present on the page.
     * It then switches the WebDriver's context to the alert and retrieves its text.
     * After retrieving the text, it accepts the alert to close it.
     *
     * @return The text of the alert as a String.
     */
    public String getAlertText() {
        waitUntilAlertIsPresent();
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        alert.accept();
        return alertText;
    }

    /**
     * Accepts the current alert on the page.
     * <p>
     * This method waits for an alert to be present on the page.
     * It then switches the WebDriver's context to the alert and accepts it to close it.
     */
    public void acceptAlert() {
        waitUntilAlertIsPresent();
        driver.switchTo().alert().accept();
    }


    /**
     * Dismisses the current alert on the page.
     * <p>
     * This method waits for an alert to be present on the page.
     * It then switches the WebDriver's context to the alert and accepts it to dismiss it.
     */
    public void dismissAlert() {
        waitUntilAlertIsPresent();
        driver.switchTo().alert().dismiss();
    }

    /**
     * Enters text into a WebElement.
     * <p>
     * This method waits for the specified WebElement to become clickable on the page.
     * If the isAfterClear parameter is true, it clears the WebElement before entering the text.
     * It then enters the specified text into the WebElement.
     * If an error occurs during this process, an error message is logged.
     *
     * @param element      The WebElement to enter the text into.
     * @param text         The text to enter into the WebElement.
     * @param isAfterClear If true, the WebElement is cleared before the text is entered. If false, the text is entered without clearing the WebElement.
     */
    public void enterTextInWebElement(WebElement element, String text, boolean isAfterClear) {
        waitForElementToBeClickable(element);
        String locator = getLocatorFromWebElement(element);
        try {
            if (isAfterClear) {
                element.clear();
                element.sendKeys(text);
            } else
                element.sendKeys(text);
            log.info("Entered text : {} in : {}", text, locator);
        } catch (Exception e) {
            log.error("Error while entering text in : {} with error message : {}", locator, e.getMessage());
        }
    }


    /**
     * Takes a screenshot of the current webpage and saves it to a specified location.
     * <p>
     * This method uses the WebDriver's TakesScreenshot interface to take a screenshot of the current webpage.
     * The screenshot is then saved as a File.
     * The screenshot is then copied to the specified destination.
     * If an error occurs during this process, an error message is logged.
     *
     * @param destination The location to save the screenshot to.
     */
    public void takesWebPageScreenShot(String destination) {
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
        File destFile = new File(destination);
        try {
            FileUtils.copyFile(srcFile, destFile);
            log.info("Successfully copied the screenshot at destination : {}", destination);
        } catch (IOException e) {
            log.error("Error occurred file copying the screenshot at destination : {} with error message :{}",
                    destination, e.getMessage());
        }
    }

    /**
     * Retrieves the text of a list of WebElements.
     * <p>
     * This method iterates over a list of WebElements, waits for each element to become visible,
     * retrieves the text of each element, and adds it to a list. This list is then returned.
     * If an error occurs during this process, an error message is logged.
     *
     * @param elementsList The list of WebElements to retrieve the text from.
     * @return A list of Strings representing the text of each WebElement in the input list.
     */
    public List<String> getWebElementsText(List<WebElement> elementsList) {
        List<String> list = new ArrayList<>();
        try {
            for (WebElement element : elementsList) {
                waitForElementVisibility(element);
                String locatorText = getLocatorFromWebElement(element);
                String text = element.getText();
                log.info("Text of locator : {} is : {}", locatorText, text);
                list.add(element.getText());
            }
        } catch (Exception e) {
            log.error("Error occurred while getting the text of elements with error : {}", e.getMessage());
        }
        return list;
    }

    /**
     * Selects an option from a dropdown menu by its value.
     * <p>
     * This method waits for the specified WebElement (which should be a dropdown menu) to become visible on the page.
     * It then creates a Select object from the WebElement and selects the option with the specified value.
     * If the option is successfully selected, a log message is generated indicating the value that was selected and the locator of the dropdown menu.
     * If an error occurs during this process, an error message is logged indicating the value that was attempted and the error message.
     *
     * @param element The WebElement representing the dropdown menu.
     * @param value   The value of the option to select.
     */
    public void selectByValue(WebElement element, String value) {
        try {
            waitForElementVisibility(element);
            String locatorText = getLocatorFromWebElement(element);
            Select select = new Select(element);
            select.selectByValue(value);
            log.info("Selected value : {} from : {}", value, locatorText);
        } catch (Exception e) {
            log.error("Error while selecting the value : {} with error message : {} ", value, e.getMessage());
        }
    }

    /**
     * Selects an option from a dropdown menu by its index.
     * <p>
     * This method waits for the specified WebElement (which should be a dropdown menu) to become visible on the page.
     * It then creates a Select object from the WebElement and selects the option with the specified index.
     * If the option is successfully selected, a log message is generated indicating the index that was selected and the locator of the dropdown menu.
     * If an error occurs during this process, an error message is logged indicating the index that was attempted and the error message.
     *
     * @param element The WebElement representing the dropdown menu.
     * @param index   The index of the option to select.
     */
    public void selectByIndex(WebElement element, int index) {
        try {
            waitForElementVisibility(element);
            String locatorText = getLocatorFromWebElement(element);
            Select select = new Select(element);
            select.selectByIndex(index);
            log.info("Selected index : {} from : {}", index, locatorText);
        } catch (Exception e) {
            log.error("Error while selecting index : {} with error message : {}", index, e.getMessage());
        }
    }

    /**
     * Selects an option from a dropdown menu by its visible text.
     * <p>
     * This method waits for the specified WebElement (which should be a dropdown menu) to become visible on the page.
     * It then creates a Select object from the WebElement and selects the option with the specified visible text.
     * If the option is successfully selected, a log message is generated indicating the visible text that was selected and the locator of the dropdown menu.
     * If an error occurs during this process, an error message is logged indicating the visible text that was attempted and the error message.
     *
     * @param element     The WebElement representing the dropdown menu.
     * @param visibleText The visible text of the option to select.
     */
    public void selectByVisibleText(WebElement element, String visibleText) {
        try {
            waitForElementVisibility(element);
            String locatorText = getLocatorFromWebElement(element);
            Select select = new Select(element);
            select.selectByVisibleText(visibleText);
            log.info("Selected text : {} from : {}", visibleText, locatorText);
        } catch (Exception e) {
            log.error("Error while selecting visible text : {} with error message : {}", visibleText,
                    e.getMessage());
        }
    }

    /**
     * Deselects all options from a dropdown menu.
     * <p>
     * This method waits for the specified WebElement (which should be a dropdown menu) to become visible on the page.
     * It then creates a Select object from the WebElement and deselects all options.
     * If the options are successfully deselected, a log message is generated indicating that all options were deselected and the locator of the dropdown menu.
     * If an error occurs during this process, an error message is logged.
     *
     * @param element The WebElement representing the dropdown menu.
     */
    public void deselectAll(WebElement element) {
        try {
            waitForElementVisibility(element);
            String locatorText = getLocatorFromWebElement(element);
            Select select = new Select(element);
            select.deselectAll();
            log.info("Deselected @all options from : {}", locatorText);
        } catch (Exception e) {
            log.error("Error while deselecting the options");
        }
    }

}
