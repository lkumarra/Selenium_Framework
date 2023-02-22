package com.org.bank.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.org.bank.modals.NewCustomerPageModal;
import com.org.bank.utils.ExcelUtils;
import com.org.bank.utils.SeleniumUtils;
import com.org.bank.utils.StreamMapperUtils;

public class NewCustomerPage {

	private final Logger logger = LoggerFactory.getLogger(NewCustomerPage.class);
	private final String newCustomerPageQuery = "Select * from NewCustomerPage";
	private SeleniumUtils seleniumUtils;
	private StreamMapperUtils streamUtils;
	private ExcelUtils excelUtils;

	public NewCustomerPage(WebDriver driver) {
		seleniumUtils = new SeleniumUtils(driver);
		logger.info("Successfully created the instace of class : {} inside class : {}",
				seleniumUtils.getClass().getName(), NewCustomerPage.class.getName());
		excelUtils = new ExcelUtils();
		logger.info("Successfully created the instace of class : {} inside class : {}", excelUtils.getClass().getName(),
				NewCustomerPage.class.getName());
		streamUtils = new StreamMapperUtils();
		logger.info("Successfully created the instace of class : {} inside class : {}",
				streamUtils.getClass().getName(), NewCustomerPage.class.getName());
		PageFactory.initElements(driver, this);
	}

	public NewCustomerPage() {

	}

	@FindBy(xpath = "//p[@class = 'heading3']")
	private WebElement addNewCustomerHeading;

	@FindBy(xpath = "//*[@name ]/parent::td/preceding-sibling::td[text()]")
	private List<WebElement> newCustomerLables;

	@FindBy(xpath = "//input[@name = 'name']")
	private WebElement customerNameField;

	@FindBy(xpath = "//input[@name = 'dob']")
	private WebElement dobField;

	@FindBy(xpath = "//textarea[@name = 'addr']")
	private WebElement addressField;

	@FindBy(xpath = "//input[@name = 'city']")
	private WebElement cityField;

	@FindBy(xpath = "//input[@name = 'state']")
	private WebElement stateField;

	@FindBy(xpath = "//input[@name = 'pinno']")
	private WebElement pinCodeField;

	@FindBy(xpath = "//input[@name = 'telephoneno']")
	private WebElement mobileNumberField;

	@FindBy(xpath = "//input[@name = 'emailid']")
	private WebElement emailIdField;

	@FindBy(xpath = "//input[@name = 'password']")
	private WebElement passwordField;

	@FindBy(xpath = "//input[@value= 'Submit']")
	private WebElement submitButton;

	@FindBy(xpath = "//input[@value= 'Reset']")
	private WebElement resetButton;

	@FindBy(xpath = "//label[@id = 'message']")
	private WebElement customerNameMessage;

	@FindBy(xpath = "//label[@id = 'message24']")
	private WebElement dateFieldMessage;

	@FindBy(xpath = "//label[@id = 'message3']")
	private WebElement addressFieldMessage;

	@FindBy(xpath = "//label[@id = 'message4']")
	private WebElement cityFieldMessage;

	@FindBy(xpath = "//label[@id = 'message5']")
	private WebElement stateFieldMessage;

	@FindBy(xpath = "//label[@id = 'message6']")
	private WebElement pinCodeFieldMessage;

	@FindBy(xpath = "//label[@id = 'message7']")
	private WebElement mobileNumberFieldMessage;

	@FindBy(xpath = "//label[@id = 'message9']")
	private WebElement emailIdFieldMessage;

	@FindBy(xpath = "//label[@id = 'message18']")
	private WebElement passwordFieldMessage;

	/**
	 * Get the heading text on new customer page
	 * 
	 * @return Heading text
	 */
	public String getAddNewCustomerHeading() {
		return seleniumUtils.getTextOfElement(addNewCustomerHeading);
	}

	/**
	 * Get the Expected text from file
	 * 
	 * @return Heading text from file
	 */
	public String getExpectedCustomerHeading() {
		return getNewCustomerPageData().stream().filter(x -> !x.getHeadingmessage().equals("")).findFirst().get()
				.getHeadingmessage();
	}

	/**
	 * Get all fields label on new customer page
	 * 
	 * @return Fields label values from DB
	 */
	public List<String> getFieldsLabelsText() {
		return seleniumUtils.getWebElementsText(newCustomerLables);
	}

	/**
	 * Get all expected label from file
	 * 
	 * @return Fields label values from file
	 */
	public List<String> getExpectedLabelMessages() {
		List<String> expectedLabels = new ArrayList<String>();
		List<NewCustomerPageModal> newCustomerPageModals = getNewCustomerPageData().stream()
				.filter(x -> !x.getFieldslabel().equals("")).collect(Collectors.toList());
		newCustomerPageModals.forEach(x -> {
			expectedLabels.add(x.getFieldslabel());
		});
		return expectedLabels;
	}

	/**
	 * Read the data of New Customer Page and map it to NewCustomerPageModal class
	 * 
	 * @return List of NewCustomerPage modal class
	 */
	public List<NewCustomerPageModal> getNewCustomerPageData() {
		String newCustomerPageData = excelUtils.fetchDataInJSON(newCustomerPageQuery).toString();
		NewCustomerPageModal[] newCustomerPageModals = streamUtils.getClassMappedResponse(newCustomerPageData,
				NewCustomerPageModal[].class);
		return Arrays.asList(newCustomerPageModals);
	}

	/**
	 * Get the validation messages by entering different inputs in customer name
	 * field
	 * 
	 * @return : List of all messages
	 */
	public List<String> getCustomerNameValidationMessages() {
		List<String> errorMessages = new ArrayList<String>();
		List<NewCustomerPageModal> newCustomerPageModals = getNewCustomerPageData().stream()
				.filter(x -> !x.getCustomernamefield().equals("")).collect(Collectors.toList());
		newCustomerPageModals.forEach(x -> {
			if (x.getCustomernamefield().equals("Blank")) {
				seleniumUtils.enterTextInWebElement(customerNameField, "", true);
				seleniumUtils.performClick(addNewCustomerHeading);
				errorMessages.add(seleniumUtils.getTextOfElement(customerNameMessage));
			} else if (x.getCustomernamefield().equals("Space")) {
				seleniumUtils.enterTextInWebElement(customerNameField, " ", true);
				seleniumUtils.performClick(addNewCustomerHeading);
				errorMessages.add(seleniumUtils.getTextOfElement(customerNameMessage));
			} else {
				seleniumUtils.enterTextInWebElement(customerNameField, x.getCustomernamefield(), true);
				seleniumUtils.performClick(addNewCustomerHeading);
				errorMessages.add(seleniumUtils.getTextOfElement(customerNameMessage));
			}
		});
		return errorMessages;
	}

	public String getCustomerNameValidationMessages(String input) {
		if (input.equals("Blank")) {
			seleniumUtils.enterTextInWebElement(customerNameField, "", true);
			seleniumUtils.performClick(addNewCustomerHeading);
			return seleniumUtils.getTextOfElement(customerNameMessage);
		} else if (input.equals("Space")) {
			seleniumUtils.enterTextInWebElement(customerNameField, " ", true);
			seleniumUtils.performClick(addNewCustomerHeading);
			return seleniumUtils.getTextOfElement(customerNameMessage);
		} else {
			seleniumUtils.enterTextInWebElement(customerNameField, input, true);
			seleniumUtils.performClick(addNewCustomerHeading);
			return seleniumUtils.getTextOfElement(customerNameMessage);
		}
	}

	/**
	 * Get the expected messages from File
	 * 
	 * @return List of expected messages for customer name field
	 */
	public List<String> getCustomerNameExpectedMessages() {
		List<String> expectedMessages = new ArrayList<String>();
		List<NewCustomerPageModal> newCustomerPageModals = getNewCustomerPageData().stream()
				.filter(x -> !x.getCustomernameexpectedmessage().equals("")).collect(Collectors.toList());
		newCustomerPageModals.forEach(x -> {
			expectedMessages.add(x.getCustomernameexpectedmessage());
		});
		return expectedMessages;
	}

	/**
	 * Get the different valiadtion messages by entering different values in DOB
	 * field
	 * 
	 * @return List of messages
	 */
	public List<String> getDateOfBirthValidationMessages() {
		List<String> errorMessages = new ArrayList<String>();
		List<NewCustomerPageModal> newCustomerPageModals = getNewCustomerPageData().stream()
				.filter(x -> !x.getDateofbirthfield().equals("")).collect(Collectors.toList());
		newCustomerPageModals.forEach(x -> {
			if (x.getDateofbirthfield().equals("Blank")) {
				seleniumUtils.enterTextInWebElement(dobField, "", true);
				seleniumUtils.performClick(addNewCustomerHeading);
				errorMessages.add(seleniumUtils.getTextOfElement(dateFieldMessage));
			} else {
				seleniumUtils.enterTextInWebElement(dobField, x.getDateofbirthfield(), true);
				seleniumUtils.performClick(addNewCustomerHeading);
				errorMessages.add(seleniumUtils.getTextOfElement(dateFieldMessage));
			}
		});
		return errorMessages;
	}

	/**
	 * Get the list of expected messages from file for DOB field
	 * 
	 * @return Expected Messages for DOB fields
	 */
	public List<String> getDateOfBirthExpectedMessages() {
		List<String> expectedMessages = new ArrayList<String>();
		List<NewCustomerPageModal> newCustomerPageModals = getNewCustomerPageData().stream()
				.filter(x -> !x.getDateofbirthexpectedmessage().equals("")).collect(Collectors.toList());
		newCustomerPageModals.forEach(x -> {
			expectedMessages.add(x.getDateofbirthexpectedmessage());
		});
		return expectedMessages;
	}

	/**
	 * Enter different inputs in city field
	 * 
	 * @return Validation messaged by entering different inputs
	 */
	public List<String> getCityFieldValidationMessages() {
		List<String> errorMessages = new ArrayList<String>();
		List<NewCustomerPageModal> newCustomerPageModals = getNewCustomerPageData().stream()
				.filter(x -> !x.getCityfield().equals("")).collect(Collectors.toList());
		newCustomerPageModals.forEach(x -> {
			if (x.getCityfield().equals("Blank")) {
				seleniumUtils.enterTextInWebElement(cityField, "", true);
				seleniumUtils.performClick(addNewCustomerHeading);
				errorMessages.add(seleniumUtils.getTextOfElement(cityFieldMessage));
			} else if (x.getCityfield().equals("Space")) {
				seleniumUtils.enterTextInWebElement(cityField, " ", true);
				seleniumUtils.performClick(addNewCustomerHeading);
				errorMessages.add(seleniumUtils.getTextOfElement(cityFieldMessage));
			} else {
				seleniumUtils.enterTextInWebElement(cityField, x.getCityfield(), true);
				seleniumUtils.performClick(addNewCustomerHeading);
				errorMessages.add(seleniumUtils.getTextOfElement(cityFieldMessage));
			}
		});
		return errorMessages;
	}

	/**
	 * Get City field expected messages
	 * 
	 * @return Expected messages
	 */
	public List<String> getCityFieldExpectedMessages() {
		List<String> expectedMessages = new ArrayList<String>();
		List<NewCustomerPageModal> newCustomerPageModals = getNewCustomerPageData().stream()
				.filter(x -> !x.getCityfieldexpectedmessage().equals("")).collect(Collectors.toList());
		newCustomerPageModals.forEach(x -> {
			expectedMessages.add(x.getCityfieldexpectedmessage());
		});
		return expectedMessages;
	}

	/**
	 * Enter different inputs in pincode field and get the validation messages
	 * 
	 * @return Validation messages
	 */
	public List<String> getPincodeValidationMessages() {
		List<String> validationMessages = new ArrayList<String>();
		List<NewCustomerPageModal> newCustomerPageModals = getNewCustomerPageData().stream()
				.filter(x -> !x.getPincodefield().equals("")).collect(Collectors.toList());
		newCustomerPageModals.forEach(x -> {
			if (x.getPincodefield().equals("Blank")) {
				seleniumUtils.enterTextInWebElement(pinCodeField, "", true);
				seleniumUtils.performClick(addNewCustomerHeading);
				validationMessages.add(seleniumUtils.getTextOfElement(pinCodeFieldMessage));
			} else if (x.getPincodefield().equals("Space")) {
				seleniumUtils.enterTextInWebElement(pinCodeField, " ", true);
				seleniumUtils.performClick(addNewCustomerHeading);
				validationMessages.add(seleniumUtils.getTextOfElement(pinCodeFieldMessage));
			} else {
				seleniumUtils.enterTextInWebElement(pinCodeField, x.getPincodefield(), true);
				seleniumUtils.performClick(addNewCustomerHeading);
				validationMessages.add(seleniumUtils.getTextOfElement(pinCodeFieldMessage));
			}
		});
		return validationMessages;
	}

	public List<String> getPinCodeFieldExpectedMessages() {
		List<String> expectedMessages = new ArrayList<String>();
		List<NewCustomerPageModal> newCustomerPageModals = getNewCustomerPageData().stream()
				.filter(x -> !x.getPincodeexpectedmessage().equals("")).collect(Collectors.toList());
		newCustomerPageModals.forEach(x -> {
			expectedMessages.add(x.getPincodeexpectedmessage());
		});
		return expectedMessages;
	}

	public List<String> getMobileValidationMessages() {
		List<String> validationMessages = new ArrayList<String>();
		List<NewCustomerPageModal> newCustomerPageModals = getNewCustomerPageData().stream()
				.filter(x -> !x.getMobilenumberfield().equals("")).collect(Collectors.toList());
		newCustomerPageModals.forEach(x -> {
			if (x.getMobilenumberfield().equals("Blank")) {
				seleniumUtils.enterTextInWebElement(mobileNumberField, "", true);
				seleniumUtils.performClick(addNewCustomerHeading);
				validationMessages.add(seleniumUtils.getTextOfElement(mobileNumberFieldMessage));
			} else if (x.getMobilenumberfield().equals("Space")) {
				seleniumUtils.enterTextInWebElement(mobileNumberField, " ", true);
				seleniumUtils.performClick(addNewCustomerHeading);
				validationMessages.add(seleniumUtils.getTextOfElement(mobileNumberFieldMessage));
			} else {
				seleniumUtils.enterTextInWebElement(mobileNumberField, x.getMobilenumberfield(), true);
				seleniumUtils.performClick(addNewCustomerHeading);
				validationMessages.add(seleniumUtils.getTextOfElement(mobileNumberFieldMessage));
			}
		});
		return validationMessages;
	}

	/**
	 * Get the expected messages for mobile field
	 * 
	 * @return Expected messages for mobile field
	 */
	public List<String> getMobileFieldExpectedMessages() {
		List<String> expectedMessages = new ArrayList<String>();
		List<NewCustomerPageModal> newCustomerPageModals = getNewCustomerPageData().stream()
				.filter(x -> !x.getMobilenumberexpectedmessage().equals("")).collect(Collectors.toList());
		newCustomerPageModals.forEach(x -> {
			expectedMessages.add(x.getMobilenumberexpectedmessage());
		});
		return expectedMessages;
	}

	/**
	 * Get the state field validation messages by entering different inputs
	 * 
	 * @return Validation messages for state field
	 */
	public List<String> getStateValidationMessages() {
		List<String> validationMessages = new ArrayList<String>();
		List<NewCustomerPageModal> newCustomerPageModals = getNewCustomerPageData().stream()
				.filter(x -> !x.getStatefield().equals("")).collect(Collectors.toList());
		newCustomerPageModals.forEach(x -> {
			if (x.getStatefield().equals("Blank")) {
				seleniumUtils.enterTextInWebElement(stateField, "", true);
				seleniumUtils.performClick(addNewCustomerHeading);
				validationMessages.add(seleniumUtils.getTextOfElement(stateFieldMessage));
			} else if (x.getStatefield().equals("Space")) {
				seleniumUtils.enterTextInWebElement(stateField, " ", true);
				seleniumUtils.performClick(addNewCustomerHeading);
				validationMessages.add(seleniumUtils.getTextOfElement(stateFieldMessage));
			} else {
				seleniumUtils.enterTextInWebElement(stateField, x.getStatefield(), true);
				seleniumUtils.performClick(addNewCustomerHeading);
				validationMessages.add(seleniumUtils.getTextOfElement(stateFieldMessage));
			}
		});
		return validationMessages;
	}

	/**
	 * Get the expected fields for state field
	 * 
	 * @return Expected fields for state field
	 */
	public List<String> getStateFieldExpectedMessages() {
		List<String> expectedMessages = new ArrayList<String>();
		List<NewCustomerPageModal> newCustomerPageModals = getNewCustomerPageData().stream()
				.filter(x -> !x.getStatefieldexpectedmessage().equals("")).collect(Collectors.toList());
		newCustomerPageModals.forEach(x -> {
			expectedMessages.add(x.getStatefieldexpectedmessage());
		});
		return expectedMessages;
	}

	/**
	 * Get the validation messages for password field
	 * 
	 * @return Password field validation messages
	 */
	public List<String> getPasswordValidationMessages() {
		List<String> validationMessages = new ArrayList<String>();
		List<NewCustomerPageModal> newCustomerPageModals = getNewCustomerPageData().stream()
				.filter(x -> !x.getPasswordfield().equals("")).collect(Collectors.toList());
		newCustomerPageModals.forEach(x -> {
			if (x.getPasswordfield().equals("Blank")) {
				seleniumUtils.enterTextInWebElement(passwordField, "", true);
				seleniumUtils.performClick(addNewCustomerHeading);
				validationMessages.add(seleniumUtils.getTextOfElement(passwordFieldMessage));
			} else if (x.getPasswordfield().equals("Space")) {
				seleniumUtils.enterTextInWebElement(passwordField, " ", true);
				seleniumUtils.performClick(addNewCustomerHeading);
				validationMessages.add(seleniumUtils.getTextOfElement(passwordFieldMessage));
			} else {
				seleniumUtils.enterTextInWebElement(passwordField, x.getPasswordfield(), true);
				seleniumUtils.performClick(addNewCustomerHeading);
				validationMessages.add(seleniumUtils.getTextOfElement(passwordFieldMessage));
			}
		});
		return validationMessages;
	}

	/**
	 * Get the password field expected messages
	 * 
	 * @return Expected field for password field
	 */
	public List<String> getPasswordFieldExpectedMessages() {
		List<String> expectedMessages = new ArrayList<String>();
		List<NewCustomerPageModal> newCustomerPageModals = getNewCustomerPageData().stream()
				.filter(x -> !x.getPasswordexpectedmessage().equals("")).collect(Collectors.toList());
		newCustomerPageModals.forEach(x -> {
			expectedMessages.add(x.getPasswordexpectedmessage());
		});
		return expectedMessages;
	}

	/**
	 * Get the validation messages for address field by entering different inputs
	 * 
	 * @return Validation messages for address field
	 */
	public List<String> getAddressValidationMessages() {
		List<String> validationMessages = new ArrayList<String>();
		List<NewCustomerPageModal> newCustomerPageModals = getNewCustomerPageData().stream()
				.filter(x -> !x.getAddressfield().equals("")).collect(Collectors.toList());
		newCustomerPageModals.forEach(x -> {
			if (x.getAddressfield().equals("Blank")) {
				seleniumUtils.enterTextInWebElement(addressField, "", true);
				seleniumUtils.performClick(addNewCustomerHeading);
				validationMessages.add(seleniumUtils.getTextOfElement(addressFieldMessage));
			} else if (x.getAddressfield().equals("Space")) {
				seleniumUtils.enterTextInWebElement(addressField, " ", true);
				seleniumUtils.performClick(addNewCustomerHeading);
				validationMessages.add(seleniumUtils.getTextOfElement(addressFieldMessage));
			} else {
				seleniumUtils.enterTextInWebElement(addressField, x.getAddressfield(), true);
				seleniumUtils.performClick(addNewCustomerHeading);
				validationMessages.add(seleniumUtils.getTextOfElement(addressFieldMessage));
			}
		});
		return validationMessages;
	}

	/**
	 * Get the address field expected messages
	 * 
	 * @return Address field expected messages
	 */
	public List<String> getAddressFieldExpectedMessages() {
		List<String> expectedMessages = new ArrayList<String>();
		List<NewCustomerPageModal> newCustomerPageModals = getNewCustomerPageData().stream()
				.filter(x -> !x.getAddressexpectedmessage().equals("")).collect(Collectors.toList());
		newCustomerPageModals.forEach(x -> {
			expectedMessages.add(x.getAddressexpectedmessage());
		});
		return expectedMessages;
	}

	/**
	 * Get the validation messages for email field by entering different inputs
	 * 
	 * @return Validation messages for email field
	 */
	public List<String> getEmailValidationMessages() {
		List<String> validationMessages = new ArrayList<String>();
		List<NewCustomerPageModal> newCustomerPageModals = getNewCustomerPageData().stream()
				.filter(x -> !x.getEmailfield().equals("")).collect(Collectors.toList());
		newCustomerPageModals.forEach(x -> {
			if (x.getEmailfield().equals("Blank")) {
				seleniumUtils.enterTextInWebElement(emailIdField, "", true);
				seleniumUtils.performClick(addNewCustomerHeading);
				validationMessages.add(seleniumUtils.getTextOfElement(emailIdFieldMessage));
			} else if (x.getEmailfield().equals("Space")) {
				seleniumUtils.enterTextInWebElement(emailIdField, " ", true);
				seleniumUtils.performClick(addNewCustomerHeading);
				validationMessages.add(seleniumUtils.getTextOfElement(emailIdFieldMessage));
			} else {
				seleniumUtils.enterTextInWebElement(emailIdField, x.getEmailfield(), true);
				seleniumUtils.performClick(addNewCustomerHeading);
				validationMessages.add(seleniumUtils.getTextOfElement(emailIdFieldMessage));
			}
		});
		return validationMessages;
	}

	/**
	 * Get the expected messages for email field
	 * 
	 * @return Email field expected messages
	 */
	public List<String> getEmailFieldExpectedMessages() {
		List<String> expectedMessages = new ArrayList<String>();
		List<NewCustomerPageModal> newCustomerPageModals = getNewCustomerPageData().stream()
				.filter(x -> !x.getEmailexpectedmessage().equals("")).collect(Collectors.toList());
		newCustomerPageModals.forEach(x -> {
			expectedMessages.add(x.getEmailexpectedmessage());
		});
		return expectedMessages;
	}

	/**
	 * Get Data Provider for Customer field
	 * 
	 * @return Datatable for customer field
	 */
	public Object[][] getCustomerFieldDataProvider() {
		List<NewCustomerPageModal> custumerFieldInputs = getNewCustomerPageData().stream()
				.filter(x -> !x.getCustomernamefield().equals("")).collect(Collectors.toList());
		List<NewCustomerPageModal> expectedCustomerMessage = getNewCustomerPageData().stream()
				.filter(x -> !x.getCustomernamefield().equals("")).collect(Collectors.toList());
		Object[][] dataTable = new Object[custumerFieldInputs.size()][1];
		for (int i = 0; i < custumerFieldInputs.size(); i++) {
			Hashtable<String, String> hashtable = new Hashtable<String, String>();
			hashtable.put("input", custumerFieldInputs.get(i).getCustomernamefield());
			hashtable.put("expectedMessage", expectedCustomerMessage.get(i).getCustomernameexpectedmessage());
			dataTable[i][0] = hashtable;
		}
		return dataTable;
	}
}
