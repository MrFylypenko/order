package com.bonvio.controller;

import com.bonvio.model.item.Item;
import com.bonvio.service.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Ivan on 10.03.2015.
 */
@Controller
@RequestMapping (value = "recipe")
public class RecipeController {

    @Autowired
    ItemService itemService;


    @RequestMapping(value = "/createrecipe", method = RequestMethod.POST)
    @ResponseBody
    public Item addRecipe(@RequestBody Item recipe) {
        itemService.createRecipe(recipe);
        return recipe;
    }

    @RequestMapping(value = "/getallrecipes", method = RequestMethod.GET)
    @ResponseBody
    public List<Item> getAllRecipes() {
        return itemService.getAllRecipes();
    }

    @RequestMapping(value = "/getrecipesbyexpression/{expression}", method = RequestMethod.GET)
    @ResponseBody
    public List<Item> getRecipesByExpression(@PathVariable("expression") String expression){
        return itemService.getRecipesByExpression(expression);
    }

    @RequestMapping(value = "/updateRecipe", method = RequestMethod.POST)
    @ResponseBody
    public Item updateRecipe(@RequestBody Item recipe) {
        itemService.updateRecipe(recipe);
        return recipe;
    }

    @RequestMapping(value = "/removerecipe/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String removeRecipe(@PathVariable("id") int id) {
        itemService.removeRecipe(id);
        return "removed";
    }

    @RequestMapping(value = "/getitemsbyexpression/{expression}", method = RequestMethod.GET)
    @ResponseBody
    public List<Item>getItemsByExpression(@PathVariable("expression") String expression){
        return itemService.getItemsByExpression(expression);
    }

}
