package com.func.spring;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;

public class TestComponent {
    /**
     * 扫描包下面的所有类并完成赋值
     */
    public static void main(String[] arg){

        HashMap<String, Object> beanMap = new HashMap<>();

        String packageName = "com.func.spring";
        String packagePath = packageName.replaceAll("\\.", "/");
        URL url = ClassLoader.getSystemClassLoader().getResource(packagePath);
        System.out.println(url);
        File f = new File(url.getPath());
        File[] files = f.listFiles();
        Arrays.stream(files).forEach(file -> {
            String fileName = file.getName();
            String className = packageName + "." +  fileName.split("\\.")[0];
            System.out.println(className);
            try {
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(Component.class)) {
                    Component component = clazz.getAnnotation(Component.class);
                    String value = component.value();
                    Constructor<?> constructor = clazz.getConstructor(String.class);
                    Object o = constructor.newInstance(value);
                    beanMap.put(value, o);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        System.out.println(beanMap);
    }

}
