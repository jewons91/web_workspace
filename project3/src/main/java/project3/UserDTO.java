package project3;

public class UserDTO {
	private String user_id;
	private String password;
	private String user_name;
	private String phone_number;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	
	@Override
	public String toString() {
		return "U_DTOTest [user_id=" + user_id + ", password=" + password + ", user_name=" + user_name
				+ ", phone_number=" + phone_number + "]";
	}
	
}
