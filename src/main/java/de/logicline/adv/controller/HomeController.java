package de.logicline.adv.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    //to load the home page, this app contains web-content as well as RESTFUL endpoints

    @RequestMapping("/home")
    public String home() {
        return "index";
    }
}
