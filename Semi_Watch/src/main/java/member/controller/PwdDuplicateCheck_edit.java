package member.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.ky_1_MemberDAO;
import member.model.ky_1_MemberDAO_imple;

public class PwdDuplicateCheck_edit extends AbstractController {

	private ky_1_MemberDAO mdao = null;
	
	public PwdDuplicateCheck_edit() {
		
		mdao = new ky_1_MemberDAO_imple();
		
	}	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String method = request.getMethod(); // "GET" 또는 "POST" 
		
		if("POST".equalsIgnoreCase(method)) {
		
			String password = request.getParameter("password");
			String userid = request.getParameter("userid");
			
			// System.out.println("확인용 password : " + password);
			// System.out.println("확인용 userid : " + userid);
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("password", password);
			paraMap.put("userid", userid);
			
			// 사용하고 있는 비밀번호 인지 확인하는 메소드
			boolean isExists = mdao.pwdDuplicateCheck_edit(paraMap); 
			
			JSONObject jsonObj = new JSONObject(); // {}
			jsonObj.put("isExists", isExists);     // {"isExists":true} 또는 {"isExists":false} 으로 만들어준다. 
			
			String json = jsonObj.toString(); // 문자열 형태인 "{"isExists":true}" 또는 "{"isExists":false}" 으로 만들어준다. 
			System.out.println(">>> 확인용 비밀번호 json => " + json);

			
			request.setAttribute("json", json);
			
		//	super.setRedirect(false); 
			super.setViewPage("/WEB-INF/jsonview.jsp");
		
		}		

	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception

}
