<!DOCTYPE html>
<html>
	<head>
		<link type="text/css" rel="stylesheet" href="/Content/style.css" media="all">
		<title>TraQR: Success!</title>
	</head>

	
	<body>
		<div class="backgroundfade"></div>
		<div class="wrap">
			<br>
			<h1>TraQR: Success!</h1>
			<br>
			
			
			<#include "/menu.ftl">
			
			<div class="content">
				<h1>Success!</h1>
    <p>Great job. You have added ${location.name} as a location</p>
    <p>This location can't be reached by anything because it is not connected to anything else. You may want to <a href="/connections">add a connection</a>.</p>
    <p>You can also <a href="/qr/location.pdf?location=${location.id}">print a QR code for this location</a>.</p>
			</div>
		</div>
	</body>
</html>