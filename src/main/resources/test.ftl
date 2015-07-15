<html>

<body>
<h1> Welcome to traQR! </h1>
<h2> The current time is: ${time}</h2>

<h3>Directions Template</h3>

<table>
    <#list nodes as node>
        <tr><td>${node}</td><td>${direction}</td></tr>
    <#else>
        No Directions Available.

    </#list>

</table>

</body>

</html>