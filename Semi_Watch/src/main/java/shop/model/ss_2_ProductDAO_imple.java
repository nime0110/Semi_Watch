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
		
		String sql = " select pdno, pdname, brand, pdimg1, price, saleprice, pd_content "
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


//제품번호를 가지고서 제품의 상세코드 가져오기 
@Override
public List<String> getColorsByPnum(String pdno) throws SQLException {

	List<String> colorList = new ArrayList<>(); //빈 리스트 만들기
	
	try {
		conn = ds.getConnection();
		
		String sql =  " select color "
				+ " from tbl_pd_detail "
				+ " where fk_pdno IN ( " + pdno + " ) ";
		
		pstmt = conn.prepareStatement(sql);
		
		rs = pstmt.executeQuery();
		
		while(rs.next()) {
			//추가이미지가 있을 경우
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
public int productInsert(String pdDetailNo, String userid, String registerday) throws SQLException {
	int result = 0; //행이 성공적으로 입력이 되면 1값 반환
	String sql = "";
	try {
		conn = ds.getConnection();
		sql += " insert into tbl_cart ( cartno , fk_pdno, fk_userid, cart_qty, registerday ) "
				+ "values (SEQ_TBL_CART_CARTNO.nextval , "+pdDetailNo+" , '"+userid+"' , 1, "+registerday+") ";
		pstmt = conn.prepareStatement(sql);
		
		result = pstmt.executeUpdate();
	} finally {
		close();
	}
	return result;
}
	

	
	

}
