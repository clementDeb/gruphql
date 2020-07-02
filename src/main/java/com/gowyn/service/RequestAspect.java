package com.gowyn.service;

import com.gowyn.exceptions.AvailableObjectException;
import com.gowyn.exceptions.ObjectUnavailable;
import com.gowyn.repo.RequestRepo;
import com.gowyn.utils.LambdaExceptionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.javatuples.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Tuple;
import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor(onConstructor_ = @__({@Autowired}))
@Slf4j
public class RequestAspect {

    private final RequestValidator validator;

    private final RequestParser parser;

    private final RequestRepo repo;


    @Before(value = "@annotation(GowynRequest) && args(id, request)", argNames = "id,request")
    public String processRequest(long id, String request) {

        //parse the request and get the list of needed object
        parser.buildRequestedObject(request).forEach(r -> {
            Object[] requestedObject = parser.parseRequestedObject(r);
            String objectName = (String) requestedObject[0];
            List<String> fields = (List<String>) requestedObject[1];
            //validate the request
            validateRequest(objectName, fields);
            //retrieve data from Db
            //List<Unit> dbInfo = repo.getDataById(id, fields, objectName);
            //build & return the response


        });
        return "";
    }

    private void validateRequest(String objectName, List<String> fields) {
        try {
            validator.validateRequestedObject(objectName, fields);
        } catch (NoSuchFieldException | AvailableObjectException | ObjectUnavailable e) {
            log.error("error while parsing the requested Object or parameter ", e);
        }
    }
}
