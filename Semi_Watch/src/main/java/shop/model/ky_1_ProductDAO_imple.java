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

import shop.domain.ImageVO;
import shop.domain.ProductVO;

public class ky_1_ProductDAO_imple implements ky_1_ProductDAO {

	// DB에 사용되는 객체
	private DataSource ds; // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	// import javax.sql.DataSource;
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	// DB Connection Pool.txt 파일내용을 복붙한 내용
	// 생성자
	public ky_1_ProductDAO_imple() {
		
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

	// 최신 등록순으로 6개의 상품 이미지를 가져오기
	@Override
	public List<ProductVO> selectByRegiDate(Map<String, String> paraMap) throws SQLException {
		
		List<ProductVO> productList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " SELECT rno, pdno, pdimg1 "
					+ " FROM "
					+ " (select row_number() over(order by pdinputdate desc) AS rno, pdno, pdimg1 "
					+ " from tbl_product) "
					+ " where rno between ? and ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(paraMap.get("start")) );
			pstmt.setInt(2, Integer.parseInt(paraMap.get("end")));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				ProductVO pvo = new ProductVO();
				
				pvo.setPdno(rs.getString("pdno"));
				pvo.setPdimg1(rs.getString("pdimg1"));
				
				productList.add(pvo);
				
			}// end of while(rs.next()) 
			
			// System.out.println("확인용 리스트 사이즈 : "+productList.size());
			
		} finally {
			close();
		}
		
		return productList;
		
	}// end of public List<ProductVO> selectByRegiDate(Map<String, String> paraMap) throws SQLException 
	
	
	

}
