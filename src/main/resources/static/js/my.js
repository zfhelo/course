function valid(obj, location) {
    let result = true;
    let l = $(location);
    l.remove();
    obj.forEach(element => {

        console.log(element.reg);
        console.log(element.msg);
        console.log(element.id);
        if (element.reg.test($("#" + element.id).val())) {
            l.append("<li>" + element.msg + "</li>");
            result = false;
        }
    });
    return result;
}

var myback = function () {
    window.history.go(-1);
};
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg); //匹配目标参数
    if (r != null) return unescape(r[2]);
    return null; //返回参数值
}



$("#uploadFile").change(function () {
    var f = this.files[0];
    var formData = new FormData();
    formData.append("smfile", f); //强调一下这个对象的name一定是smfile，不能变
    $.ajax({
        url: "https://sm.ms/api/v2/upload",
        type: "POST",
        headers:{'Authorization':'CHO1Fe1aYFP61o25emDnpu6TKatun5Kq'},
        success: function (data) {
            console.log(data);
            alert(data.data.url);
            $("#uploaded-img").attr('src',data.data.url);
            //$("#res").html(JSON.stringify(data.data.url)); //即图片的网络url//获取后可以使用
        },
        error: function (data) {
            console.log(data);
        },
        data: formData,
        cache: false,
        contentType: false,
        processData: false,
    });
});