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
	         
	         String sql = " select rno , pdno, pdname, brand, price, saleprice, pdimg1 "
		         		+ " from "
		         		+ "	 ( "
		         		+ "	select rownum as rno, pdno, pdname, brand, price, saleprice, pdimg1 "
		         		+ "	 from "
		         		+ "	( "
		         		+ "	 select rownum,pdno, pdname, brand, price, saleprice, pdimg1 "
		         		+ "	from tbl_product "
		         		+ "	 where pdstatus = 1 ";
	         			 
	         
	         String brand = paraMap.get("brand");
	         String sort = paraMap.get("sort");
	         
	         	sql += " and brand like '%'|| ? ||'%' ";
				
				
				
	            sql += " order by pdno desc "
	            		+ " ) v "
	            		+ " ) T "
	            		+ "	where T.rno between ? and ? ";
	         
	         pstmt = conn.prepareStatement(sql);
	         
	         pstmt.setString(1, brand);
	         
	         int currentShowPageNo = Integer.parseInt(paraMap.get("currentShowPageNo") );
				// 조회할 페이지 번호
				
			int sizePerPage = Integer.parseInt(paraMap.get("sizePerPage") );
				// 한페이지당 보여줄 행 갯수
	         
	         pstmt.setInt(2, (currentShowPageNo * sizePerPage) - (sizePerPage - 1) ); // 공식
			 pstmt.setInt(3, (currentShowPageNo * sizePerPage) ); 
	         
	         
	         rs = pstmt.executeQuery();
	         
	         while(rs.next()) {
	            
	            ProductVO pvo = new ProductVO();
	            
	            pvo.setPdno(String.valueOf(rs.getInt("pdno")));     // 제품번호
	            pvo.setPdname(rs.getString("pdname")); // 제품명
	            
	            
	            pvo.setBrand(rs.getString("brand"));  // 브랜드명
	            pvo.setPimage1(rs.getString("pdimg1"));   // 제품이미지1   이미지파일명
	            pvo.setPrice(rs.getInt("price"));        // 제품 정가
	            pvo.setSaleprice(rs.getInt("saleprice"));    // 제품 판매가(할인해서 팔 것이므로)
	            
	            
	            productList.add(pvo);
	            
	         }// end of while(rs.next())-------------------------
	         
	         
	      } finally {
	         close();
	      }
		
		return productList;
		
	}// end of public List<ProductVO> select_product_pagin() throws SQLException {}


	
	// 전체 상픔리스트 페이지수 알아오기
	@Override
	public int getTotalPage(Map<String, String> paraMap) throws SQLException {
		int getTotalPage = 0;
		
		try {
			conn = ds.getConnection();
         
			String sql = " select ceil(count(*)/?) as pagecnt "
					   + " from tbl_product "
					   + " where pdstatus = 1 ";
			
			String brand = paraMap.get("brand");
			String searchWord = paraMap.get("searchWord");
			
			
	
				
					sql += " and brand like '%'|| ? ||'%' ";
					// 컬럼명과 테이블명은 위치홀더(?)로 사용하면 꽝!!! 이다.
		            // 위치홀더(?)로 들어오는 것은 컬럼명과 테이블명이 아닌 오로지 데이터값만 들어온다.!!!!
					
		
			
			pstmt = conn.prepareStatement(sql); 
			
			pstmt.setInt(1, Integer.parseInt(paraMap.get("sizePerPage") ) );
			
			
			
				
				pstmt.setString(2, brand);
				
			
			rs = pstmt.executeQuery();
         	
			rs.next();
			
			getTotalPage = rs.getInt("pagecnt");
         
		} finally {
			close();
		}
		
		return getTotalPage;
		
	}// end of public int getTotalPage(Map<String, String> paraMap) throws SQLException


	
	
	@Override
	public int getTotalProductCount(Map<String, String> paraMap) throws SQLException {
		
		int getTotalProductCount = 0;
		
		try {
			conn = ds.getConnection();
         
			String sql = " select count(*) as cnt "
					+ " from tbl_product "
					+ " where pdstatus = 1  ";
			
			String brand = paraMap.get("brand");
			String searchWord = paraMap.get("searchWord");
			
			if( (brand != null && !brand.trim().isEmpty() )  ) {
				// if(colname != null && searchWord != null) { // 단일로 되는데...?
					
						sql += " and brand like '%'|| ? ||'%' ";
						// 컬럼명과 테이블명은 위치홀더(?)로 사용하면 꽝!!! 이다.
			            // 위치홀더(?)로 들어오는 것은 컬럼명과 테이블명이 아닌 오로지 데이터값만 들어온다.!!!!
						
			}
			
			pstmt = conn.prepareStatement(sql); 
			
			
			if( (brand != null && !brand.trim().isEmpty() )  ) {
				// 검색이 있는경우
				
				pstmt.setString(1, brand);
				
			}
			
			rs = pstmt.executeQuery();
         	
			rs.next();
			
			getTotalProductCount = rs.getInt("cnt");
         
		} finally {
			close();
		}
		
		return getTotalProductCount;
	}


	
	
	
	

}
