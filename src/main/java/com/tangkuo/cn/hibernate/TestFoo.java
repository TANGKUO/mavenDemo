package com.tangkuo.cn.hibernate;

import java.sql.Date;
import java.sql.Timestamp;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.tangkuo.cn.hibernate.entity.Foo;

public class TestFoo {
//	@Test
//	public void testuuid(){
//		UUID uuid = UUID.randomUUID();
//		System.out.println(uuid.toString());
//	}
	
	
	@Test
	public void testFind(){
		Session session = HibernateUtil.getSession();
		Foo foo = (Foo)session.load(Foo.class, 1);
		System.out.println(foo.getName());
		System.out.println(foo.getHireDate());
		System.out.println(foo.isMarry());
		System.out.println(foo.getLastLoginTime());
		HibernateUtil.closeSession(session);
	}
	
//	@Test
	public void testSave(){
		Foo foo = new Foo();
		foo.setName("��˿");
		foo.setSalary(6000.0);
		foo.setMarry(false);
		foo.setHireDate(
			new Date(System.currentTimeMillis()));
		foo.setLastLoginTime(
			new Timestamp(System.currentTimeMillis()));
		//��Ӳ���
		Session session = HibernateUtil.getSession();
		System.out.println(session);
		Transaction tx = session.beginTransaction();
		session.save(foo);
		tx.commit();
		HibernateUtil.closeSession(session);
	}
}
