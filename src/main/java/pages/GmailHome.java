package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import Config.HomePageOR;
import utility.CommonUtilities;

public class GmailHome {

	WebDriver driver;
	CommonUtilities cutil;
	static Logger log=Logger.getLogger("devpinoyLogger");
	
	@FindBy(how=How.XPATH, xpath=HomePageOR.homepage_userSetting)
	WebElement userSetting;
	@FindBy(how=How.XPATH, xpath=HomePageOR.HomePage_UserID)
	WebElement HomePageUserName;
	@FindBy(how=How.XPATH, xpath=HomePageOR.HomePage_ChangePic)
	WebElement HomePageChangePic;
	@FindBy(how=How.XPATH, xpath=HomePageOR.HomePage_BtnSelectPic)
	WebElement HomePageBtnSelectPic ;
	@FindBy(how=How.XPATH, xpath=HomePageOR.HomePage_BtnCancle)
	WebElement HomePageBtnCancle;
	@FindBy(how=How.XPATH,xpath=HomePageOR.HomePage_SignOutBtn)
	WebElement HomePageSignOut;
	@FindBy(how=How.XPATH,xpath=HomePageOR.HomePage_EditProfilePicIframeXpath)
	WebElement HomePageEditProfilePicIframe;
	@FindBy(how=How.XPATH,xpath=HomePageOR.HomePage_InboxLink)
	WebElement HomePageInboxLink;
	
	
	
	public GmailHome(WebDriver driver) {
		
		this.driver=driver;
		cutil = new CommonUtilities(driver);
		PageFactory.initElements(driver, this);
	}
	
	
	public String getHomePageUserName() {
		log.debug("Fetching user name from Home page");
		cutil.clickElement(userSetting);
		return HomePageUserName.getText();
		
	}
	
	
	public void editProfilePic() {
		
		cutil.clickElement(userSetting);
		cutil.clickElement(HomePageChangePic);
		cutil.switchToIframe(HomePageEditProfilePicIframe);
		cutil.clickElement(HomePageBtnCancle);
		
	}
	
	public void gmailSignOut() {
		
		cutil.clickElement(userSetting);
		cutil.clickElement(HomePageSignOut);
		
	}
}
