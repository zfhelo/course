$("#menuFrame").height(window.innerHeight - 90);

$(document).ready(function () {
  // 选择框
  $(".select2").select2();
});

// 设置激活菜单
function setSidebarActive(tagUri) {
  var liObj = $("#" + tagUri);
  if (liObj.length > 0) {
    liObj.parent().parent().addClass("active");
    liObj.addClass("active");
  }
}

$(document).ready(function () {
  // 激活导航位置
  setSidebarActive("admin-index");
});
