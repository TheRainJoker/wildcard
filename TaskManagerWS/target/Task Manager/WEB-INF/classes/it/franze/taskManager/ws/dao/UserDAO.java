package it.franze.taskManager.ws.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import it.franze.taskManager.ws.pojo.User;

public class UserDAO extends User{
	
	private static final Properties PROPERTIES = it.franze.taskManager.ws.utility.Properties.getProperties();
	
	public UserDAO(){
		super();
	}
	
	public UserDAO(int id,String name, String surname, String email, String phone, String skype, String password, char active) throws Exception {
		super(id,name,surname,email,phone,skype,password,active);
	}

	public boolean save() throws SQLException, IOException{
		ConnectionSource cs = new JdbcConnectionSource(PROPERTIES.getProperty("db.jdbc"));
		Dao<User,Integer> userdao = DaoManager.createDao(cs, User.class);
		userdao.create(this);
		cs.close();
		return true;
	}
	
	public List<User> selectAll() throws SQLException, IOException{
		ConnectionSource cs = new JdbcConnectionSource(PROPERTIES.getProperty("db.jdbc"));
		Dao<User,Integer> userdao = DaoManager.createDao(cs, User.class);
		List<User> user = userdao.queryForAll();
		cs.close();
		return user;
	}
	
	public User selectById() throws SQLException, IOException{
		ConnectionSource cs = new JdbcConnectionSource(PROPERTIES.getProperty("db.jdbc"));
		Dao<User,Integer> userdao = DaoManager.createDao(cs, User.class);
		User user = userdao.queryForId(new Integer(this.getId()));
		cs.close();
		return user;
	}
	
	public List<User> selectByQuery() throws SQLException, IOException{
		ConnectionSource cs = new JdbcConnectionSource(PROPERTIES.getProperty("db.jdbc"));
		Dao<User,Integer> userdao = DaoManager.createDao(cs, User.class);
		List<User> userLists = userdao.queryForMatching(this);
		cs.close();
		return userLists;
	}
	
	public boolean update() throws SQLException, IOException{
		ConnectionSource cs = new JdbcConnectionSource(PROPERTIES.getProperty("db.jdbc"));
		Dao<User,Integer> userdao = DaoManager.createDao(cs, User.class);
		userdao.update(this);
		cs.close();
		return true;
	}
	
	public boolean deleteByQuery() throws SQLException, IOException{
		ConnectionSource cs = new JdbcConnectionSource(PROPERTIES.getProperty("db.jdbc"));
		Dao<User,Integer> userdao = DaoManager.createDao(cs, User.class);
		userdao.delete(this.selectByQuery());
		cs.close();
		return true;
	}
	
	public boolean deleteById() throws SQLException, IOException{
		ConnectionSource cs = new JdbcConnectionSource(PROPERTIES.getProperty("db.jdbc"));
		Dao<User,Integer> userdao = DaoManager.createDao(cs, User.class);
		userdao.deleteById(new Integer(this.getId()));
		cs.close();
		return true;
	}

}
