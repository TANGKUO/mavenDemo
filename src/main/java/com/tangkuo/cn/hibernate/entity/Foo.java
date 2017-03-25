package com.tangkuo.cn.hibernate.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class Foo {
	private Integer id;
	private String name;
	private Double salary;
	private boolean marry;
	private Date hireDate;
	private Timestamp lastLoginTime;
	public Date getHireDate() {
  	return hireDate;
  }
	public void setHireDate(Date hireDate) {
  	this.hireDate = hireDate;
  }
	public Integer getId() {
  	return id;
  }
	public void setId(Integer id) {
  	this.id = id;
  }
	public Timestamp getLastLoginTime() {
  	return lastLoginTime;
  }
	public void setLastLoginTime(Timestamp lastLoginTime) {
  	this.lastLoginTime = lastLoginTime;
  }
	public boolean isMarry() {
  	return marry;
  }
	public void setMarry(boolean marry) {
  	this.marry = marry;
  }
	public String getName() {
  	return name;
  }
	public void setName(String name) {
  	this.name = name;
  }
	public Double getSalary() {
  	return salary;
  }
	public void setSalary(Double salary) {
  	this.salary = salary;
  }
}
