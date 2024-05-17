<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>

<div class="container" style="padding: 3% 0; border: solid 1px red;">
   <h2 class="text-center mb-5"> 회원 전체 목록</h2>
   
   <form name="member_search_frm">
      <select name="searchType">
         <option value="">검색대상</option>
         <option value="name">회원명</option>
         <option value="userid">아이디</option>
         <option value="email">이메일</option>
      </select>
      &nbsp;
      <input type="text" name="searchWord" /> 
       <input type="text" style="display: none;" /> 
      <button type="button" class="btn btn-secondary" onclick="goSearch()">검색</button>
      
      <span style="font-size: 12pt; font-weight: bold;">페이지당 회원명수&nbsp;-&nbsp;</span>
      <select name="sizePerPage">
         <option value="20">20명</option>
         <option value="10">10명</option>   
         <option value="5">5명</option>   
      </select>
   </form>
   <table class="table table-bordered" id="memberTbl">
      <thead>
          <tr>
             <th>번호</th>
             <th>아이디</th>
             <th>회원명</th>
             <th>이메일</th>
             <th>성별</th>	
          </tr>
      </thead>
      <tbody>
	      <c:if test="${not empty requestScope.memberList}">      
	          <c:forEach var="membervo" items="${requestScope.memberList}" varStatus="status">
	          	<tr class="memberInfo">
	          		<%-- >>> 페이징 처리시 보여주는 순번 공식 <<<
                     데이터개수 - (페이지번호 - 1) * 1페이지당보여줄개수 - 인덱스번호 => 순번 
                     <예제>
                     데이터개수 : 12
                     1페이지당보여줄개수 : 5
                  
                     ==> 1 페이지       
                     12 - (1-1) * 5 - 0  => 12
                     12 - (1-1) * 5 - 1  => 11
                     12 - (1-1) * 5 - 2  => 10
                     12 - (1-1) * 5 - 3  =>  9
                     12 - (1-1) * 5 - 4  =>  8
                  
                     ==> 2 페이지
                     12 - (2-1) * 5 - 0  =>  7
                     12 - (2-1) * 5 - 1  =>  6
                     12 - (2-1) * 5 - 2  =>  5
                     12 - (2-1) * 5 - 3  =>  4
                     12 - (2-1) * 5 - 4  =>  3
                  
                     ==> 3 페이지
                     12 - (3-1) * 5 - 0  =>  2
                     12 - (3-1) * 5 - 1  =>  1 
                 --%>
                 	<fmt:parseNumber var="currentShowPageNo" value="${requestScope.currentShowPageNo}" />
                 	<fmt:parseNumber var="sizePerPage" value="${requestScope.sizePerPage}" />
                 	<%-- fmt:parseNumber 은 문자열을 숫자형식으로 형변환 시키는 것이다. --%>
                 	<%-- 데이터개수 - (페이지번호 - 1) * 1페이지당보여줄개수 - 인덱스번호 => 순번 --%>
	          		<td>${(requestScope.totalMemberCount) - (currentShowPageNo -1) * sizePerPage - (status.index)}</td>
	          		<td class="userid">${membervo.userid}</td>
	          		<td>${membervo.name}</td>
	          		<td>${membervo.email}</td>
	          		<td>
	          			<c:choose>
	          				<c:when test="${membervo.gender == '1'}">남</c:when>
	          				<c:otherwise>여</c:otherwise>
	          			</c:choose>
	          		</td>
	          	</tr>
	          </c:forEach>
	      </c:if>
	      <c:if test="${empty requestScope.memberList}">      
	          <tr>
	          	 <td colspan="5">데이터가 존재하지 않습니다.</td>
	          </tr>
	      </c:if>
      </tbody>
   </table>     

    <div id="pageBar">
       <nav>
			<ul class="pagination">
				${requestScope.pageBar}
			</ul>
       </nav>
    </div>
</div>

<form name="memberOneDetail_frm">
	<input type="hidden" name="userid"/>
	<input type="hidden" name="goBackURL" value="${requestScope.currentURL}"/>
	<%-- 폼 전송을 통해 URL 을 보낸다. --%>
</form>

</body>
</html>