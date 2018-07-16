package de.logicline.adv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {
    //to load the home page, this app contains web-content as well as RESTFUL endpoints

    @RequestMapping("/create-adv")
    public String home() {
        return "index";
    }
}
