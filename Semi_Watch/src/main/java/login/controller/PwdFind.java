package login.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.model.ky_1_MemberDAO;
import member.model.ky_1_MemberDAO_imple;

public class PwdFind extends AbstractController {

	private ky_1_MemberDAO mdao = null;
	
	public PwdFind() {
		
		mdao = new ky_1_MemberDAO_imple();
		
	}		
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		if("POST".equalsIgnoreCase(method)) {
			
			String loc = ""; //DAO랑 연결
			String userid = request.getParameter("userid");
			String email = request.getParameter("email");
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("userid", userid);
			paraMap.put("email", email);
			
			Map<String, Object> userMap = mdao.isUserExist(paraMap);
			
			//////////////////////////////////////////////////
			
			boolean sendMailSuccess = false; // 메일 발송확인 유무
			
			
			
			if((boolean) userMap.get("isUserExist")) {
				// 회원으로 존재하는 경우
				// 인증키를 랜덤하게 생성하도록 한다.
	            Random rnd = new Random();
	            
	            String certification_code = "";
	            // 인증키는 영문소문자 5글자 + 숫자 7글자 로 만들겠습니다.
	            
	            char randchar = ' '; //초기치 빼도 괜찮음
	            for(int i=0; i<5; i++) {
	             /*
	                min 부터 max 사이의 값으로 랜덤한 정수를 얻으려면 
	                int rndnum = rnd.nextInt(max - min + 1) + min;
	                   영문 소문자 'a' 부터 'z' 까지 랜덤하게 1개를 만든다.     
	             */   
	               randchar = (char) (rnd.nextInt('z' - 'a' + 1) + 'a'); //in
	               certification_code += randchar;
	            }// end of for---------------------
	            
	            int randnum = 0;
	            for(int i=0; i<7; i++) {
	             /*
	                min 부터 max 사이의 값으로 랜덤한 정수를 얻으려면 
	                int rndnum = rnd.nextInt(max - min + 1) + min;
	                   영문 소문자 'a' 부터 'z' 까지 랜덤하게 1개를 만든다.     
	             */   
	               randnum = rnd.nextInt(9 - 0 + 1) + 0;
	               certification_code += randnum;
	            }// end of for---------------------
	            
	            System.out.println("~~~~ 확인용 certification_code : " + certification_code);
	            // ~~~~ 확인용 certification_code : nexrw2738979
	            // 랜덤하게 생성한 인증코드(certification_code)를 비밀번호 찾기를 하고자 하는 사용자의 email 로 전송시킨다.
	            // ==> 회원가입때 입력했던 내 진짜 이메일로 인증코드 쏴줌!!
	           
	            GoogleMail mail = new GoogleMail();
	           
	            try {
	            	mail.send_certification_code(userMap, paraMap, certification_code);
	            	sendMailSuccess = true;// 메일 전송 성공했음을 기록함.
	            	//email이 받는 사람(폼태그에서 넘어온 email) / 인증코드 
		           
	            	HttpSession session = request.getSession();
	            	session.setAttribute("certification_code", certification_code);
	            	//발급한 인증코드를 세션에 저장시킴.
	            	loc = request.getContextPath() + "/login/pwdFind.flex";
	            } catch(Exception e) {
	        	// 메일 전송이 실패한 경우 
	            	e.printStackTrace();
	            	sendMailSuccess = false; // 메일 전송 실패했음을 기록함.
	            }
				
			} //end of if (isUserExist)-------------------------
			
			JSONObject jsonObj = new JSONObject(); // {}
			
			jsonObj.put("isUserExist", userMap.get("isUserExist"));
            jsonObj.put("sendMailSuccess", sendMailSuccess);
            jsonObj.put("userid", userid);
            jsonObj.put("email", email);
			
	        
	        jsonObj.put("loc", loc); 
			// {"message":"메세지내용","loc":"/MyMVC/index.flex"}
	        String json = jsonObj.toString(); // String타입으로 바꿈
	        
	        request.setAttribute("json", json);
	        super.setRedirect(false);
	        super.setViewPage("/WEB-INF/jsonview.jsp");
	        return;
		}
		//들어오면 띄워줘야함
		request.setAttribute("method", method); //중요!! 이렇게 보내줘야 함
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/login/pwdFind.jsp");
		

	}

}
