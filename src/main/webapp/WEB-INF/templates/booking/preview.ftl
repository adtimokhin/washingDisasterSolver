<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>News</title>
</head>
<body>
<h1>Here are all our news:</h1>
<div id="news">

    <#if error??>
        <p>${error}</p>
    </#if>

    <#if timeTables??>
        <#list timeTables as timeTable>
            <p>${machineType} number ${timeTable.getMachine().getId()}</p>
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
    </#if>
</div>

<form method="get" action="/booking/view/${machineType}">
    <input type="text" name="date" value="${date}">
    <input type="submit">
</form>

<form method="post" action="/booking/add/${machineType}">
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
    <input type="hidden" name="date" value="${date}">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit">
</form>

<form method="post" action="/logout">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="Logout">
</form>

</body>
</html>

