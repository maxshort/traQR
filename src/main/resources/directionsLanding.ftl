<!DOCTYPE html>
<html>
	<head>
		<link type="text/css" rel="stylesheet" href="/Content/style.css" media="all">
		<title>TraQR: Get Directions</title>
	</head>

	
	<body>
		<div class="backgroundfade"></div>
		<div class="wrap">
			<br>
			<h1>TraQR: Get Directions</h1>
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
		
					<input  type="submit" value="Submit"/>
					
				</form>
			</div>
		</div>
	</body>
</html>
