package it.franze.taskManager.ws.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import it.franze.taskManager.ws.dao.AccountDAO;
import it.franze.taskManager.ws.dao.UserDAO;
import it.franze.taskManager.ws.utility.Utility;

@Path("user")
public class TaskManager {
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response setUser(@FormParam("name") String name, @FormParam("surname") String surname, @FormParam("email") String email,
							@FormParam("phone") String phone, @FormParam("skype") String skype, @FormParam("password") String password){
		try {
			UserDAO userdao = new UserDAO(0,name,surname,email,phone,skype);
			userdao.save();
			userdao = new UserDAO(userdao.selectByQuery().get(0));
			AccountDAO accountdao = new AccountDAO();
			accountdao.setId(0);
			accountdao.setUser(userdao);
			accountdao.setPassword(Utility.encryptMd5toString(password));
			accountdao.setActive('N');
			accountdao.save();
			Utility.sendMailRegistration(userdao);
			return Response.status(200).entity(new ObjectMapper().writeValueAsString("OK!")).header("Access-Control-Allow-Origin", "*").build();
			/* TODO Manager error DB as duplicate key*/
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("ERROR!").header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	// http://localhost:8080/TaskManagerWS/user?name=Fabrizio&surname=Franzè&email=franze.fabrizio@gmail.com&phone=3455028163&skype=franze.fabrizio@gmail.com&password=F4br1z10
	
	@PUT
	@Path("active")
	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response activeUser(@FormParam("active") String active){
		try {
			return Utility.checkLinkRegistration(active) ? Response.status(200).entity(new ObjectMapper().writeValueAsString("User activated!")).header("Access-Control-Allow-Origin", "*").build() :Response.status(201).entity(new ObjectMapper().writeValueAsString("Problem in user activation! Retrive later.")).header("Access-Control-Allow-Origin", "*").build();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			return Response.status(500).entity("Server error").header("Access-Control-Allow-Origin", "*").build();
		}
	}

}
