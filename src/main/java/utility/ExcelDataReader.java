package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;



public class ExcelDataReader {

	private String path;
	private File file;
	private FileInputStream fis=null;
	private FileOutputStream fos=null ;
	private XSSFWorkbook workbook=null;
	private XSSFSheet sheet=null;
	private XSSFRow row=null;
	private XSSFCell cell=null;
	static Logger log=Logger.getLogger("devpinoyLogger");
	public ExcelDataReader(String path){
		this.path=path;
		
		try {
			file = new File(path);
			fis=new FileInputStream(file);
			workbook=new XSSFWorkbook(fis);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
	}
		
	public void excelWriter(String filepath ){
		
		file = new File(filepath);
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		workbook=new XSSFWorkbook();
		sheet = workbook.createSheet("TestDataSheet");
		
		Map<Integer, Object[]> map = new HashMap<Integer, Object[]>();
		
		map.put(1, new Object[]{"FirstName","LastName","Age"});
		map.put(2, new Object[]{"Pawan","Awsthi","21"});
		map.put(3, new Object[]{"John","Rey","22"});
		map.put(4, new Object[]{"Reyan","HQ","23"});
		map.put(5, new Object[]{"Stuwart","john","24"});
		int rowid=0;
		for (Entry<Integer, Object[]> m: map.entrySet()) {
			row = sheet.createRow(rowid++);
			
			Object[] obj = m.getValue();
			int cellid=0;
			for(Object o: obj){
				
				cell=row.createCell(cellid++);
				cell.setCellValue((String)o);
				} 
		}
		try {
			workbook.write(fos);
			System.out.println("File Created Successfully");
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		
	}
	
	public String excelReader(String sheetname, int rownum ,int colNum) throws IOException{
				
			int index=workbook.getSheetIndex(sheetname);
			sheet=workbook.getSheetAt(index);
			row=sheet.getRow(rownum);
			String cell_data =row.getCell(colNum).getStringCellValue();
			return cell_data;
	}

	public int getRowCount(String sheetname) {
		if(isSheetExist(sheetname)){
		int index = workbook.getSheetIndex(sheetname);
		sheet=workbook.getSheetAt(index);
		int rowcount = sheet.getLastRowNum()+1;
		return rowcount;
		}else{
			return -1;
		}
	}
	
	public int getColumnCount(String sheetname) {
		int index = workbook.getSheetIndex(sheetname);
		sheet=workbook.getSheetAt(index);
		row=sheet.getRow(0);
		int columncount = row.getLastCellNum();
		return columncount;
	}
	
	public boolean isSheetExist(String sheetname) {
		
		int index = workbook.getSheetIndex(sheetname);
		if(index==-1){
			return false;
		}else{
		return true;
		}
	}
	public static void main(String[] args) throws IOException, ParseException{
		//String filepath = System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"
			//	+File.separator+"resources"+File.separator+"TestData"
				//+File.separator+"UserLoginCredentials.xlsx";
		
		//ExcelDataReader reader= new ExcelDataReader(filepath);
		//reader.excelWriter(filepath);
		//String cell = reader.excelReader("UserCredentials",1,0);
		//System.out.println("Get Row Count :"+ reader.getRowCount("UserCredentials"));
		//System.out.println("Get Column Count :"+ reader.getColumnCount("UserCredentials"));
		
		//System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		GetBrowser browser=new GetBrowser();
		WebDriver driver = browser.getDriver("FireFox");
		
		HashMap<String, String> test = new CommonUtilities(driver).getTestCases();
		for(Map.Entry m:test.entrySet()){  
			   System.out.println(m.getKey()+" "+m.getValue());  
			  }  
	}
	
	
}
