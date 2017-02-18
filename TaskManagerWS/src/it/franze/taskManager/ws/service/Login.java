package it.franze.taskManager.ws.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import it.franze.taskManager.ws.dao.AccountDAO;
import it.franze.taskManager.ws.dao.UserDAO;
import it.franze.taskManager.ws.pojo.Account;
import it.franze.taskManager.ws.pojo.User;
import it.franze.taskManager.ws.utility.Utility;

@Path("login")
public class Login {
	
	@GET
	@Path("{email}:{fieldLogin}/{remember}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@PathParam("email") String email, @PathParam("fieldLogin") String fieldLogin,@PathParam("remember") boolean remember){
		try {
			UserDAO userdao = new UserDAO();
			userdao.setEmail(email);
			List<User> users = userdao.selectByQuery();
			User user = new User();
			if(users.size()!=0){
				user = users.get(0);
				AccountDAO accountdao = new AccountDAO();
				accountdao.setUser(user);
				accountdao.setPassword(Utility.encryptMd5toString(fieldLogin));
				List<Account> accounts = accountdao.selectByQuery();
				if(accounts.size()!=0){
					return loginSupport(remember,new AccountDAO(accounts.get(0)),user);
				}else{
					accountdao = new AccountDAO();
					accountdao.setUser(user);
					accountdao.setToken(fieldLogin);
					accounts = accountdao.selectByQuery();
					if(accounts.size()!=0)
						return loginSupport(remember,new AccountDAO(accounts.get(0)),user);
				}
			}
			return Response.status(201).entity(new ObjectMapper().writeValueAsString("Username or password wrong")).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server error").header("Access-Control-Allow-Origin", "*").build();
		}	
	}
	
	private Response loginSupport(boolean remember, AccountDAO accountdao,User user) throws SQLException, IOException{
		if(remember){
			accountdao.setToken(Utility.generatesToken());
			accountdao.update();
			ObjectMapper om = new ObjectMapper();
			Map<String,Object> map1 = om.readValue(om.writeValueAsString(user),Map.class);
			Map<String,Object> map2 = om.readValue("{\"token\": \""+accountdao.getToken()+"\"}",Map.class);
			Map<String, Object> merged = new HashMap<String, Object>(map2);
			merged.putAll(map1);
			return Response.status(200).entity(new ObjectMapper().writeValueAsString(merged)).header("Access-Control-Allow-Origin", "*").build();
		}else{
			accountdao.setToken("");
			accountdao.save();
			return Response.status(200).entity(new ObjectMapper().writeValueAsString(user)).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	

}
