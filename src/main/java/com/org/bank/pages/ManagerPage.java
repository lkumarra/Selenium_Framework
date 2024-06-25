package com.org.bank.pages;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.org.bank.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.org.bank.modals.ManagerPageModal;
import com.org.bank.utils.ExcelUtils;
import com.org.bank.utils.SeleniumUtils;
import com.org.bank.utils.StreamMapperUtils;

@Slf4j
public final class ManagerPage {

    private final StreamMapperUtils streamMapperUtils;
    private final SeleniumUtils seleniumUtils;
    private final ExcelUtils excelUtils;

    private ManagerPage(WebDriver driver) {
        seleniumUtils = SeleniumUtils.newSeleniumUtils(driver);
        excelUtils = ExcelUtils.newExcelUtils(Constants.ExcelFile);
        streamMapperUtils = StreamMapperUtils.newStreamMapperUtils();
        PageFactory.initElements(driver, this);
    }

    public static ManagerPage newManagerPage(WebDriver driver) {
        return new ManagerPage(driver);
    }

    @FindBy(xpath = "//ul[@class='menusubnav']/li")
    private List<WebElement> menuWebelements;

    @FindBy(xpath = "//marquee[@class='heading3']")
    private WebElement welcomeMessageWebelement;

    @FindBy(xpath = "//tr[@class='heading3']")
    private WebElement managerIdWebelement;

    @FindBy(xpath = "//a[contains(text(),'New Customer')]")
    private WebElement newCustomerPageButton;

    /**
     * Return the text welcome message after login
     *
     * @return Text of welcome message.
     */
    public String getWelcomeMessageText() {
        return seleniumUtils.getTextOfElement(welcomeMessageWebelement);
    }

    /**
     * Return the text of manager Id
     *
     * @return Text of ManageId message.
     */
    public String getManagerIdText() {
        return seleniumUtils.getTextOfElement(managerIdWebelement);
    }

    /**
     * Return the text of menu options
     *
     * @return List of text of menu options.
     */
    public List<String> getAllMenuText() {
        return seleniumUtils.getWebElementsText(menuWebelements);
    }


    /**
     * This method is used to fetch the data from the ManagerPage table in the database and map it to a list of ManagerPageModal objects.
     * It uses the fetchDataInJSON method of the excelUtils object to execute the SQL query and get the result as a JSON string.
     * Then, it uses the getClassMappedResponse method of the streamMapperUtils object to map the JSON string to an array of ManagerPageModal objects.
     * The array is then converted to a list and returned.
     * If the getClassMappedResponse method returns null, the Objects.requireNonNull method will throw a NullPointerException.
     *
     * @return A list of ManagerPageModal objects representing the data in the ManagerPage table.
     */
    public List<ManagerPageModal> getManagerPageData() {
        return Arrays.asList(Objects.requireNonNull(streamMapperUtils.getClassMappedResponse(
                excelUtils.fetchDataInJSON("Select * from ManagerPage").toString(),
                ManagerPageModal[].class
        )));
    }


    /**
     * This method retrieves the welcome message from the ManagerPage data.
     * It first fetches the data from the ManagerPage table in the database and maps it to a list of ManagerPageModal objects.
     * Then, it streams the list and maps each ManagerPageModal object to its welcome message.
     * It filters out any empty welcome messages.
     * Finally, it returns the first non-empty welcome message it finds, or an empty string if no such message exists.
     *
     * @return A string representing the welcome message, or an empty string if no non-empty welcome message is found.
     */
    public String getWelcomeMessage() {
        return getManagerPageData().stream()
                .map(ManagerPageModal::getWelcomemessage)
                .filter(StringUtils::isNotEmpty)
                .findFirst()
                .orElse("");
    }

    /**
     * This method retrieves the manager ID message from the ManagerPage data.
     * It first fetches the data from the ManagerPage table in the database and maps it to a list of ManagerPageModal objects.
     * Then, it streams the list and maps each ManagerPageModal object to its manager ID message.
     * It filters out any empty manager ID messages.
     * If a non-empty manager ID message is found, it formats the message with the provided manager ID.
     * Finally, it returns the first formatted non-empty manager ID message it finds, or an empty string if no such message exists.
     *
     * @param managerId The manager ID to be used to format the manager ID message.
     * @return A string representing the formatted manager ID message, or an empty string if no non-empty manager ID message is found.
     */
    public String getManagerIdMessage(String managerId) {
        return getManagerPageData().stream()
                .map(ManagerPageModal::getManageridmessage)
                .filter(StringUtils::isNotEmpty)
                .findFirst()
                .map(message -> String.format(message, managerId))
                .orElse("");
    }


    /**
     * This method retrieves all the menu options from the ManagerPage data.
     * It first fetches the data from the ManagerPage table in the database and maps it to a list of ManagerPageModal objects.
     * Then, it streams the list and maps each ManagerPageModal object to its menu options.
     * It filters out any empty menu options.
     * Finally, it collects all the non-empty menu options into a list and returns it.
     *
     * @return A list of strings representing the menu options, or an empty list if no non-empty menu options are found.
     */
    public List<String> getAllMenuOptions() {
        return getManagerPageData().stream()
                .map(ManagerPageModal::getMenuoptions)
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.toList());
    }

    /**
     * This method is used to navigate to the 'New Customer' page.
     * It first performs a click action on the 'newCustomerPageButton' WebElement.
     * Then, it launches the URL of the 'New Customer' page.
     */
    public void clickOnNewCustomerPage() {
        seleniumUtils.performClick(newCustomerPageButton);
        seleniumUtils.launchUrl("https://www.demo.guru99.com/V4/manager/addcustomerpage.php");
    }
}
