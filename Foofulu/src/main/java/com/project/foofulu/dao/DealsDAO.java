package com.project.foofulu.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.ocpsoft.prettytime.units.Day;

import com.project.foofulu.models.Bussiness;
import com.project.foofulu.models.BussinessCategories;
import com.project.foofulu.models.BussinessDays;
import com.project.foofulu.models.BussinessImages;
import com.project.foofulu.models.Days;
import com.project.foofulu.models.DealDays;
import com.project.foofulu.models.DealImages;
import com.project.foofulu.models.Deals;
import com.project.foofulu.models.DealsCategories;
import com.project.foofulu.models.FavouriteBussiness;
import com.project.foofulu.models.MealCategories;
import com.project.foofulu.models.VerifiedDeals;

public class DealsDAO {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	public Deals addDeals(Deals objDeals) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    long id=(Long)session.save(objDeals);
			Deals objDeals2 = (Deals) session.load(Deals.class, id);
			tx.commit();
			return objDeals2;
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
	
	public int updateDeals(Deals objDeals) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    session.update(objDeals);
			tx.commit();
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			
			if (tx!=null){
				tx.rollback();
			}
			return 0;
		}finally {
	      session.close();
	    }
	}
	
	public Deals getDealById(long id) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    Deals objDeals2 = (Deals) session.load(Deals.class, id);
			tx.commit();
			return objDeals2;
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
	
	public DealImages addDealImages(DealImages objDealImages) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    long id=(Long)session.save(objDealImages);
		    DealImages objDealImages1= (DealImages) session.load(DealImages.class, id);
			tx.commit();
			return objDealImages1;
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
	
	public int updateDealImages(DealImages objDealImages) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    session.update(objDealImages);
		   tx.commit();
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			
			if (tx!=null){
				tx.rollback();
			}
			return 0;
		}finally {
	      session.close();
	    }
	}
	
	public DealImages getDealImagesById(long id) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    DealImages objDealImages2 = (DealImages) session.load(DealImages.class, id);
			tx.commit();
			return objDealImages2;
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
	
	public List<DealImages> getDealImagesByDeals(long dealId) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    @SuppressWarnings("unchecked")
			List<DealImages> objDealImages2 = session.createQuery("from DealImages where deal = "+dealId).list();
			tx.commit();
			return objDealImages2;
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
	
	public int deleteDealImages(DealImages dealImages) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    session.delete(dealImages);
			tx.commit();
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			
			if (tx!=null){
				tx.rollback();
			}
			return 0;
		}finally {
	      session.close();
	    }
	}
	
	public DealDays addDealDays(DealDays objDealdays) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    long id=(Long)session.save(objDealdays);
		    DealDays objDealDays1= (DealDays) session.load(DealDays.class, id);
			tx.commit();
			return objDealDays1;
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
	
	public DealsCategories addDealsCategories(DealsCategories objDealsCategories) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    long id=(Long)session.save(objDealsCategories);
		    DealsCategories objDealsCategories1= (DealsCategories) session.load(DealsCategories.class, id);
			tx.commit();
			return objDealsCategories1;
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
	
	public MealCategories addMealCategories(MealCategories objMealCategories) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    long id=(Long)session.save(objMealCategories);
		    MealCategories objMealCategories1= (MealCategories) session.load(MealCategories.class, id);
			tx.commit();
			return objMealCategories1;
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
	
	public List<Deals> getDealsByBussiness(long id) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    @SuppressWarnings("unchecked")
			List<Deals> deals = session.createQuery("from Deals where status=1 and bussiness = "+id).list();
		    tx.commit();
			return deals;
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
	
	public List<Deals> getDealsByUser(long id) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    @SuppressWarnings("unchecked")
			List<Deals> deals = session.createQuery("from Deals where addedBy = "+id).list();
		    tx.commit();
			return deals;
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
	
	public List<Deals> getDealsByUserAndBusiness(long userId,long businessId) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    @SuppressWarnings("unchecked")
			List<Deals> deals = session.createQuery("from Deals where addedBy = "+userId+" AND "
		    		+ "bussiness = "+businessId).list();
		    tx.commit();
			return deals;
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
	
	public Bussiness addBussiness(Bussiness objBussiness) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    long id=(Long)session.save(objBussiness);
		    Bussiness objBussiness1= (Bussiness) session.load(Bussiness.class, id);
			tx.commit();
			return objBussiness1;
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
	
	public List<Bussiness> getBussinessList() {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    List<Bussiness> bussinesses = session.createQuery("from Bussiness").list();
		    tx.commit();
			return bussinesses;
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
	
	public Bussiness getBussinessByBussinesId(String id) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    Bussiness objBussiness1= (Bussiness) session.createQuery("from Bussiness where bussinessId = '"+id+"'").uniqueResult();
			tx.commit();
			return objBussiness1;
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
	
	public Bussiness getBussinessById(long id) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    Bussiness objBussiness1= (Bussiness) session.load(Bussiness.class, id);
			tx.commit();
			return objBussiness1;
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
	
	public BussinessCategories addBussinessCategories(BussinessCategories objBussinessCategories) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    long id=(Long)session.save(objBussinessCategories);
		    BussinessCategories objBussinessCategories1= (BussinessCategories) session.load(BussinessCategories.class, id);
			tx.commit();
			return objBussinessCategories1;
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
	
	public List<BussinessCategories> getBussinessCategories(Bussiness objBussiness) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    @SuppressWarnings("unchecked")
			List<BussinessCategories> bussinessCategories = session.createQuery("from BussinessCategories where bussiness = "+
		    objBussiness.getId()).list();
		    tx.commit();
			return bussinessCategories;
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
	
	public BussinessDays addBussinessDays(BussinessDays objBussinessDays) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    long id=(Long)session.save(objBussinessDays);
		    BussinessDays objBussinessDays1= (BussinessDays) session.load(BussinessDays.class, id);
			tx.commit();
			return objBussinessDays1;
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
	
	public List<BussinessDays> getBussinessDays(Bussiness objBussiness) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    @SuppressWarnings("unchecked")
			List<BussinessDays> bussinessDays = session.createQuery("from BussinessDays where bussiness = "+
		    objBussiness.getId()+" order by id").list();
		    tx.commit();
			return bussinessDays;
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
	
	public BussinessImages addBussinessImages(BussinessImages objBussinessImages) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    long id=(Long)session.save(objBussinessImages);
		    BussinessImages objBussinessImages1= (BussinessImages) session.load(BussinessImages.class, id);
			tx.commit();
			return objBussinessImages1;
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
	
	public List<BussinessImages> getBussinessImages(Bussiness objBussiness) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    @SuppressWarnings("unchecked")
			List<BussinessImages> bussinessImages = session.createQuery("from BussinessImages where bussiness = "+
		    objBussiness.getId()).list();
		    tx.commit();
			return bussinessImages;
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
	
	public List<BussinessImages> getBussinessImagesById(long id) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    @SuppressWarnings("unchecked")
			List<BussinessImages> bussinessImages = session.createQuery("from BussinessImages where bussiness = "+id).list();
		    tx.commit();
			return bussinessImages;
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
	
	public BussinessImages getBussinessImageById(long id) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    BussinessImages objBussiness1= (BussinessImages) session.load(BussinessImages.class, id);
			tx.commit();
			return objBussiness1;
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
			
	public Days getDayById(int id) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    Days objDay2 = (Days) session.load(Days.class, id);
			tx.commit();
			return objDay2;
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
	
	public List<Days> getDays() {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    @SuppressWarnings("unchecked")
			List<Days> objDay2 = session.createQuery("from Days").list();
			tx.commit();
			return objDay2;
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
	
	public List<MealCategories> getCategories() {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    @SuppressWarnings("unchecked")
			List<MealCategories> objMealCategories2 = session.createQuery("from MealCategories").list();
			tx.commit();
			return objMealCategories2;
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
	
	public List<Bussiness> getBussiness(String condition) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    @SuppressWarnings("unchecked")
			List<Bussiness> bussinesses = session.createQuery("from Bussiness "+condition).list();
		    tx.commit();
			return bussinesses;
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
	
	public List<Object> getBussinessesByNativeQuery(String condition) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    
		    
		    String qry ="select b.id from bussiness b inner join deals d on d.bussiness=b.id "+condition;
		    System.out.println("Query is >>>> "+qry);
		    @SuppressWarnings("unchecked")
			List<Object> bussinesses = session.createSQLQuery(qry).list();
		    tx.commit();
			return bussinesses;
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
	
	public List<Deals> getDealsList(long id) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    List<Deals> deals = session.createQuery("from Deals where bussiness=" +id).list();
		    tx.commit();
			return deals;
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
	
	public List<Deals> getDealsByTitle(String text) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    @SuppressWarnings("unchecked")
			List<Deals> deals = session.createQuery("from Deals where upper(title) like '%"+text+"%'").list();
		    tx.commit();
			return deals;
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
	
	public List<Deals> searchDealsByBussinessAndText(long id,String text) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    @SuppressWarnings("unchecked")
			List<Deals> deals = session.createQuery("from Deals where status=1 and bussiness = "+id).list();
		    tx.commit();
			return deals;
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
	
	public List<DealDays> getDays(long id) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    @SuppressWarnings("unchecked")
			List<DealDays> dealDays = session.createQuery("from DealDays where deal = "+id).list();
		    tx.commit();
			return dealDays;
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
	
	public List<DealDays> getDaysByBussinesAndDay(long id,int dayId) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    @SuppressWarnings("unchecked")
			List<DealDays> dealDays = session.createQuery("from DealDays where deal.status=1 and deal.bussiness = "+id+" and "
					+ "day="+dayId).list();
		    tx.commit();
			return dealDays;
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
	
	public DealDays getDaysbyDealAndDay(long dealsId,int day) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		   DealDays dealDays = (DealDays) session.createQuery("from DealDays where  deal = "+dealsId+" AND deal.status=1 AND day = "+day).uniqueResult();
		    tx.commit();
			return dealDays;
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
	
	public int deleteDays(DealDays dealDays) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    session.delete(dealDays);
		    tx.commit();
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			
			if (tx!=null){
				tx.rollback();
			}
			return 0;
		}finally {
	      session.close();
	    }
	}
	
	public List<DealsCategories> getDealsCategories(long id) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    @SuppressWarnings("unchecked")
			List<DealsCategories> dealsCategories = session.createQuery("from DealsCategories where deal = "+id).list();
		    tx.commit();
			return dealsCategories;
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
	
	public DealsCategories getDealsCategoriesByDealIDAndCategory(long id,int category) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    DealsCategories dealsCategories = (DealsCategories) session.createQuery("from DealsCategories where deal = "+id+" And mealCategories="+category).uniqueResult();
		    tx.commit();
			return dealsCategories;
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
	
	public DealDays getDealsbyDealAndDay(long dealsId,int day) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    DealDays dealDays = (DealDays) session.createQuery("from DealDays where deal = "+dealsId+" AND day = "+day).uniqueResult();
		    tx.commit();
			return dealDays;
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
	
	public int deleteDealsCategories(DealsCategories dealsCategories) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    session.delete(dealsCategories);
		    tx.commit();
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			
			if (tx!=null){
				tx.rollback();
			}
			return 0;
		}finally {
	      session.close();
	    }
	}
	
	public MealCategories getMealById(int id) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    MealCategories objMealCategories2 = (MealCategories) session.load(MealCategories.class, id);
			tx.commit();
			return objMealCategories2;
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
	
	public VerifiedDeals addVerifiedDeal(VerifiedDeals objVerifiedDeals) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    long id=(Long)session.save(objVerifiedDeals);
		    VerifiedDeals objVerifiedDeals1= (VerifiedDeals) session.load(VerifiedDeals.class, id);
			tx.commit();
			return objVerifiedDeals1;
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
	
	public int deleteVerifiedDeal(VerifiedDeals objVerifiedDeals) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    session.delete(objVerifiedDeals);
		    tx.commit();
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			
			if (tx!=null){
				tx.rollback();
			}
			return 0;
		}finally {
	      session.close();
	    }
	}
	
	public VerifiedDeals getVerifiedDeals(long dealsId,long userId) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    VerifiedDeals VerifiedDeals = (VerifiedDeals) session.createQuery("from VerifiedDeals where deal = "+dealsId+" AND user = "+userId).uniqueResult();
		    tx.commit();
			return VerifiedDeals;
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
	
	public List<VerifiedDeals> getVerifiedDealsByDeal(long dealsId) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    @SuppressWarnings("unchecked")
			List<VerifiedDeals> VerifiedDeals =session.createQuery("from VerifiedDeals where deal = "+dealsId+" Order By id").list();
		    tx.commit();
			return VerifiedDeals;
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
	
	public FavouriteBussiness addFavouriteBussiness(FavouriteBussiness objFavouriteBussiness) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    long id=(Long)session.save(objFavouriteBussiness);
		    FavouriteBussiness objFavouriteBussiness1= (FavouriteBussiness) session.load(FavouriteBussiness.class, id);
			tx.commit();
			return objFavouriteBussiness1;
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
	
	public int deleteFavouriteBussiness(FavouriteBussiness objFavouriteBussiness) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    session.delete(objFavouriteBussiness);
		    tx.commit();
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			
			if (tx!=null){
				tx.rollback();
			}
			return 0;
		}finally {
	      session.close();
	    }
	}
	
	public List<FavouriteBussiness> getFavouriteBussinessByUser(long userId) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    List<FavouriteBussiness> favouriteBussiness =session.createQuery("from FavouriteBussiness where user = "+userId).list();
		    tx.commit();
			return favouriteBussiness;
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
	
	public FavouriteBussiness getFavouriteBussinessByUserAndBussiness(long userId,long bussinessId) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    FavouriteBussiness favouriteBussiness =(FavouriteBussiness) session.createQuery("from FavouriteBussiness where user = "+userId+" AND bussiness = "+bussinessId).uniqueResult();
		    tx.commit();
			return favouriteBussiness;
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
	
	public List<DealImages> getDealImagebyid(long id) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    List<DealImages> dealImages = session.createQuery("from DealImages where deal = "+id).list();
		    tx.commit();
			return dealImages;
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
	
	public List<DealsCategories> getDealsCategoriesbyid(long id) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    @SuppressWarnings("unchecked")
			List<DealsCategories> dealsCategories = session.createQuery("from DealsCategories where deal = "+id).list();
		    tx.commit();
			return dealsCategories;
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
	
	public Bussiness getBussinessThroughBussinesId(long id) {
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
		    tx = session.beginTransaction();
		    Bussiness objBussiness1= (Bussiness) session.createQuery("from Bussiness where id = '"+id+"'").uniqueResult();
			tx.commit();
			return objBussiness1;
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