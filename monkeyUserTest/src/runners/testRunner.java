package runners;

import org.testng.annotations.Test;

import appFunctions.testFunctions;
import core.DriverFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.util.ArrayList;
import java.util.Iterator;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

public class testRunner  {
	public static WebDriver driver =null;
	DriverFactory df=null;
	testFunctions tf=null;

	@Test(priority=1)
	public void verifyAppLaunchTest() {
		try{
			Assert.assertEquals((tf.verifyApplaunched()), true);
			System.out.println("verifyAppLaunchTest Passed");
		}
		catch (Throwable e) {
			System.out.println("verifyAppLaunchTest Failed");
			Assert.fail();
		}
	}

	@Test(priority=2)
	public void displayRandomComicTest() {
		try{
			Assert.assertEquals((tf.displayComic("random")), true);
			System.out.println("displayRandomComicTest Passed");
		}
		catch (Throwable e) {
			System.out.println("displayRandomComicTest Failed");
			Assert.fail();
		}
	}
		
	@Test(dependsOnMethods = {"displayRandomComicTest"}, priority=3)
	public void displayNextComicTest() {
		try{
			Assert.assertEquals((tf.displayComic("next")), true);
			System.out.println("displayNextComicTest Passed");
		}
		catch (Throwable e) {
			System.out.println("displayNextComicTest Failed");
			Assert.fail();
		}
	}
	
	@Test(priority=4)
	public void displayComicHistoryTest() {
		try{
			Assert.assertEquals((tf.displayComicHistory()), true);
			System.out.println("displayComicHistory Passed");
		}
		catch (Throwable e) {
			System.out.println("displayComicHistory Failed");
			Assert.fail();
		}
	}
	
	@Test(dependsOnMethods = {"displayComicHistoryTest"},priority=5)
	public void displayLatestComicTest() {
		try{
			Assert.assertEquals((tf.displayLatestComic()), true);
			System.out.println("displayLatestComicTest Passed");
		}
		catch (Throwable e) {
			System.out.println("displayLatestComicTest Failed");
			Assert.fail();
		}
	}
		
	@Test(priority=6, dataProvider="ComicDatesList")
	public void displayComicFromSpecificDatesTest(String date) {
		try{
			Assert.assertEquals((tf.displayComicFromSpecificDates(date)), true);
			System.out.println("displayComicFromSpecificDatesTest Passed");
		}
		catch (Throwable e) {
			System.out.println("displayComicFromSpecificDatesTest Failed");
			Assert.fail();
		}
	}

	@Test(priority=7,dataProvider="ShippingDetails")
	public void addToCartandCheckoutTest(String product, String contactInfo, String lastName, String addrInfo, String city, String zipCode) {
	//public void addToCartandCheckoutTest() {	
		try{
			Assert.assertEquals((tf.addToCartandCheckout(product, contactInfo, lastName, addrInfo, city, zipCode)), true);
			System.out.println("addToCartandCheckout Passed");
		}
		catch (Throwable e) {
			System.out.println("addToCartandCheckout Failed " +e.getMessage());
			Assert.fail();
		}
	}
	
	@BeforeMethod
	public void beforeMethod() {
		System.out.println("-----------Test Method Run Start --------");
		
	}

	@AfterMethod
	public void afterMethod() {
		System.out.println("-----------Test Method Run End --------");
	}

	@BeforeClass
	public void beforeClass() {
	}

	@AfterClass
	public void afterClass() {
	}

	@BeforeTest
	public void beforeTest() {
		df= new DriverFactory();
		df.initialize();
		df.launchURL();
		tf= new testFunctions(df);
	}

	@AfterTest
	public void afterTest() {
		df.driverClose();
	}

	 @DataProvider(name="ComicDatesList")
	    public static Object[][] getComicListFromDataprovider(){
	    return new Object[][]
	    	{
	            {"December  4, 2018"},
	            {"June 19, 2018"},
	            {"May 30, 2017"}
	        };
	    }
	 
	 @DataProvider(name="ShippingDetails")
	    public Iterator <Object[]> getShippingDetailsFromDataprovider(){
	    	ArrayList<Object[]> details = new ArrayList<Object[]>();
	    	Object[] input = {"Developer", "+14379722734", "Jay", "Finch Avenue", "Toronto", "M2N4H6"};
	    	details.add(input);
			return details.iterator();
	    }

	    
}
