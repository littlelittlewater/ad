$(function () {
    var $jobLogTableForm = $(".jobLog-table-form");
    var settings = {
        url: ctx + "api/mission/mission",
        pageSize: 10,
        cardView: (isPc === false),
        queryParams: function (params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                beanName: $jobLogTableForm.find("input[name='beanName']").val().trim(),
                methodName: $jobLogTableForm.find("input[name='methodName']").val().trim(),
                status: $jobLogTableForm.find("select[name='status']").val()
            };
        },
        columns: [{
            checkbox: true
        },
            {
                field: 'id',
                title: '序号'
            },
            {
                field: 'name',
                title: '名字',
            }, {
                field: 'remark',
                title: '备注'
            }, {
                field: 'platInfo',
                title: '平台信息'
            },
            {
                field: 'type',
                title: '刷量类型',
            },
            {
                field: 'exceptNumber',
                title: '完成数量',
            }, {
                field: 'exceptNumber',
                title: '期望数量',
            }, {
                field: 'publicNumber',
                title: '完成中数量',
            },
            {
                field: 'cron',
                title: '每批次时间间隔',
            },
            {
                field: 'publishPerTrigger',
                title: '每批次发布数量',
            }
            ,
            {
                field: 'cancel',
                title: '有效状态',
            }

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

function deleteSpiders() {
    var selected = $("#jobLogTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要删除的任务！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个任务！');
        return;
    }

    $MB.confirm({
        text: "删除连接的同时，会删除所有已下载和已审核的视频，并且不可恢复,确认继续？",
        confirmButtonText: "确定删除"
    }, function () {
        $.ajax({
            url: ctx + "api/url/spider_url/" + selected[0]['id'],
            type: 'DELETE',
            dataType: 'json',
            contentType: 'application/json',
            success: function k(result) {
                alert('success');
                refresh();
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

function startSpider() {
    $.post(ctx + 'spider/startSpider', function (r) {
        if (r.code === 0) {
            $MB.n_success(r.msg);
            refresh();
        } else {
            $MB.n_danger(r.msg);
        }
    });
}


function startDownloader() {
    $.post(ctx + 'spider/startDownloader', function (r) {
        if (r.code === 0) {
            $MB.n_success(r.msg);
            refresh();
        } else {
            $MB.n_danger(r.msg);
        }
    });
}

function startDownloaderForUser() {
    $.post(ctx + 'spider/startDownloaderForUser', function (r) {
        if (r.code === 0) {
            $MB.n_success(r.msg);
            refresh();
        } else {
            $MB.n_danger(r.msg);
        }
    });
}

function startBilibili() {
    $.post(ctx + 'spider/startBilibili', function (r) {
        if (r.code === 0) {
            $MB.n_success(r.msg);
            refresh();
        } else {
            $MB.n_danger(r.msg);
        }
    });
}

function startSchedule(scheduleName) {
    $.post(ctx + 'spider/start' + scheduleName, function (r) {
        if (r.code === 0) {
            $MB.n_success(r.msg);
            refresh();
        } else {
            $MB.n_danger(r.msg);
        }
    });
}