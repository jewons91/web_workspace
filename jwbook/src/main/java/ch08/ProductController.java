package ch08;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// localhost:9091/jwbook/pcontrol?action=list
// localhost:9091/jwbook/pcontrol?action=info
@WebServlet("/pcontrol")
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ProductService service;

	public ProductController() {
		service = new ProductService();
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action"); // list, info
		String view = ""; // view 저장 변수

		switch (action) {
		case "list":
			view = list(req); // req setAttribute("products", list)
			break;
		case "info":
			view = info(req); // req setAttribute("product", ProductDTO)
			break;
		}
//		getServletContext() // WAS 가져온다, request가 들어있다
//				.getRequestDispatcher("/ch08/" + view) // view => ch08/jsp file
//				.forward(req, resp); // view
		RequestDispatcher disp = req.getRequestDispatcher("/ch08/" + view);
		disp.forward(req, resp);
	}

	private String info(HttpServletRequest req) {
		String id = req.getParameter("id"); // id 꺼낸다
		ProductDTO product = service.findById(id);
		req.setAttribute("product", product); // req에 한개 상품 정보 저장
		return "productInfo.jsp";
	}

	private String list(HttpServletRequest req) {
		req.setAttribute("products", service.findAll());
		return "productList.jsp"; // view :
	}
}
