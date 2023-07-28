package webSites;

public enum InvestmentWebsitesEnum {

    ORANGESA(1,"Orange SA", "https://www.marketwatch.com/investing/stock/ORA?countryCode=FR","€"), 
    KEYSIGHT(2,"Keysight", "https://www.marketwatch.com/investing/stock/keys","$"), 
    AGILENT(3,"Agilent", "https://www.marketwatch.com/investing/stock/a","$"),
	RLUM(4,"RLUM","https://www.royallondon.com/existing-customers/your-products/manage-your-isa-or-unit-trust/rlum-isa-overview/fund-details/","p");
	
    private final Integer key;
    private final String shareName;
    private final String webURL;
    private final String currencySymbol;
    
    InvestmentWebsitesEnum(Integer key, String shareName, String webURL, String currencySymbol) {
        this.key = key;
        this.shareName = shareName;
        this.webURL = webURL;
        this.currencySymbol = currencySymbol;
    }

    public Integer getKey() {
        return key;
    }
    public String getShareName() {
        return shareName;
    }
	
    public String getWebURL() {
        return webURL;
    }
    
    public String getCurrency() {
        return currencySymbol;
    }
    
}
