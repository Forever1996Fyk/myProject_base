/** layuiAdmin.std-v1.2.1 LPPL License By http://www.layui.com/admin/ 加QQ：1293166442 获取源码版*/
;layui.define(["form", "upload"], function (t) {
    var i = layui.$, e = layui.layer, a = (layui.laytpl, layui.setter, layui.view, layui.admin), n = layui.form,
        s = layui.upload;
    i("body");
    n.verify({
        // nickname: function (t, i) {
        //     return new RegExp("^[a-zA-Z0-9_一-龥\\s·]+$").test(t) ? /(^\_)|(\__)|(\_+$)/.test(t) ? "用户名首尾不能出现下划线'_'" : /^\d+\d+\d$/.test(t) ? "用户名不能全为数字" : void 0 : "用户名不能有特殊字符"
        // },
        pass: [/^[\S]{6,12}$/, "密码必须6到12位，且不能出现空格"], repass: function (t) {
            if (t !== i("#LAY_password").val())return "两次密码输入不一致"
        }
    }), n.on("submit(set_website)", function (t) {
        return e.msg(JSON.stringify(t.field)), !1
    }), n.on("submit(set_system_email)", function (t) {
        return e.msg(JSON.stringify(t.field)), !1
    });
    var r = i("#LAY_avatarSrc");
    /* 修改头像 */
    var image = new Image();
    var panel = $(".canvas-panel");
    var bgImg = $(".canvas-bg");
    var canvas = $(".canvas-crop");
    // 激活或停止移动
    var moveEvent = false;
    var screenX = 0, screenY = 0;
    var moveTop = 0, moveLeft = 0;
    panel.on("mousedown",function (e) {
        screenX = e.screenX;
        screenY = e.screenY;
        moveTop = parseFloat(bgImg.css("top"));
        moveLeft = parseFloat(bgImg.css("left"));
        moveEvent = true;
    });
    panel.on("mouseup",function (e) {
        moveEvent = false;
    });
    panel.on("mousemove", function (e) {
        if (moveEvent){
            bgImg.css("left", moveLeft + e.screenX - screenX);
            bgImg.css("top", moveTop + e.screenY - screenY);
            renderPanel();
        }
    });
    panel.on("mousewheel", function(event, delta) {
        var dir = delta > 0 ? 'Up' : 'Down';
        var width = parseFloat(bgImg.css("width"));
        var height = parseFloat(bgImg.css("height"));
        if (dir == 'Up') {
            delta = 1;
        } else {
            delta = -1;
        }
        bgImg.css("width", width * (1 + 0.1 * delta));
        bgImg.css("height", height * (1 + 0.1 * delta));
        bgImg.css("left", parseFloat(bgImg.css("left")) - (width * 0.1 / 2) * delta);
        bgImg.css("top", parseFloat(bgImg.css("top")) - (height * 0.1 / 2) * delta);
        renderPanel();
        return false;
    });

    // 渲染画布面板
    var renderPanel = function () {
        canvas[0].width = 256;
        canvas[0].height = 256;
        var imgScale = image.width / bgImg.width();
        var context = canvas[0].getContext('2d');
        var sx = (bgImg.width() * imgScale / 2 ) - canvas.width() / 2 * imgScale,
            sy = (bgImg.height() * imgScale / 2) - canvas.height() / 2 * imgScale,
            sw = canvas.width() * imgScale, sh = canvas.height() * imgScale;
        var moveX = panel.width() / 2 - parseFloat(bgImg.css("left")) - bgImg.width() / 2;
        var moveY = panel.height() / 2 - parseFloat(bgImg.css("top")) - bgImg.height() / 2;
        context.drawImage(image, sx + moveX * imgScale, sy + moveY * imgScale, sw, sh, 0, 0, canvas[0].width, canvas[0].height);
    };
    s.render({
         url: "/api/upload/0"
        ,elem: "#LAY_avatarSrc"
        ,field: "picture"
        ,exts: 'jpg|png|gif|jpeg'
        ,auto: false
        ,bindAction: ".upload-btn"
        // 选择文件回调
        ,choose: function(obj){
            obj.preview(function(index, file, result){
                panel.show();
                $(".canvas-group").show();
                image.src = result;
                image.onload = function(){
                    bgImg.attr("src", result);
                    if(bgImg.width() >= bgImg.height()){
                        bgImg.css("height", canvas.width());
                    }else {
                        bgImg.css("width", canvas.height());
                    }
                    bgImg.css("top", (panel.height() - bgImg.height()) / 2);
                    bgImg.css("left", (panel.width() - bgImg.width()) / 2);
                    renderPanel();
                }
            });
        }
        ,before: function(obj){
            files = obj.pushFile();
            var index, file;
            for(index in files) {
                file = files[index];
            }
            var dataurl = canvas[0].toDataURL(file.type, 0.92);
            var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
                bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
            while(n--){
                u8arr[n] = bstr.charCodeAt(n);
            }
            files[index] = new File([u8arr], file.name, {type:mime});
        }
        ,done: function (response) {
            response.code === 200 ? r.val(response.data.url) : e.msg(response.message,{icon:5});
        }
    });
    // 关闭裁切面板及清空文件
    $(".upload-close").on("click", function () {
        panel.hide();
        $(".canvas-group").hide();
    });
    a.events.avartatPreview = function (t) {
        var i = r.val();
        e.photos({photos: {title: "查看头像", data: [{src: i}]}, shade: .01, closeBtn: 1, anim: 5})
    }; n.on("submit(setmypass)", function (t) {
        return e.msg(JSON.stringify(t.field)), !1
    }); t("set", {})
});