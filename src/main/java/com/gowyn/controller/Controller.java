package com.gowyn.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/gruphQl")
public class Controller {

    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, @RequestBody String request) {
        return "gruphQl request " + request;
    }


}
