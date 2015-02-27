'use strict';

var site = angular.module('orderSite', [
    'ngRoute',
    'order',
    'ui.bootstrap'
]);


/*
site.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/view/additem', {
                templateUrl: 'view/additem',
                controller: 'AddItemCTRL'
            }).
            when('/view/incoming', {
                templateUrl: 'view/incoming',
                controller: 'Incoming'
            }).
            when('/view/outgoing', {
                templateUrl: 'view/outgoing',
                controller: 'Outgoing'
            }).
            when('/view/statistic', {
                templateUrl: 'view/statistic',
                controller: 'Statistic'
            }).
            when('/view/statistic2', {
                templateUrl: 'view/statistic2',
                controller: 'Statistic2'
            }).
            when('/view/registration', {
                templateUrl: 'view/registration',
                controller: 'Registration'
            }).
            when('/view/act', {
                templateUrl: 'view/act',
                controller: 'Registration'
            }).
            when('/view/uploadsuccess', {
                templateUrl: 'view/uploadsuccess',
                controller: 'Registration'
            }).
            otherwise({
                redirectTo: '/view/statistic'
            });
    }]);*/
