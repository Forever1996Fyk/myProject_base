/**
 * Created by YuKai Fan on 2019/4/28.
 */
app.controller('permissionController', function ($scope, $controller, $compile, permissionService) {

//这其实是一种伪继承，并不是真正的继承，是通过传递scope，将基础controller的scope传递赋给子controller的scope,从而达到继承的效果
    $controller('baseController', {$scope:$scope});//第一个参数：表示要继承的controller;


    $scope.role = {};

    //查询所有
    $scope.findAll = function() {
        permissionService.findAll().success(
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
            object = permissionService.update(data);
        } else {
            object = permissionService.add(data);
        }

        return object;
    };

    //删除
    $scope.delete = function (data) {
        return permissionService.delete(data);
    };

    //批量删除
    $scope.delBatch = function (ids) {
        return permissionService.delBatch(ids);
    };

    //弹出层方法
    $scope.layerFrame = function (iframe, submitID, index, layero, tableObject) {
        var iframeWindow = window[iframe + index]
            ,submit = layero.find('iframe').contents().find('#'+ submitID);

        //监听提交
        iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
            var userForm = layero.find('iframe').contents().find('#permissionForm');
            data.field.pid = userForm.find('#pid').attr('pid');
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
        });

        submit.trigger('click');
    };

    //赋值操作
    $scope.assignment = function (id, name) {
        $("#retrunParent").val(id);
        $("#retrunParent").attr('lastName', name);
    };

    layui.use(['table', 'layer', 'form', 'tree'], function() {
        var layer = layui.layer;
        var table = layui.table;

        var tableObject = table.render({
            id:"id"
            ,elem: '#permissionListTable'
            , height: 500
            , url: '../../../permission/search'//数据接口
            , parseData: $scope.paginationConf.parseData
            , response: $scope.paginationConf.response
            , page: $scope.paginationConf.page //开启分页
            , limits: $scope.paginationConf.limits
            , limit: $scope.paginationConf.limit
            , where: {
                level: 1
            }
            , cols: [[ //表头,field要与实体类字段相同
                {type: 'checkbox'}
                , {field: 'name', title: '权限名称', align: 'center', event: 'getChildPer', style: 'cursor:pointer', templet: function (data) {
                    var result;
                    if (data.pid) {
                        result = '<span style="color: cornflowerblue">'+ data.name +'</span>';
                    } else {
                        result = data.name;
                    }
                    return result;
                }}
                , {field: 'pName', title: '上级权限名称', align: 'center', templet: function (data) {
                    var result;
                    if (!$.isEmptyObject(data.pPermission)) {
                        result = data.pPermission.name;
                    } else {
                        result = '无';
                    }
                    return result;
                }}
                , {field: 'range', title: '排序', align: 'center'}
                , {field: 'level', title: '等级', align: 'center'}
                , {field: 'url', title: '链接', align: 'center'}
                , {field: 'remark', title: '备注', align: 'center'}
                , {field: 'status', title: '是否禁用', align: 'center', templet: function (data) {
                    var result;
                    if (data.status === 1) {
                        result = '正常';
                    } else {
                        result = '禁用';
                    }
                    return result;
                }}
                , {title: '操作', width: 300, toolbar: '#btn', align: 'center'}
            ]]

        });
        /*
         表格点击事件写法,其中tool('这是表格的lay-filter'),监听点击事件         */
        table.on('tool(tblist)', function (obj) {
            if (obj.event == 'edit') {
                active.edit(obj.data);
            } else if (obj.event == 'del') {
                active.delete(obj.data);
            } else if (obj.event == 'getChildPer') {
                active.getChildPermission(obj.data);
            }

        });

        var active = {
            //新增
            add:function () {
                layer.open({
                    type: 2
                    ,title: '添加权限'
                    ,content: 'permissionform.html'
                    ,maxmin: true
                    ,area: ['500px', '450px']
                    ,btn: ['确定', '取消']
                    ,yes: function(index, layero){
                        $scope.layerFrame('layui-layer-iframe', 'permissionSubmit', index, layero, tableObject);
                    }
                    ,success:function (layero, index) {//弹出框,弹出成功后操作,向表单赋值
                        var userForm = layero.find('iframe').contents().find('#permissionForm');
                        var lastName = $("#retrunParent").attr("lastName");
                        if (lastName && lastName !== undefined) {
                            userForm.find('#pid').val(lastName);
                        }
                    }
                });
            },

            //编辑
            edit:function (data) {
                layer.open({
                    type: 2
                    ,title: '编辑权限'
                    ,content: 'permissionform.html'
                    ,maxmin: true
                    ,area: ['500px', '450px']
                    ,btn: ['确定', '取消']
                    ,yes: function(index, layero){
                        $scope.layerFrame('layui-layer-iframe', 'permissionSubmit', index, layero, tableObject);
                    }
                    ,success:function (layero, index) {//弹出框,弹出成功后操作,向表单赋值
                        var userForm = layero.find('iframe').contents().find('#permissionForm');
                        for (var key in data) {//遍历json数据，并将数据复制到form表单对应的字段，这里要求
                            userForm.find('#' + key).val(data[key]);
                        }

                        var lastName = $("#retrunParent").attr("lastName");
                        var pid = $("#retrunParent").val();
                        if (lastName && lastName !== undefined) {
                            userForm.find('#pid').val(lastName);
                            userForm.find('#pid').attr('pid', pid);
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

            //跳转到下级权限目录
            getChildPermission: function (data) {
                $scope.assignment(data.id, data.name);
                //刷新表格
                tableObject.reload({
                    page: '{curr:1}',
                    where: {'pid': data.id}
                });
            },

            //返回主目录
            retrunParent: function () {
                $scope.assignment(null, null);
                //刷新表格
                tableObject.reload({
                    page: '{curr:1}',
                    where: {level: 1}
                });
            }
            
        };
        $('.layui-btn.layuiadmin-btn-permission').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    })

});
