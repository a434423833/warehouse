<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>伟业板材 管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="assets/css/dpl-min.css" rel="stylesheet" type="text/css"/>
    <link href="assets/css/bui-min.css" rel="stylesheet" type="text/css"/>
    <link href="assets/css/main-min.css" rel="stylesheet" type="text/css"/>
    <script src='../js/bootstrap.min.css'></script>
    <script src='../js/jquery-2.1.0.js'></script>
    <script src='../js/bootstrap.min.js'></script>
</head>
<body>

<div class="header">
    <div class="dl-title">
        <a href="http://sc.chinaz.com" title="文档库地址" target="_blank"><!-- 仅仅为了提供文档的快速入口，项目中请删除链接 -->
            <span class="lp-title-port">伟业板材</span><span class="dl-title-text">后台系统</span>
        </a>
    </div>

    <div class="dl-log">欢迎您，
        <span class="dl-log-user" id="head_name">${user.username}</span>
        <span class="dl-log-user" id="head_state">${user.auth==1?"管理员":(user.auth==2?"仓管":"无")}</span>
        <a href="../../exit" title="退出系统"
           class="dl-log-quit">[退出]</a>
    </div>
</div>
<div class="content">
    <div class="dl-main-nav">
        <div class="dl-inform">
            <div class="dl-inform-title">贴心小秘书<s class="dl-inform-icon dl-up"></s></div>
        </div>
        <ul id="J_Nav" class="nav-list ks-clear">
            <li class="nav-item dl-selected">
                <div class="nav-item-inner nav-home">库存管理</div>
            </li>
            <li class="nav-item">
                <div class="nav-item-inner nav-order">出入单管理</div>
            </li>
            <li class="nav-item">
                <div class="nav-item-inner nav-inventory">利润管理</div>
            </li>
            <!-- <li class="nav-item">
                 <div class="nav-item-inner nav-supplier">详情页</div>
             </li>-->
            <li class="nav-item">
                <div class="nav-item-inner nav-marketing">账号管理</div>
            </li>
        </ul>
    </div>
    <ul id="J_NavContent" class="dl-tab-conten">

    </ul>
</div>
<script type="text/javascript" src="assets/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="assets/js/bui.js"></script>
<script type="text/javascript" src="assets/js/config.js"></script>

<script>
    var auth = "${sessionScope.user.auth}";
    BUI.use('common/main', function () {
        var config = [{
            id: 'menu',
            homePage: 'code',
            menu: [{
                text: '库存管理',
                items: [
                    {id: 'code', text: '库存查询', href: 'main/selectproduct.html', closeable: false},
                    {id: 'main-menu', text: '手动添加库存', href: 'main/addproduct.html'},
                ]
            }]
        }, {
            id: 'form',
            menu: [{
                text: '出库单',
                items: [
                    {id: 'code', text: '查看出库单', href: 'form/advalid.html'},
                    {id: 'example', text: '添加出库单', href: 'form/example.html'},
                    {id: 'introduce', text: '删除出库单', href: 'form/introduce.html'},
                ]
            }, {
                text: '入库单',
                items: [
                    {id: 'success', text: '查看入库单', href: 'form/success.html'},
                    {id: 'fail', text: '添加入库单', href: 'form/fail.html'},
                    {id: 'grid', text: '删除入库单', href: 'form/grid.html'},
                ]
            }, {
                text: '库存校对',
                items: [
                    {id: 'grid', text: '查看库存校对', href: 'form/grid.html'},
                ]
            }]
        }, {
            id: 'search',
            menu: [{
                text: '利润管理',
                items: [
                    {id: 'code', text: '今日利润', href: 'search/code.html'},
                    {id: 'example', text: '利润统计', href: 'search/example.html'}
                ]
            }]
        }, {
            /* id: 'detail',
             menu: [{
             text: '详情页面',
             items: [
             {id: 'code', text: '详情页面代码', href: 'detail/selectproduct.html'},
             {id: 'example', text: '详情页面示例', href: 'detail/example.html'},
             {id: 'introduce', text: '详情页面简介', href: 'detail/introduce.html'}
             ]
             }]*/
        }, {
            id: 'chart',
            menu: [{
                text: '账号管理',
                items: [
                    {id: 'code', text: '查看所有账号', href: 'chart/area.html'},
                    {id: 'line', text: '添加仓管账号', href: 'chart/line.html'},
                    /* {id: 'area', text: '区域图', href: 'chart/area.html'},
                     {id: 'column', text: '柱状图', href: 'chart/column.html'},
                     {id: 'pie', text: '饼图', href: 'chart/pie.html'},
                     {id: 'radar', text: '雷达图', href: 'chart/radar.html'}*/
                ]
            }]
        }];
        new PageUtil.MainPage({
            modulesConfig: config
        });
        $("li").each(function () {
            var datavalue = $(this).attr("data-id");
            if (auth != 1 && datavalue == "main-menu") {
                $(this).css("display", "none");
            }
        });
    })
    ;
</script>
<div style="display:none">
    <script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540' language='JavaScript' charset='gb2312'></script>
</div>
</body>
</html>
