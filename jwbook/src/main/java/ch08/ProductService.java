package ch08;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductService {
	// 상품정보 저장 HM, Product : 한개 상품 정보를 저장하는 object(DTO)
	Map<String, ProductDTO> products = new HashMap<>();

	// 기본 생성자 : 파라미터 없는 놈
	public ProductService() {
//		HM에 상품 추가
		ProductDTO p = new ProductDTO("101", "애플사과폰12", "애플", 1200000, "2020/12/12");
		products.put("101", p);

		p = new ProductDTO("102", "삼성우주폰21", "삼성", 1300000, "2021/02/02");
		products.put("102", p);

		p = new ProductDTO("103", "엘지듀얼폰", "엘지", 1500000, "2021/03/02");
		products.put("103", p);
	}

//	목록 반환 메소드
	public List<ProductDTO> findAll() {
		return new ArrayList<ProductDTO>(products.values());
	}

//	한개 상품 정보 반환 메소드
	public ProductDTO findById(String id) {
		return products.get(id); // ProductDTO return
	}
}
