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
<title>Registration page</title>
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
					<li><a href="index.jsp">Home</a></li>
				</ul>
			</div>
		</div>
		<div class="header">
			<div class="container">
				<h2>Registration Page</h2>
			</div>
		</div>
		<div class="content">
			<div class="container">
				<div class="main" align="center">
					<h:form id="registrationPage">
						<h:panelGrid columns="2" border="0" cellpadding="0"
							cellspacing="3">
							<f:facet name="header">
								<h:panelGroup style="display:block; text-align:center">
									<h:outputText value="Sign Up Form" style="font-weight:bold"></h:outputText>
								</h:panelGroup>
							</f:facet>
							<h:outputLabel value="First Name" style="font-weight:bold"
								styleClass="required" />
							<h:inputText id="first_name" value="#{professor.first_name}"
								required="true" requiredMessage="first name is required" />
							<h:outputLabel value="Last Name" style="font-weight:bold"
								styleClass="required" />
							<h:inputText id="last_name" value="#{professor.last_name}"
								required="true" requiredMessage="last name is required" />
							<h:outputLabel value="User Role" style="font-weight:bold"
								styleClass="required" />
							<h:selectOneMenu id="role" value="#{professor.role}"
								required="true"
								requiredMessage="user role is required . Select 'Professor' or 'TA' or 'Tutor'">
								<f:selectItem id="roleItem1" itemLabel="Professor"
									itemValue="PROFESSOR" />
								<f:selectItem id="roleItem2" itemLabel="Teaching Assistant"
									itemValue="TA" />
								<f:selectItem id="roleItem3" itemLabel="Tutor" itemValue="TUTOR" />
							</h:selectOneMenu>
							<h:outputLabel value="Email ID" style="font-weight:bold"
								styleClass="required" />
							<h:inputText id="email_id" value="#{professor.email_id}"
								required="true" requiredMessage="email ID is required" />
							<h:outputLabel value="Password" style="font-weight:bold"
								styleClass="required" />
							<h:inputSecret id="password" value="#{professor.password}"
								required="true" requiredMessage="Password is required"
								validatorMessage="Password must have a minimum of 6 and maximum of 10 characters">
								<f:validateLength maximum="10" minimum="6" />
							</h:inputSecret>
							<h:outputLabel value="Confirm Password" style="font-weight:bold"
								styleClass="required" />
							<h:inputSecret id="retype_password"
								value="#{professor.retype_password}" required="true"
								requiredMessage="Confirm password is required" />
							<h:outputLabel value="Course" style="font-weight:bold"
								styleClass="required" />
							<h:selectOneRadio id="course_code"
								value="#{professor.course_code}" required="true"
								requiredMessage="course code is required">
								<f:selectItem id="courseItem1"
									itemLabel="Quantitative Statistics" itemValue="IDS_517" />
								<br />
								<f:selectItem id="courseItem2" itemLabel="Mathematics"
									itemValue="IDS_400" />
							</h:selectOneRadio>
							<f:facet name="footer">
								<h:panelGroup style="display:block; text-align:center">
									<h:commandButton id="Submit" value="Register"
										action="#{actionBeanFile.registration}"></h:commandButton>
								</h:panelGroup>
							</f:facet>
						</h:panelGrid>
						<h:message styleClass="messages" showDetail="false"
							showSummary="true" for="first_name"></h:message>
						<br />
						<h:message styleClass="messages" showDetail="false"
							showSummary="true" for="last_name"></h:message>
						<br />
						<h:message styleClass="messages" showDetail="false"
							showSummary="true" for="role"></h:message>
						<br />
						<h:message styleClass="messages" showDetail="false"
							showSummary="true" for="email_id"></h:message>
						<br />
						<h:message styleClass="messages" showDetail="false"
							showSummary="true" for="password"></h:message>
						<br />
						<h:message styleClass="messages" showDetail="false"
							showSummary="true" for="retype_password"></h:message>
						<br />
						<h:message styleClass="messages" showDetail="false"
							showSummary="true" for="course_code"></h:message>
						<br />
					</h:form>
					<br>
					<hr />
					<h:messages styleClass="messages" showSummary="false"
						showDetail="true" globalOnly="true">
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