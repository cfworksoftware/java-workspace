package webSites;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import databaseConnection.DatabaseOperations;

class AgilentQuoteDataStorageTest {

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
	void testSendStockDataToDatabase() {
		
		AgilentQuote dailyStockPriceData;
		
		DatabaseOperations databaseOperations;
		
		String[] databaseFieldNames = {"unit_price","quote_date","stock_exchange","currency_unit"};
		String[] retrievedStockData = new String[4];
		
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
