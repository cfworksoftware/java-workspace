package databaseConnection;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;

//import databaseConnection.ConfigureDatabaseConnectionPool;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class ConfigureDatabaseConnectionPoolTest {

	private TestInfo testInfo;

	@BeforeEach
	void init(TestInfo testInfo) {
	    this.testInfo = testInfo;
	}
	
/*	@AfterEach
	void teardown() {
		
	} */
	/**
	 * Test method for {@link webdriverSelenium.ConfigureDatabaseConnectionPool#setUpPool()}.
	 */
	@Test
	@Order(1)
	void testGetInstance() {
		System.out.println("displayName = " + testInfo.getDisplayName());
		ConfigureDatabaseConnectionPool single_instance = ConfigureDatabaseConnectionPool.getInstance();
		assertNotEquals(null,single_instance,"single_instance un-instantiated");
	}
	
	@Test
	@Order(2)
	void testSetUpPool() {
		System.out.println("displayName = " + testInfo.getDisplayName());
		try {
			DataSource dataSource = ConfigureDatabaseConnectionPool.setUpPool();
			int poolSize = ConfigureDatabaseConnectionPool.getConnectionPool().getMaxActive();	
			assertEquals(6,poolSize,"Wrong pool size should be 6");
			dataSource.getConnection().close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link webdriverSelenium.ConfigureDatabaseConnectionPool#closePool()}.
	 */
	@Test
	@Order(5)
	void testClosePool() {

		System.out.println("displayName = " + testInfo.getDisplayName());
		try {
			DataSource dataSource = ConfigureDatabaseConnectionPool.setUpPool();
			Connection connObj = dataSource.getConnection();
			int usedPoolConnections = ConfigureDatabaseConnectionPool.getConnectionPool().getNumActive();
			assertEquals(1,usedPoolConnections,"Number of used connections should be 1");		
			connObj.close();
			usedPoolConnections = ConfigureDatabaseConnectionPool.getConnectionPool().getNumActive();
			assertEquals(0,usedPoolConnections,"Number of used connections should be 0");
		} catch (ClassNotFoundException | SQLException e ) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link webdriverSelenium.ConfigureDatabaseConnectionPool#getConnectionPool()}.
	 */
	@Test
	@Order(3)
	void testGetConnectionPool() {
		System.out.println("displayName = " + testInfo.getDisplayName());
		try {
			DataSource dataSource = ConfigureDatabaseConnectionPool.setUpPool();
			int usedPoolConnections = ConfigureDatabaseConnectionPool.getConnectionPool().getNumActive();
			assertEquals(0,usedPoolConnections,"Number of connections should be 0");
			dataSource.getConnection().close();
		} catch (ClassNotFoundException | SQLException e ) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link webdriverSelenium.ConfigureDatabaseConnectionPool#printConnectionPoolStatus()}.
	 */
	@Test
	@Order(4)
	@StdIo
	void testPrintConnectionPoolStatus(StdOut out) {
		System.out.println("displayName = " + testInfo.getDisplayName());
		try {
			ConfigureDatabaseConnectionPool single_instance = ConfigureDatabaseConnectionPool.getInstance();
			DataSource dataSource= ConfigureDatabaseConnectionPool.setUpPool();
			single_instance.printConnectionPoolStatus();
			int poolConnections = ConfigureDatabaseConnectionPool.getConnectionPool().getMaxActive();
			int activeConnections = ConfigureDatabaseConnectionPool.getConnectionPool().getNumActive();
			int idleConnections = ConfigureDatabaseConnectionPool.getConnectionPool().getNumIdle();
			int numberOfLines = out.capturedLines().length;
			StringBuilder sb = new StringBuilder();
			sb.append("<<Error message:<");
			for (int cnt=0;cnt<numberOfLines;cnt++) {
				sb.append(out.capturedLines()[cnt]);
				sb.append(">:<");
				}
			System.out.println(sb.toString());
			
			assertEquals("Max.: " + poolConnections + "; Active: " + activeConnections + "; Idle: " + idleConnections, out.capturedLines()[numberOfLines-1]);
			dataSource.getConnection().close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}
