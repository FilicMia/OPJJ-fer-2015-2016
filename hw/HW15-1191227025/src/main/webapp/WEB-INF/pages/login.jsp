<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<body>
	<div class="greska">
		<c:out value="${status}" />
		<c:forEach var="err" items="${errors}">
					<p><c:out value="${err}" /></p>
		</c:forEach>
	</div>
	<c:if test="${sessionScope.get('current.user')==null}">
		<body>
			<form action="/blog/servleti/main" method="post">
				nickname <input type="text" name="nick"
					value='<c:out value="${zapis.nick}"/>' size="30"><br>

				password <input type="password" name="password" size="30"><br>

				<input type="submit" name="metoda" value="Log In">
			</form>
		</body>

		<c:if test="${!errors.isEmpty()}">
			<br>
			<ul>
				<c:forEach var="err" items="${errors}">
					<li><c:out value="${err}" /></li>
				</c:forEach>
			</ul>
		</c:if>

		<body>
			<h4>
				<a href="/blog/servleti/register"> Register!!</a>
			</h4>
		</body>
	</c:if>
</body>