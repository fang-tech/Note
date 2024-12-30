package com.func.spring.bean;

import lombok.Data;

import javax.imageio.event.IIOReadProgressListener;
import java.util.Date;
import java.util.Iterator;

@Data
public class SimpleValue {
    private int i1;
    private Integer i2;

    private boolean b1;
    private Boolean b2;

    private char c1;
    private Character c2;

    private Season season;

    private String string;
    private Class clazz;

    private Date birth;
}
