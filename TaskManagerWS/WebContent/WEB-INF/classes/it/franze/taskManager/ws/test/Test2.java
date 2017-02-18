package it.franze.taskManager.ws.test;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;


import it.franze.taskManager.ws.dao.AccountDAO;
import it.franze.taskManager.ws.dao.UserDAO;
import it.franze.taskManager.ws.pojo.Account;
import it.franze.taskManager.ws.pojo.User;
import it.franze.taskManager.ws.utility.Utility;

public class Test2 {

	public static void main(String[] args) throws SQLException, IOException {

//		UserDAO userdao = new UserDAO(0, "Fabrizio", "Franzè", "franze.fabrizio@gmail.com", "3455028163", "franze.fabrizio@gmail.com");
//		
//		userdao.save();
		
//		int id = userdao.selectByQuery().get(0).getId();
//		
//		String password = "F4br1z10";
//		AccountDAO accountdao = new AccountDAO();
//		accountdao.setId(1);
//		accountdao.setUser(1);
//		accountdao.setPassword(Utility.encryptMd5toString(password));
//		accountdao.setActive('Y');
//		
//		accountdao.save();
		
//		String token = "{\"token\": \""+Utility.generatesToken()+"\"}";
//		
//		ObjectMapper om = new ObjectMapper();
//		Map<String,Object> map1 = om.readValue(om.writeValueAsString(userdao),Map.class);
//		Map<String,Object> map2 = om.readValue(token,Map.class);
//		Map<String, Object> merged = new HashMap<String, Object>(map2);
//		merged.putAll(map1);
//		System.out.println(om.writeValueAsString(merged));ù
		

//		String email = "franze.fabrizio@gmail.com";
//		String password = "F4br1z10";
//		boolean remember = true;
//		
//		AccountDAO test = new AccountDAO();
//		test.setId(1);
//		test.setPassword(Utility.encryptMd5toString(password));
//		test.setActive('Y');
//		test.setUser(1);
//		test.setToken("OK");
//		
//		test.update();
		
//		UserDAO userdao = new UserDAO();
//		userdao.setEmail(email);
//		List<User> users = userdao.selectByQuery();
//		User user = new User();
//		String response = "Nothing";
//		if(users.size()!=0){
//			user = users.get(0);
//			AccountDAO accountdao = new AccountDAO();
//			accountdao.setUser(user.getId());
//			accountdao.setPassword(Utility.encryptMd5toString(password));
//			List<Account> accounts = accountdao.selectByQuery();
//			if(accounts.size()!=0)
//				if(remember){
//					accountdao = new AccountDAO(accounts.get(0));
//					accountdao.setToken(Utility.generatesToken());
//					accountdao.update();
//					ObjectMapper om = new ObjectMapper();
//					Map<String,Object> map1 = om.readValue(om.writeValueAsString(user),Map.class);
//					Map<String,Object> map2 = om.readValue("{\"token\": \""+accountdao.getToken()+"\"}",Map.class);
//					Map<String, Object> merged = new HashMap<String, Object>(map2);
//					merged.putAll(map1);
//					response = new ObjectMapper().writeValueAsString(merged);
//				}else{
//					response = new ObjectMapper().writeValueAsString(user);
//				}
//		}
//		System.out.println(response);
		
		UserDAO userdao= new UserDAO();
		userdao.setId(1);
//		userdao.setName("");
		
		
		System.out.println(userdao.selectByQuery().get(0).getName());
	}
		
		

}
