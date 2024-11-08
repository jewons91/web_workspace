package ch12;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/test") // /api/test
public class RestApiExam {

	@GET // /api/test GET
	@Produces(MediaType.TEXT_PLAIN) // TEXT_PLAIN : 일반 문자열로 보내겠다
	public String sayHello() {
		return "Hello API Service"; // data 넘어간다
	}

	@POST // client에서 localhost:9091/jwbook/api/test POST 로 요청 처리
	public String sayHello(@QueryParam("msg") String msg) {
		return msg + " API Service"; // 클라이언트로 보내는 값
	}
}
