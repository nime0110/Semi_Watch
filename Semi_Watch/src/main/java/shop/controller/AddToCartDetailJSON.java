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
import shop.domain.Product_DetailVO;
import shop.model.ss_2_ProductDAO;
import shop.model.ss_2_ProductDAO_imple;

public class AddToCartDetailJSON extends AbstractController {
	private ss_2_ProductDAO pdao = null;
	
	public AddToCartDetailJSON() {
		pdao = new ss_2_ProductDAO_imple();
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    response.setContentType("application/json; charset=UTF-8"); //응답하는 컨텐츠 타입 선택 text/html 시 html 코드 작성해서 응답
	    // 클라이언트에 전달할 출력물 작성에 사용됨 - PrintWriter: 출력 스트림, repsonse.getWriter(); : 출력 스트림을 가져온다.
        PrintWriter out = response.getWriter(); //응답 스트림에 텍스트로 기록 - 서블릿으로 들어온 요청 => 텍스트로 응답 즉, 출력스트림
        JSONObject jsonObj = new JSONObject();

        // === 먼저 로그인 유무 검사하기
        if (!super.checkLogin(request)) {
            // 로그인을 하지 않은 상태라면
            jsonObj.put("message", "장바구니에 담으려면 로그인이 필요합니다.");
            jsonObj.put("loginRequired", true);

            out.print(jsonObj.toString()); //jsonObj에 담은 걸 문자열로바꿔서 출력한뒤에 
            return; //리턴
        } else {
            // 로그인 한 상태라면
        	HttpSession session = request.getSession();
        	MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
        	//
        	String productNo = request.getParameter("productNo");
        	String selectedColor = request.getParameter("selectedColor");
        	String quantity = request.getParameter("quantity");
        	//System.out.println("=== productNo : " + productNo);
        	//System.out.println("=== selectedColor : " + selectedColor); 없을경우 none
        	//System.out.println("=== quantity : " + quantity);
        	
        	//들어온 컬러 코드와 제품번호로 제품상세번호 가져오는 메소드 
            List<Product_DetailVO> wishDetailList = pdao.getWishDetailByPnum(productNo, selectedColor);
            
            String pdDetailNo = "";
            if (!wishDetailList.isEmpty()) {
                for (Product_DetailVO pdvo : wishDetailList) {
                    pdDetailNo = pdvo.getPd_detailno();
                }
            }
            
            String userid = loginuser.getUserid();

            //위시리스트 -> 장바구니 insert 메소드
            int n = pdao.DetailProductInsert(pdDetailNo, userid, quantity);
            if (n == 1) {
                jsonObj.put("message", "상품이 장바구니에 추가되었습니다. \n장바구니로 이동하시겠습니까?");
                jsonObj.put("loginRequired", false);
            } else {
                jsonObj.put("message", "상품을 장바구니에 추가하는 데 실패했습니다.");
                jsonObj.put("loginRequired", false);
            }
            out.print(jsonObj.toString());
            
        }
	}

}
