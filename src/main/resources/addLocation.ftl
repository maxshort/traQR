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
			<h1>Add a Location</h1>
			<br>
			
			
			<#include "/menu.ftl">
			
			<div class="content">
				<form method="POST" action="/locations">
					<label for="name">What is the name of your location?</label>	
					<br>
					<br>	
					<input name="name"  type="text" id="name" size="34" />
					<br>
					<br>
					<input  type="submit" value="Submit"/>
				</form>
			</div>
		</div>
	</body>
</html>