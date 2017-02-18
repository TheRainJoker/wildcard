package it.franze.taskManager.ws.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import it.franze.taskManager.ws.dao.UserDAO;
import it.franze.taskManager.ws.pojo.User;

@Path("user")
public class TaskManager {
	
	@GET
	@Path("id={id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserById(@PathParam("id") Integer id) {
		try {
			UserDAO userdao = new UserDAO();
	    	userdao.setId(id);
	    	userdao.setActive('Y');
	    	String response ="";
	    	User user = userdao.selectById();
	    	user.setPassword("");
			response = new ObjectMapper().writeValueAsString(user);
	        return Response.status(200).entity(response).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			return Response.status(500).entity(e.toString()).header("Access-Control-Allow-Origin", "*").build();
		} 
    }
	
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUSer() {
		try {
			String response ="";
			Iterator<User> usersIt = new UserDAO().selectAll().iterator();
			List<User> users = new ArrayList<User>();
			while(usersIt.hasNext()){
				User user = usersIt.next();
				user.setPassword("");
				users.add(user);
			}
			response = new ObjectMapper().writeValueAsString(users);
	        return Response.status(200).entity(response).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			return Response.status(500).entity(e.toString()).header("Access-Control-Allow-Origin", "*").build();
		} 
    }
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void setUser(@FormParam("name") String name, @FormParam("surname") String surname, @FormParam("email") String email,
							@FormParam("phone") String phone, @FormParam("skype") String skype, @FormParam("password") String password){
		try {
			UserDAO userdao = new UserDAO(0,name,surname,email,phone,skype,password,'N');
			userdao.save();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
