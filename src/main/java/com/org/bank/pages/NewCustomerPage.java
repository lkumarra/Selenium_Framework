package com.org.bank.pages;

import java.util.*;
import java.util.stream.Collectors;

import com.org.bank.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.org.bank.modals.NewCustomerPageModal;
import com.org.bank.utils.ExcelUtils;
import com.org.bank.utils.SeleniumUtils;
import com.org.bank.utils.StreamMapperUtils;

@Slf4j
public final class NewCustomerPage {

    private final SeleniumUtils seleniumUtils;
    private final StreamMapperUtils streamUtils;
    private final ExcelUtils excelUtils;

    private static final String BLANK = "Blank";

    private static final String SPACE = "Space";

    private NewCustomerPage(WebDriver driver) {
        seleniumUtils = SeleniumUtils.newSeleniumUtils(driver);
        excelUtils = ExcelUtils.newExcelUtils(Constants.EXCEL_FILE_PATH);
        streamUtils = StreamMapperUtils.newStreamMapperUtils();
        PageFactory.initElements(driver, this);
    }

    public static NewCustomerPage newCustomerPage(WebDriver driver) {
        return new NewCustomerPage(driver);
    }

    @FindBy(xpath = "//p[@class = 'heading3']")
    private WebElement addNewCustomerHeading;

    @FindBy(xpath = "//*[@name ]/parent::td/preceding-sibling::td[text()]")
    private List<WebElement> newCustomerLabels;

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
     * This method retrieves the heading text of the 'Add New Customer' page.
     *
     * @return A string representing the heading text of the 'Add New Customer' page.
     */
    public String getAddNewCustomerHeading() {
        return seleniumUtils.getTextOfElement(addNewCustomerHeading);
    }

    /**
     * This method retrieves the expected heading text of the 'Add New Customer' page.
     * It maps each NewCustomerPageModal to its heading message, filters out any that are blank,
     * and returns the first one found. If no non-blank heading messages are found, it returns "Default Heading".
     *
     * @return A string representing the expected heading text of the 'Add New Customer' page.
     */
    public String getExpectedCustomerHeading() {
        return getNewCustomerPageData().stream()
                .map(NewCustomerPageModal::getHeadingmessage)
                .filter(StringUtils::isNotBlank)
                .findFirst()
                .orElse("Default Heading");
    }


    /**
     * This method retrieves the text of all labels in the 'New Customer' form.
     *
     * @return A list of strings, each representing the text of a label in the 'New Customer' form.
     */
    public List<String> getFieldsLabelsText() {
        return seleniumUtils.getWebElementsText(newCustomerLabels);
    }


    /**
     * This method retrieves the expected label messages from the 'New Customer' form data.
     * It maps each NewCustomerPageModal to its fields label, filters out any that are blank,
     * and collects them into a list.
     *
     * @return A list of strings, each representing an expected label message in the 'New Customer' form.
     */
    public List<String> getExpectedLabelMessages() {
        return getNewCustomerPageData().stream()
                .map(NewCustomerPageModal::getFieldslabel)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList());
    }


    /**
     * This method retrieves the data for the 'New Customer' page from a database.
     * It executes a SQL query to select all data from the 'NewCustomerPage' table,
     * converts the result to JSON format, maps it to an array of NewCustomerPageModal objects,
     * and returns this array as a list.
     *
     * @return A list of NewCustomerPageModal objects representing the data for the 'New Customer' page.
     */
    public List<NewCustomerPageModal> getNewCustomerPageData() {
        return Arrays.asList(Objects.requireNonNull(streamUtils.getClassMappedResponse(
                excelUtils.fetchDataInJSON("Select * from NewCustomerPage").toString(),
                NewCustomerPageModal[].class)));
    }


    /**
     * This method retrieves validation messages for the 'Customer Name' field.
     * It first fetches the data for the 'New Customer' page, filters out any entries where the 'Customer Name' field is empty,
     * and collects the remaining entries into a list.
     * Then, for each entry in this list, it enters the 'Customer Name' into the corresponding WebElement,
     * performs a click action on the 'Add New Customer' heading, and adds the text of the 'Customer Name' message to the 'errorMessages' list.
     * If the 'Customer Name' is "Blank", it enters an empty string into the WebElement.
     * If the 'Customer Name' is "Space", it enters a single space into the WebElement.
     * Otherwise, it enters the 'Customer Name' as is.
     * Finally, it returns the 'errorMessages' list.
     *
     * @return A list of strings, each representing a validation message for the 'Customer Name' field.
     */
    public List<String> getCustomerNameValidationMessages() {
        List<String> errorMessages = new ArrayList<>();
        List<NewCustomerPageModal> newCustomerPageModals = getNewCustomerPageData().stream()
                .filter(x -> !x.getCustomernamefield().equals("")).collect(Collectors.toList());
        newCustomerPageModals.forEach(x -> {
            if (x.getCustomernamefield().equals(BLANK)) {
                seleniumUtils.enterTextInWebElement(customerNameField, "", true);
                seleniumUtils.performClick(addNewCustomerHeading);
                errorMessages.add(seleniumUtils.getTextOfElement(customerNameMessage));
            } else if (x.getCustomernamefield().equals(SPACE)) {
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

    /**
     * This method retrieves a validation message for the 'Customer Name' field based on the provided input.
     * If the input is "Blank", it enters an empty string into the 'Customer Name' WebElement,
     * performs a click action on the 'Add New Customer' heading, and returns the text of the 'Customer Name' message.
     * If the input is "Space", it enters a single space into the 'Customer Name' WebElement,
     * performs a click action on the 'Add New Customer' heading, and returns the text of the 'Customer Name' message.
     * Otherwise, it enters the input as is into the 'Customer Name' WebElement,
     * performs a click action on the 'Add New Customer' heading, and returns the text of the 'Customer Name' message.
     *
     * @param input A string representing the input to be entered into the 'Customer Name' field.
     * @return A string representing a validation message for the 'Customer Name' field.
     */
    public String getCustomerNameValidationMessages(String input) {
        if (input.equals(BLANK)) {
            seleniumUtils.enterTextInWebElement(customerNameField, "", true);
            seleniumUtils.performClick(addNewCustomerHeading);
            return seleniumUtils.getTextOfElement(customerNameMessage);
        } else if (input.equals(SPACE)) {
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
     * This method retrieves the expected messages for the 'Customer Name' field.
     * It first fetches the data for the 'New Customer' page, maps each entry to its 'customernameexpectedmessage',
     * filters out any messages that are empty, and collects the remaining messages into a list.
     *
     * @return A list of strings, each representing an expected message for the 'Customer Name' field.
     */
    public List<String> getCustomerNameExpectedMessages() {
        return getNewCustomerPageData().stream()
                .map(NewCustomerPageModal::getCustomernameexpectedmessage)
                .filter(x -> !x.equals(""))
                .collect(Collectors.toList());
    }


    /**
     * This method retrieves validation messages for the 'Date of Birth' field.
     * It first fetches the data for the 'New Customer' page, filters out any entries where the 'Date of Birth' field is empty,
     * and collects the remaining entries into a list.
     * Then, for each entry in this list, it enters the 'Date of Birth' into the corresponding WebElement,
     * performs a click action on the 'Add New Customer' heading, and adds the text of the 'Date of Birth' message to the 'errorMessages' list.
     * If the 'Date of Birth' is "Blank", it enters an empty string into the WebElement.
     * Otherwise, it enters the 'Date of Birth' as is.
     * Finally, it returns the 'errorMessages' list.
     *
     * @return A list of strings, each representing a validation message for the 'Date of Birth' field.
     */
    public List<String> getDateOfBirthValidationMessages() {
        List<String> errorMessages = new ArrayList<>();
        List<NewCustomerPageModal> newCustomerPageModals = getNewCustomerPageData().stream()
                .filter(x -> !x.getDateofbirthfield().equals("")).collect(Collectors.toList());
        newCustomerPageModals.forEach(x -> {
            if (x.getDateofbirthfield().equals(BLANK)) {
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
     * This method retrieves the expected messages for the 'Date of Birth' field.
     * It first fetches the data for the 'New Customer' page, maps each entry to its 'dateofbirthexpectedmessage',
     * filters out any messages that are empty, and collects the remaining messages into a list.
     *
     * @return A list of strings, each representing an expected message for the 'Date of Birth' field.
     */
    public List<String> getDateOfBirthExpectedMessages() {
        return getNewCustomerPageData().stream()
                .map(NewCustomerPageModal::getDateofbirthexpectedmessage)
                .filter(x -> !x.equals(""))
                .collect(Collectors.toList());
    }


    /**
     * This method retrieves validation messages for the 'City' field.
     * It first fetches the data for the 'New Customer' page, filters out any entries where the 'City' field is empty,
     * and collects the remaining entries into a list.
     * Then, for each entry in this list, it enters the 'City' into the corresponding WebElement,
     * performs a click action on the 'Add New Customer' heading, and adds the text of the 'City' message to the 'errorMessages' list.
     * If the 'City' is "Blank", it enters an empty string into the WebElement.
     * If the 'City' is "Space", it enters a single space into the WebElement.
     * Otherwise, it enters the 'City' as is.
     * Finally, it returns the 'errorMessages' list.
     *
     * @return A list of strings, each representing a validation message for the 'City' field.
     */
    public List<String> getCityFieldValidationMessages() {
        List<String> errorMessages = new ArrayList<>();
        List<NewCustomerPageModal> newCustomerPageModals = getNewCustomerPageData().stream()
                .filter(x -> !x.getCityfield().equals("")).collect(Collectors.toList());
        newCustomerPageModals.forEach(x -> {
            if (x.getCityfield().equals(BLANK)) {
                seleniumUtils.enterTextInWebElement(cityField, "", true);
                seleniumUtils.performClick(addNewCustomerHeading);
                errorMessages.add(seleniumUtils.getTextOfElement(cityFieldMessage));
            } else if (x.getCityfield().equals(SPACE)) {
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
     * This method retrieves the expected messages for the 'City' field.
     * It first fetches the data for the 'New Customer' page, maps each entry to its 'cityfieldexpectedmessage',
     * filters out any messages that are empty, and collects the remaining messages into a list.
     *
     * @return A list of strings, each representing an expected message for the 'City' field.
     */
    public List<String> getCityFieldExpectedMessages() {
        return getNewCustomerPageData().stream()
                .map(NewCustomerPageModal::getCityfieldexpectedmessage)
                .filter(x -> !x.equals(""))
                .collect(Collectors.toList());
    }

    /**
     * This method retrieves validation messages for the 'Pin Code' field.
     * It first fetches the data for the 'New Customer' page, filters out any entries where the 'Pin Code' field is empty,
     * and collects the remaining entries into a list.
     * Then, for each entry in this list, it enters the 'Pin Code' into the corresponding WebElement,
     * performs a click action on the 'Add New Customer' heading, and adds the text of the 'Pin Code' message to the 'validationMessages' list.
     * If the 'Pin Code' is "Blank", it enters an empty string into the WebElement.
     * If the 'Pin Code' is "Space", it enters a single space into the WebElement.
     * Otherwise, it enters the 'Pin Code' as is.
     * Finally, it returns the 'validationMessages' list.
     *
     * @return A list of strings, each representing a validation message for the 'Pin Code' field.
     */
    public List<String> getPinCodeValidationMessages() {
        List<String> validationMessages = new ArrayList<>();
        List<NewCustomerPageModal> newCustomerPageModals = getNewCustomerPageData().stream()
                .filter(x -> !x.getPincodefield().equals("")).collect(Collectors.toList());
        newCustomerPageModals.forEach(x -> {
            if (x.getPincodefield().equals(BLANK)) {
                seleniumUtils.enterTextInWebElement(pinCodeField, "", true);
                seleniumUtils.performClick(addNewCustomerHeading);
                validationMessages.add(seleniumUtils.getTextOfElement(pinCodeFieldMessage));
            } else if (x.getPincodefield().equals(SPACE)) {
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

    /**
     * This method retrieves the expected messages for the 'Pin Code' field.
     * It first fetches the data for the 'New Customer' page, maps each entry to its 'pincodeexpectedmessage',
     * filters out any messages that are empty, and collects the remaining messages into a list.
     *
     * @return A list of strings, each representing an expected message for the 'Pin Code' field.
     */
    public List<String> getPinCodeFieldExpectedMessages() {
        return getNewCustomerPageData().stream()
                .map(NewCustomerPageModal::getPincodeexpectedmessage)
                .filter(x -> !x.equals(""))
                .collect(Collectors.toList());
    }

    /**
     * This method retrieves validation messages for the 'Mobile Number' field.
     * It first fetches the data for the 'New Customer' page, filters out any entries where the 'Mobile Number' field is empty,
     * and collects the remaining entries into a list.
     * Then, for each entry in this list, it enters the 'Mobile Number' into the corresponding WebElement,
     * performs a click action on the 'Add New Customer' heading, and adds the text of the 'Mobile Number' message to the 'validationMessages' list.
     * If the 'Mobile Number' is "Blank", it enters an empty string into the WebElement.
     * If the 'Mobile Number' is "Space", it enters a single space into the WebElement.
     * Otherwise, it enters the 'Mobile Number' as is.
     * Finally, it returns the 'validationMessages' list.
     *
     * @return A list of strings, each representing a validation message for the 'Mobile Number' field.
     */
    public List<String> getMobileValidationMessages() {
        List<String> validationMessages = new ArrayList<>();
        List<NewCustomerPageModal> newCustomerPageModals = getNewCustomerPageData().stream()
                .filter(x -> !x.getMobilenumberfield().equals("")).collect(Collectors.toList());
        newCustomerPageModals.forEach(x -> {
            if (x.getMobilenumberfield().equals(BLANK)) {
                seleniumUtils.enterTextInWebElement(mobileNumberField, "", true);
                seleniumUtils.performClick(addNewCustomerHeading);
                validationMessages.add(seleniumUtils.getTextOfElement(mobileNumberFieldMessage));
            } else if (x.getMobilenumberfield().equals(SPACE)) {
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
     * This method retrieves the expected messages for the 'Mobile Number' field.
     * It first fetches the data for the 'New Customer' page, maps each entry to its 'mobilenumberexpectedmessage',
     * filters out any messages that are empty, and collects the remaining messages into a list.
     *
     * @return A list of strings, each representing an expected message for the 'Mobile Number' field.
     */
    public List<String> getMobileFieldExpectedMessages() {
        return getNewCustomerPageData().stream()
                .map(NewCustomerPageModal::getMobilenumberexpectedmessage)
                .filter(x -> !x.equals(""))
                .collect(Collectors.toList());
    }


    /**
     * This method retrieves validation messages for the 'State' field.
     * It first fetches the data for the 'New Customer' page, filters out any entries where the 'State' field is empty,
     * and collects the remaining entries into a list.
     * Then, for each entry in this list, it enters the 'State' into the corresponding WebElement,
     * performs a click action on the 'Add New Customer' heading, and adds the text of the 'State' message to the 'validationMessages' list.
     * If the 'State' is "Blank", it enters an empty string into the WebElement.
     * If the 'State' is "Space", it enters a single space into the WebElement.
     * Otherwise, it enters the 'State' as is.
     * Finally, it returns the 'validationMessages' list.
     *
     * @return A list of strings, each representing a validation message for the 'State' field.
     */
    public List<String> getStateValidationMessages() {
        List<String> validationMessages = new ArrayList<>();
        List<NewCustomerPageModal> newCustomerPageModals = getNewCustomerPageData().stream()
                .filter(x -> !x.getStatefield().equals("")).collect(Collectors.toList());
        newCustomerPageModals.forEach(x -> {
            if (x.getStatefield().equals(BLANK)) {
                seleniumUtils.enterTextInWebElement(stateField, "", true);
                seleniumUtils.performClick(addNewCustomerHeading);
                validationMessages.add(seleniumUtils.getTextOfElement(stateFieldMessage));
            } else if (x.getStatefield().equals(SPACE)) {
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
     * This method retrieves the expected messages for the 'State' field.
     * It first fetches the data for the 'New Customer' page, maps each entry to its 'statefieldexpectedmessage',
     * filters out any messages that are empty, and collects the remaining messages into a list.
     *
     * @return A list of strings, each representing an expected message for the 'State' field.
     */
    public List<String> getStateFieldExpectedMessages() {
        return getNewCustomerPageData().stream()
                .map(NewCustomerPageModal::getStatefieldexpectedmessage)
                .filter(x -> !x.equals(""))
                .collect(Collectors.toList());
    }

    /**
     * This method retrieves validation messages for the 'Password' field.
     * It first fetches the data for the 'New Customer' page, filters out any entries where the 'Password' field is empty,
     * and collects the remaining entries into a list.
     * Then, for each entry in this list, it enters the 'Password' into the corresponding WebElement,
     * performs a click action on the 'Add New Customer' heading, and adds the text of the 'Password' message to the 'validationMessages' list.
     * If the 'Password' is "Blank", it enters an empty string into the WebElement.
     * If the 'Password' is "Space", it enters a single space into the WebElement.
     * Otherwise, it enters the 'Password' as is.
     * Finally, it returns the 'validationMessages' list.
     *
     * @return A list of strings, each representing a validation message for the 'Password' field.
     */
    public List<String> getPasswordValidationMessages() {
        List<String> validationMessages = new ArrayList<>();
        List<NewCustomerPageModal> newCustomerPageModals = getNewCustomerPageData().stream()
                .filter(x -> !x.getPasswordfield().equals("")).collect(Collectors.toList());
        newCustomerPageModals.forEach(x -> {
            if (x.getPasswordfield().equals(BLANK)) {
                seleniumUtils.enterTextInWebElement(passwordField, "", true);
                seleniumUtils.performClick(addNewCustomerHeading);
                validationMessages.add(seleniumUtils.getTextOfElement(passwordFieldMessage));
            } else if (x.getPasswordfield().equals(SPACE)) {
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
     * This method retrieves the expected messages for the 'Password' field.
     * It first fetches the data for the 'New Customer' page, maps each entry to its 'passwordexpectedmessage',
     * filters out any messages that are empty, and collects the remaining messages into a list.
     *
     * @return A list of strings, each representing an expected message for the 'Password' field.
     */
    public List<String> getPasswordFieldExpectedMessages() {
        return getNewCustomerPageData().stream()
                .map(NewCustomerPageModal::getPasswordexpectedmessage)
                .filter(x -> !x.equals(""))
                .collect(Collectors.toList());
    }

    /**
     * This method retrieves validation messages for the 'Address' field.
     * It first fetches the data for the 'New Customer' page, filters out any entries where the 'Address' field is empty,
     * and collects the remaining entries into a list.
     * Then, for each entry in this list, it enters the 'Address' into the corresponding WebElement,
     * performs a click action on the 'Add New Customer' heading, and adds the text of the 'Address' message to the 'validationMessages' list.
     * If the 'Address' is "Blank", it enters an empty string into the WebElement.
     * If the 'Address' is "Space", it enters a single space into the WebElement.
     * Otherwise, it enters the 'Address' as is.
     * Finally, it returns the 'validationMessages' list.
     *
     * @return A list of strings, each representing a validation message for the 'Address' field.
     */
    public List<String> getAddressValidationMessages() {
        List<String> validationMessages = new ArrayList<>();
        List<NewCustomerPageModal> newCustomerPageModals = getNewCustomerPageData().stream()
                .filter(x -> !x.getAddressfield().equals("")).collect(Collectors.toList());
        newCustomerPageModals.forEach(x -> {
            if (x.getAddressfield().equals(BLANK)) {
                seleniumUtils.enterTextInWebElement(addressField, "", true);
                seleniumUtils.performClick(addNewCustomerHeading);
                validationMessages.add(seleniumUtils.getTextOfElement(addressFieldMessage));
            } else if (x.getAddressfield().equals(SPACE)) {
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
     * This method retrieves the expected messages for the 'Address' field.
     * It first fetches the data for the 'New Customer' page, maps each entry to its 'addressexpectedmessage',
     * filters out any messages that are empty, and collects the remaining messages into a list.
     *
     * @return A list of strings, each representing an expected message for the 'Address' field.
     */
    public List<String> getAddressFieldExpectedMessages() {
        return getNewCustomerPageData().stream()
                .map(NewCustomerPageModal::getAddressexpectedmessage)
                .filter(x -> !x.equals(""))
                .collect(Collectors.toList());
    }

    /**
     * This method retrieves validation messages for the 'Email' field.
     * It first fetches the data for the 'New Customer' page, filters out any entries where the 'Email' field is empty,
     * and collects the remaining entries into a list.
     * Then, for each entry in this list, it enters the 'Email' into the corresponding WebElement,
     * performs a click action on the 'Add New Customer' heading, and adds the text of the 'Email' message to the 'validationMessages' list.
     * If the 'Email' is "Blank", it enters an empty string into the WebElement.
     * If the 'Email' is "Space", it enters a single space into the WebElement.
     * Otherwise, it enters the 'Email' as is.
     * Finally, it returns the 'validationMessages' list.
     *
     * @return A list of strings, each representing a validation message for the 'Email' field.
     */
    public List<String> getEmailValidationMessages() {
        List<String> validationMessages = new ArrayList<>();
        List<NewCustomerPageModal> newCustomerPageModals = getNewCustomerPageData().stream()
                .filter(x -> !x.getEmailfield().equals("")).collect(Collectors.toList());
        newCustomerPageModals.forEach(x -> {
            if (x.getEmailfield().equals(BLANK)) {
                seleniumUtils.enterTextInWebElement(emailIdField, "", true);
                seleniumUtils.performClick(addNewCustomerHeading);
                validationMessages.add(seleniumUtils.getTextOfElement(emailIdFieldMessage));
            } else if (x.getEmailfield().equals(SPACE)) {
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
     * This method retrieves the expected messages for the 'Email' field.
     * It first fetches the data for the 'New Customer' page, maps each entry to its 'emailexpectedmessage',
     * filters out any messages that are empty, and collects the remaining messages into a list.
     *
     * @return A list of strings, each representing an expected message for the 'Email' field.
     */
    public List<String> getEmailFieldExpectedMessages() {
        return getNewCustomerPageData().stream()
                .map(NewCustomerPageModal::getEmailexpectedmessage)
                .filter(x -> !x.equals(""))
                .collect(Collectors.toList());
    }


    /**
     * This method retrieves the data for the 'Customer Name' field and its expected messages.
     * It first fetches the data for the 'New Customer' page, filters out any entries where the 'Customer Name' field is empty,
     * and collects the remaining entries into a list.
     * Then, it creates a 2D Object array with the size of the list.
     * For each entry in the list, it creates a Hashtable with the 'Customer Name' field as 'input' and its expected message as 'expectedMessage',
     * and adds the Hashtable to the corresponding index in the 2D array.
     * Finally, it returns the 2D array.
     *
     * @return A 2D Object array, each containing a Hashtable with the 'Customer Name' field as 'input' and its expected message as 'expectedMessage'.
     */
    public Object[][] getCustomerFieldDataProvider() {
        List<NewCustomerPageModal> customerFieldInputs = getNewCustomerPageData().stream()
                .filter(x -> !x.getCustomernamefield().equals(""))
                .collect(Collectors.toList());

        Object[][] dataTable = new Object[customerFieldInputs.size()][1];
        for (int i = 0; i < customerFieldInputs.size(); i++) {
            Map<String, String> hashtable = new HashMap<>();
            hashtable.put("input", customerFieldInputs.get(i).getCustomernamefield());
            hashtable.put("expectedMessage", customerFieldInputs.get(i).getCustomernameexpectedmessage());
            dataTable[i][0] = hashtable;
        }
        return dataTable;
    }
}
