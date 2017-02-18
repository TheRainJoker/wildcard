package it.franze.taskManager.ws.utility;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

public class Properties {
	
	private static final String PATH = "http://localhost:8080/TaskManagerWSConfiguration/";

	public static java.util.Properties getProperties(String file){
		try {
			java.util.Properties prop = new java.util.Properties();
			URL url = new URL(PATH+file);
			InputStreamReader in;
			in = new InputStreamReader(url.openStream(), "UTF-8");
			Reader reader = in;
			prop.load(reader);
			return prop;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
			return null;
		} 
	}
	
	
	
}
