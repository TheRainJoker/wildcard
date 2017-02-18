package it.franze.taskManager.ws.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;

import com.sun.corba.se.spi.servicecontext.UEInfoServiceContext;

import it.franze.taskManager.ws.dao.AccountDAO;
import it.franze.taskManager.ws.dao.UserDAO;
import it.franze.taskManager.ws.pojo.User;
import it.franze.taskManager.ws.utility.Utility;

public class Test3 {

	public static void main(String[] args) throws IOException, SQLException {
		String name="Fabrizio";
		String surname="Franzè";
		String email="Franze.fabrizio@gmail.com";
		String phone="3455028163";
		String skype="Skype";
		String password="F4br1z10";
		UserDAO userdao = new UserDAO(0,name,surname,email,phone,skype);
//		userdao.save();
		User u = userdao.selectByQuery().get(0);
		System.out.println(u.getId()+" "+u.getName());
		AccountDAO accountdao = new AccountDAO();
		accountdao.setId(0);
		accountdao.setUser(u);
		accountdao.setPassword(Utility.encryptMd5toString(password));
		accountdao.setToken("");
		accountdao.setActive('N');
		accountdao.save();
	}

}
