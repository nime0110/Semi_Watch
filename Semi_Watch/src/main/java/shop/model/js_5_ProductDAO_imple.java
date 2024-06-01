package shop.model;




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

import shop.domain.CartVO;
import shop.domain.ImageVO;
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
	         
	         String sql = " select rno , pdno, pdname, brand, price, saleprice, pdimg1, pdstatus "
		         		+ " from "
		         		+ "	( "
		         		+ "	select rownum as rno, pdno, pdname, brand, price, saleprice, pdimg1, pdstatus "
		         		+ "	from "
		         		+ "	( "
		         		+ "	select distinct pdno as pdno , pdname, brand, price, saleprice, pdimg1, pdstatus, pdinputdate "
		         		+ "	from tbl_product P "
		         		+ " join tbl_pd_detail D "
		         		+ " on P.pdno = D.fk_pdno "
		         		+ " where pd_qty > 0 ";
	         			 
	         
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
         
			String sql = " select ceil(count(distinct pdno)/?) as pagecnt "
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
         
			String sql = " select count(distinct pdno) as cnt "
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

	// 브랜드명을 검색하는 메소드
	@Override
	public List<ProductVO> search_product_pagin_brand(Map<String, String> paraMap) throws SQLException {
		
		List<ProductVO> productList = new ArrayList<>();
		
		try {
	         conn = ds.getConnection();
	         
	         String sql = " select rno , pdno, pdname, brand, price, saleprice, pdimg1, pdstatus, pdinputdate "
		         		+ " from "
		         		+ "	 ( "
		         		+ "	select rownum as rno, pdno, pdname, brand, price, saleprice, pdimg1, pdstatus, pdinputdate "
		         		+ "	 from "
		         		+ "	( "
		         		+ "	 select distinct pdno as pdno, pdname, brand, price, saleprice, pdimg1, pdstatus, pdinputdate "
		         		+ "	from tbl_product P "
		         		+ " join tbl_pd_detail D "
		         		+ " on P.pdno = D.fk_pdno "
		         		+ " where pd_qty > 0 ";
	         			 
	         
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

	
	// 상품명을 검색하는 메소드
	@Override
	public List<ProductVO> search_product_pagin_pdname(Map<String, String> paraMap) throws SQLException {
		
		List<ProductVO> productList = new ArrayList<>();
		
		try {
	         conn = ds.getConnection();
	         
	         String sql = " select rno , pdno, pdname, brand, price, saleprice, pdimg1, pdstatus, pdinputdate "
		         		+ " from "
		         		+ "	 ( "
		         		+ "	select rownum as rno, pdno, pdname, brand, price, saleprice, pdimg1, pdstatus, pdinputdate "
		         		+ "	 from "
		         		+ "	( "
		         		+ "	 select  distinct pdno as pdno , pdname, brand, price, saleprice, pdimg1, pdstatus, pdinputdate "
		         		+ "	from tbl_product P "
		         		+ " join tbl_pd_detail D "
		         		+ " on P.pdno = D.fk_pdno "
		         		+ " where pd_qty > 0 ";
	         			 
	         
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
	} // end of public List<ProductVO> search_product_pagin_pdname(Map<String, String> paraMap) throws SQLException {


	// 브랜드명을 조회해서 나오는 토탈페이지구하기
	@Override
	public int search_brand_TotalPage(Map<String, String> paraMap) throws SQLException {
		
		int getTotalPage = 0;
		
		try {
			
			conn = ds.getConnection();
         
			String sql = " select ceil(count(distinct pdno)/?) as pagecnt "
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
         
			String sql = " select ceil(count(distinct pdno)/?) as pagecnt "
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
	         			+ " price, saleprice, pdimg1, pd_content, pd_contentimg , point) "
	         			+ " values(?,?,?, "
	         			+ "	?,?,?,?,?,?) ";
	         
	         pstmt = conn.prepareStatement(sql);
	         
	         pstmt.setString(1, pvo.getPdno());
	         pstmt.setString(2, pvo.getPdname());
	         pstmt.setString(3, pvo.getBrand());    
	         pstmt.setInt(4, (int) pvo.getPrice()); 
	         pstmt.setInt(5, (int) pvo.getSaleprice()); 
	         pstmt.setString(6, pvo.getPdimg1()); 
	         pstmt.setString(7, pvo.getPd_content());
	         pstmt.setString(8, pvo.getPd_contentimg());
	         pstmt.setInt(9, pvo.getPoint());
	         
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
         
			String sql = " select rno,  pdno, pdname, brand,  price , saleprice, pdstatus, pdinputdate "
					+ " from "
					+ " ( "
					+ " select rownum as rno,  pdno, pdname, brand, price , saleprice, pdstatus, pdinputdate "
					+ " from "
					+ " ( "
					+ " select pdno, pdname, brand, price , saleprice, pdstatus "
					+ " , pdinputdate "
					+ " from tbl_product "
					+ " where pdstatus != 0 ";
			
			String colname = paraMap.get("searchType");
			String searchWord = paraMap.get("searchWord");
			
			if( (colname != null && !colname.trim().isEmpty() ) &&
					(searchWord != null && !searchWord.trim().isEmpty() ) ) {
				
						sql += " and " +  colname + " like '%'|| ? ||'%' ";
						
			}
			
			sql += " order by pdinputdate desc "
				 + " ) V "	
				 + " ) T "
				 + " where T.rno between ? and ? ";
			
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

				pvo.setPdno(rs.getString("pdno"));
				pvo.setBrand(rs.getString("brand"));
				pvo.setPdname(rs.getString("pdname"));
				pvo.setPrice(rs.getLong("price"));
				pvo.setSaleprice(rs.getLong("saleprice"));
				pvo.setPdstatus(rs.getInt("pdstatus"));
				
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


	// 특정 상품번호의 상품정보를 가져오는 메소드1
	@Override
	public ProductVO selectOneProductInfo(String pdno) throws SQLException {
		
		ProductVO pvo = null;
		
		try {
	         conn = ds.getConnection();
	         
	         String sql = " select pdno, pdname , brand , price, saleprice, pdimg1, "
	                  	+ " pd_content , to_char(pdinputdate, 'yyyy-mm-dd') as pdinputdate,"
	                  	+ " pdstatus , point , pd_contentimg "
	                  	+ " from tbl_product "	                  
	                  	+ " where pdno = ? ";
	                     
	         pstmt = conn.prepareStatement(sql);
	         
	         pstmt.setInt(1, Integer.parseInt(pdno));
	         
	         rs = pstmt.executeQuery();
	         
	         if(rs.next()) {
	            pvo = new ProductVO();
	            
	            pvo.setPdno(rs.getString(1));
	            pvo.setPdname(rs.getString(2));
	            pvo.setBrand(rs.getString(3));
	            pvo.setPrice(rs.getLong(4));
	            pvo.setSaleprice(rs.getLong(5));
	            pvo.setPdimg1(rs.getString(6));
	            pvo.setPd_content(rs.getString(7));
	            pvo.setPdinputdate(rs.getString(8));
	            pvo.setPdstatus(rs.getInt(9));
	            pvo.setPoint(rs.getInt(10));
	            pvo.setPd_contentimg(rs.getString(11));
	            
	          
	         } // end of if(rs.next())-------------------
	         
	      } finally {
	         close();
	      }
		return pvo;
		
	} // end of  public List<ProductVO> selectOneProductInfo(String pdno) throws SQLException {


	// 상품삭제하기
	@Override
	public int delete_product(String pdno) throws SQLException {
		
		int result = 0;
		
		try {
			
	         conn = ds.getConnection();
	         
	         String sql = " delete from tbl_product where pdno = ? ";
	                     
	         pstmt = conn.prepareStatement(sql);
	         
	         pstmt.setString(1, pdno);
	         
	         result = pstmt.executeUpdate();
	         
	      } finally {
	      		
	  			close();
	  		
	  	  }
	    
		
		return result;
		
	} // end of public int delete_product(String pdno) {


	// 삭제할 상품이미지 파일명 가져오기
	@Override
	public List<String> select_imgfilename(String pdno) throws SQLException {
		
		List<String> imglist = new ArrayList<>();
		
		try {
	         conn = ds.getConnection();
	         
	         String sql = " select imgfilename "
	        		 	+ " from "
	        		 	+ " ( "
	        		 	+ " select pd_extraimg as imgfilename from tbl_product_img where fk_pdno = ? "
	        		 	+ " union "
	        		 	+ " select pdimg1 as imgfilename  from tbl_product where pdno = ? "
	        		 	+ " union "
	        		 	+ " select pd_contentimg as imgfilename from tbl_product where pdno = ? "
	        		 	+ " ) "; 
	         		
	                     
	         pstmt = conn.prepareStatement(sql);
	         
	         pstmt.setString(1, pdno);
	         pstmt.setString(2, pdno);
	         pstmt.setString(3, pdno);
	         
	         rs = pstmt.executeQuery();
	         
	         while(rs.next()) {
	        	 
	        	String img = rs.getString(1); 
	            
	            imglist.add(img);
	            
	         } // end of if(rs.next())-------------------
	         
	      } finally {
	         close();
	      }
		
		return imglist;
		
	} // end of public List<ImageVO> select_imgfilename(String pdno) throws SQLException {


	// 상품테이블 업데이트 후 자식테이블 삭제 후 insert ==> 수동커밋!!
	@Override
	public int delete_after_insert(Map<String, String> paraMap) {

	    int n1 = 0;
	    int n2 = 0;
	    int n3 = 0;
	    int nnn = 0;
	    try {
	        conn = ds.getConnection();
	        conn.setAutoCommit(false);

	        String sql = " update tbl_product set pdname = ?, brand = ?, price = ?, saleprice = ?, "
	                + " pdimg1 = ?, pd_content = ?, pd_contentimg = ?, pdstatus = ?, point = ? where pdno = ? ";

	        pstmt = conn.prepareStatement(sql);
	        
	        pstmt.setString(1, paraMap.get("pdname"));
	        pstmt.setString(2, paraMap.get("brand"));
	        pstmt.setString(3, paraMap.get("price"));
	        pstmt.setString(4, paraMap.get("saleprice"));
	        pstmt.setString(5, paraMap.get("pdimg1"));
	        pstmt.setString(6, paraMap.get("pd_content"));
	        pstmt.setString(7, paraMap.get("pd_contentimg"));
	        pstmt.setString(8, paraMap.get("pdstatus"));
	        pstmt.setString(9, paraMap.get("point"));
	        pstmt.setString(10, paraMap.get("pdno"));

	        n1 = pstmt.executeUpdate();

	        if (n1 == 0) {
	            System.out.println("상품테이블 업데이트 실패 롤백");
	            conn.rollback();
	            return 0;
	        }

	        System.out.println("첫번째 성공");

	        for (int i = 1; i <= 3; i++) {
	        	
	            if (paraMap.get("color" + i) != null && paraMap.get("pdqty" + i) != null) {
	            	
	                // tbl_pd_detail 테이블에서 fk_pdno와 color가 일치하는 레코드가 있는지 확인
	                String checkSql = " select count(*) from tbl_pd_detail where fk_pdno = ? and color = ? ";
	                
	                pstmt = conn.prepareStatement(checkSql);
	                pstmt.setString(1, paraMap.get("pdno"));
	                pstmt.setString(2, paraMap.get("color" + i));
	                
	                rs = pstmt.executeQuery();

	                if (rs.next() && rs.getInt(1) > 0) {
	                    // 이미 존재하면 update
	                    String updateSql = " update tbl_pd_detail set pd_qty = ? where fk_pdno = ? and color = ? ";
	                    
	                    pstmt = conn.prepareStatement(updateSql);
	                    
	                    pstmt.setString(1, paraMap.get("pdqty" + i));
	                    pstmt.setString(2, paraMap.get("pdno"));
	                    pstmt.setString(3, paraMap.get("color" + i));
	                    
	                    int result = pstmt.executeUpdate();

	                    if (result == 1) {
	                    	
	                        System.out.println("상품상세 업데이트 성공");
	                        
	                        n2 = 1;
	                        
	                    } else {
	                        System.out.println("상품상세 업데이트 실패 롤백");
	                        
	                        conn.rollback();
	                        return 0;
	                    }
	                } else {
	                    // 존재하지 않으면 insert
	                	
	                    String insertSql = " insert into tbl_pd_detail (pd_detailno, fk_pdno, color, pd_qty) "
	                            + " values (seq_product_detail.nextval, ?, ?, ?) ";
	                    
	                    pstmt = conn.prepareStatement(insertSql);
	                    
	                    pstmt.setString(1, paraMap.get("pdno"));
	                    pstmt.setString(2, paraMap.get("color" + i));
	                    pstmt.setString(3, paraMap.get("pdqty" + i));
	                    
	                    int result = pstmt.executeUpdate();

	                    if (result == 1) {
	                    	
	                        System.out.println("삽입 성공");
	                        
	                        n2 = 1;
	                        
	                    } else {
	                    	
	                        System.out.println("삽입 실패 롤백");
	                        
	                        conn.rollback();
	                        
	                        return 0;
	                    }
	                    
	                }// end of else
	                
	            } // end of if color1 재고1 이 null 아닐경우
	            
	        } // end of for

	        if (n2 == 1) {
	        	
	            String sqlCount = " select count(*) from tbl_product_img where fk_pdno = ? ";
	            
	            pstmt = conn.prepareStatement(sqlCount);
	            
	            pstmt.setString(1, paraMap.get("pdno"));
	            
	            rs = pstmt.executeQuery();

	            if (rs.next()) {
	            	
	                int count = rs.getInt(1);

	                if (count > 0) {
	                	// 추가이미지 파일이 존재하면 삭제하기
	                    String deleteSql = " delete from tbl_product_img where fk_pdno = ? ";
	                    
	                    pstmt = conn.prepareStatement(deleteSql);
	                    
	                    pstmt.setString(1, paraMap.get("pdno"));

	                    n3 = pstmt.executeUpdate();

	                    if (n3 > 0) {
	                    	
	                        System.out.println("이미지 삭제 성공");
	                        
	                        nnn = 1;
	                        
	                    } else {
	                        System.out.println("이미지 삭제 실패");
	                        
	                        conn.rollback();
	                        System.out.println("이미 삭제실패 롤백 성공");
	                        return 0;  
	                    }
	                } else {
	                    System.out.println("상품번호에 대한 이미지 없음");
	                    
	                    nnn = 1;
	                }
	            } else {
	                System.out.println("이미지 수 카운트 실패");
	                
	                conn.rollback();
	                System.out.println("이미지 카운트 실패 롤백 성공");
	                return 0;
	                
	            } // end of else
	            
	        } // end of n2 == 1 상품상세 수정이 성공했을때 

	        conn.commit();
	        System.out.println("커밋 성공");
	    } catch (SQLException e) {
	        try {
	            conn.rollback();
	            System.out.println("e 롤백 성공");
	        } catch (SQLException e1) {
	            System.out.println("e1 롤백 실패");
	            e1.printStackTrace();
	        }
	        e.printStackTrace();
	    } finally {
	        close();
	    }

	    return nnn;
	    
	} // end of public int delete_after_insert(Map<String, String> paraMap) {


	// 삭제할 추가 상품이미지 파일명 가져오기
	@Override
	public List<String> select_extraimgfilename(String pdno2) throws SQLException {
		
		List<String> imglist = new ArrayList<>();
		
		try {
			
	         conn = ds.getConnection();
	         
	         String sql = " select pd_extraimg from tbl_product_img where fk_pdno = ? "; 
	        	           
	         pstmt = conn.prepareStatement(sql);
	         
	         pstmt.setString(1, pdno2);
	        
	         rs = pstmt.executeQuery();
	         
	         while(rs.next()) {
	        	 
	        	String img = rs.getString(1); 
	            
	            imglist.add(img);
	            
	         } // end of if(rs.next())-------------------
	         
	      } finally {
	         close();
	      }
		
		return imglist;
		
	} // end of public List<String> select_extraimgfilename(String pdno2) throws SQLException {


	// 상품수정페이지에 띄울 상품상세정보(색상별 재고)
	@Override
	public List<Product_DetailVO> selectOnePDetail(String pdno) throws SQLException {
		
		List<Product_DetailVO> pdlist = new ArrayList<>();
		
		try {
			
	         conn = ds.getConnection();
	         
	         String sql = " select decode(color, 'none' , '단일색상' , color) as color ,"
	         		+ " pd_qty from tbl_pd_detail where fk_pdno = ? "; 
	        	           
	         pstmt = conn.prepareStatement(sql);
	         
	         pstmt.setString(1, pdno);
	        
	         rs = pstmt.executeQuery();
	         
	         while(rs.next()) {
	        	 
	        	Product_DetailVO pdvo = new Product_DetailVO();
	        	
	        	pdvo.setColor(rs.getString(1));
	            pdvo.setPd_qty(rs.getInt(2));
	            
	            pdlist.add(pdvo);
	        	
	         } // end of if(rs.next())-------------------
	         
	      } finally {
	         close();
	      }
		
		return pdlist;
		
	} // end of public List<Product_DetailVO> selectOnePDetail(String pdno) throws SQLException {


	// 상품수정페이지에 띄울 상품추가이미지
	@Override
	public List<ImageVO> extraimgfilename(String pdno) throws SQLException {
		
		List<ImageVO> imglist = new ArrayList<ImageVO>();
		
		try {
			
	         conn = ds.getConnection();
	         
	         String sql = " select pd_extraimg from tbl_product_img where fk_pdno = ? "; 
	        	           
	         pstmt = conn.prepareStatement(sql);
	         
	         pstmt.setString(1, pdno);
	        
	         rs = pstmt.executeQuery();
	         
	         while(rs.next()) {
	        	 
	        	ImageVO img = new ImageVO();
	        	
	        	img.setImgfilename(rs.getString(1));
	            
	        	imglist.add(img);
	        	
	         } // end of if(rs.next())-------------------
	         
	      } finally {
	    	  
	         close();
	      }
		
		return imglist;
		
	} // end of public List<ImageVO> extraimgfilename(String pdno) throws SQLException {


	// 로그인한 유저의 장바구니 테이블 불러오기
	@Override
	public List<CartVO> selectProductCart(String userid) throws SQLException {
		
		List<CartVO> cartlist = new ArrayList<>();
		
		try {
			
	         conn = ds.getConnection();
	         
	         String sql = " select cartno, pdname, brand, pd_detailno, color, saleprice, "
	         		+ " pd_qty, cart_qty, pdimg1, point, pdno  "
	         		+ " from tbl_cart C join tbl_pd_detail D "
	         		+ " on C.fk_pd_detailno = D.pd_detailno "
	         		+ " join tbl_product P on "
	         		+ " D.fk_pdno = P.pdno "
	         		+ " where C.fk_userid = ? "; 
	        	           
	         pstmt = conn.prepareStatement(sql);
	         
	         pstmt.setString(1, userid);
	        
	         rs = pstmt.executeQuery();
	         
	         while(rs.next()) {
	        	 
	        	 CartVO cart = new CartVO();
	        	 
	        	 cart.setCartno(rs.getString("cartno"));
	        	 cart.setCart_qty(rs.getInt("cart_qty"));
	        	 cart.setFk_pd_detailno(rs.getString("pd_detailno"));
	        	 
	        	 ProductVO pvo = new ProductVO();
	        	 
	        	 pvo.setPdno(rs.getString("pdno"));
	        	 pvo.setPdname(rs.getString("pdname"));
	        	 pvo.setBrand(rs.getString("brand"));
	        	 pvo.setPdimg1(rs.getString("pdimg1"));
	        	 pvo.setPoint(rs.getInt("point"));
	        	 pvo.setSaleprice(rs.getLong("saleprice"));
	        	 
	        	 Product_DetailVO pdvo = new Product_DetailVO();
	        	 
	        	 pdvo.setColor(rs.getString("color"));
	        	 pdvo.setPd_qty(rs.getInt("pd_qty"));
	        	 pdvo.setPd_detailno(rs.getString("pd_detailno"));
	        	 
	        	 cart.setProd(pvo);
	        	 cart.setPdvo(pdvo);
	        	 
	        	 
	        	 cartlist.add(cart);
	        	 
	        	
	         } // end of if(rs.next())-------------------
	         
	      } finally {
	    	  
	         close();
	      }
		
		return cartlist;
		
	} // end of public List<CartVO> selectProductCart(String userid) throws SQLException {
	

	// 장바구니 테이블에서 특정 상품을 장바구니에서 비우기
	@Override
	public int delCart(String cartno) throws SQLException {
		
		int n = 0;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " delete from tbl_cart where cartno = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, cartno);
			
			n = pstmt.executeUpdate();
			

		} finally {
			
			close();
		}
		
		return n;
		
	} // end of public int deleteCart(String cartno) throws SQLException { 


	// 장바구니 테이블에서 특정 상품 장바구니수량 변경하기
	@Override
	public int updateCart(Map<String, String> paraMap) throws SQLException {
		
		int n = 0;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " update tbl_cart set cart_qty = ? where cartno = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, paraMap.get("cart_qty"));
			pstmt.setString(2, paraMap.get("cartno"));
			
			n = pstmt.executeUpdate();
			

		} finally {
			
			close();
		}
		
		return n;
		
	} // end of public int updateCart(Map<String, String> paraMap) throws SQLException { 


	
	// 관리자가 보는 브랜드별 주문 통계
	@Override
	public List<Map<String, String>> Purchase_byBrand(String userid) throws SQLException {
		
		List<Map<String,String>> Purchase_map_List = new ArrayList<>(); 
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " select BRAND, count(brand) as CNT, "
					+ " sum(D.order_qty * D.order_price) as SUMPAY , "
					+ " round( sum(D.order_qty * D.order_price) / (select sum(order_qty * order_price) from tbl_orderdetail  ) * 100 , 2) as SUMPAY_PCT "
					+ " from tbl_order O join "
					+ " tbl_orderdetail D "
					+ " on O.ordercode = D.fk_ordercode "
					+ " join tbl_pd_detail E "
					+ " on E.pd_detailno = D.fk_pd_detailno "
					+ " join tbl_product P "
					+ " on E.fk_pdno = P.pdno "
					+ " group by brand "
					+ " order by sumpay desc ";
			
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
	                  
	        while(rs.next()) {
	            String brand = rs.getString("BRAND");
	            String cnt = rs.getString("CNT");
	            String sumpay = rs.getString("SUMPAY");
	            String sumpay_pct = rs.getString("SUMPAY_PCT");
	            
	            Map<String, String> map = new HashMap<>();
	            map.put("brand", brand);
	            map.put("cnt", cnt);
	            map.put("sumpay", sumpay);
	            map.put("sumpay_pct", sumpay_pct);
	            
	            Purchase_map_List.add(map);
	            
	        } // end of while----------------------------------
			
		}finally {
			
			close();
			
		}
		
		return Purchase_map_List;
		
	} // end of public List<Map<String, String>> Purchase_byCategory(String userid) throws SQLException


	
	// 관리자가 보는 브랜드별 주문건수 통계
	@Override
	public List<Map<String, String>> Purchase_byBrandCnt(String userid) throws SQLException  {
		
		List<Map<String,String>> Purchase_map_List = new ArrayList<>(); 
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " select brand, count(brand) as cnt, "
					+ " sum(D.order_qty * D.order_price) as sumpay , "
					+ " round( sum(D.order_qty ) / (select sum(order_qty) from tbl_orderdetail  ) * 100 , 2) as order_pct "
					+ " from tbl_order O join "
					+ " tbl_orderdetail D "
					+ " on O.ordercode = D.fk_ordercode "
					+ " join tbl_pd_detail E "
					+ " on E.pd_detailno = D.fk_pd_detailno "
					+ " join tbl_product P "
					+ " on E.fk_pdno = P.pdno "
					+ " group by brand "
					+ " order by sumpay desc ";
			
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
	                  
	        while(rs.next()) {
	            String brand = rs.getString("BRAND");
	            String cnt = rs.getString("CNT");
	            String sumpay = rs.getString("SUMPAY");
	            String order_pct = rs.getString("ORDER_PCT");
	            
	            Map<String, String> map = new HashMap<>();
	            map.put("brand", brand);
	            map.put("cnt", cnt);
	            map.put("sumpay", sumpay);
	            map.put("order_pct", order_pct);
	            
	            Purchase_map_List.add(map);
	            
	        } // end of while----------------------------------
			
		}finally {
			
			close();
			
		}
		
		return Purchase_map_List;
	}


	
	// 관리자가 보는 브랜드 월별 주문총액 통계
	@Override
	public List<Map<String, String>> Purchase_byMonth(String userid) throws SQLException {
		
		List<Map<String, String>> myPurchase_map_List = new ArrayList<>();
	      
	      try {
	    	  
	         conn = ds.getConnection();
	         
	         String sql = " WITH "
	         		+ " O AS "
	         		+ " (SELECT ordercode, total_orderdate "
	         		+ " FROM tbl_order "
	         		+ " WHERE to_char(total_orderdate, 'yyyy') = to_char(sysdate, 'yyyy') "
	         		+ " ) "
	         		+ " , "
	         		+ " OD AS "
	         		+ " (SELECT fk_ordercode, fk_pd_detailno, order_qty, order_price "
	         		+ " FROM tbl_orderdetail "
	         		+ " ) "
	         		+ " SELECT brand "
	         		+ "      , COUNT(brand) AS CNT "
	         		+ "      , SUM(OD.order_qty * OD.order_price) AS SUMPAY "
	         		+ "      , round( SUM(OD.order_qty * OD.order_price)/( SELECT SUM(OD.order_qty * OD.order_price) "
	         		+ "									            FROM O JOIN OD "
	         		+ "                                             ON O.ordercode = OD.fk_ordercode)*100, 2) AS SUMPAY_PCT "
	         		+ "       , SUM( decode( to_char(O.total_orderdate,'mm'), '01', OD.order_qty * OD.order_price, 0) ) AS M_01 "
	         		+ "       , SUM( decode( to_char(O.total_orderdate,'mm'), '02', OD.order_qty * OD.order_price, 0) ) AS M_02 "
	         		+ "       , SUM( decode( to_char(O.total_orderdate,'mm'), '03', OD.order_qty * OD.order_price, 0) ) AS M_03 "
	         		+ "       , SUM( decode( to_char(O.total_orderdate,'mm'), '04', OD.order_qty * OD.order_price, 0) ) AS M_04 "
	         		+ "       , SUM( decode( to_char(O.total_orderdate,'mm'), '05', OD.order_qty * OD.order_price, 0) ) AS M_05 "
	         		+ "       , SUM( decode( to_char(O.total_orderdate,'mm'), '06', OD.order_qty * OD.order_price, 0) ) AS M_06 "
	         		+ "       , SUM( decode( to_char(O.total_orderdate,'mm'), '07', OD.order_qty * OD.order_price, 0) ) AS M_07 "
	         		+ "       , SUM( decode( to_char(O.total_orderdate,'mm'), '08', OD.order_qty * OD.order_price, 0) ) AS M_08 "
	         		+ "       , SUM( decode( to_char(O.total_orderdate,'mm'), '09', OD.order_qty * OD.order_price, 0) ) AS M_09 "
	         		+ "       , SUM( decode( to_char(O.total_orderdate,'mm'), '10', OD.order_qty * OD.order_price, 0) ) AS M_10 "
	         		+ "       , SUM( decode( to_char(O.total_orderdate,'mm'), '11', OD.order_qty * OD.order_price, 0) ) AS M_11 "
	         		+ "       , SUM( decode( to_char(O.total_orderdate,'mm'), '12', OD.order_qty * OD.order_price, 0) ) AS M_12 "
	         		+ "  FROM O JOIN OD "
	         		+ "  ON O.ordercode = OD.fk_ordercode "
	         		+ "  JOIN tbl_pd_detail D "
	         		+ "  ON OD.fk_pd_detailno = D.pd_detailno "
	         		+ "  join tbl_product P "
	         		+ "  on D.fk_pdno = P.pdno "
	         		+ "  GROUP BY brand "
	         		+ "  ORDER BY 3 desc ";
	         
	         pstmt = conn.prepareStatement(sql);
	   
	         rs = pstmt.executeQuery();
	                  
	         while(rs.next()) {
	            String brand = rs.getString("Brand");
	            String cnt = rs.getString("CNT");
	            String sumpay = rs.getString("SUMPAY");
	            String sumpay_pct = rs.getString("SUMPAY_PCT");
	            String m_01 = rs.getString("M_01");
	            String m_02 = rs.getString("M_02");
	            String m_03 = rs.getString("M_03");
	            String m_04 = rs.getString("M_04");
	            String m_05 = rs.getString("M_05");
	            String m_06 = rs.getString("M_06");
	            String m_07 = rs.getString("M_07");
	            String m_08 = rs.getString("M_08");
	            String m_09 = rs.getString("M_09");
	            String m_10 = rs.getString("M_10");
	            String m_11 = rs.getString("M_11");
	            String m_12 = rs.getString("M_12");
	            
	            Map<String, String> map = new HashMap<>();
	            map.put("brand", brand);
	            map.put("cnt", cnt);
	            map.put("sumpay", sumpay);
	            map.put("sumpay_pct", sumpay_pct);
	            map.put("m_01", m_01);
	            map.put("m_02", m_02);
	            map.put("m_03", m_03);
	            map.put("m_04", m_04);
	            map.put("m_05", m_05);
	            map.put("m_06", m_06);
	            map.put("m_07", m_07);
	            map.put("m_08", m_08);
	            map.put("m_09", m_09);
	            map.put("m_10", m_10);
	            map.put("m_11", m_11);
	            map.put("m_12", m_12);
	            
	            myPurchase_map_List.add(map);
	         } // end of while----------------------------------
	                  
	      } finally {
	         close();
	      }
	      
	      return myPurchase_map_List;
	      
	}// end ofpublic List<Map<String, String>> Purchase_byMonth(String userid) throws SQLException {


	
	// 관리자가 보는 월별 주문건수 통계
	@Override
	public List<Map<String, String>> Purchase_byMonthCnt(String userid) throws SQLException {
		
		List<Map<String, String>> myPurchase_map_List = new ArrayList<>();
	      
	      try {
	    	  
	         conn = ds.getConnection();
	         
	         String sql = " WITH "
	         		+ "  O AS "
	         		+ "  (SELECT ordercode, total_orderdate "
	         		+ "   FROM tbl_order "
	         		+ "   WHERE to_char(total_orderdate, 'yyyy') = to_char(sysdate, 'yyyy') "
	         		+ "  ) "
	         		+ "  , "
	         		+ "  OD AS "
	         		+ "  (SELECT fk_ordercode, fk_pd_detailno, order_qty, order_price "
	         		+ "   FROM tbl_orderdetail "
	         		+ "  ) "
	         		+ "  SELECT brand "
	         		+ "       , COUNT(brand) AS CNT "
	         		+ "       , SUM(OD.order_qty) AS SUMPAY "
	         		+ "       , round( SUM(OD.order_qty)/( SELECT SUM(OD.order_qty) "
	         		+ "                                             FROM O JOIN OD "
	         		+ "                                             ON O.ordercode = OD.fk_ordercode)*100, 2) AS ORDER_PCT "
	         		+ "       , SUM( decode( to_char(O.total_orderdate,'mm'), '01', OD.order_qty, 0) ) AS M_01 "
	         		+ "       , SUM( decode( to_char(O.total_orderdate,'mm'), '02', OD.order_qty, 0) ) AS M_02 "
	         		+ "       , SUM( decode( to_char(O.total_orderdate,'mm'), '03', OD.order_qty, 0) ) AS M_03 "
	         		+ "       , SUM( decode( to_char(O.total_orderdate,'mm'), '04', OD.order_qty, 0) ) AS M_04 "
	         		+ "       , SUM( decode( to_char(O.total_orderdate,'mm'), '05', OD.order_qty , 0) ) AS M_05 "
	         		+ "       , SUM( decode( to_char(O.total_orderdate,'mm'), '06', OD.order_qty, 0) ) AS M_06 "
	         		+ "       , SUM( decode( to_char(O.total_orderdate,'mm'), '07', OD.order_qty, 0) ) AS M_07 "
	         		+ "       , SUM( decode( to_char(O.total_orderdate,'mm'), '08', OD.order_qty, 0) ) AS M_08 "
	         		+ "       , SUM( decode( to_char(O.total_orderdate,'mm'), '09', OD.order_qty, 0) ) AS M_09 "
	         		+ "       , SUM( decode( to_char(O.total_orderdate,'mm'), '10', OD.order_qty, 0) ) AS M_10 "
	         		+ "       , SUM( decode( to_char(O.total_orderdate,'mm'), '11', OD.order_qty, 0) ) AS M_11 "
	         		+ "       , SUM( decode( to_char(O.total_orderdate,'mm'), '12', OD.order_qty, 0) ) AS M_12 "
	         		+ "  FROM O JOIN OD "
	         		+ "  ON O.ordercode = OD.fk_ordercode "
	         		+ "  JOIN tbl_pd_detail D "
	         		+ "  ON OD.fk_pd_detailno = D.pd_detailno "
	         		+ "  join tbl_product P "
	         		+ "  on D.fk_pdno = P.pdno "
	         		+ "  GROUP BY brand "
	         		+ "  ORDER BY 3 desc ";
	         
	         pstmt = conn.prepareStatement(sql);
	   
	         rs = pstmt.executeQuery();
	                  
	         while(rs.next()) {
	            String brand = rs.getString("Brand");
	            String cnt = rs.getString("CNT");
	            String sumpay = rs.getString("SUMPAY");
	            String order_pct = rs.getString("ORDER_PCT");
	            String m_01 = rs.getString("M_01");
	            String m_02 = rs.getString("M_02");
	            String m_03 = rs.getString("M_03");
	            String m_04 = rs.getString("M_04");
	            String m_05 = rs.getString("M_05");
	            String m_06 = rs.getString("M_06");
	            String m_07 = rs.getString("M_07");
	            String m_08 = rs.getString("M_08");
	            String m_09 = rs.getString("M_09");
	            String m_10 = rs.getString("M_10");
	            String m_11 = rs.getString("M_11");
	            String m_12 = rs.getString("M_12");
	            
	            Map<String, String> map = new HashMap<>();
	            map.put("brand", brand);
	            map.put("cnt", cnt);
	            map.put("sumpay", sumpay);
	            map.put("order_pct", order_pct);
	            map.put("m_01", m_01);
	            map.put("m_02", m_02);
	            map.put("m_03", m_03);
	            map.put("m_04", m_04);
	            map.put("m_05", m_05);
	            map.put("m_06", m_06);
	            map.put("m_07", m_07);
	            map.put("m_08", m_08);
	            map.put("m_09", m_09);
	            map.put("m_10", m_10);
	            map.put("m_11", m_11);
	            map.put("m_12", m_12);
	            
	            myPurchase_map_List.add(map);
	         } // end of while----------------------------------
	                  
	      } finally {
	         close();
	      }
	      
	      return myPurchase_map_List;
	      
	} // end of public List<Map<String, String>> Purchase_byMonthCnt(String userid) throws SQLException {

	
	
	
	

}