package com.tangkuo.cn.hibernate.entity;

import java.sql.Date;

public class Cost {

	private Integer id; 

	private String feeName; 

	private Integer baseDuration;

	private Float baseCost; 

	private Float unitCost; 

	private String status; 

	private String descr; 

	private Date createTime; 

	private Date startTime; 
	
	private String costType;

	public String getCostType() {
  	return costType;
  }

	public void setCostType(String costType) {
  	this.costType = costType;
  }

	public Float getBaseCost() {
		return baseCost;
	}

	public void setBaseCost(Float baseCost) {
		this.baseCost = baseCost;
	}

	public Integer getBaseDuration() {
		return baseDuration;
	}

	public void setBaseDuration(Integer baseDuration) {
		this.baseDuration = baseDuration;
	}

	

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getFeeName() {
		return feeName;
	}

	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public Date getCreateTime() {
  	return createTime;
  }

	public void setCreateTime(Date createTime) {
  	this.createTime = createTime;
  }

	public Date getStartTime() {
  	return startTime;
  }

	public void setStartTime(Date startTime) {
  	this.startTime = startTime;
  }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Float getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Float unitCost) {
		this.unitCost = unitCost;
	}

	@Override
	public String toString() {
		return id + "," + feeName;
	}

}
