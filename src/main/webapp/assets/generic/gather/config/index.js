/**
 * Created by wangyujie on 2018/1/2.
 */
jQuery(document).ready(function ($) {

    //step switch
    $(".multi-select").multiSelect({
        afterInit: function () {
            this.$selectableContainer.add(this.$selectionContainer).find('.ms-list').perfectScrollbar();
        },
        afterSelect: function () {
            this.$selectableContainer.add(this.$selectionContainer).find('.ms-list').perfectScrollbar('update');
        }
    });

    //动态添加字段
    var index=1;
    $(".addDynamicField").click(function () {
        $('#dynamicFields').append(
            '<div class="row">' +
            '<div class="col-md-3">' +
            '<div class="form-group">' +
            '<label class="control-label" for="fieldName">字段中文名称</label>' +
            '<input class="form-control" name="fieldName'+index+'" id="fieldName" data-validate="required" placeholder="请输入字段中文名称">' +
            '</div>' +
            '</div>' +
            '<div class="col-md-3">' +
            '<div class="form-group">' +
            '<label class="control-label" for="fieldEnglishName">字段英文名称</label>' +
            '<input class="form-control" name="fieldEnglishName'+index+'" id="fieldEnglishName" data-validate="required" placeholder="请输入字段英文名称(这个是数据库的字段名称请正确书写)">' +
            '</div>' +
            '</div>' +
            '<div class="col-md-3">' +
            '<div class="form-group">' +
            '<label class="control-label" for="regex">正则表达式</label>' +
            '<input class="form-control" name="regex'+index+'" data-validate="require" id="regex" placeholder="匹配该字段的正则表达式">' +
            '</div>' +
            '</div>' +
            '<div class="col-md-3">' +
            '<div class="form-group">' +
            '<label class="control-label" for="xpath">xpath</label>' +
            '<input class="form-control" name="xpath'+index+'" data-validate="require" id="xpath" placeholder="匹配该字段的Xpath">' +
            '</div>' +
            '</div>' +
            '</div>'
        );
        index++;
    });

    $(".delDynamicField").click(function () {
        $("#dynamicFields").empty();
        index=1;
    });

    //执行测试
    $(".executeTest").click(function () {

    });

    $(".common-url-test").click(function () {
        var form = $("#configurationForm"),
            commonUrl = form.find("input[name=commonUrl]").val(),
            growthPattern = form.find("input[name=growthPattern]").val(),
            urlRule = form.find("select[name=urlRule] option:selected").val(),
            startNum = form.find("input[name=startNum]").val(),
            endNum = form.find("input[name=endNum]").val();
        if (CheckUrl(commonUrl)) {
            $.ajax({
                url: "test-commoon-url.html",
                type: "post",
                dataType: "json",
                data: {
                    commonUrl: commonUrl,
                    growthPattern: growthPattern,
                    urlRule: urlRule,
                    startNum: startNum,
                    endNum: endNum
                },
                success: function (data) {
                    $("#fwv-1").append("<div class='col-md-12'><div class='alert alert-success'><button type='button' class='close' data-dismiss='alert'><span aria-hidden='true'>×</span> <span class='sr-only'>Close</span></button>"+ JSON.stringify(data.data)+"</div></div>");
                }
            });
        } else {
            alert("请输入正确的网址");
        }
    });


    //保存配置
    $(".saveConfiguration").click(function () {
        var d = {};
        var t = $('#configurationForm').serializeArray();
        $.each(t, function () {
            d[this.name] = this.value;
        });
        $.ajax({
            url: "save-config.html",
            type: "post",
            dataType: "json",
            data: {
                content: JSON.stringify(d)
            },
            success: function (data) {
               if (data.success)
               {
                   $("#spider-config-save-show-info").empty();
                   $("#fwv-6").append('<div class="col-md-6" id="spider-config-save-show-info"><div class="alert alert-success"><button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span> <span class="sr-only">Close</span></button> <strong>成功!</strong> 爬虫信息配置完成!</div></div>');
               }else {
                   $("#spider-config-save-show-info").empty();
                   $("#fwv-6").append('<div class="col-md-6" id="spider-config-save-show-info"><div class="alert alert-danger"><button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span> <span class="sr-only">Close</span></button> <strong>失败!</strong> 爬虫信息配置失败!</div></div>');
               }
            }
        });
    });
    function CheckUrl(str) {
        var RegUrl = new RegExp();
        RegUrl.compile("((http|ftp|https)://)(([a-zA-Z0-9\._-]+\.[a-zA-Z]{2,6})|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\&%_\./-~-]*)?");
        if (!RegUrl.test(str)) {
            return false;
        }
        return true;
    }
});
