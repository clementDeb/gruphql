package com.graphqllike.gruphql.parser;


import com.graphqllike.gruphql.data.AvailableObject;
import com.graphqllike.gruphql.exceptions.AvailableObjectException;
import com.graphqllike.gruphql.exceptions.ObjectUnavailable;
import com.graphqllike.gruphql.utils.LambdaExceptionUtils;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class RequestValidator {

    public static void validateRequest(String objectName, List<String> fields) throws ObjectUnavailable, AvailableObjectException {
        //using reflection check if the Object requested exist and if the properties asked are in this object
        Class<?> o = objectExist(objectName);

        // check if the defined fields are in the previously checked object
        fieldsExist(o, fields);

    }

    private static Class<?> objectExist(String objectName) throws ObjectUnavailable, AvailableObjectException {

        Reflections reflect = new Reflections();
        Set<Class<?>> validObject = reflect.getTypesAnnotatedWith(AvailableObject.class);

        for (Class clazz : validObject) {
            return checkObjectValidity(objectName, clazz);
        }
        throw new AvailableObjectException("No object are annotated");
    }

    private static Class<?> checkObjectValidity(String objectName, Class clazz) throws ObjectUnavailable {
        if (clazz.getSimpleName().equals(objectName)) {
            return clazz;
        } else {
            throw new ObjectUnavailable("The requested Object does not exist");
        }
    }

    private static void fieldsExist(Class objectClass, List<String> requestedFields) {

        //get fields from the requested object
        List<Field> existingFields = Arrays.asList(objectClass.getDeclaredFields());
        requestedFields.forEach(LambdaExceptionUtils.useWithCheckedException(rf -> checkFieldValidity(rf, existingFields)));
    }

    private static void checkFieldValidity(String rf, List<Field> existingFields) throws NoSuchFieldException {
        if (existingFields.stream().noneMatch(f -> f.getName().equalsIgnoreCase(rf))) {
            throw new NoSuchFieldException();
        }
    }

}
