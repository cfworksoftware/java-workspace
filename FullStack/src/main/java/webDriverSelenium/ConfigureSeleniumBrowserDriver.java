package webDriverSelenium;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ConfigureSeleniumBrowserDriver {

	private String driverBrowserType = "webdriver.chrome.driver";
	private String driverLocation = "C:\\Users\\colinf\\Documents\\SoftwareProgramming\\ANewMaterial\\Selenium\\chromedriver_win32\\chromedriver.exe";
	private WebDriver driver = null;
	
	public ConfigureSeleniumBrowserDriver() {
		System.setProperty(driverBrowserType, driverLocation); 
		
	}

	
	public ConfigureSeleniumBrowserDriver(String driverBrowserType, String driverLocation) {
		this.driverBrowserType = driverBrowserType;
		this.driverLocation = driverLocation;
		System.setProperty(driverBrowserType, driverLocation); 
	}
/*	
	public static void setSeleniumChromeDriver() {
		 System.setProperty(driverBrowserType, driverLocation);  	  
	}
*/	
	public WebDriver getCurrentInstanceDriver() {
		return driver;
	}
	
	
	public WebDriver getChromeDriver() {
        ChromeDriverService service = new ChromeDriverService.Builder()
        .withLogOutput(System.out)
        .build();
		if (driver == null) {
			driver = new ChromeDriver(service);
		}
		return driver; 
	}
	
	public WebDriver getHeadlessChromeDriver() {
	//	System.out.println("Window Handle:" + driver);
		if (driver == null) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless=new");
			driver = new ChromeDriver(options);
		//	System.out.println("Designating headless driver");
		}
		return driver;
	}
	
	public void setDriverURL(String URL) {
		driver.get(URL);
	}
	
	public void maximiseWindow() {
		driver.manage().window().maximize();
	}
	
	public void setDefaultBrowserSize() {
		Dimension newDimension = new Dimension(200, 200);
		driver.manage().window().setSize(newDimension);
	}
	
	public void setBrowserSize(int x, int y) {
		Dimension newDimension = new Dimension(x, y);
		driver.manage().window().setSize(newDimension);
	}
	
	public String getBrowserURL() {
		return driver.getCurrentUrl();
	}
		
	public boolean quitDriverInstance() {
	      String MainWindow = driver.getWindowHandle(); 		// To handle parent window
	      Set<String> s1 = driver.getWindowHandles(); 	      // To handle child window
	      Iterator<String> i1 = s1.iterator();
	      while (i1.hasNext()) {
	          String ChildWindow = i1.next();
	          if (!MainWindow.equalsIgnoreCase(ChildWindow)) {
	              driver.switchTo().window(ChildWindow);
//	              String pageTitle=driver.getTitle();
	              driver.close();
	  //            System.out.println(pageTitle + " window closed");
	          }
	      }
	      driver.switchTo().window(MainWindow);	
	//      driver.close();
	      driver.quit();  	// terminates the webdriver instance window, driver.quit() will terminate the browser instance
	      					//driver.close() will not terminate the browser instance.
	      if (hasQuit()) {
	 //   	  System.out.println("Closed Main window handle " + MainWindow);
	    	  return true;
	      }
	      else {
	 //   	  System.out.println("Window still open with handle " + MainWindow);
	    	  return false;
	      }
	}
	
	public boolean hasQuit() {
		if (((RemoteWebDriver) driver).getSessionId() == null) {
			driver = null;
			return true;
		}
	    return false;
	}
	
}
