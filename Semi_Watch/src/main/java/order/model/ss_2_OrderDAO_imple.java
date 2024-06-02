package order.model;

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

import shop.domain.ImageVO;
import shop.domain.ProductVO;

public class ss_2_OrderDAO_imple implements ss_2_OrderDAO {

	// DB에 사용되는 객체
	private DataSource ds; // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	// import javax.sql.DataSource;
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	// DB Connection Pool.txt 파일내용을 복붙한 내용
	// 생성자
	public ss_2_OrderDAO_imple() {
		
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


// 리뷰 테이블에 insert 하는 메소드
@Override
public int insertReview(String productNo, String reviewText, String rating, String userid) throws SQLException {
	int result = 0;
	
	try {
		conn = ds.getConnection();
		
		String sql = " INSERT INTO tbl_review "
				+ " (reviewno, fk_userid, review_content, starpoint, fk_pdno)  "
				+ " VALUES ( SEQ_REVIEWNO.nextval, ?, ?, ?, ?) ";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, userid);
		pstmt.setString(2, reviewText);
		pstmt.setString(3, rating);
		pstmt.setString(4, productNo);
		
		result = pstmt.executeUpdate();
		
	} finally {
		close();
	}
	
	return result;
}


// 리뷰 테이블에 insert 하기 전 이미 리뷰가 작성된 상품인지 그 리뷰 select
@Override
public Map<String, String> isReviewExists(String productNo, String userid) throws SQLException {
	boolean isReviewExist = false;
    Map<String, String> reviewDetail = null;
	
    try {
        conn = ds.getConnection();
        
        String sql = " select review_content, starpoint from tbl_review "
        		+ " WHERE fk_pdno = ? and fk_userid = ? ";
       pstmt = conn.prepareStatement(sql);
		
	   pstmt.setString(1, productNo);
	   pstmt.setString(2, userid);      

	   System.out.println("productNo: " + productNo);
	   System.out.println("userid: " + userid);
       
       rs = pstmt.executeQuery();
                
       if(rs.next()) {
    	   isReviewExist = true;
    	   String review_content = rs.getString("review_content");
    	   String starpoint = rs.getString("starpoint");
    	  reviewDetail = new HashMap<>();
    	   
    	   reviewDetail.put("isReviewExist", String.valueOf(isReviewExist));
    	   reviewDetail.put("review_content", review_content);
    	   reviewDetail.put("starpoint", starpoint);

       }
    } finally {
       close();
    }   
	return reviewDetail;
}


// 리뷰 테이블 업데이트 메소드
@Override
public int updateReview(String productNo, String reviewText, String rating, String userid) throws SQLException {
	int n = 0; //행이 성공적으로 입력이 되면 1값 반환
	String sql = "";
	try {
		conn = ds.getConnection();
        // 어떤 제품을 추가로 장바구니에 넣고자 하는 경우
    	sql = " UPDATE tbl_review "
    			+ " SET review_content = ?, "
    			+ " starpoint = ?"
    			+ " WHERE fk_pdno = ? and fk_userid = ? ";
    	pstmt = conn.prepareStatement(sql);
    	pstmt.setString(1, reviewText);
    	pstmt.setString(2, rating);
    	pstmt.setString(3, productNo);
    	pstmt.setString(4, userid);
    	n = pstmt.executeUpdate();
	    
	} finally {
		close();
	}
	return n;
}


//리뷰 테이블에서 삭제  
@Override
public int deleteReview(String productNo, String userid) throws SQLException {
	
	int n = 0;
	
	try {
		conn = ds.getConnection();
		
		String sql = " delete from tbl_review "
				+ " where fk_userid = ? and fk_pdno = ? ";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, userid);
		pstmt.setString(2, productNo);
		
		n = pstmt.executeUpdate();
		

	} finally {
		
		close();
	}
	
	return n;
}


	

}
