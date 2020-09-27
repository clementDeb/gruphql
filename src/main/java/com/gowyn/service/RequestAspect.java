package com.gowyn.service;

import com.gowyn.exceptions.NoEntityObjectFound;
import com.gowyn.exceptions.NoPrimaryKeyFoundException;
import com.gowyn.exceptions.ObjectUnavailable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor(onConstructor_ = @__({@Autowired}))
@Slf4j
public class RequestAspect {

    private final RequestValidator validator;

    private final RequestParser parser;

    private final RequestRepo repo;


    @Around(value = "@annotation(GowynRequest) && args(id, request)", argNames = "pjp,id,request")
    public String processRequest(ProceedingJoinPoint pjp, long id, String request) throws Throwable {

        String requestResult = (String) pjp.proceed();
        //parse the request and get the list of needed object
        parser.buildRequestedObject(request).forEach(req -> {
            Pair<String, List<String>> requestObject = retrieveObjectAndFields(req);
            String objectName = requestObject.getValue0();
            List<String> fields = requestObject.getValue1();
            validateRequest(objectName, fields);
            try {
                Object dbInfo = repo.getDataByPrimaryKey(id, fields, objectName);
                // si les champs sont vide on recupere l'objet
                // sinon, on recupere la valeur des champs sous forme de liste
            } catch (NoPrimaryKeyFoundException | NoEntityObjectFound e) {
                log.error("error while requesting the query ", e);
                throw new RuntimeException(e);
            }
            //build & return the response
            StringBuilder sb = new StringBuilder();

        });
        return requestResult;
    }

    @SuppressWarnings("unchecked")
    private Pair<String, List<String>> retrieveObjectAndFields (String request){
        Object[] requestedObject = parser.parseRequestedObject(request);
        String objectName = (String) requestedObject[0];
        List<String> fields = (List<String>) requestedObject[1];
        return new Pair<>(objectName, fields);
    }

    private void validateRequest(String objectName, List<String> fields) {
        try {
            validator.validateRequestedObject(objectName, fields);
        } catch (NoSuchFieldException | NoEntityObjectFound | ObjectUnavailable e) {
            log.error("error while parsing the requested Object or parameter ", e);
            throw new RuntimeException(e);
        }
    }
}
