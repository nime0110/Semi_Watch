package shop.model;

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

import shop.domain.ImageVO;
import shop.domain.ProductVO;

public class sw_4_ProductDAO_imple implements sw_4_ProductDAO {

	// DB에 사용되는 객체
	private DataSource ds; // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	// import javax.sql.DataSource;
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	

	// DB Connection Pool.txt 파일내용을 복붙한 내용
	// 생성자
	public sw_4_ProductDAO_imple() {
		
		try {
		Context initContext = new InitialContext();
	    Context envContext  = (Context)initContext.lookup("java:/comp/env");
	    ds = (DataSource)envContext.lookup("jdbc/semioracle");

		}catch(NamingException e) {
			e.printStackTrace();
		}
		
	}// end of public void ProductDAO_imple() {} --------------------------------------------------
	
	
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

	// 장바구니에 담은 제품 정보 보여주기
	@Override
	public List<ProductVO> select_product() throws SQLException {
		
		List<ProductVO> ProductList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " select pdname, brand, price, saleprice, pdimg1 "
					   + " from tbl_product "
					   + " where pdno in(1,2,3) ";
			
			pstmt = conn.prepareStatement(sql);
			
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) { // sql 결과물 
				
				ProductVO pvo = new ProductVO();
				// PDNAME, BRAND, PRICE, SALEPRICE, PDIMG1
				
				pvo.setPdname(rs.getString("pdname"));
				pvo.setBrand(rs.getString("brand"));
				pvo.setPrice(rs.getInt("price"));
				pvo.setSaleprice(rs.getInt("saleprice"));
				pvo.setPdimg1(rs.getString("pdimg1"));
			
				ProductList.add(pvo);
				
			} // end of while(rs.next))------------------------------------
			
		} catch(SQLException e) {
			e.printStackTrace();
	
		} finally {
			close();
		}
		
		return ProductList;
	}
	


}
