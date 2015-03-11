angular.module('app')
    .service('$recipe', recipeService);

recipeService.$inject = ['$http'];
function recipeService(http) {
    this.getRecipes = function (callback) {
        //http.post('').success(callback).error(function () {
        //    console.log('Ошибка обратобки запроса getRecipes');
        //});
    };

    this.getComponents = function (exp, callback) {
        //http.post('' + exp).success(callback).error(function () {
        //    console.log('Ошибка обратобки запроса getComponents');
        //});
    };

    this.addRecipe = function (recipe) {
        console.log(recipe);
        //http.post('', recipe).error(function () {
        //    console.log('Ошибка обратобки запроса addRecipe');
        //});
    };

    this.removeRecipe = function (recipeId) {
        console.log(recipeId);
        //http.post('' + recipeId).error(function () {
        //    console.log('Ошибка обратобки запроса removeRecipe');
        //});
    };

    this.removeComponent = function (componentId) {
        console.log(componentId);
        //http.post('' + recipeId).error(function () {
        //    console.log('Ошибка обратобки запроса removeComponent');
        //});
    };

    this.updateRecipe = function (recipeId, recipe) {
        console.log(recipeId, recipe);
        //http.post('' + recipeId, recipe).error(function () {
        //    console.log('Ошибка обратобки запроса updateRecipe');
        //});
    };
}