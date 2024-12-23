package com.func.maven.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MavenTest {
    @Test
    public void testAssert(){
        int a = 10;
        int b = 20;
        Assertions.assertEquals((a+b), 30);
    }
}
