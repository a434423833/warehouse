var data = ['请选择', '板材', '门类', '五金', '其他'];
var data1 = [
    ['请选择'],
    ['伟业生态板', '普通生态板', '伟业板', '面漆板', '多层板', '石膏板', '面板', '杨木板', '铝扣板'],
    ['衣柜移门', '瓶格门', '房门'],
    ['轻钢龙骨', '扣条', '木方', '枪钉', '合页', '拉手', '免钉胶', '小五金'],
    ['其他', '其他']
];

function bodyonload() {
    var str = "";
    for (var i = 0; i < data.length; i++) {
        str += "<option>" + data[i] + "</option> ";
    }
    $("#fatherleibie").html(str);
    selectOrder()
    searchClick(1, 1);
}
function selectOrder() {
    $.ajax({
        type: "POST",      //传输方式
        url: "../../selectOrder",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({            //传递的参数
            "orderState": "待确认",
            "page": {
                "pageNum": 1,
                "pageSize": 20,
                "orderBy": "asc"
            }
        }),
        success: function (obj) {
            if (obj.success == true) {
                var str = "<option>请选择订单 </option>";
                for (var i = 0; i < obj.data.length; i++) {
                    str += "<option>" + obj.data[i].noteno + ";&nbsp;" + obj.data[i].remork + "</option>";
                }
                $("#selectorder").html(str);
            }
        },
        error: function (data, type, err) {
            $("#modelMsg").html(+data.responseJSON.status + ":" + data.responseJSON.message);
            $('#myModal').modal();
        }
    });
}
function leimuchange(obj) {
    var index = obj.selectedIndex;
    var str = "";
    for (var i = 0; i < data1[index].length; i++) {
        str += " <option value='" + data1[index][i] + "' label='" + data1[index][i] + "'>" + data1[index][i] + " </option>";
    }
    $("#chirdleibie").html(str);
}

function searchClick(pageNum, pages) {
    var productName = $("#productName").val();
    var productBrand = $("#productbrand").val();
    var productCategory = $("#chirdleibie").val();
    var productWarehouse = $("#productwarehouse").val();
    if (productName == "") {
        productName = null;
    }
    if (productBrand == "") {
        productBrand = null;
    }
    if (productCategory == "请选择" || productCategory == "") {
        productCategory = null;
    }
    if (productWarehouse == "请选择" || productWarehouse == "") {
        productWarehouse = null;
    }
    if (pageNum <= 0 || pageNum > pages) {
        return;
    }
    $.ajax({
        type: "POST",      //传输方式
        url: "../..//selectProduct", //地址
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({            //传递的参数
            "productName": productName,
            "productCategory": productCategory,
            "productWarehouse": productWarehouse,
            "productBrand": productBrand,
            "page": {
                "pageNum": pageNum,
                "pageSize": 20,
                "orderBy": "asc"
            }
        }),
        success: function (obj) {
            if (obj.success == true) {
                var str = ""
                for (var i = 0; i < obj.data.length; i++) {
                    str += "<tr  class='odd gradeX ng-scope'>" +
                        "<td class='ng-binding'>" + (i + 1 + (obj.page.pageNum - 1) * obj.page.pageSize) + "</td>" +
                        "<td  class='ng-binding'>" + obj.data[i].productCategory + "</td>" +
                        "<td  class='ng-binding' id='productname" + obj.data[i].id + "'>" + obj.data[i].productName + "</td>" +
                        "<td class='ng-binding'  id='productcount" + obj.data[i].id + "'>" + obj.data[i].productCount + "</td>" +
                        "<td  class='ng-binding'>" + obj.data[i].productWarehouse + "</td>" +
                        "<td class='ng-binding'>" + obj.data[i].productBrand + " </td>" +
                        "<td class='ng-binding'>" +
                        "<a class='btn btn-info btn-xs' onclick=addProductToOrder('" + obj.data[i].id + "') >添加到订单" +
                        "</td></tr>";
                }
                $("#selectBody").html(str);
                if (str == "") {
                    $("#selectBody").html("没有查找到商品");
                }
                $("#fanye").html("每页 " + obj.page.pageSize + "条 | 共 " + obj.page.total + "条记录 | 当前第<span id='dangqianpage'> " + obj.page.pageNum + " </span>页 | 共 " + obj.page.pages + " 页" +
                    "| <a onclick='searchClick(" + (obj.page.pageNum - 1) + "," + obj.page.pages + ")'> 上一页 </a>" +
                    "| <a onclick='searchClick(" + (obj.page.pageNum + 1) + "," + obj.page.pages + ")'> 下一页 </a>" +
                    "| <a onclick='searchClick(1,1)'> 首页 </a>" +
                    "| <a onclick='searchClick(" + obj.page.pages + "," + obj.page.pages + ")'>尾页 </a>");
            }
        },
        error: function (data, type, err) {
            $("#modelMsg").html(+data.responseJSON.status + ":" + data.responseJSON.message);
            $('#myModal').modal();
        }
    });

}

function resetClick() {
    $("#productName").val("");
    var str = "";
    for (var i = 0; i < data.length; i++) {
        str += "<option>" + data[i] + "</option>"
    }
    $("#fatherleibie").html(str);
    $("#chirdleibie").html("<option value='请选择' label='请选择'>请选择</option>");
    $("#productwarehouse").html("<option>请选择</option><option>店内</option><option>仓库</option>");
    $("#productbrand").val("");
}
function onCreateOrder(thisobj) {
    var remork = $("#createremork").val();
    if (remork == null || remork == undefined || remork.length <= 0) {
        $("#createOutStockTishi").html("请填写备注");
        return;
    }
    $(thisobj).attr("disabled", "disabled"); //设置变灰按钮
    $.ajax({
        type: "GET",      //传输方式
        url: "../../createOrder", //地址
        dataType: 'json',
        contentType: 'application/json',
        data: {            //传递的参数
            "remork": remork
        },
        success: function (obj) {
            $(thisobj).attr("disabled", false);
            $("#myModal1").modal('hide');
            selectOrder();
        },
        error: function (data, type, err) {
            $("#modelMsg").html(+data.responseJSON.status + ":" + data.responseJSON.message);
            $(thisobj).attr("disabled", false);
            $('#myModal').modal();
            $("#myModal1").modal('hide');
        }
    });
}
var productId = null;
function addProductToOrder(id) {
    productId = id;
    $("#msg3").html("");
    $("#msg3count").val("");
    $("#msg3Name").html($("#productname" + id).html());
    $("#msg3order").html($("#selectorder").val());
    $("#myModal3").modal();
}
function mag3addProduct(thisobj) {
    var order = $("#msg3order").html();
    var count = $("#msg3count").val();
    if (order == null || order == "" || order == undefined || order == "请选择订单") {
        $("#msg3").html("没有选择订单");
        return;
    }
    if (count == null || count == "" || count == undefined) {
        $("#msg3").html("请选择商品数量");
        return;
    }
    if (count > parseInt($("#productcount" + productId).html())) {
        $("#msg3").html("该商品库存不够,只有" + $("#productcount" + productId).html() + "个");
        return;
    }
    $(thisobj).attr("disabled", true);
    $.ajax({
        type: "POST",      //传输方式
        url: "../../addProductToOrder", //地址
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({            //传递的参数
            "productId": productId,
            "orderId": order.substr(0, order.indexOf(';')),
            "productRecord": count
        }),
        success: function (obj) {
            $("#myModal3").modal('hide');
            $(thisobj).attr("disabled", false);
        },
        error: function (data, type, err) {
            $("#modelMsg").html(+data.responseJSON.status + ":" + data.responseJSON.message);
            $(thisobj).attr("disabled", false);
            $('#myModal').modal();
            $("#myModal3").modal('hide');
        }
    });
}
