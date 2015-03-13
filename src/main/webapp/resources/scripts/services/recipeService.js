angular.module('app')
    .service('$recipe', recipeService);

recipeService.$inject = ['$http'];
function recipeService(http) {
    this.getRecipes = function (callback) {

        http.get('item/getallitems').success(callback).error(function () {
            console.log('Ошибка обратобки запроса getRecipes');
        });
    };

    this.getComponents = function (exp, callback) {
        http.get('item/getitemsbyexpression/' + exp).success(callback).error(function () {
            console.log('Ошибка обратобки запроса getComponents');
        });
    };

    this.addRecipe = function (recipe) {
        http.post('item/additem', recipe).error(function () {
            console.log('Ошибка обратобки запроса addRecipe');
        });
    };

    this.removeRecipe = function (recipeId) {
        http.post('item/deleteitem/' + recipeId).error(function () {
            console.log('Ошибка обратобки запроса removeRecipe');
        });
    };

    this.removeComponent = function (componentId) {
        //console.log(componentId);
        http.post('item/removecomponent/' + componentId).error(function () {
            console.log('Ошибка обратобки запроса removeComponent');
        });
    };

    this.updateRecipe = function (recipeId, recipe) {
        //console.log(recipeId, recipe);
        http.post('item/updateitem' , recipe).error(function () {
            console.log('Ошибка обратобки запроса updateRecipe');
        });
    };
}