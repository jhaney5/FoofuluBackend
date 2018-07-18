package com.project.foofulu.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.foofulu.models.AppVersion;
import com.project.foofulu.models.Bussiness;
import com.project.foofulu.models.BussinessCategories;
import com.project.foofulu.models.BussinessDays;
import com.project.foofulu.models.BussinessImages;
import com.project.foofulu.models.Deals;
import com.project.foofulu.models.FavouriteBussiness;
import com.project.foofulu.models.Roles;
import com.project.foofulu.models.Users;
import com.project.foofulu.models.UsersLogs;
import com.project.foofulu.services.DealsServices;
import com.project.foofulu.services.UserServices;
import com.project.foofulu.utils.Commons;
import com.project.foofulu.models.*;
@Controller
public class UserController {
	
	private UserServices userServices;
	
	@Autowired(required = true)
	@Qualifier(value = "userServices")
	public void setUserServices(UserServices userServices) {
		this.userServices = userServices;
	}
	
	private DealsServices dealsServices;
	
	@Autowired(required = true)
	@Qualifier(value = "dealsServices")
	public void setDealsServices(DealsServices dealsServices) {
		this.dealsServices = dealsServices;
	}
	
	/*********** User Signup API ***********/
	/*** JSON Object 
	 *{
  		"name": "Demo ABC","password": "123456",
  		"email": "demo@gmail.com",
  		"deviceId" : "dsfvd54vdsfsv","deviceType": "ds5gvd7v5"
	}
	***/
	@RequestMapping(value = "signUp", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<String> signUp(@RequestBody String jsonStr ,
			@RequestHeader("version") String version ,
			@RequestHeader("deviceType") String deviceType ,
			HttpServletRequest request) 
			throws JSONException, JsonGenerationException, JsonMappingException, IOException {
		
		Map<String, Object> objMap = new HashMap<String,Object>();
		ObjectMapper mapper = new ObjectMapper();
		
		try{
			AppVersion appVersion = userServices.getAppVersion(deviceType);
			if(appVersion.getVersion().equals(version)){
				System.out.println("JSON Response >>>>>>>"+jsonStr);
				JSONObject jsonObject = new JSONObject(jsonStr);
				
				/*** Email check if account already exist or not ***/
				Users objUser= userServices.checkEmail(jsonObject.getString("email"));
				
				if(objUser!= null){
					/*** Account status check ***/	
					if (objUser.isStatus()) {
						objMap.put("message", "Email already registered with us.");
						String jsonInString = mapper.writeValueAsString(objMap);
						return new ResponseEntity<String>(jsonInString,HttpStatus.NOT_ACCEPTABLE);	
					}else{
						objMap.put("message", "Your account has been disabled temporarily."
								+ "Please contact at support@foofulu.com");
						String jsonInString = mapper.writeValueAsString(objMap);
						return new ResponseEntity<String>(jsonInString,HttpStatus.LOCKED);
					}
				}else{
					Roles roles = userServices.getRoleById(2);
					Users users = new Users();
					users.setCreationTime(new Date());
					users.setName(jsonObject.getString("name"));
					users.setRole(roles);
					users.setSendNotifications(true);
					users.setEmail(jsonObject.getString("email"));
					users.setPassword(jsonObject.getString("password"));
					users.setLoginType("App");
					users.setStatus(true);
					users.setDeviceId(jsonObject.getString("deviceId"));
					users.setDeviceType(jsonObject.getString("deviceType"));
					
					SecureRandom random = new SecureRandom();
					users.setSecrateKey(new BigInteger(130, random).toString(32));
					users.setCreationTime(new Date());
					
					Users user =userServices.addUsers(users);
							
					UsersLogs usersLogs = new UsersLogs();
					usersLogs.setLogin(true);
					usersLogs.setLogout(false);
					usersLogs.setOnTime(new Date());
					usersLogs.setUser(user);
					userServices.addUsersLogs(usersLogs);
					
					objMap.put("message", "Your are successfully registered with us.");
					objMap.put("secretKey", user.getSecrateKey());
					String jsonInString = mapper.writeValueAsString(objMap);
					return new ResponseEntity<String>(jsonInString,HttpStatus.OK);	
				}
			}else{
				objMap.put("message", "You are using an outdated app. Please update it to newer version.");
				objMap.put("results","");
				String jsonInString = mapper.writeValueAsString(objMap);
				return new ResponseEntity<String>(jsonInString,HttpStatus.BAD_REQUEST);	
			}
		}catch(Exception e){
			e.printStackTrace();
			objMap.put("message", e.getMessage());
			String jsonInString = mapper.writeValueAsString(objMap);
			return new ResponseEntity<String>(jsonInString,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/*********** Social login API ***********/
	/*** Required Parameters 
	 * {	"name": "Demo ABC",
	  		"email": "demo@gmail.com", "socialId" : "356826fdsg87",
	  		"deviceId" : "dsfvd54vdsfsv","deviceType": "ds5gvd7v5",
			"loginType": "Android" } 
	  
	 ***/
	@RequestMapping(value = "socialLogin", method = RequestMethod.POST,
			produces = "application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<String> socialLogin(@RequestBody String jsonStr ,
			@RequestHeader("version") String version ,
			@RequestHeader("deviceType") String deviceType ,
			HttpServletRequest request)
			throws JSONException, JsonGenerationException, JsonMappingException, IOException {
		
		Map<String, Object> objMap = new HashMap<String,Object>();
		ObjectMapper mapper = new ObjectMapper();
		try{
			AppVersion appVersion = userServices.getAppVersion(deviceType);
			if(appVersion.getVersion().equals(version)){
				Users objUser1 =null;
				JSONObject jsonObject = new JSONObject(jsonStr);
		
				/*** Social Account Check ***/
				Users objUser= userServices.checkSocialId(jsonObject.getString("socialId"));
				
				/*** Email Check ***/
				if(jsonObject.has("email")){
					objUser1= userServices.checkEmail(jsonObject.getString("email"));	
				}
				
				if(objUser1 == null){
					if(objUser == null){
						Roles roles = userServices.getRoleById(2);
						Users users = new Users();
						users.setSocialId(jsonObject.getString("socialId"));
						users.setLoginType(jsonObject.getString("loginType"));
						users.setDeviceId(jsonObject.getString("deviceId"));
						users.setDeviceType(jsonObject.getString("deviceType"));
						users.setRole(roles);
						users.setStatus(true);
						users.setSendNotifications(true);
				
						if(jsonObject.has("email")){
							users.setEmail(jsonObject.getString("email"));	
						}
						
						if(jsonObject.has("name")){
							users.setName(jsonObject.getString("name"));
						}
						
						SecureRandom random = new SecureRandom();
						users.setSecrateKey(new BigInteger(130, random).toString(32));
						users.setCreationTime(new Date());
						Users users1 = userServices.addUsers(users);
						
						/*** User Login Logs ***/
						UsersLogs usersLogs = new UsersLogs();
						usersLogs.setLogin(true);
						usersLogs.setLogout(false);
						usersLogs.setOnTime(new Date());
						usersLogs.setUser(users1);
						userServices.addUsersLogs(usersLogs);
						
						/*** If registered successfully else 
						 * something goes wrong while saving user's information ***/
						if(users1 != null){
							objMap.put("message", "Your are successfully registered with us.");
							objMap.put("secretKey", users1.getSecrateKey());
							objMap.put("isSignup", false );
							String jsonInString = mapper.writeValueAsString(objMap);
							return new ResponseEntity<String>(jsonInString,HttpStatus.OK);	
							
						}else{
							objMap.put("message", "Somthing went wrong.");
							String jsonInString = mapper.writeValueAsString(objMap);
							return new ResponseEntity<String>(jsonInString,HttpStatus.INTERNAL_SERVER_ERROR);	
						}
						
					}else{
						/***Account Status Check***/
						if(objUser.isStatus()){
					
							if(jsonObject.has("name")){
								objUser.setName(jsonObject.getString("name"));
							}
							
							SecureRandom random = new SecureRandom();
							objUser.setSecrateKey(new BigInteger(130, random).toString(32));
							
							objUser.setDeviceId(jsonObject.getString("deviceId"));
							objUser.setDeviceType(jsonObject.getString("deviceType"));
	
							Users users=userServices.updateUsers(objUser);
							
							UsersLogs usersLogs = new UsersLogs();
							usersLogs.setLogin(true);
							usersLogs.setLogout(false);
							usersLogs.setOnTime(new Date());
							usersLogs.setUser(users);
							userServices.addUsersLogs(usersLogs);
							
							if(users != null){
								objMap.put("message", "You are logged in successfully.");
								objMap.put("secretKey", users.getSecrateKey());
								objMap.put("isSignup", true );
								String jsonInString = mapper.writeValueAsString(objMap);
								return new ResponseEntity<String>(jsonInString,HttpStatus.OK);	
							}else{
								objMap.put("message", "Somthing went wrong.");
								String jsonInString = mapper.writeValueAsString(objMap);
								return new ResponseEntity<String>(jsonInString,HttpStatus.INTERNAL_SERVER_ERROR);	
							}
						}else{
							objMap.put("message", "Your account has been disabled temporarily."
									+ "Please contact at support@foofulu.com");
							String jsonInString = mapper.writeValueAsString(objMap);
							return new ResponseEntity<String>(jsonInString,HttpStatus.NOT_ACCEPTABLE);	
						}
					}
					
				}else{
					if(objUser1.isStatus()){
						objUser1.setSocialId(jsonObject.getString("socialId"));
						objUser1.setLoginType(jsonObject.getString("loginType"));
						objUser1.setDeviceId(jsonObject.getString("deviceId"));
						objUser1.setDeviceType(jsonObject.getString("deviceType"));
											
						SecureRandom random = new SecureRandom();
						objUser1.setSecrateKey(new BigInteger(130, random).toString(32));
						Users users=userServices.updateUsers(objUser1);
						
						UsersLogs usersLogs = new UsersLogs();
						usersLogs.setLogin(true);
						usersLogs.setLogout(false);
						usersLogs.setOnTime(new Date());
						usersLogs.setUser(users);
						userServices.addUsersLogs(usersLogs);
						
						if(users != null){
							objMap.put("message", "You are logged in successfully.");
							objMap.put("secretKey", users.getSecrateKey());
							objMap.put("isSignup", true );
							String jsonInString = mapper.writeValueAsString(objMap);
							return new ResponseEntity<String>(jsonInString,HttpStatus.OK);	
						}else{
							objMap.put("message", "Somthing went wrong.");
							String jsonInString = mapper.writeValueAsString(objMap);
							return new ResponseEntity<String>(jsonInString,HttpStatus.INTERNAL_SERVER_ERROR);	
						}
					
					}else{
						objMap.put("message", "Your account has been disabled temporarily."
								+ "Please contact at support@foofulu.com");
						String jsonInString = mapper.writeValueAsString(objMap);
						return new ResponseEntity<String>(jsonInString,HttpStatus.NOT_ACCEPTABLE);	
					}
				}
			}else{
				objMap.put("message", "You are using an outdated app. Please update it to newer version.");
				objMap.put("results","");
				String jsonInString = mapper.writeValueAsString(objMap);
				return new ResponseEntity<String>(jsonInString,HttpStatus.BAD_REQUEST);	
			}
			
		}catch(Exception e){
			e.printStackTrace();
			objMap.put("message", e.getMessage());
			String jsonInString = mapper.writeValueAsString(objMap);
			return new ResponseEntity<String>(jsonInString,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/*********** Login API ***********/
	/*** {
			"email" : "demo@gmail.com",
			"password" : "123456",
			"deviceId" : "dsfvd54vdsfsv","deviceType": "ds5gvd7v5"
		}
	***/
	@RequestMapping(value = "login", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<String> login(@RequestBody String jsonStr ,
			@RequestHeader("version") String version ,
			@RequestHeader("deviceType") String deviceType ,
			HttpServletRequest request)
			throws JSONException, JsonGenerationException, JsonMappingException, IOException {
		
		Map<String, Object> objMap = new HashMap<String,Object>();
		ObjectMapper mapper = new ObjectMapper();
		try{
			AppVersion appVersion = userServices.getAppVersion(deviceType);
			if(appVersion.getVersion().equals(version)){
				JSONObject jsonObject = new JSONObject(jsonStr);
				Users objUser1 =userServices.checkEmail(jsonObject.getString("email"));
				Users objUser =userServices.checkAuthentication(jsonObject.getString("email"), 
						jsonObject.getString("password"));
				if(objUser1 != null){
					if(objUser != null){
						if(objUser.isStatus()){
							SecureRandom random = new SecureRandom();
							objUser.setSecrateKey(new BigInteger(130, random).toString(32));
							objUser.setDeviceId(jsonObject.getString("deviceId"));
							objUser.setDeviceType(jsonObject.getString("deviceType"));
							userServices.updateUsers(objUser);
								 
							UsersLogs usersLogs = new UsersLogs();
							usersLogs.setLogin(true);
							usersLogs.setLogout(false);
							usersLogs.setOnTime(new Date());
							usersLogs.setUser(objUser);
							userServices.addUsersLogs(usersLogs);
								
							 objMap.put("message", "Login successfully.");
							 objMap.put("secretKey", objUser.getSecrateKey());
							 String jsonInString = mapper.writeValueAsString(objMap);
							 return new ResponseEntity<String>(jsonInString,HttpStatus.OK);
						}else{
							objMap.put("message", "Your account has been disabled temporarily."
											+ "Please contact at support@wherewedrink.com");
							String jsonInString = mapper.writeValueAsString(objMap);
								 return new ResponseEntity<String>(jsonInString,HttpStatus.LOCKED);
						}
					}else{
						objMap.put("message", "Oops! That password does not seem to work.");
						String jsonInString = mapper.writeValueAsString(objMap);
						return new ResponseEntity<String>(jsonInString,HttpStatus.NOT_ACCEPTABLE);
					}
				
				}else{
					objMap.put("message", "We can not seem to find an account with that email.");
					String jsonInString = mapper.writeValueAsString(objMap);
					return new ResponseEntity<String>(jsonInString,HttpStatus.NOT_ACCEPTABLE);
				}
			}else{
				objMap.put("message", "You are using an outdated app. Please update it to newer version.");
				objMap.put("results","");
				String jsonInString = mapper.writeValueAsString(objMap);
				return new ResponseEntity<String>(jsonInString,HttpStatus.BAD_REQUEST);	
			}	
		}catch(Exception e){
			e.printStackTrace();
			objMap.put("message", e.getMessage());
			String jsonInString = mapper.writeValueAsString(objMap);
			return new ResponseEntity<String>(jsonInString,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = "userLogout", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public ResponseEntity<String> userLogout(@RequestHeader("secretKey") String secretKey,
			@RequestHeader("version") String version ,
			@RequestHeader("deviceType") String deviceType ) throws JSONException, JsonGenerationException, JsonMappingException, IOException {
		Map<String, Object> objMap = new HashMap<String,Object>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			AppVersion appVersion = userServices.getAppVersion(deviceType);
			if(appVersion.getVersion().equals(version)){
				Users objUsers = userServices.checkSecrteKey(secretKey);
				
				if(objUsers!=null){
					objUsers.setSecrateKey(null);
					objUsers.setDeviceId(null);
					Users objUsers2 = userServices.updateUsers(objUsers);
					if(objUsers2 != null){
						objMap.put("message", "Logged Out Successfully !!!");
						String jsonInString = mapper.writeValueAsString(objMap);
			    		return new ResponseEntity<String>(jsonInString, HttpStatus.OK);
					}else{
						objMap.put("message", "Something went wrong !!!");
						String jsonInString = mapper.writeValueAsString(objMap);
			    		return new ResponseEntity<String>(jsonInString, HttpStatus.INTERNAL_SERVER_ERROR);
					}
				}else{
					if(!secretKey.equals(Commons.secretString)){
						objMap.put("message", "Session has been expired. Please login again to procced.");
						String jsonInString = mapper.writeValueAsString(objMap);
						return new ResponseEntity<String>(jsonInString,HttpStatus.UNAUTHORIZED);
					}else{
						objMap.put("message", "You need to sign up to post a deal.");
						String jsonInString = mapper.writeValueAsString(objMap);
						return new ResponseEntity<String>(jsonInString,HttpStatus.LOCKED);
					}
				}
			}else{
				objMap.put("message", "You are using an outdated app. Please update it to newer version.");
				objMap.put("results","");
				String jsonInString = mapper.writeValueAsString(objMap);
				return new ResponseEntity<String>(jsonInString,HttpStatus.BAD_REQUEST);	
			}	
		} catch (Exception e) {
			e.printStackTrace();
			objMap.put("message", "Something went wrong !!!");
			String jsonInString = mapper.writeValueAsString(objMap);
    		return new ResponseEntity<String>(jsonInString, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*********** Get Profile API ***********/
	/*****
	 {
	 "latitude" : ,
	 "longitude" : 
	 }
	 * *****/
	@RequestMapping(value = "updateProfile", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<String> updateProfile(@RequestHeader("secretKey") String secretKey,
			@RequestParam(value="name",required=false) String name,
			@RequestPart(value="file",required=false) MultipartFile file,
			@RequestHeader("version") String version ,
			@RequestHeader("deviceType") String deviceType ,
			HttpServletRequest request) 
			throws JSONException, JsonGenerationException, JsonMappingException, IOException {
		Map<String, Object> objMap = new HashMap<String,Object>();
		ObjectMapper mapper = new ObjectMapper();
		try{
			AppVersion appVersion = userServices.getAppVersion(deviceType);
			if(appVersion.getVersion().equals(version)){
				Users objUser= userServices.checkSecrteKey(secretKey);
				
				if(objUser == null){
					if(!secretKey.equals(Commons.secretString)){
						objMap.put("message", "Session has been expired. Please login again to procced.");
						String jsonInString = mapper.writeValueAsString(objMap);
						return new ResponseEntity<String>(jsonInString,HttpStatus.UNAUTHORIZED);
					}else{
						objMap.put("message", "You need to sign up to post a deal.");
						String jsonInString = mapper.writeValueAsString(objMap);
						return new ResponseEntity<String>(jsonInString,HttpStatus.LOCKED);
					}	
				}else{
					if (name != null) {
						objUser.setName(name);
					}
					if(file != null){
						String test1 = request.getSession().getServletContext().getRealPath("");
						String dir = test1 + "/resources/userImages/"+objUser.getId() + "/";
						if(!new File(dir).exists()){
							new File(dir).mkdirs();
						} else{
						      String[] myFiles = new File(dir).list();
				              for (int j=0; j<myFiles.length; j++) {
				                  File myFile = new File(new File(dir), myFiles[j]); 
				                  myFile.delete();
				              }
						}
						
						String fileName = file.getOriginalFilename();
						String ext = FilenameUtils.getExtension(fileName);
						fileName = Commons.getFileName()+"."+ext;
						
						InputStream fileContent = file.getInputStream();
						OutputStream outputStream = new FileOutputStream(new File(dir+"/"+fileName));
						int read = 0;
						byte[] bytes = new byte[1024];
						while ((read = fileContent.read(bytes)) != -1) {
							outputStream.write(bytes, 0, read);
						}
						
						String n = "/resources/userImages/"+objUser.getId() + "/";
						n= n+fileName;
						objUser.setImage(n);
						outputStream.close();
						fileContent.close();
					}
					
					Users users = userServices.updateUsers(objUser);
				
					objMap.put("message", "Profile updated successfully.");
					String jsonInString = mapper.writeValueAsString(objMap);
					return new ResponseEntity<String>(jsonInString,HttpStatus.OK);
				}
			}else{
				objMap.put("message", "You are using an outdated app. Please update it to newer version.");
				objMap.put("results","");
				String jsonInString = mapper.writeValueAsString(objMap);
				return new ResponseEntity<String>(jsonInString,HttpStatus.BAD_REQUEST);	
			}	
		}catch(Exception e){
			e.printStackTrace();
			objMap.put("message", e.getMessage());
			String jsonInString = mapper.writeValueAsString(objMap);
			return new ResponseEntity<String>(jsonInString,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/*********** Get Profile API ***********/
	/*****
	 {
	 "latitude" : ,
	 "longitude" : 
	 }
	 * *****/
	@RequestMapping(value = "getProfile", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<String> getProfile(@RequestHeader("secretKey") String secretKey,
			@RequestHeader("version") String version ,
			@RequestHeader("deviceType") String deviceType ,
			@RequestBody String jsonStr , HttpServletRequest request) 
			throws JSONException, JsonGenerationException, JsonMappingException, IOException {
		Map<String, Object> objMap = new HashMap<String,Object>();
		ObjectMapper mapper = new ObjectMapper();
		try{
			AppVersion appVersion = userServices.getAppVersion(deviceType);
			if(appVersion.getVersion().equals(version)){
				Users objUser= userServices.checkSecrteKey(secretKey);
				
				if(objUser == null){
					if(!secretKey.equals(Commons.secretString)){
						objMap.put("message", "Session has been expired. Please login again to procced.");
						String jsonInString = mapper.writeValueAsString(objMap);
						return new ResponseEntity<String>(jsonInString,HttpStatus.UNAUTHORIZED);
					}else{
						objMap.put("message", "You need to sign up to post a deal.");
						String jsonInString = mapper.writeValueAsString(objMap);
						return new ResponseEntity<String>(jsonInString,HttpStatus.LOCKED);
					}	
				}else{
				
					/*** User Profile  ***/
					
					PrettyTime prettyTime = new PrettyTime();
					System.out.println("JSON Response >>>>>>>"+jsonStr);
					JSONObject jsonObject = new JSONObject(jsonStr);
					Map<String, Object> userObject = new HashMap<String,Object>();
					userObject.put("email", objUser.getEmail());
					userObject.put("name", objUser.getName());
					
					if(objUser.getName()!=null)
						userObject.put("firstChar", objUser.getName().charAt(0));
					else
						userObject.put("firstChar", "");
					
					userObject.put("image", objUser.getImage());
					userObject.put("notificationStatus", objUser.isSendNotifications());
					
					List<Map<String, Object>> bussinesses = new ArrayList<Map<String, Object>>();
					
					List<FavouriteBussiness>  favouriteBussinesses = dealsServices.getFavouriteBussinessByUser(objUser.getId());
					for (FavouriteBussiness favouriteBussiness : favouriteBussinesses) {
						Bussiness bussiness = favouriteBussiness.getBussiness();
						Map<String, Object> objBussiness = new HashMap<String,Object>();
						
			    		objBussiness.put("bussinessId",bussiness.getId());
						objBussiness.put("name",bussiness.getName());
						objBussiness.put("price",bussiness.getCurrency());
						objBussiness.put("phone",bussiness.getPhone());
						objBussiness.put("review_count",bussiness.getReviews());
						objBussiness.put("rating",bussiness.getRating());
						objBussiness.put("location",bussiness.getLocation());
									
						List<Map<String, Object>> categories1 = new ArrayList<Map<String, Object>>();
						List<BussinessCategories> categories = dealsServices.getBussinessCategories(bussiness);
						for (int i = 0; i < categories.size(); i++) {
							Map<String, Object> objCategories = new HashMap<String,Object>();
							objCategories.put("alias", categories.get(i).getAlias());
							objCategories.put("title", categories.get(i).getTitle());
							categories1.add(objCategories);
						}
						objBussiness.put("categories", categories1);
						
						Map<String, Object> objCordinates = new HashMap<String,Object>();
						objCordinates.put("latitude", bussiness.getLatitude());
						objCordinates.put("longitude",  bussiness.getLongitude());
						objBussiness.put("coordinates",objCordinates);
						
						Double distance2 =Commons.distFrom(jsonObject.getDouble("latitude"),
								jsonObject.getDouble("longitude"), bussiness.getLatitude(),
								bussiness.getLongitude())/1000;
						distance2 = 0.621*distance2;
						objBussiness.put("distance", distance2);
						
						List<String> images = new ArrayList<String>();
						List<BussinessImages> bussinessImages =  dealsServices.getBussinessImages(bussiness);
						for (int i = 0; i < bussinessImages.size(); i++) {
							images.add(bussinessImages.get(i).getImage());
						}
						objBussiness.put("photos", images);
						
						DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
						DateFormat dateFormat1 = new SimpleDateFormat("HH:mm");
						
						Date date = new Date();
						List<BussinessDays> bussinessDays = dealsServices.getBussinessDays(bussiness);
						for (BussinessDays bussinessDays2 : bussinessDays) {
							if(bussinessDays2.getDay() == date.getDay()){
								String end = bussinessDays2.getEnd();
								String start = bussinessDays2.getStart();
								Date endDate =dateFormat1.parse(end.substring(0, 2)+":"+end.substring(2));
								Date startDate =dateFormat1.parse(start.substring(0, 2)+":"+start.substring(2));
								Date today = dateFormat1.parse(dateFormat1.format(date));
							
								String timing = dateFormat.format(dateFormat1.parse(end.substring(0, 2)+":"+end.substring(2)));
								System.out.println("date sub string >>>> "+timing);
								objBussiness.put("closing", "untill "+timing);
								
								if(today.after(startDate) && today.before(endDate)){
									objBussiness.put("is_closed", false);
								}else{
									objBussiness.put("is_closed", true);
								}
								break;
							}
						}
						bussinesses.add(objBussiness);
					}
					
					Set<Bussiness> bussinessSet = new HashSet<Bussiness>();
					List<Deals> deals= dealsServices.getDealsByUser(objUser.getId());
					for (Deals deals2 : deals) {
						bussinessSet.add(deals2.getBussiness());
					}
					
					List<Bussiness> listBussiness = new ArrayList<Bussiness>(bussinessSet);
					List<Map<String, Object>> bussinessDeals = new ArrayList<Map<String, Object>>();
					for (Bussiness bussiness : listBussiness) {
						
						List<Deals> dealsList= dealsServices.getDealsByUserAndBusiness(objUser.getId(),
								bussiness.getId());
						List<Map<String, Object>> listDeals = new ArrayList<Map<String, Object>>();
						
						for (Deals deals3 : dealsList) {
							Map<String, Object> objDeals = new HashMap<String, Object>();	
							objDeals.put("dealId", deals3.getId());
							objDeals.put("dealTitle", deals3.getTitle());
							objDeals.put("addedBy", deals3.getAddedBy().getName());
							objDeals.put("time",prettyTime.format(deals3.getCreationTime()));
							
							List<Map<String, Object>> dealImagesList = new ArrayList<Map<String, Object>>();
							List<DealImages>  dealImages= dealsServices.getDealImagesByDeals(deals3.getId());
							for (DealImages dealImages2 : dealImages) {
								Map<String, Object> objDealImages = new HashMap<String, Object>();
								objDealImages.put("image",dealImages2.getImage());
								dealImagesList.add(objDealImages);
							}
							objDeals.put("dealImages", dealImagesList);
							listDeals.add(objDeals);
						}
						
						Map<String, Object> objBussiness = new HashMap<String, Object>();
						objBussiness.put("businessName",bussiness.getName());
						objBussiness.put("businessRating", bussiness.getRating());
						objBussiness.put("businesslocation", bussiness.getLocation());
						objBussiness.put("businessId",bussiness.getId());
						objBussiness.put("businessReviews", bussiness.getReviews());
						
						List<BussinessImages> bussinessImages = dealsServices.getBussinessImages(bussiness);
						List<Map<String, Object>> bussinessImagesList = new ArrayList<Map<String, Object>>();
						for (BussinessImages bussinessImages2 : bussinessImages) {
							Map<String, Object> objBussinessImages = new HashMap<String, Object>();
							objBussinessImages.put("image", bussinessImages2.getImage());
							bussinessImagesList.add(objBussinessImages);
						}
						objBussiness.put("businessImages", bussinessImagesList);
						objBussiness.put("deals", listDeals);
						bussinessDeals.add(objBussiness);
					}
					objMap.put("bussinesses", bussinesses);
					objMap.put("myDeals", bussinessDeals);
					objMap.put("user", userObject);
					objMap.put("message", "Profile fetched successfully.");
					String jsonInString = mapper.writeValueAsString(objMap);
					return new ResponseEntity<String>(jsonInString,HttpStatus.OK);
				}
			}else{
				objMap.put("message", "You are using an outdated app. Please update it to newer version.");
				objMap.put("results","");
				String jsonInString = mapper.writeValueAsString(objMap);
				return new ResponseEntity<String>(jsonInString,HttpStatus.BAD_REQUEST);	
			}	
		}catch(Exception e){
			e.printStackTrace();
			objMap.put("message", e.getMessage());
			String jsonInString = mapper.writeValueAsString(objMap);
			return new ResponseEntity<String>(jsonInString,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/*********** Get Other Profile API ***********/
	/*****
	   {
    	"userId":"1"
  	}
	 * *****/
	@RequestMapping(value = "getOtherProfile", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<String> getOtherProfile(@RequestHeader("secretKey") String secretKey ,
			@RequestHeader("version") String version ,
			@RequestHeader("deviceType") String deviceType ,
			@RequestBody String jsonStr , HttpServletRequest request) 
			throws JSONException, JsonGenerationException, JsonMappingException, IOException {
		Map<String, Object> objMap = new HashMap<String,Object>();
		ObjectMapper mapper = new ObjectMapper();
		try{
			AppVersion appVersion = userServices.getAppVersion(deviceType);
			if(appVersion.getVersion().equals(version)){
				boolean notRegistered=false;
				Users objUser= userServices.checkSecrteKey(secretKey);
				if(objUser == null){
					if(secretKey.equals(Commons.secretString))
							notRegistered=true;
				}
				if(objUser == null && !notRegistered){			
					objMap.put("message", "Session has been expired. Please login again to procced.");
					String jsonInString = mapper.writeValueAsString(objMap);
					return new ResponseEntity<String>(jsonInString,HttpStatus.UNAUTHORIZED);	
				}else{
				
					/*** User Profile  ***/
					
					PrettyTime prettyTime = new PrettyTime();
					System.out.println("JSON Response >>>>>>>"+jsonStr);
					JSONObject jsonObject = new JSONObject(jsonStr);
			
					Users user= userServices.getUserById(jsonObject.getLong("userId"));
					DateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
					
					Map<String, Object> userObject = new HashMap<String,Object>();
					userObject.put("email", user.getEmail());
					userObject.put("name", user.getName());
					
					if(user.getName()!=null)
						userObject.put("firstChar", user.getName().charAt(0));
					else
						userObject.put("firstChar", "");
					
					userObject.put("image", user.getImage());
					userObject.put("memberSince", dateFormat.format(user.getCreationTime()));
					userObject.put("notificationStatus", user.isSendNotifications());
					
					Set<Bussiness> bussinessSet = new HashSet<Bussiness>();
					List<Deals> deals= dealsServices.getDealsByUser(user.getId());
					for (Deals deals2 : deals) {
						bussinessSet.add(deals2.getBussiness());
					}
					
					List<Bussiness> listBussiness = new ArrayList<Bussiness>(bussinessSet);
					List<Map<String, Object>> bussinessDeals = new ArrayList<Map<String, Object>>();
					for (Bussiness bussiness : listBussiness) {
						
						List<Deals> dealsList= dealsServices.getDealsByUserAndBusiness(user.getId(),
								bussiness.getId());
						List<Map<String, Object>> listDeals = new ArrayList<Map<String, Object>>();
						
						for (Deals deals3 : dealsList) {
							Map<String, Object> objDeals = new HashMap<String, Object>();	
							objDeals.put("dealId", deals3.getId());
							objDeals.put("dealTitle", deals3.getTitle());
							objDeals.put("addedBy", deals3.getAddedBy().getName());
							objDeals.put("time",prettyTime.format(deals3.getCreationTime()));
							
							List<Map<String, Object>> dealImagesList = new ArrayList<Map<String, Object>>();
							List<DealImages>  dealImages= dealsServices.getDealImagesByDeals(deals3.getId());
							for (DealImages dealImages2 : dealImages) {
								Map<String, Object> objDealImages = new HashMap<String, Object>();
								objDealImages.put("image",dealImages2.getImage());
								dealImagesList.add(objDealImages);
							}
							objDeals.put("dealImages", dealImagesList);
							listDeals.add(objDeals);
						}
						
						Map<String, Object> objBussiness = new HashMap<String, Object>();
						objBussiness.put("businessName",bussiness.getName());
						objBussiness.put("businessRating", bussiness.getRating());
						objBussiness.put("businesslocation", bussiness.getLocation());
						objBussiness.put("businessId",bussiness.getId());
						objBussiness.put("businessReviews", bussiness.getReviews());
						
						List<BussinessImages> bussinessImages = dealsServices.getBussinessImages(bussiness);
						List<Map<String, Object>> bussinessImagesList = new ArrayList<Map<String, Object>>();
						for (BussinessImages bussinessImages2 : bussinessImages) {
							Map<String, Object> objBussinessImages = new HashMap<String, Object>();
							objBussinessImages.put("image", bussinessImages2.getImage());
							bussinessImagesList.add(objBussinessImages);
						}
						objBussiness.put("businessImages", bussinessImagesList);
						objBussiness.put("deals", listDeals);
						bussinessDeals.add(objBussiness);
					}
					
					objMap.put("myDeals", bussinessDeals);
					objMap.put("user", userObject);
					objMap.put("message", "Profile fetched successfully.");
					String jsonInString = mapper.writeValueAsString(objMap);
					return new ResponseEntity<String>(jsonInString,HttpStatus.OK);
				}
			}else{
				objMap.put("message", "You are using an outdated app. Please update it to newer version.");
				objMap.put("results","");
				String jsonInString = mapper.writeValueAsString(objMap);
				return new ResponseEntity<String>(jsonInString,HttpStatus.BAD_REQUEST);	
			}	
		}catch(Exception e){
			e.printStackTrace();
			objMap.put("message", e.getMessage());
			String jsonInString = mapper.writeValueAsString(objMap);
			return new ResponseEntity<String>(jsonInString,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "contactUs", method = RequestMethod.POST)
	@ResponseBody 
	public ResponseEntity<String> contactUs(@RequestHeader("secretKey") String secrateKey,
			@RequestHeader("version") String version ,
			@RequestHeader("deviceType") String deviceType ,
			@RequestBody String jsonStr, HttpServletRequest request
			) throws JSONException, JsonGenerationException, JsonMappingException, IOException {
		Map<String, Object> objMap = new HashMap<String,Object>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			AppVersion appVersion = userServices.getAppVersion(deviceType);
			if(appVersion.getVersion().equals(version)){
				System.out.println("JSON String >>> "+jsonStr);
				JSONObject jsonObject = new JSONObject(jsonStr);
				Users user2 = userServices.checkSecrteKey(secrateKey);
			
				if(user2!=null){
					
					ContactUs contactUs = new ContactUs();
					contactUs.setCreationTime(new Date());
					contactUs.setFeedback(jsonObject.getString("feedback"));
					contactUs.setSubject(jsonObject.getString("subject"));
					contactUs.setUser(user2);
					ContactUs contactUs2 =	userServices.addContactUs(contactUs);
					
					if(contactUs2  != null){
						objMap.put("message", "Your query submitted successfully.");
						String jsonInString = mapper.writeValueAsString(objMap);
						return  new ResponseEntity<String>(jsonInString, HttpStatus.OK);
					}else{
						objMap.put("message", "Something went wrong.");
						String jsonInString = mapper.writeValueAsString(objMap);
						return  new ResponseEntity<String>(jsonInString, HttpStatus.INTERNAL_SERVER_ERROR);
					}
				}else{
					if(!secrateKey.equals(Commons.secretString)){
						objMap.put("message", "Session has been expired. Please login again to procced.");
						String jsonInString = mapper.writeValueAsString(objMap);
						return new ResponseEntity<String>(jsonInString,HttpStatus.UNAUTHORIZED);
					}else{
						objMap.put("message", "You need to sign up to post a deal.");
						String jsonInString = mapper.writeValueAsString(objMap);
						return new ResponseEntity<String>(jsonInString,HttpStatus.LOCKED);
					}
				}
			}else{
				objMap.put("message", "You are using an outdated app. Please update it to newer version.");
				objMap.put("results","");
				String jsonInString = mapper.writeValueAsString(objMap);
				return new ResponseEntity<String>(jsonInString,HttpStatus.BAD_REQUEST);	
			}
		} catch (Exception e) {
			e.printStackTrace();
			objMap.put("message", "Something went wrong");
			objMap.put("status", 0);
			String jsonInString = mapper.writeValueAsString(objMap);
			return  new ResponseEntity<String>(jsonInString, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "shareDeal", method = RequestMethod.GET)
	@ResponseBody 
	public ResponseEntity<String> shareDeal(HttpServletRequest request) throws JSONException, JsonGenerationException, JsonMappingException, IOException {
		Map<String, Object> objMap = new HashMap<String,Object>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			objMap.put("message", "You need to sign up to post a deal.");
			String jsonInString = mapper.writeValueAsString(objMap);
			return new ResponseEntity<String>(jsonInString,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			objMap.put("message", "Something went wrong");
			objMap.put("status", 0);
			String jsonInString = mapper.writeValueAsString(objMap);
			return  new ResponseEntity<String>(jsonInString, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
