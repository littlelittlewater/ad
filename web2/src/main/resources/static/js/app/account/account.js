$(function () {
    var $jobLogTableForm = $(".jobLog-table-form");
    var settings = {
        url: ctx + "api/upload/list",
        pageSize: 10,
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
                title: '序号',
            },
            {
                field: 'loginName',
                title: '用户名'
            }, {
                field: 'loginId',
                title: '登录id'
            }, {
                field: 'plat',
                title: '类型'
            }, {
                field: 'platType',
                title: '上传类型'
            }, {
                field: 'login',
                title: '是否登录',
                formatter: function (value, row, index) {
                    if (value === true) return '<span class="badge badge-success">已登录</span>';
                    if (value === false) return '<span class="badge badge-danger">未登录</span>';
                }
            }, {
                field: 'url',
                title: '登录二维码',
                formatter: function (value, row, index) {
                    return '<img src= ' + value + '?_=' + new Date().getTime() + '>';
                }
            }, {
                field: 'serverUrl',
                title: '所在服务器',
            }
        ]
    };

    $MB.initTable('jobLogTable', settings);

    $("#update-type .btn-save").click(function () {
        var selected = $("#jobLogTable").bootstrapTable('getSelections');
        var selected_length = selected.length;
        if (!selected_length) {
            $MB.n_warning('请勾选需要删除的任务！');
            return;
        }
        if (selected_length > 1) {
            $MB.n_warning('一次只能修改一个用户！');
            return;
        }
        var $updateTypeForm = $("#update-type-form");
        $updateTypeForm.find("input[name='id']").val(selected[0]['id']);
        $.post(ctx + "api/upload/updateAccountType", $updateTypeForm.serialize(), function (r) {
            $MB.closeAndRestModal("update-type");
            fresh();
        });

    });

    $("#update-type .btn-close").click(function () {
        $MB.closeAndRestModal("update-type");
    });

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
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个账号！');
        return;
    }

    $MB.confirm({
        text: "确定退出选中的账号？",
        confirmButtonText: "确认退出"
    }, function () {
        $.post("api/upload/logOut/"+selected[0]['id'], function (r) {
            refresh();
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