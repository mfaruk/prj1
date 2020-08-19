package poi;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class FacebookTest1 {
	
	WebDriverWait wait;
	static Sheet excelSheet ;
	WebDriver driver;
	
	@BeforeTest()
	public void setUp(){
		System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
		 driver=new ChromeDriver();
		driver.get("http://www.facebook.com");
		wait=new WebDriverWait(driver,20);
		}
	@Test(dataProvider="info")
	public void searchTest(String firstName,String lastName,String year)throws InterruptedException{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class,'_8fgn')]")));
		WebElement first= driver.findElement(By.xpath("//*[contains(@id,'u_0_m')]"));
		first.clear();
		first.sendKeys(firstName);
		WebElement last=driver.findElement(By.xpath("//*[contains(@id,'u_0_o')]"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class,'_8fgn')]")));
		last.clear();
		last.sendKeys(lastName);
		WebElement dob=driver.findElement(By.xpath("//*[contains(@name,'birthday_year')]"));
		Select se=new Select(dob);
		se.selectByVisibleText(year);
		
	}
	@DataProvider(name="info")
	public Object[][] dataFeeder() throws IOException{

	Object[][] tabArray;
	String filePath = "data/Facebook.xlsx";
	FileInputStream excelFileRead = new FileInputStream(filePath);
	Workbook excelWBook = new XSSFWorkbook(excelFileRead);
	 excelSheet = excelWBook.getSheet("Sheet1");

	int totalRows = excelSheet.getPhysicalNumberOfRows();//4
	System.out.println(totalRows);
	int totalCols = excelSheet.getRow(0).getPhysicalNumberOfCells();//3
	System.out.println(totalCols);
	
	//t[row][col]
	// deduct one row because of header row
	tabArray = new Object[totalRows-1][totalCols]; //3X2

	for(int row=1; row<totalRows; row++) {
	for(int col=0; col<totalCols; col++) {
	// add row+1 to get data after header row
	tabArray[row-1][col] = getCellData(row, col);

	}
	}
	
	return tabArray;
	}
	public static String getCellData(int row, int col) {
      Cell cell = excelSheet.getRow(row).getCell(col);

	//String cellData = cell.getStringCellValue().trim();
	if(cell.getCellType()==Cell.CELL_TYPE_STRING){
		String stringValue=cell.getStringCellValue();
		return stringValue;
	}
     else if(cell.getCellType()==cell.CELL_TYPE_NUMERIC){
	   String numaricValue= String.valueOf((int)cell.getNumericCellValue());
             return numaricValue;
  }
	//convert to string by using switch statement.
	//i added this line.
	// we are updateing more line.
	//bla bla bla.
	return null;
	
}
}
