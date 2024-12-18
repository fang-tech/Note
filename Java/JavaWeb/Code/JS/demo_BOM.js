function testAlert() {
    window.alert("提示信息")
}

function testConfirm() {
    // 确认框
    var con = confirm("确认要删除吗");
    if (con) {
        alert("删除成功");
    } else {
        alert("取消成功");
    }
}

function testPrompt() {
    // 信息输入对话框
    var res = prompt("请输入信息");
    alert("你输入的是:" + res);
}