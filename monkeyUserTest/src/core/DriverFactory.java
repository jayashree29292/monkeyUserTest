package core;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;

import runners.testRunner;
import utility.PropertiesFileHandler;

public class DriverFactory {
	
	private WebDriver driver =null;
	public PropertiesFileHandler eleprop =null;
	public PropertiesFileHandler configprop =null;


	public DriverFactory() {
		//Property file handlers
		eleprop = new PropertiesFileHandler("WebElementProperties");
		configprop = new PropertiesFileHandler("config");
	}
    
	// Initialize the browser
	public void initialize() {
		String browser = configprop.getValue("browser");
		switch(browser.toLowerCase().replace(" ", "")) {
		case "google chrome":
		case "chrome":
			this.driver = getChromeDriver();
			break;
		}
	}

	// Create a new instance of the driver
	private WebDriver getChromeDriver() {
		String exePath = System.getProperty("user.dir")+File.separator+"lib"+File.separator+"chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", exePath);
		
		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		int wait_time = Integer.parseInt(configprop.getValue("WAIT"));
		driver.manage().timeouts().implicitlyWait(wait_time, TimeUnit.SECONDS);
		return driver;
	}

	// Get driver instance
	public WebDriver getDriver() {
		return this.driver;
	}

	// Get the URL to launch
	public void launchURL() {
		String strURL = configprop.getValue("appURL"); 
		driver.get(strURL);
	}

	// find and click the element
	public void webClick(String eleName) {
		WebElement ele = findElement(eleName);
		try {
			if(ele.isDisplayed()) {
				ele.click();
			}
			else {
				Reporter.log("Element is not displayed");
			}
		}
		catch( Exception e) {
			Reporter.log(" Failed to click the element : " +eleName + " : " + e.getMessage());
		}
	}

	// find and input values to text elements
	public void webTextInput(String eleName, String strValue) {
		WebElement ele = findElement(eleName);
		if(ele.isDisplayed() && ele.isEnabled()) {
			ele.sendKeys(strValue);
		}
		else 
			System.out.print("Element is not displayed");
	}

	//Extract the element using xpath
	public WebElement findElement(String eleName) {
		
		String xpath = eleprop.getValue(eleName);
		WebElement ele =null;
		try {
			ele =  driver.findElement(By.xpath(xpath));
		}
		catch(Exception e) {
			System.out.println("Error while finding element " +eleName + " " + e.getMessage());
		}
		return ele;
	}
	
	// Extract the list of elements using xpath
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

	// Scroll to bottom of the page
	public void scrollToBottomOfthePage() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
        //This will scroll the web page till end.		
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}
	
	// Close the browser
	public void driverClose() {
		driver.close();
	}
}
