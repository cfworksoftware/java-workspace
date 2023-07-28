package webSites;

import databaseConnection.DatabaseOperations;

public class AgilentQuoteDataStorage {

	AgilentQuote dailyStockPriceData;
	
	DatabaseOperations databaseOperations;
	
	String[] databaseFieldNames = {"unit_price","quote_date","stock_exchange","currency_unit"};
	String[] retrievedStockData = new String[4];
	
	public void sendStockDataToDatabase() {
		
		String databaseName = "accounting";
		String tableName = "webdata_agilent_quote"; 
		String expectedCurrencySymbol = "$";
		String primaryKeyName = "quote_date";
		
		dailyStockPriceData = new AgilentQuote();
		retrievedStockData = dailyStockPriceData.getWebData("Chrome");
		databaseOperations = new DatabaseOperations();
		databaseOperations.insertDataToDatabase(databaseName, tableName, primaryKeyName, expectedCurrencySymbol, databaseFieldNames, retrievedStockData);
	}
	
}
