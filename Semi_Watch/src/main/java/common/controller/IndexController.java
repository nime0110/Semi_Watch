package common.controller;



import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import member.model.ss_2_MemberDAO;
import member.model.ss_2_MemberDAO_imple;
import shop.model.ky_1_ProductDAO;
import shop.model.ky_1_ProductDAO_imple;


public class IndexController extends AbstractController {

	private ss_2_MemberDAO mdao = null;
	
	private ky_1_ProductDAO pdao = null;
	
	public IndexController() {
		mdao = new ss_2_MemberDAO_imple();
		
		pdao = new ky_1_ProductDAO_imple();
	}
	
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		   	
			// 로그인 또는 로그아웃 시 시작페이지로 가는 것이 아니라 방금 보았던 그 페이지로 가기 위한 것
			super.goBackURL(request); // 세션에 현재 url 저장
		
			HttpSession session = request.getSession();
		    Object loginUser = session.getAttribute("loginuser");

		    if (loginUser == null) {
		        Cookie[] cookies = request.getCookies();
		        if (cookies != null) {
		            for (Cookie cookie : cookies) {
		                if ("userid".equals(cookie.getName())) {
		                    String userid = cookie.getValue();
		                    // 데이터베이스에서 유저 정보 가져오기
		                    MemberVO loginuser = mdao.getMemberByUserid(userid);
		                    if (loginuser != null) {
		                        session.setAttribute("loginuser", loginuser);
		                        break;
		                    }
		                }
		            }
		        }
		    }
		    
		    
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/index.jsp");
		// super.setViewPage("/template_orgin.jsp"); // 확인했다가 지웠음
		
	}

}
