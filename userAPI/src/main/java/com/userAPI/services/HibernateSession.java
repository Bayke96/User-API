package com.userAPI.services;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.userAPI.models.Roles;
import com.userAPI.models.User;

public class HibernateSession {
	
	public static SessionFactory factory = configurarSession();
	
	public static SessionFactory configurarSession() {
		
		SessionFactory fact;
				
		try {
	         fact = new Configuration().
	                   configure().
	                   addAnnotatedClass(User.class).
	                   addAnnotatedClass(Roles.class).
	                   buildSessionFactory();
	      } catch (Throwable ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
	         throw new ExceptionInInitializerError(ex); 
	      }
		
		return fact;
	}
}
