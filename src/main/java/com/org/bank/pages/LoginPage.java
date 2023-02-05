package com.org.bank.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.org.bank.utils.ExcelUtils;
import com.org.bank.utils.SeleniumUtils;

import static com.org.bank.driverFactory.DriverManager.*;

import java.util.HashMap;
import java.util.List;

public class LoginPage {

	private ExcelUtils excelUtils;
	private SeleniumUtils seleniumUtils;
	private Logger logger = LoggerFactory.getLogger(LoginPage.class);
	private String labelQuery = "Select usedIdLabel,passwordLabel,submitButtonLabel,resetButtonLabel,title from LoginPage";
	
	public LoginPage() {
		PageFactory.initElements(getWebDriver(), this);
		logger.info("Successfully initialized the web elements");
		seleniumUtils = new SeleniumUtils(getWebDriver());
		logger.info("Successfulyy created the instance of class : {} inside : {}", seleniumUtils.getClass().getName(),
				LoginPage.class.getName());
		excelUtils = new ExcelUtils();
		logger.info("Successfulyy created the instance of class : {} inside : {}", excelUtils.getClass().getName(),
				LoginPage.class.getName());
	}

	@FindBy(how = How.XPATH, using = "(//td[@align = 'right'])[1]")
	private WebElement userIdLabel;

	@FindBy(how = How.XPATH, using = "(//td[@align = 'right'])[2]")
	private WebElement passWordLabel;

	@FindBy(how = How.NAME, using = "uid")
	private WebElement useIdField;

	@FindBy(how = How.NAME, using = "password")
	private WebElement passwordField;

	@FindBy(how = How.NAME, using = "btnLogin")
	private WebElement loginButton;

	@FindBy(how = How.NAME, using = "btnReset")
	private WebElement resetButton;

	/**
	 * Get the title of the loginPage
	 * 
	 * @return : Title of the login page
	 */
	public String getTitleOfLoginPage() {
		String title = seleniumUtils.getTitle();
		logger.info("Title of the page is : {}", title);
		return title;
	}

	/**
	 * Get the text of the alert
	 * 
	 * @return : Text of the alert
	 */
	public String getLoginAlertText() {
		return seleniumUtils.getAlertText();
	}

	/**
	 * Get the text of UseId Label
	 * 
	 * @return : Text of userId label
	 */
	public String getUserIDLabelText() {
		return seleniumUtils.getTextOfElement(userIdLabel);
	}

	/**
	 * Get the text of password label
	 * 
	 * @return : Text of password label
	 */
	public String getPasswordLabelText() {
		return seleniumUtils.getTextOfElement(passWordLabel);
	}

	/**
	 * Get the text of login button
	 * 
	 * @return : Text of login Button
	 */
	public String getLoginButtonText() {
		return seleniumUtils.getAttribute(loginButton, "value");
	}

	/**
	 * Get the text of password button
	 * 
	 * @return : Text of password button
	 */
	public String getResetButtonText() {
		return seleniumUtils.getAttribute(resetButton, "value");
	}

	/**
	 * Click on login button
	 */
	public void clickOnLoginButton() {
		seleniumUtils.performClick(loginButton);
	}

	/**
	 * Click on reset button
	 */
	public void clickOnResetButton() {
		seleniumUtils.performClick(resetButton);
	}

	/**
	 * Enter text in userId field
	 * 
	 * @param userId
	 */
	public void enterTextInUseIdField(String userId) {
		seleniumUtils.enterTextInWebElement(useIdField, userId, false);
	}

	/**
	 * Enter text in password field
	 * 
	 * @param password
	 */
	public void enterTextInPasswordField(String password) {
		seleniumUtils.enterTextInWebElement(passwordField, password, false);
	}
	
	public List<HashMap<String,Object>> getLabelText(){
		return excelUtils.fetchData(labelQuery);
	}
}
