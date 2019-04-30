/**
 * Created by YuKaiFan on 2019/4/30.
 */
app.service('authService', function ($http) {

    this.findPermission = function (data) {
        return $http.get('../../../role/findPermission?role=' + data);
    }
});
