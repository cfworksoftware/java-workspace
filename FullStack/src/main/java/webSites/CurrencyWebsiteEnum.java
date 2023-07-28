package webSites;

public enum CurrencyWebsiteEnum {

    XE(1,"XE", "https://www.xe.com/");
	
    private final Integer key;
    private final String shareName;
    private final String webURL;

    CurrencyWebsiteEnum(Integer key, String shareName, String webURL) {
        this.key = key;
        this.shareName = shareName;
        this.webURL = webURL;
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
}