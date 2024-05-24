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
import shop.domain.Product_DetailVO;

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
		         		+ "	select rno, pdno, pdname, brand, price, saleprice, pdimg1 "
		         		+ "	 from "
		         		+ "	( "
		         		+ "	 select rownum as rno ,pdno, pdname, brand, price, saleprice, pdimg1 "
		         		+ "	from tbl_product P "
		         		+ " join tbl_pd_detail D "
		         		+ " on P.pdno = D.fk_pdno "
		         		+ " where pd_qty > 1 ";
	         			 
	         
	         String brand = paraMap.get("brand");
	         String sort = paraMap.get("sort");
	         String colname = paraMap.get("colname");
	       
	         
	         	sql += " and brand like '%'|| ? ||'%' ";
				
	            sql += " order by " +  colname + " " + sort
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
	            pvo.setPdimg1(rs.getString("pdimg1"));   // 제품이미지1   이미지파일명
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
					   + " from tbl_product P join tbl_pd_detail D "
					   + " on P.pdno = D.fk_pdno "
					   + " where pd_qty > 0 "
					   + " and brand like '%'|| ? ||'%' ";
			
			String brand = paraMap.get("brand");
			
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


	
	/* >>> 뷰단(memberList.jsp)에서 "페이징 처리시 보여주는 순번 공식" 에서 사용하기 위해 
    검색이 있는 또는 검색이 없는 회원의 총개수 알아오기 시작 <<< */
	@Override
	public int getTotalProductCount(Map<String, String> paraMap) throws SQLException {
		
		int getTotalProductCount = 0;
		
		try {
			conn = ds.getConnection();
         
			String sql = " select count(*) as cnt "
					+ " from tbl_product P join tbl_pd_detial D "
					+ " on P.pdno = D.fk_pdno "
					+ " where pd_qty > 0 ";
			
			String brand = paraMap.get("brand");
						
			if( (brand != null && !brand.trim().isEmpty() )  ) {
				// if(colname != null && searchWord != null) { // 단일로 되는데...?
					
				sql += " and brand like '%'|| ? ||'%' ";
				// 컬럼명과 테이블명은 위치홀더(?)로 사용하면 꽝!!! 이다.
	            // 위치홀더(?)로 들어오는 것은 컬럼명과 테이블명이 아닌 오로지 데이터값만 들어온다.!!!!
						
			}
			String sort = paraMap.get("sort");
	        String colname = paraMap.get("colname");
			
			sql += " order by " +  colname + " " + sort;
			
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
		
	}// end of public int getTotalProductCount(Map<String, String> paraMap) throws SQLException {}

	// 
	@Override
	public List<ProductVO> search_product_pagin_brand(Map<String, String> paraMap) throws SQLException {
		
		List<ProductVO> productList = new ArrayList<>();
		
		try {
	         conn = ds.getConnection();
	         
	         String sql = " select rno , pdno, pdname, brand, price, saleprice, pdimg1 "
		         		+ " from "
		         		+ "	 ( "
		         		+ "	select rno, pdno, pdname, brand, price, saleprice, pdimg1 "
		         		+ "	 from "
		         		+ "	( "
		         		+ "	 select rownum as rno ,pdno, pdname, brand, price, saleprice, pdimg1 "
		         		+ "	from tbl_product P "
		         		+ " join tbl_pd_detail D "
		         		+ " on P.pdno = D.fk_pdno "
		         		+ " where pd_qty > 1 ";
	         			 
	         
	         String searchWord = paraMap.get("searchWord");
	         String sort = paraMap.get("sort");
	         String colname = paraMap.get("colname");
	       
	         
	         	sql += " and brand like '%'|| ? ||'%' ";
				
	            sql += " order by " +  colname + " " + sort
	            		+ " ) v "
	            		+ " ) T "
	            		+ "	where T.rno between ? and ? ";
	         
	         pstmt = conn.prepareStatement(sql);
	         
	         pstmt.setString(1, searchWord);
	         
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
	            pvo.setPdimg1(rs.getString("pdimg1"));   // 제품이미지1   이미지파일명
	            pvo.setPrice(rs.getInt("price"));        // 제품 정가
	            pvo.setSaleprice(rs.getInt("saleprice"));    // 제품 판매가(할인해서 팔 것이므로)
	            
	            
	            productList.add(pvo);
	            
	         }// end of while(rs.next())-------------------------
	         
	         
	      } finally {
	         close();
	      }
		
		return productList;
	} // end of

	
	// 
	@Override
	public List<ProductVO> search_product_pagin_pdname(Map<String, String> paraMap) throws SQLException {
		
		List<ProductVO> productList = new ArrayList<>();
		
		try {
	         conn = ds.getConnection();
	         
	         String sql = " select rno , pdno, pdname, brand, price, saleprice, pdimg1 "
		         		+ " from "
		         		+ "	 ( "
		         		+ "	select rno, pdno, pdname, brand, price, saleprice, pdimg1 "
		         		+ "	 from "
		         		+ "	( "
		         		+ "	 select rownum as rno ,pdno, pdname, brand, price, saleprice, pdimg1 "
		         		+ "	from tbl_product P "
		         		+ " join tbl_pd_detail D "
		         		+ " on P.pdno = D.fk_pdno "
		         		+ " where pd_qty > 1 ";
	         			 
	         
	         String searchWord = paraMap.get("searchWord");
	         String sort = paraMap.get("sort");
	         String colname = paraMap.get("colname");
	       
	         
	         	sql += " and pdname like '%'|| ? ||'%' ";
				
	            sql += " order by " +  colname + " " + sort
	            		+ " ) v "
	            		+ " ) T "
	            		+ "	where T.rno between ? and ? ";
	         
	         pstmt = conn.prepareStatement(sql);
	         
	         pstmt.setString(1, searchWord);
	         
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
	            pvo.setPdimg1(rs.getString("pdimg1"));   // 제품이미지1   이미지파일명
	            pvo.setPrice(rs.getInt("price"));        // 제품 정가
	            pvo.setSaleprice(rs.getInt("saleprice"));    // 제품 판매가(할인해서 팔 것이므로)
	            
	            
	            productList.add(pvo);
	            
	         }// end of while(rs.next())-------------------------
	         
	         
	      } finally {
	         close();
	      }
		
		return productList;
	}


	// 브랜드명을 조회해서 나오는 토탈페이지구하기
	@Override
	public int search_brand_TotalPage(Map<String, String> paraMap) throws SQLException {
		
		int getTotalPage = 0;
		
		try {
			
			conn = ds.getConnection();
         
			String sql = " select ceil(count(*)/?) as pagecnt "
					   + " from tbl_product P join tbl_pd_detail D "
				   	   + " on P.pdno = D.fk_pdno "
					   + " where pd_qty > 0 "
					   + " and brand like '%'|| ? ||'%' ";
			
			String searchWord = paraMap.get("searchWord");
			
			pstmt = conn.prepareStatement(sql); 
			
			pstmt.setInt(1, Integer.parseInt(paraMap.get("sizePerPage") ) );
			
			pstmt.setString(2, searchWord);
			
			rs = pstmt.executeQuery();
         	
			rs.next();
			
			getTotalPage = rs.getInt("pagecnt");
         
		} finally {
			close();
		}
		
		return getTotalPage;
		
	} // end of public int search_brand_TotalPage(Map<String, String> paraMap) throws SQLException {

	
	// 상품명을 조회해서 나오는 토탈페이지구하기
	@Override
	public int search_pdname_TotalPage(Map<String, String> paraMap) throws SQLException {
		
		int getTotalPage = 0;
		
		try {
			conn = ds.getConnection();
         
			String sql = " select ceil(count(*)/?) as pagecnt "
					   + " from tbl_product P join tbl_pd_detail D "
				   	   + " on P.pdno = D.fk_pdno "
					   + " where pd_qty > 0 "
					   + " and pdname like '%'|| ? ||'%' ";
			
			String searchWord = paraMap.get("searchWord");
			
			pstmt = conn.prepareStatement(sql); 
			
			pstmt.setInt(1, Integer.parseInt(paraMap.get("sizePerPage") ) );
			
			pstmt.setString(2, searchWord);
			
			rs = pstmt.executeQuery();
         	
			rs.next();
			
			getTotalPage = rs.getInt("pagecnt");
         
		} finally {
			close();
		}
		
		return getTotalPage;
		
	} // end of public int search_pdname_TotalPage(Map<String, String> paraMap) throws SQLException {


	// 제품번호 채번해오기
	@Override
	public int getPnumOfProduct() throws SQLException {
		
		int pdno = 0;
	      
	      try {
	         conn = ds.getConnection();
	         
	         String sql = " select seq_tbl_product_pdno.nextval AS pdno "
	                  + " from dual ";
	         
	         pstmt = conn.prepareStatement(sql);
	         rs = pstmt.executeQuery();
	         
	         rs.next();
	         pdno = rs.getInt(1);
	         
	      } finally {
	         close();
	      }
	      
	      return pdno;
	      
	} // end of public int getPnumOfProduct() throws SQLException {


	// tbl_product 테이블에 제품정보 insert 하기
	@Override
	public int productinsert(ProductVO pvo) throws SQLException {
		
		int result = 0;
	      
	      try {
	         conn = ds.getConnection();
	         
	         String sql = " insert into tbl_product(pdno, pdname, brand, "
	         			+ " price, saleprice, pdimg1, pd_content, point) "
	         			+ " values(?,?,?, "
	         			+ "	?,?,?,?,?) ";
	         
	         pstmt = conn.prepareStatement(sql);
	         
	         pstmt.setString(1, pvo.getPdno());
	         pstmt.setString(2, pvo.getPdname());
	         pstmt.setString(3, pvo.getBrand());    
	         pstmt.setInt(4, (int) pvo.getPrice()); 
	         pstmt.setInt(5, (int) pvo.getSaleprice()); 
	         pstmt.setString(6, pvo.getPdimg1()); 
	         pstmt.setString(7, pvo.getPd_content());
	         pstmt.setInt(8, pvo.getPoint());
	         
	         result = pstmt.executeUpdate();
	         
	      } finally {
	         close();
	      }
	      
	      return result;

	} // end of public int productinsert(ProductVO pvo) throws SQLException {


	// >>> tbl_product_imagefile 테이블에 제품의 추가이미지 파일명 insert 하기 <<<
	@Override
	public int product_imagefile_insert(Map<String, String> paraMap) throws SQLException {
		
		int result = 0;
	      
	      try {
	         conn = ds.getConnection();
	         
	         String sql = " insert into tbl_product_img(img_no, fk_pdno, pd_extraimg) "
	                  + " values(seq_product_img.nextval, ?, ?) ";
	         
	         pstmt = conn.prepareStatement(sql);
	         
	         pstmt.setInt(1, Integer.parseInt(paraMap.get("pdno")) );
	         pstmt.setString(2, paraMap.get("attachFileName"));
	         
	         result = pstmt.executeUpdate();
	         
	      } finally {
	         close();
	      }
	      
	      return result;
	      
	} // end of public int product_imagefile_insert(Map<String, String> paraMap) throws SQLException {


	
	// 상품추가정보입력을 위한 상품명, 브랜도 조회해오기
	@Override
	public ProductVO select_extrainfo(String setpdno) throws SQLException {
		
		ProductVO pvo = new ProductVO();
		
		try {
	         conn = ds.getConnection();
	         
	         String sql = " select pdname, brand "
	         		+ " from tbl_product "
	         		+ " where pdno = ? ";
	         
	         pstmt = conn.prepareStatement(sql);
	         
	         pstmt.setString(1, setpdno);
	         
	         rs = pstmt.executeQuery();
	         
	         if(rs.next()){
	        	 
	        	 pvo.setPdname(rs.getString(1));
		         pvo.setBrand(rs.getString(2));
	        	 
	         }
	         
	        
	         
	      } finally {
	         close();
	      }
		
		
		return pvo;
		
	} // end of public ProductVO select_extrainfo(String setpdno) throws SQLException {


	
	// 상품추가정보 입력
	@Override
	public int insert_product_detail(Map<String, String> paraMap) throws SQLException {

		int result = 0;
		
		try {
	         conn = ds.getConnection();
	         
	         for(int i=1 ; i<=3; i++) {
	        	 
	        	 if(paraMap.get("color"+i) != null && paraMap.get("pdqty"+i) !=null) {
	        		 
	        		 String sql = " insert into tbl_pd_detail(pd_detailno, fk_pdno, color, pd_qty) "
	   	                  + " values(seq_product_detail.nextval, ?, ? , ?) ";
	        		 
	        		 pstmt = conn.prepareStatement(sql);
	        		 
	        		 pstmt.setString(1, paraMap.get("pdno") );
	    	         pstmt.setString(2, paraMap.get("color"+i));
	    	         pstmt.setString(3, paraMap.get("pdqty"+i));
	    	         
	    	         result = pstmt.executeUpdate();
	    	         
	        	 } // end of if
	        	 
	         } // end of for
	          
	         
	      } finally {
	         close();
	      }
	      
	      return result;
	} // end of public int insert_product_detail(Map<String, String> paraMap) throws SQLException {

	
	// 관리자가 상품을 수정하기위한 상품리스트의 total 페이지
	@Override
	public int get_admin_ProductTotalPage(Map<String, String> paraMap) throws SQLException {
		
		int getTotalPage = 0;
		
		try {
			conn = ds.getConnection();
         
			String sql = " select ceil(count(*)/?) as pagecnt "
					   + " from tbl_product P join tbl_pd_detail D "
					   + " on P.pdno = D.fk_pdno "
					   + " where pd_qty > -1 ";
			
			String colname = paraMap.get("searchType");
			String searchWord = paraMap.get("searchWord");
			
			if( (colname != null && !colname.trim().isEmpty() ) &&
				(searchWord != null && !searchWord.trim().isEmpty() ) ) {
			// if(colname != null && searchWord != null) { // 단일로 되는데...?
				
					sql += " and " + colname + " like '%'|| ? ||'%' ";
					// 컬럼명과 테이블명은 위치홀더(?)로 사용하면 꽝!!! 이다.
		            // 위치홀더(?)로 들어오는 것은 컬럼명과 테이블명이 아닌 오로지 데이터값만 들어온다.!!!!
					
			}
			
			pstmt = conn.prepareStatement(sql); 
			
			pstmt.setInt(1, Integer.parseInt(paraMap.get("sizePerPage") ) );
			
			
			if( (colname != null && !colname.trim().isEmpty() ) &&
				(searchWord != null && !searchWord.trim().isEmpty() ) ) {
				// 검색이 있는경우
				
				pstmt.setString(2, searchWord);
				
			}
			
			rs = pstmt.executeQuery();
         	
			rs.next();
			
			getTotalPage = rs.getInt("pagecnt");
         
		} finally {
			close();
		}
		
		return getTotalPage;
		
	} // end of public int getProductTotalPage(Map<String, String> paraMap) throws SQLException {


	// 관리자가 상품을 수정하기위한 상품리스트
	@Override
	public List<ProductVO> select_admin_product_pagin(Map<String, String> paraMap) throws SQLException {
		
		List<ProductVO> ProductList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
         
			String sql = " select rno,  pdno, pdname, brand, saleprice, pdstatus, pd_qty , color"
					+ " from "
					+ " ( "
					+ " select rno,  pdno, pdname, brand, saleprice, pdstatus, "
					+ " pd_qty , color "
					+ " from "
					+ " ( "
					+ " select rownum as rno , pdno, pdname, brand, saleprice, pdstatus, nvl(pd_qty , 0 ) as pd_qty "
					+ " , pdinputdate ,  "
					+ " CASE WHEN color = N'none' THEN N'단일색상' ELSE color END AS color "
					+ " from tbl_product P full join tbl_pd_detail D "
					+ " on P.pdno = D.fk_pdno "
					+ " where pd_qty != -1 ";
			
			String colname = paraMap.get("searchType");
			String searchWord = paraMap.get("searchWord");
			
			if( (colname != null && !colname.trim().isEmpty() ) &&
				(searchWord != null && !searchWord.trim().isEmpty() ) ) {
			
					sql += " and " + colname + " like '%'|| ? ||'%' ";
					
			}
				
			sql += " order by pdinputdate desc "
				+ " ) V "	
				+ " ) T "
				+ " where rno between ? and ? ";
		        
			pstmt = conn.prepareStatement(sql); 
				
			
			int currentShowPageNo = Integer.parseInt(paraMap.get("currentShowPageNo") );
			// 조회할 페이지 번호
			
			int sizePerPage = Integer.parseInt(paraMap.get("sizePerPage") );
			// 한페이지당 보여줄 행 갯수
			
			
			if( (colname != null && !colname.trim().isEmpty() ) &&
				(searchWord != null && !searchWord.trim().isEmpty() ) ) {
				// 검색이 있는경우
				pstmt.setString(1, searchWord);
				pstmt.setInt(2, (currentShowPageNo * sizePerPage) - (sizePerPage - 1) ); // 공식
				pstmt.setInt(3, (currentShowPageNo * sizePerPage) ); // 공식
				
			}
			/*
		    === 페이징처리의 공식 ===
		    where RNO between (조회하고자하는페이지번호 * 한페이지당보여줄행의개수) - (한페이지당보여줄행의개수 - 1) and (조회하고자하는페이지번호 * 한페이지당보여줄행의개수);
		    */
			else {
				// 검색이 없는경우
				pstmt.setInt(1, (currentShowPageNo * sizePerPage) - (sizePerPage - 1) ); // 공식
				pstmt.setInt(2, (currentShowPageNo * sizePerPage) ); // 공식
				
			} 
			
			
			rs = pstmt.executeQuery();
         	
			while(rs.next()){
				
				ProductVO pvo = new ProductVO();
				Product_DetailVO pdvo = new Product_DetailVO();
				
				pvo.setPdno(rs.getString("pdno"));
				pvo.setBrand(rs.getString("brand"));
				pvo.setPdname(rs.getString("pdname"));
				pvo.setSaleprice(rs.getLong("saleprice"));
				pvo.setPdstatus(rs.getInt("pdstatus"));
				
				pdvo.setColor(rs.getString("color"));
				pdvo.setPd_qty(rs.getInt("pd_qty"));
				
				pvo.setPdvo(pdvo);
				
				ProductList.add(pvo);
				
			}
           
         
		} finally {
			close();
		}
		
		
		return ProductList;
		
	} // end of public List<ProductVO> select_admin_product_pagin(Map<String, String> paraMap) throws SQLException {


	// 관리자가 상품을 수정하기위한 상품리스트의 total 개수
	@Override
	public int get_admin_TotalProductCount(Map<String, String> paraMap) throws SQLException {
		
		int getTotalMemberCount = 0;
		
		try {
			conn = ds.getConnection();
         
			String sql = " select count(*) as cnt "
					+ " from tbl_product "
					+ " where pdstatus != 0 ";
			
			String colname = paraMap.get("searchType");
			String searchWord = paraMap.get("searchWord");
			
			if( (colname != null && !colname.trim().isEmpty() ) &&
				(searchWord != null && !searchWord.trim().isEmpty() ) ) {
			// if(colname != null && searchWord != null) { // 단일로 되는데...?
				
					sql += " and " + colname + " like '%'|| ? ||'%' ";
					// 컬럼명과 테이블명은 위치홀더(?)로 사용하면 꽝!!! 이다.
		            // 위치홀더(?)로 들어오는 것은 컬럼명과 테이블명이 아닌 오로지 데이터값만 들어온다.!!!!
					
			}
			
			pstmt = conn.prepareStatement(sql); 
			
			if( (colname != null && !colname.trim().isEmpty() ) &&
				(searchWord != null && !searchWord.trim().isEmpty() ) ) {
				// 검색이 있는경우
				pstmt.setString(1, searchWord);
				
			}
			
			rs = pstmt.executeQuery();
         	
			rs.next();
			
			getTotalMemberCount = rs.getInt("cnt");
         
		}  finally {
			close();
		}
		
		return getTotalMemberCount;
		
	} // end of public int get_admin_TotalProductCount(Map<String, String> paraMap) throws SQLException {


	// 특정 상품번호의 상품정보를 가져오는 메소드
	@Override
	public List<ProductVO> selectOneProductInfo(String pdno) throws SQLException {
		
		List<ProductVO> pvoList = new ArrayList<ProductVO>();
		
		try {
	         conn = ds.getConnection();
	         
	         String sql =  " select userid, name, email, mobile, postcode, address, detailaddress, extraaddress, gender "
	                  + "      , birthday, coin, point, to_char(registerday, 'yyyy-mm-dd') AS registerday "
	                  + " from tbl_member "
	                  + " where status = 1 and userid = ? ";
	                     
	         pstmt = conn.prepareStatement(sql);
	         
	         pstmt.setString(1, pdno);
	         
	         rs = pstmt.executeQuery();
	         
	         if(rs.next()) {
	            ProductVO pvo = new ProductVO();
	            
	           
	            
	            
	            
	         } // end of if(rs.next())-------------------
	         
	      } finally {
	         close();
	      }
		return null;
	}




	
	
	
	

}
