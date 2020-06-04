package com.graphqllike.gruphql.parser;

import org.springframework.stereotype.Service;

@Service
public class RequestParser {

    public boolean requestValid(String request) {
        //using reflection check if the Object requested exist and if the properties asked are in this object
        return true;
    }
}
