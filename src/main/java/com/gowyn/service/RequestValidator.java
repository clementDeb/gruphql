package com.gowyn.service;


import com.gowyn.exceptions.AvailableObjectException;
import com.gowyn.exceptions.ObjectUnavailable;
import com.gowyn.utils.LambdaExceptionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class RequestValidator {

    private final ReflectionService reflectionService;

    public RequestValidator(final ReflectionService reflectionService) {
        this.reflectionService = reflectionService;
    }

    /**
     * Method used to validate the request (Object and properties)
     *
     * @param objectName
     * @param fields
     * @throws NoSuchFieldException
     * @throws AvailableObjectException
     * @throws ObjectUnavailable
     */
    public void validateRequestedObject(String objectName, List<String> fields) throws NoSuchFieldException, AvailableObjectException, ObjectUnavailable {
        //using reflection check if the Object requested exist and if the properties asked are in this object
        Set<Class<?>> clazz = getAvailablesObject();
        for (Class<?> c : clazz) {
            checkObjectValidity(objectName, c);
            // check if the defined fields are in the previously checked object
            fieldsExist(c, fields);
        }
    }

    private Set<Class<?>> getAvailablesObject() throws AvailableObjectException {

        Set<Class<?>> validObjects = reflectionService.getAvailablesObjects();
        if (validObjects.isEmpty()) {
            throw new AvailableObjectException("No object are annotated");
        }
        return validObjects;
    }

    private void checkObjectValidity(String objectName, Class<?> clazz) throws ObjectUnavailable {
        if (!clazz.getSimpleName().equals(objectName)) {
            throw new ObjectUnavailable("The syntax of the requested Object (" + objectName + ") does not match existing object: ");
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
