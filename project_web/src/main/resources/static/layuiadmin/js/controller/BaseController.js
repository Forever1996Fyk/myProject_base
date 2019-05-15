/**
 * Created by YuKai Fan on 2019/4/27.
 */
app.controller('baseController', function ($scope) {

    //公共分页配置
    $scope.paginationConf = {
        parseData: function(res) {//将返回数据统一成layui默认数据格式,这样分页才能生效
            return {
                "code": res.code,
                "msg": res.message,
                "count": res.data.total,
                "data": res.data.rows
            }
        }
        , response:{//默认加载数据的状态码是0，改为200
            statusCode: 200
        }
        , page: true //开启分页
        , limits: [10, 20, 30]
        , limit: 10
    };


});
