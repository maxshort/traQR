<!DOCTYPE html>
<html>
	<head>
		<meta name=viewport content="width=device-width, initial-scale=1">
		<link type="text/css" rel="stylesheet" href="/Content/style.css" media="all">
		<title>TraQR: Success!</title>
	</head>

	
	<body>
		<div class="backgroundfade"></div>
		<div class="wrap">
			<br>
			<a href="/"><img src="/Content/TraQR_logo.png" height=32></a>
			<h1>Success!</h1>
			<br>
			
			
			<#include "/menu.ftl">
			
			<div class="content">
<<<<<<< HEAD
				<h2>Success!</h2>
			    <p>Great job. You have added ${location.name} as a location</p>
			    <p>This location can't be reached by anything because it is not connected to anything else. You may want to <a href="/connections">add a connection</a>.</p>
			    <p>You can also <a href="/qr?location=${location.id}">print a QR code for this location</a>.</p>
=======
				<h1>Success!</h1>
    <p>Great job. You have added ${location.name} as a location</p>
    <p>This location can't be reached by anything because it is not connected to anything else. You may want to <a href="/connections">add a connection</a>.</p>
    <p>You can also <a href="/qr/location.pdf?location=${location.id}">print a QR code for this location</a>.</p>
>>>>>>> 49b37c7ceba24b7b985d0b0fc5a68f7553c3c6fe
			</div>
		</div>
	</body>
</html>