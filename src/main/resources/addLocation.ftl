<!DOCTYPE html>
<html>
	<head>
		<title>Add a location</title>
	</head>
	<style>
		
	</style>
	
	<body>
		<#include "/menu.ftl">
		<form method="POST" action="/locations">
			<label for="name">What is the name of your location?</label>	
			<br>
			<br>	
			<input name="name"  type="text" id="name" size="40" />
			<input  type="submit" value="Submit"/>
		</form>
	</body>

</html