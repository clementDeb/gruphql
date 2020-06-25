package com.gowyn.controller;

import com.gowyn.service.GowynRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/gowyn")
public class Controller {

    //use AOP to validate the request before going into the getMethod

    @GetMapping("/{id}")
    @GowynRequest
    public String getById(@PathVariable long id, @RequestBody String request) {
        return "gowyn request " + request;
    }


}
