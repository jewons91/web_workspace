package project3;

import java.sql.Timestamp;

public class MessageDTO {
	private int message_id;
	private String from_id;
	private String to_id;
	private String content;
	private Timestamp reg_date;
	
	public int getMessage_id() {
		return message_id;
	}
	public void setMessage_id(int message_id) {
		this.message_id = message_id;
	}
	public String getFrom_id() {
		return from_id;
	}
	public void setFrom_id(String from_id) {
		this.from_id = from_id;
	}
	public String getTo_id() {
		return to_id;
	}
	public void setTo_id(String to_id) {
		this.to_id = to_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getReg_date() {
		return reg_date;
	}
	public void setReg_date(Timestamp reg_date) {
		this.reg_date = reg_date;
	}
	
	@Override
	public String toString() {
		return "MessageDTO [message_id=" + message_id + ", from_id=" + from_id + ", to_id=" + to_id + ", content="
				+ content + ", reg_date=" + reg_date + "]";
	}
	
}
