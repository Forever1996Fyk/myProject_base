/**
 * Created by YuKaiFan on 2019/4/24.
 */
app.service('userService', function($http) {

    this.findAll = function () {
        return $http.get('../../../user');
    };

    this.add = function (entity) {
        return $http.post('../../../user', entity)
    };

    this.update = function (entity) {
        return $http.put('../../../user', entity)
    };

    this.delete = function (data) {
        return $http.delete('../../../user/del/' + data.id);
    };
    
    this.delBatch = function (ids) {
        return $http.delete('../../../user/delBatch?ids=' + ids);
    };
    
    this.findRole = function (data) {
        return $http.post('../../../user/findRole', data);
    }
})