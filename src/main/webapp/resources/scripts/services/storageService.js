angular.module('app').service('$storage', $storage);

$storage.$inject = ['$http'];
function $storage(http) {
    ///////////////////////////////////////////////////
    // MANAGER
    ///////////////////////////////////////////////////
    this.manager = {};

    // информация о менджере
    this.manager.getInfo = function (callback) {
//        http.get('/getuser').success(callback);
        var manager = {
            name: 'Игорь Петрович',
            recipe: true,
            density: true
        };

        callback(manager);
    };

    // получить все заказы
    this.manager.getOrders = function (callback) {
        http.get('manager/getallcommonorders').success(function (data) {
            var orders = [], copy;

            angular.forEach(data, function (order) {
                if (order.hasDeferred) {
                    copy = angular.copy(order);
                    copy.deferred = !copy.deferred;
                    orders.push(copy);
                }

                orders.push(order);
            });

            //console.log('данные с сервера по всем заказам');
            //console.log(data);
            //console.log('данные с клонированными объектами');
            //console.log(orders);

            callback(orders);
        });
    };

    // получить компоненты заказы
    this.manager.getComponentsByOrderId = function (orderId, deferred, callback) {
        http.get('manager/getitemscommonordersbycommonorderid/' + orderId).success(function (data) {
            var components = [], laboratory = [], warehouse = [], reasonPosition = [];

            angular.forEach(data, function (component) {
                if (component.deferred == deferred) {
                    components.push(component);

                    if (component.category == 'laboratory') {
                        laboratory.push(component);
                    }

                    if (component.category == 'warehouse') {
                        warehouse.push(component);
                    }

                    if (component.reason != null) {
                        reasonPosition.push(component);
                    }
                }
            });

            components.laboratory = laboratory;
            components.warehouse = warehouse;
            components.reasonPosition = reasonPosition;

            //console.log('данные с сервера текущему заказу');
            //console.log(data);
            //console.log('данные с фильтром отложенности');
            //console.log(components);
            //console.log('данные с фильтром отложенности по лаборатории');
            //console.log(components.laboratory);
            //console.log('данные с фильтром отложенности по складу');
            //console.log(components.warehouse);

            callback(components);
        });
    };

    // обновить информацию о компоненте
    this.manager.updateComponent = function (component) {
        http.post('manager/updateitemcommonorder', component);
    };

    // обновить информацию о заказе
    // TODO на сервере maybe изменить deferred у всех компонентов
    this.manager.updateOrder = function (order) {
        http.post('manager/returnfull', order);
    };


    ///////////////////////////////////////////////////
    // STOREKEEPER
    ///////////////////////////////////////////////////
    this.storekeeper = {};
    // получить информацию
    this.storekeeper.getInfo = function (callback) {
        //        http.get('/getuser').success(callback);
        var storekeeper = {
            name: 'Игорь Петрович'
        };

        callback(storekeeper);
    };
    // получить все заказы
    this.storekeeper.getOrders = function (callback) {
        http.get('storekeeper/getallcommonorders').success(callback);
    };
    // получить компоненты заказа
    this.storekeeper.getComponentsByOrderId = function (orderId, callback) {
        http.get('storekeeper/getitemscommonordersbycommonorderid/' + orderId).success(function (data) {
            var components = [], laboratory = [], warehouse = [], readyCount = 0;

            angular.forEach(data, function (component) {
                if (component.deferred == false) {
                    components.push(component);

                    if (component.category == 'laboratory') {
                        laboratory.push(component);
                    }

                    if (component.category == 'warehouse') {
                        warehouse.push(component);
                    }

                    if (component.ready) {
                        readyCount++;
                    }
                }
            });

            components.laboratory = laboratory;
            components.warehouse = warehouse;

            callback(components, readyCount);
        });
    };
    // обновить позицию заказа
    this.storekeeper.updateComponent = function (component) {
        http.post('storekeeper/updateitemcommonorder', component);
//        console.log(component);
    };
    // обновить заказ
    this.storekeeper.updateOrder = function (order) {
        http.post('storekeeper/updatecommonorder', order);
//        console.log(order);
    };


    ///////////////////////////////////////////////////
    // ASSISTANT
    ///////////////////////////////////////////////////
    this.assistant = {};
    // получить информацию
    this.assistant.getInfo = function (callback) {
//        http.get('/getuser').success(callback);
        var assistant = {
            name: 'Игорь Петрович'
        };

        callback(assistant);
    };
    // получить все заказы
    this.assistant.getOrders = function (callback) {
        http.get('assistant/getallcommonorders').success(callback);
    };
    // получить компоненты заказа
    this.assistant.getComponentsByOrderId = function (orderId, callback) {
        http.get('assistant/getitemscommonordersbycommonorderid/' + orderId).success(function (data) {
            var components = [], laboratory = [], warehouse = [], readyCount = 0;

            angular.forEach(data, function (component) {
                if (component.deferred == false) {
                    components.push(component);

                    if (component.category == 'laboratory') {
                        laboratory.push(component);
                    }

                    if (component.category == 'warehouse') {
                        warehouse.push(component);
                    }

                    if (component.ready) {
                        readyCount++;
                    }
                }
            });

            components.laboratory = laboratory;
            //components.warehouse = warehouse;
            components.warehouse = {};

            callback(components, readyCount);
        });
    };
    // обновить позицию заказа
    this.assistant.updateComponent = function (component) {
        http.post('assistant/updateitemcommonorder', component);
//        console.log(component);
    };
    // обновить заказ
    this.assistant.updateOrder = function (order) {
        http.post('assistant/updatecommonorder', order);
        console.log(order.status);
    };





    /*
     ADMIN
     */
    this.createUser = function (user) {
        http.post('admin/createuser', user).error(function () {
            console.log('ошибка добавления ногово пользователя');
        });
    };


    this.getUsers = function (callback) {
        http.get('admin/getusers').success(callback);
    };

    this.getRoles = function (callback) {
        http.get('admin/getgroups').success(callback);
    };

    this.getSetting = function (callback) {
        http.get('admin/getsettings').success(callback);
    };

    //admin/updatesettings

    this.setUserRole = function (user) {
        http.post("admin/updateuserroles", user).success(function (data) {
            console.log("data=" + data);
        }).error(function (data) {
            console.log("data error=" + data);
        });
    };

    this.updateUser = function (user) {
        http.post('admin/updateuser', user).error(function () {
            console.log('ошибка редактирования ногово пользователя');
        });
    };

    //updategrouprole

    this.setUserGrant = function (roleGrant) {
        http.post('admin/updategrouprole', roleGrant).success(function (data) {
            console.log(data);
        });
    };

    this.setSetting = function (setting) {
        console.log(setting);
    };

    this.createUser = function (user) {

        http.post("admin/createuser", user).success(function (data) {
            console.log("createuser=" + data);
        }).error(function (data) {
            console.log("createuser error=" + data);
        });
    };


    /*
     MANAGER
     */
    /*this.getOrders = function (callback) {
     http.get('manager/getallcommonorders').success(callback);
     };



     this.getManagerOrderById = function (orderId, callback) {
     http.get('manager/getitemscommonordersbycommonorderid/' + orderId).success(callback);
     };

     this.setDifferedItem = function (item, callback) {
     item.info = null;
     delete item.info;
     http.post('manager/updatecommonorder', item).success(callback);
     };

     this.setDifferedItemOne = function (item) {
     http.post('manager/updateitemcommonorder', item);
     };


     this.setOrderPriority = function (order) {
     console.log(order);
     };*/

    /*
     STOREKEEPER
     */
    this.getStorekeeperInfo = function (callback) {
        http.get('getuser').success(callback);
    };

    //this.getStorekeeperOrders = function (callback) {
    //    http.get('storekeeper/getallcommonorders').success(callback);
    //};
    //this.getStorekeeperOrderById = function (orderId, callback) {
    //    http.get('storekeeper/getItemsCommonOrdersByCommonOrderId/' + orderId).success(callback);
    //};

    this.setItemStatus = function (item) {
        console.log(item);
    };

    this.setOrderStatus = function (orderId) {
        console.log(orderId);
    };

    /*
     ASSISTANT
     */
    this.getAssistantInfo = function (callback) {
        http.get('getuser').success(callback);
    };

    this.getAssistantOrders = function (callback) {
        http.get('assistant/getallcommonorders').success(callback);
    };
    this.getAssistantOrderById = function (orderId, callback) {
        http.get('assistant/getitemscommonordersbycommonorderid/' + orderId).success(callback);
    };

    this.setItemStatus = function (item) {
        // TODO вот пункт заказа и надо ссылку для смены его статуса ичане патинабизон
        console.log(item);
    };

    // TODO вот сюда надо ссылку переходя по которой заказ будет выдан
    this.setOrderStatus = function (orderId) {
        console.log(orderId);
    };

    this.getOrdersByItem = function (itemId, callback) {
        http.post('_data/assistant/' + itemId + '.json').success(callback);
    };


    /*
     DENSITY
     */
    this.getDensity = function (exp1, callback) {
        http.get('item/getitemsbyexpression/' + exp1).success(callback);
    };

    this.setDensity = function (density) {
        http.post('item/updateitem', density).success(function () {
                console.log("ok");
            }
        );

        //console.log(density);
    };

    this.newDensity = function (density) {

        http.post('item/additem', density).success(function () {
                console.log("ok");
            }
        );
    };
}