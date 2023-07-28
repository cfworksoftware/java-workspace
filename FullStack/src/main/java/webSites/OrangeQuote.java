package webSites;

import org.openqa.selenium.WebDriver;

import webDriverSelenium.ConfigureSeleniumBrowserDriver;

public class OrangeQuote {

	static String orangeURL = "https://www.marketwatch.com/investing/stock/ORA?countryCode=FR";
	static String orangeName = "Orange SA";

	static String stockPriceQuoteCSSSelectorPath = "h2.intraday__price > bg-quote";
	
	String[] retrievedStockData = new String[4];
	
	WebDriver driver = null;
	ConfigureSeleniumBrowserDriver newBrowserDriver = new ConfigureSeleniumBrowserDriver();
	
	public String[] getWebData(String browserName) {
	
		if (browserName.equals("Chrome")) {
			newBrowserDriver.getChromeDriver();
			newBrowserDriver.maximiseWindow();
			driver = newBrowserDriver.getChromeDriver();
		}
		if (driver !=null) {
			newBrowserDriver.setDriverURL(orangeURL);
			
			WebDataMarketWatchShareQuotation orangeWebData = new WebDataMarketWatchShareQuotation();

			retrievedStockData = orangeWebData.getFinancialWebsiteShareQuote(driver, stockPriceQuoteCSSSelectorPath, orangeName);

			newBrowserDriver.quitDriverInstance();
			driver = null;
		}
		return retrievedStockData;
	}
	
}
