package com.bonvio.controller;

import com.bonvio.model.formula.Component;
import com.bonvio.model.formula.Recipe;
import com.bonvio.model.order.CommonOrder;
import com.bonvio.service.formula.FormulaService;
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
    FormulaService formulaService;

    @RequestMapping(value = "/getallrecipes", method = RequestMethod.GET)
    @ResponseBody
    public List<Recipe> getAllRecipes() {
        return formulaService.getAllRecipes();
    }

    @RequestMapping(value = "/updaterecipe", method = RequestMethod.GET)
    @ResponseBody
    public Recipe updateRecipe(@RequestBody Recipe recipe) {
        formulaService.updateRecipe(recipe);
        return recipe;
    }

    @RequestMapping(value = "/removerecipe/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String removeRecipe(@PathVariable("id") int id) {
        formulaService.removeRecipe(id);
        return "removed";
    }

    @RequestMapping(value = "/createrecipe", method = RequestMethod.GET)
    @ResponseBody
    public Recipe removeRecipe(@RequestBody Recipe recipe) {
        formulaService.createRecipe(recipe);
        return recipe;
    }

    @RequestMapping(value = "/addcomponent/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Recipe addComponent(@RequestBody Component component) {
        //formulaService.createRecipe(recipe);
        return null;
    }




}
