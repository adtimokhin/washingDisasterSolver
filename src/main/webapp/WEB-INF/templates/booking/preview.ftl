<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>News</title>
</head>
<body>
<h1>Here are all our news:</h1>
<div id="news">
    <#list timeTables as timeTable>
        <p>Washing machine number ${timeTable.getMachine().getId()}</p>
        <ul>
            <#list timeTable.timePeriods as periods>
                <li>${periods.toString()}</li>
            </#list>
        </ul>

    </#list>

</div>

<form method="post" action="/logout">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="Logout">
</form>

<#--This is the new form for the role changing method-->

<div>
    <form action="/news/change-role" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <input type="submit" value="Become an author">
    </form>
</div>

</body>
</html>

