<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css" />
</head>
<body><br><br>
	<div class="centerMain">
		<c:choose>
			<c:when test="${not empty logged}">
				<form action="Logout" method = "POST">
					<input type="submit" value="Log Out">
				</form>
			</c:when>
			<c:otherwise>
				<form action="${requestScope.register ? 'Register' : initParam.login}" method="POST">
					<div class="centerDiv">Login/Register:</div>
					<div class="retryDiv">${requestScope.error == null ? ' ' : requestScope.error }</div>
					Username: <input type = "text" name = "name"><br>
					Password: <input type = "password" name = "password"><br><br>
					<div class="centerDiv">
						<input type="submit" value="${requestScope.register ? 'Register' : initParam.login}" />
					</div>
				</form>
				<a href="${requestScope.register ? initParam.login : 'Register?true'}">${requestScope.register ? 'Homepage' : 'Create Account'}</a>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>