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

	
	// 주문코드 시퀀스 값 가져오기
	@Override
	public int get_seq_tbl_order_ordercode() throws SQLException {
		int seq = 0;
        
		try {
			conn = ds.getConnection();
         
			String sql  = " select seq_tbl_order_ordercode.nextval AS seq "
						+ " from dual";
     
			pstmt = conn.prepareStatement(sql);
     
			rs = pstmt.executeQuery();
     
			rs.next();
     
			seq = rs.getInt("seq");
         
		} finally {
			close();
		}
	         
		return seq;
	}

	
	
	// 주문하기 정보 처리(주문테이블 추가, 주문배송지테이블 추가, 주문상세 추가, 장바구니 삭제, 상품상세 변경, 유저 변경)
	@Override
	public int checkOutUpdate(Map<String, Object> paraMap) throws SQLException {
		
		int isSuccess = 0;
		int n1=0, n2=0, n3=0, n4=0, n5=0, n6=0; // 성공유무 확인용
		
		// 제품번호
		String[] pnumArr = (String[])paraMap.get("pnumArr");
		
		// 제품상세번호
		String[] pdetailArr = (String[])paraMap.get("pdetailArr");
		
		// 구매수량
		String[] oqtyArr = (String[])paraMap.get("oqtyArr");
		
		// 제품별 구매총금액
		String[] ptotalPriceArr = (String[])paraMap.get("ptotalPriceArr");
		
		// 제품옵션
		String[] poptionArr = (String[])paraMap.get("poptionArr");
			
		try {
			
			conn = ds.getConnection();
			
			conn.setAutoCommit(false);	// 수동커밋
			
			// 주문테이블 인서트
			String sql  = " insert into tbl_order(ORDERCODE, FK_USERID, TOTAL_PRICE, TOTAL_ORDERDATE) "
						+ " values(?, ? , to_number(?), default) ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, (String)paraMap.get("ordcode"));
			pstmt.setString(2, (String)paraMap.get("userid"));
			pstmt.setString(3, (String)paraMap.get("paymentTotalPrice"));
			
			n1 = pstmt.executeUpdate();
			System.out.println("~~~~~ 확인용 n1 : " + n1);
			
			// 배송지테이블 인서트
			if(n1==1) {
				sql = " insert into tbl_delivery( ORDERCODE, DELIVERY_NAME, DELIVERY_POSTCODE, DELIVERY_ADDRESS, DELIVERY_MOBILE, DELIVERY_MSG )"
					+ " values( ?, ?, ?, ?, ?, ? ) ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, (String)paraMap.get("ordcode"));
				pstmt.setString(2, (String)paraMap.get("name"));
				pstmt.setString(3, (String)paraMap.get("postcode"));
				pstmt.setString(4, (String)paraMap.get("address"));
				pstmt.setString(5, (String)paraMap.get("mobile"));
				pstmt.setString(6, (String)paraMap.get("deliverymsg"));
				
				n2 = pstmt.executeUpdate();
				System.out.println("~~~~~ 확인용 n2 : " + n2);

			}// end of 배송지
			
			// 주문상세테이블 인서트 (단일 또는 복수행)
			if(n2==1) {

				int cnt = 0;
				for(int i=0; i<pdetailArr.length; i++) {
					cnt++;
					
					sql = " insert into tbl_orderdetail( ORDER_DETAILNO, FK_PD_DETAILNO, FK_USERID, FK_ORDERCODE, ORDER_QTY, ORDER_PRICE, DELIVERY_STATUS, DELIVERY_DATE ) "
						+ " values( seq_tbl_order_ordercode.nextval, ?, ?, ?, to_number(?), to_number(?), default, default ) ";
						
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, pdetailArr[i]);
					pstmt.setString(2, (String)paraMap.get("userid"));
					pstmt.setString(3, (String)paraMap.get("ordcode"));
					pstmt.setString(4, oqtyArr[i]);
					pstmt.setString(5, ptotalPriceArr[i]);
					
					pstmt.executeUpdate();
				}
				
				if(cnt == pdetailArr.length) {	// 배열만큼 돌았는지 체크
					n3 = 1;
				}
				
				System.out.println("~~~~~ 확인용 n3 : " + n3);
				
			}// end of 주문상세
			
			
			// 상품상세 재고 업데이트
			if(n3==1) {

				
				int cnt = 0;
				for(int i=0; i<pdetailArr.length; i++) {
					cnt++;
					
					sql = " update tbl_pd_detail set PD_QTY = PD_QTY - to_number(?)"
						+ " where pd_detailno = ? and color = ? ";
						
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, oqtyArr[i]);
					pstmt.setString(2, pdetailArr[i]);
					pstmt.setString(3, poptionArr[i]);
					
					pstmt.executeUpdate();
				}
				
				if(cnt == pdetailArr.length) {	// 배열만큼 돌았는지 체크
					n4 = 1;
				}
				
				System.out.println("~~~~~ 확인용 n4 : " + n4);
				
			}// end of 상품상세 재고
			
			
			// 장바구니에서 구매할 경우 장바구니 삭제
			if(n4==1 && paraMap.get("cartnoArr") != null) {	
				
				// 장바구니 번호 
				String[] cartnoArr = (String[])paraMap.get("cartnoArr");
				// cartno_arr 은 ["1","2","3"]
				String cartno_join = String.join("','", cartnoArr);	// "1','2','3" 이렇게 나옴
				
				cartno_join = "'"+cartno_join+"'";	// '1','2','3' 이렇게 나옴
				// 이렇게 하는 이유는 숫자면 상관이 없지만, abc 와 같이 문자열로 있을 경우가 있기 때문이다.
				
				System.out.println("~~~~ 확인용 cartno_join => " + cartno_join);
	            // ~~~~ 확인용 cartno_join => '1','2','3'

				// !!! 중요 in 절은 위와 같이 위치홀더 ? 를 사용하면 안됨. !!! //
				sql = " delete from tbl_cart "
                	+ " where CARTNO in ("+cartno_join+") ";
				
				// !!! 중요 in 절은 위와 같이 직접 변수로 처리해야 함. !!!
				// String.join(",", cartno_arr) 은 "11,8,7" 이러한 것이다.
	            // 조심할 것은 in 에 사용되어지는 cartno 컬럼의 타입이 number 타입이라면 괜찮은데
	            // 만약에 cartno 컬럼의 타입이 varchar2 타입이라면 "11,8,7" 와 같이 되어지면 오류가 발생한다. 
	            // 그래서 cartno 컬럼의 타입이 varchar2 타입이라면 "11,8,7" 을 "'11','8','7'" 와 같이 변경해주어야 한다. 
				
				pstmt = conn.prepareStatement(sql); 
				n5 = pstmt.executeUpdate();
				
				if(n5 == cartnoArr.length) {	// 삭제된 행의 개수와 같으면
					n5 = 1;
				}
				
			}// end of 장바구니 삭제
			

			// 제품상세페이지에서 구매할 경우
			if(n4==1 && paraMap.get("cartnoArr") == null) {	
				n5 = 1;
			}
			
			System.out.println("~~~~~ 확인용 n5 : " + n5);
			
			
			// 유저 포인트 업데이트
			if(n5 == 1) {
				
				sql = " update tbl_member set MILEAGE = to_number(?) "
					+ " where userid = ? ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, (String)paraMap.get("updatePoint"));
				pstmt.setString(2, (String)paraMap.get("userid"));
				
				n6 = pstmt.executeUpdate();
				
			}// end of 유저포인트
			
			System.out.println("~~~~~ 확인용 n6 : " + n6);
			
			
			if(n1*n2*n3*n4*n5*n6==1) {
				conn.commit();	// 커밋
				
				conn.setAutoCommit(true); 	// 다시 자동커밋 전환
				System.out.println("~~~~~ 확인용 n1*n2*n3*n4*n5*n6 : " + n1*n2*n3*n4*n5*n6);
	            //  ~~~~~ 확인용 n1*n2*n3*n4*n5 : 1
				
				isSuccess = 1;
				
			}// end of sql 모두 성공
			

		}catch(SQLException e) {	// sql 하나라도 실패할 경우
			conn.rollback();
			
			conn.setAutoCommit(true); 	// 다시 자동커밋 전환
			isSuccess = 0;	// 확인 사살용
			
			e.printStackTrace();	
		} finally {
			close();
		}
		
		return isSuccess;
		
	}// end of public int checkOutUpdate(Map<String, Object> paraMap)

	
	
	// 주문한 제품에 대해 email 보내기시 email 내용에 넣을 주문한 제품번호들에 대한 제품정보를 얻어오는 것.
	@Override
	public List<ProductVO> getordProductList(String pnums) throws SQLException {
		
		List<ProductVO> productInfoList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
	     
			String sql = " select PDNAME, BRAND, SALEPRICE, PDIMG1 "
		               + " from tbl_product "
		               + " where pdno in("+ pnums +") ";
	     
			pstmt = conn.prepareStatement(sql);
	     
			rs = pstmt.executeQuery();
	     
			while(rs.next()) {
	        
				ProductVO pvo = new ProductVO();
	        
		        pvo.setPdname(rs.getString("PDNAME"));      // 제품명
		        pvo.setBrand(rs.getString("BRAND"));     	// 제조회사명
		        pvo.setSaleprice(rs.getInt("SALEPRICE"));   // 제품 판매가(할인해서 팔 것이므로)
		        pvo.setPdimg1(rs.getString("PDIMG1"));      // 제품이미지1   이미지파일명
		        
		        productInfoList.add(pvo);
	        
			}// end of while(rs.next())-------------------------

		} finally {
			close();
		}
		
		return productInfoList;
		
	}// end of public List<ProductVO> getordProductList ----


	

}
