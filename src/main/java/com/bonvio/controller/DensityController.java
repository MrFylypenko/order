package com.bonvio.controller;

import com.bonvio.model.density.Density;
import com.bonvio.model.order.CommonOrder;
import com.bonvio.service.density.DensityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Ivan on 27.02.2015.
 */
@Controller
@RequestMapping(value = "density")
public class DensityController {

    @Autowired
    DensityService densityService;


    @RequestMapping(value = "/getalldensiteis", method = RequestMethod.GET)
    @ResponseBody
    public List<Density> getAllDensities() {
        List<Density> res = densityService.getAllDensities();

        for (Density d:res){
            System.out.println(d.toString());
        }

        return densityService.getAllDensities();
    }

    @RequestMapping(value = "/adddensity", method = RequestMethod.POST)
    @ResponseBody
    public String addDensity(@RequestBody Density density) {
        densityService.addDensity(density);
        return "1";
    }

    @RequestMapping(value = "/updatedensity", method = RequestMethod.POST)
    @ResponseBody
    public String updateDensity(@RequestBody Density density) {
        densityService.updateDensity(density);
        return "1";
    }

   /* @RequestMapping(value = "/updatedensity", method = RequestMethod.POST)
    @ResponseBody
    public String getAllCommonOrders(@RequestBody Density density) {
        densityService.updateDensity(density);
        return "1";
    }*/

}


