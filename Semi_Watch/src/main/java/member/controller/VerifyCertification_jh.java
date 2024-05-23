package member.controller;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class VerifyCertification_jh extends AbstractController {
	

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();	// GET 또는 POST 방식
		
		if("POST".equalsIgnoreCase(method)) {
			
			boolean codeCheckAnswer = false;
			HttpSession session = request.getSession();
			String email_code = (String)session.getAttribute("email_code");
			String mobile_code = (String)session.getAttribute("mobile_code");
			
			String infoUpdate = request.getParameter("infoUpdate");
			JSONObject jsonObj = new JSONObject();
			
			
			if("email".equals(infoUpdate)) {
				
				String emailAuthTempKey = request.getParameter("emailAuthTempKey");
				
				if(email_code.equals(emailAuthTempKey)) {	// 입력한 코드가 일치할 경우
					// json으로 넘겨야한다.
					codeCheckAnswer = true;
				}

			}

			if("mobile".equals(infoUpdate)) {
				
				String mobileAuthTempKey = request.getParameter("mobileAuthTempKey");
				
				if(mobile_code.equals(mobileAuthTempKey)) {	// 입력한 코드가 일치할 경우
					// json으로 넘겨야한다.
					codeCheckAnswer = true;
				}

			}
			
			jsonObj.put("codeCheckAnswer", codeCheckAnswer);
			String json = jsonObj.toString();
			
			// System.out.println("확인용 json => "+json);
			
			request.setAttribute("json", json);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/jsonview.jsp");
			
			
			// 저장했던 코드 제거
			session.removeAttribute("mobile_code");
			session.removeAttribute("email_code");
			

		}// end of if("post".equalsIgnoreCase(method))----------
	}

}
