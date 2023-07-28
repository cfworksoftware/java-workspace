package webSites;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import java.io.FileWriter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.time.Duration;

import javax.sql.DataSource;
 
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

//import org.junit.Test;
//import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.locators.RelativeLocator;

//import com.jcg.jdbc.connection.pooling.ConnectionPool;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import databaseConnection.ConfigureDatabaseConnectionPool;
import dataFileConnection.WriteToFile;
import webDriverSelenium.ConfigureSeleniumBrowserDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.util.concurrent.TimeUnit;

public class StockMarketShares {

//	static String keysightName = "Keysight";
//	static String agilentName = "Agilent";
//	static String orangeName = "Orange SA";
//	static String keysightURL = "https://www.marketwatch.com/investing/stock/keys";
//	static String agilentURL = "https://www.marketwatch.com/investing/stock/a";
//	static String orangeURL = "https://www.marketwatch.com/investing/stock/ORA?countryCode=FR";
//	static String xeURL = "https://www.xe.com/";	
	static String rlumURL = "https://www.royallondon.com/existing-customers/your-products/manage-your-isa-or-unit-trust/rlum-isa-overview/fund-details/";
//	static String currencyCSSSelectorPath = "h2.intraday__price > sup.character";
//	static String stockPriceQuoteCSSSelectorPath = "h2.intraday__price > bg-quote";
//	static String stockQuoteDateTimeCSSSelectorPath =  "div.intraday__timestamp > span.timestamp__time";
//	static String marketClosedCSSSelectorPath = "h2.intraday__price > span.value";
//	static String marketAfterHoursCSSSelectorPath = "h2.intraday__price > bg-quote[session = 'after']";
//	static String missingData = "XX";
//	static String dataStorageFileNameType = "StockPriceData.txt";

	String formattedDate;
	ConfigureDatabaseConnectionPool connPoolInstance; 
	DataSource dataSource;
	
	private TestInfo testInfo;

/*	public String[] findStockNameElementTextByCSSSelector(WebDriver driver, String cssPath, String stockName) {
		String stockPriceQuote = missingData;
		String stockCurrency = missingData;
		String stockQuoteDate = missingData;
		String marketStatus = missingData;
		String[] retrievedData = new String [4];

		if (cssPath.contains("h2.intraday__price")){
			if (driver.findElements(By.cssSelector(marketAfterHoursCSSSelectorPath)).size()!=0) {
				stockPriceQuote = driver.findElement(By.cssSelector(cssPath)).getText();
				marketStatus = "After hours";
			}
			else if (driver.findElements(By.cssSelector(cssPath)).size()!=0) {
				stockPriceQuote = driver.findElement(By.cssSelector(cssPath)).getText();
				marketStatus = "Open";
			}
			else if (driver.findElements(By.cssSelector(marketClosedCSSSelectorPath)).size()!=0) {
				stockPriceQuote = driver.findElement(By.cssSelector(marketClosedCSSSelectorPath)).getText();
				marketStatus = "Closed";
			}
			if (driver.findElements(By.cssSelector(currencyCSSSelectorPath)).size()!=0) {
				stockCurrency = driver.findElement(By.cssSelector(currencyCSSSelectorPath)).getText();
			}
			if (driver.findElements(By.cssSelector(stockQuoteDateTimeCSSSelectorPath)).size()!=0) {
				stockQuoteDate = driver.findElement(By.cssSelector(stockQuoteDateTimeCSSSelectorPath)).getText();
			}
			new WriteToFile(dataStorageFileNameType).writeDataToFile(stockName + " | Quote Price: "+ stockCurrency + stockPriceQuote +" | Quote date: " + stockQuoteDate + " | Market Status: " + marketStatus);
			
			retrievedData[0] = stockPriceQuote;
			retrievedData[1] = stockQuoteDate;
			retrievedData[2] = marketStatus;
			retrievedData[3] = stockCurrency;
		
		}
		return retrievedData;
	}*/
	
	public void storeStockPrice(String dailyValue) {
		System.out.println("Value: "+ dailyValue);
	}

/*	public String[] getFinancialWebsiteStockQuote(String browserName, String webURL, String stockPriceQuoteCSSSelectorPath, String stockName) {
		WebDriver driver = null;
		ConfigureSeleniumBrowserDriver newBrowserDriver = new ConfigureSeleniumBrowserDriver();
		String[] retrievedStockData = new String[4];
		if (browserName.equals("Chrome")) {
			newBrowserDriver.getChromeDriver();
			newBrowserDriver.maximiseWindow();
			driver = newBrowserDriver.getChromeDriver();
		}
		if (driver !=null) {
			newBrowserDriver.setDriverURL(webURL);
			retrievedStockData = findStockNameElementTextByCSSSelector(driver, stockPriceQuoteCSSSelectorPath, stockName);

			newBrowserDriver.quitDriverInstance();
			driver = null;
		}
		return retrievedStockData;
	}
	*/
	/*
	public void insertDataToDatabase(String databaseName, String tableName, String primaryKeyName, String expectedCurrencySymbol, String[] databaseFieldNames, String[] databaseFieldValues){
		connPoolInstance = ConfigureDatabaseConnectionPool.getInstance();
		try {
			if (dataSource == null) {
			dataSource = ConfigureDatabaseConnectionPool.setUpPool();
			}
			formatDate(databaseFieldValues[1]);
			boolean test = checkPrimaryKeyNotTaken(databaseName, tableName, primaryKeyName,formattedDate);
			System.out.println("Test:" + test);
			if (test)
			{
				ResultSet rsObj = null;
		        Connection connObj = null;
		        PreparedStatement pstmtObj = null;
		        try {   
		        	connPoolInstance.printConnectionPoolStatus();
		            System.out.println("\n=====Making A New Connection Object For Db Transaction=====\n");
		            connObj = dataSource.getConnection();
		            connPoolInstance.printConnectionPoolStatus();
		            String databaseQuery = insertDataQuery(databaseName, tableName, primaryKeyName, expectedCurrencySymbol, databaseFieldNames, databaseFieldValues);
		            pstmtObj = connObj.prepareStatement(databaseQuery);
		            pstmtObj.execute();
		       //     need to capture exception: java.sql.SQLIntegrityConstraintViolationException
		            System.out.println("\n=====Releasing Connection Object To Pool=====\n");   
				}  catch(Exception sqlException) {
		            sqlException.printStackTrace();
		        } 
		        finally {
		            try {
		            	connPoolInstance.printConnectionPoolStatus();
		                pstmtObj = connObj.prepareStatement("SELECT * FROM accounting.webdata_rlumuk_growthincome_quote");
		                rsObj = pstmtObj.executeQuery();
		                while (rsObj.next()) {
		                    System.out.println("Returned data" + rsObj.toString());
		                }
		                System.out.println("\n=====Again Releasing Connection Object To Pool=====\n");   
		            //	connPoolInstance.closePool();
		                connObj.close();
		            } catch(Exception sqlException) {
		                sqlException.printStackTrace();
		            }
		        }
		        connPoolInstance.printConnectionPoolStatus();
		        } else {
		    	System.out.println("Failed to write date entry");
		    }
		}
        catch (ClassNotFoundException e) {
			e.printStackTrace();
        }
	}
*/
	/*
	public Boolean checkPrimaryKeyNotTaken(String databaseName, String tableName, String primaryKeyName, String primaryKeyValue) {
		boolean primaryKeyAvailable = false;
		String databaseQuery = "SELECT *" + " FROM "+ databaseName + "." + tableName + " WHERE " 
		+ primaryKeyName + " = " + "'" + primaryKeyValue + "';";
		//		SELECT quote_date FROM accounting.webdata_rlumuk_growthincome WHERE quote_date = '"+ formattedDate +"';";
		System.out.println(databaseQuery);
		ResultSet rsObj = null;
        Connection connObj = null;
        PreparedStatement pstmtObj = null;
        try {   
//            DataSource dataSource = setUpPool();
        	connPoolInstance.printConnectionPoolStatus();
            System.out.println("\n=====Making A New Connection Object For Db Transaction=====\n");
            connObj = dataSource.getConnection();
            connPoolInstance.printConnectionPoolStatus(); 
            
            pstmtObj = connObj.prepareStatement(databaseQuery,ResultSet.TYPE_SCROLL_SENSITIVE, 
                    ResultSet.CONCUR_UPDATABLE);
            rsObj = pstmtObj.executeQuery();
 //           System.out.println("rsObj is: " + rsObj.getString("quote_date"));
            if (!rsObj.next()) 
            {
	      //      System.out.println("rsObj is: " + rsObj.getString("quote_date"));
	         //   if (!rsObj.getString("quote_date").equals(primaryKeyValue)) {
	            	primaryKeyAvailable = true;
	            	System.out.println("No result set found");
	           }
               
            else
            {
            	System.out.println("Entering else statement");
            /*	rsObj.beforeFirst();
            	while (rsObj.next()) 
            	{*/
/*            	int cnt=1;
            	do {
	            	System.out.println("evaluating result set" + cnt++);
	   	           	if (rsObj.getString("quote_date").equals(primaryKeyValue)) 
	   	           	{
	   	        	   primaryKeyAvailable = false;
	   	        	   System.out.println("Tuple found");
	   	        	   break;
	   	           	}
            	} while (rsObj.next());
            }
            connObj.close();
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
   //     connPoolInstance.closePool();
        connPoolInstance.printConnectionPoolStatus();
        System.out.println("primary key available" + primaryKeyAvailable);
       
		return primaryKeyAvailable;
	} 
	*/
	
/*	public void formatDate(String currentDateForm) {
		//Assume date is in either DD/MM/YYYY or American system with spaces MMM DD YYYY need to convert to YYYY-MM-DD
		String[] arrOfStr = null; 
		
		if (currentDateForm.contains("/")){
			System.out.println("Identified date format with '/'");
			arrOfStr = currentDateForm.split("/", 3);
			formattedDate = arrOfStr[2] + "-" + arrOfStr[1]+ "-" + arrOfStr[0];
		}
		else if (currentDateForm.contains(" ")){
			System.out.println("Identified date format with spaces");
			arrOfStr = currentDateForm.split(" ", 8);
			String monthCnt = null;
			switch(arrOfStr[2])
			{
			case("Jan"):
				monthCnt = "01";
				break;
			case("Feb"):
				monthCnt = "02";
				break;
			case("Mar"):
				monthCnt = "03";
				break;
			case("Apr"):
				monthCnt = "04";
				break;
			case("Mai"):
				monthCnt = "05";
				break;
			case("Jun"):
				monthCnt = "06";
				break;
			case("Jul"):
				monthCnt = "07";
				break;
			case("Aug"):
				monthCnt = "08";
				break;
			case("Sep"):
				monthCnt = "09";
				break;
			case("Oct"):
				monthCnt = "10";
				break;
			case("Nov"):
				monthCnt = "11";
				break;
			case("Dec"):
				monthCnt = "12";
				break;
			default:
				monthCnt = "00";
				break;
			}
			formattedDate = arrOfStr[4]+ "-" + monthCnt + "-" + arrOfStr[3].replace(",", "");
		}
        for (String a : arrOfStr)
            System.out.println(a);
//		int lengthDate = currentDateForm.length();
//		formattedDate  = currentDateForm;
		System.out.println("Format Date:" + formattedDate);
		}
	*/
/*
	public String insertDataQuery(String databaseName, String tableName, String primaryKeyName, String expectedCurrencySymbol, String[] dataFieldName, String[] dataValueColumn) {
//System.out.println("dataValueColumn[0]"+dataValueColumn[0]);
		String unitValue="XX";
		String currencySymbol="XX";
		if (tableName.equals("webdata_rlumuk_growthincome_quote")) {
		int lengthUnitPrice = dataValueColumn[0].length();
		boolean checkCurrencyUnit = dataValueColumn[0].contains(expectedCurrencySymbol);
		boolean checkDecimalPoint = dataValueColumn[0].contains(".");
		int currencyIndex = dataValueColumn[0].indexOf(expectedCurrencySymbol);
		int decpointIndex =dataValueColumn[0].indexOf(".");
		unitValue = dataValueColumn[0].substring(0,currencyIndex);
		dataValueColumn[3] = dataValueColumn[0].substring(currencyIndex);
		dataValueColumn[0] = unitValue;
		}
*/
       /*
	if (dataValueColumn.length ==4) {
		unitValue = dataValueColumn[0];
		currencySymbol = dataValueColumn[3];
	}
	*/
	//	System.out.println(lengthUnitPrice +";" + Boolean.toString(checkDecimalPoint) +";" + Boolean.toString(checkDecimalPoint)+";" + currencyIndex +";" + decpointIndex);
/*		
		formatDate(dataValueColumn[1]);

		int lengthStockExchange =  dataValueColumn[2].length();
		
		String insertData = null;
		
//		if (dataValueColumn.length ==4) {
		insertData = "INSERT INTO "+ databaseName + "." + tableName  
				+ "(" + dataFieldName[0] + "," + dataFieldName[1] + "," + dataFieldName[2] + "," + dataFieldName[3] + ") VALUES ('" 
				+ dataValueColumn[0] + "','" + formattedDate + "','" + dataValueColumn[2] + "','"+ dataValueColumn[3] +"');";	
// (unit_price,quote_date,stock_exchange,currency_unit)		
//		}
/*		else {
			insertData = "INSERT INTO "+ databaseName + "." + tableName  
					+ "(" + dataFieldName[0] + "," + dataFieldName[1] + "," + dataFieldName[2] + ") VALUES ('" 
					+ unitValue + "','" + formattedDate + "','" + currencySymbol + "');";	
		}
		*/
/*		formattedDate = "";
		System.out.println(insertData);
		return insertData;
	}
*/	
	
	ConfigureSeleniumBrowserDriver configureSeleniumBrowserDriver = null;
	WebDriver webDriver = null;
	
	@BeforeEach
	void setUp() {
		configureSeleniumBrowserDriver = new ConfigureSeleniumBrowserDriver();
		webDriver = configureSeleniumBrowserDriver.getChromeDriver();
	}
	
	@BeforeEach
	void init(TestInfo testInfo) {
	    this.testInfo = testInfo;
	}
	
	@Test
	public void browserStarts() throws InterruptedException { 
		System.out.println("displayName = " + testInfo.getDisplayName());
		String browserName = "Chrome";
		String webURL =  "https://www.google.com/";
		String checkWebURL = "https://www.google.com/"; //
		
		if (configureSeleniumBrowserDriver != null) {
				for (int cnt=1 ; cnt<6 ; cnt++) {
					System.out.println("browser test:" + cnt);
					if (configureSeleniumBrowserDriver.getCurrentInstanceDriver() != null) {
		//				System.out.println("Quitting current instance of chrome");
						configureSeleniumBrowserDriver.quitDriverInstance();
		//				System.out.println("After quitting current instance of chrome" + configureSeleniumBrowserDriver.getCurrentInstanceDriver());			
					}
					if ((browserName == "Chrome") && (configureSeleniumBrowserDriver.getCurrentInstanceDriver() == null)) {
					//	configureSeleniumBrowserDriver = new ConfigureSeleniumBrowserDriver();
		//				System.out.println("Generating headless chrome instance");
					//	configureSeleniumBrowserDriver = new ConfigureSeleniumBrowserDriver();
						webDriver = configureSeleniumBrowserDriver.getHeadlessChromeDriver();
		//				System.out.println("Reassigned webDriver variable"+webDriver.getWindowHandles());
					} 
					if ((configureSeleniumBrowserDriver !=null) && (webDriver != null)) {
		//				System.out.println("Setting webURL");
						configureSeleniumBrowserDriver.setDriverURL(webURL);
						String currentURL = configureSeleniumBrowserDriver.getBrowserURL();
		//				System.out.println("Headless Browser URL: " + currentURL);
						assertEquals(checkWebURL,currentURL,"Current URL is not that assigned");
						if (checkWebURL.equals(currentURL)) {
							break;
						}
				//		driver.checkBrowserURL(checkWebURL);
					/*	if (driver.checkBrowserURL(checkWebURL))
						{
							driver.quitDriverInstance();
					//		break;
						}
						driver.quitDriverInstance();	
				*/	}
					Thread.sleep(2000);
				}
		}
	//	configureSeleniumBrowserDriver.quitDriverInstance();

	}
	
	@AfterEach
	void tearDown() {
		if (!configureSeleniumBrowserDriver.hasQuit()) {
			configureSeleniumBrowserDriver.quitDriverInstance();
		}
	}
	
//	@Test   
/*	public void testGoogleSearch() throws InterruptedException {   
		String[] databaseFieldNames = {"unit_price","quote_date","stock_exchange","currency_unit"};
		String[] retrievedStockData = new String[4];
		String databaseName = "accounting";
		String tableName = "webdata_keysight_quote"; 
		String expectedCurrencySymbol = "$";
		String primaryKeyName = "quote_date";
		retrievedStockData = getFinancialWebsiteStockQuote("Chrome", keysightURL, stockPriceQuoteCSSSelectorPath, keysightName);
		insertDataToDatabase(databaseName, tableName, primaryKeyName, expectedCurrencySymbol, databaseFieldNames, retrievedStockData);
		tableName = "webdata_agilent_quote"; 
		expectedCurrencySymbol = "$";
		retrievedStockData = getFinancialWebsiteStockQuote("Chrome", agilentURL, stockPriceQuoteCSSSelectorPath, agilentName);
		insertDataToDatabase(databaseName, tableName, primaryKeyName, expectedCurrencySymbol, databaseFieldNames, retrievedStockData);
		expectedCurrencySymbol = "€";
		tableName = "webdata_orangesa_quote"; 
		retrievedStockData = getFinancialWebsiteStockQuote("Chrome", orangeURL, stockPriceQuoteCSSSelectorPath, orangeName);
		insertDataToDatabase(databaseName, tableName, primaryKeyName, expectedCurrencySymbol, databaseFieldNames, retrievedStockData);
	}
	
//	@Test
	public void testCurrencySite()  throws InterruptedException {		
		ConfigureSeleniumBrowserDriver newBrowserDriver = new ConfigureSeleniumBrowserDriver();
		newBrowserDriver.getChromeDriver();
		newBrowserDriver.maximiseWindow();
		newBrowserDriver.setDriverURL(xeURL);
		WebDriver driver = 	newBrowserDriver.getChromeDriver();

		List<WebElement> options = driver.findElements(By.cssSelector("button"));
		int cnt =0;
		for (WebElement opt : options) {
		    if (opt.getText().equals("Accept")) {
		        opt.click();
		        break;
		    }
		}
		
		driver.findElement(By.cssSelector("#midmarketFromCurrency")).click();
		driver.findElement(By.cssSelector("input#midmarketFromCurrency")).click();
		driver.findElement(By.cssSelector("input#midmarketFromCurrency")).sendKeys("GBP");
		driver.findElement(By.cssSelector("input#midmarketFromCurrency")).sendKeys(Keys.ENTER);
		driver.findElement(By.cssSelector("#midmarketToCurrency")).click();
		driver.findElement(By.cssSelector("input#midmarketToCurrency")).click();
		driver.findElement(By.cssSelector("input#midmarketToCurrency")).sendKeys("EUR");
		driver.findElement(By.cssSelector("input#midmarketToCurrency")).sendKeys(Keys.ENTER);
		
		options = driver.findElements(By. cssSelector("button"));
		cnt =0;
		for (WebElement opt : options) {
		    if (opt.getText().equals("Convert")) {
		        opt.click();
		        break;  
		    }
		}
		Thread.sleep(5000);
		By currencyLocator = RelativeLocator.with(By.tagName("p")).near(By.cssSelector("span.faded-digits"));
		WebElement p=driver.findElement(By.cssSelector("span.faded-digits"));
		WebElement t= p.findElement(By.xpath("parent::*"));
		
		//check conversion currencies correct
		
		new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("p[class^='result__ConvertedText']")));
		
	//	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'COMPOSE')]")));

		String fromCurrency = driver.findElement(By.cssSelector("p[class^='result__ConvertedText']")).getText();
		System.out.println("From Currency:" + fromCurrency);
		assertTrue(fromCurrency.contains("British Pound"),()->"British Pound");		
		String toCurrency = driver.findElement(By.cssSelector("div[class^='unit-rates'] > p")).getText();
		System.out.println("To Currency:" + toCurrency);
		assertTrue(toCurrency.contains("EUR"),()->"Euros");		
		newBrowserDriver.quitDriverInstance();
		driver = null;
	}
	
//	@Test
	public void testRLUMSite() throws InterruptedException {	
		String funds = "table.fe-datatable > tbody.fe-fundtableBody > td.pdfableColumn.fe-column-name";		
		String databaseName = "accounting";
		String tableName = "webdata_rlumuk_growthincome_quote"; 
		String primaryKeyName = "quote_date";
		String expectedCurrencySymbol = "p";
		
		ConfigureSeleniumBrowserDriver newBrowserDriver = new ConfigureSeleniumBrowserDriver();
		WebDriver driver = newBrowserDriver.getChromeDriver();
		newBrowserDriver.maximiseWindow();
		newBrowserDriver.setDriverURL(rlumURL);
		Thread.sleep(2000);
		driver.findElement(By.cssSelector("button#onetrust-accept-btn-handler")).click();
		Thread.sleep(2000);
		WebElement fundRow = null;
		System.out.println("table");
		driver.switchTo().frame(driver.findElement(By.cssSelector("iFrame[title = 'iFrame Content']")));
		List<WebElement>  options = driver.findElements(By.cssSelector("tbody.fe-fund-tableBody")); //table.fe-datatable>tbody.fe-fund-tableBody>
		WebElement data = options.get(1);
		List<WebElement>  rows = data.findElements(By.cssSelector("tr"));
		System.out.println("List size: " + options.size());
		int cnt =0;
		for (WebElement row : rows) {
			System.out.println("List item " + cnt++ +" :"+ row.getText());
		}
		cnt =0;
		for (WebElement row : rows) {
			System.out.println("List item " + cnt++ +" :"+ row.getText());
			if ((row.getText()).contains("Royal London UK Income With Growth Trust Inc")) {
		    	fundRow = row;
		        break;	
			}
		}
		List<WebElement>  columns = fundRow.findElements(By.cssSelector("td[class='pdfableColumn']")); // if use td.pdfableColumn returns all classes with that text in it
		int colcnt =0;
		String[] dataColumn = new String [4];
		for (WebElement column : columns) {	
			System.out.println("List column " + colcnt +" :"+ column.getText());
			dataColumn[colcnt++] = column.getText();
		}
	//	insertDataToDatabase(String databaseName, String tableName, String primaryKeyName, String[] databaseFieldNames, String[] databaseFieldValues)
		String[] dataFieldName = {"unit_price","quote_date","stock_exchange","currency_unit"};
		insertDataToDatabase(databaseName, tableName, primaryKeyName, expectedCurrencySymbol, dataFieldName, dataColumn);
		newBrowserDriver.quitDriverInstance();		
	} */
}
