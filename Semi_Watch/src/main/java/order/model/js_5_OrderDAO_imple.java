package order.model;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import shop.domain.ImageVO;
import util.security.AES256;
import util.security.SecrectMyKey;

public class js_5_OrderDAO_imple implements js_5_OrderDAO {

	// DB에 사용되는 객체
	private DataSource ds; // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	// import javax.sql.DataSource;
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private AES256 aes;
	
	// DB Connection Pool.txt 파일내용을 복붙한 내용
	// 생성자
	public js_5_OrderDAO_imple() {
		
		try {
		Context initContext = new InitialContext();
	    Context envContext  = (Context)initContext.lookup("java:/comp/env");
	    ds = (DataSource)envContext.lookup("jdbc/semioracle");
	    aes = new AES256(SecrectMyKey.KEY);

		}catch(NamingException e) {
			
		}catch(UnsupportedEncodingException e) {
			
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
	public List<Map<String, String>> getOrderList(Map<String, String> paraMap)throws SQLException {
		
		List<Map<String,String>> order_map_List = new ArrayList<>();
		
		try {
   		conn = ds.getConnection();
   		
   		String sql = " with"
   				+ " od1 as "
   				+ " ( "
   				+ " select fk_ordercode, count(fk_ordercode) as cnt "
   				+ " from tbl_orderdetail "
   				+ " group by fk_ordercode "
   				+ " ), "
   				+ " od2 as "
   				+ " ( "
   				+ " select fk_ordercode, fk_pd_detailno, delivery_status "
   				+ " from tbl_orderdetail "
   				+ " ), "
   				+ " od3 as "
   				+ " ( "
   				+ " select  od1.fk_ordercode, od1.cnt, o.fk_userid, "
   				+ "        o.total_price, o.total_orderdate, pdname, "
   				+ "        od2.delivery_status, pdimg1 , "
   				+ "        row_number() over (partition by od1.fk_ordercode order by o.total_orderdate desc) as rn "
   				+ " from od1 join od2 "
   				+ " on od1.fk_ordercode = od2.fk_ordercode "
   				+ " join tbl_order o "
   				+ " on od2.fk_ordercode = o.ordercode "
   				+ " join tbl_pd_detail d "
   				+ " on od2.fk_pd_detailno = d.pd_detailno "
   				+ " join tbl_product p "
   				+ " on d.fk_pdno = p.pdno "
   				+ " order by total_orderdate desc"
   				+ " ),"
   				+ " od4 as"
   				+ " ( "
   				+ " select rownum as rno , fk_ordercode, cnt, pdimg1, total_price, "
   				+ " total_orderdate, pdname, fk_userid , "
   				+ " case delivery_status when 1 then '주문완료' "
   				+ " when 2 then '배송중' "
   				+ " when 3 then '배송완료' end as delivery_status "
   				+ " from od3 "
   				+ " where rn = 1 and fk_userid = ? "
   				+ " ) "
   				+ " select rno , fk_ordercode, cnt, fk_userid, total_price, "
   				+ " total_orderdate, pdname, delivery_status , pdimg1 "
   				+ " from od4 "
   				+ " where od4.rno between ? and ? ";
   				
   	
   		
   		pstmt = conn.prepareStatement(sql);
   		
   		pstmt.setString(1, paraMap.get("userid"));
   		
   		
   		int currentShowPageNo = Integer.parseInt(paraMap.get("currentShowPageNo") );
		// 조회할 페이지 번호
		
        int sizePerPage = Integer.parseInt(paraMap.get("sizePerPage") );
		// 한페이지당 보여줄 행 갯수
		
        pstmt.setInt(2, (currentShowPageNo * sizePerPage) - (sizePerPage - 1) ); // 공식
		pstmt.setInt(3, (currentShowPageNo * sizePerPage) ); 
   		
   		rs = pstmt.executeQuery();
   		
			while(rs.next()) {
				
				String ordercode = rs.getString("fk_ordercode");
				String total_price = rs.getString("total_price");
				String total_orderdate = rs.getString("total_orderdate");
				String pdimg1 = rs.getString("pdimg1");
				String pdname = rs.getString("pdname");
				String delivery_status = rs.getString("delivery_status");
				String cnt = rs.getString("cnt");
	
				
				Map<String,String> odrmap = new HashMap<>();
				odrmap.put("ordercode", ordercode);
				odrmap.put("cnt", cnt);
				odrmap.put("total_price", total_price);
				odrmap.put("total_orderdate", total_orderdate);
				odrmap.put("pdimg1", pdimg1);
				odrmap.put("delivery_status", delivery_status);
				odrmap.put("pdname", pdname);
				
				order_map_List.add(odrmap);				
			
			} // end of while(rs.next()) {}----------------
		}finally {
			close();
		}	
		return order_map_List;
	}
	

	// 관리자가 전 회원 주문내역 보기 
	@Override
	public List<Map<String, String>> getOdrListAdmin(Map<String, String> paraMap) throws SQLException {
		
		List<Map<String,String>> order_list_admin = new ArrayList<>();
		
		try {
   		conn = ds.getConnection();
   		
   		String sql = " with"
   				+ " od1 as "
   				+ " ( "
   				+ " select fk_ordercode, count(fk_ordercode) as cnt "
   				+ " from tbl_orderdetail "
   				+ " group by fk_ordercode "
   				+ " ), "
   				+ " od2 as "
   				+ " ( "
   				+ " select fk_ordercode, fk_pd_detailno, delivery_status "
   				+ " from tbl_orderdetail "
   				+ " ), "
   				+ " od3 as "
   				+ " ( "
   				+ " select  od1.fk_ordercode, od1.cnt, o.fk_userid, "
   				+ "        o.total_price, o.total_orderdate, pdname, "
   				+ "        od2.delivery_status, "
   				+ "        row_number() over (partition by od1.fk_ordercode order by o.total_orderdate desc) as rn "
   				+ " from od1 join od2 "
   				+ " on od1.fk_ordercode = od2.fk_ordercode "
   				+ " join tbl_order o "
   				+ " on od2.fk_ordercode = o.ordercode "
   				+ " join tbl_pd_detail d "
   				+ " on od2.fk_pd_detailno = d.pd_detailno "
   				+ " join tbl_product p "
   				+ " on d.fk_pdno = p.pdno "
   				+ " where total_orderdate between ? and ? "
   				+ " order by total_orderdate desc"
   				+ " ),"
   				+ " od4 as"
   				+ " ( "
   				+ " select rownum as rno , fk_ordercode, cnt, fk_userid, total_price, "
   				+ " total_orderdate, pdname, "
   				+ " case delivery_status when 1 then '주문완료' "
   				+ " when 2 then '배송중' "
   				+ " when 3 then '배송완료' end as delivery_status "
   				+ " from od3 "
   				+ " where rn = 1 and fk_userid != 'admin' "
   				+ " ) "
   				+ " select rno , fk_ordercode, cnt, fk_userid, total_price, "
   				+ " total_orderdate, pdname, delivery_status "
   				+ " from od4 "
   				+ " where od4.rno between ? and ? ";
   				
   					
			
			pstmt = conn.prepareStatement(sql);
			
			int currentShowPageNo = Integer.parseInt(paraMap.get("currentShowPageNo") );
			// 조회할 페이지 번호
			
	        int sizePerPage = Integer.parseInt(paraMap.get("sizePerPage") );
			// 한페이지당 보여줄 행 갯수
			
	        
	        String start = paraMap.get("startDate");
			String end = paraMap.get("endDate")+ " 23:59:59";
			
			Timestamp startDate = Timestamp.valueOf(start + " 00:00:00");
		    Timestamp endDate = Timestamp.valueOf(end);
			
			pstmt.setTimestamp(1, startDate);
			pstmt.setTimestamp(2, endDate);
	        
	        pstmt.setInt(3, (currentShowPageNo * sizePerPage) - (sizePerPage - 1) ); // 공식
			pstmt.setInt(4, (currentShowPageNo * sizePerPage) ); 
		
			rs = pstmt.executeQuery();
			
				while(rs.next()) {
					
					Map<String, String> odradminList = new HashMap<>();
					
					odradminList.put("ordercode", rs.getString("fk_ordercode"));
					odradminList.put("fk_userid", rs.getString("fk_userid"));
					odradminList.put("total_price", rs.getString("total_price"));
					odradminList.put("total_orderdate", rs.getString("total_orderdate"));
					odradminList.put("pdname", rs.getString("pdname"));
					odradminList.put("delivery_status", rs.getString("delivery_status"));
					odradminList.put("cnt", rs.getString("cnt"));
					
					order_list_admin.add(odradminList);
					
				} // end of while()--------------------------------------------------
				
			}finally {
				
				close();
			}
		
		return order_list_admin;
		
	} // end of public List<Map<String, String>> getOdrListAdmin(String userid) {----------------------------


	// 배송상태 업데이트
	@Override
	public int deliveryUpdate(Map<String, String> paraMap) throws SQLException {
		
		int result = 0;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " update tbl_orderdetail set "
					+ " delivery_status = ? "
					+ " where fk_ordercode = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, Integer.parseInt(paraMap.get("deliveryType")));
			pstmt.setString(2, paraMap.get("ordercode"));
			
			
			result = pstmt.executeUpdate();
			
			if(result >= 1) {
				
				result = 1;
			}
			
			
		}finally {
			
			close();
		}
		
		return result;
		
	} // end of public int deliveryUpdate(Map<String, String> paraMap) throws SQLException {


	// 개인유저의 주문내역 토탈페이지 구하기
	@Override
	public int userGetTotalPage(Map<String, String> paraMap) throws SQLException {
		
		int getTotalPage = 0;
		
		try {
			conn = ds.getConnection();
         
			String sql = " select ceil(count(distinct ordercode)/?) as pagecnt "
					   + " from tbl_order O join tbl_orderdetail D "
					   + " on O.ordercode = D.fk_ordercode "
					   + " where D.fk_userid = ? ";
			
			pstmt = conn.prepareStatement(sql); 
			
			pstmt.setInt(1, Integer.parseInt(paraMap.get("sizePerPage") ) );
			pstmt.setString(2, paraMap.get("userid"));
			
			rs = pstmt.executeQuery();
         	
			rs.next();
			
			getTotalPage = rs.getInt("pagecnt");
         
		} finally {
			close();
		}
		
		return getTotalPage;
		
	}// end of public int userGetTotalPage(Map<String, String> paraMap) throws SQLException {

	// 관리자의 전체주문내역 토탈페이지 구하기
	@Override
	public int adminGetTotalPage(Map<String, String> paraMap) throws SQLException {
		
		int getTotalPage = 0;
		
		try {
			conn = ds.getConnection();
         
			String sql = " select ceil(count(distinct ordercode)/?) as pagecnt "
					   + " from tbl_order O join tbl_orderdetail D "
					   + " on O.ordercode = D.fk_ordercode "
					   + " where D.fk_userid != 'admin' "
					   + " and total_orderdate between ? and ? ";
			
			pstmt = conn.prepareStatement(sql); 
			
			pstmt.setInt(1, Integer.parseInt(paraMap.get("sizePerPage") ) );
			
			String start = paraMap.get("startDate");
			String end = paraMap.get("endDate")+ " 23:59:59";
			
			Timestamp startDate = Timestamp.valueOf(start + " 00:00:00");
		    Timestamp endDate = Timestamp.valueOf(end);
			
			pstmt.setTimestamp(2, startDate);
			pstmt.setTimestamp(3, endDate);
			
			rs = pstmt.executeQuery();
         	
			rs.next();
			
			getTotalPage = rs.getInt("pagecnt");
         
		} finally {
			close();
		}
		
		return getTotalPage;
		
	} // end of public int adminGetTotalPage(Map<String, String> paraMap) throws SQLException {


	// 개인유저의 구매 확정짓기
	@Override
	public int deliveryComplete(String ordercode) throws SQLException {
		
		int result = 0;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " update tbl_orderdetail set "
					+ " delivery_status = 3 "
					+ " where fk_ordercode = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, ordercode);
			
			result = pstmt.executeUpdate();
			
			if(result >= 1) {
				
				result = 1;
			}
			
			
		}finally {
			
			close();
		}
		
		return result;
		
	} // end of public int deliveryComplete(String ordercode) throws SQLException {


	// 관리자가 조회하는 주문내역 상세
	@Override
	public List<Map<String, String>> adminGetOrderDetail(String odrcode) throws SQLException {
		
		List<Map<String,String>> admin_orderdetail = new ArrayList<>();
		
		try {
	   		conn = ds.getConnection();
	   		
	   		String sql = " select order_qty , pdname , pdno,  brand, decode(color, 'none', '단일색상', color) as color , "
	   				+ " saleprice, pd_qty , pd_detailno, point, pdimg1 "
	   				+ " from tbl_orderdetail od "
	   				+ " join tbl_pd_detail d on "
	   				+ " od.fk_pd_detailno = d.pd_detailno "
	   				+ " join tbl_product p "
	   				+ " on d.fk_pdno = p.pdno "
	   				+ " where fk_ordercode = ? ";
	   		
	   		
	   		pstmt = conn.prepareStatement(sql);
	   		
	   		pstmt.setString(1, odrcode);
	   		
	   		rs = pstmt.executeQuery();
	   		
	   		while(rs.next()) {
	   			
	   			Map<String,String> odrmap = new HashMap<>();
	   			
	   			odrmap.put("brand", rs.getString("brand"));
	   			odrmap.put("pdname", rs.getString("pdname"));
	   			odrmap.put("pdno", rs.getString("pdno"));
	   			odrmap.put("color", rs.getString("color"));
	   			odrmap.put("pdimg1", rs.getString("pdimg1"));
	   			odrmap.put("order_qty", rs.getString("order_qty"));
	   			odrmap.put("pd_detailno", rs.getString("pd_detailno"));
	   			odrmap.put("pd_qty", rs.getString("pd_qty"));
	   			odrmap.put("saleprice", rs.getString("saleprice"));
	   			odrmap.put("point", rs.getString("point"));
	   			
	   			admin_orderdetail.add(odrmap);
	   			
	   		} // end of while
	   		
		}finally {
			
			close();
			
		}
	
		return admin_orderdetail;
		
	} // end of public List<Map<String, String>> adminGetOrderDetail(String odrcode) throws SQLException { 


	
	// 관리자가 조회하는 상세주문내역 구매자정보
	@Override
	public Map<String, String> adminGetOrderInfo(String odrcode) throws SQLException {
		
		Map<String,String> odrmap = null;
		
		try {
	   		conn = ds.getConnection();
	   		
	   		String sql = " with "
	   				+ " V "
	   				+ " as "
	   				+ " ( "
	   				+ " select fk_userid, ordercode, total_price, total_orderdate "
	   				+ " from tbl_order "
	   				+ " where ordercode = ? "
	   				+ " ) "
	   				+ " , "
	   				+ " M "
	   				+ " as "
	   				+ " ("
	   				+ " select userid, username, mobile, email "
	   				+ " from tbl_member "
	   				+ " ) "
	   				+ " select distinct M.username, M.userid, V.ordercode, V.total_price as total_price , V.total_orderdate, "
	   				+ "       M.mobile as mobile, M.email as email, D.delivery_name , D.delivery_mobile, D.delivery_postcode , "
	   				+ "       D.delivery_address, D.delivery_msg , "
	   				+ "		  case delivery_status when 1 then '주문완료' "
	   				+ "       when 2 then '배송중' "
	   				+ "       when 3 then '배송완료' end as delivery_status "
	   				+ " from M join V"
	   				+ " on M.userid = V.fk_userid"
	   				+ " join tbl_delivery D"
	   				+ " on V.ordercode = D.ordercode "
	   				+ " join tbl_orderdetail OD "
	   				+ " on V.ordercode = OD.fk_ordercode ";
	   		
	   		
	   		pstmt = conn.prepareStatement(sql);
	   		
	   		pstmt.setString(1, odrcode);
	   		
	   		rs = pstmt.executeQuery();
	   		
	   		if(rs.next()) {
	   			
	   			odrmap = new HashMap<>();
	   			
	   			odrmap.put("username", rs.getString("username"));
	   			odrmap.put("userid", rs.getString("userid"));
	   			
	   			String email = rs.getString("email");
	            if (email != null) {
	                odrmap.put("email", aes.decrypt(email));
	            }

	            String mobile = rs.getString("mobile");
	            if (mobile != null) {
	                odrmap.put("mobile", aes.decrypt(mobile));
	            }
	   			
	   			odrmap.put("ordercode", rs.getString("ordercode"));
	   			
	   			odrmap.put("total_price", rs.getString("total_price"));
	   			
	   			odrmap.put("total_orderdate", rs.getString("total_orderdate"));
	   			odrmap.put("delivery_status", rs.getString("delivery_status"));
	   		
	   			odrmap.put("delivery_name", rs.getString("delivery_name"));
	   			odrmap.put("delivery_mobile", rs.getString("delivery_mobile"));
	   			odrmap.put("delivery_postcode", rs.getString("delivery_postcode"));
	   			odrmap.put("delivery_address", rs.getString("delivery_address"));
	   			odrmap.put("delivery_msg", rs.getString("delivery_msg"));

	   		
	   		} // end of while
	   		
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			
			e.printStackTrace();
		}finally {
			
			close();
			
		}
	
		return odrmap;
		
	} // end of public List<Map<String, String>> adminGetOrderInfo(String odrcode) throws SQLException {

	

}
