package login.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;

public class Logout extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 로그아웃 처리하기

		HttpSession session = request.getSession();	// 세션불러오기
		
		String goBackURL =(String) session.getAttribute("goBackURL");
		
        if(goBackURL != null) {
            goBackURL = request.getContextPath()+goBackURL;
            // System.out.println("goBackURL : " + goBackURL);
        }
        
        MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
        String login_userid = loginuser.getUserid();
        
		// 첫번째 방법 : 세션을 그대로 존재하게끔 해두고, 세션에 저장되어진 어떤 값(지금은 로그인 되어진 회원객체)을 삭제하기
		// session.removeAttribute("loginuser");	// loginuser 은 저장해둔 key 값
		
		
		// 두번째 방법 : WAS 메모리 상에서 세션에 저장된 모든 데이터값을 삭제하는 것
		session.invalidate();
		
		
		super.setRedirect(true);
		
		if(goBackURL != null && !"admin".equals(login_userid)) {
			// 관리자가 아닌 일반 사용자로 들어와서 돌아갈 페이지가 있다라면 돌아갈 페이지로 돌아간다.
			super.setViewPage(goBackURL);
        }
		else {     
			// 돌아갈 페이지가 없거나 또는 관리자로 로그아웃을 하면 무조건 /MyMVC/index.up 페이지로 돌아간다.
			super.setViewPage(request.getContextPath()+"/index.flex");
	    }

	}

}
