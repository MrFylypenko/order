angular.module('app', ['ankular', 'ngRoute']).config(configuration);

configuration.$inject = ['$routeProvider'];
function configuration(routeProvider) {
    routeProvider
        .when('/admin', {
            templateUrl: 'resources/views/admin.html',
            controller:  'adminController'
        })
        .when('/manager', {
            templateUrl: 'resources/views/manager.html',
            controller:  'managerController'
        })
        .when('/storekeeper', {
            templateUrl: 'resources/views/storekeeper.html',
            controller:  'storekeeperController'
        })
        .when('/assistant', {
            templateUrl: 'resources/views/assistant.html',
            controller:  'assistantController'
        })
        .when('/recipe', {
            templateUrl: 'resources/views/manager/recipe.html',
            controller:  'recipeController'
        })
        .when('/density', {
            templateUrl: 'resources/views/manager/density.html',
            controller:  'densityController'
        }).
        otherwise({
            templateUrl: 'resources/views/login.html',
            controller: 'loginController'
        });
}