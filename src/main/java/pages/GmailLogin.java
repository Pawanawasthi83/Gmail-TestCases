package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import Config.loginPageOR;
import utility.CommonUtilities;

public class GmailLogin {

	WebDriver driver;
	CommonUtilities cutil;
	static Logger log=Logger.getLogger("devpinoyLogger");
	
	@FindBy(how=How.XPATH, xpath=loginPageOR.loginpage_username)
	WebElement loginusername;
	@FindBy(how=How.XPATH, using=loginPageOR.loginpage_nextbtn)
	WebElement login_nextbtn;
	@FindBy(how=How.XPATH, xpath=loginPageOR.loginpage_password)
	WebElement loginpassword;
	@FindBy(how=How.XPATH,xpath=loginPageOR.loginpage_submitbtn)
	WebElement login_submitbtn;
	@FindBy(how=How.XPATH, xpath=loginPageOR.loginpage_titletext)
	WebElement titletext;
	@FindBy(how=How.XPATH, xpath=loginPageOR.loginpage_errorMsg01)
	WebElement errmsg01;
		
	public GmailLogin(WebDriver driver) {
		
		this.driver=driver;
		cutil = new CommonUtilities(driver);
		PageFactory.initElements(driver, this);
	}
	
	
	public void setUserName(String username, WebElement loginusername) {
		log.debug("Setting User name : "+username);
		if(cutil.isElementPresent(loginusername)){
		loginusername.sendKeys(username);
		}else{
			log.debug("loginusername web element is not present");
		} 
	}
	
	public void setPassword(String password) {
		log.debug("Setting password : "+password);
		try{
		loginpassword.sendKeys(cutil.getDecodedData(password));
		}catch (NoSuchElementException e) {
			
			System.out.println(e);
		}
	}
	
	public GmailHome loginSuccessToGmail(String usrname ,String pwd) {
	
		setUserName(usrname, loginusername);
		
		cutil.clickElement(login_nextbtn);
		
		setPassword(pwd);
		
		cutil.clickElement(login_submitbtn);
		
		return PageFactory.initElements(driver, GmailHome.class);
		
	}
	
	public String loginFailureWithPrvPassword(String usrname ,String pwd) {
		
		setUserName(usrname, loginusername);
		
		cutil.clickElement(login_nextbtn);
		
		setPassword(pwd);
		
		cutil.clickElement(login_submitbtn);
		
		return cutil.getText(By.xpath(loginPageOR.loginpage_errorMsg01));
				
	}
public String loginFailureWithBlankUserName(String usrname ,String pwd) {
		
		setUserName(usrname, loginusername);
		
		cutil.clickElement(login_nextbtn);
		
		return cutil.getText(By.xpath(loginPageOR.loginpage_errorMsg01));
		
	}
}
