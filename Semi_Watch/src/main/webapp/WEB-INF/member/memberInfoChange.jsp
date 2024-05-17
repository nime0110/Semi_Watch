<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String ctxPath = request.getContextPath();
%>
    
<%-- 강지훈 제작 페이지 --%>

<jsp:include page="../header1.jsp"></jsp:include>

<%-- 회원정보수정 관련 js --%>
<script type="text/javascript" src="<%= ctxPath%>/js/member/memberInfoChange.js"></script>

<%-- 회원정보수정 관련 css --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/member/memberInfoChange.css" />

<script type="text/javascript">
$(document).ready(function() {
    // 비밀번호 표시 버튼을 클릭했을 때의 동작을 정의합니다.
    $('img#viewEye').click(function(e) {
        // 버튼의 부모
        const parent = $(e.target).parent();
        const passwordInput = parent.find('input#confirmPassword');
        
        if (passwordInput.attr('type') == 'password') {
            $(e.target).attr("src","<%=ctxPath%>/images/eye-show.png");
        } else {
            $(e.target).attr("src","<%=ctxPath%>/images/eye-hide.png");
        }
    });
      
    
});// end of $(document).ready(function() ----------
		
</script>

<%-- 회원정보 내용 시작 --%>
<body>
	<%-- 상단 바 시작 --%>
	<div class="pt-3" id="topBar">
		<div >
			<h2 id="myPage">My Page</h2> 
		</div>
		<div class="row text-center" style="padding: 10px 0 20px 0;">
			<div class="col-xl-5" style="border: solid 1px blue; padding: 20px 0;">
				회원아이디
			</div>
			<div class="col" style="border: solid 1px blue;">
				<a class="nav-link" href="#" style="color: white;">
					장바구니
					<div>5 건</div>
				</a> 
			</div>
			<div class="col" style="border: solid 1px blue;">
				<a class="nav-link" href="#" style="color: white;">
					포인트
					<div>3000</div>
				</a>
			</div>
			<div class="col" style="border: solid 1px blue; ">
				<a class="nav-link" href="#" style="color: white;">
					쿠폰
					<div>5 개</div>
				</a>
			</div>
			<div class="col" style="border: solid 1px blue;">
				<a class="nav-link" href="#" style="color: white;">
					후기
					<div>5 개</div>
				</a>
			</div>
			
		</div>
	</div>
	<%-- 상단 바 끝 --%>

	
	<div class="container-fluid">
		<div class="row">
			<%-- 왼쪽 사이드 메뉴 시작--%>
	        <div class="col-xl-2" id="subject" style="border: solid 1px red" >
	            <nav class="navbar sticky-top">
	            	<div style="border: solid 1px red">
				    	<ul class="navbar-nav mt-3" id="menu">
				    		<li class="mb-1">
				      			<span class="h5" id="menu_first">나의 쇼핑정보</span>
				      		</li>
				      		<li>
				        		<a class="nav-link" href="#">주문배송조회</a>
				      		</li>
				      		<li>
				        		<a class="nav-link" href="#">취소/교환/반품 내역</a>
				      		</li>
				      		<li class="mb-4">
				        		<a class="nav-link" href="#">상품 리뷰</a>
				      		</li>

				      		<li>
				      			<span class="h5" id="menu_first">나의 계정설정</span>
				      		</li>
				      		<li>
				        		<a class="nav-link" href="#">회원정보수정</a>
				      		</li>
				      		<li>
				        		<a class="nav-link" href="#">쿠폰</a>
				      		</li>
				      		<li>
				        		<a class="nav-link" href="#">마일리지</a>
				      		</li>
				    	</ul>
				  	</div>
	            </nav>
	        </div>
			<%-- 왼쪽 사이드 메뉴 끝 --%>

			<div class="col-xl-9 mt-4">
				<div class="mb-3">
	            	<span class="h4" style="font-weight: bold;">기본회원정보</span>&nbsp;&nbsp;<span class="h5" style="font-weight: bold; color: #69707a;">필수</span>
	            </div>
				<table class="table" id="userinfo" style="width: 100%;">
					<colgroup>
						<col style="width: 15%;">
						<col style="width: 40%;">
						<col style="width: 45%;">
					</colgroup>

					<%-- 사진 파트--%>
					<tr >
						<th scope="row">사진</th>
                        <td>  
							<div>
								이미지
							</div>
                            
                            <p>회원님을 알릴 수 있는 사진을 등록해 주세요.</p>
                        </td>
                        <td>
                            <button class="btn btn-sm btn-outline-dark" type="button" id="change_btn">사진 변경</button>
                        </td>
                    </tr>
                    <%--사진 수정 할 경우 기본 숨김--%>
                    <tr id="change_profile_image_area">
                        <th scope="row">사진2 변경 시 보여지게 함</th>
                        <td>
                            <div>
								이미지2
							</div>
                            
                            <p class="mb-2">2회원님을 알릴 수 있는 사진을 등록해 주세요.</p>
                            <div class="mb-3">
                                <label class="btn btn-sm btn-outline-secondary selectPicture" for="profile-image" id="selectPicture">사진 선택</label>
                                <input id="profile-image" type="file" style="display: none;">
                                <button class="btn btn-sm btn-outline-secondary" type="button" >기본이미지로 변경</button>
                            </div>
                        </td>
                        <td>
                            <button class="btn btn-sm btn-outline-secondary " type="button" id="twobtn">취소</button>
							<button class="btn btn-sm btn-outline-dark" type="button" id="twobtn">완료</button>
                        </td>
                    </tr>

					<%-- 아이디 파트 --%>
					<tr id="userid">
                        <th scope="row">아이디</th>
                        <td colspan="2">example</td>
                    </tr>

					<%-- 비밀번호 파트 --%>
					<tr id="password_area">
                        <th scope="row">비밀번호</th>
                        <td id="cpwdview"><input id="currentpwd" type="hidden" value="1232132"/></td>
                        <td>
                            <button class="btn btn-sm btn-outline-dark" type="button" id="change_btn" onclick="change_password()">비밀번호 변경</button>
                        </td>
                    </tr>
					<%-- 비밀번호 변경 누를 경우 기본 숨김 --%>
					<tr id="change_password_area">
                        <th scope="row">비밀번호 변경 시 보여지게 함</th>
                        <td colspan="2">
							<div>
								<div class="input mb-2" style="display: flex;">
									<label for="password" style="width: 18%;">현재 비밀번호</label>
									<div id="password_div" style="display: flex;">
										<input id="password" type="password">
									</div>
									&nbsp;&nbsp;<span>양식에 맞게 입력했는지 확인 멘트</span>
								</div>
								<div class="input mb-2" style="display: flex;">
									<label for="newPassword" style="width: 18%;">신규 비밀번호</label>
									<div id="newPassword_div" style="display: flex;">
										<input id="newPassword" maxlength="30" type="password">
									</div>
									&nbsp;&nbsp;<span id="newpwd_check">양식에 맞게 입력했는지 확인 멘트</span>
								</div>
								<div class="mb-2" style="display: flex;">
									<label for="confirmPassword" style="width: 18%;">신규 비밀번호 재 입력</label>
									<div id="confirmPassword_div" style="display: flex;">
										<input class="n-input" id="confirmPassword" maxlength="30" type="password">
										<img type="button" src="<%=ctxPath %>/images/eye-show.png" id="viewEye"/>
									</div>
									&nbsp;&nbsp;<span id="newpwd_check2">비밀번호가 맞지않습니다. 확인멘트</span>
									
								</div>
								<%-- 올바르게 입력했을 때만 완료 버튼 활성화 --%>
								<div class="mb-3">
									<button class="btn btn-sm btn-outline-secondary" type="button" id="twobtn">취소</button>
									<button class="btn btn-sm btn-outline-dark" type="button" id="twobtn">완료</button>
								</div>
							</div>
                        </td>
                    </tr>

					<%-- 이름 파트(변경 불가) --%>
					<tr id="userid">
                        <th scope="row">이름</th>
                        <td colspan="2">홍길동</td>
                    </tr>

					<%-- 이메일 파트 --%>
					<tr id="email_area">
                        <th scope="row">이메일</th>
                        <td>
                            <span id="currentEmail">example@naver.com</span>
                        </td>
                        <td>
                            <button class="btn btn-sm btn-outline-dark" type="button" id="change_btn" onclick="change_email()">이메일 변경</button>
                        </td>
                    </tr>
                    <%--이메일 인증 기본 숨김 --%>
                    <tr id="change_email_area">
                        <th scope="row">이메일 변경 시 보여지게 함</th>
                        <td colspan="2">
                            <div class="my-info-modify">
                                <p class="mb-2">메일주소 입력 후 인증하기 버튼을 누르시면, 회원님의 이메일로 이메일 인증 번호가 적힌메일이 발송됩니다.<br>
									아래에 인증 번호를 입력하시면 인증이 완료됩니다.
								</p>
                                <div class="input mb-1" style="display: flex;">
                                    <input id="email" maxlength="50" placeholder="이메일 주소 입력" type="text" style="margin-right: 10px;">
                                    <button class="btn btn-sm btn-outline-dark" id="send-authentication-email" type="button">인증번호 발송</button>
                                </div>
                                <div class="input mb-2">
                                    <input id="email-authTempKey" placeholder="인증번호 입력" type="text">
                                </div>
                                <div class="mb-3">
                                    <button class="btn btn-sm btn-outline-secondary" type="button" id="twobtn">취소</button>
                                    <button class="btn btn-sm btn-outline-dark" type="button" id="twobtn">완료</button>
                                </div>
                            </div>
                        </td>
                    </tr>

					<%-- 전화번호 파트 --%>
					<tr id="mobile-area">
                        <th scope="row">휴대전화</th>
                        <td>
							<span>010-1234-5678</span>
                        </td>
                        <td>
                            <button class="btn btn-sm btn-outline-dark" type="button" id="change_btn">휴대전화 변경</button>
                        </td>
                    </tr>

					<%-- 주소 파트 --%>
					<tr id="post-area">
                        <th scope="row">주소</th>
                        <td>
							<div class="mb-2" style="display: flex;">
								<div style="width: 15%;">우편번호</div>
								<div >16688</div>
							</div>
							<div class="mb-2" style="display: flex;">
								<div style="width: 15%;">주소명</div>
								<div >경기도 어쩌구 저쩌구</div>
							</div>
							<div class="mb-2" style="display: flex;">
								<div style="width: 15%;">상세주소</div>
								<div >아무개 아파트 495동 2903호</div>
							</div>
							
                        </td>
                        <td>
                            <button class="btn btn-sm btn-outline-dark" type="button" id="change_btn">주소 변경</button>
                        </td>
                    </tr>

					<%-- 주소 변경 클릭 시 기본 숨김 --%>
					<tr id="post-area">
                        <th scope="row">주소 변경 시 보여지게 함</th>
                        <td colspan="2">
							<div class="mb-2" style="display: flex;">
								<div style="width: 7%;">우편번호</div>
								<input type="text" id="postcode" size="6" maxlength="5" disabled style="margin-right: 10px;"/>
								<button class="btn btn-sm btn-outline-secondary" type="button">우편번호 찾기</button>
							</div>
							<div class="mb-2" style="display: flex;">
								<div style="width: 7%;">주소명</div>
								<input type="text" size="40" maxlength="200" placeholder="주소명" disabled />
							</div>
							<div class="mb-3" style="display: flex;">
								<div style="width: 7%;">상세주소</div>
								<input type="text" size="40" maxlength="200" placeholder="상세주소를 입력하세요" />
							</div>
							<div class="mb-3">
								<button class="btn btn-sm btn-outline-secondary" type="button" id="twobtn">취소</button>
								<button class="btn btn-sm btn-outline-dark" type="button" id="twobtn">완료</button>
							</div>
                        </td>

                    </tr>
				</table>
			</div>
	    </div>
	</div>

<%-- 회원정보 내용 시작 --%>


<jsp:include page="../footer.jsp"></jsp:include>