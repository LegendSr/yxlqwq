package com.yxlqwq.www.yxlqwq.controller;

//import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


//@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String getting(@RequestParam(name="name",required=false,defaultValue="World") String name, Model mode){
        mode.addAttribute("name",name);
        return "greeting";
    }
    @GetMapping("/")
    public String index(){return "index";}
}
