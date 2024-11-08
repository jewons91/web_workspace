package ch09;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

@WebServlet("/studentControl")
public class StudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	StudentDAO dao;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dao = new StudentDAO(); // object 생성 => 실행가능한 상태
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		req에 저장된 값들의 인코딩 설정
		req.setCharacterEncoding("utf-8");
//		action의 값을 추출
//		URL : localhost:9091/jwbook/studentControl?action=list, insert
		String action = req.getParameter("action");
//		forward => view(.jsp)
		String view = "";

		switch (action) {
		case "list":
			view = list(req);
			break;
		case "insert":
			view = insert(req);
			break;
		}
		RequestDispatcher disp = req.getRequestDispatcher("/ch09/" + view);
		disp.forward(req, resp);
	}

//	DB에서 목록 가져오고 request 뷰를 선택해서 반환
	private String list(HttpServletRequest req) {
		req.setAttribute("students", dao.selectAll()); // ArrayList
		return "studentInfo.jsp"; // view
	}

	private String insert(HttpServletRequest req) {
		StudentDTO student = new StudentDTO();
		try {
			BeanUtils.populate(student, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
//		insert(DTO)
		dao.insert(student);
		return list(req); // view return
	}

}
