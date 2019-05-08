/**
 * Created by YuKaiFan on 2019/5/3.
 */
app.controller('regController', function ($scope, regService) {

    $scope.regSubmit = function (data) {
        return regService.regSubmit(data);
    };

    //layui配置
    layui.config({
        base: '../../layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'user'], function () {//index,user都表示layui定义的模块，分别对应一个js
        var form = layui.form;
        form.render();

        //提交
        form.on('submit(regSubmit)', function (obj) {
            var data = obj.field;
            //确认密码
            if (data.password !== data.repass) {
                return layer.msg('两次密码输入不一致');
            }
            //是否同意用户协议
            if (!data.agreement) {
                return layer.msg('你必须同意用户协议才能注册');
            }

            $scope.regSubmit(data).success(
                function (response) {
                    if (response.code === 200) {
                        layer.msg(response.message, {
                            offset: '15px'
                            , icon: 1
                            , time: 1000
                        }, function () {
                            location.hash = '/'; //跳转到登入页
                        });
                    } else {
                        return layer.msg(response.message);
                    }
                }
            );

            return false;
        });
    });



});
