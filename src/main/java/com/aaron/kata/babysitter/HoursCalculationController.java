package com.aaron.kata.babysitter;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HoursCalculationController {

    @RequestMapping("/")
    public String index() {
        return "Success!";
    }
}
