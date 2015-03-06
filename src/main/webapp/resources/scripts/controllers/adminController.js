angular.module("app").controller("adminController", adminController);

adminController.$inject = ["$scope", "$storage", "$http"];
function adminController(scope, storage, http) {
    /**
     * массив объектов пользователей
     * @type {Array}
     */
    scope.users = [];
    //console.log('1');

    /**
     * получение всех пользователей
     */
    scope.getUsers = storage.getUsers(function (data) {
        scope.users = data;
        console.log(scope.users.length);
        scope.setUser(0);

    });

    /**
     * выбранный пользователь
     * @type {Object}
     */
    scope.user = {};

    /**
     * установка выбранного пользователя
     */
    scope.setUser = function (userId) {
        scope.user = scope.users[userId];
    };

    /**
     * массив объктов ролей
     * @type {Array}
     */
    scope.roles = [];

    /**
     * получение всех ролей
     */
    scope.getRoles = storage.getRoles(function (data) {
        scope.roles = data;
    });

    /**
     * установка пользовательской роли и обновление грантов
     * @param roleName
     */
    scope.setUserRole = function (roleName) {
        // устанавливает пользователю новую роль
        scope.user.role = roleName;

        // устанавливаем пользователю права
        angular.forEach(scope.roles, function (role) {
            if (role.title == roleName) {
                scope.user.userRoles = angular.copy(role.roles);
            }
        });

        /*    http.post('admin/updateuserroles2', {"id":"10a"}).success(function (data) {
         console.log(data);
         }).error(function (data) {
         console.log(data + 'err');
         });*/

        storage.setUserRole(scope.user);
    };

    /**
     * установка пользовательских грантов
     */
    scope.setUserGrant = function (roleGrant) {
        storage.setUserGrant(roleGrant);
    };

    scope.setUserPath = function (event) {
        event.target.blur();
        if (event.keyCode == 13 || event.type == "blur") {
            storage.setUserRole(scope.user);
        }
    };

    /**
     * установка грантов для всех пользователей
     */
    scope.setUsersGrant = function () {
        var roleGrant = scope.currentRole;
        //if (confirm('Точно установить?')) {
            angular.forEach(scope.users, function (user) {
                angular.forEach(user.grants, function (grant) {
                    if (grant.title == roleGrant.title) {
                        grant.checked = roleGrant.checked;
                    }
                });
            });
            storage.setUserGrant(roleGrant);
        //}
    };

    scope.setting = {};
    scope.getSetting = storage.getSetting(function (data) {
        scope.setting = data;
        console.log(data);
    });

    scope.setSetting = function () {
        console.log(scope.setting);
    };

    scope.userNew = {};
    scope.createUser = function () {
        console.log(scope.userNew);
    };

    scope.currentRole = {};
    scope.setCurrentUsersGrant = function (role) {
        scope.currentRole = role;
    };

    scope.cancelUsersGrant = function () {
        scope.currentRole.checked = !scope.currentRole.checked;
    };


    scope.modal = {};
    scope.setModalParam = function (title, data, action) {
        scope.modal.title = title;
        scope.modal.action = action;
        scope.userNew = data != null ? data : {};
    };

    scope.updateUser = function () {
        var user = scope.userNew || scope.user;
        storage.updateUser(user);
    }

}