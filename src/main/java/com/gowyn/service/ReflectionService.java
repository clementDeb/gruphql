package com.gowyn.service;

import com.gowyn.data.AvailableObject;
import com.gowyn.exceptions.NoPrimaryKeyFoundException;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class ReflectionService {

    private final Reflections reflect;

    private final String packageName;

    protected ReflectionService(@Value("${avalaible.object.package}") String packageName) {
        reflect = new Reflections(packageName);
        this.packageName = packageName+".";
    }

    protected Set<Class<?>> getAvailablesObjects() {
        return reflect.getTypesAnnotatedWith(AvailableObject.class);
    }

    protected Field retrievePrimaryKey(String objectName) throws NoPrimaryKeyFoundException {
        Class<?> objectClass = null;
        try {
            objectClass = Class.forName(packageName + objectName);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
            //should not happen because of the validity done in the parser
        }
        Field[] fields = Optional.ofNullable(objectClass).map(Class::getDeclaredFields).orElseGet(() -> new Field[0]);
        return Arrays.stream(fields).filter(f -> f.isAnnotationPresent(javax.persistence.Id.class)).findFirst().orElseThrow(() -> new NoPrimaryKeyFoundException("Aucun champ de cette entité n'est une clé primaire"));

    }


}
