package ch10;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.beanutils.BeanUtils;

// client 요청 => Multipart : 파일 업로드 기능
// location="c:/temp/img" 저장 위치 지정
@WebServlet("/news.nhn") // URL 매핑
@MultipartConfig(maxFileSize = 1024 * 1024 * 2, location = "c:/temp/img")
public class NewsController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private NewsDAO dao;
	private ServletContext ctx; // Module(jwbook) => Servlet Container

//	웹 리소스 시작 경로 저장
	private final String START_PAGE = "ch10/newsList.jsp";

//	new 된 이후 바로 실행 => init()
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dao = new NewsDAO();
		ctx = getServletContext(); // 상속받아서 바로 사용
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8"); // 입력값에 대한 한글 인코딩
//		action 추출 ?action=list, newsadd ,,,,
		String action = req.getParameter("action"); // list, add,
		Method method;
		String view = null;

		if (action == null) {
			action = "listNews"; // listNews(req) 호출
		}

//		action value 에 대한 메소드 찾기 => listNews(HttpServletRequest req)
		try {
			method = this.getClass().getMethod(action, HttpServletRequest.class);
			view = (String) method.invoke(this, req);
		} catch (NoSuchMethodException e) { // 그런 이름을 가진 메소드가 없다
			e.printStackTrace();
			ctx.log("요청한 메소드(action) 없거든!!!"); // 서버에 출력
			view = START_PAGE; // "ch10/newsList.jsp"
			req.setAttribute("error", "action 파라미터가 잘못"); // 클라이언트 출력
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
//		메소드 실행 완료 => 처리 완료 => 뷰 이동 view = "redirect/X forward"
//		== 페이지 이동 (redirect, forward)
//		view = "redirect:/news.nhn?action=listNews
		if (view.startsWith("redirect:/")) { // redirect:/ 단어로 시작하면 true
			String rview = view.substring("redirect:/".length());
//			rview = "news.nhn?action=listNews" 남는다
//			DB 에서 insert, delete, update 했을 때 리다이렉트 처리 필요
			resp.sendRedirect(rview);
		} else { // forward
			RequestDispatcher disp = req.getRequestDispatcher(view);
			disp.forward(req, resp);
		}
	}

//	뉴스 추가 메소드 => insert => view return "redirect:/url mapping"
//	parameter : DTO, return view
	public String addNews(HttpServletRequest request) {
		NewsDTO newsDTO = new NewsDTO();
		try {
			Part part = request.getPart("file"); // 정상 : 파일이 서버로 넘어왔다
			String fileName = getFileName(part); // 정상 : 파일명 추출
			if (fileName != null && !fileName.isEmpty()) {
				part.write(fileName); // 파일 저장
			}
//			DTO : title, content, img
			BeanUtils.populate(newsDTO, request.getParameterMap());
			newsDTO.setImg("/img/" + fileName);
//			데이터베이스 처리
			dao.addNews(newsDTO);
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("뉴스 추가 에러");
			request.setAttribute("error", "뉴스 추가 에러");
			return listNews(request); // 에러 발생시 목록 : forward => request 전달
		}
		return "redirect:/news.nhn?action=listNews"; // 목록으로 간다
	}

//	part안에 파일 정보 들어있다 => 파일명 추출
	private String getFileName(Part part) {
		String fileName = null;
//		part에서 추출
//		part에서 헤더 가져온다
		String header = part.getHeader("content-disposition");
		System.out.println("header : " + header);
//		header : form-data; name="file"; filename="스크린샷 2024-05-29 170831.png"
		int start = header.indexOf("filename=");
		fileName = header.substring(start + 10, header.length() - 1);
		ctx.log("파일명 : " + fileName);
		return fileName;
	}

//	메인페이지 목록 => select => view return : .jsp forwarding
//	parameter : X , return view , 
	public String listNews(HttpServletRequest request) {
//		select => AL => request set
		List<NewsDTO> list;
//		dao 모든 메소드에 throws Exception
		try {
			list = dao.findAll(); // 정상 처리된 경우
			request.setAttribute("newsList", list); // 여기까지 정상처리된 경우
		} catch (Exception e) {
//			비정상 처리된 경우
//			1. 서버 에러 상태 출력
			ctx.log("뉴스 목록 생성 과정에서 문제 발생!!");
			ctx.log(e.getMessage());
//			2. 클라이언트에 에러 상태 넘긴다(request)
			request.setAttribute("error", "약간의 처리안됨");
		}
		return "ch10/newsList.jsp"; // view return
	}

//	뉴스 상세 메소드 => select
//	parameter : request(int newsid), return view, forward => jsp
	public String getNews(HttpServletRequest request) {
//		1. newsid 추출
		int newsid = Integer.parseInt(request.getParameter("newsid"));
		try {
//		2. DAO => DTO
			NewsDTO newsDTO = dao.findById(newsid); // DTO return
//		3. request에 DTO를 저장
			request.setAttribute("newsDTO", newsDTO); // 여기까지 정상
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("뉴스 상세 에러");
			request.setAttribute("error", "뉴스 상세 에러");
		}
		return "ch10/newsView.jsp"; // jsp forward
	}

//	뉴스 삭제 => delete => view return "redirect:/url mapping"
//	parameter : int newsid, return view
	public String deleteNews(HttpServletRequest request) {
//		id 추출
		int newsid = Integer.parseInt(request.getParameter("newsid"));
		try {
//		dao에 id 넣어서 삭제
			dao.delete(newsid);
		} catch (Exception e) { // 여기가 실행된다는 것은 에러 발생
			e.printStackTrace();
			ctx.log("뉴스 삭제 과정에서 문제 발생");
			request.setAttribute("error", "삭제 안됨");
//			삭제 안됨, 목록 보여주고(listNews()), 에러(req) 출력
			return listNews(request);
		}
//		에러 처리 = 서버, 클라이언트(req 저장)
		return "redirect:/news.nhn?action=listNews";
	}
}
