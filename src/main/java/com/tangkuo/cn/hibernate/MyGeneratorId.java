package com.tangkuo.cn.hibernate;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;


public class MyGeneratorId implements IdentifierGenerator{

	public Serializable generate(SessionImplementor arg0, Object arg1) throws HibernateException {
	  return null;
  }

	@Override
	public Serializable generate(SharedSessionContractImplementor arg0, Object arg1) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

}
