/**
 * Created by Administrator on 2019/4/24.
 */
app.controller('userController', function($scope, $controller, userService) {

    //这其实是一种伪继承，并不是真正的继承，是通过传递scope，将基础controller的scope传递赋给子controller的scope,从而达到继承的效果
    $controller('baseController', {$scope:$scope});//第一个参数：表示要继承的controller;


    $scope.user = {};

    //查询所有
    $scope.findAll = function() {
        userService.findAll().success(
            function (response) {
                $scope.userList = response.data;
                console.log(response);
            }
        );
    };

    //保存
    $scope.save = function(data) {
        var object = null;
        if (data.id) {
            object = userService.update(data);
        } else {
            object = userService.add(data);
        }

        return object;
    };

    //删除
    $scope.delete = function (data) {
        return userService.delete(data);
    };
    
    //批量删除
    $scope.delBatch = function (ids) {
        return userService.delBatch(ids);
    };

    //查询角色
    $scope.findRole = function (layero, userData) {
        console.log(userData);
        userService.findRole(userData).success(
            function (response) {
                var roleList = layero.find('iframe').contents().find('.roleList');
                var roleForm = layero.find('iframe').contents().find('#roleForm');
                var data = response.data;
                if (data.length > 0) {
                    roleForm.find("#id").val(userData.id);
                    for (var i = 0; i < data.length; i++) {
                        if (data[i].selected === 1) {
                            roleList.append('<input type="checkbox" name="roleId" lay-filter="checkRole" lay-skin="primary" checked title="'+ data[i].role_name +'" value="'+ data[i].id +'">');
                        } else if (data[i].selected === 0 || !data[i].selected) {
                            roleList.append('<input type="checkbox" name="roleId" lay-filter="checkRole" lay-skin="primary" title="'+ data[i].role_name +'" value="'+ data[i].id +'">');
                        }
                    }
                    //获取新窗口对象
                    var iframeWindow = layero.find('iframe')[0].contentWindow;
                    //重新渲染
                    iframeWindow.layui.form.render();//使用js拼接layui中的元素时,必须要此操作才能渲染
                }
            }
        );
    };

    //弹出层方法
    $scope.layerFrame = function (iframe, submitID, index, layero, tableObject) {
        var iframeWindow = window[iframe+ index]
            ,submit = layero.find('iframe').contents().find('#'+ submitID);

        //监听提交
        iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
            if (submitID === 'roleSubmit') {
                var roleList = layero.find('iframe').contents().find('.roleList');
                var checkElemList = roleList.children('input');
                if (checkElemList.length > 0) {
                    var roleIds = [];
                    var id = roleList.find('#id').val();
                    roleIds.push('id=' + id);
                    for (var i = 0; i < checkElemList.length; i++) {
                        if (checkElemList[i].checked) {
                            roleIds.push('roleId=' + checkElemList[i].value);
                        }
                    }
                    $.post('../../../user/saveRole', roleIds.join('&'), function(response){
                        if (response.data === null) {
                            response.data = 'submit[refresh]';
                        }
                        if (response.code === 200) {
                            layer.msg(response.message);
                            layer.close(index); //关闭弹层
                            //刷新表格
                            tableObject.reload({
                                page: '{curr:1}'
                            });
                        } else {
                            layer.msg(response.message);
                            return false;
                        }
                    });
                }
            } else {
                $scope.save(data.field).success(//保存事件
                    function (response) {
                        if (response.code === 200) {
                            layer.msg(response.message);
                            layer.close(index); //关闭弹层
                            //刷新表格
                            tableObject.reload({
                                page: '{curr:1}'
                            });
                        } else {
                            layer.msg(response.message);
                        }
                    }
                );
            }
        });

        submit.trigger('click');
    };

  layui.use(['table', 'layer', 'form'], function() {
        var layer = layui.layer;
        var table = layui.table;

        var tableObject = table.render({
            id:"id"
            ,elem: '#userListTable'
            , height: 500
            , url: '../../../user/search'//数据接口
            , parseData: $scope.paginationConf.parseData
            , response: $scope.paginationConf.response
            , page: $scope.paginationConf.page //开启分页
            , limits: $scope.paginationConf.limits
            , limit: $scope.paginationConf.limit
            , cols: [[ //表头,field要与实体类字段相同
                {type: 'checkbox'}
                , {field: 'account', title: '用户账号', align: 'center'}
                , {field: 'user_name', title: '用户名称', align: 'center'}
                , {field: 'nick_name', title: '用户昵称', align: 'center'}
                , {field: 'user_icon', title: '头像',  align: 'center'}
                , {field: 'age', title: '年龄', align: 'center'}
                , {field: 'sex', title: '性别', align: 'center', templet: function (data) {
                    var result = '';
                    if (data.sex === 1) {
                        result = '男';
                    } else {
                        result = '女';
                    }
                    return result;
                }}
                , {field: 'marry_flag', title: '婚否', align: 'center', templet: function (data) {
                    var result = '';
                    if (data.marry_flag) {
                        if (data.marry_flag === 1) {
                            result = '已婚';
                        } else if (data.marry_flag === 0) {
                            result = '未婚';
                        }
                    }
                    return result;
                }}
                , {field: 'phone', title: '手机号',align: 'center'}
                , {field: 'email', title: '邮箱', align: 'center'}
                , {field: 'address', title: '地址', align: 'center'}
                , {field: 'idcard', title: '身份证号', align: 'center'}
                , {field: 'create_time', title: '创建时间', align: 'center'}
                , {field: 'status', title: '是否禁用', align: 'center', templet: function (data) {
                    var result;
                    if (data.status === 1) {
                        result = '正常';
                    } else {
                        result = '禁用';
                    }
                    return result;
                }}
                , {title: '操作', toolbar: '#btn', align: 'center'}
            ]]

        });
        /*
        表格点击事件写法,其中tool('这是表格的lay-filter'),监听点击事件         */
        table.on('tool(tblist)', function (obj) {
            if (obj.event == 'edit') {
                active.edit(obj.data);
            } else {
                active.delete(obj.data);
            }

        });

        var active = {
            //新增
            add:function () {
                layer.open({
                    type: 2
                    ,title: '添加用户'
                    ,content: '/userManager/userform'
                    ,maxmin: true
                    ,area: ['500px', '450px']
                    ,btn: ['确定', '取消']
                    ,yes: function(index, layero){
                        $scope.layerFrame('layui-layer-iframe', 'userSubmit', index, layero, tableObject, null);
                    }
                });
            },

            //搜索
            search:function () {
                tableObject.reload({
                    where:{
                        account: $scope.user.account,
                        user_name: $scope.user.user_name,
                        nick_name: $scope.user.nick_name,
                        idcard: $scope.user.idcard
                    }
                })
            },

            //编辑
            edit:function (data) {
                layer.open({
                    type: 2
                    ,title: '编辑用户'
                    ,content: 'userform.html'
                    ,maxmin: true
                    ,area: ['500px', '450px']
                    ,btn: ['确定', '取消']
                    ,yes: function(index, layero){
                        $scope.layerFrame('layui-layer-iframe', 'userSubmit', index, layero, tableObject, null);
                    }
                    ,success:function (layero, index) {//弹出框,弹出成功后操作,向表单赋值
                        var userForm = layero.find('iframe').contents().find('#userForm');
                        for (var key in data) {//遍历json数据，并将数据复制到form表单对应的字段，这里要求
                            userForm.find('#' + key).val(data[key]);
                        }
                    }
                })
            },

            //删除
            delete:function (data) {
                layer.confirm ('确定删除吗?', function (index) {
                    $scope.delete(data).success(
                        function (response) {
                            layer.msg(response.message);
                            if (response.code === 200) {
                                //刷新表格
                                tableObject.reload({
                                    page: '{curr:1}'
                                });
                            }

                        }
                    );
                })
            },

            //批量删除
            batchDel:function () {
                var checkStatus = table.checkStatus('id')//注意这个id不是html中table元素上的id，而是table:render中定义的id
                    ,ids = [];
                if (checkStatus.data.length === 0) {
                    layer.msg('请选择要删除的数据行');
                    return;
                }

                for (var i = 0; i < checkStatus.data.length; i++) {
                    ids.push(checkStatus.data[i].id);
                }
                layer.msg('删除中...', {icon: 16, shade: 0.3, time: 5000});

                $scope.delBatch(ids).success(
                    function (response) {
                        if (response.code === 200) {
                            layer.msg(response.message);
                            //刷新表格
                            tableObject.reload({
                                page: '{curr:1}'
                            });
                        } else {
                            layer.msg(response.message);
                        }
                    }
                );
            },

            //分配角色
            assignRole: function () {
                var checkStatus = table.checkStatus('id');//注意这个id不是html中table元素上的id，而是table:render中定义的id
                if (checkStatus.data.length === 0) {
                    layer.msg('请选择用户');
                    return;
                } else if (checkStatus.data.length > 1) {
                    layer.msg('只能选择一条数据');
                    return;
                }
                layer.open({
                    type: 2
                    ,title: '角色分配'
                    ,content: 'assignRole.html'
                    ,maxmin: true
                    ,area: ['500px', '450px']
                    ,btn: ['确定', '取消']
                    ,yes: function(index, layero){
                        $scope.layerFrame('layui-layer-iframe', 'roleSubmit', index, layero, tableObject, checkStatus.data);
                    }
                    ,success:function (layero, index) {//弹出框,弹出成功后操作

                        $scope.findRole(layero, checkStatus.data[0]);
                        //iframeWindow.layui.form.render();
                    }
                })
            }
        };
        $('.layui-btn.layuiadmin-btn-useradmin').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    })
})