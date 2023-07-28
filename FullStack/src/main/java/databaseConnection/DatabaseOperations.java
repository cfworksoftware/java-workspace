package databaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;
import dataFormatting.DateFormat;
//import databaseConnection.ConfigureDatabaseConnectionPool;

public class DatabaseOperations {

	ConfigureDatabaseConnectionPool connPoolInstance; 
	String formattedDate;
	DataSource dataSource = null;
	DateFormat dateFormatter;
	
	public DatabaseOperations() {
		dateFormatter = new DateFormat();
	}
	
	public void insertDataToDatabase(String databaseName, String tableName, String primaryKeyName, String expectedCurrencySymbol, String[] databaseFieldNames, String[] databaseFieldValues){
		connPoolInstance = ConfigureDatabaseConnectionPool.getInstance();
		ResultSet rsObj = null;
		Connection connObj = null;
		try {
			if (dataSource == null) {
				System.out.println("setting up pool");
				dataSource = ConfigureDatabaseConnectionPool.setUpPool();
				ConfigureDatabaseConnectionPool.getInstance().printConnectionPoolStatus();
			}
			connObj = dataSource.getConnection();
			formattedDate = dateFormatter.formatDate(databaseFieldValues[1]);
			boolean keyAvailable = checkPrimaryKeyNotTaken(connObj, databaseName, tableName, primaryKeyName,formattedDate);
			connObj.close();
//			System.out.println("Test:" + keyAvailable);
			if (keyAvailable)
			{				
	            String databaseQuery = insertShareDataQueryString(databaseName, tableName, primaryKeyName, expectedCurrencySymbol, databaseFieldNames, databaseFieldValues);	            
	            connObj = dataSource.getConnection();
	            ConfigureDatabaseConnectionPool.getInstance().printConnectionPoolStatus();
	            executePreparedInsertStatement(connObj, databaseQuery);
	            connObj.close();
	            ConfigureDatabaseConnectionPool.getInstance().printConnectionPoolStatus();
	            String databaseSelectQuery = "SELECT *" + " FROM "+ databaseName + "." + tableName + " WHERE " 
	            		+ primaryKeyName + " = " + "'" + formattedDate+ "';";
	            connObj = dataSource.getConnection();
	            ConfigureDatabaseConnectionPool.getInstance().printConnectionPoolStatus();
	            rsObj = executePreparedSelectStatement(connObj, databaseSelectQuery);
	            rsObj.close();
	            connObj.close();
	            ConfigureDatabaseConnectionPool.getInstance().printConnectionPoolStatus();
	/*            
	            ConfigureDatabaseConnectionPool.getInstance().printConnectionPoolStatus();
	            System.out.println("Returning object to pool after key available insertion");
	            ConfigureDatabaseConnectionPool.getConnectionPool().returnObject(dataSource);
	          ConfigureDatabaseConnectionPool.getInstance().printConnectionPoolStatus();
	*/  	        } else {
		    	System.out.println("Failed to write date entry");
		    }
		}
        catch (ClassNotFoundException|SQLException e) {
			e.printStackTrace();
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//return rsObj;
	}
	
    public void insertExchangeRateDataToDatabase(String databaseName, String tableName, String primaryKeyName, String[] databaseFieldNames, String[] databaseFieldValues) {
		connPoolInstance = ConfigureDatabaseConnectionPool.getInstance();
		ResultSet rsObj = null;
		Connection connObj = null;
		try {
			if (dataSource == null) {
				System.out.println("setting up pool");
				dataSource = ConfigureDatabaseConnectionPool.setUpPool();
				ConfigureDatabaseConnectionPool.getInstance().printConnectionPoolStatus();
			}
			ConfigureDatabaseConnectionPool.getInstance().printConnectionPoolStatus();
			connObj = dataSource.getConnection();
			boolean keyAvailable = checkPrimaryKeyNotTaken(connObj, databaseName, tableName, primaryKeyName, databaseFieldValues[0]);
			ConfigureDatabaseConnectionPool.getInstance().printConnectionPoolStatus();
			connObj.close();
			ConfigureDatabaseConnectionPool.getInstance().printConnectionPoolStatus();
//			System.out.println("Test:" + keyAvailable);
			if (keyAvailable)
			{	
	            String databaseQuery = insertExchangeRateDataQueryString(databaseName, tableName, primaryKeyName,  databaseFieldNames, databaseFieldValues);
	            connObj = dataSource.getConnection();
	            ConfigureDatabaseConnectionPool.getInstance().printConnectionPoolStatus();
	            executePreparedInsertStatement(connObj, databaseQuery);
	            connObj.close();
	            ConfigureDatabaseConnectionPool.getInstance().printConnectionPoolStatus();
	            String databaseSelectQuery = "SELECT *" + " FROM "+ databaseName + "." + tableName + " WHERE " 
	            		+ primaryKeyName + " = " + "'" + databaseFieldValues[0] + "';";
	            connObj = dataSource.getConnection();
	            ConfigureDatabaseConnectionPool.getInstance().printConnectionPoolStatus();
	            rsObj = executePreparedSelectStatement(connObj, databaseSelectQuery);
	            connObj.close();
	            ConfigureDatabaseConnectionPool.getInstance().printConnectionPoolStatus();
		        } else {
		    	System.out.println("Failed to write date entry");
		    }
		}
        catch (ClassNotFoundException e) {
			e.printStackTrace();
        }
//		return rsObj;
		 catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    }
		
    public ResultSet executePreparedSelectStatement(Connection connObj, String databaseSelectQuery) {
		ResultSet rsObj = null;
 //       Connection connObj = null;
        PreparedStatement pstmtObj = null;
        try {   
     //   	connObj = dataSource.getConnection();
        	ConfigureDatabaseConnectionPool.getInstance().printConnectionPoolStatus();
      //  	dataSource.createConnectionBuilder().
      //  	ConfigureDatabaseConnectionPool.getInstance().printConnectionPoolStatus();
			pstmtObj = connObj.prepareStatement(databaseSelectQuery,ResultSet.TYPE_SCROLL_SENSITIVE, 
                    ResultSet.CONCUR_UPDATABLE);
            rsObj = pstmtObj.executeQuery();
            
            int rsCount = 0;
            if (rsObj !=null) {
            	rsObj.beforeFirst();
            
		          //but notice that you'll only get correct ResultSet size after end of the while loop
		          while(rsObj.next())
		          {
		              //do your other per row stuff 
		              rsCount = rsCount + 1;
		          }//end while
            }
            System.out.println("rscount:"+rsCount);
      //  	ConfigureDatabaseConnectionPool.getInstance().getConnectionPool().returnObject(connObj);
      //      connObj.close();
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }

    	return rsObj;
	}
	
	public void executePreparedInsertStatement(Connection connObj, String databaseInsertQuery) {
		ResultSet rsObj = null;
  //      Connection connObj = null;
        PreparedStatement pstmtObj = null;
        try {   
 //       	 connObj = dataSource.getConnection();
        	 pstmtObj = connObj.prepareStatement(databaseInsertQuery,ResultSet.TYPE_SCROLL_SENSITIVE, 
                     ResultSet.CONCUR_UPDATABLE);
	         pstmtObj.execute();
        }
	    catch(Exception sqlException) {
	    	sqlException.printStackTrace();
	    } 
        finally {
        	try {
//				connObj.close();
				
	            ConfigureDatabaseConnectionPool.getInstance().printConnectionPoolStatus();
	     //       System.out.println("Returning after insert statement object to pool");
	     //       ConfigureDatabaseConnectionPool.getConnectionPool().returnObject(connObj);
	     //       ConfigureDatabaseConnectionPool.getInstance().printConnectionPoolStatus();
				
				
/*			} catch (SQLException e) {
				e.printStackTrace();
*/			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
        
    public String insertExchangeRateDataQueryString(String databaseName, String tableName, String primaryKeyName, String[] dataFieldName, String[] dataValueColumn) {		
		String insertData = null;
		insertData = "INSERT INTO "+ databaseName + "." + tableName  
				+ "(" + dataFieldName[0] + "," + dataFieldName[1] + "," + dataFieldName[2] + "," + dataFieldName[3] + ") VALUES ('" 
				+ dataValueColumn[0] + "','" + dataValueColumn[1] + "','" + dataValueColumn[2] + "','"+ dataValueColumn[3] +"');";	
		System.out.println(insertData);
		return insertData;
	}

    public String insertShareDataQueryString(String databaseName, String tableName, String primaryKeyName, String expectedCurrencySymbol, String[] dataFieldName, String[] dataValueColumn) {
			String unitValue="XX";
			if (tableName.equals("webdata_rlumuk_growthincome_quote")) {
				int currencyIndex = dataValueColumn[0].indexOf(expectedCurrencySymbol);
				unitValue = dataValueColumn[0].substring(0,currencyIndex);
				dataValueColumn[3] = dataValueColumn[0].substring(currencyIndex);
				dataValueColumn[0] = unitValue;
		}			
		formattedDate = dateFormatter.formatDate(dataValueColumn[1]);		
		String insertData = null;
		insertData = "INSERT INTO "+ databaseName + "." + tableName  
				+ "(" + dataFieldName[0] + "," + dataFieldName[1] + "," + dataFieldName[2] + "," + dataFieldName[3] + ") VALUES ('" 
				+ dataValueColumn[0] + "','" + formattedDate + "','" + dataValueColumn[2] + "','"+ dataValueColumn[3] +"');";	

		formattedDate = "";
	//	System.out.println(insertData);
		return insertData;
	}

	public Boolean checkPrimaryKeyNotTaken(Connection connObj, String databaseName, String tableName, String primaryKeyName, String primaryKeyValue) {
		boolean primaryKeyAvailable = false;
//		Connection connObj = null;
		ResultSet rsObj = null;
		String databaseSelectQuery = "SELECT *" + " FROM "+ databaseName + "." + tableName + " WHERE " 
										+ primaryKeyName + " = " + "'" + primaryKeyValue + "';";
		System.out.println("PrimaryKey check: " + databaseSelectQuery);

/*    Connection connObj = null;
PreparedStatement pstmtObj = null;
try {   
	connPoolInstance.printConnectionPoolStatus();
    System.out.println("\n=====Making A New Connection Object For Db Transaction=====\n");
    connObj = dataSource.getConnection();
    connPoolInstance.printConnectionPoolStatus(); 
    pstmtObj = connObj.prepareStatement(databaseSelectQuery,ResultSet.TYPE_SCROLL_SENSITIVE, 
            ResultSet.CONCUR_UPDATABLE);
    rsObj = pstmtObj.executeQuery();
    */try {
    //	connObj = dataSource.getConnection();
    	rsObj = executePreparedSelectStatement(connObj,databaseSelectQuery);  

    	System.out.println("executed check statement");
    	if (rsObj !=null) {
        	rsObj.beforeFirst();
			if (!rsObj.next()) 
			   {
			       	primaryKeyAvailable = true;
			       	System.out.println("No result set found");
			      }
			   else
			   {
			   	System.out.println("Entering else statement");
			   	int cnt=1;
			   	do {
			       	System.out.println("evaluating result set" + cnt++);
			           	if (rsObj.getString("quote_date").equals(primaryKeyValue)) 
			           	{
			        	   primaryKeyAvailable = false;
			        	   System.out.println("Tuple found");
			        	   break;
			           	}
			   	} while (rsObj.next());
			   	rsObj.close();
	            ConfigureDatabaseConnectionPool.getInstance().printConnectionPoolStatus();
	            System.out.println("Returning after primary key check object to pool");
	//            ConfigureDatabaseConnectionPool.getConnectionPool().returnObject(dataSource);
	            ConfigureDatabaseConnectionPool.getInstance().printConnectionPoolStatus();
	   		}
    	}
		} catch (SQLException e) {
		
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

		return primaryKeyAvailable;
	}
}


 /*   	public void insertDataToDatabase(String databaseName, String tableName, String primaryKeyName, String expectedCurrencySymbol, String[] databaseFieldNames, String[] databaseFieldValues){
		connPoolInstance = ConfigureDatabaseConnectionPool.getInstance();
		try {
			if (dataSource == null) {
				dataSource = ConfigureDatabaseConnectionPool.setUpPool();
			}
			formatDate(databaseFieldValues[1]);
			boolean keyAvailable = checkPrimaryKeyNotTaken(databaseName, tableName, primaryKeyName,formattedDate);
			System.out.println("Test:" + keyAvailable);

			if (keyAvailable)
			{
				ResultSet rsObj = null;
		        Connection connObj = null;
		        PreparedStatement pstmtObj = null;
		        try {   
		        	connPoolInstance.printConnectionPoolStatus();
		            System.out.println("\n=====Making A New Connection Object For Db Transaction=====\n");
		            connObj = dataSource.getConnection();
		            connPoolInstance.printConnectionPoolStatus();
		            String databaseQuery = insertDataQuery(databaseName, tableName, primaryKeyName, expectedCurrencySymbol, databaseFieldNames, databaseFieldValues);
		            pstmtObj = connObj.prepareStatement(databaseQuery);
		            pstmtObj.execute();
		       //     need to capture exception: java.sql.SQLIntegrityConstraintViolationException
		            System.out.println("\n=====Releasing Connection Object To Pool=====\n");   
				}  catch(Exception sqlException) {
		            sqlException.printStackTrace();
		        } 
		        finally {
		            try {
		            	connPoolInstance.printConnectionPoolStatus();
		                pstmtObj = connObj.prepareStatement("SELECT * FROM accounting.webdata_rlumuk_growthincome_quote");
		                rsObj = pstmtObj.executeQuery();
		                while (rsObj.next()) {
		                    System.out.println("Returned data" + rsObj.toString());
		                }
		                System.out.println("\n=====Again Releasing Connection Object To Pool=====\n");   
		            //	connPoolInstance.closePool();
		                connObj.close();
		            } catch(Exception sqlException) {
		                sqlException.printStackTrace();
		            }
		        }
		        connPoolInstance.printConnectionPoolStatus();
		        } else {
		    	System.out.println("Failed to write date entry");
		    }
		}
        catch (ClassNotFoundException e) {
			e.printStackTrace();
        }
	}
*/	
	/*
    public String insertDataQuery(String databaseName, String tableName, String primaryKeyName, String expectedCurrencySymbol, String[] dataFieldName, String[] dataValueColumn) {
			String unitValue="XX";
	//		String currencySymbol="XX";
			if (tableName.equals("webdata_rlumuk_growthincome_quote")) {
		//		int lengthUnitPrice = dataValueColumn[0].length();
		//		boolean checkCurrencyUnit = dataValueColumn[0].contains(expectedCurrencySymbol);
		//		boolean checkDecimalPoint = dataValueColumn[0].contains(".");
				int currencyIndex = dataValueColumn[0].indexOf(expectedCurrencySymbol);
		//		int decpointIndex =dataValueColumn[0].indexOf(".");
				unitValue = dataValueColumn[0].substring(0,currencyIndex);
				dataValueColumn[3] = dataValueColumn[0].substring(currencyIndex);
				dataValueColumn[0] = unitValue;
		}
		formatDate(dataValueColumn[1]);

//		int lengthStockExchange =  dataValueColumn[2].length();
		
		String insertData = null;

		insertData = "INSERT INTO "+ databaseName + "." + tableName  
				+ "(" + dataFieldName[0] + "," + dataFieldName[1] + "," + dataFieldName[2] + "," + dataFieldName[3] + ") VALUES ('" 
				+ dataValueColumn[0] + "','" + formattedDate + "','" + dataValueColumn[2] + "','"+ dataValueColumn[3] +"');";	

		formattedDate = "";
		System.out.println(insertData);
		return insertData;
	}
*/  
    
/*	
    public Boolean checkPrimaryKeyNotTaken(String databaseName, String tableName, String primaryKeyName, String primaryKeyValue) {
 		boolean primaryKeyAvailable = false;
 		String databaseQuery = "SELECT *" + " FROM "+ databaseName + "." + tableName + " WHERE " 
 		+ primaryKeyName + " = " + "'" + primaryKeyValue + "';";
 		//		SELECT quote_date FROM accounting.webdata_rlumuk_growthincome WHERE quote_date = '"+ formattedDate +"';";
 		System.out.println(databaseQuery);
 		ResultSet rsObj = null;
         Connection connObj = null;
         PreparedStatement pstmtObj = null;
         try {   
         	connPoolInstance.printConnectionPoolStatus();
     //        System.out.println("\n=====Making A New Connection Object For Db Transaction=====\n");
             connObj = dataSource.getConnection();
             connPoolInstance.printConnectionPoolStatus();            
             pstmtObj = connObj.prepareStatement(databaseQuery,ResultSet.TYPE_SCROLL_SENSITIVE, 
                     ResultSet.CONCUR_UPDATABLE);
             rsObj = pstmtObj.executeQuery();
             if (!rsObj.next()) 
             {
             	primaryKeyAvailable = true;
    //         	System.out.println("No result set found");
 	        } 
             else
             {
    //         	System.out.println("Entering else statement");
    //         	int cnt=1;
             	do {
 	//            	System.out.println("evaluating result set" + cnt++);
 	   	           	if (rsObj.getString("quote_date").equals(primaryKeyValue)) 
 	   	           	{
 	   	        	   primaryKeyAvailable = false;
 	//   	        	   System.out.println("Tuple found");
 	   	        	   break;
 	   	           	}
             	} while (rsObj.next());
             }
             connObj.close();
         } catch(Exception sqlException) {
             sqlException.printStackTrace();
         }
         connPoolInstance.printConnectionPoolStatus();
//         System.out.println("primary key available" + primaryKeyAvailable);  
 		return primaryKeyAvailable;
 	}
*/
		
