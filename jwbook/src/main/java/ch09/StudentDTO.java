package ch09;

import java.sql.Date;

public class StudentDTO {
	private int stu_id;
	private String usernm;
	private String univ;
	private Date birth;
	private String email;
	
	public int getStu_id() {
		return stu_id;
	}
	public void setStu_id(int stu_id) {
		this.stu_id = stu_id;
	}
	public String getUsernm() {
		return usernm;
	}
	public void setUsernm(String usernm) {
		this.usernm = usernm;
	}
	public String getUniv() {
		return univ;
	}
	public void setUniv(String univ) {
		this.univ = univ;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "StudentDTO [stu_id=" + stu_id + ", usernm=" + usernm + ", univ=" + univ + ", birth=" + birth
				+ ", email=" + email + "]";
	}
	
}
