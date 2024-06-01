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

import shop.domain.ImageVO;

public class jh_3_OrderDAO_imple implements jh_3_OrderDAO {

	// DB에 사용되는 객체
	private DataSource ds; // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	// import javax.sql.DataSource;
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	// DB Connection Pool.txt 파일내용을 복붙한 내용
	// 생성자
	public jh_3_OrderDAO_imple() {
		
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

   
   
   
   // 주문상세정보 가져오기
   @Override
   public List<Map<String, String>> getordDetailInfo(String ordcode) throws SQLException {
	   
	   List<Map<String, String>> ordDetail_List = new ArrayList<>();
	   
	   try {
		   
		   conn = ds.getConnection();
		   
		   String sql = " WITH "
			   		  + " O AS( "
			   		  + " 	select ordercode, fk_userid "
			   		  + " 	from tbl_order "
			   		  + " ) "
			   		  + " , "
			   		  + " OD AS( "
			   		  + " 	select order_detailno, fk_pd_detailno, fk_ordercode, order_qty, delivery_status "
			   		  + " 	from tbl_orderdetail "
			   		  + " ) "
			   		  + " SELECT BRAND, PDIMG1, PDNAME, COLOR, SALEPRICE, ORDER_QTY, DELIVERY_STATUS "
			   		  + " 		 , PDNO, PD_DETAILNO "
			   		  + " FROM O JOIN OD "
			   		  + " ON O.ordercode = OD.fk_ordercode "
			   		  + " JOIN tbl_pd_detail PD "
			   		  + " ON OD.fk_pd_detailno = PD.pd_detailno "
			   		  + " JOIN tbl_product P "
			   		  + " ON PD.fk_pdno = P.pdno "
			   		  + " WHERE O.ordercode = ? ";
		   
		   pstmt = conn.prepareStatement(sql);
		   pstmt.setString(1, ordcode);
		   
		   rs = pstmt.executeQuery();
		   
		   while(rs.next()) {

			   String brand = rs.getString("BRAND");
			   String pdimg1 = rs.getString("PDIMG1");
			   String pdname = rs.getString("PDNAME");
			   String color = rs.getString("COLOR");
			   String saleprice = rs.getString("SALEPRICE");
			   String order_qty = rs.getString("ORDER_QTY");

			   String delivery_status = rs.getString("DELIVERY_STATUS");

			   String pdno = rs.getString("PDNO");
			   String pd_detailno = rs.getString("PD_DETAILNO");
			   
			   Map<String, String> ordDetail = new HashMap<>();
			   
			   ordDetail.put("brand", brand);
			   ordDetail.put("pdimg1", pdimg1);
			   ordDetail.put("pdname", pdname);
			   ordDetail.put("color", color);
			   ordDetail.put("saleprice", saleprice);
			   ordDetail.put("order_qty", order_qty);

			   ordDetail.put("delivery_status", delivery_status);

			   ordDetail.put("pdno", pdno);
			   ordDetail.put("pd_detailno", pd_detailno);
			   
			   
			   ordDetail_List.add(ordDetail);
			   
			   
		   }// end of while(rs.next()) ----
		    
		   
	   } finally {
		   close();
	   }
	   
	   return ordDetail_List;
	   
   }// end of public List<Map<String, String>> getordDetailInfo(String ordcode)---


   @Override
   public Map<String, String> getordInfo(String ordcode) throws SQLException {
	   Map<String, String> ordInfo = new HashMap<>();
	   
try {
		   
		   conn = ds.getConnection();
		   
		   String sql = " WITH "
			   		  + " O AS( "
			   		  + " 	select ordercode, fk_userid, total_price, total_orderdate "
			   		  + " 	from tbl_order "
			   		  + " ) "
			   		  + " , "
			   		  + " D AS( "
			   		  + " 	select ordercode, delivery_name, delivery_mobile, delivery_postcode, delivery_address, delivery_msg "
			   		  + " 	from tbl_delivery "
			   		  + " ) "
			   		  + " SELECT TOTAL_PRICE, TOTAL_ORDERDATE, "
			   		  + "		 DELIVERY_NAME, DELIVERY_MOBILE, DELIVERY_POSTCODE, DELIVERY_ADDRESS, DELIVERY_MSG "
			   		  + " FROM O JOIN D "
			   		  + " ON O.ordercode = D.ordercode "
			   		  + " WHERE O.ordercode = ? ";
		   
		   pstmt = conn.prepareStatement(sql);
		   pstmt.setString(1, ordcode);
		   
		   rs = pstmt.executeQuery();
		   
		   if(rs.next()) {
			   
			   String total_price = rs.getString("TOTAL_PRICE");
			   String total_orderdate = rs.getString("TOTAL_ORDERDATE");
			   
			   String name = rs.getString("DELIVERY_NAME");
			   String postcode = rs.getString("DELIVERY_POSTCODE");
			   String address = rs.getString("DELIVERY_ADDRESS");
			   String mobile = rs.getString("DELIVERY_MOBILE");
			   String msg = rs.getString("DELIVERY_MSG");
   
			   ordInfo.put("total_price", total_price);
			   ordInfo.put("total_orderdate", total_orderdate);
			   ordInfo.put("name", name);
			   ordInfo.put("postcode", postcode);
			   ordInfo.put("address", address);
			   ordInfo.put("mobile", mobile);
			   ordInfo.put("msg", msg);
			   
			   
		   }
		    
		   
	   } finally {
		   close();
	   }

	   
	   return ordInfo;
	   
   }// end of public Map<String, String> getordInfo(String ordcode)
   
   
	
	
	

}
