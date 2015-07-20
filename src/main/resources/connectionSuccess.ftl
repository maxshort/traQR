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
	    		<h2>Success!</h2>
	    		<p>You have added a connection from ${connection.start.name} to ${connection.end.name}.</p>
			</div>
		</div>
	</body>
</html>