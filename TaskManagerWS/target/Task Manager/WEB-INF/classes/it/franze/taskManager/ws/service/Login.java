package it.franze.taskManager.ws.service;

import java.io.IOException;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import it.franze.taskManager.ws.dao.UserDAO;
import it.franze.taskManager.ws.pojo.User;
import it.franze.taskManager.ws.utility.Utility;

@Path("login")
public class Login {
	
	@GET
	@Path("{email}:{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@PathParam("email") String email, @PathParam("password") String password){
		try {
			UserDAO userdao = new UserDAO();
			userdao.setEmail(email);
			
			userdao.setPassword(Utility.encryptMd5toString(password));
			List<User> user = userdao.selectByQuery();
			int response = 0;
			if(user.size()!=0)
				response = user.get(0).getId();
			else{
				userdao.setEmail(email);
				userdao.setPassword(password);
				user = userdao.selectByQuery();
				if(user.size()!=0)
					response = user.get(0).getId();
			}
			return Response.status(200).entity(new ObjectMapper().writeValueAsString(response)).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server error").header("Access-Control-Allow-Origin", "*").build();
		}	
	}
	

}
