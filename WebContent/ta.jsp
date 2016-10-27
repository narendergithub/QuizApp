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
<title>TA's page</title>
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
						<li><a href="ta.jsp">Home</a></li>
						<li><a href="help.jsp">Help</a></li>
						<li><a href="faces/Files/TAUserGuide.pdf" target="_blank">User
								Guide</a></li>
						<li><h:commandLink id="logout" rendered="true"
								action="#{loginBean.logout}">logout</h:commandLink></li>
						<li><h:commandLink id="studentview"
								action="#{professor.viewAsStudent}">View as Student</h:commandLink>
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
					<h:form id="menuList">
						<h:panelGrid bgcolor="#C0C0C0" border="0" width="600"
							cellpadding="5">
							<f:facet name="header">
								<h:panelGroup style="display:block; text-align:center">
									<h:outputText value="Teaching Assistant's home page"
										style="font-weight:bold;font"></h:outputText>
								</h:panelGroup>
							</f:facet>
							<h:commandLink id="courseAssmntTA"
								action="#{actionBeanFile.initiateFileLabelsAssessment}">View Course Assessment</h:commandLink>
							<h:commandLink id="uploadCourseTA"
								action="#{actionBeanFile.uploadCourseAssessment}">Upload course
						Assessment</h:commandLink>
							<h:commandLink id="viewRosterTA"
								action="#{actionBeanFile.initiateFileLabelsRoster}">View Roster
						</h:commandLink>
							<h:commandLink id="viewGradedAssessmentsTA"
								action="#{grades.viewGradedAssessments}">View Graded Assessments
						</h:commandLink>
							<h:commandLink id="DloadCourseTA"
								action="#{professor.dloadCourseAssessment}">Download course
						Assessment</h:commandLink>
							<h:commandLink id="DloadRosterTA"
								action="#{professor.dloadStudRoster}">Download Student Roster</h:commandLink>
							<h:commandLink id="AnalyzeScoresTA"
								action="#{statisticsBean.analyzeScores}">Analyze scores</h:commandLink>
						</h:panelGrid>
						<h:messages style="color:red;list-style-type:none;"
							showSummary="false" showDetail="true" globalOnly="true">
						</h:messages>
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