function updateSpider() {
    var selected = $("#jobLogTable").bootstrapTable("getSelections");
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的任务！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个任务！');
        return;
    }
    var spiderUrl = selected[0]['id'];
    $.get(ctx + "api/url/spider_url/"+ spiderUrl, function (spider) {
            var $form = $('#spider-add');
            $form.modal();
            $("#job-add-modal-title").html('修改url');
            $form.find("input[name='id']").val(spider['id']);
            $form.find("input[name='url']").val(spider['url']);
            $form.find("input[name='type']").val(spider.type);
            $form.find("input[name='tag']").val(spider.tag);
            $form.find("input[name='remark']").val(spider.remark);
            $form.find("input[name='reprint']").val(spider.reprint);
            $("#job-add-button").attr("name", "update");
    });
}