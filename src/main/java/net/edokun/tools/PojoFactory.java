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
 */
package net.edokun.tools;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;

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
    private boolean randomValues = false;
    private Random randomSeed;
    private T classInstance = null;

    private PojoFactory(Class<T> type) {
        this.type = type;
        randomSeed = new Random();
    }

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

    private boolean getRandomBoolean() {
        return randomSeed.nextBoolean();
    }

    private float getRandomFloat() {
        return randomSeed.nextFloat();
    }

    private double getRandomDouble() {
        return randomSeed.nextDouble();
    }

    private int getRandomInt() {
        return randomSeed.nextInt();
    }

    private char getRandomCharacter() {
        return (char) randomSeed.nextInt(Character.MAX_VALUE + 1);
    }

    private short getRandomShort() {
        return (short) randomSeed.nextInt(Short.MAX_VALUE + 1);
    }

    private long getRandomLong() {
        return randomSeed.nextLong();
    }

    private byte getRandomByte() {
        byte[] byteArray = new byte[1];
        randomSeed.nextBytes(byteArray);
        return byteArray[0];
    }

    // TODO need to improve this method
    /**
     * Sets either default values or random values for the attributes of the class,
     * currently only String, Object, primitive types and wrappers of primitives are supported.
     *
     * Arrays and Collections NOT SUPPORTED YET!!!
     *
     * @param t
     * @throws IllegalAccessException
     */
    private void fillAttributes(T t) throws IllegalAccessException {
        List<Field> fields = Arrays.stream(t.getClass().getDeclaredFields()).collect(Collectors.toList());
        for (Field field : fields) {
            field.setAccessible(true);

            if (field.getType().equals(String.class)) {
                if (randomValues) {
                    field.set(t, RandomStringUtils.randomAlphabetic(10));
                } else {
                    field.set(t, "");
                }
            // By default if the type is Object, it will instantiate a new Object class
            } else if (field.getType().equals(Object.class)) {
                field.set(t, new Object());

            } else if (field.getType().equals(Boolean.class)) {
                if (randomValues) {
                    field.set(t, getRandomBoolean());
                } else {
                    field.set(t, false);
                }
            } else if (field.getType().equals(Byte.class)) {
                if (randomValues) {
                    field.set(t, getRandomByte());
                } else {
                    field.set(t, Byte.valueOf("0"));
                }
            } else if (field.getType().equals(Character.class)) {
                if (randomValues) {
                    field.set(t, getRandomCharacter());
                } else {
                    field.set(t, '\u0000');
                }
            } else if (field.getType().equals(Short.class)) {
                if (randomValues) {
                    field.set(t, getRandomShort());
                } else {
                    field.set(t, Short.valueOf("0").shortValue());
                }
            } else if (field.getType().equals(Integer.class)) {
                if (randomValues) {
                    field.set(t, getRandomInt());
                } else {
                    field.set(t, 0);
                }
            } else if (field.getType().equals(Long.class)) {
                if (randomValues) {
                    field.set(t, getRandomLong());
                } else {
                    field.set(t, 0L);
                }
            } else if (field.getType().equals(Float.class)) {
                if (randomValues) {
                    field.set(t, getRandomFloat());
                } else {
                    field.set(t, 0.0F);
                }
            } else if (field.getType().equals(Double.class)) {
                if (randomValues) {
                    field.set(t, getRandomDouble());
                } else {
                    field.set(t, 0.0);
                }
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
            instantiatedClass = type.getConstructor().newInstance();
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
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

    /**
     * Instantiates an object of class T (Provided when get instance of PojoFactory).
     * 
     * @return an instance with the attributes filled with either random or fixed values
     */
    public T build() throws RuntimeException {
        try {
            if (classInstance == null) {
                //T classInstance = null;
                if (isInnerNonStatic(type)) {
                    classInstance = instantiateInnerClass();
                } else {
                    classInstance = instantiateClass();
                }

                fillAttributes(classInstance);

            } else {
                fillAttributes(classInstance);
            }

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
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

    /**
     * Ensure the attributes of the pojo will be random, for String attributes
     * it will be a random string of 10 characters of length
     */
    public void randomizeValues() {
        randomValues = true;
    }


    public void setInstatiatedPojo(T object) {
        classInstance = object;
    }

}