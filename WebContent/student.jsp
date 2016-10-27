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
<title>Student's home page</title>
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
					<h:form id="links">
						<li><a href="student.jsp">Home</a></li>
						<li><a href="faces/Files/StudentUserGuide.pdf"
							target="_blank">Student User guide</a></li>
						<li><h:commandLink id="logout" rendered="true"
								action="#{loginBean.logout}">logout</h:commandLink></li>
						<li><h:commandLink id="backButton"
								rendered="#{professor.studentView}"
								action="#{actionBeanFile.back}">Back to Instructor/TA home page</h:commandLink>
						</li>
					</h:form>
				</ul>
			</div>
		</div>
		<div class="header">
			<div class="container">
				<div style="height: 10px">
					<h2>
						Welcome
						<h:outputText id="UserName" value="#{loginBean.userLastName }"></h:outputText>
						!
					</h2>
				</div>
			</div>
		</div>
		<div class="content">
			<div class="container">
				<div class="main" align="center">
					<h:form id="courseForm">
						<h:panelGrid columns="1" border="0" cellpadding="0"
							cellspacing="3" bgcolor="#C0C0C0" width="500"
							style="text-align: center;">
							<f:facet name="header">
								<h:panelGroup style="display:block; text-align:center">
									<h:outputText value="Student home page"
										style="font-weight:bold;font"></h:outputText>
								</h:panelGroup>
							</f:facet>
							<h:commandLink id="viewGrades" rendered="true"
								action="#{actionBeanFile.viewGrades}">View Grades</h:commandLink>
							<h:commandLink id="assessment" rendered="true"
								action="#{actionBeanFile.viewStudentAssessment}">View Assessments</h:commandLink>
							<h:commandLink id="course" rendered="true"
								action="#{actionBeanFile.courseQuery}">View Courses</h:commandLink>
						</h:panelGrid>
					</h:form>
					<br> <br>
				</div>
			</div>
		</div>
		<div class="footer">
			<div class="container"
				style="width: auto; padding: 10px; border: 5px solid gray; margin: 0;">
				<h:outputLabel style="font-style: italic;">IP Address : </h:outputLabel>
				<h:outputLabel style="color:red;" value="#{loginBean.ipAddress}"></h:outputLabel>
				&emsp;
				<h:outputLabel style="font-style: italic;"> Last Login : </h:outputLabel>
				<h:outputLabel style="color:red" value="#{loginBean.timestamp}"></h:outputLabel>
				<h:outputLabel style="float:right;">&copy; Copyright QuizApp</h:outputLabel>
			</div>
		</div>
	</f:view>
</body>
</html>