package login.controller;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class VerifyCertification extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();
		
		if("POST".equalsIgnoreCase(method)) {
			String userCertificationCode = request.getParameter("input_confirmCode");
			String userid = request.getParameter("userid");
			System.out.println(userCertificationCode);
			
			HttpSession session = request.getSession();
	        String certification_code = (String)session.getAttribute("certification_code");
	        System.out.println(certification_code);
	        //세션에 저장해두었던 인증코드를 불러옴 (PwdFind)
	        String message = "";
	        String loc = "";
	        
	        if(userCertificationCode.equals(certification_code)) {
	        	message = "인증에 성공했습니다";
	        	loc = request.getContextPath() + "/login/pwdUpdateEnd.flex?userid=" + userid;
	        } else {
	        	message = "발급된 인증코드가 아닙니다.\\n인증코드를 다시 발급받으세요!!";	    
	        	loc = request.getContextPath() + "/login/pwdFind.flex";
	        }
	        JSONObject jsonobj = new JSONObject();
	        jsonobj.put("message",message);
	        jsonobj.put("loc",loc);
	        String json = jsonobj.toString();
	        request.setAttribute("json", json);
			System.out.println(json);
	        
	        super.setViewPage("/WEB-INF/jsonview.jsp");
	        // !!!! 중요 !!!! //
	        // !!!! 세션에 저장된 인증코드 삭제하기 !!!! => 이미 위에서 활용을 다 했으므로 반드시 폐기해야 한다 //
	        session.removeAttribute("certification_code");
	        
		}
	}

}
