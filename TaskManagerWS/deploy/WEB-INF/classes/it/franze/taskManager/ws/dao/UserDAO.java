package it.franze.taskManager.ws.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import it.franze.taskManager.ws.pojo.User;
import it.franze.taskManager.ws.utility.DBManager;

public class UserDAO extends User{
	
	public UserDAO(){
		super();
	}
	
	public UserDAO(int id,String name, String surname, String email, String phone, String skype) {
		super(id,name,surname,email,phone,skype);
	}
	
	public UserDAO(User user) {
		super(user.getId(),user.getName(),user.getSurname(),user.getEmail(),user.getPhone(),user.getSkype());
	}

	public boolean save() throws SQLException, IOException{
		DBManager dbManager = DBManager.getInstance();
		return dbManager.save(User.class, this);
	}
	
	public List<User> selectAll() throws SQLException, IOException{
		DBManager dbManager = DBManager.getInstance();
		return (List<User>)(List<?>) dbManager.selectAll(User.class);
	}
	
	public User selectById() throws SQLException, IOException{
		DBManager dbManager = DBManager.getInstance();
		return (User) dbManager.selectById(User.class,this.getId());
	}
	
	public List<User> selectByQuery() throws SQLException, IOException{
		DBManager dbManager = DBManager.getInstance();
		return (List<User>)(List<?>) dbManager.selectByQuery(User.class,this);
	}
	
	public boolean update() throws SQLException, IOException{
		DBManager dbManager = DBManager.getInstance();
		return dbManager.update(User.class, this);
	}
	
	public boolean deleteByQuery() throws SQLException, IOException{
		DBManager dbManager = DBManager.getInstance();
		return dbManager.deleteByQuery(User.class, this);
	}
	
	public boolean deleteById() throws SQLException, IOException{
		DBManager dbManager = DBManager.getInstance();
		return dbManager.deleteById(User.class, this.getId());
	}

}
