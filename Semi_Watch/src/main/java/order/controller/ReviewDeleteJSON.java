package order.controller;

import java.io.PrintWriter;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import order.model.ss_2_OrderDAO;
import order.model.ss_2_OrderDAO_imple;
import shop.domain.Product_DetailVO;


public class ReviewDeleteJSON extends AbstractController {
	private ss_2_OrderDAO odao = null;
	
	public ReviewDeleteJSON() {
		odao = new ss_2_OrderDAO_imple();
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
            jsonObj.put("message", "리뷰를 작성하시려면 로그인이 필요합니다.");
            jsonObj.put("loginRequired", true);

            out.print(jsonObj.toString()); //jsonObj에 담은 걸 문자열로바꿔서 출력한뒤에 
            return; //리턴
        } else {
            // 로그인 한 상태라면
        	HttpSession session = request.getSession();
        	MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
        	//

        	String productNo = request.getParameter("productNo"); //제품번호
        	
        	String userid = loginuser.getUserid();
        	            
        	// 리뷰 테이블에서 삭제 
            int result = odao.deleteReview(productNo, userid);
            
            
            jsonObj.put("message", "리뷰가 삭제되었습니다.");
            jsonObj.put("loginRequired", false);
            
            if(result != 1) {
            	jsonObj.put("message", "리뷰삭제에 실패했습니다.");
                jsonObj.put("loginRequired", false);
                	
            }
              
            out.print(jsonObj.toString());
            
        }
	}

}
