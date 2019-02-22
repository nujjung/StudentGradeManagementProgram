package Controller;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.spi.DirStateFactory.Result;

import Model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StudentDAO {
	public static ArrayList<Student> dbArrayList = new ArrayList<>();
	// 학생 등록하는 함수
	public static int insertStudentData(Student student) {
		int count =0;
		// 1.1 데이터베이스에 학생테이블에 입력 하는 쿼리문
		StringBuffer insertStudent = new StringBuffer();
		insertStudent.append("insert into student ");
		insertStudent.append("(no,name,grade,ban,gender,kor,eng,mat,sci,soc,mus,total,avg,date,imagepath) ");
		insertStudent.append("values ");
		insertStudent.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
		
		
//		String insertStudent = "insert into student "
//				+ "(no,name,grade,ban,gender,kor,eng,mat,sci,soc,mus,total,avg,date) " + "values "
//				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		
		// 1.2데이터 베이스 커넥션을 가져와야 한다.
		Connection con = null;

		// 1.3쿼리문을 실행해야할 statement를 만들어야 한다
		PreparedStatement psmt = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(insertStudent.toString());
			// 1.4쿼리문 실제 데이터 연결
			psmt.setString(1,student.getNo());
			psmt.setString(2,student.getName());
			psmt.setString(3,student.getGread());
			psmt.setString(4,student.getBan());
			psmt.setString(5,student.getGender());
			psmt.setString(6,student.getKor());
			psmt.setString(7,student.getEng());
			psmt.setString(8,student.getMat());
			psmt.setString(9,student.getSci());
			psmt.setString(10,student.getSoc());
			psmt.setString(11,student.getMus());
			psmt.setString(12,student.getTotal());
			psmt.setString(13,student.getAvg());
			psmt.setString(14,student.getDate());
			psmt.setString(15,student.getImagepath());

			// 1.5쿼리문 실제 데이터를 연결한 쿼리문 실행한다.
			count = psmt.executeUpdate();
			if (count == 0) {
				MainController.callAlert("쿼리 실패 : 쿼리문 삽입 실패");
				return count;
			}
		} catch (Exception e) {
			e.printStackTrace();
			MainController.callAlert("삽입 실패 : 데이터 베이스 삽입 실패");
		} finally {
			// 1.6 자원 객체를 닫아야 한다.
			try {
				if (psmt != null) {psmt.close();}
				if (con != null) {con.close();}
				

			} catch (Exception e) {MainController.callAlert("자원 닫기 실패 : psmt con 닫기실패");}
		}
		return count;
	}

	//테이블에서 전체 내용을 모두 가져오는 함수
	public static ArrayList<Student> getStudentTotaldata(){
		
		// 1.1 데이터베이스에서 학생테이블에 있는 자료를 모두 가져오는  쿼리문
		String selectStudent = "select * from student";
		
		// 1.2데이터 베이스 커넥션을 가져와야 한다.
		Connection con = null;

		// 1.3쿼리문을 실행해야할 statement를 만들어야 한다
		PreparedStatement psmt = null;
		//1.4 쿼리문을 실행하고 나서 가져와야할 레코드를 담고있는 보자기 객체
		ResultSet rs = null;
		
		
		
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(selectStudent);
			
			// 1.5쿼리문 실제 데이터를 연결한 쿼리문 실행한다.(번개를 친다)
			//executeQuery문은 쿼리문을 실행해서 결과를 가져올 때 사용하는 번개문
			//executeUpdate 는 쿼리문을 실행해서 테이블에 저장을 할때 사용하는 번개문
			rs = psmt.executeQuery();
			if (rs == null) {
				MainController.callAlert("쿼리 실패 : 쿼리문 삽입 실패");
				return null;
			}
			while(rs.next()) {
				Student student = new Student(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8),
						rs.getString(9),
						rs.getString(10),
						rs.getString(11),
						rs.getString(12),
						rs.getString(13),
						rs.getString(14),
						rs.getString(15)
						);
			dbArrayList.add(student);
			
			}
		} catch (Exception e) {
			e.printStackTrace();
			MainController.callAlert("삽입 실패 : 데이터 베이스 삽입 실패");
		} finally {
			// 1.6 자원 객체를 닫아야 한다.
			try {
				if (psmt != null) {psmt.close();}
				if (con != null) {con.close();}
			} catch (Exception e) {MainController.callAlert("자원 닫기 실패 : psmt con 닫기실패");}
		}
		return dbArrayList;
	}

	//테이블 뷰에서 선택한 레코드를 데이터 베이스에서 삭제하는 함수.
	public static int deleteStudentData(String no) {
		// 1.1 데이터베이스에서 학생테이블에 있는 자료를 삭제하는 쿼리문
				String deleteStudent = "delete from student where no = ? ";
				
				// 1.2데이터 베이스 커넥션을 가져와야 한다.
				Connection con = null;

				// 1.3쿼리문을 실행해야할 statement를 만들어야 한다
				PreparedStatement psmt = null;
				//1.4 쿼리문을 실행하고 나서 가져와야할 레코드를 담고있는 보자기 객체
				int count = 0;
				
				
				
				try {
					con = DBUtility.getConnection();
					psmt = con.prepareStatement(deleteStudent);
					psmt.setString(1, no);//   ?  에 값을 전달하는 것이다.
					
					
					// 1.5쿼리문 실제 데이터를 연결한 쿼리문 실행한다.(번개를 친다)
					//executeQuery문은 쿼리문을 실행해서 결과를 가져올 때 사용하는 번개문
					//executeUpdate 는 쿼리문을 실행해서 테이블에 저장을 할때 사용하는 번개문
					count = psmt.executeUpdate();
					if (count == 0) {
						MainController.callAlert("삭제 실패 : 삭제 쿼리문 삽입 실패");
						return count;
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					MainController.callAlert("삭제 실패 : 삭제 삽입 실패");
				} finally {
					// 1.6 자원 객체를 닫아야 한다.
					try {
						if (psmt != null) {psmt.close();}
						if (con != null) {con.close();}
					} catch (Exception e) {MainController.callAlert("자원 닫기 실패 : psmt con 닫기실패");}
				}
		
		return count;
	}

	
	//4선택한 레코드를 수정한 레코드를 데이터 베이스테이블에 수정하는 함수
	public static int updateStudentDate(Student student) {
		int count =0;
		// 1.1 데이터베이스에 학생테이블에 수정 하는 쿼리문
		StringBuffer updateStudent = new StringBuffer();
		updateStudent.append("update student set ");
		updateStudent.append("kor=?,eng=?,mat=?,sci=?,soc=?,mus=?,total=?,avg=?,date=?,imagepath=? where no=? ");
		
//		String updateStudent = "update student set "
//				+ "kor=?,eng=?,mat=?,sci=?,soc=?,mus=?,total=?,avg=?,date=? where no=? ";
				
		
		// 1.2데이터 베이스 커넥션을 가져와야 한다.
		Connection con = null;

		// 1.3쿼리문을 실행해야할 statement를 만들어야 한다
		PreparedStatement psmt = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(updateStudent.toString());
			// 1.4쿼리문 실제 데이터 연결
			
			psmt.setString(1,student.getKor());
			psmt.setString(2,student.getEng());
			psmt.setString(3,student.getMat());
			psmt.setString(4,student.getSci());
			psmt.setString(5,student.getSoc());
			psmt.setString(6,student.getMus());
			psmt.setString(7,student.getTotal());
			psmt.setString(8,student.getAvg());
			psmt.setString(9,student.getDate());
			psmt.setString(10,student.getImagepath());
			psmt.setString(11,student.getNo());

			// 1.5쿼리문 실제 데이터를 연결한 쿼리문 실행한다.
			count = psmt.executeUpdate();
			if (count == 0) {
				MainController.callAlert("수정쿼리 실패 : 쿼리문 삽입 실패");
				return count;
			}
		} catch (Exception e) {
			e.printStackTrace();
			MainController.callAlert("수정 실패 : 데이터 베이스 삽입 실패");
		} finally {
			// 1.6 자원 객체를 닫아야 한다.
			try {
				if (psmt != null) {psmt.close();}
				if (con != null) {con.close();}
				

			} catch (Exception e) {MainController.callAlert("자원 닫기 실패 : psmt con 닫기실패");}
		}
		
		
		
		return count;
	}
	
	
	
	
	

}
