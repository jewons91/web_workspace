package ch08;

public class ProductDTO {
	// 변수명은 컬럼명과 동일해야 한다
	private String id; // 상품(번호)아이디
	private String name; // 상품명
	private String maker; // 제조사
	private int price; // 가격
	private String date; // 제조일

	// 생성자
	public ProductDTO() {
	}

	public ProductDTO(String id, String name, String maker, int price, String date) {
		super();
		this.id = id;
		this.name = name;
		this.maker = maker;
		this.price = price;
		this.date = date;
	}

	// getter, setter
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	// toString
	@Override
	public String toString() {
		return "ProductDTO [id=" + id + ", name=" + name + ", maker=" + maker + ", price=" + price + ", date=" + date
				+ "]";
	}
}
