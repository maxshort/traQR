<!DOCTYPE html>
<html>
	<head>
		<meta name=viewport content="width=device-width, initial-scale=1">
		<link type="text/css" rel="stylesheet" href="/Content/style.css" media="all">
		<title>TraQR: Add Connection</title>
	</head>

	
	<body>
		<div class="backgroundfade"></div>
		<div class="wrap">
			<br>
			<a href="/"><img src="/Content/TraQR_logo.png" height=32></a>
			<h1>Add Connection</h1>
			<br>
			
			
			<#include "/menu.ftl">
			
			<div class="content">
				<form method="POST" action="/connections">
					
					
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
					
					<label for="minutes">Estimated walk-time in whole minutes:</label>
					<br>
				
					<input name="minutes"  type="text" id="minutes" size="34" />
					<br>
					<br>
		
			
					<label for="description">Directions</label>
					<br>
				
					<textarea name="description" cols="36" rows="10" id="description" ></textarea>
					<br>
			
			
					<input  type="submit" value="Submit"/>
						
						
					
				</form>

			</div>
		</div>
	</body>
</html>