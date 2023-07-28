package webSites;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
// import webDriverSelenium.ConfigureSeleniumBrowserDriver;
import dataFileConnection.WriteToFile;

public class WebDataMarketWatchShareQuotation {

	static String shareQuoteDateTimeCSSSelectorPath =  "div.intraday__timestamp > span.timestamp__time";
	static String currencyCSSSelectorPath = "h2.intraday__price > sup.character";
	static String marketClosedCSSSelectorPath = "h2.intraday__price > span.value";
	static String marketAfterHoursCSSSelectorPath = "h2.intraday__price > bg-quote[session = 'after']";
	static String missingData = "XX";
	static String dataStorageFileNameType = "StockPriceData.txt";
	
	WebDriver driver;
	
	public String[] getFinancialWebsiteShareQuote(WebDriver driver, String sharekPriceQuoteCSSSelectorPath, String shareName) {
		String[] retrievedShareData = new String[4];
		if (driver !=null) {
			retrievedShareData = findShareNameElementTextByCSSSelector(driver, sharekPriceQuoteCSSSelectorPath, shareName);
		}
		return retrievedShareData;
	}
	
	
	private String[] findShareNameElementTextByCSSSelector(WebDriver driver, String cssPath, String shareName) {
		String sharePriceQuote = missingData;
		String shareCurrency = missingData;
		String shareQuoteDate = missingData;
		String marketStatus = missingData;
		String[] retrievedData = new String [4];
		this.driver = driver;

		if (cssPath.contains("h2.intraday__price")){
			if (webElementPresent(marketAfterHoursCSSSelectorPath)) {
				sharePriceQuote = getWebElementText(marketAfterHoursCSSSelectorPath);
				marketStatus = "After hours";
			}
			else if (webElementPresent(cssPath)) {
				sharePriceQuote = getWebElementText(cssPath);
				marketStatus = "Open";
			}
			else if (webElementPresent(marketClosedCSSSelectorPath)) {
				sharePriceQuote = getWebElementText(marketClosedCSSSelectorPath);
				marketStatus = "Closed";
			}
			if (webElementPresent(currencyCSSSelectorPath)) {
				shareCurrency = getWebElementText(currencyCSSSelectorPath);
			}
			if (webElementPresent(shareQuoteDateTimeCSSSelectorPath)) {
				shareQuoteDate = getWebElementText(shareQuoteDateTimeCSSSelectorPath);
			}
			new WriteToFile(dataStorageFileNameType).writeDataToFile(shareName + " | Quote Price: "+ shareCurrency + sharePriceQuote +" | Quote date: " + shareQuoteDate + " | Market Status: " + marketStatus);
			
			retrievedData[0] = sharePriceQuote;
			retrievedData[1] = shareQuoteDate;
			retrievedData[2] = marketStatus;
			retrievedData[3] = shareCurrency;
		
		}
		return retrievedData;
	}
	
	public boolean webElementPresent(String cssSelectorPath) {
		if (this.driver.findElements(By.cssSelector(cssSelectorPath)).size()!=0) {
			return true;
		}
		return false;
	}
	
	public String getWebElementText(String cssSelectorPath) {
		String elementText = null;
		elementText = driver.findElement(By.cssSelector(cssSelectorPath)).getText();
		if (elementText.equalsIgnoreCase(null)) {
			elementText = missingData;
		}
		return elementText;
	}
	

	
	
}
