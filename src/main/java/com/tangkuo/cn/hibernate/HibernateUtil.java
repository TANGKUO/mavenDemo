package com.tangkuo.cn.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory sf;
	static{
		//����src�µ�hibernate������
		Configuration conf = new Configuration();
		conf.configure("/hibernate.cfg.xml");
		sf = conf.buildSessionFactory();
	}
	
	public static Session getSession(){
		return sf.openSession();
	}
	
	public static void closeSession(Session session){
		session.close();
	}
}
