package com.gowyn.service;

import com.gowyn.data.AvailableObject;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ReflectionService {

    private final Reflections reflect;

    public ReflectionService (@Value("${avalaible.object.package}") String packageName){
        reflect = new Reflections(packageName);
    }

    public Set<Class<?>> getAvailablesObjects(){
        return reflect.getTypesAnnotatedWith(AvailableObject.class);
    }

}
