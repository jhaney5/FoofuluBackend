package com.project.foofulu.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.project.foofulu.dao.DealsDAO;
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

public class DealsServices {
	
	private DealsDAO dealsDAO;

	public void setDealsDAO(DealsDAO dealsDAO) {
		this.dealsDAO = dealsDAO;
	}
	
	@Transactional
	public Deals addDeals(Deals objDeals) {
		return this.dealsDAO.addDeals(objDeals);
	}
	
	@Transactional
	public int updateDeals(Deals objDeals) {
		return this.dealsDAO.updateDeals(objDeals);
	}
	
	@Transactional
	public Deals getDealById(long id) {
		return this.dealsDAO.getDealById(id);
	}
	
	@Transactional
	public List<Deals> getDealsByUser(long id) {
		return this.dealsDAO.getDealsByUser(id);
	}
	
	@Transactional
	public List<Deals> getDealsByUserAndBusiness(long userId,long businessId) {
		return this.dealsDAO.getDealsByUserAndBusiness(userId, businessId);
	}
	
	@Transactional
	public DealImages getDealImagesById(long id) {
		return this.dealsDAO.getDealImagesById(id);
	}
	
	@Transactional
	public List<DealImages> getDealImagesByDeals(long dealId) {
		return this.dealsDAO.getDealImagesByDeals(dealId);
	}
	
	@Transactional
	public int deleteDealImages(DealImages dealImages) {
		return this.dealsDAO.deleteDealImages(dealImages);
	}
	
	@Transactional
	public DealImages addDealImages(DealImages objDealImages) {
		return this.dealsDAO.addDealImages(objDealImages);
	}
	
	@Transactional
	public int updateDealImages(DealImages objDealImages) {
		return this.dealsDAO.updateDealImages(objDealImages);
	}
	
	@Transactional
	public List<Deals> getDealsByTitle(String text) {
		return this.dealsDAO.getDealsByTitle(text);
	}
	
	@Transactional
	public DealDays addDealDays(DealDays objDealdays) {
		return this.dealsDAO.addDealDays(objDealdays);
	}
	
	@Transactional
	public DealsCategories addDealsCategories(DealsCategories objDealsCategories) {
		return this.dealsDAO.addDealsCategories(objDealsCategories);
	}
	@Transactional
	public MealCategories addMealCategories(MealCategories objMealCategories) {
		return this.dealsDAO.addMealCategories(objMealCategories);
	}
	
	@Transactional
	public Bussiness addBussiness(Bussiness objBussiness) {
		return this.dealsDAO.addBussiness(objBussiness);
	}
	
	@Transactional
	public Bussiness getBussinessByBussinesId(String id) {
		return this.dealsDAO.getBussinessByBussinesId(id);
	}
	
	@Transactional
	public Bussiness getBussinessById(long id) {
		return this.dealsDAO.getBussinessById(id);
	}
	
	@Transactional
	public List<Bussiness> getBussinessList() {
		return this.dealsDAO.getBussinessList();
	}
	
	@Transactional
	public BussinessCategories addBussinessCategories(BussinessCategories objBussinessCategories) {
		return this.dealsDAO.addBussinessCategories(objBussinessCategories);
	}
	
	@Transactional
	public List<BussinessCategories> getBussinessCategories(Bussiness objBussiness) {
		return this.dealsDAO.getBussinessCategories(objBussiness);
	}
	@Transactional
	public BussinessDays addBussinessDays(BussinessDays objBussinessDays) {
		return this.dealsDAO.addBussinessDays(objBussinessDays);
	}
	
	@Transactional
	public List<BussinessDays> getBussinessDays(Bussiness objBussiness) {
		return this.dealsDAO.getBussinessDays(objBussiness);
	}
	@Transactional
	public BussinessImages addBussinessImages(BussinessImages objBussinessImages) {
		return this.dealsDAO.addBussinessImages(objBussinessImages);
	}
	
	@Transactional
	public List<BussinessImages> getBussinessImages(Bussiness objBussiness) {
		return this.dealsDAO.getBussinessImages(objBussiness);
	}
	
	@Transactional
	public List<BussinessImages> getBussinessImagesById(long id) {
		return this.dealsDAO.getBussinessImagesById(id);
	}
	
	@Transactional
	public BussinessImages getBussinessImageById(long id) {
		return this.dealsDAO.getBussinessImageById(id);
	}
	
	@Transactional
	public Days getDayById(int id) {
		return this.dealsDAO.getDayById(id);
	}
	
	@Transactional
	public List<Days> getDays() {
		return this.dealsDAO.getDays();
	}
	
	@Transactional
	public List<MealCategories> getCategories() {
		return this.dealsDAO.getCategories();
	}
	
	@Transactional
	public List<DealDays> getDays(long id) {
		return this.dealsDAO.getDays(id);
	}
	
	@Transactional
	public List<DealDays> getDaysByBussinesAndDay(long id,int dayId) {
		return this.dealsDAO.getDaysByBussinesAndDay(id,dayId);
	}
	
	@Transactional
	public List<Deals> getDealsList(long id) {
		return this.dealsDAO.getDealsList(id);
	}
	
	@Transactional
	public int deleteDays(DealDays dealDays) {
		return this.dealsDAO.deleteDays(dealDays);
	}
	
	@Transactional
	public int deleteDealsCategories(DealsCategories dealsCategories) {
		return this.dealsDAO.deleteDealsCategories(dealsCategories);
	}
	
	@Transactional
	public DealDays getDaysbyDealAndDay(long dealsId,int day) {
		return this.dealsDAO.getDaysbyDealAndDay(dealsId, day);
	}
	
	@Transactional
	public List<DealsCategories> getDealsCategories(long id) {
		return this.dealsDAO.getDealsCategories(id);
	}	
	
	@Transactional
	public DealsCategories getDealsCategoriesByDealIDAndCategory(long id,int category) {
		return this.dealsDAO.getDealsCategoriesByDealIDAndCategory(id, category);
	}

	@Transactional
	public MealCategories getMealById(int id) {
		return this.dealsDAO.getMealById(id);
	}
	
	@Transactional
	public DealDays getDealsbyDealAndDay(long dealsId,int day) {
		return this.dealsDAO.getDealsbyDealAndDay(dealsId, day);
	}
	
	@Transactional
	public List<DealsCategories> getDealsCategoriesbyid(long id){
		return this.dealsDAO.getDealsCategories(id);
	}
	
	@Transactional
	public List<Bussiness> getBussiness(String condition) {
		return this.dealsDAO.getBussiness(condition);
	}
	
	public List<Object> getBussinessesByNativeQuery(String condition) {
		return this.dealsDAO.getBussinessesByNativeQuery(condition);
	}
	
	@Transactional
	public List<Deals> getDealsByBussiness(long id) {
		return this.dealsDAO.getDealsByBussiness(id);
	}
	
	@Transactional
	public List<Deals> searchDealsByBussinessAndText(long id,String text) {
		return this.dealsDAO.searchDealsByBussinessAndText(id, text);
	}
	
	@Transactional
	public VerifiedDeals addVerifiedDeal(VerifiedDeals objVerifiedDeals) {
		return this.dealsDAO.addVerifiedDeal(objVerifiedDeals);
	}
	
	@Transactional
	public VerifiedDeals getVerifiedDeals(long dealsId,long userId) {
		return this.dealsDAO.getVerifiedDeals(dealsId, userId);
	}
	
	@Transactional
	public List<VerifiedDeals> getVerifiedDealsByDeal(long dealsId) {
		return this.dealsDAO.getVerifiedDealsByDeal(dealsId);
	}
	
	@Transactional
	public int deleteVerifiedDeal(VerifiedDeals objVerifiedDeals) {
		return this.dealsDAO.deleteVerifiedDeal(objVerifiedDeals);
	}
	
	@Transactional
	public List<FavouriteBussiness> getFavouriteBussinessByUser(long userId) {
		return this.dealsDAO.getFavouriteBussinessByUser(userId);
	}
	
	@Transactional
	public int deleteFavouriteBussiness(FavouriteBussiness objFavouriteBussiness) {
		return this.dealsDAO.deleteFavouriteBussiness(objFavouriteBussiness);
	}
	
	@Transactional
	public FavouriteBussiness addFavouriteBussiness(FavouriteBussiness objFavouriteBussiness) {
		return this.dealsDAO.addFavouriteBussiness(objFavouriteBussiness);
	}	
	
	@Transactional
	public FavouriteBussiness getFavouriteBussinessByUserAndBussiness(long userId,long bussinessId) {
		return this.dealsDAO.getFavouriteBussinessByUserAndBussiness(userId, bussinessId);
	}
	
	@Transactional
	public List<DealImages> getDealImagebyid(long id) {
		return this.dealsDAO.getDealImagebyid(id);
	}
	
	@Transactional
	public Bussiness getBussinessThroughBussinesId(long id) {
		return this.dealsDAO.getBussinessThroughBussinesId(id);
	}
}
