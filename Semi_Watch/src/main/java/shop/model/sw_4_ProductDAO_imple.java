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

import shop.domain.CartVO;
import shop.domain.ImageVO;
import shop.domain.ProductVO;

public class sw_4_ProductDAO_imple implements sw_4_ProductDAO {

	// DB에 사용되는 객체
	private DataSource ds; // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	// import javax.sql.DataSource;
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	

	// DB Connection Pool.txt 파일내용을 복붙한 내용
	// 생성자
	public sw_4_ProductDAO_imple() {
		
		try {
		Context initContext = new InitialContext();
	    Context envContext  = (Context)initContext.lookup("java:/comp/env");
	    ds = (DataSource)envContext.lookup("jdbc/semioracle");

		}catch(NamingException e) {
			e.printStackTrace();
		}
		
	}// end of public void ProductDAO_imple() {} --------------------------------------------------
	
	
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
    
	// 장바구니에 담은 제품 정보 보여주기
	@Override
	public List<ProductVO> select_product() throws SQLException {
		
		List<ProductVO> ProductList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " select pdname, brand, price, saleprice, pdimg1 "
					   + " from tbl_product "
					   + " where pdno in(95, 99, 107) ";
			
			pstmt = conn.prepareStatement(sql);
			
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) { // sql 결과물 
				
				ProductVO pvo = new ProductVO();
				// PDNAME, BRAND, PRICE, SALEPRICE, PDIMG1
				
				pvo.setPdname(rs.getString("pdname"));
				pvo.setBrand(rs.getString("brand"));
				pvo.setPrice(rs.getInt("price"));
				pvo.setSaleprice(rs.getInt("saleprice"));
				pvo.setPdimg1(rs.getString("pdimg1"));
			
				ProductList.add(pvo);
				
			} // end of while(rs.next))------------------------------------
			
		} catch(SQLException e) {
			e.printStackTrace();
	
		} finally {
			close();
		}
		
		return ProductList;
	}

	// 장바구니 담기 
	// 장바구니 테이블(tbl_cart)에 해당 제품을 담아야 한다.
	// 장바구니 테이블에 해당 제품이 존재하지 않는 경우에는 tbl_cart 테이블에 insert 를 해야하고, 
	// 장바구니 테이블에 해당 제품이 존재하는 경우에는 또 그 제품을 추가해서 장바구니 담기를 한다라면 tbl_cart 테이블에 update 를 해야한다.
	@Override
	public int addCart(Map<String, String> paraMap) throws SQLException {
		
		int n = 0;
		
		try {
			conn = ds.getConnection();
			
			/*
	           먼저 장바구니 테이블(tbl_cart)에 어떤 회원이 새로운 제품을 넣는 것인지,
	           아니면 또 다시 제품을 추가로 더 구매하는 것인지를 알아야 한다.
	           이것을 알기 위해서 어떤 회원이 어떤 제품을 장바구니 테이블(tbl_cart) 넣을때
	           그 제품이 이미 존재하는지 select 를 통해서 알아와야 한다.
	           
	         -------------------------------------------
	          cartno   fk_userid     fk_pnum   oqty  
	         -------------------------------------------
	            1      seoyh          7        12     
	            2      yuseonwoo      6         3     
	            3      leess          7         5     
	        */
			
			String sql = " select cartno "
					   + " from tbl_cart "
					   + " where fk_userid =? and fk_pdno = ? ";
			
			// 카트 번호 있으면 증가 시켜줘야 함 insert 아니고 update
			
			pstmt = conn.prepareStatement(sql);  // prepareStatement 카트에서 paraMap으로 줫음
	        pstmt.setString(1, paraMap.get("userid"));
	        pstmt.setString(2, paraMap.get("pdno"));
	         
	        rs = pstmt.executeQuery();
	        
	        // 행이 있다면 (이미 장바구니에 있다.) => 로그인되어 있다는게 전제로 깔림. // 이부분 sql 수정해야함
	        if(rs.next()) {
	        	// 어떤 제품을 추가로 장바구니에 넣고자 하는 경우
	        	sql = " update tbl_cart set cart_qty = cart_qty + ? " // paraMap에 담아져 온것에 + 해주는것
	        		+ " where cartno = ? ";	
	        	
	        	pstmt = conn.prepareStatement(sql);
	        	pstmt.setInt(1, Integer.parseInt(paraMap.get("cart_qty")));
	        	pstmt.setInt(2, rs.getInt("cartno")); // 카트 넘버 있는지 물어보는거(넣은거 있는지)
	        	
	        	n = pstmt.executeUpdate();
	        }
	        else {
	        	// 장바구니에 존재하지 않는 새로운 제품을 넣고자 하는 경우 
	        	
	        	sql = " insert into tbl_cart(cartno, fk_userid, fk_pdno, cart_qty) "
	                + " values(seq_tbl_cart_cartno.nextval, 'yuseonwoo', '117', 1) ";
	                 
                 pstmt = conn.prepareStatement(sql);
                 pstmt.setString(1, paraMap.get("userid"));
                 pstmt.setInt(2, Integer.parseInt(paraMap.get("fk_pdno")));
                 pstmt.setInt(3, Integer.parseInt(paraMap.get("cart_qty")));
                 
                 n = pstmt.executeUpdate();
	                 
	        }
			
		} finally {
			close(); // 자원반납
		}
		
		return n;
	} // end of public int addCart(Map<String, String> paraMap) throws SQLException {}------------------------------------------------------------

	// 로그인한 사용자의 이름으로 된 장바구니 조회해오기.
	@Override
	public List<CartVO> selectProductCart(String userid) throws SQLException {
		
		
		List<CartVO> cartList = null; // 장바구니에 아무것도 안담아온 사람도 있기 때문에
			
		try {
			conn = ds.getConnection();
			
			String sql = " select C.cartno, C.fk_userid, C.fk_pdno, C.cart_qty, P.pdname, P.pdimg1, P.saleprice "
					+ " FROM "
					+ " (   select cartno, fk_userid, fk_pdno, cart_qty "
					+ " from tbl_cart "
					+ " where fk_userid = 'yuseonwoo' "
					+ " )C "
					+ " JOIN tbl_product P "
					+ " on C.fk_pdno = P.pdno "
					+ " order by C.cartno desc ";
			
			pstmt = conn.prepareStatement(sql);
	   //   pstmt.setString(1, userid);
	         
	        rs = pstmt.executeQuery();
	        
	        int cnt =0;

	        while(rs.next()) {
	        	
	        	cnt++;
	        	
	        	if(cnt == 1) {
	        		cartList = new ArrayList<>();
	        	}
	        	
	        	String cartno = rs.getString("cartno");
	            String fk_userid = rs.getString("fk_userid");
	            String fk_pdno = rs.getString("fk_pdno");
	            int oqty = rs.getInt("cart_qty"); // 주문량
	            String pdname = rs.getString("pdname");
	            String pdimg1 = rs.getString("pdimg1");
	            int saleprice = rs.getInt("saleprice");
	            
	            ProductVO prodvo = new ProductVO();
	            prodvo.setPdno(fk_pdno);
	            prodvo.setPdname(pdname);
	            prodvo.setPdimg1(pdimg1);
	            prodvo.setSaleprice(saleprice);
	            
	  
	            // ***** !!!! 중요함 !!!! ***** //
	            prodvo.setTotalPriceTotalPoint(oqty); // 읽어와야함.$(request.map(키명); jsp 파일에 보여줄때,
	            // 윗부분은 jsp 에서 단가*수량이 되는 부분임
	            // ***** !!!! 중요함 !!!! ***** //

	            CartVO cvo = new CartVO();
	            
	            cvo.setCartno(cartno);
	            cvo.setFk_userid(fk_userid);
	            cvo.setFk_pdno(fk_pdno);
	            cvo.setProd(prodvo);
	            
	            cartList.add(cvo);
	            
	        } // end of while(rs.next()) {}-------------------------------------------------------------------
			
		} finally {
			close();
		}
		
		
		return cartList;
	
	}// end of public List<shop.domain.CartVO> selectProductCart(String userid) throws SQLException {}----------------------------


	@Override
	public Map<String, String> selectCartSumPricePoint(String userid) {
		// TODO Auto-generated method stub
		return null;
	}



	
	
	
}
