package webSites;

public enum DatabaseTableFieldNamesEnum {

	
//	String[] databaseShareFieldNames = {"unit_price","quote_date","stock_exchange","currency_unit"};
//	String[] databaseExchangeRateFieldNames = {"quote_date","conversion_rate","currency_from_gbp","currency_to_eur"};
//	String[] databaseDollarExchangeRateFieldNames = {"quote_date","conversion_rate","currency_from_gbp","currency_to_usd"};
	
    TableFieldNames(new String[]{"unit_price","quote_date","stock_exchange","currency_unit"},new String[]{"quote_date","conversion_rate","currency_from_gbp","currency_to_eur"}, new String[]{"quote_date","conversion_rate","currency_from_gbp","currency_to_usd"});
	
    private final String[] databaseShareFieldNames;
    private final String[] databaseEuroExchangeRateFieldNames;
    private final String[] databaseDollarExchangeRateFieldNames;

    DatabaseTableFieldNamesEnum(String[] databaseShareFieldNames, String[] databaseEuroExchangeRateFieldNames, String[] databaseDollarExchangeRateFieldNames) {
        this.databaseShareFieldNames = databaseShareFieldNames;
        this.databaseEuroExchangeRateFieldNames = databaseEuroExchangeRateFieldNames;
        this.databaseDollarExchangeRateFieldNames = databaseDollarExchangeRateFieldNames;
    }

    public String[] getDatabaseShareFieldNames() {
        return databaseShareFieldNames;
    }
    public String[] getDatabaseEuroExchangeRateFieldNames() {
        return databaseEuroExchangeRateFieldNames;
    }
	
    public String[] getDatabaseDollarExchangeRateFieldNames() {
        return databaseDollarExchangeRateFieldNames;
    }
	
}

