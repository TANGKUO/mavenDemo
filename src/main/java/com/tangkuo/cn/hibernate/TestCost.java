package com.tangkuo.cn.hibernate;

import java.sql.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import com.tangkuo.cn.hibernate.entity.Cost;

public class TestCost {
	
	@Test
	public void test5(){
		Session session = HibernateUtil.getSession();
		String hql = "from Cost";//�ȼ���select * from COST
		Query query = session.createQuery(hql);
		List<Cost> list = query.list();//ִ��hql��ѯ
		for(Cost cost : list){
			System.out.println(cost.getId()+" "+cost.getFeeName());
		}
		HibernateUtil.closeSession(session);
	}
	
	@Test
	//����ɾ��
	public void test4(){
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		Cost cost = new Cost();
		cost.setId(1445);
		session.delete(cost);//delete by id
		tx.commit();
		HibernateUtil.closeSession(session);
	}
	
	@Test
	//���Ը��£�����base_duration=200
	public void test3(){
		Session session = HibernateUtil.getSession();
		//��������
		Transaction tx = session.beginTransaction();
		Cost cost = (Cost)session.load(Cost.class, 1445);
		cost.setBaseDuration(200);
		cost.setStartTime(
				new Date(System.currentTimeMillis()));
		session.update(cost);//ִ�и���
		//�ύ����
		tx.commit();
		HibernateUtil.closeSession(session);
	}
	
	@Test
	//�������
	public void test2(){
		Cost cost = new Cost();
		cost.setFeeName("�����ײ�");
		cost.setStatus("0");
		cost.setBaseDuration(100);
		cost.setBaseCost(20.0f);
		cost.setUnitCost(0.1f);
		cost.setCreateTime(
			new Date(System.currentTimeMillis()));
		//����hibernate����
		Session session = HibernateUtil.getSession();
		//��������
		Transaction tx = session.beginTransaction();
		session.save(cost);//ִ�����
		//�ύ����
		tx.commit();
		System.out.println(cost.getId());
		HibernateUtil.closeSession(session);
	}
	
	
	@Test
	public void test1(){
		Configuration conf = new Configuration();
		conf.configure("/hibernate.cfg.xml");
		SessionFactory sf = conf.buildSessionFactory();
		Session session = sf.openSession();
		Cost cost = (Cost)session.get(Cost.class, 1);
		System.out.println(cost.getFeeName() +" "+cost.getStatus()+" "+cost.getCreateTime());
		session.close();
	}
}
