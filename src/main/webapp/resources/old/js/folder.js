'use strict';

var siteBonvio = angular.module('siteBonvio', [
    'ngRoute',
    'order'
]);

var phonecatControllers = angular.module('order', []);

var parentFolder = 0;

phonecatControllers.controller('myFolder', ['$scope', '$http',
    function ($scope, $http) {

        $scope.addfolder = null;

        $scope.submit = function () {

            //$scope.addfolder.parentId = parentFolder;

            var data = new Object();
            data.id = 0;
            data.name = $scope.addfoldername;
            data.parentId = parentFolder;
            data.type = $scope.addfoldertype;

            alert (parentFolder);
            //data.id = 0;
            //data.parentId = parentFolder;
            //alert (data);

            $http({method: 'POST', url: '/spring-site/folder/addfolder', data: data, headers: {'Content-Type': 'application/json'}})
                .success(function (data) {

                    var data = null;

                    $http({method: 'GET', url: '/spring-site/folder/getfolderbyparentid/' + parentFolder, data: data, headers: {'Content-Type': 'application/x-www-form-urlencoded'}})
                        .success(function (data) {

                            $scope.folders = data;
                            parentFolder = $scope.folders[0].parentId;
                            //alert("Добавлено в базу");
                            //$scope.items = data;
                        }).error(function (res) {
                            alert("Ошибка при отправке запроса");
                        });



                }).error(function (res) {
                    alert("Ошибка при отправке запроса");
                });
        }


        $scope.getfolderbyparentid = function (e) {

            var data = null;

            var test = $scope.folders;
            parentFolder = test[e].id;


            $http({method: 'GET', url: '/spring-site/folder/getfolderbyparentid/' + test[e].id, data: data, headers: {'Content-Type': 'application/x-www-form-urlencoded'}})
                .success(function (data) {


                        $scope.folders = data;
                       // parentFolder = $scope.folders[0].parentId;

                    //alert("Добавлено в базу");
                    //$scope.items = data;
                }).error(function (res) {
                    alert("Ошибка при отправке запроса");
                });
        }


        /*  $http({method: 'POST', url: 'add', data: data, headers: {'Content-Type': 'application/x-www-form-urlencoded'}})
         .success(function (data) {
         $scope.addItem = {};
         //alert("Добавлено в базу");
         //$scope.items = data;
         }).error(function (res) {
         alert("Ошибка при отправке запроса");
         });*/

        var data = null;

        $http({method: 'GET', url: '/spring-site/folder/getfolderbyparentid/0', data: data, headers: {'Content-Type': 'application/x-www-form-urlencoded'}})
            .success(function (data) {

                $scope.folders = data;
                parentFolder = $scope.folders[0].parentId;
                //alert("Добавлено в базу");
                //$scope.items = data;
            }).error(function (res) {
                alert("Ошибка при отправке запроса");
            });


    }]);
