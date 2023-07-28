package webSites;

import databaseConnection.DatabaseOperations;

public class OrangeQuoteDataStorage {

	OrangeQuote dailyStockPriceData;
	
	DatabaseOperations databaseOperations;
	
	String[] databaseFieldNames = {"unit_price","quote_date","stock_exchange","currency_unit"};
	String[] retrievedStockData = new String[4];
	
	public void sendStockDataToDatabase() {
		
		String databaseName = "accounting";
		String tableName = "webdata_orangesa_quote"; 
		String expectedCurrencySymbol = "€";
		String primaryKeyName = "quote_date";
		
		dailyStockPriceData = new OrangeQuote();
		retrievedStockData = dailyStockPriceData.getWebData("Chrome");
		databaseOperations = new DatabaseOperations();
		databaseOperations.insertDataToDatabase(databaseName, tableName, primaryKeyName, expectedCurrencySymbol, databaseFieldNames, retrievedStockData);
	}
	
}
