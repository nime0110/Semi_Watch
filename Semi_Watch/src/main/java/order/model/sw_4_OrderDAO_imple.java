package order.model;

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

    // 로그인한 아이디에 해당하는 쇼핑내역 가져오기
    @Override
    public List<OrderVO> orderUserId(String userid) throws SQLException {
	
    	List<OrderVO> orderList = new ArrayList<>();// 빈상태로 넣어줌
    	
    	try {
    		conn = ds.getConnection();
    		
    		String sql = " select ordercode, total_price, total_orderdate "
    				   + " from tbl_order "
    				   + " where fk_userid = ? ";
    		
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, userid);
    	
    		rs = pstmt.executeQuery();
    		
    		while(rs.next()) {
    			
    			OrderVO odrvo = new OrderVO();
    			odrvo.setOrdercode(rs.getString("ordercode"));
    			odrvo.setTotal_price(rs.getInt("total_price"));
    			odrvo.setTotal_orderdate(rs.getString("total_orderdate"));
    			
    			orderList.add(odrvo);
    			// add 는 List만 해당 // put은 Map에서 해당. 
    			
    		}// END OF while(rs.next()) {}---------------------------------------------
    		
    	} finally {
    		close();
    	}
    		
    	return orderList;
    }
}