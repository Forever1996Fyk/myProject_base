/**
 * Created by Administrator on 2019/5/5.
 */
app.service('indexService', function ($http) {

    //获取菜单与用户信息
   this.getMenu = function (token) {
       return $http.get('../menu',  {
           headers : {'Authorization' : token}});
   }
});
