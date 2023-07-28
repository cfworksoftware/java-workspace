package dataFileConnection;

import java.io.FileWriter;
import java.io.IOException;

public class WriteToFile {

	String filePath = "C:\\Users\\colinf\\eclipse-workspace3\\URLwebsite\\src\\main\\resources";
	FileWriter myWriter;
	
	public WriteToFile(String fileName) {
		 try {
			myWriter = new FileWriter( filePath + "\\" + fileName, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeDataToFile(String data) {
	    try {
	        myWriter.write(data);
	        myWriter.write(System.lineSeparator());
	        myWriter.close();
	        System.out.println("Successfully wrote to the file: {" + data +"}");
	      } catch (IOException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	      }
	}
	
}
