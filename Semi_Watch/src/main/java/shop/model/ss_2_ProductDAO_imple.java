package shop.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import shop.domain.ImageVO;
import shop.domain.ProductVO;

public class ss_2_ProductDAO_imple implements ss_2_ProductDAO {

	// DB에 사용되는 객체
	private DataSource ds; // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	// import javax.sql.DataSource;
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	// DB Connection Pool.txt 파일내용을 복붙한 내용
	// 생성자
	public ss_2_ProductDAO_imple() {
		
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


// 화면에서 찜하기를 눌렀을 때 해당하는 상품의 정보를 VO에 담아서 반환하는 메소드
@Override
public List<ProductVO> getWishListItem(String pdname) throws SQLException {
	
	 List<ProductVO> wishProductList = new ArrayList<>(); 
	    
	    try {
	        conn = ds.getConnection();
	        
	        String sql = " select pdname, pdimg1, price "
	        		+ " from tbl_product "
	        		+ " where pdname IN ( " + pdname + " ) ";
	                  
	       pstmt = conn.prepareStatement(sql);
	             
	       rs = pstmt.executeQuery();
	                
	       while(rs.next()) {
	    	  ProductVO pvo = new ProductVO();
	    	  pvo.setPdname(rs.getString(1));
	    	  pvo.setPdimg1(rs.getString(2));
	    	  pvo.setPrice(rs.getInt(3));
	          
	    	  wishProductList.add(pvo);
	       }// end of while(rs.next())----------------------------------
	       
	    } finally {
	       close();
	    }   
	    
	    return wishProductList;

	
	
}
	
	
	

}
