package member.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import member.model.ss_2_MemberDAO;
import member.model.ss_2_MemberDAO_imple;

public class MemberOneDetail extends AbstractController {

	private ss_2_MemberDAO mdao = null;
	
	public MemberOneDetail() {
		mdao = new ss_2_MemberDAO_imple();
	}
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		if(loginuser != null && "admin".equals(loginuser.getUserid())) {
			String method = request.getMethod();
			if("POST".equalsIgnoreCase(method)) {
				String userid = request.getParameter("userid");
				String goBackURL = request.getParameter("goBackURL");
				
				//회원 상세정보
				MemberVO mvo = mdao.selectOneMember(userid);
		        request.setAttribute("mvo", mvo);
		        request.setAttribute("goBackURL", goBackURL);
//		        super.setRedirect(false);
		        super.setViewPage("/WEB-INF/member/admin/memberOneDetail.jsp");
			}
		}
		
	}

}
