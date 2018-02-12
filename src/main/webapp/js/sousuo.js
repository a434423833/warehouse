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
    searchClick(1, 1);
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
                        "<td  class='ng-binding'>" + obj.data[i].productName + "</td>" +
                        "<td class='ng-binding'>" + obj.data[i].productCount + "</td>" +
                        "<td  class='ng-binding'>" + obj.data[i].productWarehouse + "</td>" +
                        "<td class='ng-binding'>" + obj.data[i].productBrand + " </td>" +
                        "<td class='ng-binding'> <a class='btn btn-info btn-xs' href='../main/selectrocord.html?id=" + obj.data[i].id + "'> 库存变更明细</a >&nbsp;&nbsp;&nbsp; " +
                        "<button  class='btn btn-primary btn-xs' onclick='getId(&apos;" + obj.data[i].id + "&apos;)'>变更库存</a></td></tr>";
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