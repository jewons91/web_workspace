package ch10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewsDAO {
//	private Connection con			= null;
//	private PreparedStatement pstmt = null;

//	드라이버, Connection 정보 저장 : URL, ID, PW
	private final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private final String USERID = "ora_user";
	private final String USERPW = "1234";

	// Connectin 생성 : open()
	public Connection open() {
		Connection con = null; // 지역변수는 초기화 필요
		try {
//		1. 드라이버 로딩
			Class.forName(DRIVER);
//		2. Connection 생성
			con = DriverManager.getConnection(URL, USERID, USERPW);
		} catch (ClassNotFoundException e) {
			e.printStackTrace(); // 에러 발생시 에러 출력, 나중에 LOG 처리 변경된다.
		} catch (SQLException e) {
			e.printStackTrace();
		}

//		3. Connection 반환 : return
		return con;
	}

//	1. 뉴스 등록(추가:add) -> insert -> int
	public void addNews(NewsDTO newsDTO) throws Exception {
//		쿼리문 작성
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO news(newsid, title, img, content)		");
		sql.append("            values(									");
		sql.append("            (SELECT nvl(max(newsid),0)+1 FROM news)	");
		sql.append("             , ?, ?, ?								");
		sql.append(")													");
//		데이터베이스 처리(추가) -> insert
		Connection con = open();
		PreparedStatement pstmt = con.prepareStatement(sql.toString());

//		자동 close
		try (con; pstmt) {
			pstmt.setString(1, newsDTO.getTitle());
			pstmt.setString(2, newsDTO.getImg());
			pstmt.setString(3, newsDTO.getContent());

			pstmt.executeUpdate(); // insert 실행
		}

	}

//	2. 뉴스 전체 목록(id, title, regdt) => ALL news => List<NewsDTO> return

	public List<NewsDTO> findAll() throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT newsid			");
		sql.append("	 , title			");
		sql.append("	 , regdt  	 		"); // Date =>
		sql.append("  FROM news				");
		sql.append(" ORDER BY newsid ASC	");
		List<NewsDTO> newsList = new ArrayList<NewsDTO>();

		Connection con = open();
		PreparedStatement pstmt = con.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();

//		Connection creation
		try (con; pstmt; rs) {
//			데이터베이스 목록 추출 => ResultSet 저장
//			rs => DTO => List add
			while (rs.next()) {
				NewsDTO newsDto = new NewsDTO();
//				newsid 컬럼 추출 => DTO newsid 변수에 대입
				newsDto.setNewsid(rs.getInt("newsid"));
				newsDto.setTitle(rs.getString("title"));
				newsDto.setRegdt(rs.getDate("regdt"));

				newsList.add(newsDto);
			}
		}
		return newsList;
	}

//	3. 뉴스 한개 상세(id, title, img, regdt, content) => newsid
	public NewsDTO findById(int newsid) throws Exception {
		NewsDTO newsDTO = new NewsDTO();
//		데이터베이스에서 newsid에 해당하는 뉴스 추출 => DTO
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT *			");
		sql.append("  FROM news n		");
		sql.append(" WHERE n.newsid = ?	"); // ? <= newsid

		Connection con = open();
		PreparedStatement pstmt = con.prepareStatement(sql.toString());
		pstmt.setInt(1, newsid);

		ResultSet rs = pstmt.executeQuery();
		try (con; pstmt; rs) {
			if (rs.next()) { // null 이 아닐때 처리
				newsDTO.setNewsid(rs.getInt("newsid"));
				newsDTO.setTitle(rs.getString("title"));
				newsDTO.setImg(rs.getString("img"));
				newsDTO.setRegdt(rs.getDate("regdt"));
				newsDTO.setContent(rs.getString("content"));
			}
		}
		return newsDTO;
	}

//	4. 뉴스 삭제 => newsid delete
	public void delete(int newsid) throws Exception {
		String sql = "delete from news where newsid = ?";
		Connection con = open();
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, newsid);
		
		try(con;pstmt){
			if(pstmt.executeUpdate() == 0) {	// 삭제 안됨
				throw new SQLException("삭제 안됨");
			}
		}
	}
}
