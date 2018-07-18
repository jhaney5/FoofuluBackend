package com.project.foofulu.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value="/ViewCalls")
public class ViewCalls {
	
	@RequestMapping(value = "/dashboardPage", method = RequestMethod.GET)
	public String dashboardPage(Model model) {
		return "login/admin/dashboard";
	}
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "login/login";
	}
	@RequestMapping(value = "/usersPage", method = RequestMethod.GET)
	public String usersPage(Model model) {
		return "login/admin/users";
	}
	@RequestMapping(value = "/userDetailPage", method = RequestMethod.GET)
	public String userDetailPage(Model model) {
		return "login/admin/userDetail";
	}
	@RequestMapping(value = "/bussinessPage", method = RequestMethod.GET)
	public String bussinessPage(Model model) {
		return "login/admin/bussiness";
	}
	@RequestMapping(value = "/bussinessDetailsPage", method = RequestMethod.GET)
	public String bussinessDetailsPage(Model model) {
		return "login/admin/bussinessDetails";
	}
	@RequestMapping(value = "/dealDetailPage", method = RequestMethod.GET)
	public String dealDetailPage(Model model) {
		return "login/admin/dealDetail";
	}
	@RequestMapping(value = "/AboutUsPage", method = RequestMethod.GET)
	public String aboutUsPage(Model model) {
		return "login/admin/AboutUs";
	}
	
	@RequestMapping(value = "/profilePage", method = RequestMethod.GET)
	public String profilePage(Model model) {
		return "login/admin/profile";
	}
	
	@RequestMapping(value = "/changePasswordPage", method = RequestMethod.GET)
	public String changePasswordPage(Model model) {
		return "login/admin/changePassword";
	}
	
	@RequestMapping(value = "/my_accountPage", method = RequestMethod.GET)
	public String my_accountPage(Model model) {
		return "login/admin/my_account";
	}
	
	@RequestMapping(value = "/update_profilePage", method = RequestMethod.GET)
	public String update_profilePage(Model model) {
		return "login/admin/update_profile";
	}
	
	@RequestMapping(value = "/DealSharingPage", method = RequestMethod.GET)
	public String DealSharingPage(Model model) {
		return "login/DealSharing";
	}
	
	@RequestMapping(value = "/AppVersionPage", method = RequestMethod.GET)
	public String AppVerisonPage(Model model) {
		return "login/admin/AppVersion";
	}
}