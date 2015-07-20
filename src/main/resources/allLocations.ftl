<!DOCTYPE html>
<html>
	<head>
		<link type="text/css" rel="stylesheet" href="/Content/style.css" media="all">
		<title>TraQR: All Locations</title>
	</head>

	<body>
		<div class="backgroundfade"></div>
		<div class="wrap">
			<br>
			<h1>TraQR: All Locations</h1>
			<br>
			
			
			<#include "/menu.ftl">
			
			<div class="content">
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
			</div>
		</div>
	</body>
</html>