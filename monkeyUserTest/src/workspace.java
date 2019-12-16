import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import utility.PropertiesFileHandler;

public class workspace {
public static void main(String[] args) throws Exception {
	
	System.out.println("--------");
	
//	System.setProperty("webdriver.chrome.driver", "C:\\Selenium_work2\\onlineStores\\jars\\chromedriver_win32\\chromedriver.exe");
//	
//	// Create a new instance of the Firefox driver
//	WebDriver driver = new ChromeDriver();
//
//	//Launch the Online Store Website
//	driver.get("https://www.monkeyuser.com/");
//
//	if(driver.findElement(By.xpath("//*[.='MonkeyUser.com']")).isDisplayed()) {
//		System.out.println("Page loaded---------");
//	}
//	else{
//		System.out.println("Not loaded");
//	}
//	
//	
//	// Print a Log In message to the screen
//	System.out.println("Successfully opened the website www.Store.Demoqa.com");
//
//	//Wait for 5 Sec
//	try {
//		Thread.sleep(5000);
//	} catch (InterruptedException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}

	PropertiesFileHandler prop = new PropertiesFileHandler("WebElementProperties");
	System.out.println(prop.getValue("homePage"));
	
	
}
}
