package testholder;

import java.io.IOException;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import TestExecutor.TestExecutor;
import testbed.testbud;

//import testholder.textHolder
public class textHolder1 extends testbud
{
	@Parameters({"xlsSourceFilePath", "TestCase1SheetName"})
	@Test
	public void TestCase1(String XlsPath, String testCaseName) throws IOException
	{
		System.out.println("TestCase1");
		// pass this information to test executor
		
		TestExecutor executor = new TestExecutor(driver);
		executor.ReadAndExecuteStepsInXLSheet(XlsPath, testCaseName);
	}
	@Parameters({"xlsSourceFilePath", "TestCase2SheetName"})
	@Test
	public void TestCase2(String XlsPath, String testCaseName) throws IOException
	{
		System.out.println("TestCase2");
		// pass this information to test executor
		
		TestExecutor executor = new TestExecutor(driver);
		executor.ReadAndExecuteStepsInXLSheet(XlsPath, testCaseName);
	
	/*@Test
	public void testcase1() throws InterruptedException
	{ 
		Thread.sleep(5000L);
		System.out.println("testcase1 passed");
	}*/
	
	}
}
	

