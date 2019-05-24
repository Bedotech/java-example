package com.bedo.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {
    @RequestMapping("/square")
    public int square(@RequestParam(value = "x", defaultValue = "2") int x) {
        return x*x;
    }

    @RequestMapping("/sum")
    public int square(
        @RequestParam(value = "x", defaultValue = "2") int x,
        @RequestParam(value = "y", defaultValue = "2") int y
    ) {
        return x+y;
    }
}
