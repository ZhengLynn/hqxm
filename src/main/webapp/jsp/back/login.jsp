<%@ page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>登录</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css"/>
    <script src="${pageContext.request.contextPath}/static/js/jquery-3.4.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.min.js"></script>
    <style type="text/css">
        body {
            background-image: url("${pageContext.request.contextPath}/static/img/background.jpg");
        }

        .col-centered {
            margin-top: 150px;
        }
    </style>
    <script>
        //刷新验证码
        function flushCode() {
            $('#num').attr('src', '${pageContext.request.contextPath}/admin/securityCode?' + Math.random());
        }

        //登录
        function login() {
            let username = $('#username').val();
            let password = $('#password').val();
            let code = $('#code').val();
            $.ajax({
                url: '${pageContext.request.contextPath}/admin/login',
                method: 'post',
                data: {
                    'username': username,
                    'password': password,
                    'code': code
                },
                success: function (result) {
                    if (result == '登陆成功') {
                        location.href = '${pageContext.request.contextPath}/jsp/back/index.jsp';
                    } else {
                        alert(result);
                    }
                }
            });
        }
    </script>
</head>
<body>
<div class="container-fluid">
    <div class="row row-centered">
        <div class="col-sm-offset-4 col-sm-4 col-centered">
            <%--面板--%>
            <div class="panel panel-default" style="background-color:rgba(223,223,223,0.3);">
                <div class="panel-body">
                    <div class="col-sm-offset-1 col-sm-10">
                        <p class="text-center h2">持名法洲后台管理系统</p>
                        <br><br>
                        <%--表单--%>
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">用户名:</label>
                                <div class="col-sm-9">
                                    <input type="text" id="username" name="username" class="form-control"
                                           placeholder="请输入用户名">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">密码:</label>
                                <div class="col-sm-9">
                                    <input type="password" id="password" name="password" class="form-control"
                                           placeholder="请输入密码">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">验证码:</label>
                                <div class="col-sm-5">
                                    <input type="text" id="code" name="code" class="form-control"
                                           placeholder="请输入验证码">
                                </div>
                                <div class="col-sm-4">
                                    <img onclick="flushCode()" id="num"
                                         src="${pageContext.request.contextPath}/admin/securityCode">
                                </div>
                            </div>
                            <br>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-8">
                                    <button type="button" onclick="login()" class="btn btn-warning btn-block">登录
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
