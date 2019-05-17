/**
 * Created by Administrator on 2019/5/15.
 */
app.service('forgetService', function ($http) {
    
    this.verifyPhone = function (data) {
        return $http.post('../../verify/phone', data);
    };
    
    this.resetPwd = function (data) {
        return $http.put('../../resetPwd', data);
    };
    
    this.verifyAuthCode = function (data) {
        return $http.put('../../verify/authCode', data);
    }

});