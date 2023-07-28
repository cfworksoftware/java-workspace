package webSites;

import databaseConnection.DatabaseOperations;

public class KeysightQuoteDataStorage {
	
	KeysightQuote dailySharePriceData;
	
	DatabaseOperations databaseOperations;
	
	String[] databaseFieldNames = {"unit_price","quote_date","stock_exchange","currency_unit"};
	String[] retrievedShareData = new String[4];
	
	public void sendShareDataToDatabase() {
		
		String databaseName = "accounting";
		String tableName = "webdata_keysight_quote"; 
		String expectedCurrencySymbol = "$";
		String primaryKeyName = "quote_date";
		
		dailySharePriceData = new KeysightQuote();
		retrievedShareData = dailySharePriceData.getWebData("Chrome");
		databaseOperations = new DatabaseOperations();
		databaseOperations.insertDataToDatabase(databaseName, tableName, primaryKeyName, expectedCurrencySymbol, databaseFieldNames, retrievedShareData);
	}
}
