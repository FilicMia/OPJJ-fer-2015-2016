<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<title>Voting:</title>
<body bgcolor="${sessionScope.pickedBgColor}">
	<h1>Give a vote to your favourite band:</h1>
	<p>Which one do you prefere? Give a vote by clicking the link!</p>
	<ol>
		<c:forEach var="band" items="${bands}">
				<li><a href="glasanjeGlasaj?id=${band.id}">${band.name}</a></li>
		</c:forEach>
	</ol>
</body>
</html>