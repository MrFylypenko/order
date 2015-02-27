angular.module('app').controller('assistantController', assistantController);

assistantController.$inject = ['$scope', '$storage', '$interval'];
function assistantController(scope, storage, interval) {
    interval(function () {
        storage.getAssistantOrders(function (data) {
            scope.orders = data;
        });
    }, 10000);

    scope.assistant = {};
    scope.getAssistantInfo = storage.getAssistantInfo(function (data) {
        scope.assistant = data;
    });

    scope.orders = [];
    scope.getAssistantOrders = storage.getAssistantOrders(function (data) {
        scope.orders = data;
        scope.getAssistantOrderById(0, 1234);
    });

    scope.order = {};
    scope.readyItemCount = 0;
    scope.noComponentCount = 0;
    scope.getAssistantOrderById = function (index, orderId) {
        scope.order = scope.orders[index];
        storage.getAssistantOrderById(orderId, function (data) {
            scope.order.info = [];
            angular.forEach(data, function(info) {
                if (info.ready == true) {
                    scope.readyItemCount++;
                }

                if (info.type != 'component') {
                    scope.noComponentCount++;
                    info.index = scope.noComponentCount;
                }

                scope.order.info.push(info);
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

        console.log(scope.noComponentCount);
        console.log(scope.readyItemCount);
        scope.order.status = parseInt(scope.readyItemCount / scope.noComponentCount * 100);
        storage.setItemStatus(scope.selectedItem);
    };

    scope.ordersByItem = [];
    scope.getOrdersByItem = function (item) {
        scope.selectedItem = item;
        storage.getOrdersByItem(item.code, function (data) {
            scope.ordersByItem = data;
        });
    };
}