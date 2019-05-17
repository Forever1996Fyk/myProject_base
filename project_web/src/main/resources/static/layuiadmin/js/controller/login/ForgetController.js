/**
 * Created by Administrator on 2019/5/15.
 */
app.controller('forgetController', function ($scope, forgetService) {

    //验证手机号
    $scope.verifyPhone = function (data) {
        return forgetService.verifyPhone(data)
    };

    //验证验证码是否正确
    $scope.verifyAuthCode = function (data) {
        return forgetService.verifyAuthCode(data)
    };

    //重置密码
    $scope.resetPwd = function (data) {
        return forgetService.resetPwd(data);
    };

    layui.config({
        base: '../../layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'user'], function(){
        var form = layui.form,
        t = (layui.layer, layui.laytpl, layui.setter, layui.view, layui.admin);

        t.sendAuthCode({
            elem: "#getsmscode",
            elemPhone: "#phone",
            elemVercode: "#LAY-user-login-vercode"
            ,ajax:{
                url: "../../verify/phone" + $('#phone').val(),
                type: 'GET',
                success: function (response) {
                    if (response.code === 200) {
                        console.log($('#phone').val());
                        layer.msg(response.message);
                    } else {
                        return layer.msg(response.message);
                    }
                }
            }
        });
        form.render();

        form.on('submit(LAY-user-forget-submit)', function (obj) {
            var field = obj.field;
            location.hash = '/type=resetpass';
            location.reload();

            /*$scope.verifyAuthCode(field).success(
                function (response) {
                    if (response.code === 200) {
                        location.hash = '/type=resetpass';
                        location.reload();
                    } else {
                        return layer.msg(response.message);
                    }
                }
            );*/
        });

        //重置密码
        form.on('submit(LAY-user-forget-resetpass)', function(obj){
            var field = obj.field;
            //确认密码
            if(field.password !== field.repass){
                return layer.msg('两次密码输入不一致');
            }

            $scope.resetPwd(field).success(
                function (response) {
                    if (response.code === 200) {
                        layer.msg('密码已成功重置', {
                            offset: '15px'
                            ,icon: 1
                            ,time: 1000
                        }, function(){
                            location.href = 'login.html'; //跳转到登入页
                        });
                    } else {
                        layer.msg(response.message);
                    }
                }
            );
        });
    })
});