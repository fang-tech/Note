package org.myspringframework.core;


import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author 方天宇
 * @version 1.0
 * @className ClassPathXmlApplicationContext
 * @date 2024/12/26
 */
public class ClassPathXmlApplicationContext implements ApplicationContext {
    // Map用于存储bean
    private Map<String, Object> beanMap = new HashMap<>();


    /**
     * 构造方法解析xml文件, 并创建所有的实例bean, 以此实现曝光
     * @param resource xml文件的资源路径
     */
    public ClassPathXmlApplicationContext(String resource) {
        try {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(ClassLoader.getSystemClassLoader().getResourceAsStream(resource));
            // 获取到所有的bean标签
            List<Element> beanNodes = document.getRootElement().elements();
            // 遍历标签
            beanNodes.forEach(beanNode -> {
                String id = beanNode.attributeValue("id");
                String className = beanNode.attributeValue("class");

                // 通过反射机制创建对象, 并放入map集合中
                try {
                    Class<?> clazz = Class.forName(className);
                    // 创建新的实例
                    Constructor<?> constructor = clazz.getConstructor();
                    Object bean= constructor.newInstance();
                    // 将新的实例添加到Map集合中
                    beanMap.put(id, bean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        // 给bean的属性赋值, 将创建对象和赋值这两阶段分开进行, 以此实现曝光
        beanNodes.forEach(beanNode ->{
            String id = beanNode.attributeValue("id");
            List<Element> properties = beanNode.elements("property");
            properties.forEach(property -> {
                try {
                    String propertyName = property.attributeValue("name");
                    String value = property.attributeValue("value");
                    String ref = property.attributeValue("ref");
                    // 获取属性类型
                    Class<?> propertyType = beanMap.get(id).getClass().getDeclaredField(propertyName).getType();
                    // 获取set方法
                    String setMethodName = "set" + propertyName.toUpperCase().charAt(0) + propertyName.substring(1);
                    Method setMethod = beanMap.get(id).getClass().getDeclaredMethod(setMethodName, propertyType);
                    // 获取set方法
                    Object propertyValue = null;
                    if (value == null) {
                        // 非简单类型
                        setMethod.invoke(beanMap.get(id), beanMap.get(ref));
                    }
                    if (ref == null) {
                        // 简单类型
                        String propertySimpleName = propertyType.getSimpleName();
                        // 将值从String转为对应的简单类型
                        switch (propertySimpleName) {
                            case "int" : case "Integer" :
                                propertyValue = Integer.valueOf(value);
                                break;
                            case "double" : case "Double":
                                propertyValue = Double.valueOf(value);
                                break;
                            case "float": case "Float":
                                propertyValue = Float.valueOf(value);
                                break;
                            case "long" : case "Long" :
                                propertyValue = Long.valueOf(value);
                                break;
                            case "short" : case "Short":
                                propertyValue = Short.valueOf(value);
                                break;
                            case "boolean": case "Boolean":
                                propertyValue = Boolean.valueOf(value);
                                break;
                            case "char" : case "Character" :
                                propertyValue = value.charAt(0);
                                break;
                            default:
                                propertyValue = value;
                                break;
                        }
                        setMethod.invoke(beanMap.get(id), propertyValue);
                    }
                } catch (Exception e) {
                   e.printStackTrace();
                }
            });

        });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getBean(String beanId) {
        return beanMap.get(beanId);
    }
}
