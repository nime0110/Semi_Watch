
package order.model;

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

import order.domain.OrderVO;
import shop.domain.ImageVO;

public class sw_4_OrderDAO_imple implements sw_4_OrderDAO {

	// DB에 사용되는 객체
	private DataSource ds; // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	// import javax.sql.DataSource;
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	// DB Connection Pool.txt 파일내용을 복붙한 내용
	// 생성자
	public sw_4_OrderDAO_imple() {
		
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

	// *** 관리자가 아닌 일반사용자로 로그인 했을 경우에는 자신이 주문한 내역만 페이징 처리하여 조회를 해오고, 
	//     관리자로 로그인을 했을 경우에는 모든 사용자들의 주문내역을 페이징 처리하여 조회해온다.
	@Override
	public List<Map<String, String>> getOrderList(String userid)throws SQLException {
		
		List<Map<String,String>> order_map_List = new ArrayList<>();
		
		try {
    		conn = ds.getConnection();
    		
    		String sql = " select ordercode, total_price, total_orderdate, fk_pdno, color, pdname "
    				   + " from "
    				   + " ( "
    				   + "    select ordercode, total_price, total_orderdate, fk_pdno, color "
    				   + "    from "
    				   + "    ( "
    				   + "    select ordercode, total_price, total_orderdate, fk_pd_detailno "
    				   + "    from "
    				   + "    ( "
    				   + "        select ordercode, total_price, total_orderdate "
    				   + "        from tbl_order ";
    		
    		if(!"admin".equals(userid)) {
    			// 관리자가 아닌 일반사용자로 로그인한 경우
    			sql += " where fk_userid = ? "; 
    		}
    		
    		sql		   += "    ) A "
    				   + "    join tbl_orderdetail B "
    				   + "    on A.ordercode = B.fk_ordercode "
    				   + " ) C "
    				   + " join tbl_pd_detail D "
    				   + " on C.fk_pd_detailno = D.pd_detailno "
    				   + " ) E "
    				   + " join tbl_product F "
    				   + " on E.fk_pdno = F.pdno ";
    	
    		
    		pstmt = conn.prepareStatement(sql);
    		
    		if(!"admin".equals(userid)) {
    			// 관리자가 아닌 일반사용자로 로그인한 경우
    			pstmt.setString(1, userid);
    		}
    		rs = pstmt.executeQuery();
    		
			while(rs.next()) {
				
				String odrcode = rs.getString("ordercode");
				// String fk_userid = rs.getString("fk_userid");
				String total_price = rs.getString("total_price");
				String total_orderdate = rs.getString("total_orderdate");
				String fk_pdno = rs.getString("fk_pdno");
				String color = rs.getString("color");
				String pdname = rs.getString("pdname");
				
				Map<String,String> odrmap = new HashMap<>();
				odrmap.put("odrcode", odrcode);
				// odrmap.put("fk_userid", fk_userid);
				odrmap.put("total_price", total_price);
				odrmap.put("total_orderdate", total_orderdate);
				odrmap.put("fk_pdno", fk_pdno);
				odrmap.put("color", color);
				odrmap.put("pdname", pdname);
				
				order_map_List.add(odrmap);				
			
			} // end of while(rs.next()) {}----------------
		}finally {
			close();
		}	
	
		return order_map_List;
		
	}

}
