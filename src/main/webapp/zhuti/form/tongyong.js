//根据不同的状态获得不同的标签颜色
function getStateColor(state) {
    var str = ""
    if (state == "待确认") {
        str = "<span class='label label-sm label-warning'>待确认</span>"
    }
    if (state == "待出库") {
        str = "<span class='label label-sm label-danger'>待出库</span>"
    }
    if (state == "已出库") {
        str = "<span class='label label-sm label-primary'>已出库</span>"
    }
    if (state == "已完成") {
        str = "<span class='label label-sm label-success'>已完成</span>"
    }
    return str;
}
//格式化时间戳
function formationDate(shijiancuo) {
    var datetime = new Date(shijiancuo);
    var month = datetime.getMonth() + 1;
    var day = datetime.getDate();
    var hours = datetime.getHours();
    var minutes = datetime.getMinutes();
    var seconds = datetime.getSeconds();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (day >= 0 && day <= 9) {
        day = "0" + day;
    }
    if (hours >= 0 && hours <= 9) {
        hours = "0" + hours;
    }
    if (minutes >= 0 && minutes <= 9) {
        minutes = "0" + minutes;
    }
    if (seconds >= 0 && seconds <= 9) {
        seconds = "0" + seconds;
    }
    return datetime.getFullYear() + "-" + month + "-" + day + "&nbsp;" + hours + ":" + minutes + ":" + seconds;
}
//格式化日期
function formatTime(obj, i) {
    if (obj == null) {
        return null;
    }
    if (i == 1) {
        var str = obj.replace('年', '-').replace('月', '-').replace('日', 'T00:00:00.000+0800');
        return str;
    }
    var str = obj.replace('年', '-').replace('月', '-').replace('日', 'T23:59:59.999+0800');
    return str;
}