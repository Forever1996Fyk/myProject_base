/**
 * Created by Administrator on 2019/5/9.
 */
app.controller('userInfoController', function ($scope, $controller, userInfoService) {

    //这其实是一种伪继承，并不是真正的继承，是通过传递scope，将基础controller的scope传递赋给子controller的scope,从而达到继承的效果
    $controller('baseController', {$scope:$scope});//第一个参数：表示要继承的controller;
    
    $scope.getUserInfo = function () {
        userInfoService.getUserInfo().success(
            function (response) {
                $scope.user = response.data;
            }
        )
    };

    layui.config({
        base: '../../layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'set'], function () {

        var form = layui.form;
        $(".userInfoForm").on("mousedown", function () {
            $(this).css("opacity", 1);
        });

    });
});