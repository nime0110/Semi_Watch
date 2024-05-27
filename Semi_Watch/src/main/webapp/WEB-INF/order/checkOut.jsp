<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
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
	margin: 0 10%;
	border: solid 1px black;
}

.flexcss {
	display: flex;
}

</style>

	
<body>
   

<%-- 결제페이지 시작 --%>

<div class="container-flude mt-3 container-margin" >
    <div class="h3 text-center mb-3">결제페이지</div>
    <form class="flexcss">
        <div class="col-7 px-4" style="border: solid 1px green;">
            <label class="h4">주문자정보</label>
            <div id="userInfo" style="border: solid 1px black;">
                <div class="flexcss">
                    <div style="width: 85%;">
                    	<div class="mb-1" id="name">홍길동</div>
                        <div class="mb-1" id="email">example@naver.com</div>
                        <div class="mb-1" id="mobile">010-1234-5678</div>
                        <div class="mb-1" id="postcode">16555</div>
                        <div id="address">서울특별시 강남구 도곡7로 47 땡땡아파트 47</div>
                        <%-- 넘겨줄 값 저장소 --%>
                        <input name="name" type="hidden" value="" /><%-- 이름 --%>
                        <input name="email" type="hidden" value="example@naver.com" /><%-- 이메일 --%>
                        <input name="mobile" type="hidden" value="" /><%-- 전화번호 --%>
                        <input name="postcode" type="hidden" value="" /><%-- 우편번호 --%>
                        <input name="address" type="hidden" value="" /><%-- 주소명 --%>
                    </div>
                    <div id="edit" >
                        <button class="btn btn-md btn-secondary px-4" type="button"  onclick="gochange()">편집</button>
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
                <table class="table" style="border: solid 1px red; width: 100%;">
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
                                <textarea class="form-control fixed-size" id="comment" maxlength="30">안녕</textarea>
                            </div>
                        </td>
                    </tr>
                    
                </table>

            </div>

            <%-- 마일리지 시작 --%>
            <div>
                <label class="h4 mt-4">마일리지</label>
                <table class="table" style="border: solid 1px red; width: 100%;">
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
	                        	<input type="text" class="form-control mr-3" id="usePoint" style=" width: 40%;"/>
	                            <button type="button" class="btn btn-sm btn-dark" id="allUsePoint">모두사용</button>
	                            
                        	</div>
                            
                            <div class="mt-2 flexcss">
                                <div style="font-size: 10pt;">사용가능&nbsp;<span id="userpoint"></span>&nbsp;P</div>
                                <span>&nbsp;&nbsp;/&nbsp;&nbsp;</span>
                                <div style="font-size: 10pt;">보유&nbsp;<span id="restpoint"></span>&nbsp;P</div>
                                <input name="userpoint" type="hidden" value="5000" /><%-- 보유한 마일리지 값 --%>
                            </div>
                            
                        </td>
                    </tr>
                    
                </table>

            </div>
            <%-- 마일리지 끝 --%>

            <div class="bg-light">
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



        
        
        <div class="col-5 p-3" style="border: solid 1px blue;">
            <div > <%-- 여기는 제품 보여지는 곳 입니다.--%>
                
                <%-- 이게 제품 상품하나 씩 for 문 돌려야함--%>
                <div class="mb-3 flexcss" style="border: solid 1px orange;">
                    <img id="pImage" />
                    <div class="productInfo" style="border:solid 1px orange; padding-top:10px; margin: 5% auto 5% 2%; width: 40%;">제품명</div>
                    <div class="productInfo" style="padding-top:10px; margin: 5% auto;">10<span>개</span></div>
                    <div class="productInfo" style="padding-top:10px; margin: 5% 2% 5% auto; width: 17%;">$2000000</div>
                </div>
            </div>
            

            <%-- 여기는 총가격이 보여지는 곳입니다.--%>
            <div>
                <div class="row">
                    <div class="col-lg-6">
                        총 상품금액
                    </div>
                    <div class="col-lg-6 text-right">
                        <span>3000</span>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-6">
                        배송비
                    </div>
                    <div class="col-lg-6 text-right">
                        <span>+ 3000</span>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-6">
                        마일리지 사용
                    </div>
                    <div class="col-lg-6 text-right" style="border:solid 1px red;">
                        <span id=useEndPoint></span><input type="text" id="useEndPointInput" />
                    </div>
                </div>
                
				<br>
				
                <div class="row h4">
                    <div class="col-lg-6">
                        총 결제비용
                    </div>
                    <div class="col-lg-6 text-right">
                    	<%-- js 에서 보내준다. --%>
                    	<span id="totalCostView"></span>
                        <input type="hidden" id="totalCost" value="3000"/>
                    </div>
                </div>

                <div class="mt-3" align="center">
                    <button type="button" class="btn btn-lg btn-dark form-control" style="width: 80%;" onclick="goCheckOutPayment('<%=ctxPath%>','jhkvng123')">결제하기</button>
                </div>

            </div>
        </div>
    </form>


    
    
</div>

<%-- 결제페이지 끝 --%>




<jsp:include page="../footer.jsp"></jsp:include>