package com.userAPI.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import com.userAPI.models.Roles;

@Service
public class RoleService {

public static SessionFactory factory = HibernateSession.factory;

	//--------------- METHOD TO LIST ALL ROLES -------------------- //

	public List<Roles> getRoles() {
	
		List<Roles> resultados = new ArrayList<Roles>();
		Session session = factory.openSession();
		Transaction tx = null;
  
		try {
			tx = session.beginTransaction();
			String hql = "FROM Roles";
			Query query = session.createQuery(hql);
			resultados = query.list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close(); 
		}
		return resultados;
	}

	//--------------- METHOD TO READ A ROLE -------------------- //

	public Roles getRole(int roleID) {
	
		Roles role = new Roles();
		Session session = factory.openSession();
		Transaction tx = null;
      
		try {
			tx = session.beginTransaction();
			String hql = "FROM Roles WHERE id = :id";
			Query query = session.createQuery(hql);
			query.setParameter("id", roleID);
			List resultados = query.list();
			for (Iterator iterator = resultados.iterator(); iterator.hasNext();){
				role = (Roles) iterator.next(); 
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close(); 
		}
      return role;
	}

	// --------------- METHOD TO CREATE A ROLE -------------------- //
	
	public int createRole(Roles role) {
		
		int roleID = 0;
		Session session = factory.openSession();
	    Transaction tx = null;
	    
	      try {
	    	 role.setName(role.getName().trim());
	         tx = session.beginTransaction();
	         session.save(role); 
	         tx.commit();
	         String hql = "SELECT id FROM Roles WHERE UPPER(name) = :name";
	         Query query = session.createQuery(hql);
	         query.setParameter("name", role.getName().trim().toUpperCase());
	         roleID = Integer.parseInt(query.getSingleResult().toString());
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	      return roleID;
	}
	
	// --------------- METHOD TO EDIT AN EXISTING ROLE -------------------- //
	
	public void updateRole(Integer roleID, Roles role) {
		
		Session session = factory.openSession();
	    Transaction tx = null;
	      
	      try {
	         tx = session.beginTransaction();
	         Roles object = (Roles) session.get(Roles.class, roleID); 
	         object.setName(role.getName().trim());
	         object.setAmmountMembers(role.getAmmountMembers());
			 session.update(object); 
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	}
	
	// --------------- METHOD TO DELETE AN EXISTING ROLE -------------------- //
	
	public void deleteRole(Integer roleID, Integer newRoleID) {
		
		int oldRoleAmmount = 0;
		Session session = factory.openSession();
	    Transaction tx = null;
	      
	      try {
	         tx = session.beginTransaction();
	         String hql = "SELECT ammountMembers FROM Roles WHERE id = :id";
	         Query query = session.createQuery(hql);
	         query.setParameter("id", roleID);
	         oldRoleAmmount = Integer.parseInt(query.getSingleResult().toString());
	         
	         String SQL = "UPDATE Role SET role_members = role_members + :ammountMembers WHERE id = :id";
	         NativeQuery actualizacion = session.createSQLQuery(SQL);
	         actualizacion.setParameter("ammountMembers", oldRoleAmmount);
	         actualizacion.setParameter("id", newRoleID);
	         actualizacion.executeUpdate();
	         
	         SQL = "UPDATE User SET usr_role = :newID WHERE usr_role = :oldID";
	         actualizacion = session.createSQLQuery(SQL);
	         actualizacion.setParameter("newID", newRoleID);
	         actualizacion.setParameter("oldID", roleID);
	         actualizacion.executeUpdate();
	         
	         Roles deleteObject = (Roles) session.get(Roles.class, roleID); 
			 session.delete(deleteObject); 
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	}

}
