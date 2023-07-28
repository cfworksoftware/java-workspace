package databaseConnection;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

public class ConfigureDatabaseConnectionPool {

    // JDBC Driver Name & Database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String JDBC_DB_URL = "jdbc:mysql://localhost:3306/accounting";
 
    // JDBC Database Credentials
    static final String JDBC_USER = "root";
    static final String JDBC_PASS = "MYSQL_2023";
 
    private static GenericObjectPool gPool = null;
	
    private static ConfigureDatabaseConnectionPool single_instance = null;
    
    private ConfigureDatabaseConnectionPool() {
  //  	System.out.println("Constructor called of ConfigureDatabaseConnectionPool");
    }
	
    public static synchronized ConfigureDatabaseConnectionPool getInstance()
    {
        if (single_instance == null) {
 //       	System.out.println("Instantiation of ConfigureDatabaseConnectionPool");
            single_instance = new ConfigureDatabaseConnectionPool();
        }
        return single_instance;
    }
    
	public static javax.sql.DataSource setUpPool() throws ClassNotFoundException{
        Class.forName(JDBC_DRIVER);
  //      System.out.println("setUpPool() called of ConfigureDatabaseConnectionPool");
        // Creates an Instance of GenericObjectPool That Holds Our Pool of Connections Object!
        if (gPool ==null) {
	        gPool = new GenericObjectPool();
	        gPool.setMaxActive(6);        
	        // Creates a ConnectionFactory Object Which Will Be Use by the Pool to Create the Connection Object!
	        ConnectionFactory cf = new DriverManagerConnectionFactory(JDBC_DB_URL, JDBC_USER, JDBC_PASS);	 
	        // Creates a PoolableConnectionFactory That Will Wraps the Connection Object Created by the ConnectionFactory to Add Object Pooling Functionality!
	        PoolableConnectionFactory pcf = new PoolableConnectionFactory(cf, gPool, null, null, false, true);
        }
        return new PoolingDataSource(gPool);
    }
	
    public void closePool() {
    	try {
			getConnectionPool().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
 
    public static GenericObjectPool getConnectionPool() {
        return gPool;
    }
	
    public void printConnectionPoolStatus() {  	
        System.out.println("Max.: " + getConnectionPool().getMaxActive() + "; Active: " + getConnectionPool().getNumActive() + "; Idle: " + getConnectionPool().getNumIdle());
    }
    
}
