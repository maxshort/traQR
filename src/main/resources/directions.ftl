<html>

<body>
<h1> Welcome to traQR! </h1>
<#include "/menu.ftl">

<h3>Directions for trip from ${tripStart.name} to ${tripEnd.name}</h3>

<table border="1" style="width:100%">
        <tr>
        <th>Start Location</th>
        <th>End location</th>
        <th>Directions</th>
        <th>Estimated Duration</th>
        </tr>
    <#list trip.connections as Connection>
        <tr>
            <td>${Connection.start.name}</td>
            <td>${Connection.end.name}</td>
            <td>${Connection.description}</td>
            <td>${Connection.estimatedTime}</td>
        </tr>
    <#else>
        No Directions Available.

    </#list>



</table>

</body>

</html>