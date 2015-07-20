<!DOCTYPE html>
<html>
	<head>
		<link type="text/css" rel="stylesheet" href="/Content/style.css" media="all">
		<title>TraQR: Add a Location</title>
	</head>

	
	<body>
		<div class="backgroundfade"></div>
		<div class="wrap">
			<br>
			<h1>TraQR: Add a Location</h1>
			<br>
			
			
			<#include "/menu.ftl">
			
			<div class="content">
				<form method="POST" action="/locations">
					<label for="name">What is the name of your location?</label>	
					<br>
					<br>	
					<input name="name"  type="text" id="name" size="40" />
					<input  type="submit" value="Submit"/>
				</form>
			</div>
		</div>
	</body>
</html>