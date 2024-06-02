<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String ctxPath = request.getContextPath();
%>
    
<%-- 강지훈 제작 페이지 --%>

<jsp:include page="../header1.jsp"></jsp:include>

<jsp:include page="../header2_user.jsp"></jsp:include>

<style type="text/css">

img.userimg_size{
	width: 50px;
	height: 50px;
	object-fit: cover;
	border-radius: 50%;
}




</style>

<script type="text/javascript" src="<%= ctxPath%>/js/member/memberInfoChange.js"></script>

<script type="text/javascript">

$(document).ready(function(){
	//사진 변경 버튼 누르면
	$("button#change_img").click(function(){
	    
	    $("tr#profile_image_area").hide();
	    $("tr#change_profile_image_area").show();

	    document.getElementById("previewImg").src = "<%= ctxPath%>/images/member/${sessionScope.loginuser.userimg}";

	}); // end of $("button#change_img").click(function() -------


	// 사진 변경 취소 버튼 누르면
	$("button:reset[id='imgcancle']").click(function(){

	    $("tr#change_profile_image_area").hide();
	    $("tr#profile_image_area").show();

	});
	
	
});// end of $(document).ready(function()--------------


</script>

				<div class="mb-3">
	            	<span class="h4" style="font-weight: bold;">기본회원정보</span>&nbsp;&nbsp;<span class="h5" style="font-weight: bold; color: #69707a;">필수</span>
	            </div>
				<table class="table" id="userinfo" style="width: 100%;">
					<colgroup>
						<col style="width: 15%;">
						<col style="width: 55%;">
						<col style="width: 30%;">
					</colgroup>
					<%-- contextPath 정보 --%>
					<input id="ctxPath" type="hidden" value="<%= ctxPath%>"/>
					
					<%-- 사진 파트--%>
					<tr id="profile_image_area">
						<th scope="row">사진</th>
                        <td>  
							<div class="mb-2">
								<img class="userimg_size" src="<%= ctxPath%>/images/member/${sessionScope.loginuser.userimg}" />
							</div>
                            
                            <p>회원님을 알릴 수 있는 사진을 등록해 주세요.</p>
                        </td>
                        <td>
                            <button class="btn btn-sm btn-outline-dark change_btn" type="button" id="change_img">사진 변경</button>
                        </td>
                    </tr>
                    <%--사진 수정 할 경우 기본 숨김--%>
                    <tr id="change_profile_image_area">
                        <th scope="row">사진2 변경 시 보여지게 함</th>
                        <td>
                        	<form name="imgForm">
	                            <div class="mb-2">
									<img class="userimg_size previewImg" id="previewImg" />
								</div>
	                            
	                            <p class="mb-2">회원님을 알릴 수 있는 사진을 등록해 주세요.</p>
	                            <div class="mb-3">
	                                <label class="btn btn-sm btn-outline-secondary selectPicture" for="profile_image" id="selectPicture">사진 선택</label>
	                                <input class="previewImg" id="profile_image" name="userimg" type="file" accept='image/jpeg, image/png, .jpg, .png' style="display: none;">
	                            </div>
	                            <input name="infoUpdate" value="img" type="hidden"/>
								<input name="userid" value="${sessionScope.loginuser.userid}" type="hidden" />
                            </form>
                        </td>
                        <td>
                            <button class="btn btn-sm btn-outline-secondary twobtn" id="imgcancle" type="reset" >취소</button>
							<button class="btn btn-sm btn-outline-dark twobtn" type="button" id="imgUpdate" onclick="imgUP()" >완료</button>
							
                        </td>
                    </tr>

					<%-- 아이디 파트 --%>
					<tr id="userid_area">
                        <th scope="row">아이디</th>
                        <td colspan="2" id="userid">${sessionScope.loginuser.userid}</td>
                    </tr>

					<%-- 비밀번호 파트 --%>
					<tr id="password_area">
                        <th scope="row">비밀번호</th>
                        <td id="cpwdview">********</td>
                        <td>
                            <button class="btn btn-sm btn-outline-dark change_btn" type="button" id="change_pwd">비밀번호 변경</button>
                        </td>
                    </tr>
					<%-- 비밀번호 변경 누를 경우 기본 숨김 --%>
					<tr id="change_password_area">
                        <th scope="row">비밀번호 변경</th>
                        <td colspan="2">
							<form name="pwdForm">
								<div class="mb-2" style="display: flex;">
									<label for="password" style="width: 18%;">현재 비밀번호</label>
									<div id="password_div" style="display: flex;">
										<input id="password" type="password">
									</div>
									&nbsp;&nbsp;<span id="pwd_wrong"></span>
								</div>
								<div class="input mb-2" style="display: flex;">
									<label for="newPassword" style="width: 18%;">신규 비밀번호</label>
									<div id="newPassword_div" style="display: flex;">
										<input name="newPassword" id="newPassword" maxlength="20" type="password" />
									</div>
									&nbsp;&nbsp;<span id="pwd_check"></span>
								</div>
								<div class="mb-2" style="display: flex;">
									<label for="confirmPassword" style="width: 18%;">신규 비밀번호 재 입력</label>
									<div id="confirmPassword_div" style="display: flex;">
										<input class="n-input" id="confirmPassword" maxlength="20" type="password">
										<img type="button" src="<%=ctxPath %>/images/eye-show.png" id="viewEye"/>
									</div>
									&nbsp;&nbsp;<span id="pwd_check2"></span>
									
								</div>
								<%-- 올바르게 입력했을 때만 완료 버튼 활성화 --%>
								<div class="mb-3">
									<button class="btn btn-sm btn-outline-secondary twobtn" type="reset" id="pwdcancle">취소</button>
									<button class="btn btn-sm btn-outline-dark twobtn" type="button" id="pwdUpdate" onclick="pwdUP()">완료</button>
								</div>
								<input name="infoUpdate" value="pwd" type="hidden"/>
								<input name="userid" value="${sessionScope.loginuser.userid}" type="hidden" />
							</form>
                        </td>
                    </tr>

					<%-- 이름 파트(변경 불가) --%>
					<tr id="username">
                        <th scope="row">이름</th>
                        <td colspan="2" id="username">${sessionScope.loginuser.username}</td>
                    </tr>

					<%-- 이메일 파트 --%>
					<tr id="email_area">
                        <th scope="row">이메일</th>
                        <td>
                            <span id="currentEmail">${sessionScope.loginuser.email}</span>
                        </td>
                        <td>
                            <button class="btn btn-sm btn-outline-dark change_btn" type="button" id="change_email">이메일 변경</button>
                        </td>
                    </tr>
                    <%--이메일 인증 기본 숨김 --%>
                    <tr id="change_email_area">
                        <th scope="row">이메일 변경</th>
                        <td colspan="2">
                            <form name="emailForm">
                                <p class="mb-2">메일주소 입력 후 인증하기 버튼을 누르시면, 회원님의 이메일로 이메일 인증 번호가 적힌메일이 발송됩니다.<br>
									아래에 인증 번호를 입력하시면 인증이 완료됩니다.
								</p>
                                <div class="emailbtn mb-1" >
                                    <input name="newEmail" id="newEmail" maxlength="50" placeholder="이메일 주소 입력" type="text" style="margin-right: 10px;">
                                    <button class="btn btn-sm btn-outline-dark" id="send_authentication_email" type="button">인증번호 발송</button>
                                    &nbsp;&nbsp;<span id="email_check"></span>
                                </div>
                                <div class="emailbtn mb-2" id="email_authTemp">
                                    <input name="emailAuthTempKey" id="email_authTempKey" placeholder="인증번호 입력" type="text" style="margin-right: 10px;">
                                    <button class="btn btn-sm btn-outline-dark" id="authTempKey_check" type="button">인증</button>
                                    &nbsp;&nbsp;<span id="authTempKey_checkMent"></span>
                                </div>
                                <div class="mb-3">
                                    <button class="btn btn-sm btn-outline-secondary twobtn" type="reset" id="emailcancle">취소</button>
                                    <button disabled class="btn btn-sm btn-outline-dark twobtn" id="submit_email" type="button" onclick="emailUP()">완료</button>
                                </div>
                                <input name="infoUpdate" id="infoUpdateE" value="email" type="hidden"/>
								<input name="userid" value="${sessionScope.loginuser.userid}" type="hidden" />
								<input name="newEmailSave" id="newEmailSave" value="" type="hidden"/>
                            </form>
                        </td>
                    </tr>

					<%-- 전화번호 파트 --%>
					<tr id="mobile_area">
                        <th scope="row">휴대전화</th>
                        <td>
							<span id="mobile"><input id="mobile" type="hidden" value="${sessionScope.loginuser.mobile}"/> </span>
                        </td>
                        <td>
                            <button class="btn btn-sm btn-outline-dark change_btn" type="button" id="change_mobile">휴대전화 변경</button>
                        </td>
                    </tr>
                    <%-- 전화번호 변경 파트 --%>
                    <tr id="change_mobile_area">
                        <th scope="row">휴대전화 변경</th>
                        <td colspan="2">
                            <form name="mobileForm">
                                <p class="mb-2">전화번호 입력 후 인증하기 버튼을 누르시면, 회원님의 전화번호로 인증 번호가 발송됩니다.<br>
									인증 번호를 입력하시면 인증이 완료됩니다.
								</p>
                                <div class="mobilebtn mb-1" >
                                    <input name="newMoblie" id="newMoblie" maxlength="11" placeholder="전화번호 입력(숫자만)" type="text" style="margin-right: 10px;">
                                    <button disabled class="btn btn-sm btn-outline-dark" id="send_authentication_mobile" type="button">인증번호 발송</button>
                                    &nbsp;&nbsp;<span id="mobile_check"></span>
                                </div>
                                <div class="mobilebtn mb-2" id="mobile_authTemp">
                                    <input name="mobileAuthTempKey" id="mobile_authTempKey" placeholder="인증번호 입력" type="text" style="margin-right: 10px;">
                                    <button class="btn btn-sm btn-outline-dark" id="mobileAuthTempKey_check" type="button">인증</button>
                                    &nbsp;&nbsp;<span id="mobileAuthTempKey_checkMent"></span>
                                </div>
                                <div class="mb-3">
                                    <button class="btn btn-sm btn-outline-secondary twobtn" type="reset" id="mobilecancle">취소</button>
                                    <button disabled class="btn btn-sm btn-outline-dark twobtn" id="submit_mobile" type="button" onclick="mobileUP()">완료</button>
                                </div>
                                <input name="infoUpdate" id="infoUpdateM" value="mobile" type="hidden"/>
								<input name="userid" value="${sessionScope.loginuser.userid}" type="hidden" />
								<input name="newMoblieSave" id="newMoblieSave" value="" type="hidden"/>
                            </form>
                        </td>
                    </tr>

					<%-- 주소 파트 --%>
					<tr id="post_area">
                        <th scope="row">주소</th>
                        <td>
							<div class="mb-2 p-flex">
								<div style="width: 90px;">우편번호</div>
								<div >${sessionScope.loginuser.postcode}</div>
							</div>
							<div class="mb-2 p-flex">
								<div style="width: 90px;">주소명</div>
								<div >${sessionScope.loginuser.address}&nbsp;${sessionScope.loginuser.extra_address}</div>
							</div>
							<div class="mb-2 p-flex">
								<div style="width: 90px;">상세주소</div>
								<div >${sessionScope.loginuser.detail_address}</div>
							</div>
							
                        </td>
                        <td>
                            <button class="btn btn-sm btn-outline-dark change_btn" type="button" id="change_post">주소 변경</button>
                        </td>
                    </tr>

					<%-- 주소 변경 클릭 시 기본 숨김 --%>
					<tr id="change_post_area">
                        <th scope="row">주소 변경</th>
                        <td colspan="2">
	                        <form name="postForm" class="postForm">
								<div class="mb-2 p-flex">
									<div class="postname">우편번호</div>
									<input type="text" name="postcode" id="postcode" size="10" maxlength="10" placeholder="우편번호" style="margin-right: 15px;" value=""/>
									<button class="btn btn-sm btn-outline-secondary" type="button" id="findpost">우편번호 찾기</button>
								</div>
								<div class="mb-2 p-flex">
									<div class="postname">주소명</div>
									<input type="text" name="address" id="address" size="40" maxlength="250" placeholder="주소명" value="" />
									<%-- 주소와 xx동 나오는 값 나누어 저장 --%>
									<input type="hidden" name="pcode" id="postcodeinput" value="" />
									<input type="hidden" name="addr" id="addrinput" value="" />
									<input type="hidden" name="extraAddr" id="extraAddrinput" value="" />
									
								</div>
								<div class="mb-3 p-flex">
									<div class="postname">상세주소</div>
									<input type="text" name="addressDetail" id="addressDetail" size="40" maxlength="200" placeholder="상세주소를 입력하세요" value=""/>
								</div>
								<div class="mb-3">
									<button class="btn btn-sm btn-outline-secondary twobtn" type="reset" id="postcancle">취소</button>
									<button class="btn btn-sm btn-outline-dark twobtn" type="button" id="submit_post" onclick="postUP()">완료</button>
								</div>
								<input name="infoUpdate" value="post" type="hidden"/>
								<input name="userid" value="${sessionScope.loginuser.userid}" type="hidden" />
							</form>
                        </td>

                    </tr>
				</table>
				
<jsp:include page="../footer2_user.jsp"></jsp:include>


<jsp:include page="../footer.jsp"></jsp:include>