angular.module('app').controller('recipeController', recipeController);

recipeController.$inject = ['$scope', '$recipe', '$interval'];
function recipeController(scope, recipe, interval) {
    interval(function () {
        scope.getRecipes();
    }, 5000); // 10 sec

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
    scope.addComponent = function () {
        if (scope.selectedRecipe.components == undefined) {
            scope.selectedRecipe.components = [];
        }

        if (scope.component.name != undefined && scope.component.name != undefined) {
            scope.component.item = scope.component;
            scope.selectedRecipe.components.push(scope.component.item);
            scope.component = {};
        }
    };
    /* удаление компонента */
    scope.removeComponentIn = function (index) {
        scope.selectedRecipe.components.splice(index, 1);
    };

    /* добавление рецепта */
    scope.addRecipe = function () {
        if (scope.selectedRecipe.title != undefined) {
            scope.recipes.push(scope.selectedRecipe);
            recipe.addRecipe(scope.selectedRecipe);
            scope.selectedRecipe = {};
        }
    };

    /* редактирование рецепта */
    scope.updateRecipe = function () {
        console.log(scope.recipes);
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


    scope.components = [];
    scope.getComponents = function (exp) {
        console.log(exp);



        recipe.getComponents(exp, function (data) {
            scope.components = data;
        });
    };

    scope.recipes = [];
    scope.getRecipes = function () {

        recipe.getRecipes(function (data) {
            scope.recipes = data;
        });



    };
}