$(function () {
    var $jobLogTableForm = $(".jobLog-table-form");
    var settings = {
        url: ctx + "upload/list",
        pageSize: 100,
        cardView:(isPc===false),
        queryParams: function (params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                type: $jobLogTableForm.find("input[name='type']").val().trim(),
                methodName: $jobLogTableForm.find("input[name='methodName']").val().trim(),
                status: $jobLogTableForm.find("select[name='status']").val()
            };
        },
        columns: [{
            checkbox: true
        },
            {
                field: 'uid',
                title: '序号'
            },
            {
                field: 'uploadTitle',
                title: '上传标题',
            }, {
                field: 'keywords',
                title: '标签'
            },{
                field: 'imagePath',
                title: '图片',
            },{
                field: 'videoPath',
                title: '视频',
            },{
                field: 'createDate',
                title: '创建时间'
            },{
                field: 'viewNumber',
                title: '观看数量',
            }, {
                field: 'likeNumber',
                title: '点赞数量'
            },{
                field: 'favoriteNumber',
                title: '收藏数量'
            },
        ]
    };
    $MB.initTable('jobLogTable', settings);
});

function search() {
    $MB.refreshTable('jobLogTable');
}

function refresh() {
    $(".jobLog-table-form")[0].reset();
    search();
}

function upload() {
    var selected = $("#jobLogTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要上传的任务！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i]['uid'];
        if (i !== (selected_length - 1)) ids += ",";
    }

    $MB.confirm({
        text: "确定上传选中的任务？",
        confirmButtonText: "确定上传"
    }, function () {
        $.post(ctx + 'upload/update', {"ids": ids}, function (r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

function deleteUpload() {
    var selected = $("#jobLogTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要删除的视频信息！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i]['uid'];
        if (i !== (selected_length - 1)) ids += ",";
    }

    $MB.confirm({
        text: "确定删除选中的任务？",
        confirmButtonText: "确定删除"
    }, function () {
        $.post(ctx + 'upload/delete', {"ids": ids}, function (r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}


function refreshVideo() {
    var $jobLogTableForm = $(".jobLog-table-form");
    $MB.confirm({
        text: "确定刷新任务？",
        confirmButtonText: "确定刷新"
    }, function () {
        $.post(ctx + 'upload/refresh', {"type": $jobLogTableForm.find("input[name='type']").val().trim(),}, function (r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}



function exportJobLogExcel() {
    $.post(ctx + "jobLog/excel", $(".jobLog-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}

function exportJobLogCsv() {
    $.post(ctx + "jobLog/csv", $(".jobLog-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}