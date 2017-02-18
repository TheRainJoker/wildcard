package it.franze.taskManager.ws.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "user")
public class User {
	
	@DatabaseField(id = true)
	private int id;
	@DatabaseField(canBeNull = false)
	private String name;
	@DatabaseField(canBeNull = false)
	private String surname;
	@DatabaseField(canBeNull = false)
	private String email;
	@DatabaseField
	private String phone;
	@DatabaseField
	private String skype;
	
	public User(){}
	
	public User(int id,String name, String surname, String email, String phone, String skype){
		setId(id);
		setName(name);
		setSurname(surname);
		setEmail(email);
		setPhone(phone);
		setSkype(skype);
	}
//	@JsonProperty("id)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name.substring(0,1).toUpperCase()+name.substring(1,name.length()).toLowerCase();
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname.substring(0,1).toUpperCase()+surname.substring(1,surname.length()).toLowerCase();
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email.toLowerCase();
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSkype() {
		return skype;
	}
	public void setSkype(String skype) {
		this.skype = skype;
	}
}
