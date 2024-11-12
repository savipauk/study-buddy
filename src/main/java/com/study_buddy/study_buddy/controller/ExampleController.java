package com.study_buddy.study_buddy.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/example")
public class ExampleController {

    @GetMapping(produces = "application/json")
    public Map<String, String> getExample() {
        return Map.of("hello", "world");
    }
}
