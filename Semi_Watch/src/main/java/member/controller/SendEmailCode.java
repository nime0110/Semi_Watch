package member.controller;

import java.util.Random;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;

public class SendEmailCode extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();	// GET 또는 POST 방식
		String checkGoodong = "none";
		
		if("POST".equalsIgnoreCase(method)) {
			
			HttpSession session = request.getSession();
//			MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
//			String loginuserid = loginuser.getUserid();
//			String userid = request.getParameter("userid");
			
			System.out.println("인증코드 클래스 넘어왔어요");
			
			boolean sendMailSuccess = false; // 메일이 정상적으로 전송되었는지 유무를 알아오기 위한 용도
			
//			if(userid.equals(loginuserid)) {
				String newEmail = request.getParameter("newemail");
				
				Random rnd = new Random();
	            
	            String email_code = "";
	            // 인증키는 영문소문자 5글자 + 숫자 7글자 로 만들겠습니다.
	            
	            char randchar = ' ';
	            for(int i=0; i<5; i++) {
	             /*
	                min 부터 max 사이의 값으로 랜덤한 정수를 얻으려면 
	                int rndnum = rnd.nextInt(max - min + 1) + min;
	                   영문 소문자 'a' 부터 'z' 까지 랜덤하게 1개를 만든다.     
	             */   
	               randchar = (char) (rnd.nextInt('z' - 'a' + 1) + 'a');
	               email_code += randchar;
	               
	            }// end of for---------------------
	            
	            int randnum = 0;
	            for(int i=0; i<7; i++) {
	             /*
	                min 부터 max 사이의 값으로 랜덤한 정수를 얻으려면 
	                int rndnum = rnd.nextInt(max - min + 1) + min;
	                   영문 소문자 'a' 부터 'z' 까지 랜덤하게 1개를 만든다.     
	             */   
	               randnum = rnd.nextInt(9 - 0 + 1) + 0;
	               email_code += randnum;
	               
	            }// end of for---------------------
	            
	            System.out.println("~~~~ 확인용 certification_code : " + email_code);
	            // ~~~~ 확인용 certification_code : ahjgy9907204
	            
	            
	            // 랜덤하게 생성한 인증코드(certification_code)를 비밀번호 찾기를 하고자 하는 사용자의 email 로 전송시킨다.
				GoogleMail_my mail = new GoogleMail_my();
				
				try {
					
					mail.send_certification_code(newEmail, email_code);
					// send_certification_code(수신자, 보내는 내용);
					
					sendMailSuccess = true;	// 메일 전송이 성공했음을 기록함.
					checkGoodong = "yes";
					
					session.setAttribute("email_code", "12345");
					// 발급한 인증키를 sesstion 에 저장시킨다. 그래서 다른 클래스 및 jsp에서 사용할 수 있도록 한다.
					
				}catch(Exception e) {
					// 메일 전송이 실패한 경우 
					e.printStackTrace();
					sendMailSuccess = false; // 메일 전송 실패했음을 기록함. 확인사살용
				}
				
				request.setAttribute("sendMailSuccess", sendMailSuccess);
				request.setAttribute("newEmail", newEmail);
				
//			}
				
			request.setAttribute("checkGoodong", checkGoodong);
				
			// get 방식이든 post 이든 같은 form 을 보여줘야하기 때문에 아래다 둔다.
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/member/memberInfoChange.jsp");
			
		}// end of if("POST".equalsIgnoreCase(method))---
	}

}
