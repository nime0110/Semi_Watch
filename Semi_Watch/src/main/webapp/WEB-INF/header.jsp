<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String ctxPath = request.getContextPath();
    //    /MyMVC
%>    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="author" content="Untree.co">
  <link rel="shortcut icon" href="favicon.png">

  <meta name="description" content="" />
  <meta name="keywords" content="bootstrap, bootstrap4" />

<%-- Optional JavaScript --%>
<script type="text/javascript" src="<%= ctxPath%>/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="<%= ctxPath%>/js/bootstrap.bundle.min.js" ></script> 


<!-- Bootstrap CSS -->
<link href="css/reset.css" rel="stylesheet">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
<link href="css/tiny-slider.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
		
		<title>Wathch Shoping Mall 임시</title>
<style>
nav#header {
		position:sticky;
		top:0;
        z-index: 9999;
}

ul.find_wrap a {
	text-decoration: none;

}

ul.find_wrap li {
	
	margin-right: 2%;

}

</style>		
		
</head>
<body>


		<nav id="header" class="custom-navbar navbar navbar navbar-expand-md navbar-dark bg-dark">

			<div class="container">
				<a class="navbar-brand" href="index.html">Watch Shop 임시이름<span>.</span></a>

				<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarsFurni" aria-controls="navbarsFurni" aria-expanded="false" aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>

				<div class="collapse navbar-collapse" id="navbarsFurni">
					<ul class="custom-navbar-nav navbar-nav ms-auto mb-2 mb-md-0">
						
						<%-- 
						<li class="nav-item active">
							<a class="nav-link" href="index.html">Home</a>
						</li>
						--%>
						
						<li><a class="nav-link" href="#">All</a></li>
						<li><a class="nav-link" href="#">new</a></li>
						<li><a class="nav-link" href="#">best</a></li>
						<li><a class="nav-link" href="#">about us</a></li>
						
					</ul>
						
						
	
					<ul class="custom-navbar-cta navbar-nav mb-2 mb-md-0 ms-5">
						<li><a class="nav-link" href="#"><img src="../images/user.svg"></a></li>
						<li><a class="nav-link" href="cart.html"><img src="../images/cart.svg"></a></li>
					</ul>
				</div>
			</div>
				
		</nav>
