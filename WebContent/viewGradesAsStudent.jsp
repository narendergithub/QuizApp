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
<title>Graded Quiz Student View</title>
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
						<li><h:commandLink id="logout" rendered="true"
								action="#{loginBean.logout}">logout</h:commandLink></li>
						<li><h:commandLink id="backButton" rendered="true"
								action="student.jsp">Back</h:commandLink></li>
					</h:form>
				</ul>
			</div>
		</div>
		<div class="header">
			<div class="container">
				<div style="height: 10px">
					<h2>View graded assessments as a student</h2>
				</div>
			</div>
		</div>
		<div class="content">
			<div class="container">
				<div class="main" align="center">
					<h:form enctype="multipart/form-data">
						<h:commandButton id="submit" value="Display assessments"
							action="#{actionBeanFile.viewGradedQuizStudent}"></h:commandButton>
					</h:form>
					<h:form id="viewGradedquiz"
						rendered="#{actionBeanFile.renderGradedQuiz}">
						<t:dataTable value="#{actionBeanFile.viewGradesList}"
							rendered="#{actionBeanFile.renderGradedQuiz}" var="rowNumber"
							border="1" cellspacing="0" cellpadding="1" align="center"
							columnClasses="columnClass1 border" headerClass="headerClass"
							footerClass="footerClass" rowClasses="rowClass2"
							styleClass="dataTableEx" width="800">
							<h:column>
								<f:facet name="header">
									<h:outputText>Assessment Name</h:outputText>
								</f:facet>
								<h:outputText value="#{rowNumber.assessmentName}" />
							</h:column>
							<h:column>
								<f:facet name="header">
									<h:outputText>Student Email</h:outputText>
								</f:facet>
								<h:outputText value="#{rowNumber.student_email_id}" />
							</h:column>
							<h:column>
								<f:facet name="header">
									<h:outputText>Assessment Score</h:outputText>
								</f:facet>
								<h:outputText value="#{rowNumber.assessment_grade}" />
							</h:column>
							<h:column>
								<f:facet name="header">
									<h:outputText>Professor Comments</h:outputText>
								</f:facet>
								<h:outputText value="#{rowNumber.professor_comments}" />
							</h:column>
							<h:column>
								<f:facet name="header">
									<h:outputText>Action</h:outputText>
								</f:facet>
								<h:commandLink value="view assessment"
									action="#{actionBeanFile.fetchQuiz(rowNumber.assessmentName)}"></h:commandLink>
							</h:column>
						</t:dataTable>
					</h:form>
					<h:form id="takeQuizform"
						rendered="#{actionBeanFile.renderassessment}">
						<t:dataTable value="#{actionBeanFile.assessmentList}"
							var="rowNumber1" rendered="#{actionBeanFile.renderassessment}"
							border="1" cellspacing="0" cellpadding="1" align="center"
							columnClasses="columnClass1 border" headerClass="headerClass"
							footerClass="footerClass" rowClasses="rowClass2"
							styleClass="dataTableEx" width="800">
							<h:column>
								<f:facet name="header">
									<h:outputText>Question Number</h:outputText>
								</f:facet>
								<h:outputText value="#{rowNumber1.questionNumber}" />
							</h:column>
							<h:column>
								<f:facet name="header">
									<h:outputText>Question Type</h:outputText>
								</f:facet>
								<h:outputText value="#{rowNumber1.questionType}" />
							</h:column>
							<h:column>
								<f:facet name="header">
									<h:outputText>Actual Question</h:outputText>
								</f:facet>
								<h:outputText value="#{rowNumber1.actualQuestion}" />
							</h:column>
							<h:column>
								<f:facet name="header">
									<h:outputText>Your Answer</h:outputText>
								</f:facet>
								<h:outputText value="#{rowNumber1.submittedAnswers}" />
							</h:column>
							<h:column>
								<f:facet name="header">
									<h:outputText>scores</h:outputText>
								</f:facet>
								<h:outputText value="#{rowNumber1.score}" />
							</h:column>
						</t:dataTable>
					</h:form>
					<h:messages style="color:red;list-style-type:none;"
						showSummary="false" showDetail="true" globalOnly="true">
					</h:messages>
					<br> <br>
				</div>
			</div>
		</div>
		<div class="footer">
			<div class="container" align="right">&copy; Copyright QuizApp</div>
		</div>
	</f:view>
</body>
</html>