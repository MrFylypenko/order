angular.module('app').controller('recipeController', recipeController);

recipeController.$inject = ['$scope', '$recipe', '$interval'];
function recipeController(scope, recipe, interval) {
    scope.exp1 = 'a';

    /*interval(function () {
        scope.getRecipes = function (exp1) {
            recipe.getRecipes(exp1, function (data) {
                scope.recipes = data;
            });
        };
    }, 5000); // 10 sec*/

    scope.selectedRecipe = {};
    scope.setSelectedRecipe = function (recipe) {
        if (recipe == null) {
            scope.selectedRecipe = {};
            scope.currentAction = 'create';
        } else {
            scope.selectedRecipe = recipe;
            scope.currentAction = 'update';
        }
    };

    scope.component = {};
    /* добавление компонента */
    scope.addComponent = function (newComponent) {
        if (scope.selectedRecipe.components == undefined) {
            scope.selectedRecipe.components = [];
        }

        if (newComponent.name != undefined) {
            newComponent = {};
            newComponent.measure = scope.selectedItem.measure;
            newComponent.quantity = scope.component.quantity;
            newComponent.item = scope.selectedItem;
            scope.selectedRecipe.components.push(newComponent);
            scope.selectedItem = {};
        }
    };
    /* удаление компонента */
    scope.removeComponentIn = function (index) {
        scope.selectedRecipe.components.splice(index, 1);
    };

    /* добавление рецепта */
    scope.addRecipe = function (selectedRecipe) {
        if (scope.selectedRecipe.name != undefined) {
            scope.recipes.push(scope.selectedRecipe);
            recipe.addRecipe(scope.selectedRecipe);
            scope.selectedRecipe = {};
        }
    };

    /* редактирование рецепта */
    scope.updateRecipe = function () {
        recipe.updateRecipe(scope.selectedRecipe.id, scope.selectedRecipe);
    };

    /* удаление рецепта */
    scope.removeRecipe = function (index, recipeId) {
        scope.recipes.splice(index, 1);
        recipe.removeRecipe(recipeId);
    };

    scope.removeComponent = function (index, componentId, recipeIndex) {
        scope.recipes[recipeIndex].components.splice(index, 1);
        recipe.removeComponent(componentId);
    };

    /* подтвержнение */
    scope.confirm = '';
    scope.setConfirm = function (fn) {
        scope.confirm = 'scope.' + fn;
    };
    scope.runConfirm = function () {
        eval(scope.confirm);
    };

    scope.setCurrentComponent = function (component) {
        console.log(component);
    };


    scope.components = [];
    scope.getComponents = function (exp) {
        recipe.getComponents(exp, function (data) {
            scope.components = data;
        });
    };

    scope.recipes = [];
    scope.getRecipes = function (exp1) {
        recipe.getRecipes(exp1, function (data) {
            scope.recipes = data;
        });
    };
}