<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>News</title>
</head>
<body>

<h3>${name}</h3>

<#if msg??>
    <p>${msg}</p>
</#if>


<#if washings??>
    <#list washings as w>
        <p>${w.presentNicely()}</p>
        <form method="post" action="/booking/cancel/washing_machines">
            <input type="hidden" name="id" value="${w.getId()}">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <input type="submit" value="Cancel">
        </form>
    </#list>
<#else>
    <p>You have to bookings for washing machines</p>
</#if>

<#if dryings??>
    <#list dryings as d>
        <p>${d.presentNicely()}</p>
        <form method="post" action="/booking/cancel/drying_machines">
            <input type="hidden" name="id" value="${d.getId()}">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <input type="submit" value="Cancel">
        </form>
    </#list>
<#else>
    <p>You have to bookings for drying machines</p>
</#if>

<form method="post" action="/logout">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="Logout">
</form>

</body>
</html>

