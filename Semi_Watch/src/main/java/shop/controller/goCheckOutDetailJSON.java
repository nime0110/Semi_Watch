package shop.controller;

import java.io.PrintWriter;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import shop.domain.ProductVO;
import shop.domain.Product_DetailVO;
import shop.model.ss_2_ProductDAO;
import shop.model.ss_2_ProductDAO_imple;

public class goCheckOutDetailJSON extends AbstractController {
	private ss_2_ProductDAO pdao = null;
	
	public goCheckOutDetailJSON() {
		pdao = new ss_2_ProductDAO_imple();
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    response.setContentType("application/json; charset=UTF-8"); //응답하는 컨텐츠 타입 선택 text/html 시 html 코드 작성해서 응답
	    // 클라이언트에 전달할 출력물 작성에 사용됨 - PrintWriter: 출력 스트림, repsonse.getWriter(); : 출력 스트림을 가져온다.
        PrintWriter out = response.getWriter(); //응답 스트림에 텍스트로 기록 - 서블릿으로 들어온 요청 => 텍스트로 응답 즉, 출력스트림

        // === 먼저 로그인 유무 검사하기
        if (!super.checkLogin(request)) {
        	JSONObject jsonLogin = new JSONObject();
            // 로그인을 하지 않은 상태라면
            jsonLogin.put("message", "장바구니에 담으려면 로그인이 필요합니다.");
            jsonLogin.put("loginRequired", true);

            out.print(jsonLogin.toString()); //jsonObj에 담은 걸 문자열로바꿔서 출력한뒤에 
            return; //리턴
        } else {
	        // 로그인 한 상태라면
	    	HttpSession session = request.getSession();
	    	MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
	    	
	    	String str_pdno = request.getParameter("productNo");
	    	String selectedColor = request.getParameter("selectedColor");
	    	String str_cart_qty = request.getParameter("quantity");
	    	//System.out.println("=== productNo : " + productNo);
	    	//System.out.println("=== selectedColor : " + selectedColor); 없을경우 none
	    	//System.out.println("=== quantity : " + quantity);
	    	
	    	//제품번호를 통해서 해당 제품의 정보 조회
	    	ProductVO pvo = pdao.selectOneProductBypdno(str_pdno); // 제품 하나를 리턴
	    	
	    	//들어온 컬러 코드와 제품번호로 제품상세번호 가져오는 메소드 
	        List<Product_DetailVO> wishDetailList = pdao.getWishDetailByPnum(str_pdno, selectedColor);
	        
	        String str_pd_detailno = "";
	        if (!wishDetailList.isEmpty()) {
	            for (Product_DetailVO pdvo : wishDetailList) {
	            	str_pd_detailno = pdvo.getPd_detailno();
	            }
	        }
	        
	        String userid = loginuser.getUserid();
	
	        //상품별 총가격 개당단가 * 수량
	        
	        long str_pdPriceArr = pvo.getSaleprice() * Integer.parseInt(str_cart_qty); //총가격
	        long str_pdPointArr = (pvo.getPoint() * Integer.parseInt(str_cart_qty));
	        //상품별 총포인트 (개당포인트 * 수량) 
	        
	        /*
	        request.setAttribute("str_pdno", str_pdno);
	        request.setAttribute("str_cart_qty", str_cart_qty);
	        request.setAttribute("str_pd_detailno", str_pd_detailno);
	        request.setAttribute("str_pdPriceArr", str_pdPriceArr);
	        request.setAttribute("str_pdPointArr", str_pdPointArr);
	        */
	        
	        // 제품번호 and 색상 재고 확인 메소드
	        boolean isPdQtyOk = false; //재고없는경우
	        isPdQtyOk = pdao.itemDetailCheckPdQty(str_pdno, selectedColor, str_cart_qty);
	
	        JSONObject jsonObj = new JSONObject();
	        jsonObj.put("str_cart_qty", str_cart_qty); //구매수량
	        jsonObj.put("str_pd_detailno", str_pd_detailno); //상품상세번호
	        jsonObj.put("str_pdPriceArr", str_pdPriceArr); //총가격
	        jsonObj.put("str_pdPointArr", str_pdPointArr); //총포인트
	        jsonObj.put("isPdQtyOk", isPdQtyOk); //재고괜찮은지여부
	        
	        String json = jsonObj.toString();
		    request.setAttribute("json", json);
		    super.setViewPage("/WEB-INF/jsonview.jsp");
        }
	}

}
