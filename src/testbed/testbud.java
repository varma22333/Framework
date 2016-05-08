package testbed;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import testholder.textHolder1;

public class testbud {

	public static Logger log = Logger.getLogger(textHolder1.class.getName());
	public static boolean isIntialized = false;
	public static WebDriver driver;
	@BeforeSuite
	public void InIntializeLogs()
	{
		if(!isIntialized)
		
			DOMConfigurator.configure("Log4j.xml");
			log.info("Intialization is done");
			System.out.println("Intialization is done");
			isIntialized = true;
	}


@Parameters({"AppUrl"})
@BeforeClass
public void InitializeSelenium(String AppUrl)
{
	log.info("Intialization of driver and firefox browser is starting");
	
	/*ProfilesIni allProfiles = new ProfilesIni();
	FirefoxProfile profile = allProfiles.getProfile("sandeep");*/
	driver = new FirefoxDriver();
	log.info("Intialization of driver and firefox browser is done");
	log.info("Opening of url is starting");
	 
	driver.get(AppUrl);
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
	log.info("Opening of url is done");
    }

  @AfterClass
public void CloseBrowser()
{
	log.info("closing of browser");
	//driver.close();
   // driver.switchTo().alert().accept();
	driver.close();
   //System.out.println("  gfg");
	log.info("closing of browser is done");
}
  
}
