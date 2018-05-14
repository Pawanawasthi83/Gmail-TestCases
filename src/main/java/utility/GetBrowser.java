package utility;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import Config.Configuration;

public class GetBrowser {

	WebDriver driver;
	static Logger log=Logger.getLogger("devpinoyLogger");
	FileInputStream fis;
	Properties prop;
	
	String path = null;
	
public WebDriver getDriver(String Browser) {
		
		log.debug("Initializing Web Drver for :"+Browser);
		
		if(Browser.equalsIgnoreCase("FireFox")){
			driver=new FirefoxDriver();
		}else if(Browser.equalsIgnoreCase("IE")){
			path = System.getProperty("user.dir")+File.separator+Configuration.iedriverpath;
			System.setProperty("webdriver.ie.driver", path);
			driver=new InternetExplorerDriver();
		}else{
			path = System.getProperty("user.dir")+File.separator+Configuration.chromedriverpath;
			System.setProperty("webdriver.chrome.driver", path);			
			driver=new ChromeDriver();
		}
		log.debug("Web Driver Created Successfully");
		return driver;
		
	}
}
