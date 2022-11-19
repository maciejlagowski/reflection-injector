package io.github.maciejlagowski.reflectioninjector;

import io.github.maciejlagowski.reflectioninjector.annotations.Autowired;
import io.github.maciejlagowski.reflectioninjector.annotations.Controller;
import io.github.maciejlagowski.reflectioninjector.annotations.Instance;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReflectionInjector {

    private Set<Object> instances;
    private Set<Object> controllers;

    public Set<Object> scanAndConstruct() throws Exception {
        Reflections reflections = new Reflections();
        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Controller.class);
        Set<Class<?>> instances = reflections.getTypesAnnotatedWith(Instance.class);
        this.instances = constructObjects(instances);
        this.controllers = constructObjects(controllers);
        return this.controllers;
    }

    private Set<Object> constructObjects(Set<Class<?>> classes) throws Exception {
        Set<Object> objects = new HashSet<Object>();
        for (Class<?> clazz: classes) {
            Constructor<?>[] ctors = clazz.getConstructors();
            Constructor<?> constructor = null;
            for (Constructor<?> ctor: ctors) {
                if (ctor.isAnnotationPresent(Autowired.class)) {
                    constructor = ctor;
                    break;
                }
            }
            if (constructor == null) {
                constructor = clazz.getConstructor();
            }
            objects.add(constructObject(constructor));
        }
        return objects;
    }

    private Object constructObject(Constructor<?> ctor) throws Exception {
        Class<?>[] parameters = ctor.getParameterTypes();
        List<Object> objParams = new ArrayList<Object>(parameters.length);
        for (Class<?> parameter: parameters) {
            for (Object obj: this.instances) {
                if (obj.getClass().equals(parameter)) {
                    objParams.add(obj);
                }
            }
        }
        if (objParams.size() < parameters.length) {
            throw new Exception("Cannot find all required instances");
        }
        return ctor.newInstance(objParams.toArray());
    }
}
