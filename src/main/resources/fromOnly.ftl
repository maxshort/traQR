<!DOCTYPE html>
<html>
	<head>
		<meta name=viewport content="width=device-width, initial-scale=1">
		<link type="text/css" rel="stylesheet" href="/Content/style.css" media="all">
		<title>TraQR: Add a Location</title>
	</head>

	
	<body>
		<div class="backgroundfade"></div>
		<div class="wrap">
			<br>
			<a href="/"><img src="/Content/TraQR_logo.png" height=32></a>
			<h1>Directions</h1>
			<br>
			
			
			<#include "/menu.ftl">
			
			<div class="content">
			    <h1>Select Destination</h1>
			
			    <form action = "/directions">
			        <input type="hidden" name="fromLocation" value="${tripStart.id}">
			        <select name ="toLocation">
			            <#list locationsWithoutStart as location>
			                <option value=${location.id}>${location.name}</option>
			            </#list>
			        </select>
			
			        <input type="submit" value="Get directions">
				</form>
			</div>
		</div>
	</body>
</html>