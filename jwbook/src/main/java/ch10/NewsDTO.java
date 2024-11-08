package ch10;

import java.sql.Date;

public class NewsDTO {
	private int newsid;
	private String title;
	private String img;
	private Date regdt;
	private String content;

	public int getNewsid() {
		return newsid;
	}

	public void setNewsid(int newsid) {
		this.newsid = newsid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Date getRegdt() {
		return regdt;
	}

	public void setRegdt(Date regdt) {
		this.regdt = regdt;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "NewsDTO [newsid=" + newsid + ", title=" + title + ", img=" + img + ", regdt=" + regdt + ", content="
				+ content + "]";
	}
}
