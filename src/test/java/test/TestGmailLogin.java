package test;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import Config.Configuration;
import Config.loginPageOR;
import pages.GmailHome;
import pages.GmailLogin;
import utility.CommonUtilities;
import utility.DataProviders;
import utility.GetBrowser;

public class TestGmailLogin {

	WebDriver driver;
	Properties prop;
	String Browser;
	GetBrowser browser;
	CommonUtilities cutil;
	HashMap<String, String> runMode;
	static Logger log=Logger.getLogger("devpinoyLogger");
	
	@Parameters("Browser")
	@BeforeClass
	public void config(@Optional("FireFox") String Browser){
		browser=new GetBrowser();
		driver = browser.getDriver(Browser);
		cutil =new CommonUtilities(driver);
		runMode = cutil.getTestCases();
	}
	@Parameters("Browser")
	@BeforeMethod
	public void initialization(@Optional("FireFox") String Browser){
		browser=new GetBrowser();
		driver = browser.getDriver(Browser);
		cutil =new CommonUtilities(driver);
		prop = cutil.getProperties();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(Configuration.testUrl);
		
	}
	
	@Test(dataProvider="login",dataProviderClass=DataProviders.class,priority=1,
		   enabled=true,groups={"QA","UAT","PROD"},alwaysRun=true,testName="testGmailSuccessfullLogin",
		   description="Enter Correct user Name and Password")
	public void testGmailSuccessfullLogin(String name ,String password){
		
		log.debug("Initiating test case "+cutil.getMethodName()+" With user name: "+name+"Password: "+password);
		
		if(cutil.isRunModeTrue(runMode, cutil.getMethodName()).equals("N")){
			log.debug("Skiping test case"+cutil.getMethodName());
			throw new SkipException("Skiping Test case");
		}		
		GmailLogin objLogin = new GmailLogin(driver);
		String pageBanner = cutil.getText(By.xpath(loginPageOR.loginpage_titletext));
		Assert.assertTrue(pageBanner.equals(prop.getProperty("validate_SingIn_Text")));
		GmailHome objHome = objLogin.loginSuccessToGmail(name, password);
		String userID = objHome.getHomePageUserName();
		Assert.assertTrue(userID.toLowerCase().equals(name));
	}
	@Test(priority=2,enabled=true,groups={"QA","UAT","PROD"},alwaysRun=true,testName="testGmailLoginWithBlankUserName",
			   description="Email/phone Field is blank")
	public void testGmailLoginWithBlankUserName(){
			
			log.debug("Initiating test case "+cutil.getMethodName());
			
			GmailLogin objLogin = new GmailLogin(driver);
			String pageBanner = cutil.getText(By.xpath(loginPageOR.loginpage_titletext));
			Assert.assertTrue(pageBanner.equals(prop.getProperty("validate_SingIn_Text")));
			String actualErrorMsg = objLogin.loginFailureWithBlankUserName(" "," ");
			System.out.println(actualErrorMsg);
			System.out.println(prop.getProperty("gmail_blank_user_error"));
			Assert.assertTrue(actualErrorMsg.equalsIgnoreCase(prop.getProperty("gmail_blank_user_error")));
		}
	@Test(priority=3,enabled=true,groups={"QA","UAT","PROD"},alwaysRun=true,testName="testGmailLoginWithPreviousPassword",
			   description="Verify error message after entering previous password")
	public void testGmailLoginWithPreviousPassword() {
			
			log.debug("Initiating test case "+cutil.getMethodName());
			
			GmailLogin objLogin = new GmailLogin(driver);
			
			String pageBanner = cutil.getText(By.xpath(loginPageOR.loginpage_titletext));
			
			Assert.assertTrue(pageBanner.equals(prop.getProperty("validate_SingIn_Text")));
			String actualErrorMsg = objLogin.loginFailureWithPrvPassword(prop.getProperty("gmail_username"), 
					prop.getProperty("gmail_password_Prv"));
			Date password_Change_Date = null;
			try{
				password_Change_Date=new SimpleDateFormat("dd MM yyyy").parse(prop.getProperty("gmail_Prv_psw_change_date"));
			}catch (ParseException e) {
				
				System.out.println(e);
			}
			Date currentDate=new Date();
			String expectedMsg = MessageFormat.format(prop.getProperty("gmail_Pwd_changed_error"), 
					cutil.getDaysBetweenDates(password_Change_Date,currentDate));
			System.out.println(actualErrorMsg);
			System.out.println(expectedMsg);
			Assert.assertTrue(actualErrorMsg.equalsIgnoreCase(expectedMsg));
		}
	@Test(priority=5,enabled=true,groups={"QA","UAT","PROD"},alwaysRun=true,testName="GmailLogin",
			description="Verify Edit gmail profile ")
	public void testEditProfilePic() {
		
		log.debug("Initiating test case"+cutil.getMethodName());
		GmailLogin objLogin = new GmailLogin(driver);
		GmailHome objHome = objLogin.loginSuccessToGmail(prop.getProperty("gmail_username"), prop.getProperty("gmail_password"));
		objHome.editProfilePic();

	}
	@Test(priority=4,enabled=true,groups={"QA","UAT","PROD"},alwaysRun=true,testName="GmailLogin"
			,description="Verify Gmail logout")
	public void testGmailLogOut() {
		log.debug("Initiating test case"+cutil.getMethodName());
		GmailLogin objLogin = new GmailLogin(driver);
		GmailHome objHome = objLogin.loginSuccessToGmail(prop.getProperty("gmail_username"), prop.getProperty("gmail_password"));
		objHome.gmailSignOut();
		if(cutil.isAlertPresent()){
			driver.switchTo().alert().accept();
		}
		Assert.assertTrue(cutil.isElementPresent(driver.findElement(By.xpath(loginPageOR.loginpage_submitbtn))));
	}

	@AfterMethod
	public void rampDown(){
		//driver.close();
		}
	
	
}
