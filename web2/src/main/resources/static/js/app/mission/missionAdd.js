var validator;
var $jobAddForm = $("#job-add-form");

$(function () {
    // validateRule();

    $.fn.serializeJson=function(){
        var serializeObj={};
        var array=this.serializeArray(); //将form表单序列化数组对象
        var str=this.serialize();  //将form表单序列化字符串
        $(array).each(function(){  //遍历表单数组拼接json串
            if(serializeObj[this.name]){
                if($.isArray(serializeObj[this.name])){
                    serializeObj[this.name].push(this.value);
                }else{
                    serializeObj[this.name]=[serializeObj[this.name],this.value];
                }
            }else{
                serializeObj[this.name]=this.value;
            }
        });
        return JSON.stringify(serializeObj);
    };

    $("#spider-add .btn-save").click(function () {
        var name = $(this).attr("name");
        validator = $jobAddForm.validate();
        var flag = validator.form();
        if (flag) {
            if (name === "save") {
                $.ajax({
                    url: ctx + "api/url/spider_url/" ,
                    type: 'post',
                    data: $jobAddForm.serializeJson(),
                    dataType: 'json',
                    contentType : 'application/json',
                    success: function k(result) {
                        alert('success');
                        closeModal();
                        refresh();
                    }
                });
            }
            if (name === "update") {
                $.ajax({
                    url: ctx + "api/url/spider_url/"+$("#id").val() ,
                    type: 'PUT',
                    data: $jobAddForm.serializeJson(),
                    dataType: 'json',
                    contentType : 'application/json',
                    success: function k(result) {
                        alert('success');
                        closeModal();
                        refresh();
                    }
                });
            }
        }
    });

    $("#spider-add .btn-close").click(function () {
        closeModal();
    });

});

function closeModal() {
    $("#job-add-button").attr("name", "save");
    $MB.closeAndRestModal("spider-add");
    validator.resetForm();
    $("#job-add-modal-title").html('新增任务');
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $jobAddForm.validate({
        rules: {
            beanName: {
                required: true,
                maxlength: 100
            },
            methodName: {
                required: true,
                maxlength: 100
            },
            cronExpression: {
                required: true,
                maxlength: 100,
                remote: {
                    url: "job/checkCron",
                    type: "get",
                    dataType: "json",
                    data: {
                        cron: function () {
                            return $("input[name='cronExpression']").val().trim();
                        }
                    }
                }
            }
        },
        messages: {
            beanName: {
                required: icon + "请输入Bean名称",
                maxlength: icon + "长度不能超过100个字符"
            },
            methodName: {
                required: icon + "请输入方法名称",
                maxlength: icon + "长度不能超过100个字符"
            },
            cronExpression: {
                required: icon + "请输入cron表达式",
                maxlength: icon + "长度不能超过100个字符",
                remote: icon + "cron表达式不合法"
            }
        }
    });
}