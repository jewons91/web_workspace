package ch11;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class ListenerExam implements ServletContextListener
	, ServletContextAttributeListener
	, HttpSessionListener
	, HttpSessionAttributeListener {

	public ListenerExam() {
	}

	public void contextDestroyed(ServletContextEvent sce) {
		sce.getServletContext().log("WAS 종료됨!!");
//    	WAS 종료된 후 해야 할 일들 명시
	}

	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().log("WAS 시작됨!!!!");
//    	WAS가 시작된 후 실행해야 할 것들을 명시
	}

	@Override
	public void attributeAdded(ServletContextAttributeEvent event) {
		event.getServletContext().log("컨텍스트에 속성 추가 : " + event.getValue());
	}
	
	@Override
	public void attributeRemoved(ServletContextAttributeEvent event) {
	}
	
//	session
//	session creation
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		se.getSession().getServletContext().log("session 생성 : " + se.getSession().getId());
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
	}
	
	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		event.getSession().getServletContext().log("session 속성 추가 : " + event.getValue());
	}
}