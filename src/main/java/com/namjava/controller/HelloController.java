package com.namjava.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@Tag(name = "Test hello API", description = "Test hello api")
public class HelloController {

    @GetMapping("/hello")
    @Operation(summary = "Test hello api summary", description = "Test hello api description")
    public String sayHello(@RequestParam String name) {
        return "Hello" + name;
    }
}
