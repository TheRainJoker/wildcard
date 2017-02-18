package it.franze.taskManager.ws.test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import it.franze.taskManager.ws.dao.UserDAO;
import it.franze.taskManager.ws.utility.Utility;

public class test {

	public static void main(String[] args) throws Exception {
		String pass = "F4br1z10";
		
		
		UserDAO userdao = new UserDAO(1,"Fabrizio","Franzè","franze.fabrizio@gmail.com","3455028163","franze.fabrizio@gmail.com",Utility.encryptMd5toString(pass),'Y');
		userdao.update();
	}

}
