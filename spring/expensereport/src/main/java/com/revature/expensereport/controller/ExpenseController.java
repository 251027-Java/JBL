package com.revature.expensereport.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ExpenseController {

    @GetMapping("/test")
    Data test() {
        return new Data("this is a test", 0);
    }

    @GetMapping("/hello")
    String hello(@RequestParam String name, @RequestParam(defaultValue = "Hello") String greeting) {
        return String.format("%s %s!", greeting, name);
    }

    public record Data(String name, int id) {}
}
