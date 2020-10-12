package com.gowyn.service;


import com.gowyn.exceptions.NoEntityObjectFound;
import com.gowyn.exceptions.ObjectUnavailable;
import com.gowyn.invariant.DataInput;
import com.gowyn.utils.LambdaExceptionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class RequestValidator {

    private final ReflectionService reflectionService;

    protected RequestValidator(final ReflectionService reflectionService) {
        this.reflectionService = reflectionService;
    }

    /**
     * Method used to validate the request (Object and properties)
     *
     * @param data to be requested
     * @throws NoSuchFieldException
     * @throws NoEntityObjectFound
     * @throws ObjectUnavailable
     */
    protected void validateRequestedObject(final DataInput data) throws NoSuchFieldException, NoEntityObjectFound, ObjectUnavailable {
        //using reflection check if the Object requested exist and if the properties asked are in this object
        Set<Class<?>> clazz = getAvailablesObject();
        for (Class<?> c : clazz) {
            checkObjectValidity(data.getObjectName(), c);
            // check if the defined fields are in the previously checked object
            fieldsExist(c, data.getFields());
        }
    }

    private Set<Class<?>> getAvailablesObject() throws NoEntityObjectFound {

        Set<Class<?>> validObjects = reflectionService.getAvailablesObjects();
        if (validObjects.isEmpty()) {
            throw new NoEntityObjectFound("No object are annotated as an entity object");
        }
        return validObjects;
    }

    private void checkObjectValidity(String objectName, Class<?> clazz) throws ObjectUnavailable {
        if (!clazz.getSimpleName().equals(objectName)) {
            throw new ObjectUnavailable("The syntax of the requested Object (" + objectName + ") does not match existing entity object: ");
        }
    }

    private void fieldsExist(Class<?> objectClass, List<String> requestedFields) throws NoSuchFieldException {
        //get fields from the requested object
        List<Field> existingFields = Arrays.asList(objectClass.getDeclaredFields());
        requestedFields.forEach(LambdaExceptionUtils.useWithCheckedException(rf -> checkFieldValidity(rf, existingFields)));
    }

    private void checkFieldValidity(String rf, List<Field> existingFields) throws NoSuchFieldException {
        if (existingFields.stream().noneMatch(f -> f.getName().equalsIgnoreCase(rf))) {
            throw new NoSuchFieldException("The requested property does not exist: " + rf);
        }
    }

}
