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
<title>Database Login Page</title>
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
					<li><a href="dbLogin.jsp">Database Login</a></li>
					<li><a href="documentation.jsp">Project Documents</a></li>
					<li><a href="HomeWithCSS.jsp">Main Page</a></li>
				</ul>
			</div>
		</div>
		<div class="header">
			<div class="container">
				<div style="height: 10px">
					<h2>Database Access view</h2>
				</div>
			</div>
		</div>
		<div class="content">
			<div class="container">
				<div class="main" align="center">
					<h:form id="loginpage">
						<h:panelGrid columns="2" border="0" cellpadding="0"
							cellspacing="2">
							<f:facet name="header">
								<h:panelGroup style="display:block; text-align:center">
									<h:outputText value="Database Login" style="font-weight:bold;"></h:outputText>
								</h:panelGroup>
							</f:facet>
							<h:outputLabel value="UserName" style="font-weight:bold" />
							<h:inputText id="userNameInput"
								value="#{databaseConnect.userName}"></h:inputText>
							<h:outputLabel value="Password" style="font-weight:bold" />
							<h:inputSecret id="pwdInput" value="#{databaseConnect.password}"
								redisplay="true"></h:inputSecret>
							<h:outputLabel id="Host" value="Host"></h:outputLabel>
							<h:selectOneMenu id="DbHostMenu" value="#{databaseConnect.host}">
								<f:selectItem id="Hostitem1" itemLabel="localhost"
									itemValue="localhost" />
								<f:selectItem id="Hostitem2" itemLabel="server54"
									itemValue="server54" />
								<f:selectItem id="Hostitem3" itemLabel="server57"
									itemValue="server57" />
							</h:selectOneMenu>
							<h:outputLabel id="RDBMS" value="RDBMS"></h:outputLabel>
							<h:selectOneMenu id="DbRDBMSMenu"
								value="#{databaseConnect.rDbms}">
								<f:selectItem id="DBitem1" itemLabel="MySQL" itemValue="MySQL" />
								<f:selectItem id="DBitem2" itemLabel="DB2" itemValue="DB2" />
								<f:selectItem id="DBitem3" itemLabel="Oracle" itemValue="Oracle" />
							</h:selectOneMenu>
							<h:outputLabel id="DB_Schema" value="Database Schema">
							</h:outputLabel>
							<h:inputText id="DBschema" value="#{databaseConnect.schema}"></h:inputText>
							<f:facet name="footer">
								<h:panelGroup style="display:block; text-align:center">
									<h:commandButton id="Submit" value="Login"
										action="#{databaseConnect.fetchDBdata}"></h:commandButton>
								</h:panelGroup>
							</f:facet>
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
			<div class="container" align="right">&copy; Copyright QuizApp</div>
		</div>
	</f:view>
</body>
</html>