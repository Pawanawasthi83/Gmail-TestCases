package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Config.Configuration;

public class CommonUtilities {

	WebDriver driver;
	static Logger log=Logger.getLogger("devpinoyLogger");
	
	FileInputStream fis;
	Properties prop;
	
	String path = null;
	
	public CommonUtilities(WebDriver driver) {
		
		this.driver=driver;
		
	}
	
	public Properties getProperties() {
		
		try {
			fis = new FileInputStream(System.getProperty("user.dir")+File.separator+Configuration.configpath);
			prop = new Properties();
			prop.load(fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return prop;
		
	}
	
	public String getDecodedData(String encoded) {
		
		log.debug("Decoding the user password");
		if(encoded!=" " && encoded!=null){
		byte[] decodedBytes = Base64.getDecoder().decode(encoded);
		log.debug("Password successfully decoded..");
	    return new String(decodedBytes);
	    }else{
	    	log.debug("String is blank can not decoded");
	    	return null;
	    }
	}
	
	public String getMethodName() {
	    return Thread.currentThread().getStackTrace()[2].getMethodName();
	} 
	
	public boolean isElementPresent(WebElement element) {
		
		if(element!=null){
			return true;
		}else{
			return false;
		}
	}
	public String getText(final By obj)  {
		log.debug("Getting Text .... ");
		
		WebDriverWait driverWait = new WebDriverWait(driver, 20);
		driverWait.until(new ExpectedCondition<Boolean>(){			
			public Boolean apply(WebDriver driver) {
				return driver.findElement(obj).getText().length()!=0;
				}
			}
		);
		return driver.findElement(obj).getText();
		
	}
	
	public void clickElement(WebElement obj) {
		
		WebDriverWait driverWait = new WebDriverWait(driver, 25);
		driverWait.until(ExpectedConditions.elementToBeClickable(obj));
		if(isElementPresent(obj)){
			obj.click();
		}else{
			log.debug(obj+" Element Not Present");
		}
		}
	public void switchToIframe(WebElement Iframe) {
		log.debug("Switching to Iframe"+Iframe);
		driver.switchTo().frame(Iframe);
	}
	
	public void switchToMainWindow(){
		log.debug("Switching to Main Window");
		driver.switchTo().defaultContent();
				
	}
	
	public int getDaysBetweenDates(Date fromDate,Date ToDate){
		
		    try{
			long diff = ToDate.getTime() - fromDate.getTime();
		    return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		    }catch (Exception e) {
				System.out.println(e);
				return 0;
			}
			
		
	}
	
	public boolean isAlertPresent(){
		
		try{
			driver.switchTo().alert();
			return true;
		}catch(NoAlertPresentException e){
			return false;
		}
	}
	
	public HashMap<String , String> getTestCases() {
		
		String filepath = System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"
				+File.separator+"resources"+File.separator+"TestData"
				+File.separator+"UserLoginCredentials.xlsx";
		HashMap<String, String> testcases= new HashMap<String, String>();
		ExcelDataReader reader = new ExcelDataReader(filepath);
			int rowCount=reader.getRowCount("GmailLoginTestCases");
			for(int i=1;i<rowCount;i++){
				
				try {
					testcases.put(reader.excelReader("GmailLoginTestCases", i, 0), reader.excelReader("GmailLoginTestCases", i, 1));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		return testcases;
			
	}
	
	public String isRunModeTrue(HashMap<String,String> runMode,String testCase) {
		
		for(Entry<String, String>   m:runMode.entrySet()){
			     if(m.getKey().equals(testCase)){
			    	 return (String) m.getValue();}else {
						return "Y";
					}
		}
		return testCase;
	}
	
	
	
	
	
	
}
