package com.project.foofulu.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.project.foofulu.models.AppVersion;
import com.project.foofulu.models.ContactUs;
import com.project.foofulu.models.Roles;
import com.project.foofulu.models.Users;
import com.project.foofulu.models.UsersLogs;

public class UsersDAO {
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	public Users checkAuthentication(String username, String password) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Query query = session.createQuery("from Users where email=:username and password=:password");
			query.setParameter("username", username);
			query.setParameter("password", password);
			Users p = (Users) query.uniqueResult();
			tx.commit();
			return p;
		}catch(Exception e){
			e.printStackTrace();
			if (tx!=null){
				tx.rollback(); 
			}
			return null;
		}finally {
	      session.close();
	    }
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> getAdmin() {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			List<Users> list = session.createQuery("from Users where role=1").list();
			tx.commit();
			return list;
		}catch(Exception e){
			e.printStackTrace();
			if (tx!=null){
				tx.rollback();
			}
			return null;
		}finally {
	      session.close();
	    }
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> getUsersList() {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			List<Users> list = session.createQuery("from Users where role=2").list();
			tx.commit();
			return list;
		}catch(Exception e){
			e.printStackTrace();
			if (tx!=null){
				tx.rollback();
			}
			return null;
		}finally {
	      session.close();
	    }
	}
	public Users getUserByfacebookId(String facebookId) {
		Session session = null;
		Transaction tx = null;
		Users user = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
				System.out.println("Email id :::::: Null");
				user =(Users) session.createQuery("from Users where facebookId = '"+facebookId+"'").uniqueResult();
			tx.commit();
			return user;
		}catch(Exception e){
			e.printStackTrace();
			if (tx!=null){
				tx.rollback();
			}
			return null;
		}finally {
	      session.close();
	    }
	}
	public Users addUsers(Users objUsers) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    long id=(Long)session.save(objUsers);
			Users objUsers2 = (Users) session.load(Users.class, id);
			tx.commit();
			return objUsers2;
		}catch(Exception e){
			e.printStackTrace();
			
			if (tx!=null){
				tx.rollback();
			}
			return null;
		}finally {
	      session.close();
	    }
	}
	public Users updateUsers(Users objUsers) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();			
			session.update(objUsers);
			tx.commit();
			return objUsers;
		}catch(Exception e){
			e.printStackTrace();
			if (tx!=null){
				tx.rollback();
			}
				return null;
			}finally {
		      session.close();
		    }
	}
	
	public Users getUserById(long id) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    Users p = (Users) session.load(Users.class, id);
		    tx.commit();
		    return p;
		}catch(Exception e){
			e.printStackTrace();
			if (tx!=null){
				tx.rollback();
			}
			return null;
		}finally {
	      session.close();
	    }
	}
	
	public Users checkSecrteKey(String secretKey) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Users p = (Users) session.createQuery("from Users where secrateKey = '"+secretKey+"'").uniqueResult();
			tx.commit();
			return p;
		}catch(Exception e){
			e.printStackTrace();
			if (tx!=null){
				tx.rollback();
			}
			return null;
		}finally {
	      session.close();
	    }
	}
	public Roles getRoleById(int id) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    Roles p = (Roles) session.load(Roles.class, id);
		    tx.commit();
		    return p;
		}catch(Exception e){
			e.printStackTrace();
			if (tx!=null){
				tx.rollback();
			}
			return null;
		}finally {
	      session.close();
	    }
	}
	public Users checkEmail(String email) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Query query= session.createQuery("from Users where email=:email");
			query.setParameter("email", email);
			Users p = (Users) query.uniqueResult();
			tx.commit();
			return p;
		}catch(Exception e){
			e.printStackTrace();
			if (tx!=null){
				tx.rollback();
			}
			return null;
		}finally {
	      session.close();
	    }
	}
	
	public Users checkSocialId(String socialId) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Query query= session.createQuery("from Users where socialId = '"+socialId+"'");
			Users p = (Users) query.uniqueResult();
			tx.commit();
			return p;
		}catch(Exception e){
			e.printStackTrace();
			if (tx!=null){
				tx.rollback();
			}
			return null;
		}finally {
	      session.close();
	    }
	}
	
	public UsersLogs addUsersLogs(UsersLogs objUsersLogs) {
		Session session = null;
		Transaction tx = null;
		long i=1;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    i=(Long)session.save(objUsersLogs);
//			Users p = (Users) session.load(Users.class, i);
			tx.commit();
			return objUsersLogs;
		}catch(Exception e){
			e.printStackTrace();
			
			if (tx!=null){
				tx.rollback();
			}
			return null;
		}finally {
	      session.close();
	    }
	}
	
	@SuppressWarnings("unchecked")
	public List<UsersLogs> getUsersList(String date1 , String date2)
	{
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
			List<UsersLogs> list = session.createQuery("from UsersLogs where onTime Between '"+date1 +"' AND '"+date2 +"' Group By user").list();
			tx.commit();
			return list;
		}catch(Exception e){
			e.printStackTrace();
			if (tx!=null){
				tx.rollback();
			}
			return null;
		}finally {
	      session.close();
	    }
	}
	
	public ContactUs addContactUs(ContactUs objContactUs) {
		Session session = null;
		Transaction tx = null;
		
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    long i=(Long)session.save(objContactUs);
		    ContactUs p = (ContactUs) session.load(ContactUs.class, i);
			tx.commit();
			return p;
		}catch(Exception e){
			e.printStackTrace();
			
			if (tx!=null){
				tx.rollback();
			}
			return null;
		}finally {
	      session.close();
	    }
	}
	
	public AppVersion getAppVersion(String deviceType){
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
			AppVersion appVersion = (AppVersion)session.createQuery("from AppVersion where deviceType= '"+deviceType+"'").uniqueResult();
			tx.commit();
			return appVersion;
		}catch(Exception e){
			e.printStackTrace();
			if (tx!=null){
				tx.rollback();
			}
			return null;
		}finally {
	      session.close();
	    }
	}
	
	public AppVersion updateAppVersion(AppVersion appVersion){
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
			AppVersion appVersion1 = (AppVersion)session.merge(appVersion);
			tx.commit();
			return appVersion1;
		}catch(Exception e){
			e.printStackTrace();
			if (tx!=null){
				tx.rollback();
			}
			return null;
		}finally {
	      session.close();
	    }
	}
}
