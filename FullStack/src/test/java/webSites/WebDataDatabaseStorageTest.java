package webSites;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import databaseConnection.DatabaseOperations;

class WebDataDatabaseStorageTest {

	DatabaseOperations databaseOperations;
	
	String[] databaseShareFieldNames = {"unit_price","quote_date","stock_exchange","currency_unit"};
	String[] databaseEuroExchangeRateFieldNames = {"quote_date","conversion_rate","currency_from_gbp","currency_to_eur"};
	String[] databaseDollarExchangeRateFieldNames = {"quote_date","conversion_rate","currency_from_gbp","currency_to_usd"};
	String databaseName;
	String tableName; 
	String primaryKeyName;
	String expectedCurrencySymbol;
	String[] retrievedData = new String[4];
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void keysightTest() {

		databaseName = "accounting";
		primaryKeyName = "quote_date";
		
		//Loop here through enum
		for(InvestmentWebsitesEnum test: InvestmentWebsitesEnum.values()) {
			WebDataDailyFinancialQuoteRetrieval dailyQuote = new WebDataDailyFinancialQuoteRetrieval("Chrome");
			if (test.name().toString().equalsIgnoreCase("RLUM")){
				retrievedData = dailyQuote.getWebRLUMData(InvestmentWebsitesEnum.RLUM);
			}
			else {
			retrievedData = dailyQuote.getWebShareData(InvestmentWebsitesEnum.valueOf(test.name()));
			}
			databaseOperations = new DatabaseOperations();
			tableName = DatabaseTableNamesEnum.valueOf(test.name()).getDatabaseTableName();
			expectedCurrencySymbol = InvestmentWebsitesEnum.valueOf(test.name()).getCurrency();
			databaseOperations.insertDataToDatabase(databaseName, tableName, primaryKeyName, expectedCurrencySymbol, databaseShareFieldNames, retrievedData);
		}
		
		databaseName = "accounting";
		tableName = "webdata_keysight_quote"; 
		expectedCurrencySymbol = "$";
		primaryKeyName = "quote_date";
		
		WebDataDailyFinancialQuoteRetrieval keysightQuote = new WebDataDailyFinancialQuoteRetrieval("Chrome");
		retrievedData = keysightQuote.getWebShareData(InvestmentWebsitesEnum.KEYSIGHT);
		databaseOperations = new DatabaseOperations();
		databaseOperations.insertDataToDatabase(databaseName, tableName, primaryKeyName, expectedCurrencySymbol, databaseShareFieldNames, retrievedData);
	
		databaseName = "accounting";
		tableName = "webdata_agilent_quote"; 
		expectedCurrencySymbol = "$";
		primaryKeyName = "quote_date";
		
		WebDataDailyFinancialQuoteRetrieval agilentQuote = new WebDataDailyFinancialQuoteRetrieval("Chrome");
		retrievedData = agilentQuote.getWebShareData(InvestmentWebsitesEnum.AGILENT);
		databaseOperations = new DatabaseOperations();
		databaseOperations.insertDataToDatabase(databaseName, tableName, primaryKeyName, expectedCurrencySymbol, databaseShareFieldNames, retrievedData);
	
		databaseName = "accounting";
		tableName = "webdata_orangesa_quote"; 
		expectedCurrencySymbol = "€";
		primaryKeyName = "quote_date";
		
		WebDataDailyFinancialQuoteRetrieval orangesaQuote = new WebDataDailyFinancialQuoteRetrieval("Chrome");
		retrievedData = orangesaQuote.getWebShareData(InvestmentWebsitesEnum.ORANGESA);
		databaseOperations = new DatabaseOperations();
		databaseOperations.insertDataToDatabase(databaseName, tableName, primaryKeyName, expectedCurrencySymbol, databaseShareFieldNames, retrievedData);
		
	}

	@Test
	void rlumTest() {	
		databaseName = "accounting";
		tableName = "webdata_rlumuk_growthincome_quote"; 
		expectedCurrencySymbol = "p";
		primaryKeyName = "quote_date";
		
		WebDataDailyFinancialQuoteRetrieval rlumQuote = new WebDataDailyFinancialQuoteRetrieval("Chrome");
		retrievedData = rlumQuote.getWebRLUMData(InvestmentWebsitesEnum.RLUM);
		databaseOperations = new DatabaseOperations();
		databaseOperations.insertDataToDatabase(databaseName, tableName, primaryKeyName, expectedCurrencySymbol, databaseShareFieldNames, retrievedData);
	}
	
	@Test
	void euroRateTest() {	
		databaseName = "accounting";
		tableName = "webdata_currencyrate_gbp_eur";
		primaryKeyName = "quote_date";
		
		WebDataDailyFinancialQuoteRetrieval dailyExchangeRate = new WebDataDailyFinancialQuoteRetrieval("Chrome");
		retrievedData = dailyExchangeRate.getWebCurrencyData(CurrencyWebsiteEnum.XE,"GBP", "EUR");
		databaseOperations = new DatabaseOperations();
		databaseOperations.insertExchangeRateDataToDatabase(databaseName, tableName, primaryKeyName, databaseEuroExchangeRateFieldNames, retrievedData);
	}
	
	@Test
	void dollarRateTest() {	
		databaseName = "accounting";
		tableName = "webdata_currencyrate_gbp_usd";
		primaryKeyName = "quote_date";
		
		WebDataDailyFinancialQuoteRetrieval dailyExchangeRate = new WebDataDailyFinancialQuoteRetrieval("Chrome");
		retrievedData = dailyExchangeRate.getWebCurrencyData(CurrencyWebsiteEnum.XE,"GBP", "USD");
		databaseOperations = new DatabaseOperations();
		databaseOperations.insertExchangeRateDataToDatabase(databaseName, tableName, primaryKeyName, databaseDollarExchangeRateFieldNames, retrievedData);
	}
	
	@Test
	void enumMapping() {
		String currencySymbol = InvestmentWebsitesEnum.ORANGESA.getCurrency();
		System.out.println(InvestmentWebsitesEnum.valueOf("ORANGESA").getShareName());
		InvestmentWebsitesEnum dummy = InvestmentWebsitesEnum.KEYSIGHT;
		
		System.out.println(dummy.name());
		
		for(InvestmentWebsitesEnum test: InvestmentWebsitesEnum.values()) {
			assertDoesNotThrow( ()->DatabaseTableNamesEnum.valueOf(test.name()));
//			assertNotEquals(DatabaseTableNamesEnum.valueOf(test.name()),null,test.name()+"does not exist as a table");
		}	
		System.out.println("By index: " + InvestmentWebsitesEnum.values()[0]);
	}
	
//	@Test
	void enumReverseMapping() {
		for(DatabaseTableNamesEnum test: DatabaseTableNamesEnum.values()) {
			assertDoesNotThrow(()->InvestmentWebsitesEnum.valueOf(test.name()));
//			assertNotEquals(DatabaseTableNamesEnum.valueOf(test.name()),null,test.name()+"does not exist as a table");
		}	
	}
	
	@Test
	void missingValueThrowsIllegalArgumentException() {
	    assertThrows(IllegalArgumentException.class, () -> DatabaseTableNamesEnum.valueOf("XXXX"));
//	    assertDoesNotThrow(()->DatabaseTableNamesEnum.valueOf("RLUM"));
	}
}

