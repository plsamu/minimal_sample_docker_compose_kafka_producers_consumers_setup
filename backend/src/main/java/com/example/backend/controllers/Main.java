package com.example.backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class Main {

    // @Autowired
    // DataRepos dataRepos;

    @GetMapping("")  // these two both are the same thing
    @RequestMapping(value = "", method = RequestMethod.GET)
    String getMain() {
        return "<p>ciau</p>";
    }
}
