/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 * @param <T>
 */
package net.edokun.tools;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class contains logic to instantiate a Java object following
 * the Factory Pattern and uses Generics.
 *
 * The class should be treated as a Singleton and needs to provide the target
 * class to be able to instantiate an object of that class, the reason for this
 * class is to provide a simple way to instantiate POJO's with random values or specified values
 * for unit tests when it is required to test with POJO's with values, it is not advised to use this class
 * when only a simple empty instance of a POJO is required.
 *
 * Currently this class can instantiate a POJO if:
 * <li>The POJO is a normal class with a zero-arg default constructor.</li>
 * <li>The POJO is a inner non static class.</li>
 *
 * @author Eduardo Vidal
 *
 * @param <T> The type or Target class to instantiate objects with respective attributes with values.
 */
public class PojoFactory<T> {

    private static final String INSTANTIATION_ERROR_MESSAGE = "Cannot instantiate class without default constructor";

    private final Class<T> type;

    private PojoFactory(Class<T> type) {
        this.type = type;
    }

    // private helper methods
    // TODO consider moving this to a helper class

    /**
     * Method that helps to determine if the class has at least a default zero-arg constructor or not.
     *
     * @param clazz The class to be verified
     * @return True if the class has a zero-arg constructor, False otherwise.
     */
    private boolean hasDefaultConstructor(Class<?> clazz) {
        for (Constructor<?> constructor : clazz.getConstructors()) {
            // In Java <= 7, use getParameterTypes and check the length of the array returned
            if (constructor.getParameterCount() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method that helps to determine if the class is an Inner non-static class.
     *
     * @param clazz The class to be verified
     * @return True if the class has a zero-arg constructor, False otherwise.
     */
    private boolean isInnerNonStatic(Class<?> clazz) {
        if (clazz.getEnclosingClass() != null && !Modifier.isStatic(clazz.getModifiers())) {
            return true;
        }

        return false;
    }

    // TODO need to improve this method
    // TODO add javadoc
    private void fillAttributes(T t) throws IllegalAccessException {
        List<Field> fields = Arrays.stream(t.getClass().getDeclaredFields()).collect(Collectors.toList());
        for (Field field : fields) {
            System.out.println("Attribute Name: " + field.getName() + " Attribute Type: " + field.getType());

            if (field.getType().equals(String.class)) {
                field.setAccessible(true);
                field.set(t, "test");
            }
        }
    }

    /**
     * This method instantiates a normal class, it only can
     * instantiate if the target class has a default zero-arg constructor.
     * 
     * @return an Instance of the target class T
     * @throws InstantiationException
     */
    private T instantiateClass() throws InstantiationException {
        T instantiatedClass = null;

        if (!hasDefaultConstructor(type)) {
            throw new InstantiationException(INSTANTIATION_ERROR_MESSAGE);
        }

        try {
            instantiatedClass = type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new InstantiationException(e.getMessage());
        }
        return instantiatedClass;
    }

    /**
     * This method instantiates an inner class only if the class is not static and
     * also has a default zero-arg constructor. Same as the parent class, both need a zero-arg constructor
     * 
     * @return an Instance of the target inner class T
     * @throws InstantiationException
     */
    private T instantiateInnerClass() throws InstantiationException {
        Class<?> enclosingClass = type.getEnclosingClass();
        if (!hasDefaultConstructor(enclosingClass)) {
            throw new InstantiationException(INSTANTIATION_ERROR_MESSAGE);
        }

        try {
            Object enclosingInstance = enclosingClass.newInstance();
            Class<?> innerClass =
                    Class.forName(enclosingClass.getCanonicalName() + "$" + type.getSimpleName());

            Constructor<?> constructor = innerClass.getDeclaredConstructor(enclosingClass);
            Object innerInstance = constructor.newInstance(enclosingInstance);

            return (T) innerInstance;

        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException
                | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            e.printStackTrace();
            throw new InstantiationException(e.getMessage());
        }
    }

    // public methods
    /**
     * Instantiates an object of class T (Provided when get instance of PojoFactory).
     * 
     * @return an instance with the attributes filled with either random or fixed values
     */
    public T build() {
        T classInstance = null;
        try {
            if (isInnerNonStatic(type)) {
                classInstance = instantiateInnerClass();
            } else {
                classInstance = instantiateClass();
            }

            fillAttributes(classInstance);

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return classInstance;
    }

    /**
     * Due to the nature of the Factory pattern of this class
     * a new instance for specific class needs to be created with the
     * specific target class as part of the parameters, hence in order to get an instance of the
     * PojoFactory a call to the getInstance method is required.
     * 
     * @param type The target class to instantiate objects.
     * @return An instantiated object of PojoFactory ready to build instantiated objects of the target class.
     */
    public static <V> PojoFactory<V> getInstance(Class<V> type) {
        return new PojoFactory<V>(type);
    }

}