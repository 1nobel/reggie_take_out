package com.fct.test;

import org.junit.jupiter.api.Test;

public class TestString {

    @Test
    public void test01(){
        String a = new String("asdcvdrfas.jpg");
        String substring = a.substring(a.lastIndexOf("."));
        System.out.println(substring);
    }
}
