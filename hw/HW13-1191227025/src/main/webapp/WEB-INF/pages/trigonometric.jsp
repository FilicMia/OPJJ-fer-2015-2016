<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
 	<head>
		<title>Trigonometric function calculator</title>
	</head>
	<body bgcolor="${sessionScope.pickedBgColor}">
		
		<table border=1>
			<tr><th>Number:</th>
			<th>Sinus value:</th>
			<th>Cosinus value:</th></tr>
			<c:forEach var="tl" items="${trigonometricList}">
				<tr><td>${tl.number}</td>
				<td>${tl.sin}</td>
				<td>${tl.cos}</td></tr>
			</c:forEach>
		</table>
		
		<p><a href="index.jsp">Main page</a></p>
	</body>
</html>