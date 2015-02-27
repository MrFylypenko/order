'use strict';

/* Controllers */

var order = angular.module('order', []);

order.controller('main', ['$scope', '$location', function($scope, $location) {
    $scope.$location = $location;
}]);




order.controller('AdminController', ['$scope', '$http', function($scope, $http) {

    $scope.admin = "Проверка работы ангуляра";

    $scope.currentuser = {};


    /*changeuserroles*/

    $http.get("admin/getusers").success(function (data, status, headers, config) {
        $scope.users = data;
    })

    $http.get("admin/getroles").success(function (data, status, headers, config) {
        $scope.roles = data;
    })


    $scope.selectUser = function(index){
        $scope.currentuser = $scope.users[index];
    }

    $scope.addRole = function(index){
        var role = $scope.roles[index];
        delete role.id;
        $scope.currentuser.userRole.push(role);
    }

    $scope.deleteRole = function(index){
        var newRoles = [];
        for(var i = 0; i< $scope.currentuser.userRole.length; i++){
            if (index != i)newRoles.push($scope.currentuser.userRole[i]);
        }


        $scope.currentuser.userRole = newRoles;
    }

    $scope.saveUser = function(){
        var data = $scope.currentuser;

        $http.post("admin/changeuserroles", data).success(function (data, status, headers, config) {
            console.log("ok");
        })
    }



}]);








/*


order.controller('Outgoing', ['$scope', '$http', '$routeParams',
    function ($scope, $http) {

        $scope.addeditems = outgoinItems;

        $scope.onKeyUp = function ($event, searchitem) {
            if (((searchitem != null) & (searchitem.length > 1)) & ($event.keyCode == 13)) {
                console.log($event.keyCode);
                var url = 'item/' + searchitem;
                $http.get(url).success(function (data, status, headers, config) {
                    $scope.searchedItems = data;
                })
            }
        };

        $scope.addItem = function (number, frominput) {
            var item = $scope.searchedItems[number];
            var itemClone = {};
            for (var key in item) {
                itemClone[key] = item[key];
            }
            itemClone.toOperation = frominput;
            var total = outgoinItems.push(itemClone);
            $scope.addeditems = outgoinItems;
            //$scope.searchedItems = [];
        }

        $scope.deleteItemAddedItems = function (index) {

            //deleting from revers array
            index = $scope.addeditems.length - index -1;

            var result = [];
            for (var i = 0; i < $scope.addeditems.length; i++) {
                if (i != index) {
                    var item = $scope.addeditems [i];
                    var itemClone = {};
                    for (var key in item) {
                        itemClone[key] = item[key];
                    }
                    result.push(itemClone);
                }
            }
            outgoinItems = result;
            $scope.addeditems = outgoinItems;
        }

        $scope.clearWaybill = function () {
            outgoinItems = [];
            $scope.addeditems = outgoinItems;
            $scope.company = '';
            $scope.numberWaybill = '';
            $scope.typeMove = '';
        }

        $scope.sendWaybill = function () {
            var dataWaybills = $scope.addeditems;

            //creating date on server and  parse string to Date
            var dateStringGet = $("#datestring").val();
            var companyGet = $scope.company;
            var numberWaybillGet = $scope.numberWaybill;
            var typeMove = $scope.typeMove;

            var checkError = '';

            if (dataWaybills.length == 0) {
                checkError += 'Не выбрано ни одного наименования! \n\r';
            }

            if (companyGet == null) {
                checkError += 'Не заполнен контрагент! \n\r';
            }

            if (numberWaybillGet == null) {
                checkError += 'Не заполнен номер накладной! \n\r';
            }

            if (typeMove == null) {
                checkError += 'Не заполнен тип перемещения! \n\r';
            }

            if (checkError.length > 0) {
                alert(checkError);
            } else {

                for (var i = 0; i < dataWaybills.length; i++) {
                    dataWaybills[i].dateString = dateStringGet;
                    dataWaybills[i].company = companyGet;
                    dataWaybills[i].numberWaybill = numberWaybillGet;
                    dataWaybills[i].typeWaybill = 'расходная';
                    dataWaybills[i].typeMove = typeMove;
                }

                var data = {};
                data.company = companyGet;
                data.dateString = dateStringGet;
                data.numberWaybill = numberWaybillGet;
                data.typeWaybill = 'расходная';
                data.typeMove = typeMove;
                data.waybills = dataWaybills;

                $http.post("waybill", data).success(function (data) {
                    if (data == 'ok') {
                        outgoinItems = [];
                        $scope.addeditems = outgoinItems;
                        $scope.company = '';
                        $scope.numberWaybill = '';
                    } else {
                        alert(data);
                    }
                }).error(function (res) {
                    alert("Ошибка при отправке запроса");
                });
            }
        }

    }]);


order.controller('Incoming', ['$scope', '$http',
    function ($scope, $http) {

        $scope.addeditems = incomimgItems;

        $scope.onKeyUp = function ($event, searchitem) {
            if (((searchitem != null) & (searchitem.length > 1)) & ($event.keyCode == 13)) {
                var url = 'item/' + searchitem;
                $http.get(url).success(function (data, status, headers, config) {
                    $scope.searchedItems = data;
                })
            }
        };

        $scope.addItem = function (number, frominput) {
            var item = $scope.searchedItems[number];
            var itemClone = {};
            for (var key in item) {
                itemClone[key] = item[key];
            }
            itemClone.toOperation = frominput;
            var total = incomimgItems.push(itemClone);
            $scope.addeditems = incomimgItems;
            //$scope.searchedItems = [];
        }

        $scope.deleteItemAddedItems = function (index) {

            //deleting from revers array
            index = $scope.addeditems.length - index -1;

            var result = [];
            for (var i = 0; i < $scope.addeditems.length; i++) {
                if (i != index) {
                    var item = $scope.addeditems [i];
                    var itemClone = {};
                    for (var key in item) {
                        itemClone[key] = item[key];
                    }
                    result.push(itemClone);
                }
            }
            incomimgItems = result;
            $scope.addeditems = incomimgItems;
        }


        $scope.clearWaybill = function () {
            incomimgItems = [];
            $scope.addeditems = incomimgItems;
            $scope.company = '';
            $scope.numberWaybill = '';
        }

        $scope.sendWaybill = function () {
            var dataWaybills = $scope.addeditems;

            //creating date on server, parse date
            var dateStringGet = $("#datestring").val();
            var companyGet = $scope.company;
            var numberWaybillGet = $scope.numberWaybill;

            var checkError = '';

            if (dataWaybills.length == 0) {
                checkError += 'Не выбрано ни одного наименования! \n\r';
            }

            if (companyGet == null) {
                checkError += 'Не заполнен контрагент! \n\r';
            }

            if (numberWaybillGet == null) {
                checkError += 'Не заполнен номер накладной! \n\r';
            }

            if (checkError.length > 0) {
                alert(checkError);
            } else {

                for (var i = 0; i < dataWaybills.length; i++) {
                    dataWaybills[i].dateString = dateStringGet;
                    dataWaybills[i].company = companyGet;
                    dataWaybills[i].numberWaybill = numberWaybillGet;
                    dataWaybills[i].typeWaybill = 'приходная';
                }

                var data = {};
                data.company = companyGet;
                data.dateString = dateStringGet;
                data.numberWaybill = numberWaybillGet;
                data.typeWaybill = 'приходная';

                data.waybills = dataWaybills;


                $http.post("waybill", data).success(function (data) {
                    if (data == 'ok') {
                        incomimgItems = [];
                        $scope.addeditems = incomimgItems;
                        $scope.company = '';
                        $scope.numberWaybill = '';
                    } else {
                        alert(data);
                    }
                }).error(function (res) {
                    alert("Ошибка при отправке запроса");
                });
            }
        }
    }]);

order.controller('AddItemCTRL', ['$scope', '$http',
    function ($scope, $http) {

        $scope.measureVal = [
            {id: "шт.", value: "шт."},
            {id: "л.", value: "л."},
            {id: "уп.", value: "уп."},
            {id: "к-т.", value: "к-т."},
            {id: "кг.", value: "кг."}
        ];

        $scope.submit = function () {
            var data = $scope.addItem;
            //$http({method: 'POST', url: 'add', data: data, headers: {'Content-Type': 'application/x-www-form-urlencoded'}})
            $http.post("add", data)
                .success(function (data) {
                    $scope.addedItem = $scope.addItem.name;
                    $scope.addItem = {};
                }).error(function (res) {
                    alert("Ошибка при отправке запроса");
                });
        }
    }]);

order.controller('Statistic', ['$scope', '$http',
    function ($scope, $http) {

        $scope.onKeyUp = function ($event, searchitem) {
            if (((searchitem != null) & (searchitem.length > 1)) & ($event.keyCode == 13)) {
                var url = 'item/' + searchitem;
                $http.get(url).success(function (data, status, headers, config) {
                    $scope.searchedItems = data;
                })
            }
        };

        $scope.showItem = function (idItem) {
            var url = 'waybilliditem/' + idItem;
            $http.get(url).success(function (data, status, headers, config) {
                $scope.waybills = data;
            })
        }

        $scope.showByWaybill = function (idWaybill) {
            var url = 'waybillbyid/' + idWaybill;
            $http.get(url).success(function (data, status, headers, config) {
                $scope.waybills = data;
            })
        }
    }]);

order.controller('Statistic2', ['$scope', '$http',
    function ($scope, $http) {

        $scope.loadStatistic = function () {
            var data = {};
            //date creating on server, parse date
            data.from = $("#fromDate").val();
            data.to = $("#toDate").val();
            $http.post('statistic', data).success(function (data, status, headers, config) {
                $scope.statistic = data;
            })
        }

        $scope.onKeyUp = function ($event, searchitem) {
            if ((searchitem != null) & (searchitem.length > 0)) {
                var url = 'item/' + searchitem;
                $http.get(url).success(function (data, status, headers, config) {
                    $scope.searchedItems = data;
                })
            }
        };

        $scope.showItem = function (idItem) {
            var url = 'waybilliditem/' + idItem;
            $http.get(url).success(function (data, status, headers, config) {
                $scope.waybills = data;
            })
        }

        $scope.showByWaybill = function (idWaybill) {
            var url = 'waybillbyid/' + idWaybill;
            $http.get(url).success(function (data, status, headers, config) {
                $scope.waybills = data;
            })
        }
    }]);


order.controller('Registration', ['$scope', '$http',
    function ($scope, $http) {

        $scope.registration = function () {
            var data = $scope.user;
            $http({method: 'POST', url: 'registration', data: data, headers: {'Content-Type': 'application/x-www-form-urlencoded'}})
                .success(function (data) {
                    $scope.user = {};
                    alert("Добавлено в базу " + data);
                }).error(function (res) {
                    alert("Ошибка при отправке запроса");
                });
        }

        $scope.changePassword = function () {
            var data = $scope.userPassword;
            $http({method: 'POST', url: 'changepassword', data: data, headers: {'Content-Type': 'application/x-www-form-urlencoded'}})
                .success(function (data) {
                    $scope.userPassword = {};

                    if (data == 'notmatch') {
                        alert("Пароли не совпадают");
                    }
                    if (data == 'ok') {
                        alert("Пароль успешно изменен! ");
                    }
                }).error(function (res) {
                    alert("Ошибка при отправке запроса");
                });
        }

        $scope.addItems = function () {
            var data = $scope.item;
            $http({method: 'POST', url: 'additems', data: data, headers: {'Content-Type': 'multipart/form-data'}})
                .success(function (data) {
                    $scope.item = {};
                    if (data == 'ok') {
                        alert("Данные добавлены! ");
                    }
                }).error(function (res) {
                    alert("Ошибка при отправке запроса");
                });
        }
    }]);


order.controller('Act', ['$scope', '$http',
    function ($scope, $http) {

        $scope.onKeyUp = function ($event, searchitem) {
            if (((searchitem != null) & (searchitem.length > 1)) & ($event.keyCode == 13)) {
            //if ((searchitem != null) & (searchitem.length > 0)) {
                var url = 'item/' + searchitem;
                $http.get(url).success(function (data, status, headers, config) {
                    $scope.searchedItems = data;
                })
            }
        };

        $scope.addeditems = act;

        $scope.addItem = function (number, frominput) {
            var item = $scope.searchedItems[number];
            var itemClone = {};
            for (var key in item) {
                itemClone[key] = item[key];
            }
            itemClone.toOperation = frominput;
            var total = act.push(itemClone);
            $scope.addeditems = act;
        }

        $scope.deleteItemAddedItems = function (index) {

            //deleting from revers array
            index = $scope.addeditems.length - index -1;

            var result = [];
            for (var i = 0; i < $scope.addeditems.length; i++) {
                if (i != index) {
                    var item = $scope.addeditems [i];
                    var itemClone = {};
                    for (var key in item) {
                        itemClone[key] = item[key];
                    }
                    result.push(itemClone);
                }
            }
            act = result;
            $scope.addeditems = act;
        }


        $scope.clearWaybill = function () {
            act = [];
            $scope.addeditems = act;
            $scope.company = '';
            $scope.numberWaybill = '';
        }

        $scope.sendWaybill = function () {
            var dataWaybills = $scope.addeditems;
            var dateStringGet = $("#datestring").val();
            var companyGet = $scope.company;
            var numberWaybillGet = $scope.numberWaybill;

            var checkError = '';

            if (dataWaybills.length == 0) {
                checkError += 'Не выбрано ни одного наименования! \n\r';
            }

            if (companyGet == null) {
                checkError += 'Не заполнен контрагент! \n\r';
            }

            if (numberWaybillGet == null) {
                checkError += 'Не заполнен номер Акта! \n\r';
            }

            if (checkError.length > 0) {
                alert(checkError);
            } else {

                for (var i = 0; i < dataWaybills.length; i++) {
                    dataWaybills[i].dateString = dateStringGet;
                    dataWaybills[i].company = companyGet;
                    dataWaybills[i].numberWaybill = numberWaybillGet;
                    dataWaybills[i].typeWaybill = 'акт';
                }

                var data = {};
                data.company = companyGet;
                data.dateString = dateStringGet;
                data.numberWaybill = numberWaybillGet;
                data.typeWaybill = 'акт';

                data.waybills = dataWaybills;


                $http.post("waybill", data).success(function (data) {
                    if (data == 'ok') {
                        act = [];
                        $scope.addeditems = act;
                        $scope.company = '';
                        $scope.numberWaybill = '';
                    } else {
                        alert(data);
                    }
                }).error(function (res) {
                    alert("Ошибка при отправке запроса");
                });
            }
        }
    }]);*/
