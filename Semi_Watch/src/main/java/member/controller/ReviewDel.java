package member.controller;

import java.sql.SQLException;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import member.model.ky_1_MemberDAO;
import member.model.ky_1_MemberDAO_imple;

public class ReviewDel extends AbstractController {
	
	private ky_1_MemberDAO mdao = null;
	
	public ReviewDel() {
		
		mdao = new ky_1_MemberDAO_imple();
		
	}	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 관리자(admin)로 로그인 했을 때만 삭제처리가 가능하도록 설정
		HttpSession session = request.getSession(); // 세션 내역 불러오기
		
		
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		if( loginuser != null && "admin".equals(loginuser.getUserid())) {
			// 관리자로 로그인 했을 때
			String method = request.getMethod();
			
			if("POST".equalsIgnoreCase(method)) {
					
				String reviewno = request.getParameter("reviewno");
				String message = "";
				String loc = "";
				
				try {
					int n = mdao.deleteOneReview(reviewno);
					
					if(n == 1) { // 삭제 성공시
						
						super.setRedirect(false);
						
						String goBackURL = (String)session.getAttribute("goBackURL"); 
						// System.out.println("확인용 goBackURL : "+goBackURL);
						
						session.removeAttribute("goBackURL");
						message = "리뷰 삭제에 성공했습니다.";
						loc = request.getContextPath()+goBackURL;
						
					}
						
					
				} catch (SQLException e) {
					
					message = "리뷰 삭제에 실패했습니다.";
					loc = "javascript:history.back()";
					e.printStackTrace();
				}
				
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				super.setRedirect(false);		
				super.setViewPage("/WEB-INF/msg.jsp");
				
			}// end of if("POST".equalsIgnoreCase(method))
		
		}
		else {
			// 로그인을 안한 경우 또는 일반사용자로 로그인 한 경우
		      String message = "관리자만 접근이 가능합니다.";
		      String loc = "javascript:history.back()";

		      request.setAttribute("message", message);
		      request.setAttribute("loc", loc);

		      // super.setRedirect(false);
		      super.setViewPage("/WEB-INF/msg.jsp");
		}

	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception 

}
