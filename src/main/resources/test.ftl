<html>

<body>
<h1> Hello world! </h1>
<h2> The current time is: ${time}</h2>

<h3>Primary Colors</h3>

<table>
    <#list colors as color>
        <tr> <td>${color}</td> </tr>
    <#else>
        No Colors!!!

    </#list>

</table>

<p>Name: ${person.name} <BR> Age:${person.age} </p>

</body>

</html>