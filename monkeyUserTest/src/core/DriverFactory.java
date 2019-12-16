package core;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import runners.testRunner;
import utility.PropertiesFileHandler;

public class DriverFactory {

	private WebDriver driver =null;

	public PropertiesFileHandler eleprop =null;


	public DriverFactory() {
		
		eleprop = new PropertiesFileHandler("WebElementProperties");


	}


	public void initialize(String browser) {

		switch(browser.toLowerCase().replace(" ", "")) {

		case "chrome":
			this.driver = getChromeDriver();
			break;

		}


	}

	private WebDriver getChromeDriver() {


		String chromeDriver_path = "C:\\Selenium_work2\\onlineStores\\jars\\chromedriver_win32\\chromedriver.exe";

		System.setProperty("webdriver.chrome.driver", chromeDriver_path);

		// Create a new instance of the Chrome driver
		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(2L, TimeUnit.SECONDS);
		return driver;


	}


	public WebDriver getDriver() {

		return this.driver;

	}

	public WebDriver getDriverDF() {


		this.driver = testRunner.driver;

		return this.driver;

	}
	public void launchURL() {

		String strURL ="https://www.monkeyuser.com/"; 
		driver.get(strURL);
	}


	public void webClick(String eleName) {
		WebElement ele = findElement(eleName);
		if(ele.isDisplayed()) {
			ele.click();
		}
		else {
			System.out.print("Element is not displayed");
		}
	}


	public WebElement findElement(String eleName) {

		String xpath = eleprop.getValue(eleName);
	//	String xpath =new PropertiesFileHandler("WebElementProperties").getValue(eleName);
		WebElement ele =null;
		try {
			ele =  driver.findElement(By.xpath(xpath));
		}
		catch(Exception e) {
			System.out.println("Error while finding element " +eleName + " " + e.getMessage());
		}
		return ele;
	}
	
	public List<WebElement> findElements(String eleName) {

		String xpath = eleprop.getValue(eleName);
		List<WebElement> ele =null;
		try {
			ele = driver.findElements(By.xpath(xpath));
		}
		catch(Exception e) {
			System.out.println("Error while retrieving the elements : " +eleName + " " + e.getMessage());
		}
		return ele;
	}

}
