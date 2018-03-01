function bodyonload() {
    $("#orderNoteno").html(objOrder.noteno);
    $("#orderRemork").html(objOrder.remork);
    $("#updateTime").html(formationDate(objOrder.updateTime));
    $("#orderState").html(getStateColor(objOrder.state));
    $("#operationButton").html(getOperationButton(objOrder.state))
    if (objOrder.state == "待确认") {
        $("#conutupdate").html("数量&nbsp;&nbsp;" + "<button  class='label label-sm label-primary' onclick='updatestate()'>点击修改</button>");
    } else {
        $("#conutupdate").html("数量");

    }
    showproduct(0);
}
//显示产品 0不可编辑，1编辑状态
function showproduct(update) {
    var str = "";
    for (var i = 0; i < objOrder.productList.length; i++) {
        var data = objOrder.productList[i];
        str += "<tr class='odd gradeX ng-scope' >\n" +
            " <td class='ng-binding'>" + i + 1 + "</td>\n" +
            " <td class='ng-binding'>" + data.productName + "</td>"
        ;
        if (objOrder.state == "待确认" && update == 1) {
            str += "<td><input type='number' value='" + data.productCount + "' id='productCount" + i + "''</td>\n";
        } else {
            str += " <td>" + data.productCount + "</td>\n";
        }
        str += "<td>" + data.productWarehouse + "</td>" +
            "<td>" + data.productCategory + "</td>" +
            /* "<td ng-bind='item.des' class='ng-binding'>" +
             "<button class='btn btn-info btn-xs' onclick=selectOrderItem(" + i + ")> 查看订单详细</button></td>+"*/
            "</tr>"
    }
    $("#selectBody").html(str);
    if (objOrder != null) {
        $("#pageBody").html(" 共 " + objOrder.productList.length + "个商品");
    }
}

function updatestate() {
    $("#conutupdate").html("数量&nbsp;&nbsp;" + "<button class='label label-sm label-success' onclick='updatesave()'>保存修改</button>");
    showproduct(1);
}
function updatesave() {
    $("#conutupdate").html("数量&nbsp;&nbsp;" + "<button class='label label-sm label-primary' onclick='updatestate()'>点击修改</button>");
    var objnew = deepClone(objOrder);
    for (var i = 0; i < objOrder.productList.length; i++) {
        objnew.productList[i].productCount = $("#productCount" + i).val();
    }
    updateOrder(objnew);
    showproduct(0);
}
function updateOrder(objnew) {
    $.ajax({
        type: "POST",      //传输方式
        url: "../../updateOrder", //地址
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(objnew),
        async: false,
        success: function (obj) {
            objOrder = deepClone(objnew);//成功刷新objOrder
        },
        error: function (data, type, err) {
            $("#modelMsg").html("修改商品数量失败" + data.responseJSON.status + ":" + data.responseJSON.message);
            $('#myModal').modal();
        }
    });
}

function getOperationButton(state) {
    var str = "";
    if (state == "待确认") {
        str = "<button type='button' class='btn btn-info' onclick='updateOrderState(this,\"待出库\")'>确认订单</button><span style='color: red'>*确认后无法修改产品数量</span>";
    }
    if (state == "待出库") {
        str = "<button type='button' class='btn btn-info' onclick='stock(this)'>出库</button></button><span style='color: red'>*出库后扣库存无法删除订单</span>";
    }
    if (state == "已出库") {
        str = "<button type='button' class='btn btn-info'  onclick='updateOrderState(this,\"已完成\")'>确认完成</button><span style='color: red'>*完成订单</span>"
    }
    return str;
}

function updateOrderState(thisobj, updateState) {
    $(thisobj).attr("disabled", true);
    $.ajax({
        type: "POST",      //传输方式
        url: "../../updateOrderState", //地址
        data: {
            "noteno": objOrder.noteno,
            "state": updateState
        },
        success: function (obj) {
            $(thisobj).attr("disabled", false);
            $("#modelMsg2").html("订单状态已修改为" + updateState);
            $('#myModal2').modal();
            objOrder = obj.data;
            bodyonload();
        },
        error: function (data, type, err) {
            $(thisobj).attr("disabled", false);
            $("#modelMsg").html("修改订单状态失败" + data.responseJSON.status + ":" + data.responseJSON.message);
            $('#myModal').modal();
        }
    });
}
function stock(thisobj) {
    $(thisobj).attr("disabled", true);
    $.ajax({
        type: "POST",      //传输方式
        url: "../../stock", //地址
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(objOrder),
        success: function (obj) {
            $(thisobj).attr("disabled", false);
            $("#modelMsg2").html("出库成功，已扣减库存");
            $('#myModal2').modal();
            objOrder = obj.data;
            bodyonload();
        },
        error: function (data, type, err) {
            $(thisobj).attr("disabled", false);
            $("#modelMsg").html("出库失败" + ":" + data.responseJSON.message);
            $('#myModal').modal();
        }
    });
}
//深拷贝
function deepClone(data) {
    var type = getType(data);
    var obj;
    if (type === 'array') {
        obj = [];
    } else if (type === 'object') {
        obj = {};
    } else {
        //不再具有下一层次
        return data;
    }
    if (type === 'array') {
        for (var i = 0, len = data.length; i < len; i++) {
            obj.push(deepClone(data[i]));
        }
    } else if (type === 'object') {
        for (var key in data) {
            obj[key] = deepClone(data[key]);
        }
    }
    return obj;
}
function getType(obj) {
    //tostring会返回对应不同的标签的构造函数
    var toString = Object.prototype.toString;
    var map = {
        '[object Boolean]': 'boolean',
        '[object Number]': 'number',
        '[object String]': 'string',
        '[object Function]': 'function',
        '[object Array]': 'array',
        '[object Date]': 'date',
        '[object RegExp]': 'regExp',
        '[object Undefined]': 'undefined',
        '[object Null]': 'null',
        '[object Object]': 'object'
    };
    if (obj instanceof Element) {
        return 'element';
    }
    return map[toString.call(obj)];
}