package member.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class VerifyCertification_my extends AbstractController {
	

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();	// GET 또는 POST 방식
		
		if("POST".equalsIgnoreCase(method)) {
			
			String emailAuthTempKey = request.getParameter("emailAuthTempKey");
			String userid = request.getParameter("userid");
			String newemail = request.getParameter("newemail");
			// String infoUpdate = request.getParameter("infoUpdate");
			
			System.out.println("VerifyCertification_my 클래스까지 왔어용");
			
			// 세션에 저장한 생성된 인증키값 불러오기
			HttpSession session = request.getSession();
			String email_code = (String)session.getAttribute("email_code");
			
			// 메시지 전달용
			String checkMent = "";
			
			// 인증성공여부 전달용
			boolean AuthTemp = false;
			
			// 입력한 인증키와 생성된 인증키가 일치하는지 확인
			if(email_code.equals(emailAuthTempKey)) {
				checkMent = "인증성공 되었습니다. 변경하시려면 완료버튼을 눌러주세요.";
				// 이메일 변경하는 페이지로 이동

				AuthTemp = true;
			}
			else {
				checkMent = "발급된 인증코드가 아닙니다. 인증코드를 다시 발급받으세요!!";
			}
			
			request.setAttribute("userid", userid);
			request.setAttribute("newEmail", newemail);
			request.setAttribute("checkMent", checkMent);
			request.setAttribute("AuthTemp", AuthTemp);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/member/memberInfoChange.jsp");
			
			
			// !!!! 중요 !!!! //
			// !!!! 세션에 저장된 인증코드 삭제하기 !!!! //
			session.removeAttribute("email_code");
			
			
			

		}// end of if("post".equalsIgnoreCase(method))----------
	}

}
