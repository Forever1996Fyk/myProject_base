/**
 * Created by YuKaiFan on 2019/5/3.
 */
app.service('regService', function ($http) {
    this.regSubmit = function (data) {
        return $http.post('../../../register', data);
    }
});
