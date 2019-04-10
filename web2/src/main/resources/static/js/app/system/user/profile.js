var validator;
var $userProfileForm = $("#update-profile-form");
$(function () {
    $(".profile__img").find("img").attr("src", avatar);
    $("#update-profile").bind('hide.bs.modal', function () {
        $(".modal-backdrop").remove();
    });
    $("#default-avatars").bind('hide.bs.modal', function () {
        $(".modal-backdrop").remove();
    });
    $("#update-profile .btn-close").click(function () {
        $MB.closeAndRestModal("update-profile");
        validator.resetForm();

    });
    $(".default_avatars_list").find("img").each(function () {
        var $this = $(this);
        $this.on("click", function () {
            var target_src = $(this).attr("src");
            $.post(ctx + "user/changeAvatar", {"imgName": target_src}, function (r) {
                if (r.code === 0) {
                    $("#close_update_avatar_button").trigger("click");
                    $MB.n_success(r.msg);
                    refreshUserProfile();
                    $(".user__img").attr("src", ctx + target_src);
                } else $MB.n_danger(r.msg);
            });
        });
    });
    $(".profile__img__edit").on('click', function () {
        $('#default-avatars').modal();
    });
    validateRule();

    $("#update-profile .btn-save").click(function () {

        var validator = $userProfileForm.validate();
        var flag = validator.form();
        if (flag) {
            $.post(ctx + "user/updateUserProfile", $userProfileForm.serialize(), function (r) {
                if (r.code === 0) {
                    $("#update-profile .btn-close").trigger("click");
                    $MB.n_success(r.msg);
                    refreshUserProfile();
                } else $MB.n_danger(r.msg);
            });
        }
    });

});

function refreshUserProfile() {
    $.post(ctx + "user/profile", function (r) {
        $main_content.html("").append(r);
    });
}

function editUserProfile() {
    $.post(ctx + "user/getUserProfile", {"userId": userId}, function (r) {
        if (r.code === 0) {
            var $form = $('#update-profile');

            $form.modal();
            var user = r.msg;
            $form.find("input[name='username']").val(user.username).attr("readonly", true);
            $form.find("input[name='oldusername']").val(user.username);
            $form.find("input[name='userId']").val(user.userId);
            $form.find("input[name='email']").val(user.email);
            $form.find("input[name='mobile']").val(user.mobile);
            $form.find("input[name='description']").val(user.description);
            $form.find("input[name='bilibiliType']").val(user.bilibiliType);
            $form.find("input[name='acfunType']").val(user.acfunType);
            $form.find("input[name='aiqiyiType']").val(user.aiqiyiType);
            $form.find("input[name='youkuType']").val(user.youkuType);
            $form.find("input[name='savePath']").val(user.savePath);
            $("input:radio[value='" + user.ssex + "']").attr("checked", true);
        } else {
            $MB.n_danger(r.msg);
        }
    });
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $userProfileForm.validate({
        rules: {
            email: {
                email: true
            },
            mobile: {
                checkPhone: true
            },
            ssex: {
                required: true
            },
            description: {
                maxlength: 100
            }
        },
        errorPlacement: function (error, element) {
            if (element.is(":checkbox") || element.is(":radio")) {
                error.appendTo(element.parent().parent());
            } else {
                error.insertAfter(element);
            }
        },
        messages: {
            email: icon + "邮箱格式不正确",
            ssex: icon + "请选择性别",
            description: icon + "个人描述不能超过100个字符"
        }
    });
}



