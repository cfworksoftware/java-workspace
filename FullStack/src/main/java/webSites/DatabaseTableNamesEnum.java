package webSites;

public enum DatabaseTableNamesEnum {

    ORANGESA(1,"webdata_orangesa_quote"), 
    KEYSIGHT(2,"webdata_keysight_quote"), 
    AGILENT(3,"webdata_agilent_quote"),
	RLUM(4,"webdata_rlumuk_growthincome_quote"),
    POUNDEURO(5,"webdata_currencyrate_gbp_eur"),
	POUNDDOLLAR(6,"webdata_currencyrate_gbp_usd");
	
    private final Integer key;
    private final String databaseTableName;

    DatabaseTableNamesEnum(Integer key, String databaseTableName) {
        this.key = key;
        this.databaseTableName = databaseTableName;
    }

    public Integer getKey() {
        return key;
    }
    public String getDatabaseTableName() {
        return databaseTableName;
    }	
}
