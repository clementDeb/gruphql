package com.gowyn.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestParser {

    protected List<String> buildRequestedObject(String request) {
        return request.lines().collect(Collectors.toList());
    }

    protected Object[] parseRequestedObject(String request) {

        int objectDelimiter = request.indexOf(RequestConstant.OBJECT_DELIMITER);
        String objectName = request.substring(0, objectDelimiter);
        String fieldString = request.substring(objectDelimiter+1);
        List<String> fields = Arrays.asList(fieldString.split(RequestConstant.FIELDS_DELIMITER));

        return new Object[]{objectName, fields};
    }
}
