package webSites;

import org.openqa.selenium.WebDriver;

import webDriverSelenium.ConfigureSeleniumBrowserDriver;

public class AgilentQuote {

	static String agilentURL = "https://www.marketwatch.com/investing/stock/a";
	static String agilentName = "Agilent";
	
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
			newBrowserDriver.setDriverURL(agilentURL);

			WebDataMarketWatchShareQuotation agilentWebData = new WebDataMarketWatchShareQuotation();

			retrievedStockData = agilentWebData.getFinancialWebsiteShareQuote(driver, stockPriceQuoteCSSSelectorPath, agilentName);

			newBrowserDriver.quitDriverInstance();
			driver = null;
		}
		return retrievedStockData;
	}
	
}
