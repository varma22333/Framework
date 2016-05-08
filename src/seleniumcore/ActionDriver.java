package seleniumcore;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;



public class ActionDriver 
{
	public static WebDriver driver; 
	public static Actions action;
	public static Logger log = Logger.getLogger(ActionDriver.class.getName()); 
	public static boolean ActionStatus = false;
	
	
	public ActionDriver(WebDriver driver)
	{
		this.driver = driver;
	}
	
	public ActionDriver(WebDriver driver, Actions action)
	{
		this.driver = driver;
		this.action = action;
	}
	
	public enum Locator
	{
		xpath,
		id,
		css,
		linkText,
	}
	
	public void waitForPageReady()
	 {
		try
		{
			this.driver.manage().timeouts().pageLoadTimeout(180, TimeUnit.SECONDS);
			ActionStatus = true;
		}
		catch(Exception ex)
		{
			log.info("Error in page wait");
			ActionStatus = false;
		}
		 
	 }
	
	public WebElement FindElement(String ElementLocator, String element)
	{
		WebElement elemnt;
		log.info("Printing the Element Locator value coming from xl sheet ===" + ElementLocator);
		switch(Locator.valueOf(ElementLocator))
		{
			case id:
				log.info("Finding element based on ID");
				//elemnt = driver.findElement(By.id(element));
				elemnt = pollForAnElementId(element);
				break;
			
			case xpath:
				log.info("Finding based on xpath");
				elemnt = pollForAnElementXpath(element);
				break;
				
			case css:
				log.info("Finding based on css");
				elemnt = driver.findElement(By.className(element));
				break;
				
			case linkText:
				log.info("Finding based on linkText");
				elemnt = driver.findElement(By.linkText(element));
				break;
				
			default:
				log.info("Finding based on Default");
				elemnt = pollForAnElementXpath(element);
				break;
		}
		
		return elemnt;
	}
	
	public void click(String ElementLocator, String element) throws IOException
	{
		WebElement elm = FindElement(ElementLocator,element);
		if(elm != null)
		{
			if(elm.isDisplayed())
			{
				elm.click();
				log.info("Clicking on" + element + "is successful");
				ActionStatus = true;
			}
			else 
			{
				log.info("Element is present but not displayed for clicking");
				ActionStatus = false;
			}
		}
		else
		{
			TakeScreenShotWhenActionFailed();
			log.info("Element is not present for clicking");
			ActionStatus = false;
		}
	}
	
	public void VerifyText(String ElementLocator, String element, String ExpectedText)
	{
		String Text = null;
		WebElement elm = FindElement(ElementLocator,element);
		if(elm != null)
		{
			if(elm.isDisplayed())
			{
				Text = FindElement(ElementLocator,element).getText();
				log.info("Clicking on" + element + "is successful");
				ActionStatus = true;
			}
			else 
			{
				log.info("Element is present but not displayed for clicking");
				ActionStatus = false;
			}
		}
		else
		{
			log.info("Element is present for clicking");
			ActionStatus = false;
		}
		boolean status = false;
		try 
		{
			status= Text.equals(ExpectedText);
		}
		catch(NullPointerException ex)
		{
			log.info("Element is present for so null pointer execption is thrown");
		}
		Assert.assertTrue("Expected Text is not equal to element inner text", status);
		
	}
	
	public void type(String ElementLocator, String element, String TextToEnter) throws IOException
	{
		WebElement elm = FindElement(ElementLocator,element);
		if(elm != null)
		{
			if(elm.isDisplayed())
			{
				elm.sendKeys(TextToEnter);
				log.info("Typing into " + element + "is successful");
				ActionStatus = true;
			}
			else 
			{
				log.info("Element is present but not displayed for typing");
				ActionStatus = false;
			}
		}
		else
		{
			TakeScreenShotWhenActionFailed();
			log.info("Element is present for typing");
			ActionStatus = false;
		}
	}

	public void assertElementPresent(String elementLocator, String element) throws IOException
	 {
		boolean status = false;
		WebElement elm = FindElement(elementLocator ,element);
		
		if(elm.isDisplayed() && elm != null)
		{
			status = true;
			ActionStatus = true;
		}
		if(!status)
		{
			TakeScreenShotWhenActionFailed();
		}
			
		Assert.assertTrue("Specified element is not available on the page", status);
	 }

	public void assertTextPresent(String pattern)
	 {
		boolean condition = driver.getPageSource().contains(pattern);
		if(!condition)
			ActionStatus = true;
		Assert.assertTrue( "Specified text is not available on the page", condition);
	 }
	
	public void assertDynamicElementPresent(String elementLocator, String element, String value) throws IOException
	 {
		 boolean condition = false;
		 String[] values = {value};
		 System.out.println("Values ---" + values);
				 
		
		 //String completeXpath = String.format(element, values);
		 String completeXpath1 = MessageFormat.format(element, values);
		 String completeXpath = element.replace("%s",value);
		 System.out.println("complete Xpath1-------" + completeXpath1);
		 System.out.println("complete Xpath--" + completeXpath);
		 WebElement elm = FindElement(elementLocator, completeXpath);
		 System.out.println("ELement status " + elm );
		 if(elm.isDisplayed())
			 ActionStatus = true;
		 else 
			 ActionStatus = false;
			
		//Assert.assertNotNull(findElementBy(elementLocator, completeXpath));
		Assert.assertTrue("Specified dynamic element is not available on the page", elm.isDisplayed());
		log.info("Specified dynamic element is available on the page");	
	 }
	
	public void TakeScreenShotWhenActionFailed() throws IOException
	{
		String fileName = new SimpleDateFormat("yyyyMMddhhmm'.txt'").format(new Date());
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File("D:\\screenshot\\"+fileName+".png"));
		System.out.println("element is not present");
	}
	
	public WebElement pollForAnElementId(String element) 
	{
		Wait<WebDriver> wait1 = new FluentWait<WebDriver>(driver)
				.withTimeout(60, TimeUnit.SECONDS)
				.pollingEvery(50, TimeUnit.MILLISECONDS)
				.ignoring(NoSuchElementException.class)
				.ignoring(WebDriverException.class)
				.ignoring(StaleElementReferenceException.class);
		return wait1.until(ExpectedConditions.visibilityOfElementLocated(By
				.id(element)));
	}
	
	public WebElement pollForAnElementXpath(String element) 
	{
		System.out.println(element);
		Wait<WebDriver> wait1 = new FluentWait<WebDriver>(driver)
				.withTimeout(60, TimeUnit.SECONDS)
				.pollingEvery(50, TimeUnit.MILLISECONDS)
				.ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class)
				.ignoring(WebDriverException.class);
		return wait1.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath(element)));
	}
}
