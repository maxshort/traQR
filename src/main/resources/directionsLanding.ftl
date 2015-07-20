<!DOCTYPE html>
<html>
	<head>
		<meta name=viewport content="width=device-width, initial-scale=1">
		<link type="text/css" rel="stylesheet" href="/Content/style.css" media="all">
		<title>TraQR: Get Directions</title>
	</head>

	
	<body>
		<div class="backgroundfade"></div>
		<div class="wrap">
			<br>
			<a href="/"><img src="/Content/TraQR_logo.png" height=32></a>
			<h1>Get Directions</h1>
			<br>
			
			
			<#include "/menu.ftl">
			
			<div class="content">
				<form method="GET" action="/directions/">
					
					
					<label for="fromLocation">Start Point:</label>
					<br>
		
					<select name="fromLocation" id="fromLocation">
						<#list locations as Location>
							<option value="${Location.id}">${Location.name}</option>
						</#list>
					</select>
					<br>
					<br>
		
					<label for="toLocation">End Point:</label>
					<br>
		
					<select name="toLocation" id="toLocation">
						<#list locations as Location>
							<option value="${Location.id}">${Location.name}</option>
						</#list>
					</select>
					<br>
					<br>
					
					<input  type="submit" value="Submit"/>
					
				</form>
			</div>
		</div>
	</body>
</html>
