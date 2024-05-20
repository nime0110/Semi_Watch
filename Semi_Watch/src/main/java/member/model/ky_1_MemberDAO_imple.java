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

public class ky_1_MemberDAO_imple implements ky_1_MemberDAO {
	
	// DB에 사용되는 객체
	private DataSource ds; // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	// import javax.sql.DataSource;
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	private AES256 aes;
	
	
	// DB Connection Pool.txt 파일내용을 복붙한 내용
	// 생성자
	public ky_1_MemberDAO_imple() {
		
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

	
	
	// 회원가입을 해주는 메소드 (tbl_member 테이블에 insert)
	@Override
	public int registerMember(MemberVO member) throws SQLException {
		int result = 0;
		
		try {
			conn = ds.getConnection();
			String sql = " insert into tbl_member(userid, pw, username, email, mobile, postcode, address, detail_address, extra_address, gender, birthday) " 
	                  + " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getUserid());
			pstmt.setString(2, Sha256.encrypt(member.getPw()) ); // 암호를 SHA256 알고리즘으로 단방향 암호화 시킨다.
			pstmt.setString(3, member.getUsername());	
			pstmt.setString(4, aes.encrypt(member.getEmail()) ); // 이메일을 AES256 알고리즘으로 양방향 암호화 시킨다.		 
			pstmt.setString(5, aes.encrypt(member.getMobile())); // 휴대폰번호를 AES256 알고리즘으로 양방향 암호화 시킨다.
			pstmt.setString(6, member.getPostcode());
			pstmt.setString(7, member.getAddress());
			pstmt.setString(8, member.getDetail_address());
			pstmt.setString(9, member.getExtra_address());
			pstmt.setString(10, member.getGender());
			pstmt.setString(11, member.getBirthday());
			
			result = pstmt.executeUpdate();
			
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();	
		} finally {
			close();
		}
		
		
		return result;
		
	}// end of public int registerMember(MemberVO member) throws SQLException
	
	
	// ID 중복검사 (tbl_member 테이블에서 userid 가 존재하면 true 를 리턴해주고, userid 가 존재하지 않으면 false 를 리턴한다)
	@Override
	public boolean idDuplicateCheck(String userid) throws SQLException {
		
		boolean isExists = false;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select userid"
					   + " from tbl_member"
					   + " where userid = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			
			rs = pstmt.executeQuery();
			
			isExists = rs.next(); // 행이 있으면(중복된 userid) true,
            		   			  // 행이 없으면(사용가능한 userid) false
			
			
		} finally {
			close();
		}
		
		return isExists;
		
	}// end of public boolean idDuplicateCheck(String userid) throws SQLException

	
	// email 중복검사 (tbl_member 테이블에서 email 가 존재하면 true 를 리턴해주고, email 가 존재하지 않으면 false 를 리턴한다)
	@Override
	public boolean emailDuplicateCheck(String email) throws SQLException {
		
		boolean isExists = false;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select email"
					   + " from tbl_member"
					   + " where email = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, aes.encrypt(email));
			
			rs = pstmt.executeQuery();
			
			isExists = rs.next(); // 행이 있으면(중복된 email) true,
            		   			  // 행이 없으면(사용가능한 email) false
			
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();	
		} finally {
			close();
		}
		
		return isExists;
		
	}// end of public boolean emailDuplicateCheck(String email) throws SQLException 


	// 로그인 처리
	@Override
	public MemberVO login(Map<String, String> paraMap) throws SQLException {
		
		MemberVO member = null; 
	      
		try {
			conn = ds.getConnection();
         
			String sql = " SELECT userid, username, mileage, pwdchangegap, "
					+ "        NVL( lastlogingap, trunc( months_between(sysdate, registerday) ) ) AS lastlogingap, " 
					+ "        idle, "
					+ "        email, mobile, "
					+ "        postcode, address, detail_address, extra_address "    
					+ " FROM "
					+ " ( select userid, username, mileage, "
					+ "          trunc( months_between(sysdate, lastpwdchangedate) ) AS pwdchangegap, " 
					+ "          registerday, idle, "
					+ "          email, mobile, "
					+ "          postcode, address, detail_address, extra_address "  
					+ "   from tbl_member "
					+ "   where status = 1 and userid = ? and pw = ? ) M "  
					+ " CROSS JOIN "
					+ " ( select trunc( months_between(sysdate, max(logindate)) ) AS lastlogingap " 
					+ "   from tbl_loginhistory "
					+ "   where fk_userid = ? ) H "; 
         
			pstmt = conn.prepareStatement(sql);
         
			pstmt.setString(1, paraMap.get("userid") );
			pstmt.setString(2, Sha256.encrypt(paraMap.get("pwd")) );
			pstmt.setString(3, paraMap.get("userid") );
         
			rs = pstmt.executeQuery();
         
			if(rs.next()) {
            
				member = new MemberVO();
            
				member.setUserid(rs.getString("userid"));
				member.setUsername(rs.getString("name"));
				member.setMileage(rs.getInt("mileage"));
            
				if( rs.getInt("lastlogingap") >= 12 ) {
					// 마지막으로 로그인 한 날짜시간이 현재시각으로 부터 1년이 지났으면 휴면으로 지정
					member.setIdle(1);   
               
					if(rs.getInt("idle") == 0) {
						// === tbl_member 테이블의 idle 컬럼의 값을 1로 변경하기 === //
						sql = " update tbl_member set idle = 1 "
								+ " where userid = ? ";
                  
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, paraMap.get("userid"));
                  
						pstmt.executeUpdate();
					}
				}
            
            
				// === 휴면이 아닌 회원만 tbl_loginhistory(로그인기록) 테이블에 insert 하기 시작 === //
				if( rs.getInt("lastlogingap") < 12 ) {
					sql = " insert into tbl_loginhistory(historyno, fk_userid, clientip) "
							+ " values(seq_historyno.nextval, ?, ?) ";
               
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, paraMap.get("userid"));
					pstmt.setString(2, paraMap.get("clientip"));
               
					pstmt.executeUpdate();
					// === 휴면이 아닌 회원만 tbl_loginhistory(로그인기록) 테이블에 insert 하기 끝 === //
               
					if( rs.getInt("pwdchangegap") >= 3 ) {
						// 마지막으로 암호를 변경한 날짜가 현재시각으로 부터 3개월이 지났으면 true
						// 마지막으로 암호를 변경한 날짜가 현재시각으로 부터 3개월이 지나지 않았으면 false 
                  
						member.setRequirePwdChange(true); // 로그인시 암호를 변경해라는 alert 를 띄우도록 할때 사용한다. 
					}
				}
            
				member.setEmail( aes.decrypt(rs.getString("email")) );
				member.setMobile( aes.decrypt(rs.getString("mobile")) );
				member.setPostcode( rs.getString("postcode") );
				member.setAddress( rs.getString("address") );
				member.setDetail_address( rs.getString("detail_address") );
				member.setExtra_address( rs.getString("extra_address") );
            
			}// end of if(rs.next())-----------------------
         
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			close();
		}
            
		return member;
	}// end of public MemberVO login(Map<String, String> paraMap) throws SQLException------

	
	// 아이디 찾기(성명, 이메일을 입력받아서 해당 사용자의 아이디를 알려준다.)
	@Override
	public String findUserid(Map<String, String> paraMap) throws SQLException {
		
		String userid = null;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select userid, email"
					   + " from tbl_member"
					   + " where status = 1 and username = ? and email = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("name"));
			pstmt.setString(2, aes.encrypt(paraMap.get("email")) );
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				userid = rs.getString("userid");
			}
			
			
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();	
		} finally {
			close();
		}
		
		return userid;
		
	}// end of public String findUserid(Map<String, String> paraMap) throws SQLException

	
	// 비밀번호 찾기(아이디, 이메일을 입력받아서 해당 사용자가 존재하는지 유무를 알려준다.)
	@Override
	public Map<String, Object> isUserExist(Map<String, String> paraMap) throws SQLException {
		
		boolean isUserExist = false;
      	   String name = null;
	       Map<String, Object> userMap = new HashMap<>();
	      
	      try {
	         conn = ds.getConnection();
	         String sql = " select username "
	                  + " from tbl_member "
	                  + " where status = 1 and userid = ? and email = ? ";
	         pstmt = conn.prepareStatement(sql);
	         
	         pstmt.setString(1, paraMap.get("userid"));
	         pstmt.setString(2, aes.encrypt( paraMap.get("email")));
	         
	         rs = pstmt.executeQuery();
	         if(rs.next()) {
	            name = rs.getString("username"); //username
	            isUserExist = true;
	         }
	         userMap.put("name", name); //username
	         userMap.put("isUserExist", isUserExist); //있는지 없는지 여부 가져오기
	         
	      } catch(UnsupportedEncodingException | GeneralSecurityException e) {
	         e.printStackTrace();
	      } finally {
	         close();
	      }
	      
	      
	      return userMap;
	}

	
	// 비밀번호 수정하기(맵에서 유저 아이디랑 새로운 비밀번호를 가져와서 데이터베이스 상에 업데이트 해준다.)
	@Override
	public int pwdUpdate(Map<String, String> paraMap) throws SQLException {
		
		int result = 0;

		try {
			conn = ds.getConnection();
			String sql = " update tbl_member set pw = ?, lastpwdchangedate = sysdate " + " where userid = ? ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, Sha256.encrypt(paraMap.get("new_pwd")));
			pstmt.setString(2, paraMap.get("userid"));

			result = pstmt.executeUpdate();

       } finally {
    	   close();
       }

	       return result;

	}// end of public int pwdUpdate(Map<String, String> paraMap) throws SQLException---

	
	
}
