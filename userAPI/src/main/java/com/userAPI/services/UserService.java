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

import com.userAPI.models.User;
import com.userAPI.security.BCrypt;

@Service
public class UserService {
	
	public static SessionFactory factory = HibernateSession.factory;
	
	//--------------- METHOD TO LIST ALL USERS -------------------- //

		public List<User> getUsers() {
		
			List<User> resultados = new ArrayList<User>();
			Session session = factory.openSession();
			Transaction tx = null;
	  
			try {
				tx = session.beginTransaction();
				String hql = "FROM User";
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
	
	// --------------- METHOD TO GET AN USER -------------------- //
	
	public User getUser(int userID) {
		
		User user = new User();
		Session session = factory.openSession();
	    Transaction tx = null;
	      
	      try {
	         tx = session.beginTransaction();
	         String hql = "FROM User WHERE id = :id";
	         Query query = session.createQuery(hql);
	         query.setParameter("id", userID);
	         List resultados = query.list();
	         for (Iterator iterator = resultados.iterator(); iterator.hasNext();){
	            user = (User) iterator.next(); 
	         }
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
		return user;
	}
	
	// --------------- METHOD TO VERIFY AN USER'S PASSWORD -------------------- //
	
	public boolean verifyPassword(String email, String password) {
		
		boolean result = false;
		BCrypt encrypt = new BCrypt();
		Session session = factory.openSession();
		Transaction tx = null;
		
	      try {
	    	  
	    	 tx = session.beginTransaction();
	    	 Query query = session.createQuery("SELECT c.password FROM User c WHERE UPPER(c.email) = :email");
	    	 query.setParameter("email", email.toUpperCase().trim());
	    	 String pass = query.uniqueResult().toString();
	    	 result = encrypt.verifyPassword(password, pass);
	         tx.commit();
	         
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	      
		return result;
	}
	
	// --------------- METHOD TO CHANGE AN USER'S PASSWORD -------------------- //
	
	public void changePassword(String email, String oldPass, String newPass) {
		
		if(verifyPassword(email, oldPass) == false) {
			System.out.println("Incorrect Password!");
			return;
		} else {
			
			if(newPass.length() < 8) {
				 System.out.println("The new password must contain at least 8 characters!");
	    		 return;
			}
			
			Session session = factory.openSession();
			Transaction tx = null;
			BCrypt crypt = new BCrypt();
			try {
		    	 tx = session.beginTransaction();
		    	 Query query = session.createQuery("SELECT c.id FROM User c WHERE UPPER(c.email) = :email");
		    	 query.setParameter("email", email.toUpperCase().trim());
		    	 int userID = Integer.parseInt(query.uniqueResult().toString());
		    	 User object = (User) session.get(User.class, userID); 
		    	 object.setPassword(crypt.encryptPassword(newPass));
		    	 session.update(object);
		         tx.commit();
		      } catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      } finally {
		         session.close(); 
		      }
		}
	}
	
	// --------------- USER LOGIN METHOD -------------------- //
	
	public boolean loginUser(User user) {
		
		List list = new ArrayList();
		BCrypt encrypt = new BCrypt();
		boolean result = false;
		Session session = factory.openSession();
		Transaction tx = null;
		
	      try {
	    	 tx = session.beginTransaction();
	    	 Query query = session.createQuery("SELECT c.password FROM USER c WHERE UPPER(c.email) = :email");
	    	 query.setParameter("email", user.getEmail().toUpperCase().trim());
	    	 list = query.list();
	    	 
	    	 if(list.isEmpty() == true) {
	    		 return false;
	    	 } else {
	    		 result = encrypt.verifyPassword(user.getPassword(), list.get(0).toString());
	    	 }
	    	 	    	
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
		return result;
	}
	
	// --------------- METHOD TO CREATE AN USER -------------------- //
	
	public int createUser(User user) {
		
		int objectID = 0;
		Session session = factory.openSession();
		BCrypt encriptado = new BCrypt();
	    Transaction tx = null;
	    
	      try {
	    	 if(user.getPassword().length() < 8) {
	    		 System.out.println("The password requires at least 8 characters!");
	    		 return 0;
	    	 }
	         tx = session.beginTransaction();
	         String encryptedPassword = encriptado.encryptPassword(user.getPassword());
	         user.setPassword(encryptedPassword);
	         session.save(user); 
	         String SQL = "UPDATE Role SET role_members = role_members + 1 WHERE id = :id";
	         NativeQuery actualizacion = session.createSQLQuery(SQL);
	         actualizacion.setParameter("id", user.getRole().getId());
	         actualizacion.executeUpdate();
	         tx.commit();
	         String hql = "SELECT id FROM User WHERE UPPER(email) = :email";
	         Query query = session.createQuery(hql);
	         query.setParameter("email", user.getEmail().trim().toUpperCase());
	         objectID = Integer.parseInt(query.getSingleResult().toString());
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	      return objectID;
	}
	
	// --------------- METHOD TO EDIT AN EXISTING USER -------------------- //
	
	public void updateUser(Integer userID, User user) {
		
		Session session = factory.openSession();
	    Transaction tx = null;
	      
	      try {
	         tx = session.beginTransaction();
	         User object = (User) session.get(User.class, userID); 
	         
	         if(object.getRole().getId() != user.getRole().getId()) {
	        	 
	        	 String SQL = "UPDATE Role SET role_members = role_members - 1 WHERE id = :id";
		         NativeQuery actualizacion = session.createSQLQuery(SQL);
		         actualizacion.setParameter("id", object.getRole().getId());
		         actualizacion.executeUpdate();
		         
		         SQL = "UPDATE Role SET role_members = role_members + 1 WHERE id = :id";
		         actualizacion = session.createSQLQuery(SQL);
		         actualizacion.setParameter("id", user.getRole().getId());
		         actualizacion.executeUpdate();
	         }
	         
	         object.setRole(user.getRole());
	         object.setFullName(user.getFullName().trim());
	         object.setEmail(user.getEmail().trim());
	         object.setPhoneNumber(user.getPhoneNumber().trim());
	         if(user.getAddress() != null) object.setAddress(user.getAddress().trim());
	         if(user.getProfilePic() != null) object.setProfilePic(user.getProfilePic());
			 session.update(object); 
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	}
	
	// --------------- METHOD TO DELETE AN EXISTING USER -------------------- //
	
	public void deleteUser(Integer userID) {
		
		Session session = factory.openSession();
	    Transaction tx = null;
	      
	      try {
	         tx = session.beginTransaction();
	         User object = (User) session.get(User.class, userID); 
	         String SQL = "UPDATE Role SET role_members = role_members - 1 WHERE id = :id";
	         NativeQuery actualizacion = session.createSQLQuery(SQL);
	         actualizacion.setParameter("id", object.getRole().getId());
	         actualizacion.executeUpdate();
	         session.delete(object); 
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	      
	}

}
