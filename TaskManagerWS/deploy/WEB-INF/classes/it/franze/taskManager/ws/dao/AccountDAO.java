package it.franze.taskManager.ws.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import it.franze.taskManager.ws.pojo.Account;
import it.franze.taskManager.ws.pojo.User;
import it.franze.taskManager.ws.utility.DBManager;

public class AccountDAO extends Account{
	
	public AccountDAO(){
		super();
	}
	
	public AccountDAO(int id, User user, String password, char active, String token) {
		super(id, user, password, active, token);
	}
	
	public AccountDAO(Account account){
		super(account.getId(), account.getUser(), account.getPassword(), account.getActive(), account.getToken());
	}

	public boolean save() throws SQLException, IOException{
		DBManager dbManager = DBManager.getInstance();
		return dbManager.save(Account.class, this);
	}
	
	public List<Account> selectAll() throws SQLException, IOException{
		DBManager dbManager = DBManager.getInstance();
		return (List<Account>)(List<?>) dbManager.selectAll(Account.class);
	}
	
	public Account selectById() throws SQLException, IOException{
		DBManager dbManager = DBManager.getInstance();
		return (Account) dbManager.selectById(Account.class,this.getId());
	}
	
	public List<Account> selectByQuery() throws SQLException, IOException{
		DBManager dbManager = DBManager.getInstance();
		return (List<Account>)(List<?>) dbManager.selectByQuery(Account.class,this);
	}
	
	public boolean update() throws SQLException, IOException{
		DBManager dbManager = DBManager.getInstance();
		return dbManager.update(Account.class, this);
	}
	
	public boolean deleteByQuery() throws SQLException, IOException{
		DBManager dbManager = DBManager.getInstance();
		return dbManager.deleteByQuery(Account.class, this);
	}
	
	public boolean deleteById() throws SQLException, IOException{
		DBManager dbManager = DBManager.getInstance();
		return dbManager.deleteById(Account.class, this.getId());
	}

}
