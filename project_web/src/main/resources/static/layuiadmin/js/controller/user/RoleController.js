/**
 * Created by Administrator on 2019/4/24.
 */
app.controller('roleController', function($scope, $controller, roleService) {

    //这其实是一种伪继承，并不是真正的继承，是通过传递scope，将基础controller的scope传递赋给子controller的scope,从而达到继承的效果
    $controller('baseController', {$scope:$scope});//第一个参数：表示要继承的controller;


    $scope.role = {};

    //查询所有
    $scope.findAll = function() {
        roleService.findAll().success(
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
            object = roleService.update(data);
        } else {
            object = roleService.add(data);
        }

        return object;
    };

    //删除
    $scope.delete = function (data) {
        return roleService.delete(data);
    };
    
    //批量删除
    $scope.delBatch = function (ids) {
        return roleService.delBatch(ids);
    };

    //弹出层方法
    $scope.layerFrame = function (iframe, submitID, index, layero, tableObject, checkData) {
        var iframeWindow = window[iframe + index]
            ,submit = layero.find('iframe').contents().find('#'+ submitID);

        //监听提交
        iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
            if (submitID === "authSubmit") {
                var zTreeObj = $.fn.zTree.getZTreeObj("authTree");
                var authList = zTreeObj.getCheckedNodes(true);
                var authIds = [];
                if (checkData !== null) {
                    authIds.push("id=" + checkData[0].id);
                }
                authList.forEach(function(item){
                    if(item.id > 0){
                        authIds.push("authId="+item.id);
                    }
                });
                if (authIds.length <= 0) {
                    layer.msg('请选择权限');
                    return;
                }

                $.post('../../../role/savePermission', authIds.join("&"), function(response){
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

    //获取该角色的权限
    $scope.findPermission = function (data, tree) {
        console.log(data);
        roleService.findPermission(data[0]).success(
            function (response) {
                $scope.loadTree(response, tree);
            }
        );
    };
    
    //保存权限
    $scope.savePermission = function (authIds) {
        return roleService.savePermission(authIds);
    };

    //加载ztree
    $scope.loadTree = function (result, tree) {
        var setting = {
            check: {
                enable: true,
                chkboxType: { "Y" : "ps", "N" : "ps" }
            },
            data: {
                simpleData: {
                    enable: true
                }
            }
        };
        var keyPid = [];
        result.data.forEach(function(item){
            keyPid[item.pid] = true;
        });
        var zNodes =[];
        result.data.forEach(function (item) {
            var menu = {
                id: item.id,
                pId: item.pid,
                name: item.name
            };
            if(item.pid === 0){
                menu.open = true;
            }
            if(item.selected === 1){
                menu.checked = true;
            }
            zNodes.push(menu);
        });
        $.fn.zTree.init(tree, setting, zNodes);
    };

    //layui插件
  layui.use(['table', 'layer', 'form'], function() {
        var layer = layui.layer;
        var table = layui.table;

        var tableObject = table.render({
            id:"id"
            ,elem: '#roleListTable'
            , height: 500
            , url: '../../../role/search'//数据接口
            , parseData: $scope.paginationConf.parseData
            , response: $scope.paginationConf.response
            , page: $scope.paginationConf.page //开启分页
            , limits: $scope.paginationConf.limits
            , limit: $scope.paginationConf.limit
            , cols: [[ //表头,field要与实体类字段相同
                {type: 'checkbox'}
                , {field: 'role_name', title: '角色名称', align: 'center'}
                , {field: 'remark', title: '备注', align: 'center'}
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
                , {title: '操作', width: 300, toolbar: '#btn', align: 'center'}
            ]]

        });
        /*
        表格点击事件写法,其中tool('这是表格的lay-filter'),监听点击事件         */
        table.on('tool(tblist)', function (obj) {
            if (obj.event === 'edit') {
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
                    ,title: '添加角色'
                    ,content: 'roleform.html'
                    ,maxmin: true
                    ,area: ['500px', '450px']
                    ,btn: ['确定', '取消']
                    ,yes: function(index, layero){
                        $scope.layerFrame('layui-layer-iframe', 'roleSubmit', index, layero, tableObject, null);
                    }
                });
            },

            //搜索
            search:function () {
                tableObject.reload({
                    where:{
                        role_name: $scope.role.role_name
                    }
                })
            },

            //编辑
            edit:function (data) {
                layer.open({
                    type: 2
                    ,title: '编辑角色'
                    ,content: 'roleform.html'
                    ,maxmin: true
                    ,area: ['500px', '450px']
                    ,btn: ['确定', '取消']
                    ,yes: function(index, layero){
                        $scope.layerFrame('layui-layer-iframe', 'roleSubmit', index, layero, tableObject, null);
                    }
                    ,success:function (layero, index) {//弹出框,弹出成功后操作,向表单赋值
                        var userForm = layero.find('iframe').contents().find('#roleForm');
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
            
            //角色授权
            authorization: function () {
                var checkStatus = table.checkStatus('id')//注意这个id不是html中table元素上的id，而是table:render中定义的id
                if (checkStatus.data.length === 0) {
                    layer.msg('请选择要授权的角色');
                    return;
                } else if (checkStatus.data.length > 1) {
                    layer.msg('只能选择一条数据');
                    return;
                }
                layer.open({
                    type: 2
                    ,title: '授权'
                    ,content: 'authorization.html'
                    ,maxmin: true
                    ,area: ['500px', '450px']
                    ,btn: ['确定', '取消']
                    ,yes: function(index, layero){
                        $scope.layerFrame('layui-layer-iframe', 'authSubmit', index, layero, tableObject, checkStatus.data);
                    }
                    ,success:function (layero, index) {//弹出框,弹出成功后操作,向表单赋值
                        var tree = layero.find('iframe').contents().find('#authTree');
                        $scope.findPermission(checkStatus.data, tree);
                    }
                })
            }
        };
        $('.layui-btn.layuiadmin-btn-role').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    })
});