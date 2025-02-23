package com.func.javassist;

import com.func.bank.pojo.Account;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.*;
import java.util.Arrays;


public class GenerateDaoByJavassist {
    /**
     * 创建dao层的接口的代理对象, 实现其中的增删改查
     * @param sqlSession 会话实例
     * @param daoInterface 需要被代理的接口
     * @return 接口的代理类
     */
    public static Object getMapper(SqlSession sqlSession, Class daoInterface){
        // 生成代理类
        ClassPool classPool = ClassPool.getDefault();
            // 拼接代理类的完整包名
        CtClass ctClass = classPool.makeClass(daoInterface.getPackageName() + ".impl." + daoInterface.getSimpleName() + "Impl");
            // 将接口类放入类池
        CtClass ctInterface = classPool.makeClass(daoInterface.getName());
            // 代理类代理接口类
        ctClass.addInterface(ctInterface);

        // 获取接口中的所有方法并实现
        Method[] methods = daoInterface.getDeclaredMethods();
        Arrays.stream(methods).forEach(method -> {
            // 拼接方法的签名
            StringBuilder methodStr = new StringBuilder();
            // 返回类型, 直接获取的是Class<?>, 不是类型名
            String returnType = method.getReturnType().getName();
            methodStr.append(returnType);
            methodStr.append(" ");
            // 拼接方法名
            String methodName = method.getName();
            methodStr.append(methodName);
            methodStr.append(" ");
            // 拼接参数列表
            methodStr.append("(");
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                Class<?> parameterType = parameterTypes[i];
                String typeName = parameterType.getName();
                methodStr.append(typeName);
                methodStr.append(" arg");
                methodStr.append(i);
                if (i != parameterTypes.length - 1) {
                    methodStr.append(", ");
                }
            }
            methodStr.append(") {");

            // 拼接方法体
                // 获取对应需要执行的Sql语句的type
            // 这行代码导致以后namespace必须是接口的全限定接口名, sqlId必须是方法名
            String sqlId = daoInterface.getName() + "." + methodName;
            // 获取SQLCommandType
            String sqlCommandType = sqlSession.getConfiguration().getMappedStatement(sqlId).getSqlCommandType().name();

            if ("SELECT".equals(sqlCommandType)) {
                // 获取session对象
                methodStr.append("org.apache.ibatis.session.SqlSession sqlSession = com.func.bank.utils.SqlSessionUtil.openSession();");
                // 执行SQL语句
                methodStr.append("Object obj = sqlSession.selectOne(\""+ sqlId + "\"," + "arg0);");
                methodStr.append("return (" + returnType + ") obj;");
            } else if ("UPDATE".equals(sqlCommandType)) {
                // 获取session对象
                methodStr.append("org.apache.ibatis.session.SqlSession sqlSession = com.func.bank.utils.SqlSessionUtil.openSession();");
                // 执行SQL语句
                methodStr.append("int count = sqlSession.update(\""+ sqlId + "\"," + "arg0);");
                methodStr.append("return count;");
            }
            methodStr.append("}");
            System.out.println(methodStr);

            try {
                // 将方法添加到类中
                CtMethod ctMethod = CtMethod.make(methodStr.toString(), ctClass);
                ctMethod.setModifiers(Modifier.PUBLIC);
                ctClass.addMethod(ctMethod);
            } catch (CannotCompileException e) {
                throw new RuntimeException(e);
            }
        });

        try {
            // 创建实体类
            Class<?> aClass = ctClass.toClass();
            Constructor<?> declaredConstructor = aClass.getDeclaredConstructor();
            Object o = declaredConstructor.newInstance();
            return o;
        } catch (CannotCompileException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
