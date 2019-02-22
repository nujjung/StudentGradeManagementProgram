package Controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtility {
	
	public static Connection getConnection() {
		Connection con = null;
		//1 MySql database class 로드
		try {
			Class.forName("com.mysql.jdbc.Driver");
		
		//2주소와 아이디 비밀번호를 통해서 접속 요청한다.
		con = DriverManager.getConnection("jdbc:mysql://localhost/studentdb", "root", "123456");
		
		MainController.callAlert("데이터 베이스 연결 성공: DB연결 성공 하셨습니다.");
		} catch (Exception e) {
			MainController.callAlert("데이터 베이스 연결 실패: DB연결 오류.");
			return null;
		}
		return con;
	}
	
	
}
