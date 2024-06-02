package member.controller;

import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import member.model.jh_3_MemberDAO;
import member.model.jh_3_MemberDAO_imple;
import member.model.ky_1_MemberDAO;
import member.model.ky_1_MemberDAO_imple;
import shop.model.jh_3_ProductDAO;
import shop.model.jh_3_ProductDAO_imple;

public class MemberInfoChange extends AbstractController {
	
	private jh_3_MemberDAO mdao = null;
	
	public MemberInfoChange() {
		mdao = new jh_3_MemberDAO_imple();
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if(checkLogin(request)) {
			HttpSession session = request.getSession();
			MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
			String userid = (String) loginuser.getUserid();
			
			// 유저 이미지 관련 내용
			// 1. 첨부되어진 파일을 디스크의 어느 경로에 업로드 할 것인지 경로를 설정
			ServletContext svlCtx = session.getServletContext();
            String uploadFileDir = svlCtx.getRealPath("/images");
            
            String userimg = null;
            
            // 시스템 파일 덮어쓰기 방지용
            String userimg_systemFileName = null;
            // 사용자가 올린 파일이름
            String userimg_originFileName = null;
            
            // 추가이미지 파일의 개수
            String attachCount = request.getParameter("attachCount");
			
			
			
			
			// 가져와야할 데이터
			// 장바구니 건수, 리뷰건수..
			
			Map<String, String> cnt = mdao.get_cart_review_cnt(userid);
			
			
			request.setAttribute("userid", userid);
			session.setAttribute("cnt", cnt);
			
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/member/memberInfoChange.jsp");
		}
		else {
			super.setRedirect(true);
			super.setViewPage(request.getContextPath()+"/login/login.flex");
		}
		
		
		

	}

}
