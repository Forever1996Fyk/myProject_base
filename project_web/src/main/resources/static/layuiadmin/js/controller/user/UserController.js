/**
 * Created by Administrator on 2019/4/24.
 */
app.controller('userController', function($scope, userService) {
    
    $scope.findAll = function() {
        userService.findAll().success(
            function (response) {
                $scope.userList = response.data;
                console.log(response);
            }
        );
    }

    $scope.save = function(data) {
        userService.addUser(data).success(
            function(response) {
                console.log(response);
                $scope.findAll();
            }
        );
    }

    layui.use('layer', function() {
        var layer = layui.layer;
        var active = {
            add:function () {
                layer.open({
                    type: 2
                    ,title: '添加用户'
                    ,content: 'userform.html'
                    ,maxmin: true
                    ,area: ['500px', '450px']
                    ,btn: ['确定', '取消']
                    ,yes: function(index, layero){
                        var iframeWindow = window['layui-layer-iframe'+ index]
                            ,submitID = 'LAY-user-front-submit'
                            ,submit = layero.find('iframe').contents().find('#'+ submitID);

                        //监听提交
                        iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
                            $scope.save(data.field);
                            layer.close(index); //关闭弹层
                        });

                        submit.trigger('click');
                    }
                });
            }
        };
        $('.layui-btn.layuiadmin-btn-useradmin').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    })
})