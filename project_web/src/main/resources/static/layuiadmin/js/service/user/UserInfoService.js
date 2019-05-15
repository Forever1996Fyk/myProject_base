/**
 * Created by Administrator on 2019/5/9.
 */
app.service('userInfoService', function ($http) {
    
    this.getUserInfo = function () {
        return $http.get('../../userInfo');
    }
});