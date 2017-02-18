package it.franze.taskManager.ws.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "account")
public class Account {

	@DatabaseField(id=true)
	private int id;
	@DatabaseField(canBeNull = false, foreign = true)
	private User user;
	@DatabaseField(canBeNull = false)
	private String password;
	@DatabaseField(canBeNull = false)
	private char active;
	@DatabaseField
	private String token;
	
	public Account(){}
	
	public Account(int id, User user, String password, char active, String token) {
		super();
		setId(id);
		setUser(user);
		setPassword(password);
		setActive(active);
		setToken(token);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public char getActive() {
		return active;
	}

	public void setActive(char active) {
		this.active = active;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}
