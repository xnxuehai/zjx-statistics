package com.park.domain;

/**
 * @author Aaron
 * @date 2020/6/10 6:26 下午
 */
public class Data {

	private String name;

	private int age;

	private Integer height;

	public Data() {

	}

	public Data(String name, int age, Integer height) {
		this.name = name;
		this.age = age;
		this.height = height;
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

	@Override
	public String toString() {
		return "Data{" +
				"name='" + name + '\'' +
				", age=" + age +
				", height=" + height +
				'}';
	}
}
