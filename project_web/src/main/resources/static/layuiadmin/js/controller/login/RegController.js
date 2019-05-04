/**
 * Created by YuKaiFan on 2019/5/3.
 */
app.controller('regController', function ($scope, $controller, regService) {

    //这其实是一种伪继承，并不是真正的继承，是通过传递scope，将基础controller的scope传递赋给子controller的scope,从而达到继承的效果
    $controller('baseController', {$scope:$scope});//第一个参数：表示要继承的controller;

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
                            location.hash = '/user/login'; //跳转到登入页
                        });
                    } else {
                        return layer.msg(response.message);
                    }
                }
            );

            return false;
        });
    });

    //layui插件
    layui.use(['layer', 'form'], function() {
        var layer = layui.layer;

    });


});
