/**
 * Created by Administrator on 2019/5/5.
 */
app.controller('indexController', function ($scope, indexService) {

    $scope.getMenu = function () {
         indexService.getMenu().success(
            function (response) {
                console.log(response);
                $scope.menuList = response.data.treeMap;
                $scope.user = response.data.user;
            }
        );
    };
    
    $scope.logout = function () {
        indexService.logout().success(
            function (response) {
                location.href = '/';
            }
        );
    };

    layui.config({
        base: '../layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'element'], function () {
        //$scope.getMenu();
    });

});
