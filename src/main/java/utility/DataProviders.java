package utility;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;

public class DataProviders {

	String filepath;
	static Logger log=Logger.getLogger("devpinoyLogger");
	
	@DataProvider(name="login",parallel=false)
	public Object[][] getUserLoginData() throws IOException{
		
		filepath = System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"
				+File.separator+"resources"+File.separator+"TestData"
				+File.separator+"UserLoginCredentials.xlsx";
		ExcelDataReader reader = new ExcelDataReader(filepath);
		int row = reader.getRowCount("UserCredentials");
		int col = reader.getColumnCount("UserCredentials");
		
		Object[][] arr;
		arr=new Object[row-1][col];
		for(int i=1;i<row;i++){
			
			for(int j=0;j<col;j++){
				
				arr[i-1][j]=reader.excelReader("UserCredentials", i, j);
				
			}
	}
		return arr;
}
}
