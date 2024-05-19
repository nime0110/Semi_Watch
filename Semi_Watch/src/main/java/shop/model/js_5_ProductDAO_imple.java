package shop.model;

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



import shop.domain.ProductVO;

public class js_5_ProductDAO_imple implements js_5_ProductDAO {

	// DB에 사용되는 객체
	private DataSource ds; // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	// import javax.sql.DataSource;
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	// DB Connection Pool.txt 파일내용을 복붙한 내용
	// 생성자
	public js_5_ProductDAO_imple() {
		
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


   // 전체 상픔리스트 가져오기
	@Override
	public List<ProductVO> select_product_pagin(Map<String, String> paraMap) throws SQLException {
		
		List<ProductVO> productList = new ArrayList<>();
		
		try {
	         conn = ds.getConnection();
	         
	         String sql = " select pdno, pdname, brand, pdimg1, price, saleprice "
	         			+ " from tbl_product "
	         			+ " where pdstatus = 1 ";
	         			 
	         
	         String brand = paraMap.get("brand");
	         String sort = paraMap.get("sort");
	         
	         	sql += " and brand like '%'|| ? ||'%' ";
				
				
				
	            sql += " order by pdno desc ";
	         
	         pstmt = conn.prepareStatement(sql);
	         
	         pstmt.setString(1, brand);
	         
	         
	         rs = pstmt.executeQuery();
	         
	         while(rs.next()) {
	            
	            ProductVO pvo = new ProductVO();
	            
	            pvo.setPdno(String.valueOf(rs.getInt(1)));     // 제품번호
	            pvo.setPdname(rs.getString(2)); // 제품명
	            
	            
	            pvo.setBrand(rs.getString(3));  // 브랜드명
	            pvo.setPimage1(rs.getString(4));   // 제품이미지1   이미지파일명
	            pvo.setPrice(rs.getInt(5));        // 제품 정가
	            pvo.setSaleprice(rs.getInt(6));    // 제품 판매가(할인해서 팔 것이므로)
	            
	            
	            productList.add(pvo);
	            
	         }// end of while(rs.next())-------------------------
	         
	         
	      } finally {
	         close();
	      }
		
		
		
		
		return productList;
		
	}// end of public List<ProductVO> select_product_pagin() throws SQLException {}
	
	
	

}
