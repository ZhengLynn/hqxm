<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8" %>
<script>
    $(function () {
        $('#userList').jqGrid({
            styleUI: 'Bootstrap',
            url: '${pageContext.request.contextPath}/user/findAll',
            datatype: 'json',
            mtype: 'post',
            colNames: ['ID', '用户名', '性别', '状态', '真实姓名', '简介', '地址', '创建时间', '最后登录时间', '头像'],
            colModel: [
                {name: 'id', align: 'center', search: false},
                {name: 'nick_name', editable: true, align: 'center', editrules: {required: true}},
                {name: 'sex', editable: true, align: 'center'},
                {
                    name: 'status', editable: true, align: 'center', edittype: 'select',
                    editoptions: {
                        value: '1:正常;0:冻结'
                    },
                    formatter: function (value, options, row) {
                        if (row.status == 0) return '冻结';
                        if (row.status == 1) return '正常';
                    }
                },
                {name: 'name', editable: true, align: 'center', editrules: {required: true}},
                {name: 'intro', editable: true, align: 'center', editrules: {required: true}},
                {name: 'address', editable: true, align: 'center', editrules: {required: true}},
                {
                    name: 'create_date',
                    editable: true,
                    edittype: 'date',
                    formatter: 'date',
                    align: "center",
                    formatoptions: {newformat: 'Y-m-d H:i:s'}
                },
                {
                    name: 'last_date',
                    editable: true,
                    edittype: 'date',
                    formatter: 'date',
                    align: "center",
                    formatoptions: {newformat: 'Y-m-d H:i:s'}
                },
                {
                    name: 'photo', editable: true, edittype: 'file', align: 'center',
                    editoptions: {enctype: 'multipart/form-data'},
                    formatter: function (value, options, row) {
                        if (row.photo == null || row.photo == '') return '';
                        let temp = '<img height="70px" src="' + row.photo + '"/>';
                        return temp;
                    }
                }
            ],
            multiselect: true,
            pager: '#userPager',
            rowNum: 6,
            rowList: [3, 6, 9, 12],
            viewrecords: true,
            editurl: '${pageContext.request.contextPath}/user/operation',
            autowidth: true,
            height: 504
        }).navGrid(
            '#userPager',
            {
                edit: true, add: false, del: false, search: false,
                edittext: '编辑'
            },
            {
                closeAfterEdit: true, beforeShowForm: function (frm) {
                    $(frm).find('#nick_name').attr('disabled', true);
                    $(frm).find('#sex').attr('disabled', true);
                    $(frm).find('#name').attr('disabled', true);
                    $(frm).find('#intro').attr('disabled', true);
                    $(frm).find('#address').attr('disabled', true);
                    $(frm).find('#photo').attr('disabled', true);
                    $(frm).find('#create_date').attr('disabled', true);
                    $(frm).find('#last_date').attr('disabled', true);
                }
            }, {}, {}, {}
        );
    });
</script>
<div class="col-sm-10">
    <div class="page-header">
        <h1>用户管理</h1>
    </div>
    <ul class="nav nav-tabs">
        <li class="active"><a href="#">用户信息</a></li>
    </ul>
    <table id="userList"></table>
    <div id="userPager"></div>
</div>
