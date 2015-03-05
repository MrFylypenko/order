/**
 * Created by niko on 05.03.15.
 */

angular.module('app').service('$loginService', $loginService);

$loginService.$inject = ['$http'];
function $loginService(http) {
    this.login = function (login, password) {
        console.log(login, password);
    }
}