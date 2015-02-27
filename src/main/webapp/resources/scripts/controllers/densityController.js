angular.module('app').controller('densityController', densityController);

densityController.$inject = ['$scope', '$storage'];
function densityController(scope, storage) {
    scope.manager = {};
    scope.getManagerInfo = storage.getManagerInfo(function (data) {
        scope.manager = data;
    });

    scope.density = [];
    scope.getDensity = function () {
        storage.getDensity(function (data) {
            scope.density = data;
        });
    };

    scope.selectedDensity = {};
    scope.setSelectedDensity = function (density) {
        scope.selectedDensity = density;
    };
}