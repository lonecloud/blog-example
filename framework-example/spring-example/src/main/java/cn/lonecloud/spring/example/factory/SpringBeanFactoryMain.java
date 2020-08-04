package cn.lonecloud.spring.example.factory;

import cn.lonecloud.spring.example.bean.PersonFactoryBean;
import cn.lonecloud.spring.example.bo.Person;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * BeanFactory 例子
 *
 * @author lonecloud <lonecloud@aliyun.com>
 * @date 2020/8/4 23:32
 * @since v1.0
 */
public class SpringBeanFactoryMain {

    public static void main(String[] args) {
        //这个是BeanFactory
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("application.xml");
        //获取对应的对象化
        Object demo = beanFactory.getBean("demo");
        System.out.println(demo instanceof Person);
        System.out.println(demo);
        //获取从工厂Bean中获取对象
        Person demoFromFactory = beanFactory.getBean("demoFromFactory", Person.class);
        System.out.println(demoFromFactory);
        //获取对应的personFactory
        Object bean = beanFactory.getBean("&demoFromFactory");
        System.out.println(bean instanceof PersonFactoryBean);
        PersonFactoryBean factoryBean=(PersonFactoryBean) bean;
        System.out.println("初始化参数为："+factoryBean.getInitStr());
    }
}
