package cn.lonecloud.spring.example.bean;

import cn.lonecloud.spring.example.bo.Person;
import org.springframework.beans.factory.FactoryBean;

import java.util.Objects;

/**
 * PersonFactoryBean
 *
 * @author lonecloud <lonecloud@aliyun.com>
 * @date 2020/8/4 23:39
 * @since v1.0
 */
public class PersonFactoryBean implements FactoryBean<Person> {

    /**
     * 初始化Str
     */
    private String initStr;

    @Override
    public Person getObject() throws Exception {
        //这里我需要获取对应参数
        Objects.requireNonNull(initStr);
        String[] split = initStr.split(",");
        Person p=new Person();
        p.setAge(Integer.parseInt(split[0]));
        p.setName(split[1]);
        //这里可能需要还要有其他复杂事情需要处理
        return p;
    }

    @Override
    public Class<?> getObjectType() {
        return Person.class;
    }

    public String getInitStr() {
        return initStr;
    }

    public void setInitStr(String initStr) {
        this.initStr = initStr;
    }
}
