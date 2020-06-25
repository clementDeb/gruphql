package com.gowyn.service;

import com.gowyn.repo.RequestRepo;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RequestAspect {

    RequestValidator validator;

    RequestParser parser;

    RequestRepo repo;

    public RequestAspect(RequestValidator validator,RequestParser parser, RequestRepo repo) {
        this.validator = validator;
        this.parser = parser;
        this.repo = repo;
    }


    @Before("@annotation(GowynRequest) && args(id, request)")
    public String processRequest(long id, String request) {

        //parse the request

        //validate the request

        //retrieve data from Db

        //build & return the response

        return "";
    }
}
