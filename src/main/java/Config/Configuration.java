package Config;

import java.io.File;

public class Configuration {

	//Configuration
	public static String testUrl = "http://www.gmail.com";
	public static String chromedriverpath ="src"+File.separator+"main"+File.separator+"resources"+File.separator
			+ "Drivers"+File.separator+"chromedriver_win32 2.25"+File.separator+"chromedriver.exe";
	public static String iedriverpath ="src"+File.separator+"main"+File.separator+"resources"+File.separator
			+ "Drivers"+File.separator+"IEDriverServer_x64_2.53.0"+File.separator+"IEDriverServer.exe";
	public static String configpath ="src"+File.separator+"main"+File.separator+"resources"+File.separator
			+ "TestData"+File.separator+"Config.properties";
	public static String testdatapath = System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"
			+File.separator+"resources"+File.separator+"TestData"
			+File.separator+"UserLoginCredentials.xlsx";
	
		
}
