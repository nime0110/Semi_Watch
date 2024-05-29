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

public class jh_3_ProductDAO_imple implements jh_3_ProductDAO {

	// DB에 사용되는 객체
	private DataSource ds; // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	// import javax.sql.DataSource;
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	// DB Connection Pool.txt 파일내용을 복붙한 내용
	// 생성자
	public jh_3_ProductDAO_imple() {
		
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

	
	
	// 장바구니에 있는 제품 정보(제품명, 이미지, 가격, 옵션명 등)
	@Override
	public List<ProductVO> select_odrProductInfo(Map<String, String[]> paraMap) throws SQLException {
		List<ProductVO> pvoList = null;
		
		try {
			conn = ds.getConnection();
			
			String[] pdnoArr = paraMap.get("pdnoArr");
			String[] pd_detailnoArr = paraMap.get("pd_detailnoArr");
			
			int cnt = 0;
			for(int i=0; i<pdnoArr.length; i++) {
				
				cnt++;
				
				if(cnt ==1) {
					pvoList = new ArrayList<>();
				}
				
				String sql  = " SELECT PDNAME, SALEPRICE, PDIMG1, COLOR "
							+ " FROM "
							+ " ( "
							+ " 	select pdno, pdname, saleprice, pdimg1 "
							+ " 	from tbl_product "
							+ " 	where pdno = to_number(?) "
							+ " ) P "
							+ " JOIN tbl_pd_detail D "
							+ " ON P.pdno = D.fk_pdno "
							+ " WHERE D.pd_detailno = to_number(?) ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, pdnoArr[i]);
				pstmt.setString(2, pd_detailnoArr[i]);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					
					ProductVO pvo = new ProductVO();
					pvo.setPdname(rs.getString("PDNAME"));
					pvo.setSaleprice(rs.getInt("SALEPRICE"));
					pvo.setPdimg1(rs.getString("PDIMG1"));
					
					
					Product_DetailVO pdvo = new Product_DetailVO();
					pdvo.setColor(rs.getString("COLOR"));
					
					pvo.setPdvo(pdvo);
					
					
					pvoList.add(pvo);
					
				}

			}// end of for------------
					
			
		} finally {
			close();
		}
		
		return pvoList;

	}// end of public List<ProductVO> select_odrProductInfo(Map<String, String[]> paraMap)----


	

}
