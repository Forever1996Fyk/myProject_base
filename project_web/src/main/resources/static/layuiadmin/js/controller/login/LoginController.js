/**
 * Created by YuKai Fan on 2019/5/3.
 */
app.controller('loginController', function ($scope, loginService) {

    //登录
    $scope.login = function (data) {
        return loginService.login(data);
    };
    
    layui.config({
        base: '../../layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'user'], function(){
        var setter = layui.setter
            ,admin = layui.admin
            ,form = layui.form;

        form.render();

        //提交
        form.on('submit(loginSubmit)', function(obj){
            var data = obj.field;
            $scope.login(data).success(
                function (response) {
                    console.log(response);
                    if (response.code === 200) {
                        //请求成功后，写入 access_token
                        layui.data(setter.tableName, {
                            key: setter.request.tokenName
                            ,value: response.data
                        });
                        //登入成功的提示与跳转
                        layer.msg(response.message, {
                            offset: '15px'
                            ,icon: 1
                            ,time: 1000
                        }, function () {
                            location.href = '../../views/index.html';
                        });
                    } else {
                        return layer.msg(response.message);
                    }
                }
            );
        });

    });
});