package member.controller;

import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import member.model.jh_3_MemberDAO;
import member.model.jh_3_MemberDAO_imple;

public class MemberInfoChange extends AbstractController {
	
	private jh_3_MemberDAO mdao = null;
	
	public MemberInfoChange() {
		mdao = new jh_3_MemberDAO_imple();
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if(checkLogin(request)) {
			HttpSession session = request.getSession();
			MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
			String userid = (String) loginuser.getUserid();
			
			// 가져와야할 데이터
			// 장바구니 건수, 리뷰건수..
			
			Map<String, String> cnt = mdao.get_cart_review_cnt(userid);
			
			
			request.setAttribute("userid", userid);
			session.setAttribute("cnt", cnt);
			
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/member/memberInfoChange.jsp");
		}
		else {
			super.setRedirect(true);
			super.setViewPage(request.getContextPath()+"/login/login.flex");
		}
		
		
		

	}

}
