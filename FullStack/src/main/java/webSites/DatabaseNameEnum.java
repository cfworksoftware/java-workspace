package webSites;

public enum DatabaseNameEnum {
    FINANCIALDATABASE(1,"accounting");
	
    private final Integer key;
    private final String databaseName;

    DatabaseNameEnum(Integer key, String databaseName) {
        this.key = key;
        this.databaseName = databaseName;
    }

    public Integer getKey() {
        return key;
    }
    public String getDatabaseName() {
        return databaseName;
    }	
}
