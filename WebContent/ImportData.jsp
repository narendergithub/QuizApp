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
<title>File Import page</title>
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
								action="#{actionBeanFile.back}">Back</h:commandLink></li>
					</h:form>
				</ul>
			</div>
		</div>
		<div class="header">
			<div class="container">
				<div style="height: 10px">
					<h2>Welcome to File Import page</h2>
				</div>
			</div>
		</div>
		<div class="content">
			<div class="container">
				<div class="main" align="center">
					<h:form id="importfilemenu" enctype="multipart/form-data">
						<h:panelGrid columns="2" border="1" cellpadding="0"
							cellspacing="2" bgcolor="#C0C0C0">
							<f:facet name="header">
								<h:outputText value="File Import" style="font-weight:bold"></h:outputText>
							</f:facet>
							<h:outputLabel value="select file to upload:" />
							<t:inputFileUpload id="fileUpload" label="File to upload"
								storage="default" value="#{actionBeanFile.uploadedFile}"
								size="60" />
							<h:outputLabel value="file label:"
								rendered="#{actionBeanFile.renderUploadTypeQuiz }" />
							<h:inputText id="fileLabel" value="#{actionBeanFile.fileLabel}"
								size="60" rendered="#{actionBeanFile.renderUploadTypeQuiz }"
								required="true"
								requiredMessage="Please enter a name for the file to be uploaded" />
							<h:outputLabel value=" "
								rendered="#{actionBeanFile.renderUploadTypeQuiz }" />
							<f:facet name="footer">
								<h:panelGroup style="display:block; text-align:center">
									<h:commandButton id="uploadFileAssessment"
										rendered="#{actionBeanFile.renderUploadTypeQuiz}"
										action="#{actionBeanFile.importAssessment}"
										value="Upload Assessment" />
									<h:commandButton id="uploadFileRoster"
										rendered="#{actionBeanFile.renderUploadTypeRoster}"
										action="#{actionBeanFile.importRoster}" value="Upload Roster" />
								</h:panelGroup>
							</f:facet>
						</h:panelGrid>
						<h:message style="color:red;list-style-type:none;" for="fileLabel"
							showDetail="true" showSummary="false"></h:message>
						<h:messages style="color:red;list-style-type:none;"
							showSummary="false" showDetail="true" globalOnly="true">
						</h:messages>
					</h:form>
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