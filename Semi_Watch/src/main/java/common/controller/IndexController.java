package common.controller;



import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class IndexController extends AbstractController {

	
	// 메소드가 호출될때마다 ProductDAO pdao = new ProductDAO_imple(); 하는건 낭비이기 때문에!!!! 아래방법을 사용한다!! 
	// ~~~.up (init) 일때만 기본생성자를 호출하고 그외에 경우에는 다시 호출하지않기때문에 객체생성(new ProductDAO_imple()은  ) 복습... 
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/index.jsp");
		// super.setViewPage("/template_orgin.jsp"); // 확인했다가 지웠음
		
	}

}
