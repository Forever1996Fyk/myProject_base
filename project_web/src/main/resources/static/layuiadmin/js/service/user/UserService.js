/**
 * Created by YuKaiFan on 2019/4/24.
 */
app.service('userService', function($http) {

    this.findAll = function () {
        return $http.get('../../../user');
    }

    this.addUser = function (entity) {
        return $http.post('../../../user', entity)
    }
})