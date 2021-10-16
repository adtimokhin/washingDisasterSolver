<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>News</title>
</head>
<body>
<h1>Here are all our news:</h1>
<div id="news">

    <#if errors??>
        <#list errors as e>
            <p>${e.getMessage()}</p>
        </#list>
    </#if>

    <#list timeTables as timeTable>
        <p>Washing machine number ${timeTable.getMachine().getId()}</p>
        <table>
            <tr>
                <#list timeTable.timePeriods as periods>
                    <th>${periods.getTimeBounds()}</th>
                </#list>
            </tr>
            <tr>
                <#list timeTable.timePeriods as periods>
                    <th><#if periods.isFree()> Free <#else> Occupied</#if></th>
                </#list>
            </tr>
        </table>
    </#list>

</div>

<form method="get" action="/booking/view/washing_machines">
    <input type="text" name="date" value="2021 10 16 00:00">
    <input type="submit">
</form>

<form method="post" action="/booking/add/washing_machine">
    <div>
        <p>
            machineId
        </p>
        <input type="text" name="machineId">
    </div>
    <div>
        <p>
            startHour
        </p>
        <input type="text" name="startHour">
    </div>
    <div>
        <p>
            startMinute
        </p>
        <input type="text" name="startMinute">
    </div>
    <div>
        <p>
            endHour
        </p>
        <input type="text" name="endHour">
    </div>
    <div>
        <p>
            endMinute
        </p>
        <input type="text" name="endMinute">
    </div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit">
</form>

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

