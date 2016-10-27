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
<title>Login Page</title>
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
						<li><a href="databaseAccess.jsp">Database Access</a></li>
						<li><a href="faces/Files/WebApplicationUserGuide.pdf"
							target="_blank">User Guide</a></li>
						<li><h:commandLink id="logout" rendered="true"
								action="#{loginBean.dbLogout}" value="Database logout"></h:commandLink>
						</li>
					</h:form>
				</ul>
			</div>
		</div>
		<div class="header">
			<div class="container">
				<div style="height: 10px">
					<h2>Login Page</h2>
				</div>
			</div>
		</div>
		<div class="content">
			<div class="container">
				<div class="main" align="center">
					<h:form id="loginpage">
						<h:panelGrid columns="2" border="1" cellpadding="0"
							cellspacing="2">
							<f:facet name="header">
								<h:panelGroup style="display:block; text-align:center">
									<h:outputText value="Sign In" style="font-weight:bold"></h:outputText>
								</h:panelGroup>
							</f:facet>
							<h:outputLabel value="UserName" style="font-weight:bold"
								styleClass="required" />
							<h:inputText id="username" value="#{loginBean.username}" />
							<h:outputLabel value="Password" style="font-weight:bold"
								styleClass="required" />
							<h:inputSecret id="password" value="#{loginBean.password}" />
							<f:facet name="footer">
								<h:panelGroup style="display:block; text-align:center">
									<h:commandButton id="Submit" value="Submit"
										action="#{loginBean.login}"></h:commandButton>
								</h:panelGroup>
							</f:facet>
						</h:panelGrid>
						<h:outputLabel style="font-style:italic;color:blue">Are You a new user ?</h:outputLabel>
						<h:commandLink action="#{professor.registerUser}">Register here</h:commandLink>
					</h:form>
					<br>
					<hr />
					<h:messages style="color:red;list-style-type:none;"
						showSummary="false" showDetail="true" globalOnly="true">
					</h:messages>
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