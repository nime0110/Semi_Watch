package member.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import member.model.jh_3_MemberDAO;
import member.model.jh_3_MemberDAO_imple;

public class MemberInfoChangeEnd extends AbstractController {
	
	private jh_3_MemberDAO mdao = null;
	
	public MemberInfoChangeEnd() {
		mdao = new jh_3_MemberDAO_imple();
	}	

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();
		System.out.println("MemberInfoChangeEnd 클래스 작동!");
		
		String message = "";
		String loc = "";
		
		if("POST".equalsIgnoreCase(method)) {	// post 방식일 경우에만 실행
			
			String infoUpdate = request.getParameter("infoUpdate");
			String userid = request.getParameter("userid");
			
			HttpSession session = request.getSession();
			MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
			
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("userid", userid);
			
			
			
			try {
				int result = 0;
				
				
				if("pwd".equals(infoUpdate)){	// 비밀번호 변경일 경우에만
					String newPassword = request.getParameter("newPassword");
					
					if(newPassword == null) {
						System.out.println("비밀번호 값 null 임");
					}
					
					paraMap.put("newPassword", newPassword);
					
					result = mdao.updatePWD(paraMap);
					
					loginuser.setPw(newPassword);
					
					message = "비밀번호 변경이 완료되었습니다.";
					
					
				}
				else if("email".equals(infoUpdate)){
					String newEmail = request.getParameter("newEmail");
					if(newEmail == null) {
						System.out.println("이메일 값 null 임");
					}
					
					paraMap.put("newEmail", newEmail);
					
					result = mdao.updateEmail(paraMap);
					
					loginuser.setEmail(newEmail);
					
					message = "이메일 변경이 완료되었습니다.";
					
				}
				else if("mobile".equals(infoUpdate)){
					String newMoblie = request.getParameter("newMoblie");
					
					if(newMoblie == null) {
						System.out.println("전화번호 값 null 임");
					}
					
					paraMap.put("newMoblie", newMoblie);
					
					result = mdao.updateMobile(paraMap);
					
					loginuser.setMobile(newMoblie);
					
					message = "전화번호 변경이 완료되었습니다.";
					
					
				}
				else if("post".equals(infoUpdate)){
					String postcode = request.getParameter("postcode");	// 우편번호
					String addr = request.getParameter("addr");	// 도로명
					String extraAddr = request.getParameter("extraAddr");	// 구주소동
					String addressDetail = request.getParameter("addressDetail");	// 상세주소
					
					if(postcode == null) {
						System.out.println("이메일 값 null 임");
					}
					
					paraMap.put("postcode", postcode);
					paraMap.put("addr", addr);
					paraMap.put("extraAddr", extraAddr);
					paraMap.put("addressDetail", addressDetail);
					
					result = mdao.updatePost(paraMap);
					
					loginuser.setPostcode(postcode);
					loginuser.setAddress(addressDetail);
					loginuser.setExtra_address(extraAddr);
					loginuser.setDetail_address(addressDetail);
					
					message = "주소 변경이 완료되었습니다.";
					
				}
				
				
				
				loc = request.getContextPath()+"/member/memberInfoChange.flex"; // 다시페이지로 이동한다.
			
			
			}catch(SQLException e) {
				message = "SQL구문 에러발생";
				loc = "javascript:history.back()"; // 자바스크립트를 이용한 이전페이지로 이동하는 것. 
				e.printStackTrace();
			}
			
			message = "비정상적인 경로로 들어왔습니다.";
			loc = "javascript:history.go(0);";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			// request.setAttribute("memberEditEnd", true); 필요없을듯
			
		//	super.setRedirect(false); 
			super.setViewPage("/WEB-INF/msg.jsp");
			

		}// end of if("POST".equalsIgnoreCase(method))---

	}

}
