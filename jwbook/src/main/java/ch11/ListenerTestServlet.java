package ch11;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// listener test
@WebServlet("/lts")
public class ListenerTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ServletContext sc; // WAS

//	한번만 호출
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		sc = getServletContext();
	}

//	요청 처리
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		sc.setAttribute("name", "홍길동"); // Context 처리
//		세션 추출
		HttpSession session = req.getSession();
//		session 특정값 저장
		session.setAttribute("ssName", session.getId() + " 세션에 저장");
	}
}
