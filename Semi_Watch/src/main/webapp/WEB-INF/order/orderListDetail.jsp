<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<%
	String ctxPath = request.getContextPath();
%>
    
<%-- 강지훈 제작 페이지 --%>

<jsp:include page="../header1.jsp"></jsp:include>

<jsp:include page="../header2_user.jsp"></jsp:include>

<style type="text/css">

tr.subject{
	height : 75px; 
	font-size: 18pt; 
	font-weight: bold;
	border-bottom: solid 2px gray;
}

img.pimg {
	width: 100px;
	height: 100px;
	object-fit: cover;
	border: solid 1px black;
	
}

div.areaP{
	margin: 100px 0;


}


td#deliverS{
	font-size: 14pt;
	font-weight: bold;

}

div.d_subject{
	font-size: 18pt; 
	font-weight: bold;
}

hr.lineS{
	border: solid 1px black;
	margin: 10px 0 0 0;
}

div.heightC{
	height: 60px;
	/*vertical-align: middle;*/
	align-items: center;
	font-size: 13pt;
	display:flex;

}

div.heightB{
	margin: 60px 0;
}

div.heightP{
	margin: 8px 0;
}


.tableLineB{
	border-bottom: solid 1px #696969;

}
.tableLineR{
	border-right: solid 2px #A9A9A9;
}


</style>


<script type="text/javascript">
$(document).ready(function() {
	// 배송지 연락처
	let mobile = $("div#mobile").text();
	
	const h1 = mobile.slice(0,3);   
    const h2 = mobile.slice(3,8);   
    const h3 = mobile.slice(8);
    
    mobile = h1+"-"+h2+"-"+h3;

    $("div#mobile").text(mobile);
    
    
    // 구매자 연락처
    let userMobile = $("div#userMobile").text();
    
    const h1 = mobile.slice(0,3);   
    const h2 = mobile.slice(3,8);   
    const h3 = mobile.slice(8);
    
    userMobile = h1+"-"+h2+"-"+h3;

    $("div#userMobile").text(userMobile);
    
    
    
});// end of $(document).ready(function() ----------
	
</script>

				<div>
	            	<div class="d_subject">주문상품정보</div>
               		<hr class="lineS">
	            </div>
				<table id="userinfo" style="width: 100%;">
					<colgroup>
						<col style="width: 55%;">
						<col style="width: 20%;">
						<col style="width: 25%;">
					</colgroup>
					<tr class="text-center subject">
						<td scope="row">
							상품정보
						</td>
                        <td>  
							진행상태
                        </td>
                        <td class="text-center">
                            구매후기
                        </td>
                    </tr>
                    
                    <%-- 상품정보 및 배송상태 리뷰 쓰는 버튼 생성 --%>
                    <c:forEach var="ordList" items="${requestScope.ordDetail_List}" varStatus="">
                    	<tr>
							<td style="border: solid 1px green; display:flex;">
								<a>
									<img class="pimg" src="<%=ctxPath %>/images/product/${ordList.pdimg1}" />
								</a>
								<div class="ml-4 pt-2">
									<p>${ordList.brand}</p>
									<p>${ordList.pdname}</p>
									<p>${ordList.color} <span>구매수량 : ${ordList.order_qty}</span></p>
									<p><fmt:formatNumber pattern="###,###" value="${ordList.saleprice}"/>&nbsp;원</p>
									<input type="hidden" value="${ordList.pdno}"/>
								</div>
							</td>
	                        <td id="deliverS" align="center" style="border: solid 1px red;">  
								<c:choose>
		            				<c:when test="${ordList.delivery_status == '1'}">주문완료</c:when>
		            				<c:when test="${ordList.delivery_status == '2'}">배송중</c:when>
		            				<c:otherwise>배송완료</c:otherwise>
	            				</c:choose>
	                        </td>
	                        <td class="text-center" style="border: solid 1px blue;">
	                            <button class="btn btn-sm btn-outline-dark change_btn" type="button" id="change_btn">리뷰 작성</button>
	                        </td>
	                    </tr>
	                    
	                    <%-- 일단 숨김 성심님이 만든거 적용한다함
	                    <tr id="writeReviewArr">
	                    	<td scope="row" colspan="2">
	                    		<textarea class="form-control text-left" maxlength="40" style="resize: none;"></textarea>
	                    	</td>
	                    	<td class="text-center">
	                    		<button class="btn btn-sm btn-outline-secondary twobtn" type="reset" id="cancle">취소</button>
								<button class="btn btn-sm btn-outline-dark twobtn" type="button" id="saveReview">저장</button>
	                    	</td>
	                    </tr>
	                     --%>
	                     
	                    <%-- 공백 --%>
                    	<tr>
	                    	<td colspan="3">
	                    		<div class="heightP"></div>
	                    	</td>
                    	</tr>

                    </c:forEach>
                    
                    <%-- 공백 --%>
                    <tr>
                    	<td colspan="3">
                    		<div class="heightB"></div>
                    	</td>
                    </tr>
                    
                    
                    
                    <%-- 구매자 정보 --%>
                    <tr>
                    	<td colspan="3">
                    		<div class="d_subject" >구매자 정보</div>
                    		<hr class="lineS">
                    		<div class="heightC tableLineB">
                    			<div class="col-2">이름</div>
                    			<div class="col-4 tableLineR">${sessionScope.loginuser.username}</div>
                    			<div class="col-2 text-center">연락처</div>
                    			<div class="col-4" id="userMobile">${sessionScope.loginuser.mobile}</div>
                    		</div>
                    		<div class="heightC tableLineB">
                    			<div class="col-2">이메일</div>
                    			<div class="col">${sessionScope.loginuser.email}</div>
                    		</div>
                    	</td>
                    </tr>
                    
                    
                    <%-- 공백 --%>
                    <tr>
                    	<td colspan="3">
                    		<div class="heightB"></div>
                    	</td>
                    </tr>
                    
                    
                    <%-- 결제정보 --%>
                    <tr>
                    	<td colspan="3">
                    		<div class="d_subject">결제정보</div>
                   			<hr class="lineS">
                    		<div class="heightC tableLineB">
                    			<div class="col-5">주문금액</div>
                    			<div class="col text-right" style="font-size: 18pt; font-weight: bold;"><fmt:formatNumber pattern="###,###" value="${requestScope.ordInfo.total_price}"/>&nbsp;원</div>
                    		</div>
                    	</td>
                    </tr>
                    
                    
                    <%-- 공백 --%>
                    <tr>
                    	<td colspan="3">
                    		<div class="heightB"></div>
                    	</td>
                    </tr>
                    
                    
                    <%-- 배송지정보 --%>
                    <tr class="areaP">
                    	<td colspan="3">
                    		<div class="d_subject">배송지 정보</div>
                    		<hr class="lineS">
                    		<div class="heightC tableLineB">
                    			<div class="col-2">받는사람</div>
                    			<div class="col-4 tableLineR">${requestScope.ordInfo.name}</div>
                    			<div class="col-2 text-center">연락처</div>
                    			<div class="col-4" id="mobile">${requestScope.ordInfo.mobile}</div>
                    		</div>
                    		<div class="heightC tableLineB">
                    			<div class="col-2">주소</div>
                    			<div class="col">${requestScope.ordInfo.postcode}&nbsp;&nbsp;${requestScope.ordInfo.address}</div>
                    		</div>
                    		<div class="heightC tableLineB">
                    			<div class="col-2">배송요청사항</div>
                    			<div class="col">${requestScope.ordInfo.msg}</div>
                    		</div>
                    	</td>
                    </tr>
				</table>
				
<jsp:include page="../footer2_user.jsp"></jsp:include>


<jsp:include page="../footer.jsp"></jsp:include>