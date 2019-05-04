/**
 * Created by YuKai Fan on 2019/5/3.
 */
app.service('loginService', function ($http) {
    this.login = function (data) {
        return $http.post('../../../login', data);
    }
});