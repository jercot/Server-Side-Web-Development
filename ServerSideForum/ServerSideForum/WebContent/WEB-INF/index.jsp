<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css" />
</head>
<body>
	<br>
	<div class="center">
		<a href="${not empty logged ? 'Logout' : 'Account'}">${not empty logged ? 'Log Out' : 'Login/Create Account'}</a>
		<a href="${not empty logged ? 'Members' : 'Account'}">Members</a><br><br>
		Forums<br><br>
		<c:choose>
			<c:when test="${not empty forums}">
				<c:forEach var="item" items="${forums}">
					<div class = "post">
  						<a href="${pageContext.request.contextPath}/Forums/${item.linkName}.${item.id}">${item.name}</a><br> 
 					</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
				Something went wrong. <a href="${pageContext.request.contextPath}">Refresh</a>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>