<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:useBean id="index" scope="session" class="model.IndexBean" />
        <jsp:setProperty name="index" property="*" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OO Server Side Programming Assignment One</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/CSS/layout.css" />
</head>
<body>
	<div class="centerOne">
		<div class="centerTwo">
			<div class="outter">
				<div class="inner">
					<br>
					<h1>Screech</h1>
					<form action="Index" method="POST">
						<hr>
						<div class="skid">
							Skid Mark Length: <input type="text" name="skid" class = "textfield" value='${param.skid}' required> feet
						</div>
						<hr>
						<div class="surface">
							What type of surface were you driving on?<br>
							<input type="radio" name="surface" value="cem" ${index.checked == 'cem' ? 'checked' : ''}/>Portland Cement<br> 
							<input type="radio" name="surface" value="asph" ${index.checked == 'asph' ? 'checked' : ''}/>Asphalt<br>
							<input type="radio" name="surface" value="grav" ${index.checked == 'grav' ? 'checked' : ''}/>Gravel<br>
							<input type="radio" name="surface" value="ice" ${index.checked == 'ice' ? 'checked' : ''}/>Ice<br>
							<input type="radio" name="surface" value="snow" ${index.checked == 'snow' ? 'checked' : ''}/>Snow<br>
							<input type="checkbox" name="cookies" ${param.cookies!=null ? 'checked' : ''}> Save Cookies
						</div>
						<hr>
						<div class="submit">
							<input type="submit" value="Submit">
						</div>
					</form>
					<div class = "${index.calculation == 'Error' ? 'textError' : 'current' }">
						<h4>Current Calculation: ${index.calculation}</h4>
					</div>
					<div class = "indent">
						${requestScope.answer.length!=null ? 'Distance: '.concat(requestScope.answer.length).concat(' feet') : ''}
						${requestScope.answer.surfaceS!=null ? '  |  Surface: '.concat(requestScope.answer.surfaceS) : ''}
						${requestScope.answer!=null ? '  |  Starting Speed: '.concat(requestScope.answer.answer) : '' }
					</div>
					<div class = "previous">
						${cookie.savedSkid != null ? '<hr><h4>Previous Calculation:</h4>' : ''}
					</div>
					<div class = "indent">
						${cookie.savedSkid.value}
						${cookie.savedSurface != null ? '  |  '.concat(cookie.savedSurface.value) : ''}
						${cookie.savedSpeed != null ? '  |  '.concat(cookie.savedSpeed.value) : ''}
					</div>
					<hr>
					<div class = "info">
						<h4>Session Info</h4>
					</div>
					<div class = "indent">
						Server Name: ${pageContext.request.localAddr}<br>
						Client IP: ${pageContext.request.remoteAddr}<br>
						Saved Bean Variable: ${index.checked}<br>
						${sessionScope.sessionPar!=null ? 'Session Variable: '.concat(sessionScope.sessionPar) : ''}
					</div>
					<hr>
					<div class = "link">
						<a href = "Info">Get Info</a>
					</div>
					<br>
				</div>
			</div>
		</div>
	</div>
</body>
</html>