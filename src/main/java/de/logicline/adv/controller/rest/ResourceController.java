package de.logicline.adv.controller.rest;

import de.logicline.adv.model.Country;
import de.logicline.adv.model.frontend.Customer;
import de.logicline.adv.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("adv-service/v1")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/countries")
    public List<Country> getCountries(){
        return resourceService.getCountryList();

    }
}
