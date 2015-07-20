<!DOCTYPE html>
<html>
	<head>
		<meta name=viewport content="width=device-width, initial-scale=1">
		<link type="text/css" rel="stylesheet" href="/Content/style.css" media="all">
		<title>TraQR: Directions</title>
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
				<h3>Directions for trip from ${tripStart.name} to ${tripEnd.name}</h3>
				<hr>
				
				
				    <#list trip.connections as Connection>
			    		<div class="directions">
					        <p class="description">${Connection.description}</p>
					        <p class="point">${Connection.end.name}</p>
					        <p class="time">${Connection.niceDuration}</p>
				        </div>
				        <hr style="clear: both;">
				    <#else>
				        <p>No Directions Available.</p>
				    </#list>
				<h5 style="clear: both">Total estimated time: ${trip.niceDuration}</h5>
			</div>
		</div>
		
	</body>
</html>