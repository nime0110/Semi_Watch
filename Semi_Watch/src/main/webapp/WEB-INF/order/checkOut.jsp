<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String ctxPath = request.getContextPath();
%>
    
<%-- 강지훈 제작 페이지 --%>
    
<jsp:include page="../header1.jsp"></jsp:include>

<%-- 결제페이지 관련 js --%>
<script type="text/javascript" src="<%= ctxPath%>/js/order/checkOut.js"></script>

<%-- 결제페이지 관련 css --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/order/checkOut.css" />


	
<body>
   

<%-- 결제페이지 시작 --%>

<div class="container mt-3" style="border: solid 1px red;" >
    <div class="h3 text-center mb-3">결제페이지</div>
    <div style="display: flex;">
        <div class="col-7 px-4" style="border: solid 1px green;">
            <label class="h4">주문자정보</label>
            <div id="userInfo">
                <div style="display: flex;">
                    <div style="border: solid 1px red;">
                        <div>홍길동</div>
                        <div>example@naver.com</div>
                        <div>010-1234-5678</div>
                        <div>16555</div>
                        <div>서울특별시 강남구 도곡7로 47 땡땡아파트 47</div>
                    </div>
                    <div id="edit" >
                        <button class="btn btn-md btn-secondary px-4" type="button"  onclick="gochange()">편집</button>
                    </div>
                </div>
                
            </div>

            
            <%-- 편집 누르면 보이도록 --%>
            <div class="my-2 " id="chageInfo">
                <form>
                    <div class="mb-3">
                        <input class="form-control" id="order" type="email" placeholder="성명" value="" />
                        <div id="shouldmsg">이름을 입력하세요</div>
                    </div>
    
                    <div class="row mb-3">
                        <div class="col-6">
                            <input class="form-control" type="email" placeholder="이메일" value="" />
                            <div id="shouldmsg">이메일을 입력하세요</div>
                        </div>
                        <div class="col-6">
                            <input class="form-control" type="text" placeholder="전화번호" value="" />
                            <div id="shouldmsg">전화번호를 입력하세요</div>
                        </div>
                    </div>
                    
                    <div class="mb-2">
                        <button class="btn btn-outline-dark col-5" id="deliver">우편번호 검색</button>
                    </div>

                    <input class="form-control col-5 mb-2" type="text" placeholder="우편번호" vlaue="" disabled/>
    
                    <input class="form-control mb-2" type="text" placeholder="주소명" vlaue="" disabled/>
                    <input class="form-control mb-3" type="text" placeholder="상세주소명" vlaue="" />

                    <div class="row p-4">
                        <button class="col-3 btn btn-outline-danger" style="margin: 0 3% 0 auto;" type="reset" onclick="gochange_end()">취소</button>
                        <button class="col-3 btn btn-outline-dark" type="button" onclick="gochange_end()">저장</button>
                    </div>
                </form>
                
            </div>


            <%-- 배송 요청사항 부분 --%>
            <div style="border: solid 1px red;">
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
                                <select class="form-control" id="sel1">
	                                <option>배송시 요청사항을 선택해 주세요.</option>
	                                <option>부재시 문앞에 놓아주세요.</option>
	                                <option>부재시 경비실에 맡겨 주세요.</option>
	                                <option>부재시 전화 또는 문자 주세요.</option>
	                                <option>택배함에 넣어 주세요.</option>
	                                <option>배송전에 연락 주세요.</option>
	                                <option id="ownWrite">직접입력</option>
                                </select>
                                <textarea class="form-control" rows="2" id="comment" >안녕</textarea>
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
                        <td style="">
                            <input class="form-control" style="width: 40%; border: solid 1px red;" value=""/>
                            <span><button class="btn btn-sm btn-dark">모두사용</button></span>
                            <div class="mt-2">
                                <span>사용가능&nbsp;<span id="usePoint" val="">00</span> / 보유&nbsp;<span val="">00</span></span>
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
                <div class="mb-3" style="border: solid 1px orange; display: flex; ">
                    <div style="border: solid 1px blue; width: 100px; height: 100px;">이미지</div>
                    <div id="productInfo" style="border:solid 1px orange; padding-top:10px; margin: 5% auto 5% 2%; width: 40%;">제품명</div>
                    <div id="productInfo" style="padding-top:10px; margin: 5% auto;">10<span>개</span></div>
                    <div id="productInfo" style="padding-top:10px; margin: 5% 2% 5% auto; width: 17%;">$2000000</div>
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
                        <span>3000</span>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-6">
                        포인트
                    </div>
                    <div class="col-lg-6 text-right">
                        <span>-3000</span>
                    </div>
                </div>

                <div class="row">
                    <div class="col-lg-6">
                        총 결제비용
                    </div>
                    <div class="col-lg-6 text-right">
                        <span>3000</span>
                    </div>
                </div>

                <div class="mt-3">
                    <button class="btn btn-dark form-control">결제하기</button>
                </div>

            </div>
        </div>
    </div>


    
    
</div>

<%-- 결제페이지 끝 --%>




<jsp:include page="../footer.jsp"></jsp:include>