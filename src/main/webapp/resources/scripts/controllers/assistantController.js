angular.module('app').controller('assistantController', assistantController);

assistantController.$inject = ['$scope', '$storage', '$interval'];
function assistantController(scope, storage, interval) {
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

    // информация о клабаротаристе
    scope.getInfo = function () {
        storage.assistant.getInfo(function (data) {
            scope.assistant = data;
        });
    };

    // все заказы
    scope.getOrders = function () {
        storage.assistant.getOrders(function (data) {
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
        storage.assistant.getComponentsByOrderId(order.id, function (data, readyCount) {
            scope.order.components = data;
            scope.readyComponentCount = readyCount;
        });
    };

    // выбрать компонент
    scope.selectedComponent = {};
    scope.setSelectedComponent = function (component) {
        scope.selectedComponent = component;
    };

    // изменить статус компонента
    scope.setComponentStatus = function (status, reason) {
        scope.selectedComponent.ready = status;
        scope.selectedComponent.reason = reason == '' ? null : reason;

        if (status) scope.readyComponentCount++;
        else if (status == undefined) scope.readyComponentCount--;

        scope.order.status = parseInt(scope.readyComponentCount / (scope.order.components.allCount) * 100);
        console.log(scope.order.components.allCount);

        storage.assistant.updateComponent(scope.selectedComponent);
        storage.assistant.updateOrder(scope.order);
    };
}