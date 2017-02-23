<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Welcome!</title>
	</head>
   <body  bgcolor="${sessionScope.pickedBgColor}">
   		<ul>
   			<li><a href="colors.jsp">Background color chooser</a></li>
   			<li><a href="trigonometric?a=0&b=90">
   				Trigonometric function calculator</a></li>
   			<li><a href="stories/funny.jsp">Short story</a></li>
   			<li><a href="report.jsp">Os usage</a></li>
   			<li><a href="powers?a=1&b=100&n=3">Powers</a></li>
   			<li><a href="appInfo.jsp">App info</a></li>
   			<li><a href="glasanje">Vote</a></li>
   		</ul>
   </body>
</html>
