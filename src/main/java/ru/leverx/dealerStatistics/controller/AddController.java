package ru.leverx.dealerStatistics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ind")
public class AddController{

    @GetMapping("/hello")
    public String helloIndex(){
        System.out.println("hello");
        return "hello";
    }

    @GetMapping("/bye")
    public String goodbyePage(){
        System.out.println("bye");
        return "hello";
    }
}