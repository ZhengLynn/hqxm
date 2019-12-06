<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8" %>
<script>
    $(function () {
        $('#albumList').jqGrid({
            styleUI: 'Bootstrap',
            url: '${pageContext.request.contextPath}/album/findAll',
            datatype: 'json',
            mtype: 'post',
            colNames: ['ID', '标题', '星数', '作者', '播音员', '专辑简介', '章节数', '状态', '发行时间', '上传时间', '封面'],
            colModel: [
                {name: 'id', align: 'center', search: false},
                {name: 'name', editable: true, align: 'center', editrules: {required: true}},
                {name: 'star', editable: true, align: 'center', editrules: {required: true}},
                {name: 'author', editable: true, align: 'center', editrules: {required: true}},
                {name: 'broadcast', editable: true, align: 'center', editrules: {required: true}},
                {name: 'intro', editable: true, align: 'center', editrules: {required: true}},
                {name: 'counts', editable: true, align: 'center', editrules: {required: true}},
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
                {
                    name: 'release_date',
                    editable: true,
                    edittype: 'date',
                    formatter: 'date',
                    align: "center",
                    formatoptions: {newformat: 'Y-m-d H:i:s'}
                },
                {
                    name: 'upload_date',
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
                        let temp = '<img width="120px" src="' + row.cover + '"/>';
                        return temp;
                    }
                }
            ],
            multiselect: true,
            pager: '#albumPager',
            rowNum: 6,
            rowList: [3, 6, 9, 12],
            viewrecords: true,
            editurl: '${pageContext.request.contextPath}/album/operation',
            autowidth: true,
            height: 504,
            subGrid: true,
            subGridRowExpanded: function (subgrid_id, row_id) {
                subGrid(subgrid_id, row_id);
            },
            subGridRowColapsed: function (subgrid_id, row_id) {
            }
        });
        $('#albumList').navGrid(
            '#albumPager',
            {
                edit: true, add: true, del: true, search: false,
                edittext: '编辑', addtext: '添加', deltext: '删除'
            },
            {
                closeAfterEdit: true, beforeShowForm: function (frm) {
                    $(frm).find('#cover').attr('disabled', true);
                    $(frm).find('#release_date').attr('disabled', true);
                    $(frm).find('#upload_date').attr('disabled', true);
                }
            },
            {
                closeAfterAdd: true, afterSubmit: function (response, post) {
                    let albumID = response.responseJSON.albumId;
                    $.ajaxFileUpload({
                        url: '${pageContext.request.contextPath}/album/upload',
                        data: {albumId: albumID},
                        datatype: 'json',
                        type: 'post',
                        fileElementId: 'cover',
                        success: function () {
                            $('#albumList').trigger('reloadGrid');
                        }
                    })
                    return post;
                }
            },
            {},
            {}
        );
    });

    function subGrid(subgrid_id, row_id) {
        let subGridTable = subgrid_id + "_table";
        let subGridPage = subgrid_id + "_page";
        $('#' + subgrid_id).html('<table id="' + subGridTable + '"></table><div id="' + subGridPage + '"></div>');
        $('#' + subGridTable).jqGrid({
            styleUI: 'Bootstrap',
            url: '${pageContext.request.contextPath}/chapter/findAll?album_id=' + row_id,
            datatype: 'json',
            mtype: 'post',
            colNames: ['ID', '标题', '大小', '时长', '上传时间', '音频', '操作'],
            colModel: [
                {name: 'id', align: 'center', search: false},
                {name: 'name', editable: true, align: 'center', editrules: {required: true}},
                {name: 'size', align: 'center'},
                {name: 'time', align: 'center'},
                {
                    name: 'upload_date',
                    editable: true,
                    edittype: 'date',
                    formatter: 'date',
                    align: "center",
                    formatoptions: {newformat: 'Y-m-d H:i:s'}
                },
                {
                    name: 'path', editable: true, edittype: 'file', align: 'center',
                    editoptions: {enctype: 'multipart/form-data'}
                },
                {
                    name: 'path', align: 'center', formatter(data) {
                        let result = "";
                        result += `<a href="javascript:;" onclick="playAudio('` + data + `')" class="btn"><span class="glyphicon glyphicon-play-circle"></span></a>`;
                        result += `<a href="javascript:;" onclick="downAudio('` + data + `')" class="btn"><span class="glyphicon glyphicon-download"></span></a>`;
                        return result;
                    }
                }
            ],
            multiselect: true,
            pager: '#' + subGridPage,
            rowNum: 3,
            rowList: [2, 3, 4, 5],
            viewrecords: true,
            editurl: '${pageContext.request.contextPath}/chapter/operation?album_id=' + row_id,
            autowidth: true,
            height: 190
        });
        $('#' + subGridTable).navGrid(
            '#' + subGridPage,
            {
                edit: true, add: true, del: true, search: false,
                edittext: '编辑', addtext: '添加', deltext: '删除'
            },
            {
                closeAfterEdit: true, beforeShowForm: function (frm) {
                    $(frm).find('#path').attr('disabled', true);
                    $(frm).find('#upload_date').attr('disabled', true);
                }
            },
            {
                closeAfterAdd: true, afterSubmit: function (response, post) {
                    let chapterID = response.responseJSON.chapterId;
                    $.ajaxFileUpload({
                        url: '${pageContext.request.contextPath}/chapter/upload',
                        data: {chapterId: chapterID},
                        datatype: 'json',
                        type: 'post',
                        fileElementId: 'path',
                        success: function () {
                            $('#albumList').trigger('reloadGrid');
                        }
                    })
                    return post;
                }
            },
            {},
            {}
        )
    }

    function playAudio(data) {
        if (data != 'null' && data != null && data != '') {
            $('#myAudio').attr('src', '${pageContext.request.contextPath}/static/audio/chapter/' + data);
            $('#myModal').modal('show');
        }
    }

    function downAudio(data) {
        if (data != 'null' && data != '' && data != null)
            location.href = '${pageContext.request.contextPath}/chapter/down?url=' + data;
    }
</script>
<div class="col-sm-10">
    <div class="page-header">
        <h1>专辑章节管理</h1>
    </div>
    <ul class="nav nav-tabs">
        <li class="active"><a href="#">专辑章节信息</a></li>
    </ul>
    <table id="albumList"></table>
    <div id="albumPager"></div>
</div>
<div class="modal fade" id="myModal" tabindex="-1">
    <audio id="myAudio" controls></audio>
</div>
