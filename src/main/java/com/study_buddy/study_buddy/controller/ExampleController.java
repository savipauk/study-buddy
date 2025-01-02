package com.study_buddy.study_buddy.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/example")
public class ExampleController {

    @GetMapping(value = "/greeting", produces = "application/json")
    public Map<String, String> getExample() {
        return Map.of("hello", "world");
    }

    @GetMapping(value = "/unauthorized", produces = "application/json")
    public Map<String, String> getUnauthorizedExample() {
        return Map.of("unauthorized", "world");
    }
}
