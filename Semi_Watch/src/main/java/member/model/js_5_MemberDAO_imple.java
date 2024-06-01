package member.model;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import member.domain.MemberVO;
import util.security.AES256;
import util.security.SecrectMyKey;
import util.security.Sha256;

public class js_5_MemberDAO_imple implements js_5_MemberDAO {
	
	// DB에 사용되는 객체
	private DataSource ds; // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	// import javax.sql.DataSource;
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	private AES256 aes;
	
	
	// DB Connection Pool.txt 파일내용을 복붙한 내용
	// 생성자
	public js_5_MemberDAO_imple() {
		
		try {
			Context initContext = new InitialContext();
		    Context envContext  = (Context)initContext.lookup("java:/comp/env");
		    ds = (DataSource)envContext.lookup("jdbc/semioracle");
		    
		    aes = new AES256(SecrectMyKey.KEY);
		    // SecrectMyKey.KEY 은 우리가 만든 암호화/복호화 키이다.
	
		}catch(NamingException e) {
			
		}catch(UnsupportedEncodingException e) {
			
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


	// 월별 방문횟수 통계(로그인)
	@Override
	public List<Map<String, String>> Purchase_byMonthvisitCnt(String userid) throws SQLException {
		
		List<Map<String, String>> myPurchase_map_List = new ArrayList<>();
	      
	      try {
	    	  
	         conn = ds.getConnection();
	         
	         String sql = " select count(fk_userid) AS TOTALVISIT_CNT "
	         		+ "       , SUM( decode( to_char(logindate,'mm'), '01', 1 , 0) ) AS M_01 "
	         		+ "       , SUM( decode( to_char(logindate,'mm'), '02', 1 , 0) ) AS M_02 "
	         		+ "       , SUM( decode( to_char(logindate,'mm'), '03', 1 , 0) ) AS M_03 "
	         		+ "       , SUM( decode( to_char(logindate,'mm'), '04', 1, 0) ) AS M_04 "
	         		+ "       , SUM( decode( to_char(logindate,'mm'), '05', 1, 0) ) AS M_05 "
	         		+ "       , SUM( decode( to_char(logindate,'mm'), '06', 1, 0) ) AS M_06 "
	         		+ "       , SUM( decode( to_char(logindate,'mm'), '07', 1, 0) ) AS M_07 "
	         		+ "       , SUM( decode( to_char(logindate,'mm'), '08', 1, 0) ) AS M_08 "
	         		+ "       , SUM( decode( to_char(logindate,'mm'), '09', 1, 0) ) AS M_09 "
	         		+ "       , SUM( decode( to_char(logindate,'mm'), '10', 1, 0) ) AS M_10 "
	         		+ "       , SUM( decode( to_char(logindate,'mm'), '11', 1, 0) ) AS M_11 "
	         		+ "       , SUM( decode( to_char(logindate,'mm'), '12', 1, 0) ) AS M_12 "
	         		+ "  from tbl_loginhistory "
	         		+ "  where to_char(logindate,'yyyy') = to_char(sysdate,'yyyy') AND "
	         		+ "        fk_userid != 'admin' "
	         		+ "  group by to_char(logindate,'mm') ";
	         
	         pstmt = conn.prepareStatement(sql);
	   
	         rs = pstmt.executeQuery();
	                  
	         while(rs.next()) {
	            String totalvist_cnt = rs.getString("TOTALVISIT_CNT");
	            String m_01 = rs.getString("M_01");
	            String m_02 = rs.getString("M_02");
	            String m_03 = rs.getString("M_03");
	            String m_04 = rs.getString("M_04");
	            String m_05 = rs.getString("M_05");
	            String m_06 = rs.getString("M_06");
	            String m_07 = rs.getString("M_07");
	            String m_08 = rs.getString("M_08");
	            String m_09 = rs.getString("M_09");
	            String m_10 = rs.getString("M_10");
	            String m_11 = rs.getString("M_11");
	            String m_12 = rs.getString("M_12");
	            
	            Map<String, String> map = new HashMap<>();
	            map.put("totalvist_cnt", totalvist_cnt);

	            map.put("m_01", m_01);
	            map.put("m_02", m_02);
	            map.put("m_03", m_03);
	            map.put("m_04", m_04);
	            map.put("m_05", m_05);
	            map.put("m_06", m_06);
	            map.put("m_07", m_07);
	            map.put("m_08", m_08);
	            map.put("m_09", m_09);
	            map.put("m_10", m_10);
	            map.put("m_11", m_11);
	            map.put("m_12", m_12);
	            
	            myPurchase_map_List.add(map);
	         } // end of while----------------------------------
	                  
	      } finally {
	         close();
	      }
	      
	      return myPurchase_map_List;
	      
	} // public List<Map<String, String>> Purchase_byMonthvisitCnt(String userid) throws SQLException {

	
	
}
