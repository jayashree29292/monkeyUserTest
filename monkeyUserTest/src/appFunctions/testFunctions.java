package appFunctions;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import core.DriverFactory;

public class testFunctions extends DriverFactory {

	DriverFactory df;
	public WebDriver driver;

	//	public testFunctions(WebDriver driver) {
	//		this.driver =driver; 
	//	}
	public testFunctions(DriverFactory df) {
		this.df =df; 
	}
	/*
	public boolean goToHomePage() {
		boolean testStatus =false;

		return testStatus;
	}
	 */	
	public boolean verifyAppluanched()  {

		boolean testStatus =false;

		if(df.findElement("homePage").isDisplayed()) {
			System.out.println("Page loaded successully");
			testStatus= true;
		}
		else 
			System.out.println("Page not loaded");
		return testStatus;
	}

	public boolean displayComic(String strAction)  {
		boolean testStatus =false;

		String before_img = df.findElement("imgComic_home").getAttribute("src");

		if(strAction.equalsIgnoreCase("random")) {
			df.webClick("btnRandom");
		}
		else if(strAction.equalsIgnoreCase("next")) {
			df.webClick("btnNext");
		}

		String after_img = df.findElement("imgComic_home").getAttribute("src");

		if(!before_img.equals(after_img)) {
			System.out.println("Successfully displayed " + strAction+ " Comic");
			testStatus = true;
		}
		else
			System.out.println("Failed to display " + strAction+ " Comic");
		return testStatus;
	}

	public boolean displayComicHistory() {
		boolean testStatus =false;
		//Click Comics List
		df.webClick("lnkComicsList");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(df.findElement("lnkLatestComic").isDisplayed()) {
			System.out.println("Successfully displayed the Comic History");
			testStatus = true;
		}
		else
			System.out.println("Failed to display Comic History");
		return testStatus;
	}

	public boolean displayLatestComic() {
		boolean testStatus =false;
		String before_img = df.findElement("lnkLatestComic").getAttribute("data-src");

		//Fetch the image
		df.webClick("lnkLatestComic");

		String after_img = df.findElement("imgComic_home").getAttribute("src");

		//Verify if image is displayed
		if(after_img.contains(before_img)) {
			System.out.println("The Latest Comic is Displayed is " + df.findElement("eleTime_home").getText());
			testStatus = true;
		}
		else
			Reporter.log("Failed to fetch the latest comic");
		return testStatus;
	}

	public boolean displayComicFromSpecificDates(String date) {
		boolean testStatus =false;

		if(!displayComicHistory()) {
			System.out.println("The Comic List failed to open");
			return testStatus;
		}
		List<WebElement> dateList = df.findElements("comicDates");
		for(int i=0; i<dateList.size(); i++) {
			String temp = dateList.get(i).getText();
			if(temp.replace(" ","").replace(",","").equals(date.replace(" ","").replace(",",""))) 
			{
				System.out.println("Comic found on the date: " + date);
				testStatus = true;
				String linkxpath = "(//strong/..//a[@class='image-title'])["+(i+1)+"]";
				//System.out.println(linkxpath);
				try {
					df.getDriver().findElement(By.xpath(linkxpath)).click();
				}
				catch(Exception e) {
					Reporter.log("Error while clicking the comic");
					testStatus = false;
				}
				return testStatus;
			}
		}
		Reporter.log("No comic found on the date: "+ date);
		return testStatus;
	}

	public boolean addToCartandCheckout(String product) {
		boolean testStatus =false;

		//Click Shop
		df.webClick("btnShop_home");

		//Verify if "Products" element is displayed
		if(!df.findElement("productsPage").isDisplayed()) {
			Reporter.log("Unable to load Products Page");
			return testStatus;
		}

		//Click product based on choice
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement ele_prd = df.getDriver().findElement(By.xpath("//span[text()='"+product+"']/.."));
		if(ele_prd.isDisplayed()) {
			ele_prd.click();
		}
		else
		{
			Reporter.log("Product not found :" +product);
			return testStatus;
		}

		df.webClick("btnAddToCart");


		WebElement ele_cart_prd = df.getDriver().findElement(By.xpath("//a[contains(text(),'"+product+"')]"));
		if(!ele_cart_prd.isDisplayed()) {
			Reporter.log("Product not displayed in Cart Page");
			return testStatus;
		}

		df.webClick("btnCheckout");	


		if(!df.findElement("btnContinueShip").isDisplayed()) {
			Reporter.log("Unable to load Cart Page");
			return testStatus;
		}

		Reporter.log("Successfully added the product to cart and checkedout");
		testStatus = true;
		return testStatus;
	}
}
