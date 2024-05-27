package member.model;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import member.domain.MemberVO;
import shop.domain.ImageVO;
import util.security.AES256;
import util.security.SecrectMyKey;
import util.security.Sha256;

public class ss_2_MemberDAO_imple implements ss_2_MemberDAO {
	
	// DB에 사용되는 객체
	private DataSource ds; // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	// import javax.sql.DataSource;
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	private AES256 aes;
	
	
	// DB Connection Pool.txt 파일내용을 복붙한 내용
	// 생성자
	public ss_2_MemberDAO_imple() {
		
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


	//로그인 처리 메소드
	@Override
	public MemberVO login(Map<String, String> paraMap) throws SQLException {

	    MemberVO member = null; // 만약 로그인했을때 해당 회원이 데이터베이스에 없다면 null반환
	    try {
	      conn = ds.getConnection();
	      String sql = " SELECT userid, username, pwdchangegap, "
	      		+ " NVL( lastlogingap, trunc(months_between(sysdate,registerday)) ) AS lastlogingap, "
	      		+ "	idle, "
	      		+ " mobile, email, postcode, address, detail_address, extra_address  FROM "
	      		+ "	( select userid, username, "
	      		+ "	trunc( months_between(sysdate, lastpwdchangedate) ) AS pwdchangegap, "
	      		+ "	registerday, idle,  "
	      		+ "	mobile, email, postcode, address, detail_address, extra_address  "
	      		+ "	from tbl_member where status = 1 and userid = ? and pw = ?) M  "
	      		+ "	CROSS JOIN "
	      		+ "	( select trunc( months_between(sysdate, max(logindate)) ) AS lastlogingap  "
	      		+ "	from tbl_loginhistory  where fk_userid = ?) H ";
	      pstmt = conn.prepareStatement(sql);
	      System.out.println("userid"+paraMap.get("userid"));
	      System.out.println("pwd"+ Sha256.encrypt(paraMap.get("pwd")));
	      pstmt.setString(1, paraMap.get("userid"));
	      pstmt.setString(2, Sha256.encrypt(paraMap.get("pwd")));
	      pstmt.setString(3, paraMap.get("userid"));

	      rs = pstmt.executeQuery();
	      // 있으면 1개밖에 안나옴 - userid가 PK 이기 때문
	      if (rs.next()) {
	        member = new MemberVO();
	        member.setUserid(rs.getString("userid"));
	        member.setUsername(rs.getString("username"));

	        if (rs.getInt("lastlogingap") >= 12) {
	          // 마지막으로 로그인 한 날짜 시간이 현재 시각으로부터 1년이 지났으면 휴면으로 지정
	          member.setIdle(1); // defalut 0 인걸 1로 바꿔버림(객체에서)=> DB에서 1이라고 해도, 이 객체 상태는 1이 아니기 때문에 set
	                             // 해줘야 함.
	          if (rs.getInt("idle") == 0) {
	            // === tbl_member 테이블의 idle 컬럼의 값을 1로 변경하기 === //
	            sql = " update tbl_member set idle = 1 " + " where userid = ? ";

	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, paraMap.get("userid"));

	            pstmt.executeUpdate();

	          }

	        }
	        if (rs.getInt("lastlogingap") < 12) {
	          // == >>휴면이 아닌<< 회원만 tbl_loginhistory(로그인기록) 테이블에 입력하기 시작
	          sql = " insert into tbl_loginhistory(fk_userid, clientip) "
	              + " values(?, ?) ";
	          pstmt = conn.prepareStatement(sql);
	          pstmt.setString(1, paraMap.get("userid"));
	          pstmt.setString(2, paraMap.get("clientip"));

	          pstmt.executeUpdate();
	          // == 휴면이 아닌 회원만 tbl_loginhistory(로그인기록) 테이블에 입력하기 끝

	          if (rs.getInt("pwdchangegap") >= 3) {
	            // 마지막으로 암호를 변경한 날짜가 현재시각으로 부터 3개월이 지났으면 true
	            // 마지막으로 암호를 변경한 날짜가 현재시각으로 부터 3개월이 지나지 않았으면 false
	            member.setRequirePwdChange(true); // 로그인시 암호를 변경하라는 alert 를 띄우도록 할때 사용한다.
	          }
	        }
	        member.setEmail(aes.decrypt(rs.getString("email")));
	        member.setMobile(rs.getString("mobile"));
	        member.setPostcode(rs.getString("postcode"));
	        member.setAddress(rs.getString("address"));
	        member.setDetail_address(rs.getString("detail_address"));
	        member.setExtra_address(rs.getString("extra_address"));
	      } // end of if(rs.next()) ----------------------------

	    } catch (UnsupportedEncodingException | GeneralSecurityException e) {
	      e.printStackTrace();
	    } finally {
	      close();
	    }
	    return member;
	}

	// 페이지 바 만들기 - 페이징 처리를 위한 검색이 있는/없는 회원에 대한 총페이지 수 알아오기
	@Override
	public int getTotalPage(Map<String, String> paraMap) throws SQLException {
		int totalPage = 0;
		
		List<MemberVO> memberList = new ArrayList<>();
		try {
			 
			conn = ds.getConnection();
			
			String sql = " select ceil(count(*)/?) "
			 		+ " from tbl_member "
			 		+ " where userid != 'admin' ";

			String colname = paraMap.get("searchType");
			String searchWord = paraMap.get("searchWord");
			 
			if("email".equals(colname)) { //이메일 검색대상-> 암호화해서 검색
				searchWord = aes.encrypt(searchWord);
			}
			
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
			} catch (GeneralSecurityException | UnsupportedEncodingException e) {
				e.printStackTrace();
			} finally {
				close();
			}
		return totalPage;

	}

	
	 // 페이징 처리를 한 모든 회원 또는 검색한 회원 목록 보여주기
	@Override
	public List<MemberVO> select_Member_paging(Map<String, String> paraMap) throws SQLException {
		
		List<MemberVO> memberList = new ArrayList<>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " select rno, userid, username, email, gender "
					+ " from ( "
					+ " select rownum AS rno, userid, username, email, gender "
					+ " from ( "
					+ " select userid, username, email, gender "
					+ " from tbl_member "
					+ " where userid != 'admin' ";
			 
			 
			String colname = paraMap.get("searchType");
			String searchWord = paraMap.get("searchWord");
			 
			if("email".equals(colname)) { //이메일 검색대상-> 암호화해서 검색
				searchWord = aes.encrypt(searchWord);
			}
			
			//유저가 검색했다면 true;
		    boolean userSearch = (colname != null && !colname.trim().isEmpty()) && 
                    (searchWord != null && !searchWord.trim().isEmpty());
			
			if(userSearch) {
				 sql += " and " + colname + " like '%' || ? || '%' ";
			}
				
			sql += " order by registerday desc "
			 		+ " ) V "
			 		+ " ) T "
			 		+ " where T.rno between ? and ? ";
			
			pstmt = conn.prepareStatement(sql);
			// (10-9) and 10 // => between 1 and 10
			
			int currentShowPageNo = Integer.parseInt(paraMap.get("currentShowPageNo")); // 현재 페이지위치
			int sizePerPage = Integer.parseInt(paraMap.get("sizePerPage")); //1페이지당 보여지는 회원명수
			
			if(userSearch) { 
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
				mvo.setUserid(rs.getString("userid"));
				mvo.setUsername(rs.getString("username"));
				mvo.setEmail(aes.decrypt(rs.getString("email")));
				mvo.setGender(rs.getString("gender"));
				
				memberList.add(mvo);
			}
			
		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return memberList;
	}

	// 뷰단(memberList.jsp)에서 "페이징 처리시 보여주는 순번 공식" 에서 사용하기 위해 검색이 있는 또는 검색이 없는 회원의 총개수 알아오기
	@Override
	public int getTotalMemberCount(Map<String, String> paraMap) throws SQLException {
		int totalMemberCount = 0;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " select count(*) "
					+ " from tbl_member "
					+ " where userid != 'admin' "; 
			 
			 
			String colname = paraMap.get("searchType");
			String searchWord = paraMap.get("searchWord");
			 
			if("email".equals(colname)) { //이메일 검색대상-> 암호화해서 검색
				searchWord = aes.encrypt(searchWord);
			}
			
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
			totalMemberCount = rs.getInt(1); 
			
		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return totalMemberCount;
		
	}

	//회원 상세정보 보여주기 - 비밀번호는 암호화 때문에 불러오지 않음
	@Override
	public MemberVO selectOneMember(String userid)  throws SQLException {
		MemberVO member = null;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " select userid, username, email, mobile, postcode, address, detail_address, extra_address, gender "
					+ " , birthday, to_char(registerday, 'yyyy-mm-dd') AS registerday "
					+ " from tbl_member "
					+ " where status = '1' and userid = ?";
			 
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userid);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
		        member = new MemberVO();
		        member.setUserid(rs.getString(1));
		        member.setUsername(rs.getString(2));
		        member.setEmail( aes.decrypt(rs.getString(3)) );  // 복호화
		        member.setMobile( aes.decrypt(rs.getString(4)) ); // 복호화
		        member.setPostcode(rs.getString(5));
		        member.setAddress(rs.getString(6));
		        member.setDetail_address(rs.getString(7));
		        member.setExtra_address(rs.getString(8));
		        member.setGender(rs.getString(9));
		        member.setBirthday(rs.getString(10));
		        member.setRegisterday(rs.getString(11));
		    } // end of if(rs.next())-------------------
		       
		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return member;
		
	}

	// userid로 데이터베이스에서 유저 정보를 가져오기 - 자동 로그인 부분
	@Override
	public MemberVO getMemberByUserid(String userid) throws SQLException {
		MemberVO member = null;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " select userid, username, email, mobile, postcode, address, detail_address, extra_address, gender "
					+ " , birthday, to_char(registerday, 'yyyy-mm-dd') AS registerday "
					+ " from tbl_member "
					+ " where status = '1' and userid = ?";
			 
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userid);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
		        member = new MemberVO();
		        member.setUserid(rs.getString(1));
		        member.setUsername(rs.getString(2));
		        member.setEmail( aes.decrypt(rs.getString(3)) );  // 복호화
		        member.setMobile( aes.decrypt(rs.getString(4)) ); // 복호화
		        member.setPostcode(rs.getString(5));
		        member.setAddress(rs.getString(6));
		        member.setDetail_address(rs.getString(7));
		        member.setExtra_address(rs.getString(8));
		        member.setGender(rs.getString(9));
		        member.setBirthday(rs.getString(10));
		        member.setRegisterday(rs.getString(11));
		    } // end of if(rs.next())-------------------
		       
		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return member;
		
	}

	// 상품상세 이미지 파일명 가져오는 메소드 
	@Override
	public List<ImageVO> imageSelectAll() throws SQLException {
			
			List<ImageVO> imgList = new ArrayList<>();
			try {
				conn = ds.getConnection();
				
				String sql = " select fk_pdno, pd_extraimg"
						+ " from tbl_product_img ";
				
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					
					ImageVO imgvo = new ImageVO();
					imgvo.setImgno(rs.getInt("fk_pdno"));
					imgvo.setImgfilename(rs.getString("pd_extraimg"));
					
					imgList.add(imgvo);
					
				}//end of while(rs.next())
				
			} finally {
				close();
			}
			return imgList;
		
		
	}

	// 상품상세 이미지 메인 - 파일명 가져오는 메소드 
	@Override
	public ImageVO imageSelect() throws SQLException {
		ImageVO imgvo = null;
		try {
			conn = ds.getConnection();
			
			String sql = " select pdno, pdimg1 "
					+ " from tbl_product ";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				imgvo = new ImageVO();
				imgvo.setImgno(rs.getInt("pdno"));
				imgvo.setImgfilename(rs.getString("pdimg1"));
				
			}//end of while(rs.next())
			
		} finally {
			close();
		}
		return imgvo;
	
	}
	
}
