package webSites;

import org.openqa.selenium.WebDriver;

import webDriverSelenium.ConfigureSeleniumBrowserDriver;
//import databaseConnection.DatabaseOperations;

public class KeysightQuote {

	static String keysightURL = "https://www.marketwatch.com/investing/stock/keys";
	static String keysightName = "Keysight";
	
//	String[] databaseFieldNames = {"unit_price","quote_date","stock_exchange","currency_unit"};
//	String databaseName = "accounting";
//	String tableName = "webdata_keysight_quote"; 
//	String expectedCurrencySymbol = "$";
//	String primaryKeyName = "quote_date";
	static String sharePriceQuoteCSSSelectorPath = "h2.intraday__price > bg-quote";
	
	String[] retrievedShareData = new String[4];
	
	WebDriver driver = null;
	ConfigureSeleniumBrowserDriver newBrowserDriver = new ConfigureSeleniumBrowserDriver();
	
	public String[] getWebData(String browserName) {
	
		if (browserName.equals("Chrome")) {
			newBrowserDriver.getChromeDriver();
			newBrowserDriver.maximiseWindow();
			driver = newBrowserDriver.getChromeDriver();
		}
		if (driver !=null) {
			newBrowserDriver.setDriverURL(keysightURL);
			
		//	retrievedStockData = findStockNameElementTextByCSSSelector(driver, stockPriceQuoteCSSSelectorPath, stockName);
			WebDataMarketWatchShareQuotation keysightWebData = new WebDataMarketWatchShareQuotation();
//			DatabaseOperations writeKeysightData = new DatabaseOperations();
			retrievedShareData = keysightWebData.getFinancialWebsiteShareQuote(driver, sharePriceQuoteCSSSelectorPath, keysightName);
//			writeKeysightData.insertDataToDatabase(databaseName, tableName, primaryKeyName, expectedCurrencySymbol, databaseFieldNames, retrievedStockData);
			newBrowserDriver.quitDriverInstance();
			driver = null;
		}
		return retrievedShareData;
	}

}
