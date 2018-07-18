package com.project.foofulu.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.foofulu.models.AppVersion;
import com.project.foofulu.models.Bussiness;
import com.project.foofulu.models.BussinessImages;
import com.project.foofulu.models.DealDays;
import com.project.foofulu.models.DealImages;
import com.project.foofulu.models.Deals;
import com.project.foofulu.models.Users;
import com.project.foofulu.models.UsersLogs;
import com.project.foofulu.models.VerifiedDeals;
import com.project.foofulu.services.DealsServices;
import com.project.foofulu.services.UserServices;

@Controller
public class AdminController {

	private UserServices userServices;
	private DealsServices dealsServices;
	
	@Autowired(required = true)
	@Qualifier(value = "dealsServices")
	public void setDealservices(DealsServices dealsServices) {
		this.dealsServices = dealsServices;
	}

	@Autowired(required = true)
	@Qualifier(value = "userServices")
	public void setUserServices(UserServices userServices) {
		this.userServices = userServices;
	}
	
	
	@RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
	public String login(Model model, HttpServletRequest request) {
		List<Users> listUsers = userServices.getAdmin();
		try{
			if(listUsers.isEmpty()){
				Users objUsers = new Users();
				objUsers.setStatus(true);
				objUsers.setEmail("admin@gmail.com");
				objUsers.setName("Admin");
				objUsers.setPassword("admin");
				objUsers.setRole(userServices.getRoleById(1));
				userServices.addUsers(objUsers);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "login/index";
	}
	
	@RequestMapping(value = "/checkAuthentication", method = RequestMethod.POST)
	@ResponseBody 
	public Users checkAuthentication(@RequestBody String jsonStr, HttpServletRequest request) {
		try{
			HttpSession session = request.getSession();
			JSONObject jsonObject = new JSONObject(jsonStr);
			String email = jsonObject.getString("email");
		    String password = jsonObject.getString("password");
		    Users i = userServices.checkAuthentication(email,password);
		    if(i != null){
		    	if(i.getEmail().equalsIgnoreCase(email) && i.getPassword().equalsIgnoreCase(password)){
		    		session.setAttribute("user", i.getId());
					session.setAttribute("role", i.getRole().getId());
					session.setAttribute("obj", i);
					return i;
		    	}else{
		    		return null;
				}
		    }else{
		    	return null;
		    }
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public void logout(HttpServletRequest request) throws ParseException {
		HttpSession session = request.getSession();
		session.removeAttribute("role");
		session.removeAttribute("user");
		session.removeAttribute("obj");
		session.invalidate();
	}
		
	@RequestMapping(value = "updateSystemUser", method = RequestMethod.POST)
	@ResponseBody
	public Users updateSystemUser(@RequestBody Users user, HttpServletRequest request) {
		try{
			Users users=userServices.updateUsers(user);
			return users;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/getUsersList", method = RequestMethod.POST)
	@ResponseBody
	public List<Users> getUsersList() throws JsonGenerationException, JsonMappingException, IOException, NumberFormatException, JSONException {
		Map<String, Object> objMap = new HashMap<String,Object>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<Users> list = userServices.getUsersList();
			return list;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/getBussinessList", method = RequestMethod.POST)
	@ResponseBody
	public String getBussinessList() throws JsonGenerationException, JsonMappingException, IOException, NumberFormatException, JSONException {
		Map<String, Object> objMap1 = new HashMap<String,Object>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<Map<String ,Object>> listBusinesses = new ArrayList<Map<String,Object>>();
			List<Bussiness> list = dealsServices.getBussinessList();
			List<Map<String ,Object>> listImage = new ArrayList<Map<String,Object>>();
			for(Bussiness list1: list){
				Map<String, Object> objBusiness = new HashMap<String, Object>();
				objBusiness.put("id", list1.getId());
				objBusiness.put("address", list1.getLocation());
				objBusiness.put("name",list1.getName());
				objBusiness.put("lat",list1.getLatitude());
				objBusiness.put("long", list1.getLongitude());
				objBusiness.put("rating", list1.getRating());
				
				
				List<BussinessImages> bussinessImages= dealsServices.getBussinessImagesById(list1.getId());
				List<Map<String ,Object>> imageList = new ArrayList<Map<String,Object>>();
				for (BussinessImages bussinessImages2 : bussinessImages) {
					Map<String, Object> obj2Image = new HashMap<String, Object>();
					obj2Image.put("image", bussinessImages2.getImage());
					obj2Image.put("id", bussinessImages2.getId());
					imageList.add(obj2Image);
				}
				
				objBusiness.put("images", imageList);
				listBusinesses.add(objBusiness);
			}
			objMap1.put("businesses",listBusinesses);
			String jsonInString = mapper.writeValueAsString(objMap1);
			return jsonInString;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/get_Users", method = RequestMethod.POST)
	@ResponseBody 
	public String usersList() throws JSONException, JsonGenerationException, JsonMappingException, IOException {
		Map<String, Object> objMap = new HashMap<String,Object>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			////////////////////////////// This Week
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, -30);
			Date dateBefore30Days = cal.getTime();
			System.out.println("Date before 30 days : "+dateBefore30Days);	
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(date);
			cal1.add(Calendar.DATE, -6);
			Date dateBefore7Days = cal1.getTime();
			System.out.println("Date before 7 days : "+dateBefore7Days);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			Date frmDate = format.parse(format1.format(new Date()) + " 00:00:00");
			List<UsersLogs> usersLogsToday = userServices.getUsersList(format.format(frmDate), format.format(new Date()));
			List<UsersLogs> usersLogs7Days = userServices.getUsersList(format.format(dateBefore7Days), format.format(new Date()));
			List<UsersLogs> usersLogs30Days = userServices.getUsersList(format.format(dateBefore30Days), format.format(new Date()));
			List<Users> registeredUsers = userServices.getUsersList();
			objMap.put("sevendays", usersLogs7Days.size());
			objMap.put("today", usersLogsToday.size());
			objMap.put("thirtydays", usersLogs30Days.size());
			objMap.put("registeredUsers", registeredUsers.size());
			objMap.put("status", 1);
			String jsonInString = mapper.writeValueAsString(objMap);
			return jsonInString;
		} catch (Exception e) {
			e.printStackTrace();
			objMap.put("message", "Something went wrong");
			objMap.put("status", 0);
			String jsonInString = mapper.writeValueAsString(objMap);
			return jsonInString;
		}
	}	
	
	@RequestMapping(value = "disableUser", method = RequestMethod.POST)
	@ResponseBody
	public Users disableUser(@RequestBody String user, HttpServletRequest request) {
		try{
			System.out.println("Controller called");
			JSONObject jsonObject = new JSONObject(user);
			Users users=userServices.getUserById(Integer.parseInt(jsonObject.getString("id")));
			users.setStatus( Boolean.parseBoolean(jsonObject.getString("status")));
			System.out.println(jsonObject.getString("status"));
			Users users1= userServices.updateUsers(users);
			return users;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "getAdminProfile", method = RequestMethod.POST)
	@ResponseBody
	public Users getAdminProfile(@RequestBody  String id, HttpServletRequest request) {
		try{
			JSONObject jsonObject = new JSONObject(id);
			Users users=userServices.getUserById(Integer.parseInt(jsonObject.getString("id")));
			return users;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "getUserProfile", method = RequestMethod.POST)
	@ResponseBody
	public Users getUserProfile(@RequestBody  String id, HttpServletRequest request) {
		try{
			JSONObject jsonObject = new JSONObject(id);
			Users users=userServices.getUserById(Integer.parseInt(jsonObject.getString("id")));
			System.out.println("User Profile///////////////" + id);
			return users;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "editUser", method = RequestMethod.POST)
	@ResponseBody
	public Users editUser(@RequestPart(value = "objUser") String  user, @RequestPart(value = "file", required = false) MultipartFile file,HttpServletRequest request) {
		try{
			JSONObject jsonObject = new JSONObject(user);
			Users users=userServices.getUserById(Integer.parseInt(jsonObject.getString("id")));
			System.out.println("User Information :::::"+users.getId());
			users.setName(jsonObject.getString("name"));
			users.setEmail(jsonObject.getString("email"));
			Users j =userServices.updateUsers(users);	
			Users users1=null;
			if(j!= null){
				users1=userServices.getUserById(Integer.parseInt(jsonObject.getString("id")));
			}
			if(file!=null && users1!=null){
				String test1 = request.getSession().getServletContext().getRealPath("");
				String dir = test1 + "/resources/usersImages/"+users1.getId() + "/";
				if(!new File(dir).exists()){
					new File(dir).mkdirs();
				}else{
				      String[] myFiles = new File(dir).list();
		              for (int i=0; i<myFiles.length; i++) {
		                  File myFile = new File(new File(dir), myFiles[i]); 
		                  myFile.delete();
		              }
				}
				String fileName = file.getOriginalFilename();
				String ext = FilenameUtils.getExtension(fileName);
				fileName = getFileName()+"."+ext;
				InputStream fileContent = file.getInputStream();
				OutputStream outputStream = new FileOutputStream(new File(dir+"/"+fileName));
				int read = 0;
				byte[] bytes = new byte[1024];
				while ((read = fileContent.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}
				String n = "resources/usersImages/"+users1.getId() + "/";
				users1.setImage(n+fileName);
				userServices.updateUsers(users1);
				outputStream.close();
				fileContent.close();
			}
			return users1;	
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	@RequestMapping(value = "/getDealsListData", method = RequestMethod.POST)
	@ResponseBody
	public String getDealsListData(@RequestBody  String id, HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException, NumberFormatException, JSONException {
		Map<String, Object> objMap1 = new HashMap<String,Object>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			JSONObject jsonObject = new JSONObject(id);
			List<Map<String ,Object>> listDeals = new ArrayList<Map<String,Object>>();
			List<Deals> list = dealsServices.getDealsList(Integer.parseInt(jsonObject.getString("id")));
			for(Deals list1: list){
				Map<String, Object> objDeals =  new HashMap<String, Object>();
				objDeals.put("id", list1.getId());
				objDeals.put("name",list1.getTitle());
				objDeals.put("addedBy", list1.getAddedBy());
				objDeals.put("updatedBy", list1.getUpdatedBy());
				objDeals.put("creationTime", list1.getCreationTime());
				
				List<DealImages> dealImages= dealsServices.getDealImagebyid(list1.getId());
				List<Map<String ,Object>> imageList = new ArrayList<Map<String,Object>>();
				for (DealImages dealsImages2 : dealImages) {
					Map<String, Object> obj2Image = new HashMap<String, Object>();
					obj2Image.put("image", dealsImages2.getImage());
					obj2Image.put("id", dealsImages2.getId());
					imageList.add(obj2Image);
				}
				
				List<DealDays>  dealDays = dealsServices.getDays(list1.getId());
				List<Map<String ,Object>> daysList = new ArrayList<Map<String,Object>>();
				for (DealDays dealDays2 : dealDays) {
					Map<String, Object> objDay = new HashMap<String, Object>();
					objDay.put("day", dealDays2.getDay().getDay());
					objDay.put("id", dealDays2.getDay().getId());
					daysList.add(objDay);
				}
				objDeals.put("days", daysList);
				objDeals.put("images", imageList);
				listDeals.add(objDeals);
			}
			objMap1.put("dealData",listDeals);
			String jsonInString = mapper.writeValueAsString(objMap1);
			return jsonInString;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "getImageAndBussiness", method = RequestMethod.POST)
	@ResponseBody
	public String getImageAndBussiness(@RequestBody  String id, HttpServletRequest request) {
		Map<String, Object> objMap = new HashMap<String,Object>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			JSONObject jsonObject= new JSONObject(id);
			Bussiness bussiness= dealsServices.getBussinessThroughBussinesId(Long.parseLong(jsonObject.getString("id")));
			System.out.println(bussiness.getId());
			
			List<BussinessImages> bussinessImages= dealsServices.getBussinessImagesById(bussiness.getId());
			List<Map<String ,Object>> imageList = new ArrayList<Map<String,Object>>();
			for (BussinessImages bussinessImages2 : bussinessImages) {
				Map<String, Object> objImage = new HashMap<String, Object>();
				objImage.put("image", bussinessImages2.getImage());
				objImage.put("id", bussinessImages2.getId());
				imageList.add(objImage);
			}
			objMap.put("image",imageList);
			objMap.put("title",bussiness.getBussinessId());
			objMap.put("address",bussiness.getLocation());
			String jsonInString = mapper.writeValueAsString(objMap);
			return jsonInString;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "getDealsByBussiness", method = RequestMethod.POST)
	@ResponseBody
	public String getDealsByBussiness(@RequestBody  String id, HttpServletRequest request) {
		Map<String, Object> objMap = new HashMap<String,Object>();
		ObjectMapper mapper = new ObjectMapper();
		try{
			JSONObject jsonObject = new JSONObject(id);
			Deals deals= dealsServices.getDealById(Long.parseLong(jsonObject.getString("id")));

			List<DealDays>  dealDays = dealsServices.getDays(deals.getId());
			List<Map<String ,Object>> daysList = new ArrayList<Map<String,Object>>();
			for (DealDays dealDays2 : dealDays) {
				Map<String, Object> objDay = new HashMap<String, Object>();
				objDay.put("day", dealDays2.getDay().getDay());
				objDay.put("id", dealDays2.getDay().getId());
				daysList.add(objDay);
			}
			PrettyTime prettyTime = new PrettyTime();
			Map<String, Object> objAddedBy = new HashMap<String, Object>();
			objAddedBy.put("name", deals.getAddedBy().getName());
			objAddedBy.put("id", deals.getAddedBy().getId());
			objAddedBy.put("time", prettyTime.format(deals.getCreationTime()));
			
			Map<String, Object> objupdatedBy = new HashMap<String, Object>();
			if(deals.getUpdatedBy() != null){
				objupdatedBy.put("name", deals.getUpdatedBy().getName());
				objupdatedBy.put("id", deals.getUpdatedBy().getId());
				objupdatedBy.put("time", prettyTime.format(deals.getUpdationTime()));
			}
			
			List<VerifiedDeals> verifiedDeals = dealsServices.getVerifiedDealsByDeal(deals.getId());
			if(!verifiedDeals.isEmpty()){	
				objMap.put("vrified", verifiedDeals.size());
			}
			objMap.put("updatedBy", objupdatedBy);
			objMap.put("objAddedBy", objAddedBy);
			objMap.put("days", daysList);
			objMap.put("title",deals.getTitle());
			String jsonInString = mapper.writeValueAsString(objMap);
			return jsonInString;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	@RequestMapping(value = "getAppVersion", method = RequestMethod.GET)
	public ResponseEntity<String> getAppVersion(HttpServletRequest request) {
		Map<String, Object> objMap = new HashMap<String,Object>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			AppVersion appVersion =userServices.getAppVersion("IOS");
			objMap.put("appVersion",appVersion);
			String jsonInString = mapper.writeValueAsString(objMap);
			return new ResponseEntity<String>(jsonInString, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "updateAppVersion", method = RequestMethod.POST)
	public ResponseEntity<String> updateAppVersion(@RequestBody AppVersion appVersion , HttpServletRequest request) {
		Map<String, Object> objMap = new HashMap<String,Object>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			AppVersion appVersion1 =userServices.updateAppVersion(appVersion);
			objMap.put("appVersion",appVersion1);
			objMap.put("message","Version updated successfully.");
			String jsonInString = mapper.writeValueAsString(objMap);
			return new ResponseEntity<String>(jsonInString, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getFileName(){
		String CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int RANDOM_STRING_LENGTH = 10;
		StringBuffer randStr = new StringBuffer();
		for (int i = 0; i < RANDOM_STRING_LENGTH; i++) {
			int number;
			int randomInt = 0;
			Random randomGenerator = new Random();
			randomInt = randomGenerator.nextInt(52);
			if (randomInt - 1 == -1) {
				number = randomInt;
			} else {
				number = randomInt - 1;
			}
			char ch = CHAR_LIST.charAt(number);
			randStr.append(ch);
		}
		return randStr.toString();
	}
}