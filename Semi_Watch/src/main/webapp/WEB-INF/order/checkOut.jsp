<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
    
<%
	String ctxPath = request.getContextPath();
%>
    
<%-- 강지훈 제작 페이지 --%>
    
<jsp:include page="../header1.jsp"></jsp:include>

<%-- 결제페이지 관련 js --%>
<script type="text/javascript" src="<%= ctxPath%>/js/order/checkOut.js"></script>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<%-- 결제페이지 관련 css --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/order/checkOut.css" />



<style type="text/css">
.container-margin {
	margin: 0 10% 5% 10%;
}

.flexcss {
	display: flex;
}

div#loaderArr{
	position: sticky;
  	top: 0;
  	width: 130px;
  	height: 0px ;
}


/* -- CSS 로딩화면 구현 시작(bootstrap 에서 가져옴) 시작 -- */    
div.loader {
 /* border: 16px solid #f3f3f3; */
	border: 12px solid #f3f3f3;
   	border-radius: 50%;
 /* border-top: 16px solid #3498db; */
    border-top: 12px solid blue;

   	width: 100px;
   	height: 100px;
   	-webkit-animation: spin 2s linear infinite; /* Safari */
   	animation: spin 2s linear infinite;

}

div.font12{
	font-size: 13pt;
}


/* Safari */
	@-webkit-keyframes spin {
	0% { -webkit-transform: rotate(0deg); }
	100% { -webkit-transform: rotate(360deg); }
   	}
   
   	@keyframes spin {
    	0% { transform: rotate(0deg); }
    	100% { transform: rotate(360deg); }
   	}
/* -- CSS 로딩화면 구현 끝(bootstrap 에서 가져옴) 끝 -- */


</style>

<script type="text/javascript">
	$(document).ready(function(){
		<%-- 초기 주소록 로그인 정보로 입력 --%>
		const username = $("input:hidden[name='name']").val();
	    const useremail= $("input:hidden[name='email']").val();
	    let usermobile =$("input:hidden[name='mobile']").val();
	    const userpostcode =$("input:hidden[name='postcode']").val();
	    const useraddress =$("input:hidden[name='address']").val();
		<%-- 여기까지 함 이제 전화번호 (-)추가해야함 --%>
		
		const h1 = usermobile.slice(0,3);   
        const h2 = usermobile.slice(3,7);   
        const h3 = usermobile.slice(7);
		
        usermobile = h1+"-"+h2+"-"+h3;
		
	    $("div#name").text(username);
	    $("div#email").text(useremail);
	    $("div#mobile").text(usermobile);
	    $("div#postcode").text(userpostcode);
	    $("div#address").text(useraddress);
	    
	});// end of $(document).ready(function()------
	
</script>

	
<body>
   
<%-- CSS 로딩화면 구현한것--%>
<div id="loaderArr" style="display: flex; top: 45%; left: 48%; border: solid 0px blue;">
	<div class="loader" style="margin: auto"></div>
</div>

<%-- 결제페이지 시작 --%>

<div class="container-flude mt-3 container-margin" >
    <div class="h3 text-center mb-3">결제페이지</div>
    <form class="flexcss">
        <div class="px-4" style="width: 55%;">
            <label class="h4">주문자정보</label>
            <div id="userInfo" style="border: solid 1px black;">
                <div class="flexcss">
                    <div style="width: 85%;">
                    	<div class="mb-1 font12" id="name"></div>
                        <div class="mb-1 font12" id="email"></div>
                        <div class="mb-1 font12" id="mobile"></div>
                        <div class="mb-1 font12" id="postcode"></div>
                        <div class="font12" id="address"></div>
                        <%-- 넘겨줄 값 저장소 --%>
                        <c:set var="loginuser" value="${sessionScope.loginuser}"></c:set>
                        <input name="name" type="hidden" value="${loginuser.username}" /><%-- 이름 --%>
                        <input name="email" type="hidden" value="${loginuser.email}" /><%-- 이메일 --%>
                        <input name="mobile" type="hidden" value="${loginuser.mobile}" /><%-- 전화번호 --%>
                        <input name="postcode" type="hidden" value="${loginuser.postcode}" /><%-- 우편번호 --%>
                        <input name="address" type="hidden" value="${loginuser.address} ${loginuser.extra_address} ${loginuser.detail_address}" /><%-- 주소명 --%>
                    </div>
                    <div id="edit" >
                        <button class="btn btn-md btn-secondary px-4" type="button" id="infoEdit" onclick="gochange()">편집</button>
                    </div>
                </div>
                
            </div>

            
            <%-- 편집 누르면 보이도록 --%>
            <div class="my-2 " id="chageInfo">
                <div>
                    <div class="row mb-3">
                        <div class="col-6">
                            <input class="form-control" id="ch_name" type="text" placeholder="성명" value="" maxlength="15" />
                        	<div class="namemt" id="shouldmsg">이름을 입력하세요</div>
                        </div>
                        <div class="col-6">
                            <input class="form-control" id="ch_mobile" type="text" placeholder="전화번호" value="" maxlength="11"/>
                            <div class="mobilemt" id="shouldmsg">전화번호를 입력하세요</div>
                        </div>
                    </div>
                    
                    <div class="mb-2">
                        <button type="button" class="btn btn-outline-dark col-5" id="postSearch" >우편번호 검색</button>
                    </div>

                    <input disabled class="form-control col-5 mb-2" id="ch_postcode" type="text" placeholder="우편번호" value=""/>
    
                    <input disabled class="form-control mb-2" type="text" id="ch_address" placeholder="주소명" value=""/>
                    <input class="form-control " type="text" id="ch_detailAddress" placeholder="상세주소명" value="" maxlength="50"/>
                    <div class="mb-2 detailAddrmt" id="shouldmsg">상세주소를 입력하세요</div>

                    <div class="row p-4">
                        <button class="col-3 btn btn-outline-danger" style="margin: 0 3% 0 auto;" type="reset" onclick="gochange_cancel()">취소</button>
                        <button class="col-3 btn btn-outline-dark" type="button" onclick="gochange_complite()">저장</button>
                    </div>
                </div>
                
            </div>


            <%-- 배송 요청사항 부분 --%>
            <div >
                <label class="h4 mt-4">배송요청사항</label>
                <table class="table" style="width: 100%;">
                    <colgroup>
                        <col style="width: 22%;">
                        <col style="width: 78%;">
                    </colgroup>

                    <tr>
                        <th>
                            배송메시지
                        </th>
                        <td>
                            <div class="form-group">
                                <select class="form-control" id="requiremsg">
	                                <option>배송시 요청사항을 선택해 주세요.</option>
	                                <option>부재시 문앞에 놓아주세요.</option>
	                                <option>부재시 경비실에 맡겨 주세요.</option>
	                                <option>부재시 전화 또는 문자 주세요.</option>
	                                <option>택배함에 넣어 주세요.</option>
	                                <option>배송전에 연락 주세요.</option>
	                                <option id="ownWrite">직접입력</option>
                                </select>
                                <textarea class="form-control fixed-size" id="comment" maxlength="30"></textarea>
                            </div>
                        </td>
                    </tr>
                    
                </table>

            </div>

            <%-- 마일리지 시작 --%>
            <div>
                <label class="h4 mt-4">마일리지</label>
                <table class="table" style="width: 100%;">
                    <colgroup>
                        <col style="width: 22%;">
                        <col style="width: 78%;">
                    </colgroup>

                    <tr>
                        <th>
                            <label>사용금액 입력</label>
                        </th>
                        <td>
                        	<div class="flexcss">
	                        	<input type="text" class="form-control mr-3" id="usePoint" style=" width: 40%;" value="0"/>
	                            <button type="button" class="btn btn-sm btn-dark" id="allUsePoint">모두사용</button>
	                            
                        	</div>
                            
                            <div class="mt-2 flexcss">
                                <div style="font-size: 10pt;">사용가능&nbsp;<span id="userpoint"></span>&nbsp;P</div>
                                <span>&nbsp;&nbsp;/&nbsp;&nbsp;</span>
                                <div style="font-size: 10pt;">보유&nbsp;<span id="restpoint"></span>&nbsp;P</div>
                                <input name="userpoint" type="hidden" value="${sessionScope.loginuser.mileage}" style="border: solid 1px red;"/><%-- 보유한 마일리지 값 --%>
                            </div>
                            
                        </td>
                    </tr>
                    
                </table>

            </div>
            <%-- 마일리지 끝 --%>

            <div class="bg-light p-3">
                <ul id="advantage">
                    <li><span>혜택</span>&nbsp;&nbsp;[카카오페이] 삼성전자 5% 즉시할인</li>
                    <li><span>혜택</span>&nbsp;&nbsp;[PAYCO] 페이코 포인트 3천원 즉시할인</li>
                    <li><span>혜택</span>&nbsp;&nbsp;[카카오페이] 삼성전자 5% 즉시할인</li>
                    <li><span>혜택</span>&nbsp;&nbsp;[카카오페이] 삼성전자 5% 즉시할인</li>
                    <li><span>혜택</span>&nbsp;&nbsp;[카카오페이] 삼성전자 5% 즉시할인</li>
                    <li><span>혜택</span>&nbsp;&nbsp;[카카오페이] 삼성전자 5% 즉시할인</li>
                    <li><span>혜택</span>&nbsp;&nbsp;[카카오페이] 삼성전자 5% 즉시할인</li>
                </ul>
                
            </div>

        </div>



        
        
        <div class="p-3 payArea" style="width: 45%;">
            <div > <%-- 여기는 제품 보여지는 곳 입니다.--%>
            
                <%-- 이게 제품정보 상품하나 씩 for 문 돌려야함--%>
                <c:set var="pvoList" value="${requestScope.pvoList}"></c:set>
				<c:forEach var="no" begin="0" end="${fn:length(pvoList)-1}" varStatus="status" >
	                <div class="mb-3 flexcss pInfoArea" name="pInfo" id="pInfo${status.index}">
	                	<a href="<%=ctxPath%>/item/itemDetail.flex?pdno=${requestScope.pdnoArr[no]}">
		                	<img class="pImage" src="<%=ctxPath%>/images/product/${requestScope.pvoList[no].pdimg1}"/>
		                </a>
	                    <div class="productInfo pInfo1" >
	                    	<span id="pname${status.index}">${requestScope.pvoList[no].pdname}</span>
	                    	<br>
	                    	<span style="font-size: 10pt; color: blue;"><fmt:formatNumber pattern="###,###" value="${requestScope.pvoList[no].saleprice}"/>원</span>
	                    </div>
	                    
	                    <div class="productInfo pInfo4" >
	                    	<c:choose >
	                    		<c:when test="${requestScope.pvoList[no].pdvo.color eq 'none'}">단일색상</c:when>
	                    		<c:otherwise>${requestScope.pvoList[no].pdvo.color}</c:otherwise>
	                    	</c:choose>
	                    </div>

	                    <div class="productInfo pInfo2" align="center"><span name="oqty">${requestScope.cart_qtyArr[no]}</span>개</div>
	                    <div class="productInfo pInfo3 " align="right"><fmt:formatNumber pattern="###,###" value="${requestScope.pdPriceArr[no]}"/> 원</div>
	                    
						<%-- 결제완료시 업데이트할 때 보내줄 값 저장소 --%>
	                    <input type="hidden" class="pnum" value="${requestScope.pdnoArr[no]}" /> <%-- 제품번호 --%>
	                    <input type="hidden" class="pdetail" value="${requestScope.pd_detailnoArr[no]}" /> <%-- 제품상세번호 --%>
	                    <input type="hidden" class="poption" value="${requestScope.pvoList[no].pdvo.color}" /> <%-- 제품옵션 --%>
	                    <input type="hidden" class="oqty" value="${requestScope.cart_qtyArr[no]}"> <%-- 구매수량 --%>
	                    <input type="hidden" class="ptotalPrice" value="${requestScope.pdPriceArr[no]}"> <%-- 제품별 총금액 --%>
	                    <input type="hidden" class="cartno" value="${requestScope.cartnoArr[no]}" /> <%-- 장바구니번호 --%>
	                </div>
	           	</c:forEach>     
            </div>
            

            <%-- 여기는 총가격이 보여지는 곳입니다.--%>
            <div>
                <div class="row mb-1">
                    <div class="col-lg-6">
                        총 상품금액
                    </div>
                    <div class="col-lg-6 text-right">
                        <span class="totalPrice"></span>
                    </div>
                </div>
                <div class="row mb-1">
                    <div class="col-lg-6">
                        배송비
                    </div>
                    <div class="col-lg-6 text-right">
                    	<c:if test="${requestScope.totalPrice >= 100000}">
                    		<span class="deliveryfeeView">무료</span>
                    		<input class="deliveryfee" type="hidden" value="0"/>
                    	</c:if>
                    	<c:if test="${requestScope.totalPrice < 100000}">
                    		<span class="deliveryfeeView">5000&nbsp;원</span>
                    		<input class="deliveryfee" type="hidden" value="5000"/>
                    	</c:if>
                        
                    </div>
                </div>
                <div class="row mb-1">
                    <div class="col-lg-5">
                        마일리지 사용
                    </div>
                    <div class="col text-right">
                        <span id=useEndPoint></span><input type="hidden" name="useEndPointInput" value="0"/>
                    </div>
                </div>
                <%-- 여기에 구매시 마일리지 확인 --%>
                <div class="row mb-1">
                    <div class="col-lg-5">
                        예상 마일리지 적립
                    </div>
                    <div class="col text-right">
                        <span name="pointSave"><fmt:formatNumber value="${requestScope.totalPoint}" pattern="###,###" /> &nbsp;P</span><input type="hidden" name="pointSaveInput" value="${requestScope.totalPoint}"/>
                    </div>
                </div>
                
                
				<br>
				
                <div class="row h4 mt-3">
                    <div class="col-lg-4 pt-1">
                        총 결제비용
                    </div>
                    <div class="col-lg-8 text-right">
                    	<%-- js 에서 보내준다. --%>
                    	<span id="totalCostView" ></span>
                        <input type="hidden" id="totalPrice" value="${requestScope.totalPrice}"/>
                    </div>
                </div>

                <div class="mt-3" align="center">
                    <button id="chechoutSubmit" type="button" class="btn btn-lg btn-dark form-control" style="width: 80%;" onclick="goCheckOutPayment('<%=ctxPath%>','${requestScope.userid}')">결제하기</button>
                </div>

            </div>
        </div>
    </form>
    
</div>

<%-- 결제페이지 끝 --%>






<jsp:include page="../footer.jsp"></jsp:include>