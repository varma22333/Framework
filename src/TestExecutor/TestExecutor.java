package TestExecutor;

import java.io.File;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import seleniumcore.ActionDriver;
import utility.Xls_Reader;


public class TestExecutor extends ActionDriver
{
	public static Logger log = Logger.getLogger(TestExecutor.class.getName());
	
	public static WebDriver driver;
	
	public TestExecutor(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	private enum SelectActionMethods
	{
		mouseHower,
		waitForPageReady,
		click,
		doubleclick,
		type,
		getText,
		selectComboBox,
		VerifyPageText,
		uploadFile,
		assertElementPresent,
		assertTextPresent,
		assertTrue,
		assertDynamicElementPresent,
		StoreValue,
		ComapreValues,
		SwitchFrame,
		SwitchBackToTopWindow,
		SwitchFrameByIDOrName
	}
	
	public void ReadAndExecuteStepsInXLSheet(String XlsPath, String testCaseName) throws IOException
	{
		// 1. Need to call the methods XLs Reader 
		// 2. Pass the Xlsheet and path to xlsReader 
		
		Xls_Reader xls = new Xls_Reader(XlsPath);
		
		int rows = xls.getRowCount(testCaseName);
		int cols = xls.getColumnCount(testCaseName);
		
		System.out.println("Number Of Rows = " + xls.getRowCount(testCaseName));
		System.out.println("Number Of column = " + xls.getColumnCount(testCaseName));
		
		for (int i = 2; i <= rows; i++) 
		{
			String flag = xls.getCellData(testCaseName, 0, i);
			String action = xls.getCellData(testCaseName, 1, i);
			String ElementLocator = xls.getCellData(testCaseName, 2, i);
			String element = xls.getCellData(testCaseName, 3, i);
			String ElementDescription = xls.getCellData(testCaseName, 4, i);
			String EnterText = xls.getCellData(testCaseName, 5, i);
			String VerifyText = xls.getCellData(testCaseName, 6, i);
			
			System.out.println("flag -->" + flag);
			System.out.println("action -->" + action);
			System.out.println("ElementLocator -->" + ElementLocator);
			System.out.println("element -->" + element);
			System.out.println("ElementDescription -->" + ElementDescription);
			System.out.println("EnterText -->" + EnterText);
			System.out.println("VerifyText -->" + VerifyText);
			
			if((flag.trim()).equalsIgnoreCase("Yes"))
			{
				// then perform that action
				switch(SelectActionMethods.valueOf(action))
    			{
	    			/*case mouseHower:
	    				mouseHover(ElementLocator, element);
	    				break;*/
	
	    			case click: 
	    				click(ElementLocator, element);	
	    				if(ActionStatus)
	    				{
	    					xls.setCellData(testCaseName, "Result", i, "Pass");
	    				}
	    				else
	    				{
	    					xls.setCellData(testCaseName, "Result", i, "Fail");
	    				}
	    				break;
	    			
	    			/*case doubleclick:
        				doubleClick(ElementLocator, element);
        				break;*/
        			
	    			case type:
	    				if(!EnterText.isEmpty())
	    				{
	    					type(ElementLocator, element, EnterText);
	    				}
	    				if(ActionStatus)
	    				{
	    					xls.setCellData(testCaseName, "Result", i, "Pass");
	    				}
	    				else
	    				{
	    					xls.setCellData(testCaseName, "Result", i, "Fail");
	    				}
	    				break;
	    				
	    			/*case selectComboBox:
	    				selectComboBox(ElementLocator, element, EnterText);
	    				break;*/
	    					    				
	    			/*case uploadFile:
	    				uploadFile(ElementLocator, element, EnterText);
	    				break;*/
	    				
	    			case assertElementPresent:
	    				assertElementPresent(ElementLocator, element);
	    				if(ActionStatus)
	    				{
	    					xls.setCellData(testCaseName, "Result", i, "Pass");
	    				}
	    				else
	    				{
	    					xls.setCellData(testCaseName, "Result", i, "Fail");
	    				}
	    				break;
	    				
	    			case assertTextPresent:
	    				assertTextPresent(EnterText);
	    				if(ActionStatus)
	    				{
	    					xls.setCellData(testCaseName, "Result", i, "Pass");
	    				}
	    				else
	    				{
	    					xls.setCellData(testCaseName, "Result", i, "Fail");
	    				}
	    				break;
	    				
	    			case assertDynamicElementPresent:
	    				assertDynamicElementPresent(ElementLocator, element, EnterText);
	    				if(ActionStatus)
	    				{
	    					xls.setCellData(testCaseName, "Result", i, "Pass");
	    				}
	    				else
	    				{
	    					xls.setCellData(testCaseName, "Result", i, "Fail");
	    				}
	    				break;
	    			
	    			case waitForPageReady:
	    				waitForPageReady();
	    				if(ActionStatus)
	    				{
	    					xls.setCellData(testCaseName, "Result", i, "Pass");
	    				}
	    				else
	    				{
	    					xls.setCellData(testCaseName, "Result", i, "Fail");
	    				}
	    				break;
	    			
	    			/*	
	    			case SwitchFrame:
	    				SwitchFrame(element);
	    				break;
	    				
	    			case SwitchBackToTopWindow:
	    				SwitchBackToTopWindow();
	    				break;*/
	    				
	    			default: log.info("No Action Key matched with the switch cases");
        			break;
    			}
			}
				
			}
			
		}
	}
	
	
	
	
	
	


