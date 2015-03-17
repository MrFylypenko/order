angular.module('app').service('$storage', $storage);

$storage.$inject = ['$http'];
function $storage(http) {
    /*
     ADMIN
     */
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
        console.log(user);
    };

    //updategrouprole

    this.setUserGrant = function (roleGrant) {
        console.log(roleGrant);
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
    this.getOrders = function (callback) {
        http.get('manager/getallcommonorders').success(callback);
    };

    this.getManagerInfo = function (callback) {
        http.post('_data/manager/manager.json').success(callback);
    };

    this.getManagerOrderById = function (orderId, callback) {
        http.get('manager/getitemscommonordersbycommonorderid/' + orderId).success(callback);
    };

    this.setDifferedItem = function (item, callback) {

        http.post('manager/updateitemcommonorder', item).success(callback);

        console.log(item);
    };


    this.setOrderPriority = function (order) {
        console.log(order);
    };

    /*
     STOREKEEPER
     */
    this.getStorekeeperInfo = function (callback) {
        http.get('getuser').success(callback);
    };

    this.getStorekeeperOrders = function (callback) {
        http.get('storekeeper/getallcommonorders').success(callback);
    };
    this.getStorekeeperOrderById = function (orderId, callback) {
        http.get('storekeeper/getItemsCommonOrdersByCommonOrderId/' + orderId).success(callback);
    };

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
        console.log(item);
    };

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
        http.get('item/getallitems/' + exp1).success(callback);
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