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
                "title": "Банана",
                "quantity": 10,
                "recipeId": 1
            },
            {
                "id": 3,
                "title": "Яблоко",
                "quantity": 100,
                "recipeId": 4
            }
        ];
        scope.addComponent = function () {
            console.log('121');
        };


        scope.recipes1 = [
            {
                "title": "Банана-пирог",
                "components": [
                    {
                        "title": "Банана1"
                    },
                    {
                        "title": "Банана2"
                    }
                ]
            },
            {
                "title": "Банана-пирог2",
                "components": [
                    {
                        "title": "Банана21"
                    },
                    {
                        "title": "Банана22"
                    }
                ]
            }
        ];

        //storage.getRecipes(function (data) {
        //    scope.recipes = data;
        //});
    };
}