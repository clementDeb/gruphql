package com.gowyn.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RequestParser {

    private static final String OBJECT_DELIMITER = ":";

    private static final String FIELDS_DELIMITER = "/";

    public List<String> buildRequestedObject(String request) {
        return request.lines().collect(Collectors.toList());
    }

    public Map<String, String[]> parseRequestedObject(String request) {

        int objectDelimiter = request.indexOf(OBJECT_DELIMITER);
        String objectName = request.substring(0, objectDelimiter);
        String fieldString = request.substring(objectDelimiter+1);
        String[] fields = fieldString.split(FIELDS_DELIMITER);

        Map<String, String[]> requestedObject = new HashMap<>();
        requestedObject.put(objectName, fields);

        return requestedObject;
    }
}
