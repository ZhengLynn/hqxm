<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8" %>
<script>
    $(function () {
        $('#guruList').jqGrid({
            styleUI: 'Bootstrap',
            url: '${pageContext.request.contextPath}/guru/findByPage',
            datatype: 'json',
            mtype: 'post',
            colNames: ['ID', '姓名', '头像', '状态', '法号'],
            colModel: [
                {name: 'id', align: 'center', search: false},
                {name: 'name', editable: true, align: 'center', editrules: {required: true}},
                {
                    name: 'photo', editable: true, edittype: 'file', align: 'center',
                    editoptions: {enctype: 'multipart/form-data'},
                    formatter: function (value, options, row) {
                        if (row.photo == null || row.photo == '') return '';
                        let temp = '<img height="70px" src="' + row.photo + '"/>';
                        return temp;
                    }
                },
                {
                    name: 'status', editable: true, align: 'center', edittype: 'select',
                    editoptions: {
                        value: '1:展示;0:隐藏'
                    },
                    formatter: function (value, options, row) {
                        if (row.status == 0) return '隐藏';
                        if (row.status == 1) return '展示';
                    }
                },
                {name: 'nick_name', editable: true, align: 'center', editrules: {required: true}}
            ],
            multiselect: true,
            pager: '#guruPager',
            rowNum: 6,
            rowList: [3, 6, 9, 12],
            viewrecords: true,
            editurl: '${pageContext.request.contextPath}/guru/operation',
            autowidth: true,
            height: 504
        }).navGrid(
            '#guruPager',
            {
                edit: true, add: true, del: true, search: false,
                edittext: '编辑', addtext: '添加', deltext: '删除'
            },
            {
                closeAfterEdit: true, beforeShowForm: function (frm) {
                    $(frm).find('#photo').attr('disabled', true);
                }
            },
            {
                closeAfterAdd: true, afterSubmit: function (response, post) {
                    let guruId = response.responseJSON.guruId;
                    $.ajaxFileUpload({
                        url: '${pageContext.request.contextPath}/guru/upload',
                        data: {guruId: guruId},
                        datatype: 'json',
                        type: 'post',
                        fileElementId: 'photo',
                        success: function () {
                            $('#guruList').trigger('reloadGrid');
                        }
                    })
                    return post;
                }
            },
            {},
            {}
        );
    });
</script>
<div class="col-sm-10">
    <div class="page-header">
        <h1>上师管理</h1>
    </div>
    <ul class="nav nav-tabs">
        <li class="active"><a href="#">上师信息</a></li>
    </ul>
    <table id="guruList"></table>
    <div id="guruPager"></div>
</div>
