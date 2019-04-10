$(function () {
    var $jobLogTableForm = $(".jobLog-table-form");
    var settings = {
        url: ctx + "api/upload/v1/analyze",
        pageSize: 200,
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
                field: 'user_id',
                title: '账户id'
            },{
                field: 'last_day_publish',
                title: '昨天发布数目'
            }, {
                field: 'd_1_add',
                title: 'D-1',
            },{
                field: 'd_2_add',
                title: 'D-2',
            },
            {
                field: 'd_3_add',
                title: 'D-3',
            },
            {
                field: 'd_4_add',
                title: 'D-4',
            },
            {
                field: 'd_5_add',
                title: 'D-5',
            },
            {
                field: 'd_6_add',
                title: 'D-6',
            },
            {
                field: 'd_7_add',
                title: 'D-7',
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

function logOutByTypes() {
    var selected = $("#jobLogTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要退出登陆的账号');
        return;
    }
    var types = "";
    for (var i = 0; i < selected_length; i++) {
        types += selected[i]['type'];
        if (i !== (selected_length - 1)) types += ",";
    }

    $MB.confirm({
        text: "确定退出选中的账号？",
        confirmButtonText: "确认退出"
    }, function () {
        $.post(ctx + 'account/exit', {"types": types}, function (r) {
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