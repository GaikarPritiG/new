package com.eppm.common;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;






public class pagebase extends TestBase
{ 

	
	public static final String EMPTY_KEYWORD = "{empty}";
	public static final String SPACE_KEYWORD = "{space}";
	
	
	public static WebDriver webDriver=null;
	public void refreshPage()
	{	
		webDriver.navigate().refresh();	
		
		waitForPageLoad();
	}
	public void waitForPageLoad()
	{
		String pageLoadStatus = null;
		JavascriptExecutor js = (JavascriptExecutor)webDriver;
		do {
			js = (JavascriptExecutor) webDriver;
			pageLoadStatus = (String)js.executeScript("return document.readyState");
		} while ( !pageLoadStatus.equals("complete") );
	}

	public void clearTextBox(String locator)
	{
		WebElement webElement = findWebElement(locator);
		webElement.clear();
	}

	public void sendKeys(String locator, String textValue) 
	{ 

		WebElement webElement = null;
		if(!isBlankOrNull(locator)){
			webElement = findWebElement(locator);
			if(isBlankOrNull(textValue)) {
				return;
			}

			if(isValueEmpty(textValue)) {
				//info("Clear the field contents [text='"+textValue+"', locator='"+locator+"']");
				webElement.clear();
				return;
			}

			textValue = processSpaceValues(textValue);

			webElement.clear();
			webElement.sendKeys(textValue);
			
		}
		else {
			//	throw new Exception("Invalid locator format [locator='"+locator+"']");
		}
		//log.trace("Exiting method typeInto");
	}
	
	
	
	//Check if element exists before sending keys
	public void sendKeys(String locator, String textValue,String message, boolean verifyIfExist) 
	{ 
		if(verifyIfExist)
		{
			if(verifyMustExists(locator,textValue))
			{
				sendKeys(locator, textValue, message);
			}
		}
	}
	
	


	public void sendKeys(String locator, String textValue,String message) 
	{ 
		
		try
		{
			WebElement webElement = null;
			if(!isBlankOrNull(locator)){
				webElement = findWebElement(locator);
				if(isBlankOrNull(textValue)) {
					return;
				}

				if(isValueEmpty(textValue)) {
					//info("Clear the field contents [text='"+textValue+"', locator='"+locator+"']");
					webElement.clear();
					return;
				}

				textValue = processSpaceValues(textValue);

				webElement.clear();
				webElement.sendKeys(textValue);
				
				//info("Type text into field [text='"+textValue+"', locator='"+locator+"']");
			}
			else {
				//	throw new Exception("Invalid locator format [locator='"+locator+"']");
				refreshPage();
				throw new SkipException("Skipping This Execution");
			}
		}
		catch(Exception e)
		{
			
			refreshPage();
			throw new SkipException("Skipping This Execution");

		}}
		
		protected String processSpaceValues(String value) {
			if(StringUtils.containsIgnoreCase(value,SPACE_KEYWORD)) {
				value = value.replace(SPACE_KEYWORD, " ");
				value = value.replace(SPACE_KEYWORD.toUpperCase(), " ");
				if("".equals(value.trim())) {
					//info("Set the value in text box as [value='"+value+"']");
					//log.trace("Exit method processSpaceValues");
					return value;
				}
				else {
					//	throw new Exception("Invalid space value format! [value='" + value + "']");
				}
			}
			return value;
		}

	
	protected WebElement findWebElement(String locator){

		webDriver.manage().timeouts().implicitlyWait(20000, TimeUnit.MILLISECONDS);

		WebElement webElement = null;
		if(!isBlankOrNull(locator)){
			try {

				//Report.LogInfo("findWebElement","Web element '"+locator+ "' is finding", "INFO");
				if(locator.startsWith("@id")){ // e.g @id='elementID'
					// Find the text input element by its id
					webElement = TestBase.webDriver.findElement(By.id(removeStart(locator, "@id=")));

				}else if(locator.startsWith("@name")){
					// Find the text input element by its name
					webElement = TestBase.webDriver.findElement(By.name(removeStart(locator, "@name=")));
				}else if(locator.startsWith("@linkText")){
					// Find the text input element by its link text
					webElement = TestBase.webDriver.findElement(By.linkText(removeStart(locator, "@linkText=")));
				}else if(locator.startsWith("@partialLinkText")){
					// Find the text input element by its link text
					webElement = TestBase.webDriver.findElement(By.partialLinkText(removeStart(locator, "@partialLinkText=")));
				}else if(locator.startsWith("@xpath")){
					//using XPATH locator.
					webElement = TestBase.webDriver.findElement(By.xpath(removeStart(locator, "@xpath="))); 
				}else if(locator.startsWith("@css")){
					// Find the text input element by its css locator
					webElement = TestBase.webDriver.findElement(By.cssSelector(removeStart(locator, "@css=")));
				}else if(locator.startsWith("@className")){
					// Find the text input element by its class Name
					webElement = TestBase.webDriver.findElement(By.className(removeStart(locator, "@className=")));
				}
				else if(locator.startsWith("@tagName")){
					// Find the text input element by its class Name
					webElement = TestBase.webDriver.findElement(By.className(removeStart(locator, "@tagName=")));
				}
				

			} catch(NoSuchElementException e) { 
				//Report.LogInfo("findWebElement", "Exception encountered while trying to find element [locator='"+locator+"']: "+e.getMessage(), "FAIL");
			} catch(RuntimeException e) { 
				//Report.LogInfo("findWebElement", "Element does not exist [locator='"+locator+"']: "+e.getMessage(), "FAIL");	
				((JavascriptExecutor)webDriver).executeScript("arguments[0].style.border='3px solid red'", webElement);
			}
		}
		((JavascriptExecutor)webDriver).executeScript("arguments[0].style.border='3px solid green'", webElement);

		//log.trace("Exiting method findWebElement");
		return webElement;
	}
	
	public static String removeStart(String str, String remove)
	{
		//log.trace((new StringBuilder()).append("Entering method removeStart [str=").append(str).append(", remove=").append(remove).append("]").toString());
		String returnStr = "";
		if(isBlankOrNull(str) || isBlankOrNull(remove))
		{
			//log.debug((new StringBuilder()).append("Returned value is [str='").append(str).append("']").toString());
			returnStr = str;
		}
		if(str.startsWith(remove))
			returnStr = str.substring(remove.length());
		//log.trace((new StringBuilder()).append("Exiting method removeStart [returnStr='").append(returnStr).append("']").toString());
		return returnStr;


	}
	
	public static boolean isBlankOrNull(String str)
	{
		//log.debug((new StringBuilder()).append("Inside isBlankOrNull [str=").append(str).append("]").toString());
		return str == null || str.trim().length() == 0;
	}
	
	protected boolean isValueEmpty(String value) {
		if(StringUtils.containsIgnoreCase(value,EMPTY_KEYWORD)) {
			if(EMPTY_KEYWORD.equalsIgnoreCase(value.trim())) {
				return true;
			}
			else {
				//	throw new Exception("Invalid empty value format! [value='" + value + "']");
			}
		}
		return false;
	}


public boolean verifyMustExists(String locator,String Object) {
	
	if(!isBlankOrNull(locator)){
		try {
			waitForPageLoad();				

			WebElement element=findWebElement(locator);
			WebDriverWait wait = new WebDriverWait(webDriver, 60);
			wait.until(ExpectedConditions.visibilityOf(element));
			wait.until(ExpectedConditions.elementToBeClickable(element));
		if(element != null)
			{
				
				return true;
			}
			else
			{
				refreshPage();
				throw new SkipException("Skipping This Execution");					
				//return false;					
			}				
		} catch (Exception e) {

			//setImplicitWaitTimeout(50000);
					//return false;
			refreshPage();
			throw new SkipException("Skipping This Execution");
		} 
	}else{

		//return false;	
		refreshPage();
		throw new SkipException("Skipping This Execution");
	}
}



}


