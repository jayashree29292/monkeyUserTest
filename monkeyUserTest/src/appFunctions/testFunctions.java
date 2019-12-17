package appFunctions;

import java.beans.Visibility;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import core.DriverFactory;

public class testFunctions extends DriverFactory {

	DriverFactory df;
	public WebDriver driver;

	//constructor
	public testFunctions(DriverFactory df) {
		this.df =df; 
	}
	
	/*  Function : Verifies the home page launch
	 *  return : test status
	 */
	public boolean verifyApplaunched()  {
		boolean testStatus =false;
		if(df.findElement("homePage").isDisplayed()) {
			Reporter.log("Successfully loaded the home page");
			testStatus= true;
		}
		else 
			Reporter.log("Failed to load the home page");
		return testStatus;
	}

	/*  Function : Displays the Comics List
	 *  parameter : strAction - click button (random/next)
	 *  return : test status
	 */
	public boolean displayComic(String strAction)  {
		boolean testStatus =false;
		
		// Store the comic details before click
		String before_img = df.findElement("imgComic_home").getAttribute("src");

		if(strAction.equalsIgnoreCase("random")) {
			df.webClick("btnRandom");
		}
		else if(strAction.equalsIgnoreCase("next")) {
			df.webClick("btnNext");
		}
		// Store the comic details after click
		String after_img = df.findElement("imgComic_home").getAttribute("src");

		//Compare the before and after displayed comics
		if(!before_img.equals(after_img)) {
			Reporter.log("Successfully displayed " + strAction+ " Comic");
			testStatus = true;
		}
		else
			Reporter.log("Failed to display " + strAction+ " Comic");
		return testStatus;
	}

	/*  Function : Displays the comic history
	 *  return : test status
	 */
	public boolean displayComicHistory() {
		boolean testStatus =false;
		//Click Comics List
		df.webClick("lnkComicsList");

		//Verify if the latest comic is displayed
		if(df.findElement("lnkLatestComic").isDisplayed()) {
			Reporter.log("Successfully displayed the Comic History");
			testStatus = true;
		}
		else
			Reporter.log("Failed to display the Comic History");
		return testStatus;
	}

	/*  Function : Displays the latest comic from the comic list
	 *  return : test status
	 */
	public boolean displayLatestComic() {
		boolean testStatus =false;
		
		// In Comics list, Store the latest comic details before click
		String before_img = df.findElement("lnkLatestComic").getAttribute("data-src");

		//Click to display the latest comic
		df.webClick("lnkLatestComic");

		//Store the latest comic details after display
		String after_img = df.findElement("imgComic_home").getAttribute("src");

		//Verify if the latest comic is displayed on home page
		if(after_img.contains(before_img)) {
			Reporter.log("The Latest Comic is Displayed is " + df.findElement("eleTime_home").getText());
			testStatus = true;
		}
		else
			Reporter.log("Failed to fetch the latest comic");
		return testStatus;
	}

	/*  Function : Displays the Comic from a particular date
	 *  parameter : 'date' - specific date of the comic to be displayed
	 *  return : test status
	 */
	public boolean displayComicFromSpecificDates(String date) {
		boolean testStatus =false;

		// display the comics list page
		if(!displayComicHistory()) {
			Reporter.log("The Comic List failed to open");
			return testStatus;
		}
		
		// Scroll to bottom of the comics list page
		df.scrollToBottomOfthePage();
		
		// Verify if bottom of the comics list page is displayed
		if(!df.findElement("endOfPage").isDisplayed()) {
			Reporter.log("END of page not displayed at First Scroll");
		}else {
			// Scroll to bottom of the comics list page
			df.scrollToBottomOfthePage();
			if(!df.findElement("endOfPage").isDisplayed()) {
				Reporter.log("END of page not displayed");
			}
		}
		
		// Store all the comics dates into a list
		List<WebElement> dateList = df.findElements("comicDates");
		/*
		for(int i=0; i<dateList.size(); i++) {
			String temp = dateList.get(i).getText();
			System.out.println(temp);
		}*/
		
		//Iterate through the list against the expected date
		for(int i=0; i<dateList.size(); i++) {
			String temp = dateList.get(i).getText();
			if(temp.replace(" ","").replace(",","").equals(date.replace(" ","").replace(",",""))) 
			{
				Reporter.log("Comic found on the date: " + date);
				testStatus = true;
				
				//Dynamic xpath to obtain comic from particular date
				String linkxpath = "(//strong/..//a[@class='image-title'])["+(i+1)+"]";

				//Display the matching comic
				try {
					df.getDriver().findElement(By.xpath(linkxpath)).click();
				}
				catch(Exception e) {
					Reporter.log("Error while clicking the comic : " +e.getMessage());
					testStatus = false;
				}
				return testStatus;
			}
		}
		Reporter.log("No comic found on the date: "+ date + " ");
		return testStatus;
	}

	/*  Function : Adds the chosen product to cart and checks out.
	 *  parameter : 'product' - product to be shopped
	 *  return : test status
	 */
	public boolean addToCartandCheckout(String product, 
			String contactInfo, String lastName, String addrInfo, String city, String zipCode) {
		boolean testStatus =false;

		//1. Click the Shop
		df.webClick("btnShop_home");

		//Verify if products page is displayed
		if(!df.findElement("productsPage").isDisplayed()) {
			Reporter.log("Failed to load Products Page");
			return testStatus;
		}

		//Click on the chosen product
		WebElement ele_prd = df.getDriver().findElement(By.xpath("//span[text()='"+product+"']/.."));
		if(ele_prd.isDisplayed()) {
			ele_prd.click();
		}
		else
		{
			Reporter.log("Product is not found :" +product);
			return testStatus;
		}

		//2. Add product to the cart
		df.webClick("btnAddToCart");

        // verify if the product is added to cart
		WebElement ele_cart_prd = df.getDriver().findElement(By.xpath("//a[contains(text(),'"+product+"')]"));
		if(!ele_cart_prd.isDisplayed()) {
			Reporter.log("Product is not displayed in Cart Page");
			return testStatus;
		}

		//3. Check out the product
		df.webClick("btnCheckout");	

        // Verify if checkout page is displayed
		if(!df.findElement("btnContinueShip1").isDisplayed()) {
			Reporter.log("Unable to load checkout page");
			return testStatus;
		}

		//4. Fill the shipping details.
		df.webTextInput("txtContact", contactInfo);
		df.webTextInput("txtLastName", lastName);
		df.webTextInput("txtAddress", addrInfo);
		df.webTextInput("txtCity", city);
		df.webTextInput("txtZipcode", zipCode);
		
		//Close the suggestions on ZIP Code
		df.webClick("btnCloseSug_Zip");
			
		//5. Click Continue to shipping
		df.webClick("btnContinueShip2");
		
		//6. Verify if the shipping page is displayed 
		if(!df.findElement("btnContinuePay").isDisplayed()) {
			Reporter.log("Unable to load shipping page");
			return testStatus;
		}
		
		Reporter.log("Successfully added the product " + product + " to cart and checkedout");
		testStatus = true;
		return testStatus;
	}
}
