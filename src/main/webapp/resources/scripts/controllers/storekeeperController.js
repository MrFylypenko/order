angular.module('app').controller('storekeeperController', storekeeperController);

storekeeperController.$inject = ['$scope', '$storage', '$interval'];
function storekeeperController(scope, storage, interval) {
    interval(function () {
        storage.getStorekeeperOrders(function (data) {
            scope.orders = data;
        });
    }, 10000);

    scope.storekeeper = {};
    scope.getStorekeeperInfo = storage.getStorekeeperInfo(function (data) {
        scope.storekeeper = data;
    });

    scope.orders = [];
    scope.getStorekeeperOrders = storage.getStorekeeperOrders(function (data) {
        scope.orders = data;
        scope.getStorekeeperOrderById(0, 1234);
    });

    scope.order = {};
    scope.readyItemCount = 0;
    scope.getStorekeeperOrderById = function (index, orderId) {
        scope.order = scope.orders[index];
        storage.getStorekeeperOrderById(orderId, function (data) {
            scope.order.info = [];
            angular.forEach(data, function(info) {
                scope.order.info.push(info);
                if (info.ready == true) {
                    scope.readyItemCount++;
                }
            });
        });
    };

    scope.closeOrder = function () {
        storage.setOrderStatus(scope.order.number);
    };

    scope.selectedItem = {};
    scope.setSelectedItem = function (item) {
        scope.selectedItem = item;
        scope.selectedItem.number = scope.order.number;
    };

    scope.reasonStatus = '';
    scope.setItemStatus = function(status, reason) {
        scope.selectedItem.ready = status;
        scope.selectedItem.reason = reason;

        if (status) {
            scope.readyItemCount++;
        } else if (status == undefined) {
            scope.readyItemCount--;
        }

        scope.order.status = parseInt(scope.readyItemCount / scope.order.info.length * 100);
        storage.setItemStatus(scope.selectedItem);
    };
}