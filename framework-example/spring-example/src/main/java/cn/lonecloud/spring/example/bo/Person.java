package cn.lonecloud.spring.example.bo;

/**
 * Person 对象
 *
 * @author lonecloud <lonecloud@aliyun.com>
 * @date 2020/8/4 23:34
 * @since v1.0
 */
public class Person {

    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
