package utility;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MyFileWriter{
	
	
	public static boolean writeReportToFile(String filename, String writeThis){
		
		//try to open the file
		OutputStreamWriter fileWrite;
		try {
			fileWrite = new OutputStreamWriter(new FileOutputStream(filename), "UTF-8");
		} catch (Exception e) {
			System.out.println("---Error creating file " + filename + " for writing---");
			e.printStackTrace();
			return false;
		}
		
		//write the report to the file
		try {
			fileWrite.write(writeThis);
			System.out.println("...Report saved to " + filename);
		} catch (IOException e) {
			System.out.println("---Error writing to file " + filename + "---");
			e.printStackTrace();
		}
		
		
		//close file and return true if successful
		try {
			fileWrite.close();
		} catch (IOException e) {
			System.out.println("---Error closing file " + filename + "---");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
