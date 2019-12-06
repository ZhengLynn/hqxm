<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8" %>
<script>
    $(function () {
        $('#bannerList').jqGrid({
            styleUI: 'Bootstrap',
            url: '${pageContext.request.contextPath}/banner/findAll',
            datatype: 'json',
            mtype: 'post',
            colNames: ['ID', '标题', '状态', '链接', '描述', '创建时间', '图片'],
            colModel: [
                {name: 'id', align: 'center', search: false},
                {name: 'name', editable: true, align: 'center', editrules: {required: true}},
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
                {name: 'link', editable: true, align: 'center', editrules: {required: true}},
                {name: 'intro', editable: true, align: 'center', editrules: {required: true}},
                {
                    name: 'create_date',
                    editable: true,
                    edittype: 'date',
                    formatter: 'date',
                    align: "center",
                    formatoptions: {newformat: 'Y-m-d H:i:s'}
                },
                {
                    name: 'url', editable: true, edittype: 'file', align: 'center',
                    editoptions: {enctype: 'multipart/form-data'},
                    formatter: function (value, options, row) {
                        if (row.url == null || row.url == '') return '';
                        let temp = '<img height="70px" src="' + row.url + '"/>';
                        return temp;
                    }
                }
            ],
            multiselect: true,
            pager: '#bannerPager',
            rowNum: 6,
            rowList: [3, 6, 9, 12],
            viewrecords: true,
            editurl: '${pageContext.request.contextPath}/banner/operation',
            autowidth: true,
            height: 504
        }).navGrid(
            '#bannerPager',
            {
                edit: true, add: true, del: true, search: false,
                edittext: '编辑', addtext: '添加', deltext: '删除'
            },
            {
                closeAfterEdit: true, beforeShowForm: function (frm) {
                    $(frm).find('#url').attr('disabled', true);
                    $(frm).find('#create_date').attr('disabled', true);
                }
            },
            {
                closeAfterAdd: true, afterSubmit: function (response, post) {
                    let bannerID = response.responseJSON.bannerId;
                    $.ajaxFileUpload({
                        url: '${pageContext.request.contextPath}/banner/upload',
                        data: {bannerId: bannerID},
                        datatype: 'json',
                        type: 'post',
                        fileElementId: 'url',
                        success: function () {
                            $('#bannerList').trigger('reloadGrid');
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
        <h1>轮播图管理</h1>
    </div>
    <ul class="nav nav-tabs">
        <li class="active"><a href="#">轮播图信息</a></li>
        <li><a href="javascript:location.href = '${pageContext.request.contextPath}/banner/outBanner'">导出轮播图信息</a></li>
        <li><a href="#">导出轮播图模板</a></li>
        <li><a href="#">导入轮播图模板</a></li>
    </ul>
    <table id="bannerList"></table>
    <div id="bannerPager"></div>
</div>
