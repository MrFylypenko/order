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
        scope.selectedAction = 'update';
        scope.selectedDensity = density;
    };

    scope.setDensity = function() {
        if (scope.selectedAction == 'update') {
            storage.setDensity(scope.selectedDensity);
            scope.selectedAction = '';
        } else {
            storage.newDensity(scope.selectedDensity);
            scope.density.push(scope.selectedDensity);
        }
    };
}