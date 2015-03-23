angular.module('app').controller('managerController', managerController);

managerController.$inject = ['$scope', '$storage', '$interval'];
function managerController(scope, storage, interval) {
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

    // информация о менеджере
    scope.getInfo = function () {
        storage.manager.getInfo(function (data) {
            scope.manager = data;
        });
    };

    // все заказы
    scope.getOrders = function () {
        storage.manager.getOrders(function (data) {
            scope.orders = data;
            if (scope.order.id != undefined) {
                scope.getComponentsByOrder(scope.order);
            } else {
                scope.getComponentsByOrder(scope.orders[0]);
            }
        });
    };

    // текущий заказ
    scope.getComponentsByOrder = function (order) {
        scope.order = order;
        storage.manager.getComponentsByOrderId(order.id, order.deferred, function (data) {
            scope.order.components = data;
        });
    };

    // текущий компонент
    scope.selectedComponent = {};
    scope.setSelectedComponent = function (component) {
        scope.selectedComponent = component;
    };

    // изменить отложенность компонента
    scope.setComponentDeferred = function () {
        scope.selectedComponent.deferred = !scope.selectedComponent.deferred;
        storage.manager.updateComponent(scope.selectedComponent);
    };

    // изменить отложенность заказа
    scope.setOrderDeferred = function () {
        scope.getComponentsByOrder(scope.orders[0]);
//        scope.order.deferred = !scope.order.deferred;
//        console.log(scope.order);
        storage.manager.updateOrder(scope.order);
    };

    // изменить приоритет заказа
    scope.updatePriority = function(priority) {
        scope.order.priority = priority;
        storage.manager.updateOrder(scope.order);
    };

    // добавить комментарий к заказу
    scope.setOrderComment = function () {
        storage.manager.updateOrderComment(scope.order);
    };
}