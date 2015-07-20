<!DOCTYPE html>
<html>
	<head>
		<meta name=viewport content="width=device-width, initial-scale=1">
		<link type="text/css" rel="stylesheet" href="/Content/style.css" media="all">
		<title>TraQR: All Locations</title>
	</head>

	<body>
		<div class="backgroundfade"></div>
		<div class="wrap">
			<br>
			<a href="/"><img src="/Content/TraQR_logo.png" height=32></a>
			<h1>All Locations</h1>
			<br>
			
			
			<#include "/menu.ftl">
			
			<div class="content">
				<h2>All locations:</h2>
				
				<#list locations as Location>
					<a href="/qr/?location=${Location.id}"><p>${Location.name}</p></a>
				</#list>
				
			</div>
		</div>
	</body>
</html>