package com.gowyn.service;

import com.gowyn.exceptions.NoEntityObjectFound;
import com.gowyn.exceptions.NoPrimaryKeyFoundException;
import com.gowyn.exceptions.ObjectUnavailable;
import com.gowyn.invariant.DataInput;
import com.gowyn.invariant.DatasInput;
import com.gowyn.invariant.DatasOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor(onConstructor_ = @__({@Autowired}))
@Slf4j
public class RequestAspect {

    private final RequestValidator validator;

    private final RequestRepo repo;


    @Around(value = "@annotation(GowynRequest) && args(id, datas)", argNames = "pjp,id,datas")
    public DatasOutput processRequest(ProceedingJoinPoint pjp, long id, DatasInput datas) throws Throwable {

        DatasInput result = (DatasInput) pjp.proceed();
        //parse the request and get the list of needed object
        return Optional.ofNullable(result).map(ds -> buildResponse(id, ds)).orElseThrow(() -> new RuntimeException("result can't be build"));
    }

    private DatasOutput buildResponse(long id, DatasInput datas) {
        for (DataInput data : datas.getInputs()) {
            validateRequest(data);
            int propsCount = List.of(data.getClass().getDeclaredFields()).size();

            Object dbResults;
            try {
                dbResults = repo.getDataByPrimaryKey(id, data);
                // si les champs sont vide on recupere l'objet
                // sinon, on recupere la valeur des champs sous forme de liste
                if (data.getFields().isEmpty()) {

                    //Data dataResult = buildDataResult(data.getObjectName(), dbResults);
                }

            } catch (NoPrimaryKeyFoundException | NoEntityObjectFound e) {
                log.error("error while requesting the query ", e);
                throw new RuntimeException(e);
            }
            //build & return the response

        }
        return null;
    }

    private void validateRequest(DataInput data) {
        try {
            validator.validateRequestedObject(data);
        } catch (NoSuchFieldException | NoEntityObjectFound | ObjectUnavailable e) {
            log.error("error while parsing the requested Object or parameter ", e);
            throw new RuntimeException(e);
        }
    }

//    private Data buildDataResult(final String objectName, final Object dbResult) {
//        List<String> fieldsFromDb;
//
//
//        return null;
//    }
}
