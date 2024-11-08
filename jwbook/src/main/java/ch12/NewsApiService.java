package ch12;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch10.NewsDAO;
import ch10.NewsDTO;

@Path("/news")
public class NewsApiService {
//	DAO 선언
	NewsDAO dao;

	public NewsApiService() {
		dao = new NewsDAO();
	}

//	뉴스 등록 (insert) => POST 받아야 한다
//	문자열(json)로 실패/성공 => 클라이언트(Consumes) 보낸다
	@POST // POST로 받겠다
	@Consumes(MediaType.APPLICATION_JSON) // 보내는 방식 설정 => JSON
	public String addNews(NewsDTO newsDto) {
		try {
			dao.addNews(newsDto);
		} catch (Exception e) {
			e.printStackTrace();
			return "News API:뉴스 등록 실패";
		}
		return "News API:뉴스 등록됨";
	}

//	뉴스 삭제 : localhost:9091/jwbook/api/news/뉴스번호, method=DELETE
	@DELETE
	@Path("{newsid}") // 받는 경로 설정만 한 것이다. 값을 추출하지 않았다
	public String delNews(@PathParam("newsid") int newsid) {
		try {
			dao.delete(newsid);
		} catch (Exception e) {
			e.printStackTrace();
			return "News API: 삭제 에러";
		}
		return "News API: 정상 삭제";
	}

//	뉴스 목록 조회
//	전체 뉴스(DTO) => ArrayList return => client => JSON 으로 넘어가게 할 것이다
	@GET // URL : localhost:9091/jwbook/api/news
	@Produces(MediaType.APPLICATION_JSON) // 서버(Produces)에서 클라이언트(Consumes)로 보내는 데이터 형식 지정
	public List<NewsDTO> getNewsList() {
		List<NewsDTO> newsList = null;

		try {
			newsList = dao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newsList;
	}

//	특정 뉴스의 상세 조회 => newsid => select => GET
	@GET
	@Path("{newsid}") // URL 경로만 매핑, 추출 안했다
	@Produces(MediaType.APPLICATION_JSON)
	public NewsDTO getNews(@PathParam("newsid") int newsid) {
		NewsDTO newsDTO = null;
		try {
			newsDTO = dao.findById(newsid);
		} catch (Exception e) {
			e.printStackTrace(); // error => newsDTO = null 이다
		}
		return newsDTO;
	}
}