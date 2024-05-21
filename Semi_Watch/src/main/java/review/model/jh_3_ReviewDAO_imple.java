package review.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class jh_3_ReviewDAO_imple implements jh_3_ReviewDAO {
	
	// DB에 사용되는 객체
		private DataSource ds; // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
		// import javax.sql.DataSource;
		
		private Connection conn;
		private PreparedStatement pstmt;
		private ResultSet rs;
		
		
		// DB Connection Pool.txt 파일내용을 복붙한 내용
		// 생성자
		public jh_3_ReviewDAO_imple() {
			
			try {
			Context initContext = new InitialContext();
		    Context envContext  = (Context)initContext.lookup("java:/comp/env");
		    ds = (DataSource)envContext.lookup("jdbc/semioracle");

			}catch(NamingException e) {
				
			}
			
		}// end of public void ProductDAO_imple() {} 
		
		
		// 사용한 자원을 반납하는 close() 메소드 생성하기 
	   private void close() {
	      try {
	         if(rs != null)    {rs.close();    rs=null;}
	         if(pstmt != null) {pstmt.close(); pstmt=null;}
	         if(conn != null)  {conn.close();  conn=null;}
	      } catch(SQLException e) {
	         e.printStackTrace();
	      }
	   } // end of private void close() {} 

}
