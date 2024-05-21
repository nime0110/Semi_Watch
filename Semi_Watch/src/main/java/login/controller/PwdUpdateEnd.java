package login.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.ky_1_MemberDAO;
import member.model.ky_1_MemberDAO_imple;

public class PwdUpdateEnd extends AbstractController {
	
	private ky_1_MemberDAO mdao = null;
	
	public PwdUpdateEnd() {
		
		mdao = new ky_1_MemberDAO_imple();
		
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		
		String userid = request.getParameter("userid"); // get 방식 /post 방식 둘다 넘어와야됨
		String method = request.getMethod();
		if("POST".equalsIgnoreCase(method)) {
			//암호변경하기 버튼을 클릭했을경우
			
			String new_pwd = request.getParameter("pwd"); // POST 방식일때만 넘어와야함
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("userid", userid);
			paraMap.put("new_pwd", new_pwd);			
			
			int n = 0;
			try {				
				n = mdao.pwdUpdate(paraMap); //정상적이면 1행 이 변경 => 1 이 나옴
			} catch(SQLException e) {
				e.printStackTrace();
			}	
			request.setAttribute("n",n);
			// n ==> 1  또는 n ==> 0 1일 때는 새로이 변경되었다고 하고
			// pwdUpdateEnd.jsp 로 똑같이 전송
			
			
		}// end of if("POST".equalsIgnoreCase(method)) ------------- 
		
		
		request.setAttribute("userid",userid);
		
		request.setAttribute("method",method); //get 방식인지 post 방식인지
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/login/pwdUpdateEnd.jsp"); 
	}

}
