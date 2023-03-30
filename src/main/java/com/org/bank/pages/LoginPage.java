package com.org.bank.pages;

import com.org.bank.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.org.bank.modals.LoginPageModal;
import com.org.bank.utils.ExcelUtils;
import com.org.bank.utils.SeleniumUtils;
import com.org.bank.utils.StreamMapperUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class contains methods and webelements related to login page of the
 * application
 * 
 * @author Lavendra Kumar Rajput
 *
 * @Date 08/02/2023
 */
@Slf4j
public final class LoginPage {

	private final ExcelUtils excelUtils;
	private final SeleniumUtils seleniumUtils;
	private final StreamMapperUtils streamMapperUtils;
	private final String labelQuery = "Select usedIdLabel,passwordLabel,submitButtonLabel,resetButtonLabel,title from LoginPage";

	private LoginPage(WebDriver driver) {
		seleniumUtils = SeleniumUtils.newSeleniumUtils(driver);
		excelUtils = ExcelUtils.newExcelUtils(Constants.ExcelFile);
		streamMapperUtils = StreamMapperUtils.newStreamMapperUtils();
		PageFactory.initElements(driver, this);
	}

	public static LoginPage newLoginPage(WebDriver driver) {
		return new LoginPage(driver);
	}

	@FindBy(how = How.XPATH, using = "(//td[@align = 'right'])[1]")
	private WebElement userIdLabel;

	@FindBy(how = How.XPATH, using = "(//td[@align = 'right'])[2]")
	private WebElement passWordLabel;

	@FindBy(how = How.XPATH, using = "//input[@name = 'uid']")
	private WebElement useIdField;

	@FindBy(how = How.XPATH, using = "//input[@name = 'password']")
	private WebElement passwordField;

	@FindBy(how = How.XPATH, using = "//input[@name = 'btnLogin']")
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
		log.info("Title of the page is : {}", title);
		return title;
	}

	/**
	 * Get the text of the alert
	 * 
	 * @return : Text of the alert
	 */
	public String getLoginAlertText() {
		String alertText = seleniumUtils.getAlertText();
		log.info("Alert text is : {} ", alertText);
		return alertText;
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

	/**
	 * Get data in list of map from excel
	 * 
	 * @return Data in list of hashmap
	 */
	public List<HashMap<String, Object>> getLabelText() {
		return excelUtils.fetchData(labelQuery);
	}

	/**
	 * Get Data in JSONArray by reading from excel
	 * 
	 * @return {@link JSONArray}
	 */
	public JSONArray getDataInJSON() {
		return excelUtils.fetchDataInJSON(labelQuery);
	}

	/**
	 * Mapped the data to modal class
	 * 
	 * @return : Data mapped to {@link LoginPageModal} class
	 */
	public LoginPageModal getLoginPageLabelsText() {
		String stringJson = excelUtils.fetchDataInJSON(labelQuery).toString();
		LoginPageModal[] loginPageModal = streamMapperUtils.getClassMappedResponse(stringJson, LoginPageModal[].class);
		List<LoginPageModal> loginPageModalList = Arrays.asList(loginPageModal);
		return loginPageModalList.stream().filter(x -> !(x.getTitle().equals(""))).findFirst().get();
	}

	/**
	 * Get the data for testing login functionality
	 * 
	 * @return Data mapped to {@link LoginPageModal} class
	 */
	public List<LoginPageModal> getLoginCredentialsData() {
		String credentialsQuery = "Select userId, password, expectedMessage from LoginPage";
		String stringJson = excelUtils.fetchDataInJSON(credentialsQuery).toString();
		LoginPageModal[] loginPageModal = streamMapperUtils.getClassMappedResponse(stringJson, LoginPageModal[].class);
		return Arrays.asList(loginPageModal);
	}

	/**
	 * Get @all expected error message
	 * 
	 * @return : Error messages
	 */
	public List<String> getExpectedErrorMessages() {
		return getLoginCredentialsData().stream().map(LoginPageModal::getExpectedmessage).collect(Collectors.toList());
	}

	/**
	 * Get @all error alert messages and store in List
	 * 
	 * @return : Error alert message by entring different test data
	 */
	public List<String> getErrorAlertMessagesForInvalidCredentials() {
		List<LoginPageModal> loginPageModalList = getLoginCredentialsData();
		List<String> stringActualErrorMessage = new ArrayList<String>();
		loginPageModalList.forEach(x -> {
			if (x.getUserid().equals("") && x.getPassword().equals("")) {
				seleniumUtils.performClick(loginButton);
				stringActualErrorMessage.add(seleniumUtils.getAlertText());
			} else if (!x.getUserid().equals("") && x.getPassword().equals("")) {
				seleniumUtils.enterTextInWebElement(useIdField, x.getUserid(), true);
				seleniumUtils.performClick(loginButton);
				stringActualErrorMessage.add(seleniumUtils.getAlertText());
			} else if (x.getUserid().equals("")) {
				seleniumUtils.enterTextInWebElement(passwordField, x.getPassword(), true);
				seleniumUtils.performClick(loginButton);
				stringActualErrorMessage.add(seleniumUtils.getAlertText());
			} else {
				seleniumUtils.enterTextInWebElement(useIdField, x.getUserid(), true);
				seleniumUtils.enterTextInWebElement(passwordField, x.getPassword(), true);
				seleniumUtils.performClick(loginButton);
				stringActualErrorMessage.add(seleniumUtils.getAlertText());
			}
		});
		return stringActualErrorMessage;
	}

	/**
	 * Perform login
	 * 
	 * @param userName : UserName to login
	 * @param password : Password to login
	 * @return true if login successfully else false
	 */
	public boolean isLoginSuccessful(String userName, String password) {
		boolean isLoginSuccessfull = false;
		try {
			seleniumUtils.enterTextInWebElement(useIdField, userName, true);
			seleniumUtils.enterTextInWebElement(passwordField, password, true);
			seleniumUtils.performClick(loginButton);
			isLoginSuccessfull = true;
			log.info("User is successfully logged in with userName : {} and password : {} ", userName, password);
		} catch (Exception e) {
			isLoginSuccessfull = false;
			log.error("User is not able to login with userName : {} and password : {}", userName, password);
		}
		return isLoginSuccessfull;
	}

}
