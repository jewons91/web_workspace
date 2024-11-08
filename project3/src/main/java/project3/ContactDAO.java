package project3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {
	private final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private final String USERID = "ora_user";
	private final String USERPW = "1234";

	private Connection open() {
		Connection con = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USERID, USERPW);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

//	중복 ID 확인
	public boolean checkUser(UserDTO user) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT *");
		sql.append("  FROM user_information a");
		sql.append(" WHERE a.USER_ID = ?");

		Connection con = open();
		PreparedStatement pstmt = con.prepareStatement(sql.toString());
		pstmt.setString(1, user.getUser_id());
		ResultSet rs = pstmt.executeQuery();

		boolean duplicatedID;
		try (con; pstmt; rs) {
			if (rs.next()) {
				duplicatedID = true;
			} else {
				duplicatedID = false;
			}
		}
		return duplicatedID;
	}

//	회원가입
	public void insertUser(UserDTO user) throws SQLException {
		String sql = "INSERT INTO user_information VALUES (?,?,?)";

		Connection con = open();
		PreparedStatement pstmt = con.prepareStatement(sql);

		try (con; pstmt) {
			pstmt.setString(1, user.getUser_id());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getUser_name());

			pstmt.executeUpdate();
		}
	}

//	로그인
	public boolean login(UserDTO user) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT *					");
		sql.append("  FROM user_information ui	");
		sql.append(" WHERE ui.USER_ID = ?		");
		sql.append("   AND ui.PASSWORD = ?		");

		Connection con = open();
		PreparedStatement pstmt = con.prepareStatement(sql.toString());
		pstmt.setString(1, user.getUser_id());
		pstmt.setString(2, user.getPassword());
		ResultSet rs = pstmt.executeQuery();
		boolean login;
		try (con; pstmt; rs) {
			if (rs.next()) {
				login = true;
			} else {
				login = false;
			}
		}
		return login;
	}

//	유저 이름 가져오기
	public String getUserName(String user_id) throws SQLException {
		StringBuilder sql = new StringBuilder();
		String user_name;
		sql.append("SELECT a.USER_NAME 			");
		sql.append("  FROM user_information a	");
		sql.append(" WHERE a.USER_ID = ?		");
		Connection con = open();
		PreparedStatement pstmt = con.prepareStatement(sql.toString());
		pstmt.setString(1, user_id);
		ResultSet rs = pstmt.executeQuery();
		try (con; pstmt; rs) {
			if (rs.next()) {
				user_name = rs.getString("user_name");
			} else {
				throw new SQLException("아이디 없음");
			}
		}
		return user_name;
	}

//	전체목록 가져오기
	public List<ContactDTO> findAll(String user_id) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT c.CONTACT_ID 							");
		sql.append("	 , c.NAME 									");
		sql.append("	 , c.PHONE_NUMBER 							");
		sql.append("	 , c.ADDRESS 								");
		sql.append("	 , (SELECT g.gubun_name						");
		sql.append("	 	  FROM gubun g 							");
		sql.append("	 	 WHERE g.gubun_id = c.GUBUN_ID 			");
		sql.append("	   ) AS gubun_name							");
		sql.append("  FROM contact c 								");
		sql.append(" WHERE c.CONTACT_ID IN (SELECT r.contact_id		");
		sql.append(" 						  FROM relation r 		");
		sql.append(" 						 WHERE r.user_id LIKE ?)");
		List<ContactDTO> contacts = new ArrayList<ContactDTO>();

		Connection con = open();
		PreparedStatement pstmt = con.prepareStatement(sql.toString());
		pstmt.setString(1, user_id);
		ResultSet rs = pstmt.executeQuery();

		try (con; pstmt; rs) {
			while (rs.next()) {
				ContactDTO contact = new ContactDTO();
				contact.setContact_id(rs.getInt("contact_id"));
				contact.setName(rs.getString("name"));
				contact.setPhone_number(rs.getString("phone_number"));
				contact.setAddress(rs.getString("address"));
				contact.setGubun_name(rs.getString("gubun_name"));
				contacts.add(contact);
			}
		}
		return contacts;
	}

//	검색하여 목록가져오기
	public List<ContactDTO> findByType(String user_id, String searchInput, String searchType) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT c.CONTACT_ID 									");
		sql.append("	 , c.NAME 											");
		sql.append("	 , c.PHONE_NUMBER 									");
		sql.append("	 , c.ADDRESS 										");
		sql.append("	 , (SELECT g.gubun_name								");
		sql.append("	 	  FROM gubun g 									");
		sql.append("	 	 WHERE g.gubun_id = c.GUBUN_ID 					");
		sql.append("	   ) AS gubun_name									");
		sql.append("  FROM contact c 										");
		sql.append(" WHERE c.CONTACT_ID IN (SELECT r.contact_id				");
		sql.append(" 						  FROM relation r 				");
		sql.append("		 						 WHERE r.user_id LIKE ?)");

		if (searchType.equals("name")) {
			sql.append("AND c.name LIKE ?");
		} else if (searchType.equals("phone_number")) {
			sql.append("AND c.PHONE_NUMBER LIKE ?");
		} else if (searchType.equals("address")) {
			sql.append("AND c.address LIKE ?");
		} else if (searchType.equals("gubun_name")) {
			sql.append("AND c.GUBUN_ID = (SELECT gubun_id			");
			sql.append("					FROM gubun				");
			sql.append("				   WHERE gubun_name LIKE ?)	");
		}
		searchInput = "%" + searchInput + "%";

		Connection con = open();
		PreparedStatement pstmt = con.prepareStatement(sql.toString());
		pstmt.setString(1, user_id);
		pstmt.setString(2, searchInput);
		ResultSet rs = pstmt.executeQuery();

		List<ContactDTO> contacts = new ArrayList<ContactDTO>();
		try (con; pstmt; rs) {
			while (rs.next()) {
				ContactDTO contact = new ContactDTO();
				contact.setContact_id(rs.getInt("contact_id"));
				contact.setName(rs.getString("name"));
				contact.setPhone_number(rs.getString("phone_number"));
				contact.setAddress(rs.getString("address"));
				contact.setGubun_name(rs.getString("gubun_name"));
				contacts.add(contact);
			}
		}
		return contacts;
	}

//	중복확인 확인하면서 연락처 아이디 가져오기
	public int getContactId(String phone_number) throws SQLException {
		StringBuilder sql = new StringBuilder();
		int contact_id;
		sql.append("SELECT a.CONTACT_ID			");
		sql.append("  FROM contact a			");
		sql.append(" WHERE a.PHONE_NUMBER = ?	");
		Connection con = open();
		PreparedStatement pstmt = con.prepareStatement(sql.toString());
		pstmt.setString(1, phone_number);
		ResultSet rs = pstmt.executeQuery();

		try (con; pstmt; rs) {
			if (rs.next()) {
				contact_id = rs.getInt("contact_id");
				return contact_id;
			} else {
				contact_id = 0;
				return contact_id;
			}
		}
	}

//	구분 번호 가져오기
	private String getGubunId(String gubun_name) throws SQLException {
		StringBuilder sql = new StringBuilder();
		String gubun_id;
		sql.append("SELECT a.GUBUN_ID		");
		sql.append("  FROM gubun a			");
		sql.append(" WHERE a.GUBUN_NAME = ?	");
		Connection con = open();
		PreparedStatement pstmt = con.prepareStatement(sql.toString());
		pstmt.setString(1, gubun_name);
		ResultSet rs = pstmt.executeQuery();
		try (con; pstmt; rs) {
			if (rs.next()) {
				gubun_id = rs.getString("gubun_id");
			} else {
				throw new SQLException("원하는 정보 찾지 못함");
			}
		}
		return gubun_id;
	}

//	연락처 추가
	public void insertContact(ContactDTO contact) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO contact 									");
		sql.append("VALUES (												");
		sql.append("		(SELECT nvl(MAX(CONTACT_ID),0)+1 AS contact_id	");
		sql.append("   		   FROM contact									");
		sql.append("		)												");
		sql.append("		,?,?,?,?)										");

		Connection con = open();
		PreparedStatement pstmt = con.prepareStatement(sql.toString());
		pstmt.setString(1, contact.getName());
		pstmt.setString(2, contact.getPhone_number());
		pstmt.setString(3, contact.getAddress());
		pstmt.setString(4, getGubunId(contact.getGubun_name()));

		try (con; pstmt) {
			if (pstmt.executeUpdate() == 0) {
				throw new SQLException("추가 안됨");
			}
		}
	}

//	관계테이블 중복 확인
	public boolean checkRelation(String user_id, int contact_id) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT *				");
		sql.append("  FROM relation a		");
		sql.append(" WHERE a.user_id = ?	");
		sql.append("   AND a.CONTACT_ID = ?	");

		Connection con = open();
		PreparedStatement pstmt = con.prepareStatement(sql.toString());
		pstmt.setString(1, user_id);
		pstmt.setInt(2, contact_id);
		ResultSet rs = pstmt.executeQuery();
		boolean duplicatedRelation;
		try (con; pstmt; rs) {
			if (rs.next()) {
				duplicatedRelation = true;
			} else {
				duplicatedRelation = false;
			}
		}
		return duplicatedRelation;
	}

//	관계테이블 추가
	public void insertRelation(String user_id, int contact_id) throws SQLException {
		String sql = "INSERT INTO relation VALUES (?,?)";
		Connection con = open();
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, user_id);
		pstmt.setInt(2, contact_id);

		try (con; pstmt) {
			if (pstmt.executeUpdate() == 0) {
				throw new SQLException("관계 테이블 추가 안됨");
			}
		}
	}

//	연락처 수정
	public void update(ContactDTO contact) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE contact			");
		sql.append("   SET name = ?			");
		sql.append("   	 , PHONE_NUMBER = ?	");
		sql.append("   	 , ADDRESS = ?		");
		sql.append("   	 , GUBUN_ID = ?		");
		sql.append(" WHERE CONTACT_ID = ?	");

		Connection con = open();
		PreparedStatement pstmt = con.prepareStatement(sql.toString());

		try (con; pstmt) {
			pstmt.setString(1, contact.getName());
			pstmt.setString(2, contact.getPhone_number());
			pstmt.setString(3, contact.getAddress());
			pstmt.setString(4, getGubunId(contact.getGubun_name()));
			pstmt.setInt(5, contact.getContact_id());

			if (pstmt.executeUpdate() == 0) {
				throw new SQLException("수정 안됨");
			}
		}
	}

//	수정 중복번호 아니면서 같은 번호인지 확인
	public String getNumberById(int contact_id) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.PHONE_NUMBER	");
		sql.append("  FROM contact a		");
		sql.append(" WHERE a.contact_id = ?	");

		Connection con = open();
		PreparedStatement pstmt = con.prepareStatement(sql.toString());
		pstmt.setInt(1, contact_id);
		ResultSet rs = pstmt.executeQuery();
		String phone_number;
		try (con; pstmt; rs) {
			if (rs.next()) {
				phone_number = rs.getString("phone_number");
			} else {
				phone_number = null;
			}
		}
		return phone_number;
	}

//	관계테이블에서 삭제
	public void deleteRelation(String user_id, int contact_id) throws SQLException {
		String sql = "DELETE FROM relation WHERE user_id = ? AND contact_id = ?";

		Connection con = open();
		PreparedStatement pstmt = con.prepareStatement(sql);
		try (con; pstmt) {
			pstmt.setString(1, user_id);
			pstmt.setInt(2, contact_id);
			if (pstmt.executeUpdate() == 0) {
				throw new SQLException("삭제 안됨");
			}
		}
	}

//	해당 contactId가 관계테이블에 남아있는지 여부
	public boolean checkRelation(int contact_id) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT *				");
		sql.append("  FROM relation a		");
		sql.append(" WHERE a.CONTACT_ID = ?	");
		Connection con = open();
		PreparedStatement pstmt = con.prepareStatement(sql.toString());
		pstmt.setInt(1, contact_id);
		ResultSet rs = pstmt.executeQuery();
		boolean leftID;
		try (con; pstmt; rs) {
			if (rs.next()) {
				leftID = true;
			} else {
				leftID = false;
			}
		}
		return leftID;
	}

//	연락처 삭제
	public void delete(int contact_id) throws SQLException {
		String sql = "delete from contact where contact_id = ?";
		Connection con = open();
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, contact_id);

		try (con; pstmt) {
			if (pstmt.executeUpdate() == 0) {
				throw new SQLException("삭제 안됨");
			}
		}
	}

//	메시지 불러오기
	public List<MessageDTO> selectMessage(String to_id) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.message_id		");
		sql.append("	 , a.from_id		");
		sql.append("	 , a.content		");
		sql.append("	 , a.REG_DATE		");
		sql.append("  FROM message a		");
		sql.append(" WHERE a.to_id = ?		");
		sql.append(" ORDER BY a.REG_DATE ASC");

		Connection con = open();
		PreparedStatement pstmt = con.prepareStatement(sql.toString());
		pstmt.setString(1, to_id);
		ResultSet rs = pstmt.executeQuery();
		List<MessageDTO> messages = new ArrayList<MessageDTO>();
		try (con; pstmt; rs) {
			while (rs.next()) {
				MessageDTO message = new MessageDTO();
				message.setMessage_id(rs.getInt("message_id"));
				message.setFrom_id(rs.getString("from_id"));
				message.setContent(rs.getString("content"));
				message.setReg_date(rs.getTimestamp("reg_date"));
				messages.add(message);
			}
		}
		return messages;
	}

//	메시지 보내기
	public void insertMessage(MessageDTO messageDTO) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO message (message_id, from_id, to_id, content)	");
		sql.append("VALUES (													");
		sql.append("		(SELECT nvl(MAX(message_id),0)+1 AS message_id		");
		sql.append("   		   FROM message									");
		sql.append("		)													");
		sql.append("		, ?, ?, ?)											");
		Connection con = open();
		PreparedStatement pstmt = con.prepareStatement(sql.toString());
		pstmt.setString(1, messageDTO.getFrom_id());
		pstmt.setString(2, messageDTO.getTo_id());
		pstmt.setString(3, messageDTO.getContent());

		try (con; pstmt) {
			if (pstmt.executeUpdate() == 0) {
				throw new SQLException("메시지 전송 안됨");
			}
		}
	}

//	회원 목록 조회
	public List<UserDTO> selectUser(String user_id) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.USER_ID			");
		sql.append("	 , a.USER_name			");
		sql.append("  FROM user_information a	");
		sql.append(" WHERE NOT (a.USER_ID = ?)	");
		Connection con = open();
		PreparedStatement pstmt = con.prepareStatement(sql.toString());
		pstmt.setString(1, user_id);
		ResultSet rs = pstmt.executeQuery();
		List<UserDTO> users = new ArrayList<UserDTO>();
		try (con; pstmt; rs) {
			while (rs.next()) {
				UserDTO user = new UserDTO();
				user.setUser_id(rs.getString("user_id"));
				user.setUser_name(rs.getString("user_name"));
				users.add(user);
			}
		}
		return users;
	}

//	메시지 삭제
	public void deleteMessage(int message_id) throws SQLException {
		String sql = "DELETE FROM message WHERE message_id = ?";
		Connection con = open();
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, message_id);
		try (con; pstmt) {
			if (pstmt.executeUpdate() == 0) {
				throw new SQLException("메시지 삭제 안됨");
			}
		}
	}

}