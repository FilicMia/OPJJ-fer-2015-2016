<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Log in</title>

<style type="text/css">
.greska {
	font-family: fantasy;
	font-weight: bold;
	font-size: 0.9em;
	color: red;
}
</style>
</head>
<header>
</header>

<c:choose>
	<c:when test="${errors!=null&&!errors.isEmpty()}">
	<c:forEach var="err" items="${errors}">
			<c:out value="${err}" />
	</c:forEach>
	</c:when>

	<c:otherwise>
		<body>
			<form action="/blog/servleti/entry/add" method="get">
				Title <input type="text" name="title" 
				value='<c:out value="${zapis2.title}"/>' size="30"><br>
				Text<textarea name = "text" rows="30" cols="30"><c:out value="${zapis2.text}" /></textarea><br>
				<input type="hidden" name="author" value="${sessionScope.get('current.user.nick')}">
				<input type="submit" name="metoda" value="Save">
				<input type="submit" name="metoda" value="Cancel">
			</form>
		</body>
	</c:otherwise>

</c:choose>
</body>
</html>