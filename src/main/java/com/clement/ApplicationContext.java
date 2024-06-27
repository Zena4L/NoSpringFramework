package com.clement;

import com.clement.annotation.Autowired;
import com.clement.annotation.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.clement.annotation.ComponentScan;
import org.reflections.Reflections;

public class ApplicationContext {

    private final Map<Class<?>, Object> beanMap = new HashMap<>();

    public ApplicationContext(Class<?> config) throws Exception {
        if (!config.isAnnotationPresent(ComponentScan.class)) {
            throw new IllegalStateException("Must be annotated with @Component");
        }

        ComponentScan componentScan = config.getAnnotation(ComponentScan.class);
        String basePackage = componentScan.packageName();

        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> clazz = reflections.getTypesAnnotatedWith(Component.class);

        for (var c : clazz) {
            Object instance = c.getDeclaredConstructor().newInstance();
            beanMap.put(c, instance); // add bean to the contain
        }

        // DI
        for (Object b : beanMap.values()) {
            Field[] fields = b.getClass().getDeclaredFields();

            for (var f : fields) {
                if (f.isAnnotationPresent(Autowired.class)) {
                    f.setAccessible(true);
                    f.set(b, beanMap.get(f.getType()));
                }
            }
        }
    }

    public <T> T getBean(Class<T> beanClass) {
        return beanClass.cast(beanMap.get(beanClass));
    }
}
