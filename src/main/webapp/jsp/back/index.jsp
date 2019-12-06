<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>持名法洲后台管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/jqGrid/css/ui.jqgrid-bootstrap.css"/>
    <script src="${pageContext.request.contextPath}/static/js/jquery-3.4.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/jqGrid/js/jquery.jqGrid.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/jqGrid/i18n/grid.locale-cn.js"></script>
    <script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/ajaxfileupload.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/ent_UUID.js"></script>
    <script src="${pageContext.request.contextPath}/static/kindeditor/kindeditor-all-min.js"></script>
    <script src="${pageContext.request.contextPath}/static/kindeditor/lang/zh-CN.js"></script>
    <script src="${pageContext.request.contextPath}/static/echarts/echarts.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/echarts/china.js"></script>
    <script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <script>
        //安全退出
        function safeOut() {
            $.get(
                '${pageContext.request.contextPath}/admin/safeOut',
                function (result) {
                    if (result == null || result == '') {
                        location.href = '${pageContext.request.contextPath}/jsp/back/login.jsp';
                    }
                }
            );
        }

        //富文本编辑器初始化
        KindEditor.ready(function (K) {
            window.editor = K.create('#myEditor', {
                width: '100%',
                height: '300px',
                uploadJson: '${pageContext.request.contextPath}/article/upload',
                allowFileManager: true,
                fileManagerJson: '${pageContext.request.contextPath}/article/showAllImgs'
            });
        })
    </script>
    <style>
        .page-header {
            margin-top: -10px;
        }

        th {
            text-align: center;
        }

        .ui-jqgrid tr.jqgrow td {
            white-space: normal !important;
            height: auto;
            vertical-align: text-top;
            padding-top: 2px;
            display: table-cell;
        }
    </style>
</head>
<body>
<!--导航-->
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="index.jsp">持名法洲后台管理系统</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">欢迎: ${sessionScope.admin.username}</a></li>
                <li><a href="#" onclick="safeOut()">退出登录</a></li>
            </ul>
        </div>
    </div>
</nav>
<%--主体--%>
<div class="container-fluid">
    <div class="row">
        <%--下拉列表--%>
        <div class="col-sm-2">
            <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingOne">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                                用户管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse">
                        <div class="panel-body">
                            <div class="list-group">
                                <a href="javascript:$('#content').load('./user/userList.jsp')" class="list-group-item">用户列表</a>
                                <a href="javascript:$('#content').load('./user/userBar.jsp')" class="list-group-item">注册趋势图</a>
                                <a href="javascript:$('#content').load('./user/userMap.jsp')" class="list-group-item">地理分布图</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingTwo">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                                上师管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse">
                        <div class="panel-body">
                            <div class="list-group">
                                <a class="list-group-item"
                                   href="javascript:$('#content').load('./guru/guru.jsp')">上师列表</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingThree">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
                                文章管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse">
                        <div class="panel-body">
                            <div class="list-group">
                                <a class="list-group-item"
                                   href="javascript:$('#content').load('./article/articleShow.jsp')">文章列表</a>
                                <a class="list-group-item"
                                   href="javascript:$('#content').load('./article/articleSearch.jsp')">文章搜索</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingFour">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseFour">
                                专辑管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFour" class="panel-collapse collapse">
                        <div class="panel-body">
                            <div class="list-group">
                                <a class="list-group-item"
                                   href="javascript:$('#content').load('./album/album.jsp')">专辑列表</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingFive">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseFive">
                                轮播图管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFive" class="panel-collapse collapse">
                        <div class="panel-body">
                            <div class="list-group">
                                <a class="list-group-item" href="javascript:$('#content').load('./banner/banner.jsp')">轮播图列表</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%--内容--%>
        <div id="content">
            <div class="col-sm-10">
                <%--面板--%>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <p class="h3">欢迎使用持名法州后台管理系统!</p>
                    </div>
                </div>
                <%--轮播图--%>
                <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                    <!-- 指示灯 -->
                    <ol class="carousel-indicators">
                        <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                        <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                        <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                    </ol>

                    <!-- 图片 -->
                    <div class="carousel-inner">
                        <div class="item active">
                            <img style="height: 695px" src="${pageContext.request.contextPath}/static/img/banner0.jpg">
                            <div class="carousel-caption">
                                0.jpg
                            </div>
                        </div>
                        <div class="item">
                            <img style="height: 695px" src="${pageContext.request.contextPath}/static/img/banner1.jpg">
                            <div class="carousel-caption">
                                1.jpg
                            </div>
                        </div>
                        <div class="item">
                            <img style="height: 695px" src="${pageContext.request.contextPath}/static/img/banner2.jpg">
                            <div class="carousel-caption">
                                2.jpg
                            </div>
                        </div>
                    </div>

                    <!-- 控制 -->
                    <a class="left carousel-control" href="#carousel-example-generic" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="right carousel-control" href="#carousel-example-generic" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
<br><br>
<div class="panel-footer">
    <p class="text-center">@百知教育 baizhi@zparkhr.com.cn</p>
</div>
<%--添加文章 - 模态框--%>
<div class="modal fade bs-example-modal-lg" id="articleModal">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span>&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">添加文章</h4>
            </div>
            <div class="modal-body">
                <form method="post" id="modalForm">
                    <div class="form-group">
                        <label>标题</label>
                        <input type="hidden" name="id" id="id" value="">
                        <input type="text" class="form-control" name="title" id="title" value=""
                               placeholder="请输入文章标题"/>
                    </div>
                    <div class="form-group">
                        <label>所属上师</label>
                        <select id="guruList" name="guru_id" class="form-control"></select>
                    </div>
                    <div class="form-group">
                        <label>发布时间</label>
                        <input type="date" class="form-control" name="release_date" id="release_date" value=""/>
                    </div>
                    <div class="form-group">
                        <label>封面</label>
                        <input type="file" name="imgFile" id="imgFile"/>
                    </div>
                    <div class="form-group">
                        <label>内容</label>
                        <textarea name="content" id="myEditor"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer" id="modal_footer"></div>
        </div>
    </div>
</div>
</body>
</html>
