/**
 * Created by niko on 05.03.15.
 */

angular.module('app').controller('loginController', loginController);

loginController.$inject = ['$scope', '$loginService'];
function loginController(scope, loginService) {
    scope.username = '';
    scope.password = '';
    scope.login = function () {
        loginService.login(scope.username, scope.password);
    }
}
