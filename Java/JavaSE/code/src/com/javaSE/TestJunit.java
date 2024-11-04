package com.javaSE;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestJunit {
    int counter = 1;
    @Test
    public void test1(){
        counter++;
        System.out.println("counter = " + counter);
        System.out.println("This is test1");
    }

    @Test
    public void test2(){
        System.out.println("counter = " + counter);
        System.out.println("This is test2");
    }

    @Before
    public void methodBefore(){
        System.out.println("This is methodBefore");
    }

    @After
    public void methodAfter(){
        System.out.println("This is methodAfter");
    }

    @BeforeClass
    public static void methodBeforeClass(){
        System.out.println("This is BeforeClass");
    }
}
