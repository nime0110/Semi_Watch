package member.model;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import util.security.AES256;
import util.security.SecrectMyKey;
import util.security.Sha256;

public class jh_3_MemberDAO_imple implements jh_3_MemberDAO {
	
	// DB에 사용되는 객체
	private DataSource ds; // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	// import javax.sql.DataSource;
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	private AES256 aes;
	
	
	// DB Connection Pool.txt 파일내용을 복붙한 내용
	// 생성자
	public jh_3_MemberDAO_imple() {
		
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

	
	
	// 기존 비밀번호와 입력한 비밀번호가 일치하는지 확인
	@Override
	public boolean pwdDuplicateCheck(Map<String, String> paraMap) throws SQLException {
		boolean isExists = false;
	      
	    try {
	    	conn = ds.getConnection();
	         
	        String sql = " select pw "
	                   + " from tbl_member "
	                   + " where userid = ? and pw = ? ";
	         
	        pstmt = conn.prepareStatement(sql); 
	        pstmt.setString(1, paraMap.get("userid"));
	        pstmt.setString(2, Sha256.encrypt(paraMap.get("password")));
	         
	        rs = pstmt.executeQuery();
	         
	        isExists = rs.next(); // 행이 있으면(현재 사용중인 비밀번호) true,
	                               // 행이 없으면(새로운 비밀번호) false
	         
	    } finally {
	        close();
	    }
	      
	    return isExists;
	    
	}// end of public boolean pwdDuplicateCheck------

	
	// 비밀번호 변경
	@Override
	public int updatePWD(Map<String, String> paraMap) throws SQLException {
		int result = 0;
		
		try {
			conn = ds.getConnection();
			System.out.println(Sha256.encrypt(paraMap.get("newPassword")));
			
			String sql  = " update tbl_member set pw = ?, lastpwdchangedate = sysdate "
						+ " where userid = ? ";
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, Sha256.encrypt(paraMap.get("newPassword")));
			pstmt.setString(2, paraMap.get("userid"));
			
			result = pstmt.executeUpdate();
			

		} finally {
			close();
		}
		
		return result;
	}// end of public int updatePWD-----

	
	// 이메일 변경
	@Override
	public int updateEmail(Map<String, String> paraMap) throws SQLException {
		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql  = " update tbl_member set email = ? "
						+ " where userid = ? ";
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, aes.encrypt(paraMap.get("newEmail")));
			pstmt.setString(2, paraMap.get("userid"));
			
			result = pstmt.executeUpdate();
			

		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return result;
	}// end of public int updateEmail ---

	// 전화번호 변경
	@Override
	public int updateMobile(Map<String, String> paraMap) throws SQLException {
		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql  = " update tbl_member set mobile = ? "
						+ " where userid = ? ";
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, aes.encrypt(paraMap.get("newMoblie")));
			pstmt.setString(2, paraMap.get("userid"));
			
			result = pstmt.executeUpdate();
			

		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return result;
	}// end of public int updateMobile---

	
	// 주소 변경
	@Override
	public int updatePost(Map<String, String> paraMap) throws SQLException {
		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql  = " update tbl_member "
						+ " set postcode = ? , address = ? , extra_address = ? , detail_address = ? "
						+ " where userid = ? ";
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, paraMap.get("postcode"));
			pstmt.setString(2, paraMap.get("addr"));
			pstmt.setString(3, paraMap.get("extraAddr"));
			pstmt.setString(4, paraMap.get("addressDetail"));
			pstmt.setString(5, paraMap.get("userid"));
			
			result = pstmt.executeUpdate();
			

		} finally {
			close();
		}
		
		return result;
	}// end of public int updatePost -----

	
	
	
	// 개인정보 상단 장바구니, 리뷰 건수
	@Override
	public Map<String, String> get_cart_review_cnt(String userid) throws SQLException {
		Map<String, String> cnt = new HashMap<>();
		
		try {
			conn = ds.getConnection();
			
			String sql  = " select nvl(sum(count(*)), 0) as cart_cnt "
						+ " from tbl_cart "
						+ " where fk_userid = ? "
						+ " group by cartno ";
			
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, userid);

			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String cart_cnt = String.valueOf(rs.getInt(1)); 
				
				cnt.put("cart_cnt", cart_cnt);
			}
			
			sql = " select nvl(sum(count(*)), 0) as review_cnt "
				+ " from tbl_review "
				+ " where fk_userid = ? "
				+ " group by reviewno ";
		
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, userid);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String review_cnt = String.valueOf(rs.getInt(1)); 
				
				cnt.put("review_cnt", review_cnt);
				
			}


		} finally {
			close();
		}
		
		return cnt;
		

	}// end of public Map<String, String> get_cart_review_cnt(String userid)-----

	
	// 프로필 이미지 업데이트
	@Override
	public int updateIMG(Map<String, String> paraMap) throws SQLException {
		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql  = " update tbl_member "
						+ " set userimg = ? "
						+ " where userid = ? ";
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, paraMap.get("userimg"));
			pstmt.setString(2, paraMap.get("userid"));
			
			result = pstmt.executeUpdate();
			

		} finally {
			close();
		}
		
		return result;
		
	}// end of public int updateIMG(Map<String, String> paraMap)-----

	
	
}
