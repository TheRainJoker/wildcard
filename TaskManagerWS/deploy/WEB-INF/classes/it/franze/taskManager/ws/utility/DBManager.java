package it.franze.taskManager.ws.utility;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;


public class DBManager {
	
	private static final Properties PROPERTIES = it.franze.taskManager.ws.utility.Properties.getProperties("Properties.properties");
	private static DBManager dbManager;
	private ConnectionSource cs;
	
	private DBManager() throws SQLException{
		cs = new JdbcConnectionSource(PROPERTIES.getProperty("db.jdbc"));
	}
	
	public static DBManager getInstance() throws SQLException{
		if(dbManager==null)
			dbManager = new DBManager();
		return dbManager;
	}
	
	public boolean save(Class claxx, Object object) throws SQLException, IOException{
		Dao<Object,Integer> dao = DaoManager.createDao(cs, claxx);
		dao.create(object);
		return true;
	}
	
	public List<Object> selectAll(Class claxx) throws SQLException, IOException{
		Dao<Object,Integer> dao = DaoManager.createDao(cs, claxx);
		List<Object> objects = dao.queryForAll();
		return objects;
	}
	
	public Object selectById(Class claxx,int id) throws SQLException, IOException{
		Dao<Object,Integer> dao = DaoManager.createDao(cs, claxx);
		Object object = dao.queryForId(new Integer(id));
		return object;
	}
	
	public List<Object> selectByQuery(Class claxx, Object object) throws SQLException, IOException{
		Dao<Object,Integer> dao = DaoManager.createDao(cs, claxx);
		List<Object> objects = dao.queryForMatching(object);
		return objects;
	}
	
	public boolean update(Class claxx, Object object) throws SQLException, IOException{
		Dao<Object,Integer> dao = DaoManager.createDao(cs, claxx);
		dao.update(object);
		return true;
	}
	
	public boolean deleteByQuery(Class claxx, Object object) throws SQLException, IOException{
		Dao<Object,Integer> dao = DaoManager.createDao(cs, claxx);
		dao.delete(this.selectByQuery(claxx, object));
		return true;
	}
	
	public boolean deleteById(Class claxx,int id) throws SQLException, IOException{
		Dao<Object,Integer> dao = DaoManager.createDao(cs, claxx);
		dao.deleteById(new Integer(id));
		return true;
	}

}
