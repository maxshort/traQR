<html>

<body>
	<#include "/menu.ftl">

</body>

</html>



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
			<h1>TraQR: Directions</h1>
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