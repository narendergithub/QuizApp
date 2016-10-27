<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="t" uri="http://myfaces.apache.org/tomahawk"%>
<!DOCTYPE >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Home page</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="Description" lang="en" content="Online Quiz Application">
<meta name="author" content="F15g109">
<meta name="robots" content="index, follow">

<!-- icons -->
<link rel="apple-touch-icon" href="CSS/assets/img/apple-touch-icon.png">
<link rel="shortcut icon" href="CSS/favicon.ico">

<!-- Override CSS file - add your own CSS rules -->
<link type="text/css" rel="stylesheet" href="CSS/assets/css/styles.css">
</head>
<body>
	<f:view>
		<div class="header">
			<div class="container" align="center">
				<h1 class="header-heading">IDS517 Enterprise Application
					Development</h1>
				<h1 class="header-heading">Online Quiz Application - f15g109</h1>
			</div>
		</div>
		<div class="nav-bar">
			<div class="container">
				<ul class="nav">
					<li><a href="faces/dbLogin.jsp">Database Login</a></li>
					<li><a href="faces/documentation.jsp">Project Documents</a></li>
				</ul>
			</div>
		</div>
		<div class="content">
			<div class="container">
				<div class="main">
					<div id="Headers" align="center" style="color: blue">
						<br> <br>
						<h1 style="color: red">Group Members</h1>
						<h2>Premnath Ramanathan</h2>
						<h2>Narender Tankasala</h2>
						<h2>Dinesh Kumaar Rajan</h2>
						<hr>
					</div>
					<br> <br>
				</div>
			</div>
		</div>
	</f:view>
	<div class="footer">
		<div class="container" align="right">&copy; Copyright QuizApp</div>
	</div>
</body>
</html>