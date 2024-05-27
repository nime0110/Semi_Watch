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

import org.apache.jasper.tagplugins.jstl.core.Catch;

import jakarta.servlet.jsp.tagext.TryCatchFinally;
import member.domain.MemberVO;
import review.domain.ReviewVO;
import shop.domain.ProductVO;
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

	
	// 회원가입유저 오토 로그인 처리
	@Override
	public MemberVO loginAfterReg(Map<String, String> paraMap) throws SQLException {
		
		MemberVO member = null;
		
		try {
			conn = ds.getConnection();
			
			String sql = " SELECT userid, username, email, mobile, postcode, address, detail_address, NVL( lastlogingap, trunc( months_between(sysdate, registerday)) ) AS lastlogingap "
					   + " FROM "
					   + " ( select userid, username, email, mobile, postcode, address||' '||extra_address AS address, detail_address, registerday "
					   + " from tbl_member "
					   + " where status = 1 and userid = ? and pw = ?) M "
					   + " CROSS JOIN "
					   + " ( select trunc( months_between(sysdate, max(logindate)) ) AS lastlogingap "
					   + " from tbl_loginhistory "
					   + " where fk_userid = ? ) H ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, paraMap.get("userid"));
			pstmt.setString(2, Sha256.encrypt(paraMap.get("pwd")));
			pstmt.setString(3, paraMap.get("userid"));
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				member = new MemberVO();
				
				member.setUserid(rs.getString("userid"));
				member.setUsername(rs.getString("username"));
				member.setEmail( aes.decrypt(rs.getString("email")) );
				member.setMobile( aes.decrypt(rs.getString("mobile")) );
				member.setPostcode(rs.getString("postcode"));
				member.setAddress(rs.getString("address"));
				member.setDetail_address(rs.getString("detail_address"));
				// loginuser VO에 추가할 것이 있다면 여기에 추가하시오.
			
				if( rs.getInt("lastlogingap") < 12) {
					sql = " insert into tbl_loginhistory(fk_userid, clientip) "
						+ " values(?, ?) ";
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, paraMap.get("userid"));
					pstmt.setString(2, paraMap.get("clientip"));
						
					
					pstmt.executeUpdate();
					
				}
			
			}// end of if(rs.next()) 
		} catch(UnsupportedEncodingException | GeneralSecurityException e) {
	         e.printStackTrace();
		} finally {
			close();
		}
		
		return member;
		
	}// end of public MemberVO loginAfterReg(Map<String, String> paraMap) throws SQLException 


	// 페이지 바 만들기 - 페이징 처리를 위한 검색이 있는/없는 리뷰에 대한 총페이지 수 알아오기
	@Override
	public int getTotalPage(Map<String, String> paraMap) throws SQLException {

		int totalPage = 0;
		
		try {
			 
			conn = ds.getConnection();
			
			String sql = " select ceil(count(*)/?) "
					+ " from tbl_review R JOIN tbl_product P "
					+ " on R.fk_pdno = P.pdno "
					+ " where fk_userid != 'admin' ";

			String colname = paraMap.get("searchType");
			String searchWord = paraMap.get("searchWord");
			
			//유저가 검색했다면 true;
		    boolean userSearch = (colname != null && !colname.trim().isEmpty()) && 
                    (searchWord != null && !searchWord.trim().isEmpty());
			
			if(userSearch) {
				 sql += " and " + colname + " like '%' || ? || '%' ";
			}
			
			pstmt = conn.prepareStatement(sql);
	
			pstmt.setInt(1, Integer.parseInt(paraMap.get("sizePerPage")));
			
			if(userSearch) {
				pstmt.setString(2, searchWord);
			}
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			totalPage = rs.getInt(1); //ceil(count(*)/?)
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close();
			}
		return totalPage;
		
	}// end of public int getTotalPage(Map<String, String> paraMap) throws SQLException	
	
	
	// 페이징 처리를 한 모든 리뷰 또는 검색한 리뷰 목록 보여주기
	@Override
	public List<ReviewVO> select_review_paging(Map<String, String> paraMap) throws SQLException {
		
		List<ReviewVO> reviewList = new ArrayList<>();
		
		try {
			
			conn = ds.getConnection();
			
			String colname = paraMap.get("searchType");
			String searchWord = paraMap.get("searchWord");
			
			//유저가 검색했다면 true;
		    boolean userSearch = (colname != null && !colname.trim().isEmpty()) && 
                    (searchWord != null && !searchWord.trim().isEmpty());
			
			String sql = " SELECT rno, reviewno, pdname, userid, username, brand, review_content, starpoint "
					+ " FROM "
					+ " ( "
					+ "    SELECT rownum AS rno, TO_NUMBER(R.reviewno) AS reviewno, P.pdname AS pdname, M.userid AS userid, "
					+ "    M.username AS username, P.brand AS brand, R.review_content AS review_content, R.starpoint AS starpoint "
					+ "    FROM tbl_review R "
					+ "    JOIN tbl_product P ON R.fk_pdno = P.pdno "
					+ "    JOIN tbl_member M ON R.fk_userid = M.userid "
					+ "    WHERE M.userid != 'admin' ";
			
			if(userSearch) {
				 sql +="AND " + colname + " LIKE '%' || ? || '%' ";
			}
				
			sql += " ORDER BY reviewno DESC) "
			    + " WHERE rno BETWEEN ? AND ? ";
			
			pstmt = conn.prepareStatement(sql);
			// (10-9) and 10 // => between 1 and 10
			
			int currentShowPageNo = Integer.parseInt(paraMap.get("currentShowPageNo")); // 현재 페이지위치
			int sizePerPage = Integer.parseInt(paraMap.get("sizePerPage")); //1페이지당 보여지는 회원명수			
			
			if(userSearch) {
				searchWord = searchWord.toUpperCase();
				
				pstmt.setString(1, searchWord);
				pstmt.setInt(2, (currentShowPageNo * sizePerPage) - (sizePerPage - 1));
				pstmt.setInt(3, (currentShowPageNo * sizePerPage));
			} else {
				pstmt.setInt(1, (currentShowPageNo * sizePerPage) - (sizePerPage - 1));
				pstmt.setInt(2, (currentShowPageNo * sizePerPage));
			}
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MemberVO mvo = new MemberVO();
				ProductVO pvo = new ProductVO();
				ReviewVO rvo = new ReviewVO();
				
				mvo.setUserid(rs.getString("userid"));
				mvo.setUsername(rs.getString("username"));
				pvo.setBrand(rs.getString("brand"));
				pvo.setPdname(rs.getString("pdname"));
				
				rvo.setMvo(mvo);
				rvo.setPvo(pvo);
				rvo.setReviewno(rs.getString("reviewno"));
				rvo.setReview_content(rs.getString("review_content"));
				rvo.setStarpoint(rs.getString("starpoint"));
				
				reviewList.add(rvo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return reviewList;
		
	}// end of public List<MemberVO> select_review_paging(Map<String, String> paraMap) throws SQLException 	

	
	// 뷰단(memberList.jsp)에서 "페이징 처리시 보여주는 순번 공식" 에서 사용하기 위해 검색이 있는 또는 검색이 없는 회원의 총개수 알아오기 시작
	@Override
	public int getTotalReviewCount(Map<String, String> paraMap) throws SQLException {
		
		int totalReviewCount = 0;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = "  select count(*) "
					+ " from tbl_review R JOIN tbl_product P "
					+ " on R.fk_pdno = P.pdno "
					+ " where fk_userid != 'admin' "; 
			 
			 
			String colname = paraMap.get("searchType");
			String searchWord = paraMap.get("searchWord");
			 
			
			//유저가 검색했다면 true;
		    boolean userSearch = (colname != null && !colname.trim().isEmpty()) && 
                    (searchWord != null && !searchWord.trim().isEmpty());
			
			if(userSearch) {
				 sql += " and " + colname + " like '%' || ? || '%' ";
			}
				
			
			pstmt = conn.prepareStatement(sql);
			// (10-9) and 10 // => between 1 and 10
		
			
			if(userSearch) { 
				pstmt.setString(1, searchWord);
			}
			
			rs = pstmt.executeQuery();
			rs.next();
			totalReviewCount = rs.getInt(1); 
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return totalReviewCount;
		
	}// end of public int getTotalReviewCount(Map<String, String> paraMap) throws SQLException 

	
	// 입력받은 reviewno 를 가지고 하나의 리뷰정보를 리턴시켜주는 메소드
	@Override
	public ReviewVO selectOneReview(String reviewno) throws SQLException {
		
		ReviewVO review = null;
		
		// System.out.println("확인용 reviewno : " + reviewno);
		
		try {
			conn = ds.getConnection();
			
			String sql = " SELECT R.reviewno AS reviewno, P.brand AS brand, P.pdname AS pdname, P.pdimg1 AS pdimg1, M.userid AS userid, M.username AS username, R.review_content AS review_content, R.starpoint AS starpoint, R.review_date AS review_date "
					+ " FROM tbl_review R JOIN tbl_product P "
					+ " ON R.fk_pdno = P.pdno JOIN tbl_member M "
					+ " on R.fk_userid = M.userid "
					+ " where reviewno = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, reviewno);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				review = new ReviewVO();
				
				ProductVO pvo = new ProductVO();
				MemberVO mvo = new MemberVO();
				
				review.setReviewno(rs.getString("reviewno"));
				pvo.setBrand(rs.getString("brand"));
				pvo.setPdname(rs.getString("pdname"));
				pvo.setPdimg1(rs.getString("pdimg1"));
				mvo.setUserid(rs.getString("userid"));
				mvo.setUsername(rs.getString("username"));
				review.setReview_content(rs.getString("review_content"));
				review.setStarpoint(rs.getString("starpoint"));
				review.setReview_date(rs.getString("review_date"));
				
				review.setPvo(pvo);
				review.setMvo(mvo);
				
			}
	
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
			
		return review;
	}// end of public ReviewVO selectOneReview(String userid) throws SQLException 

	
	// 입력받은 reviewno 를 가지고 리뷰정보를 삭제해주는 메소드
	@Override
	public int deleteOneReview(String reviewno) throws SQLException {
		int n = 0;
			
		try {
			conn = ds.getConnection();
			
			String sql = " delete from tbl_review where reviewno = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, reviewno);
			
			n = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return n;
	}// end of public int deleteOneReview(String reviewno) throws SQLException 


	
}
