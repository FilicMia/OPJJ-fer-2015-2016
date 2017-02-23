<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<body>
<c:choose>
<c:when test="${sessionScope.get('current.user.id') != null}" >
	<h4>Logged in as: <c:out value="${sessionScope.get('current.user.first')}"/>  <c:out value="${sessionScope.get('current.user.last')}"/>
	<a href="/blog/servleti/logout">Log Out</a></h4>
</c:when>

<c:otherwise>
	<h4>Nobody is logged in.</h4>
<jsp:include page="/WEB-INF/pages/login.jsp"/>
</c:otherwise>
</c:choose>
</body>