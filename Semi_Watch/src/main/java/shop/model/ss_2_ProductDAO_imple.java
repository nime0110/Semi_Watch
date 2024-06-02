package shop.model;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
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

import member.domain.MemberVO;
import review.domain.ReviewVO;
import shop.domain.ImageVO;
import shop.domain.ProductVO;
import shop.domain.Product_DetailVO;

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
public List<ProductVO> getWishListItem(String pdno) throws SQLException {
	 List<ProductVO> wishProductList = new ArrayList<>(); 
	    
	    try {
	        conn = ds.getConnection();
	        
	        String sql = " select pdname, pdimg1, saleprice, pdno "
	        		+ " from tbl_product "
	        		+ " where pdno IN ( " + pdno + " ) ";
	       pstmt = conn.prepareStatement(sql);
	             
	       rs = pstmt.executeQuery();
	                
	       while(rs.next()) {
	    	  ProductVO pvo = new ProductVO();
	    	  pvo.setPdname(rs.getString(1));
	    	  pvo.setPdimg1(rs.getString(2));
	    	  pvo.setSaleprice(rs.getLong(3));
	    	  pvo.setPdno(rs.getString(4));

	    	  wishProductList.add(pvo);
	       }// end of while(rs.next())----------------------------------
	       
	    } finally {
	       close();
	    }   
	    
	    return wishProductList;

	
	
}

//제품번호를 가지고서 제품의 정보 가져오기
@Override
public ProductVO selectOneProductBypdno(String pdno) throws SQLException {
	ProductVO pvo = null;
	
	try {
		conn = ds.getConnection();
		
		String sql = " select pdno, pdname, brand, pdimg1, price, saleprice, pd_content, pd_contentimg, point "
				+ " from tbl_product "
				+ " where pdno = ? ";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, pdno);
		
		rs = pstmt.executeQuery();
		
		while(rs.next()) {
			pvo = new ProductVO();
			
			pvo.setPdno(rs.getString("pdno"));
			pvo.setPdname(rs.getString("pdname"));
			pvo.setBrand(rs.getString("brand"));
			pvo.setPdimg1(rs.getString("pdimg1"));
			pvo.setPrice(rs.getLong("price"));
			pvo.setSaleprice(rs.getLong("saleprice"));
			pvo.setPd_content(rs.getString("pd_content"));
			pvo.setPd_contentimg(rs.getString("pd_contentimg"));
			pvo.setPoint(rs.getInt("point"));
			
			
		}//end of while(rs.next()) --------------
		
	} finally {
		close();
	}
	
	return pvo;
}

//제품정보를 가지고서 상세이미지 가져오기 
@Override
public List<String> getImagesByPnum(String pdno) throws SQLException {
	
	List<String> imgList = new ArrayList<>(); //빈 리스트 만들기
	
	try {
		conn = ds.getConnection();
		
		String sql =  " select pd_extraimg "
				+ " from tbl_product_img "
				+ " where fk_pdno = ? ";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, pdno);
		
		rs = pstmt.executeQuery();
		
		while(rs.next()) {
			//추가이미지가 있을 경우
			String imgfilename = rs.getString(1);
			imgList.add(imgfilename);
		}//end of while(rs.next()) --------------
		
	} finally {
		close();
	}
	
	
	return imgList;	
}


//제품번호를 가지고서 제품의 색상 가져오기 
@Override
public List<String> getColorsByPnum(String pdno) throws SQLException {

	List<String> colorList = new ArrayList<>(); //빈 리스트 만들기
	
	try {
		conn = ds.getConnection();
		
		String sql =  " select color "
				+ " from tbl_pd_detail "
				+ " where fk_pdno IN ( " + pdno + " ) and pd_qty > 0 ";
		//재고가 있는 상품만 가져오는 조건 추가 (and  pd_qty > 0)
		pstmt = conn.prepareStatement(sql);
		
		rs = pstmt.executeQuery();
		
		while(rs.next()) {
			colorList.add(rs.getString("color"));
		 }//end of while(rs.next()) --------------
		
	} finally {
		close();
	}
	
	return colorList;	
}

//들어온 컬러 코드와 제품번호로 제품상세번호 가져오는 메소드 
@Override
public List<Product_DetailVO> getWishDetailByPnum(String pdno, String selectedColor) throws SQLException {
	List<Product_DetailVO> wishProductDetailList = new ArrayList<>(); 
    
    try {
        conn = ds.getConnection();
        
        String sql = " SELECT pd_detailno "
        		+ " FROM tbl_product A "
        		+ " JOIN tbl_pd_detail B "
        		+ " ON A.pdno = B.fk_pdno "
        		+ " WHERE (A.pdno = ? AND B.color LIKE ? ) "; 
        
       pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, pdno);
		pstmt.setString(2, selectedColor);
             
       rs = pstmt.executeQuery();
                
       if(rs.next()) {
    	  Product_DetailVO pdvo = new Product_DetailVO();
    	  pdvo.setPd_detailno(rs.getString(1));
    	  wishProductDetailList.add(pdvo);
       }// end of while(rs.next())----------------------------------
       
    } finally {
       close();
    }   
    
    return wishProductDetailList;		

}

//위시리스트 -> 장바구니 insert 메소드
@Override
public int wishProductInsert(String pdDetailNo, String userid) throws SQLException {
	//이미 존재하는 행일 시 업데이트 / 존재하지 않는 행일시 insert
	
	
	int n = 0; //행이 성공적으로 입력이 되면 1값 반환
	String sql = "";
	try {
		conn = ds.getConnection();
		sql = "SELECT cartno "
				+ "FROM tbl_cart " 
				+ "WHERE fk_userid = ? AND fk_pd_detailno = ?";
	    pstmt = conn.prepareStatement(sql);
	    pstmt.setString(1, userid);
	    pstmt.setString(2, pdDetailNo);
	    rs = pstmt.executeQuery();

	    if(rs.next()) {
	        // 어떤 제품을 추가로 장바구니에 넣고자 하는 경우
	    	sql = "UPDATE tbl_cart "
	    	  		+ "SET cart_qty = cart_qty + 1 " // 위시리스트에 옮기는거라 무조건 한개
	    	  		+ "WHERE fk_pd_detailno = ?";
	    	pstmt = conn.prepareStatement(sql);
	    	pstmt.setString(1, pdDetailNo);
	    	n = pstmt.executeUpdate();
	    } else {	    	
	    	// 장바구니에 존재하지 않는 새로운 제품을 넣고자 하는 경우 
	    	sql = "INSERT INTO tbl_cart (cartno, fk_userid, cart_qty, fk_pd_detailno) "
	    			+ "VALUES (SEQ_TBL_CART_CARTNO.nextval, ?, 1, ?)";
	    	pstmt = conn.prepareStatement(sql);
	    	pstmt.setString(1, userid);
	    	pstmt.setString(2, pdDetailNo);
	    	
	    	n = pstmt.executeUpdate();
	    }
	    
	} finally {
		close();
	}
	return n;
}

//상품상세 -> 장바구니 insert 메소드
@Override
public int DetailProductInsert(String pdDetailNo, String userid, String quantity) throws SQLException {
	//이미 존재하는 행일 시 업데이트 / 존재하지 않는 행일시 insert
	int n = 0; //행이 성공적으로 입력이 되면 1값 반환
	String sql = "";
	try {
		conn = ds.getConnection();
		sql = "SELECT cartno "
				+ "FROM tbl_cart " 
				+ "WHERE fk_userid = ? AND fk_pd_detailno = ?";
	    pstmt = conn.prepareStatement(sql);
	    pstmt.setString(1, userid);
	    pstmt.setString(2, pdDetailNo);
	    rs = pstmt.executeQuery();

	    if(rs.next()) {
	        // 어떤 제품을 추가로 장바구니에 넣고자 하는 경우
	    	sql = "UPDATE tbl_cart "
	    	  		+ "SET cart_qty = cart_qty + ? " // 위시리스트에 옮기는거라 무조건 한개
	    	  		+ "WHERE fk_pd_detailno = ?";
	    	pstmt = conn.prepareStatement(sql);
	    	pstmt.setString(1, quantity);
	    	pstmt.setString(2, pdDetailNo);
	    	n = pstmt.executeUpdate();
	    } else {	    	
	    	// 장바구니에 존재하지 않는 새로운 제품을 넣고자 하는 경우 
	    	sql = "INSERT INTO tbl_cart (cartno, fk_userid, cart_qty, fk_pd_detailno) "
	    			+ "VALUES (SEQ_TBL_CART_CARTNO.nextval, ?, ?, ?)";
	    	pstmt = conn.prepareStatement(sql);
	    	pstmt.setString(1, userid);
	    	pstmt.setString(2, quantity);
	    	pstmt.setString(3, pdDetailNo);
	    	
	    	n = pstmt.executeUpdate();
	    }
	    
	} finally {
		close();
	}
	return n;
	
}


@Override
public List<ProductVO> wishAdd(Map<String, Object> paraMap) throws SQLException {
    List<ProductVO> wishProductList = new ArrayList<>();

    try {
        conn = ds.getConnection();

        String[] pdno_arr = (String[]) paraMap.get("pdnoArray");
        String[] color_arr = (String[]) paraMap.get("colorsArray");

        String sql = "SELECT pdname, pdimg1, saleprice, pdno, color " +
                     "FROM tbl_product A JOIN tbl_pd_detail B ON A.pdno = B.fk_pdno " +
                     "WHERE ";

        // 쿼리 조건 설정
        for (int i = 0; i < pdno_arr.length; i++) {
            if (i > 0) {
                sql += "OR ";
            }
            sql += "(pdno = ? AND color = ?) ";
        }

        pstmt = conn.prepareStatement(sql);

        // PreparedStatement 값 설정
        int index = 1;
        for (int i = 0; i < pdno_arr.length; i++) {
            pstmt.setString(index++, pdno_arr[i]);
            pstmt.setString(index++, color_arr[i]);
        }

        rs = pstmt.executeQuery();

        while (rs.next()) {
            ProductVO pvo = new ProductVO();
            pvo.setPdname(rs.getString("pdname"));
            pvo.setPdimg1(rs.getString("pdimg1"));
            pvo.setSaleprice(rs.getLong("saleprice"));
            pvo.setPdno(rs.getString("pdno"));
            
            Product_DetailVO pdvo = new Product_DetailVO();
            
            pdvo.setColor(rs.getString("color"));
            
            pvo.setPdvo(pdvo);
            wishProductList.add(pvo);
        }

    } finally {
        close();
    }

    return wishProductList;
}


// 제품번호로 리뷰를 가져오는 메소드
@Override
public List<Map<String, String>> getReviewsBypnum(Map<String, String> paraMap) throws SQLException {
    List<Map<String, String>> reviewMapList = new ArrayList<>();

    try {
        conn = ds.getConnection();

        String sql = "SELECT rno, fk_pdno, fk_userid, review_content, starpoint, avg_starpoint, reviewcount, review_date "
                   + "FROM ( "
                   + "  SELECT rownum AS rno, fk_pdno, fk_userid, review_content, starpoint, avg_starpoint, reviewcount, review_date "
                   + "  FROM ( "
                   + "    SELECT A.fk_pdno, A.fk_userid, A.review_content, A.starpoint, B.avg_starpoint, B.reviewcount, "
                   + "           TO_CHAR(TO_DATE(A.review_date, 'yyyy-mm-dd'), 'yy-mm-dd') AS review_date "
                   + "    FROM tbl_review A "
                   + "    JOIN ( "
                   + "      SELECT fk_pdno, TRUNC(AVG(starpoint), 1) AS avg_starpoint, COUNT(review_content) AS reviewcount "
                   + "      FROM tbl_review "
                   + "      GROUP BY fk_pdno "
                   + "    ) B ON A.fk_pdno = B.fk_pdno "
                   + "    WHERE A.fk_pdno = ? "
                   + "    ORDER BY A.review_date DESC "  // 정렬 추가
                   + "  ) "
                   + ") "
                   + "WHERE rno BETWEEN ? AND ?";

        pstmt = conn.prepareStatement(sql);
        
        // fk_pdno 설정
        pstmt.setString(1, paraMap.get("pdno"));
        
        int currentShowPageNo = Integer.parseInt(paraMap.get("currentShowPageNo")); // 현재 페이지위치
        int sizePerPage = Integer.parseInt(paraMap.get("sizePerPage")); //1페이지당 보여지는 회원명수

        // BETWEEN 절의 시작값과 끝값 설정
        pstmt.setInt(2, (currentShowPageNo - 1) * sizePerPage + 1); // 시작 rownum
        pstmt.setInt(3, currentShowPageNo * sizePerPage); // 끝 rownum

        rs = pstmt.executeQuery();

        while (rs.next()) {
            String fk_pdno = rs.getString("fk_pdno");
            String fk_userid = rs.getString("fk_userid");
            String review_content = rs.getString("review_content");
            String starpoint = rs.getString("starpoint");
            String avg_starpoint = rs.getString("avg_starpoint");
            String reviewcount = rs.getString("reviewcount");
            String review_date = rs.getString("review_date");

            // 사용자 ID 마스킹 처리
            String fk_usermask = fk_userid.replaceAll("(?<=.{3}).", "*");

            Map<String, String> map = new HashMap<>();
            map.put("fk_pdno", fk_pdno);
            map.put("fk_usermask", fk_usermask);
            map.put("review_content", review_content);
            map.put("starpoint", starpoint);
            map.put("avg_starpoint", avg_starpoint);
            map.put("reviewcount", reviewcount);
            map.put("review_date", review_date);

            reviewMapList.add(map);
        }

    } finally {
        close();
    }

    return reviewMapList;
}

// 페이지 바 만들기 - 페이징 처리를 위한 리뷰에 대한 총페이지 수를 알아와야 한다.
@Override
public int getTotalPage(Map<String, String> paraMap) throws SQLException {
	int totalPage = 0;
	
	List<MemberVO> memberList = new ArrayList<>();
	try {
		 
		conn = ds.getConnection();
		
		String sql = " select ceil(count(*)/?) "
				+ " from tbl_review  "
				+ " where fk_pdno = ? ";
		
		
		pstmt = conn.prepareStatement(sql);

		pstmt.setInt(1, Integer.parseInt(paraMap.get("sizePerPage")));
		pstmt.setInt(2, Integer.parseInt(paraMap.get("pdno")));

		
		rs = pstmt.executeQuery();
		
		rs.next();
		
		totalPage = rs.getInt(1); //ceil(count(*)/?)
		} finally {
			close();
		}
	
	return totalPage;
}

// 사용자가 구매하는 상품(컬러) 재고가 남아있는지 확인하는 메소드
@Override
public boolean itemDetailCheckPdQty(String str_pdno, String selectedColor, String str_cart_qty) throws SQLException {
	boolean isPdQtyOk = false;

	
    try {
        conn = ds.getConnection();
        
        String sql = "  SELECT pdname, pdimg1, saleprice, pdno, color, pd_qty "
        		+ " FROM tbl_product A JOIN tbl_pd_detail B ON A.pdno = B.fk_pdno "
        		+ " WHERE pdno = ? AND color = ? AND pd_qty > ? ";
       pstmt = conn.prepareStatement(sql);
		
	   pstmt.setString(1, str_pdno);
	   pstmt.setString(2, selectedColor);      
	   pstmt.setString(3, str_cart_qty);      
       
       rs = pstmt.executeQuery();
                
       if(rs.next()) {
    	   isPdQtyOk = true;

       }
    } finally {
       close();
    }   
	return isPdQtyOk;
}
	
	

}
