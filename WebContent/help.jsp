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
<title>Help page</title>
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
	<%
		if (session.getAttribute("login") == null)
			response.sendRedirect("dbLogin.jsp");
	%>
	<f:view>
		<div class="header">
			<div class="container">
				<img align="left" src="QuizApp.png" width="200px" height="110px">
				<h1 class="header-heading" align="justify"
					style="color: #ffffff; font-family: Segoe UI">QuizApp</h1>
				<h5 align="right">IDS517 Enterprise Application Development</h5>
				<h5 align="right">Online Quiz Application - f15g109</h5>
			</div>
		</div>
		<div class="nav-bar">
			<div class="container">
				<ul class="nav">
					<h:form>
						<li><h:commandLink id="logout" rendered="true"
								action="#{loginBean.logout}" value="logout"></h:commandLink></li>
						<li><h:commandLink id="backButton" rendered="true"
								action="#{actionBeanFile.back}">Back</h:commandLink></li>
					</h:form>
				</ul>
			</div>
		</div>
		<div class="header">
			<div class="container">
				<div style="height: 10px">
					<h2>Welcome to Help Page</h2>
				</div>
			</div>
		</div>
		<div class="content">
			<div class="container">
				<div class="main" align="center">
					<h3>FAQS</h3>
					<ul>
						<li>Best practices for taking an online test in Blackboard
							Learn</li>
						<li>I am enrolled for a course but I do not see my course on
							Blackboard, what do I do?</li>
						<li>I still see the old course/dropped course listed, how do
							I get rid of it?</li>
						<li>I am trying to submit a paper on Dashboard through the
							SafeAssign link and get an error message.</li>
						<li>I am not able to view any Dashboard updates even though
							it shows that I have updates.</li>
						<li>I am not able to view/post/edit/delete comments on the
							discussion board for my course.</li>
						<li>I cant see my grades.</li>
						<li>Where can I find the Academic Calendar?</li>
					</ul>
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