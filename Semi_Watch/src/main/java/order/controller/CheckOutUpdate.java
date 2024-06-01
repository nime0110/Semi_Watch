package order.controller;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import login.controller.GoogleMail;
import member.domain.MemberVO;
import shop.domain.ProductVO;
import shop.model.jh_3_ProductDAO;
import shop.model.jh_3_ProductDAO_imple;

public class CheckOutUpdate extends AbstractController {
	
	private jh_3_ProductDAO pdao = null;
	
	public CheckOutUpdate() {
		pdao = new jh_3_ProductDAO_imple();
	}
	
	private String getOdrcode() {
    	
    	// 전표(주문코드) 형식 : t+날짜+sequence  t20240529-1
    	
    	// 날짜 생성
        Date now = new Date();
        SimpleDateFormat smdatefm = new SimpleDateFormat("yyyyMMdd"); 
        String today = smdatefm.format(now);
        
        // 시퀀스 가져오기
        // pdao.get_seq_tbl_order(); 는 시퀀스 seq_tbl_order 값("주문코드(명세서번호) 시퀀스")을 채번해오는 것.
        int seq = 0;
        
		try {
			seq = pdao.get_seq_tbl_order_ordercode();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        return "t"+today+"-"+seq;
    	
    }

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		String ctxPath = request.getContextPath();
		
		if("post".equalsIgnoreCase(method)) {
			// 배송정보
			String name = request.getParameter("name");
			String mobile = request.getParameter("mobile");
			String postcode = request.getParameter("postcode");
			String address = request.getParameter("address");
			String deliverymsg = request.getParameter("deliverymsg");
			
			// 제품정보
			String str_pnum_join = request.getParameter("str_pnum_join");
			String str_pdetail_join = request.getParameter("str_pdetail_join");
			String str_poption_join = request.getParameter("str_poption_join");
			String str_oqty_join = request.getParameter("str_oqty_join");
			String str_cartno_join = request.getParameter("str_cartno_join");
			String str_ptotalPrice_join = request.getParameter("str_ptotalPrice_join");
			
			String[] pnumArr = str_pnum_join.split(",");
			String[] pdetailArr = str_pdetail_join.split(",");
			String[] poptionArr = str_poption_join.split(",");
			String[] oqtyArr = str_oqty_join.split(",");
			
			String[] ptotalPriceArr = str_ptotalPrice_join.split(",");
			
			
			
			// 결제정보
			String paymentTotalPrice = request.getParameter("paymentTotalPrice");
			String updatePoint = request.getParameter("updatePoint");
			
			
			// System.out.println("확인용 str_pnum_join : " + str_pnum_join);
			
			// 담을 그릇
			Map<String, Object> paraMap = new HashMap<>();
			
			// 주문테이블 넣을 데이터
			String ordcode = getOdrcode();
			HttpSession session = request.getSession();
			MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
			
			paraMap.put("userid", loginuser.getUserid());
			paraMap.put("ordcode", ordcode);
			paraMap.put("paymentTotalPrice", paymentTotalPrice);
			
			// 배송지정보 넣을 데이터
			paraMap.put("name", name);
			paraMap.put("mobile", mobile);
			paraMap.put("postcode", postcode);
			paraMap.put("address", address);
			paraMap.put("deliverymsg", deliverymsg);
			
			
			
			// 주문상세에 넣을 데이터
			paraMap.put("pnumArr", pnumArr);
			paraMap.put("pdetailArr", pdetailArr);
			paraMap.put("oqtyArr", oqtyArr);
			paraMap.put("ptotalPriceArr", ptotalPriceArr);
			
			// 장바구니 삭제할 데이터가 있는 경우(장바구니에서 주문하는 경우만)
			if(str_cartno_join != null && str_cartno_join != "" ) {
				String[] cartnoArr = str_cartno_join.split(",");
				paraMap.put("cartnoArr", cartnoArr);
			}
			
			// 상품상세 재고 변경할 데이터
			paraMap.put("poptionArr", poptionArr);
			
			// 유저 포인트 업데이트 데이터
			paraMap.put("updatePoint", updatePoint);
			
			
			// *** Transaction 처리를 해주는 메소드 호출하기 *** //
         	int isSuccess = pdao.checkOutUpdate(paraMap);	// 성공하면 1 Transaction 처리
         	
         	
         	if(isSuccess == 1) {
         		System.out.println("업데이트 성공");
         		
         		// 유저포인트 갱신 및 주문메일 발송
         		loginuser.setMileage(Integer.parseInt(updatePoint));
         		
         		// ==== 메일보내기 시작 ==== //
         		GoogleMail mail = new GoogleMail();
         		
         		String pnums = "'"+String.join("','", pnumArr)+"'";
         		
         		System.out.println("확인용 주문한 제품번호 pnumes : " + pnums);
         		
         		// 주문한 제품에 대해 email 보내기시 email 내용에 넣을 주문한 제품번호들에 대한 제품정보를 얻어오는 것.
	         	List<ProductVO> ordProductList = pdao.getordProductList(pnums);
         		
	         	StringBuilder sb = new StringBuilder();
	         	sb.append("주문코드번호 : <span style='color: blue; font-weight: bold;'>"+ordcode+"</span><br/><br/>");
	         	sb.append("<주문상품><br/>");
	         	
	         	for(int i=0; i<ordProductList.size(); i++) {
	         		// jumunProductList.size() 와 oqty_arr.length 와 같다.
	         		sb.append(ordProductList.get(i).getPdname()+"&nbsp;옵션명: "+poptionArr[i]+"&nbsp;구매수량 :"+oqtyArr[i]+"개&nbsp;<br>");
	         		sb.append("<img src='http://127.0.0.1:9090/MyMVC/images/"+ordProductList.get(i).getPdimg1()+"' />");	// 구글메일은 127.0.0.1 은 허락하지않는다.

	         	}// end of for-----
	         	
	         	sb.append("<br>");
         		sb.append("<br/>이용해 주셔서 감사합니다.");
	         	
	         	String emailContents = sb.toString();
	         	
	         	
	         	mail.sendmail_checkOutFinish(loginuser.getEmail(), loginuser.getUsername(), emailContents);
         		
         		
         		// ==== 메일보내기 끝 ==== //
         		
         	}
			
         	JSONObject jsobj = new JSONObject();
         	jsobj.put("ctxPath", ctxPath);	// success 시 사용하려고 보냄
         	jsobj.put("isSuccess", isSuccess);	// 1 또는 0
         	
         	String json = jsobj.toString();	// {isSuccess:1} 또는 {isSuccess:0}
         	
         	request.setAttribute("json", json);
         	
         	super.setRedirect(false);
            super.setViewPage("/WEB-INF/jsonview.jsp");
			

		}
		else {
			// GET 방식이라면
			String message = "비정상적인 경로로 들어왔습니다";
			String loc = "javascript:history.back()";
        
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
       
			// super.setRedirect(false);   
			super.setViewPage("/WEB-INF/msg.jsp");
		}

	}

}
