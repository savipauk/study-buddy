package com.study_buddy.study_buddy.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

// Annotate the class with @RestController
@RestController
@RequestMapping("/example")
public class ExampleController {

    // Define a GET endpoint for /example
    @GetMapping
    public Map<String, String> getExample() {
        return Map.of("hello", "world");
    }
}

