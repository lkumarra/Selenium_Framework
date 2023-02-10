package com.org.bank.utils;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Objects;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This Class contains the method related to selenium utility
 * 
 * @author Lavendra Kumar Rajput
 *
 * @Date 08/02/2023
 */
public class SeleniumUtils {

	private static final int defaultTime = 30;

	private final Logger logger = LoggerFactory.getLogger(SeleniumUtils.class);

	private WebDriver driver;

	public SeleniumUtils(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * 
	 * This method will return the instance of WebDriverWait classSS
	 * 
	 * @param waitTimeInSecond : Max time to wait in seconds
	 * @return Instance of {@link WebDriverWait} class
	 */
	private WebDriverWait getWebdriverWait(int waitTimeInSecond) {
		return new WebDriverWait(driver, Duration.ofSeconds(waitTimeInSecond));

	}

	/**
	 * This method will return the instance of Action class
	 * 
	 * @return Instance of {@link Actions} class
	 */
	private Actions getActions() {
		return new Actions(driver);
	}

	/**
	 * Get the locatoe for webelement
	 * 
	 * @param element : Webelement to fetch locator
	 * @return Locator with finding strategy Example : Xpath : //*[@className =
	 *         'abc']
	 */
	private String getLocatorFromWebElement(WebElement element) {
		return element.toString().split("->")[1].replaceFirst("(?s)(.*)\\]", "$1" + "");
	}

	/**
	 * Wait for the visisblity of webelement for default 30 second
	 * 
	 * @param element : Webelement to check the visiblity
	 */
	public void waitForElementVisiblity(WebElement element) {
		getWebdriverWait(defaultTime).until(ExpectedConditions.visibilityOf(element));
		logger.info("Waiting for element to be visible for : {} seconds", defaultTime);
	}

	/**
	 * Wait for the visisblity of webelement for given time
	 * 
	 * @param element : Webelement to check visiblity
	 * @param time    : Time to check visiblity in seconds
	 */
	public void waitForElementVisiblity(WebElement element, int time) {
		getWebdriverWait(time).until(ExpectedConditions.visibilityOf(element));
		logger.info("Waiting for element to be visible for : {} seconds", time);
	}

	/**
	 * Wait for element to be in clickable state for default max 30 second
	 * 
	 * @param element : Webelement to check clickable state
	 */
	public void waitForElementToBeClickable(WebElement element) {
		getWebdriverWait(defaultTime).until(ExpectedConditions.elementToBeClickable(element));
		logger.info("Waiting for element to be clickable for : {} seconds", defaultTime);
	}

	/**
	 * Wait for element to be in clickable state for given time in seconds
	 * 
	 * @param element : Webelement to check clickable state
	 * @param time    : Max time to wait
	 */
	public void waitForElementToBeClickable(WebElement element, int time) {
		getWebdriverWait(time).until(ExpectedConditions.elementToBeClickable(element));
		logger.info("Waiting for element to be clickable for : {} seconds", time);
	}

	/**
	 * Wait for alert to be present for max default time of 30 second
	 */
	public void waitUntilAlertIsPresent() {
		getWebdriverWait(defaultTime).until(ExpectedConditions.alertIsPresent());
		logger.info("Waiting for alert to be present for  : {} seconds", defaultTime);
	}

	/**
	 * Wait for alert to be present for max given time in seconds
	 * 
	 * @param time : Max time to wait for an alert
	 */
	public void waitUntilAlertIsPresent(int time) {
		getWebdriverWait(time).until(ExpectedConditions.alertIsPresent());
		logger.info("Waiting for alert to be present for  : {} seconds", time);
	}

	/**
	 * Wait for invisiblity of webelement for max default time of 30 second
	 * 
	 * @param element : Webelement to check invisiblity
	 */
	public void waitForInvisisblityOfElement(WebElement element) {
		getWebdriverWait(defaultTime).until(ExpectedConditions.invisibilityOf(element));
	}

	/**
	 * Wait for invisiblity of webelement for max given time in seconds
	 * 
	 * @param element : Webelement to check invisiblity
	 * @param time    : Max time to checkin seconds
	 */
	public void waitForInvisisblityOfElement(WebElement element, int time) {
		getWebdriverWait(time).until(ExpectedConditions.invisibilityOf(element));
	}

	/**
	 * This method will set an implicit of time in seconds for
	 * 
	 * @param timeInSecond : Maximum time to wait for an element in seconds
	 */
	public void setImplicitWait(int timeInSecond) {
		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeInSecond));
			logger.info("Implicit wait is set for : {} seconds", timeInSecond);
		} catch (Exception e) {
			logger.error("Error occured while setting the implicit wait");
		}
	}

	/**
	 * This method will wait for webelement to check the desired text of the
	 * webelement for max default time of 30 seconds
	 * 
	 * @param element      : Webelement to check the text
	 * @param expectedText : Expected text
	 */
	public void waitForExpectedText(WebElement element, String expectedText) {
		getWebdriverWait(defaultTime).until(ExpectedConditions.textToBePresentInElement(element, expectedText));
		logger.info("Waiting for element until text is : {} for  : {} seconds", expectedText, defaultTime);
	}

	/**
	 * This method will perform click using actions class
	 * 
	 * @param element : Webelement to perform click action
	 */
	public void performClickUsingActionClass(WebElement element) {
		waitForElementToBeClickable(element);
		String locator = getLocatorFromWebElement(element);
		try {
			getActions().click(element).build().perform();
			logger.info("Clicking on {} using action class", locator);
		} catch (Exception e) {
			logger.error("Error occured while clicking on element : {} with error message : {}", locator,
					e.getMessage());
		}
	}

	/**
	 * This method will perform click using JavaScript executor
	 * 
	 * @param element : Webelement to perform click action
	 */
	public void performClickUsingJSExecutor(WebElement element) {
		waitForElementToBeClickable(element);
		String locator = getLocatorFromWebElement(element);
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].click()", element);
			logger.info("Clicking on {} using javascript executor", locator);
		} catch (Exception e) {
			logger.error("Error occured while clicking on element : {} with error message : {}", locator,
					e.getMessage());
		}
	}

	/**
	 * This method will perform click by trying different ways of click
	 * 
	 * @param element : Webelement to perform click action
	 */
	public void performClick(WebElement element) {
		waitForElementToBeClickable(element);
		String locator = getLocatorFromWebElement(element);
		try {
			element.click();
			logger.info("Clicked on {} ", locator);
		} catch (Exception e) {
			logger.warn("Unable to click on {} using element.click() method trying anther way", locator);
			try {
				performClickUsingActionClass(element);
			} catch (Exception e1) {
				try {
					performClickUsingJSExecutor(element);
				} catch (Exception e2) {

				}
			}
		}
	}

	/**
	 * 
	 * Get the text of the webelement
	 * 
	 * @param element : Webelement to get text
	 * @return Text of the webelement
	 */
	public String getTextOfElement(WebElement element) {
		waitForElementVisiblity(element);
		String locator = getLocatorFromWebElement(element);
		try {
			String text = element.getText();
			logger.info("Text of {} is {}", locator, text);
			return text;
		} catch (Exception exception) {
			logger.error("Error occured while getting the text for {} with error message {}", locator,
					exception.getMessage());
		}
		return "No text is found";
	}

	/**
	 * 
	 * Get the attribute value of the webelement
	 * 
	 * @param element       : Webelement to get attribute
	 * @param attributeName : Name of the attribute
	 * @return attribute value
	 */
	public String getAttribute(WebElement element, String attributeName) {
		waitForElementVisiblity(element);
		String locator = getLocatorFromWebElement(element);
		try {
			String text = element.getAttribute(attributeName);
			logger.info("Attribute of {} with attribute name {} is {}", locator, attributeName, text);
			return text;
		} catch (Exception exception) {
			logger.error("Error occured while getting the attribute for {} with error message {}", locator,
					exception.getMessage());
		}
		return "No attribute value found";
	}

	/**
	 * This method will check whether a webelement is present or not
	 * 
	 * @param element : Webelement to check presence
	 * @return True if webelement is present else False
	 */
	public boolean isWebElementPresent(WebElement element) {
		boolean isElementVisible = true;
		try {
			waitForElementVisiblity(element);
			isElementVisible = element.isDisplayed();
		} catch (Exception e) {
			isElementVisible = false;
		}
		return isElementVisible;
	}

	/**
	 * This method will check whether a webelement is enabled or not
	 * 
	 * @param element : Webelement to check
	 * @return True if webelement is enabled else False
	 */
	public boolean isWebElementEnabled(WebElement element) {
		waitForElementVisiblity(element);
		String locator = getLocatorFromWebElement(element);
		boolean isEnabled = true;
		try {
			isEnabled = element.isEnabled();
			logger.info("{} is in enabled state {}", locator, isEnabled);
		} catch (Exception e) {
			logger.error("Error occured while getting the element state for locator {} ", locator);
		}
		return isEnabled;
	}

	/**
	 * This method will take the screenshot of the webelement
	 * 
	 * @param element:    Webelement to take screenshot
	 * @param destination : Destination to copy the screenshot
	 */
	public void takeScreenShotOfWebElement(WebElement element, String destination) {
		try {
			if (isWebElementPresent(element) && Objects.nonNull(destination)) {
				File sourceFile = element.getScreenshotAs(OutputType.FILE);
				File destinationFile = new File(destination);
				FileUtils.copyFile(sourceFile, destinationFile);
				logger.info("Successfully copied the screenshot on {} ", destinationFile);
			}
		} catch (Exception e) {
			logger.error("Error occured while taking the screenshot with error message {}", e.getMessage());
		}
	}

	/**
	 * This method will maximize the window
	 */
	public void maxiMizeWindow() {
		driver.manage().window().fullscreen();
	}

	/**
	 * This method will lauch the url
	 * 
	 * @param url : Url to launch
	 */
	public void launchUrl(String url) {
		driver.navigate().to(url);
		logger.info("Successfully lauched the url : {}", url);
	}

	/**
	 * This method will return the title of the the current page
	 * 
	 * @return : Title of the current page
	 */
	public String getTitle() {
		String title = driver.getTitle();
		logger.info("Current page title is : {} ", title);
		return title;
	}

	/**
	 * This method will refresh the page
	 */
	public void refresh() {
		driver.navigate().refresh();
		logger.info("{} : Page is successfully refreshed", getTitle());
	}

	/**
	 * This method will navigate forward
	 */
	public void navigateForward() {
		driver.navigate().forward();
		logger.info("Successfully navigated forward from page : {} ", driver.getTitle());
	}

	/**
	 * This method will navigate backword
	 */
	public void navigateBackward() {
		driver.navigate().back();
		logger.info("Successfully navigated backward from page : {} ", driver.getTitle());
	}

	/**
	 * Get the alert text and accept
	 * 
	 * @return Text of the alert
	 */
	public String getAlertText() {
		waitUntilAlertIsPresent();
		Alert alert = driver.switchTo().alert();
		String alertText = alert.getText();
		alert.accept();
		return alertText;
	}

	/**
	 * Accept the alert
	 */
	public void acceptAlert() {
		waitUntilAlertIsPresent();
		driver.switchTo().alert().accept();
	}

	/**
	 * Dismiss alert
	 */
	public void dismissAlert() {
		waitUntilAlertIsPresent();
		driver.switchTo().alert().accept();
	}

	/**
	 * Enter text in Web Element
	 * 
	 * @param element : WebElement to enter text;
	 * @param text    : Text to enter
	 */
	public void enterTextInWebElement(WebElement element, String text, boolean isAfterClear) {
		waitForElementToBeClickable(element);
		String locator = getLocatorFromWebElement(element);
		try {
			if (isAfterClear) {
				element.clear();
				element.sendKeys(text);
			} else {
				element.sendKeys(text);
			}
			logger.info("Enterd text : {} in : {}", text, locator);
		} catch (Exception e) {
			logger.error("Error while entering text in : {} with error message : {}", locator, e.getMessage());
		}
	}

	/**
	 * This method is responsible for taking the screenshot and copying that on
	 * desired destination
	 * 
	 * @param destination : Destination to copy the screenshot
	 */
	public void takesWebPageScreenShot(String destination) {
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
		File destFile = new File(destination);
		try {
			FileUtils.copyFile(srcFile, destFile);
			logger.info("Successfully copied the screenshot at destination : {}", destination);
		} catch (IOException e) {
			logger.error("Error occured file copying the screenshot at destination : {} with error message :{}",
					destination, e.getMessage());
		}
	}

}
