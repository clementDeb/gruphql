package com.graphqllike.gruphql.parser;


import com.graphqllike.gruphql.data.AvailableObject;
import com.graphqllike.gruphql.exceptions.AvailableObjectException;
import com.graphqllike.gruphql.exceptions.ObjectUnavailable;
import com.graphqllike.gruphql.utils.LambdaExceptionUtils;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class RequestedObjectValidator {

//    @Value("${annotated.object.package.name}")
//    private static String packageName;

    public static Reflections reflect = new Reflections("com.graphqllike.gruphql.data");

    private RequestedObjectValidator() {
    }

    public static void validateRequestedObject(String objectName, List<String> fields) throws NoSuchFieldException, AvailableObjectException, ObjectUnavailable {
        //using reflection check if the Object requested exist and if the properties asked are in this object
        Set<Class<?>> clazz = objectExist();
        for (Class c : clazz) {
            checkObjectValidity(objectName, c);
            // check if the defined fields are in the previously checked object
            fieldsExist(c, fields);
        }
    }

    private static Set<Class<?>> objectExist() throws AvailableObjectException {
        Set<Class<?>> validObjects = reflect.getTypesAnnotatedWith(AvailableObject.class);

        if (validObjects.isEmpty()) {
            throw new AvailableObjectException("No object are annotated");
        }
        return validObjects;
    }

    private static void checkObjectValidity(String objectName, Class clazz) throws ObjectUnavailable {
        if (!clazz.getSimpleName().equalsIgnoreCase(objectName)) {
            throw new ObjectUnavailable("The requested Object does not exist: " + objectName);
        }
    }

    private static void fieldsExist(Class objectClass, List<String> requestedFields) throws NoSuchFieldException {
        //get fields from the requested object
        List<Field> existingFields = Arrays.asList(objectClass.getDeclaredFields());
        requestedFields.forEach(LambdaExceptionUtils.useWithCheckedException(rf -> checkFieldValidity(rf, existingFields)));
    }

    private static void checkFieldValidity(String rf, List<Field> existingFields) throws NoSuchFieldException {
        if (existingFields.stream().noneMatch(f -> f.getName().equalsIgnoreCase(rf))) {
            throw new NoSuchFieldException("The requested property does not exist: " + rf);
        }
    }

}
