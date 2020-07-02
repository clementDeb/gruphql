package com.gowyn.service;

import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.List;

@Service
public class ResponseBuilder {

    protected String buildResponse(List<Tuple> tuples, String objectName){
        StringBuilder response = new StringBuilder();
        response.append(objectName).append(RequestConstant.OBJECT_DELIMITER);
        tuples.forEach(tuple -> {
            int tuplePosition = tuples.indexOf(tuple);
            if (tuplePosition < tuples.size()-1){
                response.append(tuple).append(RequestConstant.FIELDS_DELIMITER);
            }
            response.append(tuple);
        });

        return response.toString();
    }

}
