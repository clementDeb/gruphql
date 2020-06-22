package com.gowyn.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/gowyn")
public class Controller {

    //use AOP to validate the request before going into the getMethod

    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, @RequestBody String request) {
        return "gowyn request " + request;
    }


}
