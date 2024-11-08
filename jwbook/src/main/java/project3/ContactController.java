package project3;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

@WebServlet("/contactControl")
public class ContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ContactDAO dao;
	private ServletContext ctx;

	private final String START_PAGE = "project3/login.jsp";

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dao = new ContactDAO();
		ctx = getServletContext();
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String action = req.getParameter("action");
		Method method;
		String view = null;

		if (action == null) {
			action = "main";
		}
		try {
			method = this.getClass().getMethod(action, HttpServletRequest.class);
			view = (String) method.invoke(this, req);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			view = START_PAGE;
			req.setAttribute("error", "뭔가 잘못됨");
		} catch (SecurityException e) {
			e.printStackTrace();
			view = START_PAGE;
			req.setAttribute("error", "뭔가 잘못됨");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			view = START_PAGE;
			req.setAttribute("error", "뭔가 잘못됨");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			view = START_PAGE;
			req.setAttribute("error", "뭔가 잘못됨");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			view = START_PAGE;
			req.setAttribute("error", "뭔가 잘못됨");
		}
		if (view.startsWith("redirect:/")) {
			String redirectView = view.substring("redirect:/".length());
			resp.sendRedirect(redirectView);
		} else {
			RequestDispatcher disp = req.getRequestDispatcher(view);
			disp.forward(req, resp);
		}
	}

	public String main(HttpServletRequest request) {
		String user_id = getSessionUserId(request);
		if (user_id == null) {
			return START_PAGE;
		} else {
			return contactList(request);
		}
	}

	private String getSessionUserId(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		return user_id;
	}

	public String toRegister(HttpServletRequest request) {
		return "project3/register.jsp";
	}

	public String register(HttpServletRequest request) {
		UserDTO user = new UserDTO();
		try {
			BeanUtils.populate(user, request.getParameterMap());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		try {
			if (dao.checkUser(user)) {
				request.setAttribute("duplicated", "중복된 아이디가 있습니다");
				return "contactControl?action=toRegister";
			} else {
				dao.insertUser(user);
				return "redirect:/contactControl";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ctx.log("에러 발생");
			return "redirect:/contactControl";
		}
	}

	public String login(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserDTO user = new UserDTO();
		try {
			BeanUtils.populate(user, request.getParameterMap());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		try {
			if (dao.login(user)) {
				String user_name = dao.getUserName(user.getUser_id());
				session.setAttribute("user_id", user.getUser_id());
				session.setAttribute("user_name", user_name);
				return contactList(request);
			} else {
				return "redirect:/contactControl";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "redirect:/contactControl";
		}
	}

	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return "redirect:/contactControl";
	}

//	연락처 테이블 불러오기
	public String contactList(HttpServletRequest request) {
		String user_id = getSessionUserId(request);
		if (user_id == null) {
			return START_PAGE;
		}
		List<ContactDTO> contacts;
		try {
			contacts = dao.findAll(user_id);
			request.setAttribute("contacts", contacts);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "project3/contact.jsp";
	}

	public String findByType(HttpServletRequest request) {
		String user_id = getSessionUserId(request);
		String searchType = request.getParameter("searchType");
		String searchInput = request.getParameter("searchInput");
		List<ContactDTO> contacts;
		try {
			contacts = dao.findByType(user_id, searchInput, searchType);
			request.setAttribute("contacts", contacts);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "project3/contact.jsp";
	}

//	연락처 추가
	public String insertContact(HttpServletRequest request) {
		String user_id = getSessionUserId(request);
		ContactDTO contact = new ContactDTO();
		try {
			BeanUtils.populate(contact, request.getParameterMap());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		int checkContactId = 0;
		try {
			checkContactId = dao.getContactId(contact.getPhone_number());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (checkContactId == 0) {
			try {
				dao.insertContact(contact);
				checkContactId = dao.getContactId(contact.getPhone_number());
				dao.insertRelation(user_id, checkContactId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			try {
				if (!dao.checkRelation(user_id, checkContactId)) {
					dao.insertRelation(user_id, checkContactId);
				} else {
					request.setAttribute("duplicated", "중복된 번호가 있습니다");
					return "contactControl?action=contactList";
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "redirect:/contactControl?action=contactList";
	}

//	연락처 변경
	public String updateContact(HttpServletRequest request) {
		ContactDTO contact = new ContactDTO();
		try {
			BeanUtils.populate(contact, request.getParameterMap());
			int checkNumber = dao.getContactId(contact.getPhone_number());
			if (checkNumber == 0 || dao.getNumberById(contact.getContact_id()).equals(contact.getPhone_number())) {
				dao.update(contact);
			} else {
				request.setAttribute("duplicated", "중복된 번호가 있습니다");
				return "contactControl?action=contactList";
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "redirect:/contactControl?action=contactList";
	}

	public String deleteContact(HttpServletRequest request) {
		String user_id = getSessionUserId(request);
		int contact_id = Integer.parseInt(request.getParameter("contact_id"));
		try {
			dao.deleteRelation(user_id, contact_id);
			if (!dao.checkRelation(contact_id)) {
				dao.delete(contact_id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "redirect:/contactControl?action=contactList";
	}
}
