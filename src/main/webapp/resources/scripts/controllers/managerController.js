angular.module('app').controller('managerController', managerController);

managerController.$inject = ['$scope', '$storage', '$interval'];
function managerController(scope, storage, interval) {

    scope.setSelectedOrder = function (index, order) {
        scope.selectedOrder = order;
        scope.selectedIndex = index;
        scope.selectedOrderBackup = angular.copy(order);
    };

    scope.updatePriority = function () {
        storage.setOrderPriority(scope.selectedOrder);
    };
    scope.cancelPriority = function () {
        storage.getOrders(function (data) {
            scope.orders = data;
        });
    };

    scope.orderByField = 'priority';
    scope.reverseSort = false;

    /*interval(function () {
        storage.getOrders(function (data) {
            scope.orders = data;
        });
    }, 10000); // 10 sec*/

    scope.manager = {};
    scope.getManagerInfo = storage.getManagerInfo(function (data) {
        scope.manager = data;
    });

    scope.orders = [];
    scope.quantityDefferedOrders = 0;

    scope.getOrders = storage.getOrders(function (data) {
        angular.forEach(data, function(order) {
            scope.orders.push(order);
            if (order.deferred) {
                scope.quantityDefferedOrders++;
            }
            //console.log(scope.orders);
        });
        scope.getOrderById(0, scope.orders[0].id);
    });

    scope.order = {};
    scope.getOrderById = function (index, orderId) {
        scope.order = scope.orders[index];
        storage.getManagerOrderById(orderId, function (data) {
            console.log(data);
            scope.order.info = data;
        });
    };

    scope.selectedItem = {};
    scope.setSelectedItem = function (item, index) {
        scope.selectedItem = item;
        scope.selectedItem.deferred = true;
    };

    scope.setDifferedItem = function () {
        scope.order.info.splice(scope.selectedItem.index, 1);
        delete scope.selectedItem.index;
        storage.setDifferedItem(scope.selectedItem, function () {

            // БЫДЛОКОД НАЧАЛО
            storage.getOrders(function (data) {
                angular.forEach(data, function(order) {
                    scope.orders.push(order);
                    if (order.deffered) {
                        scope.quantityDefferedOrders++;
                    }
                    console.log(scope.orders);
                });
                scope.getOrderById(0, scope.orders[0].id);
            });
            // БЫДЛОКОД КОНЕЦ
        });
    };

    scope.createOrder = function () {
        console.log('БАНАНА ИЩЕЕЕЕЕ');
    };

    scope.setDeffered = function (status) {
        scope.order.deferred = status;
        storage.setDifferedItem(scope.order);
    };

    scope.componentStatus = function (status) {
        console.log(status);
    };
}