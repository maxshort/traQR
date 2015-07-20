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
<<<<<<< HEAD
				<h2>All locations:</h2>
				
				<#list locations as Location>
					<a href="/qr/?location=${Location.id}"><p>${Location.name}</p></a>
				</#list>
				
=======
				<h2>All locations:<h2>
				<table>
					<#list locations as Location>
						<tr>
							<td>
								${Location.id}
							</td>
							<td>
								<a href="/qr/location.pdf?location=${Location.id}">${Location.name}</a>
							</td>
						</tr>
					</#list>
				</table>
>>>>>>> 49b37c7ceba24b7b985d0b0fc5a68f7553c3c6fe
			</div>
		</div>
	</body>
</html>