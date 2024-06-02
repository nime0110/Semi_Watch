package login.controller;

import java.util.HashMap;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import member.model.ss_2_MemberDAO;
import member.model.ss_2_MemberDAO_imple;

public class Login extends AbstractController {

   private ss_2_MemberDAO mdao = null;
   
   public Login() {
      mdao = new ss_2_MemberDAO_imple();
   }
   
   @Override
   public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

      String method = request.getMethod();
      if(!"POST".equalsIgnoreCase(method)) { //get방식으로 들어온다면 
            // 쿠키 확인
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("userid")) {
                        String userid = cookie.getValue();
                        HttpSession session = request.getSession();
                        
                        // userid로 데이터베이스에서 유저 정보를 가져옴
                        MemberVO loginuser = mdao.getMemberByUserid(userid);
                        if (loginuser != null) {
                           session.setAttribute("userid", userid);
                           session.setAttribute("loginuser", loginuser);
                           
                           // 메인 페이지로 리다이렉트
                           System.out.println("쿠키로 로그인: " + userid);
                           super.setRedirect(true); // setRedirect(true) 는 페이지 전환하는 것
                           super.setViewPage(request.getContextPath() + "/index.flex"); // 페이지를 보여주기 때문에 절대경로가 필요하다
                           return;
                        }
                    }
                }
            }

         super.setRedirect(false); // setRedirect(false) 는 입력된 데이터값과 함께 뷰페이지로 보내주는 것
          super.setViewPage("/WEB-INF/login/goLogin.jsp"); // 데이터와 함께 jsp로 전환되는 것이다. 
          return;
      } 
      //POST 방식으로 넘어온 것이라면
      String userid = request.getParameter("userid");
      String pwd = request.getParameter("pwd");
      String loginChk = request.getParameter("loginChk"); //자동 로그인
      System.out.println("loginChk="+loginChk);
      
      //== 클라이언트의 아이피 주소를 알아오는 것 ==// 
      String clientip = request.getRemoteAddr();
      //먼저 C:\NCS\workspace_jsp\MyMVC\src\main\webapp\JSP 파일을 실행시켰을 때 IP 주소가 제대로 출력되기위한 방법.txt에 있는 방법 실행할 것
   
      
      /*
       * System.out.println("확인용 userid : " + userid); 
       * System.out.println("확인용 pwd : "+ pwd); 
       * System.out.println("확인용 clientip : " + clientip);
       */
      
      
      Map<String, String> paraMap = new HashMap<>();
      paraMap.put("userid", userid);
      paraMap.put("pwd", pwd);
      paraMap.put("clientip", clientip);
      
      //로그인처리
      MemberVO loginuser = mdao.login(paraMap);
      
      if(loginuser != null) { 

         if(loginuser.getIdle() == 1) {   
            // 마지막으로 로그인 한것이 1년 이상 지난 경우 
               
               String message = "로그인을 한지 1년이 지나서 휴면상태로 되었습니다.\\n휴면을 풀어주는 페이지로 이동합니다!!"; // \\n은 alert의 줄바꿈이다 
               String loc = request.getContextPath()+"/index.flex";
               // 원래는 프로젝트시에 위와같이 index.up 이 아니라 휴면인 계정을 풀어주는 페이지로 URL을 잡아주어야 한다.!!!
               
               request.setAttribute("message", message);
               request.setAttribute("loc", loc);
               
               super.setRedirect(false); 
               super.setViewPage("/WEB-INF/msg.jsp");
               
               return; // 메소드 종료
         }
            
            // 로그인 성공시
            HttpSession session = request.getSession();
            // WAS 메모리에 생성되어져 있는 session 을 불러오는 것이다.
            
            session.setAttribute("loginuser", loginuser);
            // session(세션)에 로그인 되어진 사용자 정보인 loginuser 를 키이름을 "loginuser" 으로 저장시켜두는 것이다.
               System.out.println("로그인 성공: " + loginuser);
               System.out.println("세션에 저장된 loginuser: " + session.getAttribute("loginuser"));

            
            
            // 자동 로그인 - 로그인 유지 처리
            if(loginChk != null) { // 체크박스에 check를 안 한 경우 null
               Cookie cookie = new Cookie("userid", userid);
               cookie.setMaxAge(24*60*60); //24시간 유지
               cookie.setPath("/");
               response.addCookie(cookie); //클라이언트 pc 쿠키
                   cookie.setHttpOnly(true); // HttpOnly 속성 추가
                   cookie.setSecure(true); // Secure 속성 추가 (HTTPS를 사용할 경우)
            }
            
            
            if(loginuser.isRequirePwdChange()) { //true라면
               // 비밀번호를 변경한지 3개월 이상된 경우 
                    
                  String message = "비밀번호를 변경하신지 3개월이 지났습니다.\\n암호를 변경하는 페이지로 이동합니다!!";
                  String loc = request.getContextPath()+"/member/memberPwdChange.flex";

                  
                  request.setAttribute("message", message);
                  request.setAttribute("loc", loc);
                  
                  super.setRedirect(false); 
                  super.setViewPage("/WEB-INF/msg.jsp");
                  
                  return; // 메소드 종료
            }
            
            super.setRedirect(true);
            super.setViewPage(request.getContextPath() + "/index.flex"); //로그인이 성공되어지면 시작페이지에 머뭄
         
      } else {
         String message = "로그인에 실패하였습니다. 아이디 또는 비밀번호를 확인하세요.";
         String loc = request.getContextPath()+"/login/login.flex";
            
           request.setAttribute("message", message);
           request.setAttribute("loc", loc);
            
           super.setRedirect(false); 
           super.setViewPage("/WEB-INF/msg.jsp");
      }
      
   }

}