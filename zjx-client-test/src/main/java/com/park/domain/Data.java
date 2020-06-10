package com.park.domain;

/**
 * @author Aaron
 * @date 2020/6/10 6:26 下午
 */
public class Data {

    private String name;

    private Integer age;

    public Data() {
        
    }

    public Data(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Data{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
