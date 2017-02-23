<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Registration</title>

<style type="text/css">
.greska {
	font-family: fantasy;
	font-weight: 200;
	font-size: 0.9em;
	color: red;
}
</style>
</head>

<body>
	<form action="register" method="post">
		First Name <input type="text" name="first.name" /><br> Last Name
		<input type="text" name="last.name" /><br> Nickname <input
			type="text" name="nick" /><br> E-mail <input type="text"
			name="email" /><br> Password <input type="password"
			name="password" /><br> <input type="submit" name="metoda"
			value="Register">
	</form>
</body>

<body>
	<div class="greska">
		<c:if test="${error!=null}">
			<c:out value="${error}" /><br>
		<c:forEach var="e" items="${errors}">
			<c:out value="${e}"	/><br>
		</c:forEach>	
		</c:if>
	</div>
</body>
</html>