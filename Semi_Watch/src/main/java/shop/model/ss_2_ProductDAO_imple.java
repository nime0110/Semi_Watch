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
	
	

}
