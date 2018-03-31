function selectAll(selectStatus) {
    $("input[name='checkItem']").each(function (i, n) {
        n.checked = selectStatus;
    });
}

jQuery(document).ready(function ($) {

    //jedatedatepicker
    $("#startTime").jeDate({
        format: "YYYY-MM-DD hh:mm:ss"
    });
    $("#endTime").jeDate({
        format: "YYYY-MM-DD hh:mm:ss"
    });


    $(".list-group-item").click(function () {
        $(this).parents("div").children("a").removeClass("active");
        $(this).attr("class", "list-group-item active");
        var id = $(this).attr('value');
        if ($("#page").pagination()) {
            $("#page").pagination('destroy');
        }

        $("#page").pagination({
            pageSize: 10,
            remote: {
                url: 'getDbDataList.html',
                pageParams: function (data) {
                    return {
                        index: data.pageIndex,
                        id: id,
                        size: data.pageSize
                    };
                },
                success: function (data) {
                    $("#table-column-name").empty();
                    $("#table-column-data").empty();
                    var columnNameList = " <th><input value='"+id+"' id='selectAllIds' onclick=selectAll(this.checked) type='checkbox'/></th>";
                    var columnData = "";
                    data.data.forEach(function (item, index) {
                        if (index == 0) {
                            item.forEach(function (v, index) {
                                if (index != 0) {
                                    columnNameList = columnNameList + "<th>" + v + "</th>"
                                }
                            });

                        } else {
                            var content = "";
                            var dataId = 0;
                            item.forEach(function (v, index) {
                                if (index != 0) {
                                    content = content + "<td>" + v + "</td>"
                                } else {
                                    content += "<tr id='" + v + "'><td><input value='" + v + "'name='checkItem' type='checkbox' /></td>";
                                    dataId = v;
                                }
                            });
                            columnData = columnData + content;
                        }
                    });
                    $("#table-column-name").append(columnNameList);
                    $("#table-column-data").append(columnData)
                },
                totalName: 'total'
            }

        });
    });


    $("#batch-del").click(function () {
        var id=$("#selectAllIds").val();
        if (id==null)
        {
            alert("请选择表格后再做此操作");
        }else {
        var param = [];
        $("input:checkbox[name='checkItem']:checked").each(function (i) {
            param.push($(this).val());
        });
        $('#modal-batch-del').modal('show', {backdrop: 'fade'});
        $(".del-batch-data").unbind('click').click(function () {
            $.ajax({
                url: "batchesDelete.html",
                type: "post",
                dataType: "json",
                data: {
                    ids: param,
                    id:$("#selectAllIds").val()
                },
                success: function (data) {
                    if (data.success) {
                        $(".show-message").empty();
                        $(".show-message").append('<div class="col-md-6" ><div class="alert alert-success"><button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span> <span class="sr-only">Close</span></button> <strong>成功</strong></div></div>');
                        setTimeout(function () {
                            window.location.reload();
                        }, 1000);
                    } else {
                        $(".show-message").empty();
                        $(".show-message").append('<div class="col-md-6"><div class="alert alert-danger"><button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span> <span class="sr-only">Close</span></button> <strong>失败</strong></div></div>');
                    }
                }
            })
        });
        }
    });




    $("#all-del").click(function () {
        var id=$("#selectAllIds").val();
        if (id==null)
        {
            alert("请选择表格后再做此操作");
        }else {
            $('#modal-all-del').modal('show', {backdrop: 'fade'});
            $(".del-all-data").unbind('click').click(function () {
                $.ajax({
                    url: "deleteAll.html",
                    type: "post",
                    dataType: "json",
                    data: {
                        id:$("#selectAllIds").val()
                    },
                    success: function (data) {
                        if (data.success) {
                            $(".show-message").empty();
                            $(".show-message").append('<div class="col-md-6" ><div class="alert alert-success"><button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span> <span class="sr-only">Close</span></button> <strong>成功</strong></div></div>');
                            setTimeout(function () {
                                window.location.reload();
                            }, 1000);
                        } else {
                            $(".show-message").empty();
                            $(".show-message").append('<div class="col-md-6"><div class="alert alert-danger"><button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span> <span class="sr-only">Close</span></button> <strong>失败</strong></div></div>');
                        }
                    }
                })
            });
        }
    });


    $("#all-edit").click(function () {
        var id=$("#selectAllIds").val();
        if (id==null)
        {
            alert("请选择表格后再做此操作");
        }else {
            $("#replace-all-content-id").val(id);
            $('#replace-all-content').modal('show', {backdrop: 'fade'});

            $(".replace-all-content-submit").unbind('click').click(function () {
                $.ajax({
                    url: "fieldContentReplace.html",
                    type: "post",
                    dataType: "json",
                    data: $("#replace-all-content-form").serializeArray(),
                    success: function (data) {
                        if (data.success) {
                            $(".replace-all-content-msg").empty();
                            $(".replace-all-content-msg").append('<div class="col-md-6" ><div class="alert alert-success"><button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span> <span class="sr-only">Close</span></button> <strong>成功</strong></div></div>');
                            setTimeout(function () {
                                window.location.reload();
                            }, 1000);
                        } else {
                            $(".replace-all-content-msg").empty();
                            $(".replace-all-content-msg").append('<div class="col-md-6"><div class="alert alert-danger"><button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span> <span class="sr-only">Close</span></button> <strong>失败</strong></div></div>');
                        }
                    }
                })
            });

        }
    });


    $("#batch-edit").click(function () {

        var id=$("#selectAllIds").val();
        if (id==null)
        {
            alert("请选择表格后再做此操作");
        }else {
            $("#replace-batches-content-id").val(id);
            var param = [];
            $("input:checkbox[name='checkItem']:checked").each(function (i) {
                param.push($(this).val());
            });
            $("#replace-batches-content-ids").val(param);
            $('#replace-content-by-id').modal('show', {backdrop: 'fade'});

            $(".replace-content-by-id-submit").unbind('click').click(function () {
                $.ajax({
                    url: "fieldContentReplaceById.html",
                    type: "post",
                    dataType: "json",
                    data: $("#replace-content-by-id-form").serializeArray(),
                    success: function (data) {
                        if (data.success) {
                            $(".replace-content-by-id-msg").empty();
                            $(".replace-content-by-id-msg").append('<div class="col-md-6" ><div class="alert alert-success"><button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span> <span class="sr-only">Close</span></button> <strong>成功</strong></div></div>');
                            setTimeout(function () {
                                window.location.reload();
                            }, 1000);
                        } else {
                            $(".replace-content-by-id-msg").empty();
                            $(".replace-content-by-id-msg").append('<div class="col-md-6"><div class="alert alert-danger"><button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span> <span class="sr-only">Close</span></button> <strong>失败</strong></div></div>');
                        }
                    }
                })
            });
        }
    });


});