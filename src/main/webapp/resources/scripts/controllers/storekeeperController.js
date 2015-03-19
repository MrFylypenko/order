angular.module('app').controller('storekeeperController', storekeeperController);

storekeeperController.$inject = ['$scope', '$storage', '$interval'];
function storekeeperController(scope, storage, interval) {
    scope.orderByField = 'priority';
    scope.reverseSort = false;
    scope.orders = [];
    scope.order = [];

    interval(function () {
        // обновляем все заказы
        scope.getOrders();
        // консолеложим месагу
        console.log('обновляшка');
    }, 2000);

    // информация о складовщике
    scope.getInfo = function () {
        storage.storekeeper.getInfo(function (data) {
            scope.storekeeper = data;
        });
    };

    // все заказы
    scope.getOrders = function () {
        storage.storekeeper.getOrders(function (data) {
            scope.orders = data;

            if (scope.order.id != undefined) {
                scope.getComponentsByOrder(scope.order);
            } else {
                scope.getComponentsByOrder(scope.orders[0]);
            }
        });
    };

    // компоненты текущего заказа
    scope.getComponentsByOrder = function (order) {
        scope.order = order;
        storage.storekeeper.getComponentsByOrderId(order.id, function (data, readyCount) {
            scope.order.components = data;
            scope.readyComponentCount = readyCount;
        });
    };

    // выбрать компонент
    scope.selectedComponent = {};
    scope.setSelectedComponent = function (component) {
        scope.selectedComponent = component;
    };

    // изменить статус готовности заказа
    scope.setOrderStatus = function (order) {
        order.closed = true;
        storage.storekeeper.updateOrder(order);
    };

    // изменить статус компонента
    scope.setComponentStatus = function (status, reason) {
        scope.selectedComponent.ready = status;
        scope.selectedComponent.reason = reason;

        if (status) scope.readyComponentCount++;
        else if (status == undefined) scope.readyComponentCount--;

        scope.order.status = parseInt(scope.readyComponentCount / scope.order.components.warehouse.length * 100);

        storage.storekeeper.updateComponent(scope.selectedComponent);
        storage.storekeeper.updateOrder(scope.order);
    };
}