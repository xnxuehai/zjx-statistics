package com.park.domain;

/**
 * @author Aaron
 * @date 2020/6/10 6:26 下午
 */
public class Data {

    private Integer userId;

    private String name;

    private int age;

    private Integer height;

    private Integer status;

    public Data() {

    }

    public Data(String name, int age, Integer height, Integer userId) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

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

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
