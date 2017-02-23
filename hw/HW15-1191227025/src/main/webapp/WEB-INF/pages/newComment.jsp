<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
</head>
<body>
	<form action="/blog/servleti/comment/add" method="get">
		E-mail <input type="text" name="email" size="30"><br>
		Message
		<textarea name="message" rows="5" cols="50">  </textarea>
		<br> <input type="hidden" name="id" value="${entry.id}">
		<input type="hidden" name="nick" value="${entry.creator.nick }">
		<input type="submit" name="metoda" value="Save"> <input
			type="submit" name="metoda" value="Cancel">
	</form>
</body>

</body>
</html>