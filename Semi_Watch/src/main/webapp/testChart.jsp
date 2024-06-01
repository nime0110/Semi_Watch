<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
	String ctxPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="EUC-KR">
	
	<%-- 공용 제이쿼리/부트스트랩 --%>
	<script src="<%= ctxPath%>/js/jquery-3.7.1.min.js" type="text/javascript"></script>
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.3.2/jquery.rateyo.min.css">
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.3.2/jquery.rateyo.min.js"></script>
	<script type="text/javascript">
		$(function () {
		 
			  $("#rateYo").rateYo({
				    rating: 4.5,
				    readOnly: true
				  });
		 
		});
		// Getter
		var readOnly = $("#rateYo").rateYo("option", "readOnly"); //returns true
		 
		// Setter
		$("#rateYo").rateYo("option", "readOnly", false); //returns a jQuery Element

	</script>
	<title>모달</title>
</head>

<body>
<div id="rateYo"></div>
<p>Selected Rating: <span id="ratingValue">0</span></p>
</body>

</html>