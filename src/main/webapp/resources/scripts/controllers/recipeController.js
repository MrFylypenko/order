angular.module('app').controller('recipeController', recipeController);

recipeController.$inject = ['$scope', '$recipe', '$interval'];
function recipeController(scope, recipe, interval) {
    var $scope = scope;

    $scope.items = [
        { id: 1, name: 'foo' },
        { id: 2, name: 'bar' },
        { id: 3, name: 'blah' }
    ];


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
    scope.addComponent = function (newComponent) {
        if (scope.selectedRecipe.components == undefined) {
            scope.selectedRecipe.components = [];
        }

        if (newComponent.name != undefined) {
            newComponent = {};
            newComponent.measure = scope.selectedItem.measure;
            newComponent.quantity = scope.selectedItem.quantity;
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

    scope.setCurrentComponent = function (component) {
        console.log('BANANA');
        console.log(component);
    };


    scope.components = [];
    scope.getComponents = function (exp) {
        console.log('123');
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