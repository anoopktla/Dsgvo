package de.logicline.adv.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    //to load the home page, this app contains web-content as well as RESTFUL endpoints

    @RequestMapping("/")
    public String home() {
        return "index";
    }
}
