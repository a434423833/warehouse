var data = ['请选择', '待确认', '待出库 ', '已出库', '已完成'];
function bodyonloadOrder(obj) {
    var str = "";
    for (var i = 0; i < data.length; i++) {
        str += "<option>" + data[i] + "</option> ";
    }
    $("#orderstate").html(str);
    searchClickOrder(1, 1);
}
function changestate() {
    if ($("#orderstate").val() == "已完成") {
        $("#beginmoney").attr("disabled", false); //设置变灰按钮
        $("#endmoney").attr("disabled", false); //设置变灰按钮
    } else {
        $("#beginmoney").val("");
        $("#endmoney").val("");
        $("#beginmoney").attr("disabled", true); //设置变灰按钮
        $("#endmoney").attr("disabled", true); //设置变灰按钮
    }
}
var objdata;
function searchClickOrder(pageNum, pages, noteno) {
    var remork = $("#noteno").val();
    var beginTime = formatTime($("#beginTime").val(), 1);
    var endTime = formatTime($("#endTime").val(), 2);
    var orderstate = $("#orderstate").val();
    var beginmoney = $("#beginmoney").val();
    var endmoney = $("#endmoney").val();
    if (orderstate == "请选择") {
        orderstate = null;
    }
    if (pageNum <= 0 || pageNum > pages) {
        return;
    }
    $.ajax({
            type: "POST",      //传输方式
            url: "../../selectOrder",
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify({            //传递的参数
                "noteno": noteno,
                "remork": remork,
                "beginTime": beginTime,
                "endTime": endTime,
                "orderState": orderstate,
                "beginMoney": beginmoney,
                "endMoney": endmoney,
                "page": {
                    "pageNum": pageNum,
                    "pageSize": 20,
                    "orderBy": "asc"
                }
            }),
            success: function (obj) {
                if (obj.success == true) {
                    var str = "";
                    for (var i = 0; i < obj.data.length; i++) {
                        var data = obj.data[i];
                        str += "<tr class='odd gradeX ng-scope' >\n" +
                            " <td class='ng-binding'>" + (i + 1 + (obj.page.pageNum - 1) * obj.page.pageSize) + "</td>\n" +
                            " <td class='ng-binding'>" + data.noteno + "</td>" +
                            " <td>" + getStateColor(data.state) + "</td>\n" +
                            "<td id='orderRemork" + data.noteno + "'>" + data.remork + "</td>" +
                            "<td>" + data.createUser + "</td>" +
                            "<td >" + formationDate(data.createTime) + "</td>" +
                            "<td ng-bind='item.des' class='ng-binding'>" +
                            "<button class='btn btn-info btn-xs' onclick=selectOrderItem(" + i + ")> 查看订单详细</button> "
                        ;
                        if (data.state == ("待出库") || data.state == ("待确认")) {
                            str += "<button class='btn btn-danger btn-xs'onclick='getIdForDel(&apos;" + data.noteno + "&apos;)'> 删除订单</button> ";
                        } else {
                            str += "<button class='btn btn-danger btn-xs' disabled='disabled'> 删除订单</button> ";
                        }
                        str += "</td></tr>"
                    }
                    objdata = obj.data;
                    $("#selectOrderBody").html(str);
                    $("#fenye").html("每页 " + obj.page.pageSize + "条 | 共 " + obj.page.total + "条记录 | 当前第<span id='dangqianpage'> " + obj.page.pageNum + " </span>页 | 共 " + obj.page.pages + " 页" +
                        "| <a onclick='searchClickOrder(" + (obj.page.pageNum - 1) + "," + obj.page.pages + ")'> 上一页 </a>" +
                        "| <a onclick='searchClickOrder(" + (obj.page.pageNum + 1) + "," + obj.page.pages + ")'> 下一页 </a>" +
                        "| <a onclick='searchClickOrder(1,1)'> 首页 </a>" +
                        "| <a onclick='searchClickOrder(" + obj.page.pages + "," + obj.page.pages + ")'>尾页 </a>");
                }
            },
            error: function (data, type, err) {
                $("#modelMsg").html(+data.responseJSON.status + ":" + data.responseJSON.message);
                $('#myModal').modal();
            }
        }
    )
    ;
}
var delectnoteno;
function getIdForDel(noteno) {
    delectnoteno = noteno;
    $("#modelMsg2").html("是否删除<span style='color: red'>" + $("#orderRemork" + delectnoteno).html() + "</span>该订单");
    $("#myModal2").modal();
}
function ondelectorder(thisobj) {
    $(thisobj).attr("disabled", true); //设置变灰按钮
    $.ajax({
        type: "POST",      //传输方式
        url: "../../delectOrder", //地址
        data: {            //传递的参数
            "noteno": delectnoteno
        },
        success: function (obj) {
            $(thisobj).attr("disabled", false);
            $("#myModal2").modal('hide');
            searchClickOrder($("#dangqianpage").html(), $("#dangqianpage").html());
        },
        error: function (data, type, err) {
            $(thisobj).attr("disabled", false);
            $("#myModal2").modal('hide');
            $("#modelMsg").html(+data.responseJSON.status + ":" + data.responseJSON.message);
            $('#myModal').modal();
        }
    });

}
function resetClickOrder() {
    $("#noteno").val("");
    $("#beginTime").val("");
    $("#endTime").val("");
    var str = "";
    for (var i = 0; i < data.length; i++) {
        str += "<option>" + data[i] + "</option> ";
    }
    $("#orderstate").html(str);
    $("#beginmoney").val("");
    $("#endmoney").val("");
}
function selectOrderItem(index) {
    sessionStorage.setItem("item", JSON.stringify(objdata[index]));
    window.location.href = 'orderitem.html';
}

