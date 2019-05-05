/**
 * Created by Administrator on 2019/5/5.
 */
app.controller('indexController', function ($scope, indexService) {

    $scope.getMenu = function () {
        var token = localStorage.getItem("access_token");
        indexService.getMenu(token).success(
            function (response) {
                console.log(response);
                $scope.menuList = response.treeMap;
            }
        );
    };

    layui.config({
        base: '../layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use('index', function () {
    })
});
