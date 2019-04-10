$(function () {
    fresh();
    freshQuickTile();
});

function freshQuickTile() {
    $.get("/api/url/quickTitle/me", function (videos) {
        $(".updateForm").each(function (i) {
            v = $(".updateForm").eq(i);
            $.each(videos.rows, function (index) {
                v.find("#quickTitle").append("<button  type=\"button\"\n" +
                    "                        class=\"btn btn-success quickCommit\"\n" +
                    "                        onclick=quickCommit(this," + i + ") >" +
                    videos.rows[index].quickTitle +
                    "                </button>&nbsp;");
            });

        });
    });

}

function fresh() {
    $.post("/api/url/getViewVideo?ids=0", function (videos) {
        $(".updateForm").each(function (i) {
            v = $(".updateForm").eq(i);
            video = videos[i];
            v.fi

            nd("input[name='id']").attr("value", video['id']);
            v.find("input[name='title']").val(video['title']);
            v.find("input[name='updateTitle']").val(video['updateTitle']);
            v.find("#title").attr("value", video['title']);
            $(".video").eq(i).attr("src", video['videoPath']);
        });
    });
    freshNumber();
}

function freshSpecfic(i) {
    ids = "";
    $(".updateForm").each(function (i) {
        v = $(".updateForm").eq(i);
        ids += v.find("input[name='id']").attr("value") + ",";
    });
    ids += 0;
    $.post("/api/url/getViewVideo?ids=" + ids, function (videos) {
        v = $(".updateForm").eq(i);
        video = videos[0];
        v.find("input[name='id']").attr("value", video['id']);
        v.find("input[name='title']").val(video['title']);
        v.find("input[name='updateTitle']").val(video['updateTitle']);
        v.find("#title").attr("value", video['title']);
        $(".video").eq(i).attr("src", video['videoPath']);

    });
    freshNumber();
}

function freshNumber() {
    $.post("/api/url/getReviewNumber", function (number) {
        $("#DownloadNumber").text(number);
    });

    $.post("/api/url/getUploadingNumber", function (number) {
        $("#UploadNumber").text(number);
    });
}


function update(index, stauts) {
    //alert(index + ":" + stauts);
    var $updateForm = $(".updateForm").eq(index);
    $updateForm.find("#status").attr("value", stauts);
    $.post("/api/url/v1/updateVideo", $updateForm.serialize(), function (r) {
        freshSpecfic(index);
    });
}

function quickCommit(html, i) {
    var text = $(html).html();
    $(".uploadTitle").eq(i).val(text);
    update(i,5);
}


