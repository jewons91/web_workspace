package ch12;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application; // Rest 실행

@ApplicationPath("/api") // /api 요청은 아래 클래스 받겠다 라는 의미다
public class RestConfig extends Application {
//	하위 경로 처리 서블릿 등록
	public Map<String, Object> getProperties(){
		Map<String, Object> properties = new HashMap<>();
		
		properties.put("jersey.config.server.provider.packages", "ch12");
		
		return properties;
	}
}
