/**
 * Created by YuKaiFan on 2019/4/24.
 */
app.service('roleService', function($http) {

    this.findAll = function () {
        return $http.get('../../../role');
    };

    this.add = function (entity) {
        return $http.post('../../../role', entity)
    };

    this.update = function (entity) {
        return $http.put('../../../role', entity)
    };

    this.delete = function (data) {
        return $http.delete('../../../role/del/' + data.id);
    };
    
    this.delBatch = function (ids) {
        return $http.delete('../../../role/delBatch?ids=' + ids);
    }
});