package com.gowyn.controller;

import com.gowyn.invariant.DatasInput;
import com.gowyn.invariant.DatasOutput;
import com.gowyn.service.GowynRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/gowyn")
public class Controller {

    //use AOP to validate the request before going into the getMethod

    @PostMapping("/{id}")
    @GowynRequest
    public DatasOutput getById(@PathVariable(required = false) long id, @RequestBody(required = false) DatasInput datas) {

        DatasOutput out = new DatasOutput();
        return out;
    }


}
