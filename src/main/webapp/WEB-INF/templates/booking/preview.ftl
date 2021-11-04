<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Washing Disaster Solver
        | <#if machineType == "washing_machines">Washing Machines<#else >Drying Machines</#if></title>
    <link href="../../resources/css/main.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
</head>
<body style="background: rgb(214, 217, 208);">

<div class="container main-context">

    <div class="row">
        <div class="container">
            <div class="row">
                <div class="col-lg-4 col-0"></div>
                <div class="col-lg-4 col-12">
                    <a href="/booking/actions" class="button mx-auto d-block" style="">Return</a>
                </div>
                <div class="col-lg-4 col-0"></div>
            </div>
        </div>
    </div>

    <#if timeTables??>
        <div class="row">
            <div class="col-2"></div>
            <div class="col-8">
                <#list timeTables as timeTable>
                    <div class="table-container">
                        <h3><#if machineType == "washing_machines">Washing Machine<#else >Drying Machine</#if>
                            (id: ${timeTable.getMachine().getId()})</h3>
                        <table>
                            <tr>
                                <#list timeTable.timePeriods as periods>
                                    <th>${periods.getTimeBounds()}</th>
                                </#list>
                            </tr>
                            <tr>
                                <#list timeTable.timePeriods as periods>
                                    <#if periods.isFree()>
                                        <th style="background-color: #F2F2F2">Free</th> <#else>
                                        <th>Occupied</th></#if>
                                </#list>
                            </tr>
                        </table>
                    </div>
                </#list>
            </div>
            <div class="col-2"></div>
        </div>
    </#if>

    <div class="row">
        <div class="col-2"></div>
        <div class="col-8">
            <div class="form-container">
                <form method="get" action="/booking/view/${machineType}">
                    <div class="input-holder">
                        <input type="text" name="date" value="${date}" class="mx-auto d-block">
                    </div>
                    <div class="input-holder">
                        <input type="submit" class="mx-auto d-block button" value="Look up machines for this date">
                    </div>
                </form>
            </div>
        </div>
        <div class="col-2"></div>
    </div>

    <#if error??>
        <div class="row">
            <div class="col-12">
                <h4 class="error-msg">${error}</h4>
            </div>
        </div>
    </#if>

    <#if msg??>
        <div class="row">
            <div class="col-12">
                <h4 class="error-msg">${msg}</h4>
            </div>
        </div>
    </#if>

    <div class="row">
        <div class="col-2"></div>
        <div class="col-8">
            <div class="form-container">
                <form method="post" action="/booking/add/${machineType}">
                    <div class="input-holder">
                        <h4 class="form-title">MAKE A RESERVATION</h4>
                    </div>
                    <div class="input-holder">
                        <p class="mx-auto d-block">Enter machine's id</p>
                        <input type="text" name="machineId" class="mx-auto d-block">
                    </div>
                    <div class="input-holder">
                        <p class="mx-auto d-block">Enter starting hour of your booking</p>
                        <input type="text" name="startHour" class="mx-auto d-block">
                    </div>
                    <div class="input-holder">
                        <p class="mx-auto d-block">Enter starting minute of your booking</p>
                        <input type="text" name="startMinute" class="mx-auto d-block">
                    </div>
                    <div class="input-holder">
                        <p class="mx-auto d-block">Enter finishing hour of your booking</p>
                        <input type="text" name="endHour" class="mx-auto d-block">
                    </div>
                    <div class="input-holder">
                        <p class="mx-auto d-block">Enter finishing minute of your booking</p>
                        <input type="text" name="endMinute" class="mx-auto d-block">
                    </div>
                    <input type="hidden" name="date" value="${date}">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    <div class="input-holder">
                        <input type="submit" value="Make a reservation" class="mx-auto d-block button">
                    </div>
                </form>
            </div>
        </div>
        <div class="col-2"></div>
    </div>


</div>

<!-- Footer -->
<footer class="footer">
    <div class="container">
        <div class="row">
            <!-- photo -->
            <div class="col-md-4 col-sm-5 col-xs-12" id="logo-container">
                <div class="logo-container">
                    <img src="../../resources/images/adtimokhinLOGOfooter.svg" class="mx-auto d-block">
                </div>
            </div>
            <!-- Other action buttons -->
            <div class="col-md-4 col-sm-2 col-xs-6">
                <a href="#" class="footer-link-title">adtimokhin@gmail.com</a>
                <a href="https://github.com/adtimokhin/" target="_blank" class="footer-link">GitHub</a>
                <a href="https://www.linkedin.com/in/aleksandr-timokhin-5300361b6" target="_blank"
                   class="footer-link">LinkedIn</a>
                <a href="https://www.instagram.com/adtimokhin/" target="_blank" class="footer-link">Instagram</a>
            </div>

            <div class="col-md-4 col-sm-3 col-xs-6">
                <a href="#" class="footer-link-title">Terms & policy</a>
            </div>
        </div>
    </div>
</footer>
</body>
</html>

