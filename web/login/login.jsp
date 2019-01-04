<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/../login/login.css"/>
    <script src="https://cdn.bootcss.com/jquery/2.2.4/jquery.js"></script>
    <script>
            window.jQuery || document.write('<script src="js/jquery-2.1.1.min.js"><\/script>')
    </script>
    <script src="${pageContext.request.contextPath}/../login/index.js" type="text/javascript"></script>
</head>

    <body>
        <div class="homepage-hero-module">
            <div class="video-container">
                <div class="filter"></div>
                <video autoplay loop class="fillWidth">
                    <source src="${pageContext.request.contextPath}/../login/I_Just_Wanted.mp4" type="video/mp4" />Your browser does not support the video tag. I suggest you upgrade your browser.
                    <source src="${pageContext.request.contextPath}/../login/I_Just_Wanted.webm" type="video/webm" />Your browser does not support the video tag. I suggest you upgrade your browser.
                </video>
            </div>
        </div>
        <div class="htmleaf-container">
            <div class="wrapper">
                <div class="content">
                    <h1 align="center"style="color:white ; font-size:75px">文件管理系统</h1>
                    <form class="form" action="login.action" method="POST">
                        <input type="text" placeholder="UserName" name="userName">
                        <input type="password" placeholder="Password" name="userPassword">
                        <input type="submit" id="login-button" value="Login">
                    </form>
                </div>
            </div>
        </div>

</body>
</html>