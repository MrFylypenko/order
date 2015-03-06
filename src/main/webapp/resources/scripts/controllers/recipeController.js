angular.module('app').controller('recipeController', recipeController);

recipeController.$inject = ['$scope', '$storage'];
function recipeController(scope, storage) {
    scope.recipes = [];
    scope.getRecipes = function () {
        scope.recipes = [
            {
                "id": 1,
                "title": "Банана-пирог"
            },
            {
                "id": 2,
                "title": "Банана",
                "quantity": 10,
                "recipeId": 1
            },
            {
                "id": 3,
                "title": "Яблоко",
                "quantity": 20,
                "recipeId": 1
            },
            {
                "id": 4,
                "title": "Банана-пирог большой"
            },
            {
                "id": 2,
                "title": "Редкий банана",
                "quantity": 50,
                "recipeId": 4
            },
            {
                "id": 3,
                "title": "Яблоко",
                "quantity": 100,
                "recipeId": 4
            }
        ];
        //storage.getRecipes(function (data) {
        //    scope.recipes = data;
        //});
    };
}