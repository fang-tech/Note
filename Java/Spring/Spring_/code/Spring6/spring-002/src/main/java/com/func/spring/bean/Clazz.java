package com.func.spring.bean;

public class Clazz {
    private String clazz;

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getClazz() {
        return clazz;
    }

    @Override
    public String toString() {
        return "Clazz{" +
                "clazz='" + clazz + '\'' +
                '}';
    }
}
