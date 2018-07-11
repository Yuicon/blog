package aop;

import ioc.Dao;
import ioc.OrderDao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Yuicon
 * @Date 2018/7/10
 */
public class DynamicProxy implements InvocationHandler {

    /**
     * 被代理类
     */
    private Object target;

    public DynamicProxy(Object target) {
        this.target = target;
    }

    public static Object bind(Object target) {
        InvocationHandler invocationHandler = new DynamicProxy(target);
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), invocationHandler);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(method.getName() + " 方法执行前");
        //执行被代理类方法
        Object ret = method.invoke(target, args);
        System.out.println(method.getName() + " 方法执行后");
        return ret;
    }

    public static void main(String[] args) {
        Dao dao = new OrderDao();
        dao.doSomeThing();
        Dao daoProxy = (Dao) DynamicProxy.bind(dao);
        daoProxy.doSomeThing();
    }

}
