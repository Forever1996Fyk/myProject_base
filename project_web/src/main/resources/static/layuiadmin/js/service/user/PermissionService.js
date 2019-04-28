/**
 * Created by Administrator on 2019/4/28.
 */
app.service('permissionService', function ($http) {
    this.findAll = function () {
        return $http.get('../../../permission');
    };

    this.add = function (entity) {
        return $http.post('../../../permission', entity)
    };

    this.update = function (entity) {
        return $http.put('../../../permission', entity)
    };

    this.delete = function (data) {
        return $http.delete('../../../permission/del/' + data.id);
    };

    this.delBatch = function (ids) {
        return $http.delete('../../../permission/delBatch?ids=' + ids);
    }
});