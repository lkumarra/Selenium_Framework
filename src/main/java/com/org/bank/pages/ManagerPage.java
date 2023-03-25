package com.org.bank.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
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
		log.info("Successfully created the instance of class : {} inside : {}", seleniumUtils.getClass().getName(),
				ManagerPage.class.getName());
		excelUtils = ExcelUtils.newExcelUtils();
		log.info("Successfully created the instance of class : {} inside : {}", excelUtils.getClass().getName(),
				ManagerPage.class.getName());
		streamMapperUtils = StreamMapperUtils.newStreamMapperUtils();
		log.info("Successfully created the instance of class : {} inside : {}",
				streamMapperUtils.getClass().getName(), ManagerPage.class.getName());
		PageFactory.initElements(driver, this);
		log.info("Successfully initialized the web elements of class : {}", ManagerPage.class.getName());
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
	 * Map the data of Manager page with ManagerPageModal class
	 * 
	 * @return List of ManagerPageModal class
	 */
	public List<ManagerPageModal> getManagerPageData() {
		String menuQuery = "Select * from ManagerPage";
		String managePageData = excelUtils.fetchDataInJSON(menuQuery).toString();
		ManagerPageModal[] managerPageModals = streamMapperUtils.getClassMappedResponse(managePageData,
				ManagerPageModal[].class);
		return Arrays.asList(managerPageModals);
	}

	/**
	 * Get the welcome message data.
	 * 
	 * @return Welcome message data
	 */
	public String getWelcomeMessage() {
		return getManagerPageData().stream().filter(x -> !x.getWelcomemessage().equals("")).findFirst().get()
				.getWelcomemessage();
	}

	/**
	 * Get the manager id message
	 * 
	 * @return Manager Id message
	 */
	public String getManagerIdMessage(String managerId) {
		String managerIdData = getManagerPageData().stream().filter(x -> !x.getManageridmessage().equals(""))
				.findFirst().get().getManageridmessage();
		return String.format(managerIdData, managerId);
	}

	/**
	 * Get all menu options
	 * 
	 * @return Menu options listed on Manager Page
	 */
	public List<String> getAllMenuOptions() {
		List<String> menuOptions = new ArrayList<String>();
		getManagerPageData().forEach(x -> {
			if (!x.getMenuoptions().equals("")) {
				menuOptions.add(x.getMenuoptions());
			}
		});
		return menuOptions;
	}

	public void clickOnNewCustomerPage() {
		seleniumUtils.performClick(newCustomerPageButton);
		seleniumUtils.launchUrl("https://www.demo.guru99.com/V4/manager/addcustomerpage.php");
	}
}
