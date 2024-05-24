package member.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.jh_3_MemberDAO;
import member.model.jh_3_MemberDAO_imple;

public class PwdDuplicateCheck extends AbstractController {

	private jh_3_MemberDAO mdao = null;
	
	public PwdDuplicateCheck() {
		mdao = new jh_3_MemberDAO_imple();
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String method = request.getMethod(); // "GET" 또는 "POST" 
		
		if("POST".equalsIgnoreCase(method)) {
		
			String password = request.getParameter("password");
			String userid = request.getParameter("userid");
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("password", password);
			paraMap.put("userid", userid);
			
			// 사용하고 있는 비밀번호 인지 확인한다.
			boolean isExists = mdao.pwdDuplicateCheck(paraMap); 
			
			JSONObject jsonObj = new JSONObject(); // {}
			jsonObj.put("isExists", isExists);     // {"isExists":true} 또는 {"isExists":false} 으로 만들어준다. 
			
			String json = jsonObj.toString(); // 문자열 형태인 "{"isExists":true}" 또는 "{"isExists":false}" 으로 만들어준다. 
			System.out.println(">>> 확인용 비밀번호 json => " + json);

			
			request.setAttribute("json", json);
			
		//	super.setRedirect(false); 
			super.setViewPage("/WEB-INF/jsonview.jsp");
		
		}		
		
	}	

}
