<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Washing Disaster Solver | Sign Up Page</title>
    <link href="../../resources/css/main.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
</head>
<body style="background: rgb(214, 217, 208);">


<div class="container main-context">
    <#if errors??>
        <div class="row">
            <div class="col-12">
                <#list errors as error>
                    <h4 class="error-msg">${error.errorMessage}</h4>
                </#list>
            </div>
        </div>
    </#if>
    <div class="row">
        <div class="col-2"></div>
        <div class="col-8">
            <div class="form-container">
                <form method="post" action="/sign_up">
                    <div class="input-holder">
                        <h4 class="form-title">SING UP</h4>
                    </div>
                    <div class="input-holder">
                        <p class="mx-auto d-block">Enter your email address<br/></p>
                        <input type="text" name="email" class="mx-auto d-block">
                    </div>
                    <div class="input-holder">
                        <p class="mx-auto d-block">Enter your name</p>
                        <input type="text" name="name" class="mx-auto d-block">
                    </div>
                    <div class="input-holder">
                        <p class="mx-auto d-block">Enter your password</p>
                        <input type="password" name="password" class="mx-auto d-block">
                    </div>
                    <div class="input-holder">
                        <p class="mx-auto d-block">Confirm your password</p>
                        <input type="password" name="passwordTwo" class="mx-auto d-block">
                    </div>
                    <div class="input-holder">
                        <div class="popup">
                            <input type="submit" value="Register" class="mx-auto d-block button">
                        </div>
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                </form>
            </div>
        </div>
    </div>
    <div class="col-2"></div>
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

