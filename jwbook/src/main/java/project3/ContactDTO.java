package project3;

public class ContactDTO {
	private int contact_id;
	private String name;
	private String phone_number;
	private String address;
	private String gubun_name;
	
	public int getContact_id() {
		return contact_id;
	}
	public void setContact_id(int contact_id) {
		this.contact_id = contact_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getGubun_name() {
		return gubun_name;
	}
	public void setGubun_name(String gubun_name) {
		this.gubun_name = gubun_name;
	}
	
	@Override
	public String toString() {
		return "ContactDTO [contact_id=" + contact_id + ", name=" + name + ", phone_number=" + phone_number
				+ ", address=" + address + ", gubun_name=" + gubun_name + "]";
	}
	
}
