package member.controller;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MySMTPAuthenticator_my extends Authenticator{
	
	@Override
	public PasswordAuthentication getPasswordAuthentication() {
		
		// Gmail 의 경우 @gmail.com 을 제외한 아이디만 입력한다.
		return new PasswordAuthentication("jhkvng9546","ftlc dyst xqbs daoo");
		// PasswordAuthentication("자기메일계정아이디","jrkx nfqz lxya vuen");
		// "nduxpuhmhxeiitha" 은 Google에 로그인 하기위한 앱비밀번호 이다.
	}
}
