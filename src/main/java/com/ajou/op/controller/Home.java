package com.ajou.op.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Home {

    @GetMapping("/")
    public String HomeController() {

        return "index.html";
    }
    @GetMapping("/login")
    public String home2() {

        return "index.html";
    }
    @GetMapping("/admin")
    public String home3() {

        return "index.html";
    }


}
