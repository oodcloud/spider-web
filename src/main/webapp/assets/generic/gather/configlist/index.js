jQuery(document).ready(function ($) {

$(".task-delete").click(function () {
    var id = $(this).attr('value');
    $('#modal-task-del').modal('show', {backdrop: 'fade'});
    $(".execute-task-del").unbind('click').click(function () {
        $.ajax({
            url: "del.html",
            type: "post",
            dataType: "json",
            data: {
                id: id
            },
            success: function (data) {
                if (data.success) {
                    $(".show-message").empty();
                    $(".show-message").append('<div class="col-md-6" ><div class="alert alert-success"><button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span> <span class="sr-only">Close</span></button> <strong>成功</strong></div></div>');
                    setTimeout(function () {
                        window.location.reload();
                    },1000);
                } else {
                    $(".show-message").empty();
                    $(".show-message").append('<div class="col-md-6"><div class="alert alert-danger"><button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span> <span class="sr-only">Close</span></button> <strong>失败</strong></div></div>');
                }
            }
        })
    });
});


});