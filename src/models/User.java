package models;
public class User {
	private int Id;
	private String UserName;
	private String PassWord;
	private String Name;
	private String Family;
	public ChargingStation chargingStation;

	public User() {
		
	}
	
	public int GetId() {
		return Id;
	}
	public void SetId(int id) {
		this.Id=id;
	}
	
	public String GetUserName() {
		return UserName;
	}
	
	public void SetUserName(String userName) {
		this.UserName=userName;
	}
	
	public String GetPassword() {
		return PassWord;
	}
	
	public void SetPassWord(String passWord) {
		this.PassWord=passWord;
	}
	
	public String GetName() {
		return Name;
	}
	
	public void SetName(String name) {
		this.Name=name;
	}
	public String GetFamily() {
		return Family;
	}
	public void SetFamily(String family) {
		this.Family=family;
	}
}

