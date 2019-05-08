/**
 * 验证操作js
 */
;layui.define("form", function (e) {
    var s = layui.$, t = (layui.layer, layui.laytpl, layui.setter, layui.view, layui.admin), i = layui.form,
        a = s("body");
    i.verify({
        nick_name: function (e, s) {
            return new RegExp("^[a-zA-Z0-9_一-龥\\s·]+$").test(e) ? /(^\_)|(\__)|(\_+$)/.test(e) ? "用户名首尾不能出现下划线'_'" : /^\d+\d+\d$/.test(e) ? "用户名不能全为数字" : void 0 : "用户名不能有特殊字符"
        },
        password: [/^[\S]{6,12}$/, "密码必须6到12位，且不能出现空格"],
        account: [/^[\S]{6,12}$/, "账号必须6到12位，且不能出现空格"]
    }), t.sendAuthCode({
        elem: "#getsmscode",
        elemPhone: "#LAY-user-login-cellphone",
        elemVercode: "#LAY-user-login-vercode"
        ,ajax:{url:layui.setter.base+"json/user/sms.js"}
    }), a.on("click", "#LAY-user-get-vercode", function () {//验证码生成
        s(this);
        this.src = "https://www.oschina.net/action/user/captcha?t=" + (new Date).getTime()
        console.log(this.src);
    }), e("user", {})
});