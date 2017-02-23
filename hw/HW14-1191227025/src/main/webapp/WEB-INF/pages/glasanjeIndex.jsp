<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<title>Voting:</title>
<body>
	<h1>${poll.title}</h1>
	<p>${poll.message}</p>
	<ol>
		<c:forEach var="option" items="${options}">
				<li><a href="glasanjeGlasaj?id=${option.id}">${option.optionTitle}</a></li>
		</c:forEach>
	</ol>
</body>
</html>