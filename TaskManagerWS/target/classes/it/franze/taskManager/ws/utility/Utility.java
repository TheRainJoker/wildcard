package it.franze.taskManager.ws.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Utility {
	
	public static String encryptMd5toString(String s){
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] arr = md.digest(s.getBytes());
			return Base64.getEncoder().encodeToString(arr).replace("%", "").replace("/","").replace("?","").replace("&","").replace("$","").replace("=","").replace(":","").replace("@","").replace("#","");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

}
