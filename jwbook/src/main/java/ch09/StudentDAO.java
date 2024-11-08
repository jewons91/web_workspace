package ch09;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDAO {
	private Connection con 			= null;
	private PreparedStatement pstmt = null;
	
//	드라이버, Connection 정보 저장 : URL, ID, PW
	private final String DRIVER 	= "oracle.jdbc.driver.OracleDriver";
	private final String URL 		= "jdbc:oracle:thin:@localhost:1521:xe";
	private final String USERID 	= "ora_user";
	private final String USERPW 	= "1234";
	
	public void open() {
//		드라이버 로딩
		try {
			Class.forName(DRIVER);
//			Connection 생성
			con = DriverManager.getConnection(URL, USERID, USERPW);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
//	Connection close()
	public void close() {
		try {
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
//	학생 등록 메소드 구현
//	입력화면(client) -> 서블릿 -> DTO -> DAO --> insert --> int
	public void insert(StudentDTO student) {
//		Connection open()
		open();
//		insert 처리
		String sql = "INSERT INTO student									"
				   + "VALUES (												"
				   + "(SELECT NVL(max(stu_id),1)+1 AS stu_id FROM student)	"
				   + ", ?, ?, ?, ?)											";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, student.getUsernm());
			pstmt.setString(2, student.getUniv());
			pstmt.setDate(3, student.getBirth());
			pstmt.setString(4, student.getEmail());
			
			pstmt.executeUpdate();	// int return
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
//		Connection close()
			close();
		}
	}
	
//	학생 목록 메소드 구현 => select => return ArrayList<StudentDTO>
	public ArrayList<StudentDTO> selectAll(){
		open();	// Connection 생성
		ArrayList<StudentDTO> students = new ArrayList<StudentDTO>();
//		DB에서 학생 목록을 꺼내서 ArrayList 담아서 리턴
		try {
//			꺼내기 시작
			pstmt = con.prepareStatement("select * from student");
			ResultSet rs = pstmt.executeQuery();	// ResultSet return
//			꺼내기 종료
			while(rs.next()) {
				StudentDTO student = new StudentDTO();
				
				student.setStu_id(rs.getInt("stu_id"));
				student.setUsernm(rs.getString("usernm"));
				student.setUniv(rs.getString("univ"));
				student.setBirth(rs.getDate("birth"));
				student.setEmail(rs.getString("email"));
				
				students.add(student);	// 리스트에 학생한명 추가
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return students;
	}
	
	
}
