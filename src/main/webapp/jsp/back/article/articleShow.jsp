<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8" %>
<script>
    //画出jqGrid
    $(function () {
        $('#articleList').jqGrid({
            styleUI: 'Bootstrap',
            url: '${pageContext.request.contextPath}/article/findAll',
            datatype: 'json',
            mtype: 'post',
            colNames: ['ID', '标题', '作者', '发布时间', '封面', '内容', '操作'],
            colModel: [
                {name: 'id', align: 'center', search: false},
                {name: 'title', editable: true, align: 'center', editrules: {required: true}},
                {name: 'author', editable: true, align: 'center', editrules: {required: true}},
                {
                    name: 'release_date',
                    editable: true,
                    edittype: 'date',
                    formatter: 'date',
                    align: "center",
                    formatoptions: {newformat: 'Y-m-d H:i:s'}
                },
                {
                    name: 'cover', editable: true, edittype: 'file', align: 'center',
                    editoptions: {enctype: 'multipart/form-data'},
                    formatter: function (value, options, row) {
                        if (row.cover == null || row.cover == '') return '';
                        let temp = '<img height="70px" src="' + row.cover + '"/>';
                        return temp;
                    }
                },
                {name: 'content', editable: true, align: 'center', editrules: {required: true}, hidden: true},
                {
                    name: 'options', formatter: function (value, options, row) {
                        return `<a href="javascript:;" class="btn" title="修改文章信息" onclick="updateShow('` + row.id + `')"><span class="glyphicon glyphicon-pencil"></span></a>`
                    }
                }
            ],
            multiselect: true,
            pager: '#articlePager',
            rowNum: 6,
            rowList: [3, 6, 9, 12],
            viewrecords: true,
            editurl: '${pageContext.request.contextPath}/article/operation',
            autowidth: true,
            height: 504
        }).navGrid(
            '#articlePager',
            {
                edit: false, add: false, del: true, search: false,
                deltext: '删除'
            },
            {},
            {},
            {},
            {}
        );
    });

    //打开模态框 并设置提交按钮为修改
    function updateShow(id) {
        //根据id获取当前行内容
        let data = $('#articleList').jqGrid('getRowData', id);
        $('#id').val(data.id);
        $('#title').val(data.title);
        $('#author').val(data.author);
        //禁用日期和文件
        $('#release_date').val(data.release_date).attr('disabled', true);
        $('#imgFile').attr('disabled', true);
        //回显富文本域内容
        KindEditor.html("#myEditor", data.content);
        console.log(data)
        $('#modal_footer').empty().append(`<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>` +
            `<button type="button" onclick="update()" class="btn btn-primary">修改</button>`)
        $('#articleModal').modal('show');
    }

    //打开模态框 并设置提交按钮为添加
    function addShow() {
        //清空表单内容
        $('#modalForm')[0].reset();
        //启用日期和文件
        $('#release_date').attr('disabled', false);
        $('#imgFile').attr('disabled', false);
        //清空富文本域内容
        KindEditor.html("#myEditor", "");
        $('#modal_footer').empty().append(`<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>` +
            `<button type="button" onclick="add()" class="btn btn-primary">添加</button>`)
        $('#articleModal').modal('show');
    }

    //修改文章
    function update() {
        editor.sync();
        let author = $('#guruList')[0].options[$('#guruList')[0].options.selectedIndex].text;
        $.ajax({
            url: '${pageContext.request.contextPath}/article/operation',
            data: {
                oper: 'edit',
                sign: 'cover',
                id: $('#id').val(),
                title: $('#title').val(),
                author: author,
                release_date: $('#release_date').val(),
                content: $('#myEditor').val(),
                guru_id: $('#guruList').val()
            },
            datatype: 'json',
            type: 'post',
            fileElementId: 'imgFile',
            success: function () {
                $('#articleModal').modal('hide');
                $('#articleList').trigger('reloadGrid');
            }
        })
    }

    //添加文章
    function add() {
        editor.sync();
        let author = $('#guruList')[0].options[$('#guruList')[0].options.selectedIndex].text;
        $.ajaxFileUpload({
            url: '${pageContext.request.contextPath}/article/operation',
            data: {
                oper: 'add',
                sign: 'cover',
                title: $('#title').val(),
                author: author,
                release_date: $('#release_date').val(),
                content: $('#myEditor').val(),
                guru_id: $('#guruList').val()
            },
            datatype: 'json',
            type: 'post',
            fileElementId: 'imgFile',
            success: function () {
                $('#articleModal').modal('hide');
                $('#articleList').trigger('reloadGrid');
            }
        })
    }

    //获取所有上师
    $.get(
        '${pageContext.request.contextPath}/guru/findAll',
        function (list) {
            let option = '<option value="0">通用文章</option>';
            list.forEach(function (data) {
                option += '<option value="' + data.id + '">' + data.nick_name + '</option>';
            })
            $('#guruList').html(option);
        }
    )
</script>
<div class="col-sm-10">
    <div class="page-header">
        <h1>文章管理</h1>
    </div>
    <ul class="nav nav-tabs">
        <li class="active"><a href="#">文章列表</a></li>
        <li><a href="javascript:;" onclick="addShow()">添加文章</a></li>
    </ul>
    <table id="articleList"></table>
    <div id="articlePager"></div>
</div>
