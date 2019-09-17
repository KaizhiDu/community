function collapseComment(e) {

    var id = e.getAttribute("data-id");
    var comment = $("#comment-" + id);

    if (comment.hasClass("in")) {
        comment.removeClass("in");
        e.classList.remove("on-choose");
    } else {
        // 获取二级评论数据
        $.getJSON("/comment/" + id, function (data) {
            console.log(data);
            var subCommentContainer = $("#comment-body-" + id);
            subCommentContainer.text("");
            $.each(data.reverse(), function (index, comment) {

                var mediaBodyElement = $("<div/>", {
                    "class": "media-body"
                }).append($("<h5/>", {
                    "class": "media-heading bold",
                    "html": comment.user.name
                })).append($("<div/>", {
                    "html": comment.content
                })).append($("<div/>", {
                    "class": "menu"
                }).append($("<div/>", {
                    "class": "pull-right",
                    "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                })));

                var avatarElement = $("<img/>", {
                    "class": "media-object img-circle avartar-size",
                    "src": comment.user.avatarUrl
                });

                var mediaLeftElement = $("<div/>", {
                    "class": "media-left"
                }).append(avatarElement);

                var mediaElement = $("<div/>", {
                    "class": "media"
                }).append(mediaLeftElement).append(mediaBodyElement);

                var commentElement = $("<div/>", {
                    "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 each-comment"
                }).append(mediaElement);

                subCommentContainer.prepend(commentElement);
            });
            comment.addClass("in");
            e.classList.add("on-choose");

        });
    }
}

function commitComment() {
    var questionId = $("#questionId").val();
    var commentContent = $("#commentContent").val();
    dealComment(questionId, commentContent, 0);
}

function commitComment2(data) {
    var commentId = data.getAttribute("data-id");
    var commentContent = $("#input-" + commentId).val();
    dealComment(commentId, commentContent, 1);
}

function dealComment(id, commentContent, type) {
    if (!commentContent) {
        alert("content can not be empty");
        return
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        dataType: "JSON",
        contentType: "application/json",
        data: JSON.stringify({
            parentId: id,
            content: commentContent,
            type: type
        }),
        success: function (data) {
            alert(data.msg);
            if (type == 0) {
                $("#replyBox").hide();
            } else {
                $("#input-" + id).val("");
            }

            window.location.reload();

        },
        error: function (err) {
            var isAccepted = confirm("Please login Before comment");
            if (isAccepted) {
                window.open("https://github.com/login/oauth/authorize?client_id=068383357a7087d1b2cb&redirect_uri=http://localhost:8080/callback&scope=user&state=1")
                localStorage.setItem("closable", true);

            }
            console.log(err);
        }
    });
}

