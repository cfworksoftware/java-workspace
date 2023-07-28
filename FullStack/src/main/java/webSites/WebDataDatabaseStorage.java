package webSites;

import databaseConnection.DatabaseOperations;

public class WebDataDatabaseStorage {

	KeysightQuote dailySharePriceData;
	
	DatabaseOperations databaseOperations;
	
//	String[] databaseShareFieldNames = {"unit_price","quote_date","stock_exchange","currency_unit"};
//	String[] databaseExchangeRateFieldNames = {"quote_date","conversion_rate","currency_from_gbp","currency_to_eur"};
//	String[] databaseDollarExchangeRateFieldNames = {"quote_date","conversion_rate","currency_from_gbp","currency_to_usd"};
	String databaseName;
	String tableName; 
	String expectedCurrencySymbol;
	String primaryKeyName;
	String[] retrievedData = new String[4];
	
	public void sendFinancialDataToDatabase() {
		
		databaseName = "accounting";
		tableName = "webdata_currencyrate_gbp_eur";
		//tableName = "webdata_keysight_quote"; 
		primaryKeyName = "quote_date";
		
		WebDataDailyFinancialQuoteRetrieval dailyEuroExchangeRate = new WebDataDailyFinancialQuoteRetrieval("Chrome");
		retrievedData = dailyEuroExchangeRate.getWebCurrencyData(CurrencyWebsiteEnum.XE,"GBP", "EUR");
		databaseOperations = new DatabaseOperations();
		databaseOperations.insertExchangeRateDataToDatabase(databaseName, tableName, primaryKeyName, DatabaseTableFieldNamesEnum.TableFieldNames.getDatabaseEuroExchangeRateFieldNames(), retrievedData);
			
		databaseName = "accounting";
		tableName = "webdata_currencyrate_gbp_usd";
		//tableName = "webdata_keysight_quote"; 
		primaryKeyName = "quote_date";
		
		WebDataDailyFinancialQuoteRetrieval dailyDollarExchangeRate = new WebDataDailyFinancialQuoteRetrieval("Chrome");
		retrievedData = dailyDollarExchangeRate.getWebCurrencyData(CurrencyWebsiteEnum.XE,"GBP", "USD");
		databaseOperations = new DatabaseOperations();
		databaseOperations.insertExchangeRateDataToDatabase(databaseName, tableName, primaryKeyName, DatabaseTableFieldNamesEnum.TableFieldNames.getDatabaseDollarExchangeRateFieldNames(), retrievedData);
			
		databaseName = "accounting";
		tableName = "webdata_keysight_quote"; 
		expectedCurrencySymbol = "$";
		primaryKeyName = "quote_date";
		
		WebDataDailyFinancialQuoteRetrieval keysightQuote = new WebDataDailyFinancialQuoteRetrieval("Chrome");
		retrievedData = keysightQuote.getWebShareData(InvestmentWebsitesEnum.KEYSIGHT);
		databaseOperations = new DatabaseOperations();
		databaseOperations.insertDataToDatabase(databaseName, tableName, primaryKeyName, expectedCurrencySymbol, DatabaseTableFieldNamesEnum.TableFieldNames.getDatabaseShareFieldNames(), retrievedData);
	
		databaseName = "accounting";
		tableName = "webdata_agilent_quote"; 
		expectedCurrencySymbol = "$";
		primaryKeyName = "quote_date";
		
		WebDataDailyFinancialQuoteRetrieval agilentQuote = new WebDataDailyFinancialQuoteRetrieval("Chrome");
		retrievedData = agilentQuote.getWebShareData(InvestmentWebsitesEnum.AGILENT);
		databaseOperations = new DatabaseOperations();
		databaseOperations.insertDataToDatabase(databaseName, tableName, primaryKeyName, expectedCurrencySymbol, DatabaseTableFieldNamesEnum.TableFieldNames.getDatabaseShareFieldNames(), retrievedData);
		
		databaseName = "accounting";
		tableName = "webdata_orangesa_quote"; 
		expectedCurrencySymbol = "€";
		primaryKeyName = "quote_date";
		
		WebDataDailyFinancialQuoteRetrieval orangesaQuote = new WebDataDailyFinancialQuoteRetrieval("Chrome");
		retrievedData = orangesaQuote.getWebShareData(InvestmentWebsitesEnum.ORANGESA);
		databaseOperations = new DatabaseOperations();
		databaseOperations.insertDataToDatabase(databaseName, tableName, primaryKeyName, expectedCurrencySymbol, DatabaseTableFieldNamesEnum.TableFieldNames.getDatabaseShareFieldNames(), retrievedData);
		
		databaseName = "accounting";
		tableName = "webdata_rlumuk_growthincome_quote;"; 
		expectedCurrencySymbol = "p";
		primaryKeyName = "quote_date";
		
		WebDataDailyFinancialQuoteRetrieval rlumQuote = new WebDataDailyFinancialQuoteRetrieval("Chrome");
		retrievedData = rlumQuote.getWebShareData(InvestmentWebsitesEnum.RLUM);
		databaseOperations = new DatabaseOperations();
		databaseOperations.insertDataToDatabase(databaseName, tableName, primaryKeyName, expectedCurrencySymbol, DatabaseTableFieldNamesEnum.TableFieldNames.getDatabaseShareFieldNames(), retrievedData);
		
		
	}
}
