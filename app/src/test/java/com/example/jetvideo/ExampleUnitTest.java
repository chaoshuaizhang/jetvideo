package com.example.jetvideo;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
         Ktest.Companion.testMap();
//        Integer integer = new Integer(123);
//        Class<? extends Integer> aClass = integer.getClass();
//        Field field = aClass.getDeclaredField("TYPE");
//        Class clazz = (Class) field.get(null);
//        if (clazz.isPrimitive()) {
//            System.out.println("-----------------");
//        }
    }
}