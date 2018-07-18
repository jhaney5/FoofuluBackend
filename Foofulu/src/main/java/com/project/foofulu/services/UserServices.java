package com.project.foofulu.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.project.foofulu.dao.UsersDAO;
import com.project.foofulu.models.AppVersion;
import com.project.foofulu.models.ContactUs;
import com.project.foofulu.models.Roles;
import com.project.foofulu.models.Users;
import com.project.foofulu.models.UsersLogs;


public class UserServices {
	private UsersDAO dao;

	public void setDao(UsersDAO dao) {
		this.dao = dao;
	}
	@Transactional
	public Users checkAuthentication(String email, String password){
		return this.dao.checkAuthentication(email,password);
	}
	@Transactional
	public List<Users> getAdmin() {
		return this.dao.getAdmin();
	}
	@Transactional
	public List<Users> getUsersList(){
		return this.dao.getUsersList();
	}
	@Transactional
	public Users addUsers(Users objUsers) {
		return this.dao.addUsers(objUsers);
	}
	@Transactional
	public Users updateUsers(Users objUsers) {
		return this.dao.updateUsers(objUsers);
	}
	
	@Transactional	
	public Users getUserById(long id) {
	return this.dao.getUserById(id);
	}	
	@Transactional
	public Users checkSecrteKey(String secretKey) {
		return this.dao.checkSecrteKey(secretKey);
	}
	
	@Transactional
	public Users checkEmail(String email) {
		return this.dao.checkEmail(email);
	}
	
	@Transactional
	public Roles getRoleById(int id) {
		return this.dao.getRoleById(id);
	}
	
	@Transactional
	public Users checkSocialId(String socialId) {
		return this.dao.checkSocialId(socialId);
	}
	
	@Transactional
	public UsersLogs addUsersLogs(UsersLogs objUsersLogs) {
		return this.dao.addUsersLogs(objUsersLogs);
	}
	
	@Transactional
	public List<UsersLogs> getUsersList(String date1 , String date2){
		return this.dao.getUsersList(date1, date2);
	}
	
	@Transactional
	public ContactUs addContactUs(ContactUs objContactUs) {
		return this.dao.addContactUs(objContactUs);
	}
	
	@Transactional
	public AppVersion getAppVersion(String deviceType){
		return this.dao.getAppVersion(deviceType);
	}

	@Transactional
	public AppVersion updateAppVersion(AppVersion appVersion){
		return this.dao.updateAppVersion(appVersion);
	}
}
