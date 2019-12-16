package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFileHandler {

	
	private FileInputStream  input =null;
	private Properties prop = new Properties();
	
	public PropertiesFileHandler(String fileName)   {
		
		String filePath = System.getProperty("user.dir")+File.separator+"config"+File.separator+fileName+".properties";
				
		try {
			input = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			System.out.println("Error while loading file : "+e.getMessage());
		}
		try {
			prop.load(input);
		} catch (IOException e) {
			System.out.println("Error while loading prop file : "+e.getMessage());
		}
	}
	
	
	public String getValue(String propName) {
		
		String strValue = prop.getProperty(propName);
		
		return strValue;
		
	}
	
	
	
}
