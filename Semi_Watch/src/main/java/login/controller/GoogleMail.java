package login.controller;

import java.util.Map;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

import member.controller.MySMTPAuthenticator_my;

public class GoogleMail {
	
	public void send_certification_code(Map<String, Object> userMap, Map<String, String> paraMap, String certification_code) throws Exception { 
	      
	      // 1. 정보를 담기 위한 객체
	       Properties prop = new Properties(); 
	       
	       
	       // 2. SMTP(Simple Mail Transfer Protocoal) 서버의 계정 설정
	          //    Google Gmail 과 연결할 경우 Gmail 의 email 주소를 지정 
	       prop.put("mail.smtp.user", "jhkvng9546@gmail.com"); 
	             
	      
	       // 3. SMTP 서버 정보 설정
	       //    Google Gmail 인 경우  smtp.gmail.com
	       prop.put("mail.smtp.host", "smtp.gmail.com");
	            
	       
	       prop.put("mail.smtp.port", "465");
	       prop.put("mail.smtp.starttls.enable", "true");
	       prop.put("mail.smtp.auth", "true");
	       prop.put("mail.smtp.debug", "true");
	       prop.put("mail.smtp.socketFactory.port", "465");
	       prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	       prop.put("mail.smtp.socketFactory.fallback", "false");
	       
	       prop.put("mail.smtp.ssl.enable", "true");
	       prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	       prop.put("mail.smtp.ssl.protocols", "TLSv1.2"); // MAC 에서도 이메일 보내기 가능하도록 한것임. 또한 만약에 SMTP 서버를 google 대신 naver 를 사용하려면 이것을 해주어야 함.
	         
	    /*  
	        혹시나 465 포트에 연결할 수 없다는 에러메시지가 나오면 아래의 3개를 넣어주면 해결된다.(주석 풀면됨)=맥의 경우
	        prop.put("mail.smtp.starttls.enable", "true");
	        prop.put("mail.smtp.starttls.required", "true");
	        prop.put("mail.smtp.ssl.protocols", "TLSv1.2");
	    */ 
	       
	       Authenticator smtpAuth = new MySMTPAuthenticator();
	       Session ses = Session.getInstance(prop, smtpAuth);
	          
	       // 메일을 전송할 때 상세한 상황을 콘솔에 출력한다.
	       ses.setDebug(true);
	               
	       // 메일의 내용을 담기 위한 객체생성
	       MimeMessage msg = new MimeMessage(ses);

	       // 받는 사람의 아이디
	       String userid = paraMap.get("userid");
	       
	       // 받는 사람의 이름
	       String name = (String) userMap.get("name");
	       // 제목 설정
	       String subject = name + " 회원님의 비밀번호를 찾기위한 인증코드 발송";
	       msg.setSubject(subject);
	               
	       // 보내는 사람의 메일주소
	       String sender = "jhkvng9546@gmail.com";
	       Address fromAddr = new InternetAddress(sender);
	       msg.setFrom(fromAddr); 
	               
	       // 받는 사람의 메일주소
	       Address toAddr = new InternetAddress(paraMap.get("email"));
	       msg.addRecipient(Message.RecipientType.TO, toAddr);
	       
	               
	       // 메시지 본문의 내용과 형식, 캐릭터 셋 설정
	       // ==> 추후 HTML 코드로 예쁘게 꾸며서 발송할 수 있음(div태그나 table 태그로)
	       // msg.setContent("발송된 인증코드 : <span style='font-size:14pt; color:red;'>"+certification_code+"</span>", "text/html;charset=UTF-8");
	       msg.setContent(" <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" "
	       		+ "    style=\"font-family: 'Noto Sans KR', '맑은고딕', Malgun Gothic, '돋움', Dotum, sans-serif;padding: 20px 0;font-size: 16px;color: #333;font-weight: 400;max-width:780px;width:100%;padding: 0px;margin: 0 auto;border-collapse: collapse;\"> "
	       		+ "    <tbody> "
	       		+ "      <tr> "
	       		+ "        <td> "
	       		+ "        </td> "
	       		+ "      </tr> "
	       		+ "      <tr> "
	       		+ "        <td "
	       		+ "          style=\"border-radius: 0 30px 0 30px;background: #21a39b;color: #fff;font-weight: 500;font-size: 24px;text-align: center;padding: 27px 0;\"> "
	       		+ "          TimeLess 쇼핑몰 비밀번호 찾기 안내</td> "
	       		+ "      </tr> "
	       		+ "      <tr> "
	       		+ "        <td style=\"padding: 30px 0;\"> "
	       		+ "          <p> "
	       		+ "            안녕하세요. TimeLess 쇼핑몰 입니다.</p> "
	       		+ "          <p><span>"+ name +" 님께서 요청하신 비밀번호 찾기 인증코드를 다음과 같이 안내해드립니다.</span>&nbsp;&nbsp;<br></p> "
	       		+ "          <p>아래 인증코드를 복사하여, 비밀번호 찾기를 계속 진행하여 주시기 바랍니다.</p> "
	       		+ "          <p>본인이 진행하지 않은 경우라면 고객센터(_______)로 연락 주시기 바랍니다.</p> "
	       		+ "        </td> "
	       		+ "      </tr> "
	       		+ "      <tr> "
	       		+ "        <td> "
	       		+ "          <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" "
	       		+ "            style=\"width: 100%;font-size: 14px;border-top: 1px solid #666;\"> "
	       		+ "            <colgroup> "
	       		+ "              <col style=\"width: 23%;\"> "
	       		+ "              <col> "
	       		+ "            </colgroup> "
	       		+ "            <tbody> "
	       		+ "              <tr> "
	       		+ "                <th colspan=\"1\" rowspan=\"1\" scope=\"row\" "
	       		+ "                  style=\"text-align: left;font-weight: 600;border-bottom: 1px solid #ccc;border-right: 1px #ccc;background: #f1f1f1;padding: 19px 0 19px 19px;\"> "
	       		+ "                  회원성명</th> "
	       		+ "                <td style=\"border-bottom: 1px solid #ccc;padding: 19px 0 19px 19px;\">"+ name +"</td> "
	       		+ "              </tr> "
	       		+ "              <tr> "
	       		+ "                <th colspan=\"1\" rowspan=\"1\" scope=\"row\" "
	       		+ "                  style=\"text-align: left;font-weight: 600;border-bottom: 1px solid #ccc;border-right: 1px #ccc;background: #f1f1f1;padding: 19px 0 19px 19px;\"> "
	       		+ "                  아이디</th> "
	       		+ "                <td style=\"border-bottom: 1px solid #ccc;padding: 19px 0 19px 19px;\">"+ userid +"</td> "
	       		+ "              </tr> "
	       		+ "              <tr> "
	       		+ "                <th colspan=\"1\" rowspan=\"1\" scope=\"row\" "
	       		+ "                  style=\"text-align: left;font-weight: 600;border-bottom: 1px solid #ccc;border-right: 1px #ccc;background: #f1f1f1;padding: 19px 0 19px 19px;\"> "
	       		+ "                  인증코드</th> "
	       		+ "                <td style=\"border-bottom: 1px solid #ccc;padding: 19px 0 19px 19px;\"> "
	       		+ "                  <p><span "
	       		+ "                      style=\"color: rgb(18, 52, 86); font-family: 굴림; font-size: 13px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\"><span "
	       		+ "                        style=\"color: rgb(18, 52, 86); font-family: 굴림; font-size: 13px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\">"+
	       		certification_code
	       		+ "</span></span> "
	       		+ "                  </p> "
	       		+ "                </td> "
	       		+ "              </tr> "
	       		+ "            </tbody> "
	       		+ "          </table> "
	       		+ "        </td> "
	       		+ "      </tr> "
	       		+ "      <tr> "
	       		+ "        <td style=\"padding-top: 30px;\">TimeLess는 더욱 편리한 서비스를 제공하기 위해 항상 최선을 다하겠습니다.</td> "
	       		+ "      </tr> "
	       		+ "      <tr> "
	       		+ "        <td style=\"text-align: center; padding: 40px 0 50px 0;\"> "
	       		+ "          <a href=\"\" style=\"     background-color: #21a39b; color: white;padding: 15px 25px;text-align: center; border-radius: 10px;text-decoration: none; display: inline-block; \" target=\"_blank\" title=\"새창 열림\" rel=\"noreferrer noopener\"> "
	       		+ "            TimeLess 쇼핑몰 홈페이지로 돌아가기 "
	       		+ "        </td> "
	       		+ "      </tr> "
	       		+ "    </tbody> "
	       		+ "  </table> "
	       		+ "", "text/html;charset=UTF-8");     
	       // 메일 발송하기
	       Transport.send(msg);
	       
	   }// end of public void send_certification_code(String recipient, String certification_code) throws Exception--------

	
	
	
	
	// 개인정보수정에서 이메일 인증코드 관련 메일보내기
	public void send_certification_email_code(Map<String, String> paraMap) throws Exception { 
		
		// 1. 정보를 담기 위한 객체
		Properties prop = new Properties();
       
       
		// 2. SMTP(Simple Mail Transfer Protocoal) 서버의 계정 설정
          //    Google Gmail 과 연결할 경우 Gmail 의 email 주소를 지정 
		prop.put("mail.smtp.user", "jhkvng9546@gmail.com"); 
             
      
		// 3. SMTP 서버 정보 설정
		//    Google Gmail 인 경우  smtp.gmail.com
		prop.put("mail.smtp.host", "smtp.gmail.com");
            
       
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.debug", "true");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		prop.put("mail.smtp.socketFactory.fallback", "false");
       
		prop.put("mail.smtp.ssl.enable", "true");
		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		prop.put("mail.smtp.ssl.protocols", "TLSv1.2"); // MAC 에서도 이메일 보내기 가능하도록 한것임. 또한 만약에 SMTP 서버를 google 대신 naver 를 사용하려면 이것을 해주어야 함.
         
    /*  
        혹시나 465 포트에 연결할 수 없다는 에러메시지가 나오면 아래의 3개를 넣어주면 해결된다.
       	prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.starttls.required", "true");
        prop.put("mail.smtp.ssl.protocols", "TLSv1.2");
    */ 
       
		Authenticator smtpAuth = new MySMTPAuthenticator_my();
		Session ses = Session.getInstance(prop, smtpAuth);
          
		// 메일을 전송할 때 상세한 상황을 콘솔에 출력한다.
		ses.setDebug(true);
               
		// 메일의 내용을 담기 위한 객체생성
		MimeMessage msg = new MimeMessage(ses);

		// 제목 설정
		String subject = paraMap.get("username")+" 회원님의 이메일 변경을 위한 인증코드 발송";
		msg.setSubject(subject);
               
		// 보내는 사람의 메일주소
		String sender = "jhkvng9546@gmail.com";
		Address fromAddr = new InternetAddress(sender);
		msg.setFrom(fromAddr);
               
		// 받는 사람의 메일주소
		Address toAddr = new InternetAddress(paraMap.get("newEmail"));
		msg.addRecipient(Message.RecipientType.TO, toAddr);
               
		// 메시지 본문의 내용과 형식, 캐릭터 셋 설정
		msg.setContent(" <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" "
	       		+ "    style=\"font-family: 'Noto Sans KR', '맑은고딕', Malgun Gothic, '돋움', Dotum, sans-serif;padding: 20px 0;font-size: 16px;color: #333;font-weight: 400;max-width:780px;width:100%;padding: 0px;margin: 0 auto;border-collapse: collapse;\"> "
	       		+ "    <tbody> "
	       		+ "      <tr> "
	       		+ "        <td> "
	       		+ "        </td> "
	       		+ "      </tr> "
	       		+ "      <tr> "
	       		+ "        <td "
	       		+ "          style=\"border-radius: 0 30px 0 30px;background: #21a39b;color: #fff;font-weight: 500;font-size: 24px;text-align: center;padding: 27px 0;\"> "
	       		+ "          TimeLess 쇼핑몰 이메일 변경에 따른 인증코드 안내</td> "
	       		+ "      </tr> "
	       		+ "      <tr> "
	       		+ "        <td style=\"padding: 30px 0;\"> "
	       		+ "          <p> "
	       		+ "            안녕하세요. TimeLess 쇼핑몰 입니다.</p> "
	       		+ "          <p><span>"+ paraMap.get("username") +" 님께서 요청하신 이메일 변경 인증코드를 다음과 같이 안내해드립니다.</span>&nbsp;&nbsp;<br></p> "
	       		+ "          <p>아래 인증코드를 복사하여, 이메일 변경을 계속 진행하여 주시기 바랍니다.</p> "
	       		+ "          <p>본인이 진행하지 않은 경우라면 고객센터(_______)로 연락 주시기 바랍니다.</p> "
	       		+ "        </td> "
	       		+ "      </tr> "
	       		+ "      <tr> "
	       		+ "        <td> "
	       		+ "          <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" "
	       		+ "            style=\"width: 100%;font-size: 14px;border-top: 1px solid #666;\"> "
	       		+ "            <colgroup> "
	       		+ "              <col style=\"width: 23%;\"> "
	       		+ "              <col> "
	       		+ "            </colgroup> "
	       		+ "            <tbody> "
	       		+ "              <tr> "
	       		+ "                <th colspan=\"1\" rowspan=\"1\" scope=\"row\" "
	       		+ "                  style=\"text-align: left;font-weight: 600;border-bottom: 1px solid #ccc;border-right: 1px #ccc;background: #f1f1f1;padding: 19px 0 19px 19px;\"> "
	       		+ "                  회원성명</th> "
	       		+ "                <td style=\"border-bottom: 1px solid #ccc;padding: 19px 0 19px 19px;\">"+ paraMap.get("username") +"</td> "
	       		+ "              </tr> "
	       		+ "              <tr> "
	       		+ "                <th colspan=\"1\" rowspan=\"1\" scope=\"row\" "
	       		+ "                  style=\"text-align: left;font-weight: 600;border-bottom: 1px solid #ccc;border-right: 1px #ccc;background: #f1f1f1;padding: 19px 0 19px 19px;\"> "
	       		+ "                  아이디</th> "
	       		+ "                <td style=\"border-bottom: 1px solid #ccc;padding: 19px 0 19px 19px;\">"+ paraMap.get("userid") +"</td> "
	       		+ "              </tr> "
	       		+ "              <tr> "
	       		+ "                <th colspan=\"1\" rowspan=\"1\" scope=\"row\" "
	       		+ "                  style=\"text-align: left;font-weight: 600;border-bottom: 1px solid #ccc;border-right: 1px #ccc;background: #f1f1f1;padding: 19px 0 19px 19px;\"> "
	       		+ "                  인증코드</th> "
	       		+ "                <td style=\"border-bottom: 1px solid #ccc;padding: 19px 0 19px 19px;\"> "
	       		+ "                  <p><span "
	       		+ "                      style=\"color: rgb(18, 52, 86); font-family: 굴림; font-size: 13px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\"><span "
	       		+ "                        style=\"color: rgb(18, 52, 86); font-family: 굴림; font-size: 13px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;\">"+
	       		paraMap.get("email_code")
	       		+ "</span></span> "
	       		+ "                  </p> "
	       		+ "                </td> "
	       		+ "              </tr> "
	       		+ "            </tbody> "
	       		+ "          </table> "
	       		+ "        </td> "
	       		+ "      </tr> "
	       		+ "      <tr> "
	       		+ "        <td style=\"padding-top: 30px;\">TimeLess는 더욱 편리한 서비스를 제공하기 위해 항상 최선을 다하겠습니다.</td> "
	       		+ "      </tr> "
	       		+ "      <tr> "
	       		+ "        <td style=\"text-align: center; padding: 40px 0 50px 0;\"> "
	       		+ "          <a href=\"\" style=\"     background-color: #21a39b; color: white;padding: 15px 25px;text-align: center; border-radius: 10px;text-decoration: none; display: inline-block; \" target=\"_blank\" title=\"새창 열림\" rel=\"noreferrer noopener\"> "
	       		+ "            TimeLess 쇼핑몰 홈페이지로 돌아가기 "
	       		+ "        </td> "
	       		+ "      </tr> "
	       		+ "    </tbody> "
	       		+ "  </table> "
	       		+ "", "text/html;charset=UTF-8");
		// 디자인은 html 로 만들어서 보내면 된다.(table tag 추천)
               
		// 메일 발송하기
		Transport.send(msg);
       
	}// end of public void send_certification_email_code(Map<String, String> paraMap)--------




	
	public void sendmail_checkOutFinish(String recipient, String username, String emailContents) throws Exception{
		// 1. 정보를 담기 위한 객체
		Properties prop = new Properties(); 
       
       
		// 2. SMTP(Simple Mail Transfer Protocoal) 서버의 계정 설정
          //    Google Gmail 과 연결할 경우 Gmail 의 email 주소를 지정 
		prop.put("mail.smtp.user", "jhkvng9546@gmail.com"); 
             
      
		// 3. SMTP 서버 정보 설정
		//    Google Gmail 인 경우  smtp.gmail.com
		prop.put("mail.smtp.host", "smtp.gmail.com");
            
       
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.debug", "true");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		prop.put("mail.smtp.socketFactory.fallback", "false");
       
		prop.put("mail.smtp.ssl.enable", "true");
		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		prop.put("mail.smtp.ssl.protocols", "TLSv1.2"); // MAC 에서도 이메일 보내기 가능하도록 한것임. 또한 만약에 SMTP 서버를 google 대신 naver 를 사용하려면 이것을 해주어야 함.
         
    /*  
        혹시나 465 포트에 연결할 수 없다는 에러메시지가 나오면 아래의 3개를 넣어주면 해결된다.
       	prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.starttls.required", "true");
        prop.put("mail.smtp.ssl.protocols", "TLSv1.2");
    */ 
       
		Authenticator smtpAuth = new MySMTPAuthenticator();
		Session ses = Session.getInstance(prop, smtpAuth);
          
		// 메일을 전송할 때 상세한 상황을 콘솔에 출력한다.
		ses.setDebug(true);
               
		// 메일의 내용을 담기 위한 객체생성
		MimeMessage msg = new MimeMessage(ses);

		// 제목 설정
		String subject = "Timeless : "+username+"님의 주문 내역";
		msg.setSubject(subject);
               
		// 보내는 사람의 메일주소
		String sender = "jhkvng9546@gmail.com";
		Address fromAddr = new InternetAddress(sender);
		msg.setFrom(fromAddr);
               
		// 받는 사람의 메일주소
		Address toAddr = new InternetAddress(recipient);
		msg.addRecipient(Message.RecipientType.TO, toAddr);
               
		// 메시지 본문의 내용과 형식, 캐릭터 셋 설정
		msg.setContent("<div style='font-size:14pt; color:red;'>"+emailContents+"</div>", "text/html;charset=UTF-8");
		// 디자인은 html 로 만들어서 보내면 된다.(table tag 추천)
               
		// 메일 발송하기
		Transport.send(msg);
		
	}// end of public void sendmail_checkOutFinish(String email, String username, String emailContents



}

