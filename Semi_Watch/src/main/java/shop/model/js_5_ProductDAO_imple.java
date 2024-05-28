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
	         
	         String sql = " select rno , pdno, pdname, brand, price, saleprice, pdimg1, pdstatus "
		         		+ " from "
		         		+ "	 ( "
		         		+ "	select rownum as rno, pdno, pdname, brand, price, saleprice, pdimg1, pdstatus "
		         		+ "	 from "
		         		+ "	( "
		         		+ "	 select pdno, pdname, brand, price, saleprice, pdimg1, pdstatus "
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
	         
	         String sql = " select rno , pdno, pdname, brand, price, saleprice, pdimg1, pdstatus "
		         		+ " from "
		         		+ "	 ( "
		         		+ "	select rownum as rno, pdno, pdname, brand, price, saleprice, pdimg1, pdstatus "
		         		+ "	 from "
		         		+ "	( "
		         		+ "	 select  pdno , pdname, brand, price, saleprice, pdimg1, pdstatus "
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
					   + " where pdstatus != -1 ";
			
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
					+ " where pdstatus != 0 "
					+ " order by pdinputdate desc ";
			
			String colname = paraMap.get("searchType");
			String searchWord = paraMap.get("searchWord");
			
			if( (colname != null && !colname.trim().isEmpty() ) &&
				(searchWord != null && !searchWord.trim().isEmpty() ) ) {
			
					sql += " and " + colname + " like '%'|| ? ||'%' ";
					
			}
				
			sql += " ) V "	
				+ " ) T "
				+ " where T.rno between ? and ? "	;
		        
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
		
	    int sum = 0;
	    
	    try {
	    	
	        conn = ds.getConnection();
	        
	        conn.setAutoCommit(false);

	        String sql = " update tbl_product set pdname = ? , brand = ? , price = ? , saleprice = ? , "
	        		+ " pdimg1 = ?, pd_content = ? , pd_contentimg = ? , pdstatus = ?, point = ? where pdno = ? ";
	        
	        pstmt = conn.prepareStatement(sql);
	        /*
	        System.out.println(paraMap.get("pdname"));
	        System.out.println(paraMap.get("brand"));
	        System.out.println(paraMap.get("price"));
	        System.out.println(paraMap.get("saleprice"));
	        System.out.println(paraMap.get("pdimg1"));
	        System.out.println(paraMap.get("pd_content"));
	        System.out.println(paraMap.get("pd_contentimg"));
	        System.out.println(paraMap.get("pdstatus"));
	        System.out.println(paraMap.get("point"));
	        System.out.println(paraMap.get("pdno"));
			*/
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

	        sum = pstmt.executeUpdate();

	        if(sum == 0) {
	        	
	            System.out.println("첫번째 SQL 문 실행 실패");
	        }

	        if(sum == 1) {
	        	System.out.println("첫번째 성공");
	        	
	        	String sqls = " select count(*) from tbl_pd_detail where fk_pdno = ? ";
	            
	            pstmt = conn.prepareStatement(sqls);
	            
	            pstmt.setString(1, paraMap.get("pdno"));
	            
	            rs = pstmt.executeQuery();
	        	
	        	if(rs.next()) {
	        		
	        		int count = rs.getInt(1);
	        		
	        		String sql2 = " delete from tbl_pd_detail where fk_pdno = ? ";
		            
		            pstmt = conn.prepareStatement(sql2);
		            
		            pstmt.setString(1, paraMap.get("pdno"));
		            
		            sum = pstmt.executeUpdate();

		            if(sum != count) {
		            	
		                System.out.println("두번째 SQL 실행 실패1");
		                
		            }else {
		            	
		            	System.out.println("두번째 SQL 성공");
		            	sum = 1;
		            }
		            
	        	}else {
	        		
	        		System.out.println("두번째 SQL 실행 실패2");
	        		
	        	}
	        	
	        }else {
	        	
	        	System.out.println("두번째 SQL 실행 실패3");
	        }
	        

	        if (sum == 1) {
	        	
	            for (int i = 1; i <= 3; i++) {
	            	
	                if ( paraMap.get("color" + i) != null && paraMap.get("pdqty" + i) != null ) {
	                	
	                    String sql3 = " INSERT INTO tbl_pd_detail (pd_detailno, fk_pdno, color, pd_qty) "
	                            + " VALUES (seq_product_detail.nextval, ?, ?, ?) ";
	                    
	                    pstmt = conn.prepareStatement(sql3);
	                    
	                    pstmt.setString(1, paraMap.get("pdno"));
	                    pstmt.setString(2, paraMap.get("color" + i));
	                    pstmt.setString(3, paraMap.get("pdqty" + i));
	                    
	                    int result = pstmt.executeUpdate();
	                    
	                    if (result == 1) {
	                    	
	                    	System.out.println("일단 insert 하나 성공");
	                    	sum = 1;
	                        
	                    }
	                }
	                
	            } // end of for
	            
	        } 
	        
	        if (sum == 1) {
	            String sqlCount = " select count(*) from tbl_product_img where fk_pdno = ? ";
	            
	            pstmt = conn.prepareStatement(sqlCount);
	            pstmt.setString(1, paraMap.get("pdno"));
	            
	            rs = pstmt.executeQuery();
	            
	            if (rs.next()) {
	                int count = rs.getInt(1);
	                
	                if (count > 0) {
	                    String deleteSql = " delete from tbl_product_img where fk_pdno = ? ";
	                    
	                    pstmt = conn.prepareStatement(deleteSql);
	                    
	                    pstmt.setString(1, paraMap.get("pdno"));
	                    
	                    int result = pstmt.executeUpdate();
	                    
	                    if (result > 0) {
	                        System.out.println("SQL 성공 이미지 삭제 완료");
	                    } else {
	                        System.out.println("SQL 실패 이미지 삭제 실패");
	                        sum = 0;
	                    }
	                } else {
	                    System.out.println("SQL 실패 상품번호에 대한 이미지 없음");
	                }
	            } else {
	                System.out.println("SQL 실패 count 없음 ");
	               
	            }
	        }

	        if(sum == 1) {
	        	
	            conn.commit();
	            
	            System.out.println("커밋 성공");
	            
	        } else {
	        	
	            try {
	                conn.rollback();
	                
	                System.out.println("롤백 성공");
	                
	            } catch (SQLException e1) {
	            	
	                System.out.println("롤백 실패");
	                
	                e1.printStackTrace();
	                
	            }
	        }

	    } catch(SQLException e) {
	    	
	        try {
	            conn.rollback();
	            
	        } catch (SQLException e1) {
	        	
	            e1.printStackTrace();
	            
	        }
	        e.printStackTrace();
	        
	    } finally {
	    	
	        close();
	    }

	    return sum;
	    
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
	         
	         String sql = " select color, pd_qty from tbl_pd_detail where fk_pdno = ? "; 
	        	           
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
	





	
	
	
	

}