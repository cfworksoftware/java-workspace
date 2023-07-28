package webDriverSelenium;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.platform.suite.api.IncludeTags;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

//import webDriverSelenium.ConfigureSeleniumBrowserDriver;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@IncludeTags("UnitTest")
class ConfigureSeleniumBrowserDriverTest {

	ConfigureSeleniumBrowserDriver sourceDriver = null;
	WebDriver driver = null;
	private TestInfo testInfo;
	
	@BeforeAll
	void setUp(){
		sourceDriver = new ConfigureSeleniumBrowserDriver();
		driver = sourceDriver.getChromeDriver();
	}

	@AfterAll
	void tearDown() throws Exception {
		if (((RemoteWebDriver) driver).getSessionId() !=null) {
			sourceDriver.quitDriverInstance();
		}
		assertEquals(null,((RemoteWebDriver) driver).getSessionId(),"driver not released");
	}

	@BeforeEach
	void init(TestInfo testInfo) {
	    this.testInfo = testInfo;
	}
	
	@Test
	@Order(1)
	void testConfigureSeleniumBrowserDriver() {
		System.out.println("displayName = " + testInfo.getDisplayName());
		String Key = "webdriver.chrome.driver";
		String expectedDefinition = "C:\\Users\\colinf\\Documents\\SoftwareProgramming\\ANewMaterial\\Selenium\\chromedriver_win32\\chromedriver.exe";
		String driverLocation = System.getProperty(Key);
		
		assertEquals(expectedDefinition,driverLocation, "Driver system property not set correctly by {key,def} pairing");		
		System.clearProperty(Key);
		driverLocation = System.getProperty(Key);
		assertEquals(null,driverLocation,"Driver system property not cleared");
	}

	@Test
	@Order(2)
	void testConfigureSeleniumBrowserDriverStringString() {
		System.out.println("displayName = " + testInfo.getDisplayName());
		String newDriverKey = "webdriver.firefox.driver";
	//	String newDriverLocation = "C:\\Users\\colinf\\Documents\\SoftwareProgramming\\ANewMaterial\\Selenium\\firefox_win32\\firefoxdriver.exe";
		String expectedDriverLocation = "C:\\Users\\colinf\\Documents\\SoftwareProgramming\\ANewMaterial\\Selenium\\firefox_win32\\firefoxdriver.exe";;
	//	ConfigureSeleniumBrowserDriver newSourceDriver = new ConfigureSeleniumBrowserDriver(newDriverKey, newDriverLocation);
	//	driver.getChromeDriver();
		String driverLocation = System.getProperty(newDriverKey);
		assertNotEquals(expectedDriverLocation,driverLocation, "Driver system property not set correctly by {key,def} pairing");
		System.clearProperty(newDriverKey);
		driverLocation = System.getProperty(newDriverKey);
		assertEquals(null,driverLocation,"Driver system property not cleared");
	}

	@Test
	@Tag("UnitTest")
	@Order(3)
	void testGetChromeDriver() {
		System.out.println("displayName = " + testInfo.getDisplayName());
		assertEquals(sourceDriver.getChromeDriver(),driver,"different drivers");
		assertAll("drivers identities",
				()->assertNotEquals(sourceDriver.getChromeDriver(),null,"different drivers"),
				()->assertNotEquals(driver,null,"different drivers"),
				()->assertEquals(sourceDriver.getChromeDriver(),driver,"different drivers")
		);
	}

//	@Test
	@Order(7)
	void testGetHeadlessChromeDriver() {
		System.out.println("displayName = " + testInfo.getDisplayName());
		fail("Not yet implemented");
	}

	@Test
	@Order(4)
	void testSetDriverURL() throws InterruptedException {
		System.out.println("displayName = " + testInfo.getDisplayName());
		String webURL =  "https://www.google.com/";
		String checkWebURL = "https://www.google.com/"; //
		if (driver !=null) {
			sourceDriver.setDriverURL(webURL);
		}
		String currentURL= sourceDriver.getBrowserURL();
		assertEquals(checkWebURL,currentURL,"Current URL is not that assigned");
	}

	@Test
	@Order(5)
	void testMaximiseWindow() {
		System.out.println("displayName = " + testInfo.getDisplayName());
		driver.manage().window().maximize();

		int driverWindowHeight = driver.manage().window().getSize().getHeight();
		int driverWindowWdth = driver.manage().window().getSize().getWidth();
		
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	    int width = (int)size.getWidth()+16; //adjustment for visible window size
	    int height = (int)size.getHeight()-24; //adjustment for visible window size

		assertAll("window height and width maximised",
				()->assertEquals(height,driverWindowHeight,"Window height not maximised"),
				()->assertEquals(width,driverWindowWdth,"Window width not maximised")
		);

/*		System.out.println("Window height size: "+driver.manage().window().getSize().getHeight());
		System.out.println("Window width size: "+driver.manage().window().getSize().getWidth());
		System.out.println("Window width: "+width);
		System.out.println("Window height: "+height); */
		
	}
/*
 * Try running javascript script to get window inner sizes
	@Test
	void testSetDefaultBrowserSize() {
		
		sourceDriver.setDefaultBrowserSize();
		int driverWindowHeight = driver.manage().window().getSize().getHeight();
		int driverWindowWdth = driver.manage().window().getSize().getWidth();
		
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	    int width = (int)size.getWidth()+16; //adjustment for visible window size
	    int height = (int)size.getHeight()-24; //adjustment for visible window size

		System.out.println("Window height size: "+driver.manage().window().getSize().getHeight());
		System.out.println("Window width size: "+driver.manage().window().getSize().getWidth());
		System.out.println("Window width: "+width);
		System.out.println("Window height: "+height);
	    
		assertAll("window height and width maximised",
				()->assertEquals(height,driverWindowHeight,"Window height not maximised"),
				()->assertEquals(width,driverWindowWdth,"Window width not maximised")
		);	

	}
*/
	@Test
	@Order(6)
	void testSetBrowserSizeIntInt() {
		System.out.println("displayName = " + testInfo.getDisplayName());
		sourceDriver.setBrowserSize(500,500);
		int driverWindowHeight = driver.manage().window().getSize().getHeight();
		int driverWindowWdth = driver.manage().window().getSize().getWidth();
		
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	    int width = (int)size.getWidth()-850; //adjustment for visible window size
	    int height = (int)size.getHeight()-268; //adjustment for visible window size

		assertAll("window height and width maximised",
				()->assertEquals(height,driverWindowHeight,"Window height not maximised"),
				()->assertEquals(width,driverWindowWdth,"Window width not maximised")
		);	
	}

	@Test
	@Order(8)
	void testQuitDriverInstance() {
		System.out.println("displayName = " + testInfo.getDisplayName());
		String sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
		assertNotNull(sessionId,"Session Id is null");
		sourceDriver.quitDriverInstance();
		WebDriver driverNulled = sourceDriver.getCurrentInstanceDriver();
		assertEquals(null,driverNulled,"Session still running");
	}
}