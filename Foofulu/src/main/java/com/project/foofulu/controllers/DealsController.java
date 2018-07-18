package com.project.foofulu.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
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
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.foofulu.models.AppVersion;
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
import com.project.foofulu.models.Users;
import com.project.foofulu.models.VerifiedDeals;
import com.project.foofulu.services.DealsServices;
import com.project.foofulu.services.UserServices;
import com.project.foofulu.utils.Commons;
import com.project.foofulu.utils.YelpApi;

@Controller
public class DealsController {

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

	/*** JSON Object 
	{
		"longitude":"-74.0060",
		"latitude":"40.7128"
	 ***/

	@RequestMapping(value = "getVenuesList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<String> getVenuesList(@RequestHeader("secretKey") String secretKey ,
			@RequestHeader("version") String version ,@RequestHeader("deviceType") String deviceType ,
			@RequestBody String jsonStr,HttpServletRequest request) 
			throws JSONException, JsonGenerationException, JsonMappingException, IOException {
		List<Map<String, Object>> venuesList = new ArrayList<Map<String,Object>>();
		Map<String, Object> objMap = new HashMap<String,Object>();
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
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
					System.out.println("JSON Response >>>>>>>"+jsonStr);
					JSONObject jsonObject = new JSONObject(jsonStr);
					String response = YelpApi.getAccessToken();
					JSONObject jsonObject2 = new JSONObject(response);
					String accessToken = jsonObject2.getString("access_token");
					System.out.println("Access Token  >>> "+accessToken);
					
					String venuesResponse = YelpApi.getVenuesList(jsonObject.getString("latitude")
							, jsonObject.getString("longitude"),accessToken);
				
					System.out.println("Data  >>> "+ venuesResponse);				
					JSONObject jsonObject3  = new JSONObject(venuesResponse);
					
					JSONArray jsonArray = jsonObject3.getJSONArray("businesses");
					for (int i = 0; i < jsonArray.length(); i++) {
						Map<String, Object> objVenue = new HashMap<String,Object>();
						JSONObject objBussiness= jsonArray.getJSONObject(i);
						System.out.println("Bussiness Object  >>> "+ objBussiness.toString());	
						objVenue.put("id", objBussiness.getString("id"));
						objVenue.put("name",objBussiness.getString("name"));
						objVenue.put("imageUrl",objBussiness.getString("image_url"));
						
						JSONObject  location =objBussiness.getJSONObject("location");
						objVenue.put("location",location.getString("address1")+" , "+
								location.getString("city")+" , "+location.getString("zip_code"));
						
						double distance = objBussiness.getDouble("distance")/1000;
						distance = 0.621*distance;
						distance =Commons.round(distance, 2);
						objVenue.put("distance",distance);
						venuesList.add(objVenue);
					}
					objMap.put("message", "Venues list fetched successfully.");
					objMap.put("results", venuesList);
					String jsonInString = mapper.writeValueAsString(objMap);
					return new ResponseEntity<String>(jsonInString,HttpStatus.OK);
				}
			}else{
				objMap.put("message", "You are using an outdated app. Please update it to newer version.");
				objMap.put("results","");
				String jsonInString = mapper.writeValueAsString(objMap);
				return new ResponseEntity<String>(jsonInString,HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e) {
			e.printStackTrace();
			objMap.put("message", e.getMessage());
			String jsonInString = mapper.writeValueAsString(objMap);
			return new ResponseEntity<String>(jsonInString,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	/*** JSON Object 
	 *	{
			"bussinessId":"aahar-indian-cuisine-new-york",
			"longitude":"-74.0060",
			"latitude":"40.7128"
		}
	***/
	@RequestMapping(value = "getVenueDetails", method = RequestMethod.POST,
			produces = "application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<String> getVenueDetails(@RequestHeader("secretKey") String secretKey ,
			@RequestHeader("version") String version ,@RequestHeader("deviceType") String deviceType ,
			@RequestBody String jsonStr,HttpServletRequest request) 
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
					System.out.println("JSON Response >>>>>>>"+jsonStr);
					JSONObject jsonObject = new JSONObject(jsonStr);
					String response = YelpApi.getAccessToken();
					JSONObject jsonObject2 = new JSONObject(response);
					String accessToken = jsonObject2.getString("access_token");
					System.out.println("Access Token  >>> "+accessToken);
					String venuesResponse = YelpApi.getVenuesDetails(jsonObject.getString("bussinessId"), accessToken);
					JSONObject jsonObject3  = new JSONObject(venuesResponse);
					System.out.println(venuesResponse);
					
					Map<String, Object> objBussiness = new HashMap<String,Object>();
					
					objBussiness.put("name",jsonObject3.getString("name"));
					objBussiness.put("price",jsonObject3.getString("price"));
					objBussiness.put("is_closed", jsonObject3.getBoolean("is_closed"));
					objBussiness.put("display_phone",jsonObject3.getString("display_phone"));
					objBussiness.put("phone",jsonObject3.getString("phone"));
					objBussiness.put("review_count",jsonObject3.getInt("review_count"));
					objBussiness.put("rating",jsonObject3.getDouble("rating"));
					
					JSONObject  location =jsonObject3.getJSONObject("location");
					objBussiness.put("location",location.getString("address1")+" , "+
							location.getString("city")+" , "+location.getString("country")+
							" , "+location.getString("zip_code"));
								
					List<Map<String, Object>> categories = new ArrayList<Map<String, Object>>();
					JSONArray jsonArray1 =  jsonObject3.getJSONArray("categories");
					for (int i = 0; i < jsonArray1.length(); i++) {
						Map<String, Object> objCategories = new HashMap<String,Object>();
						objCategories.put("alias", jsonArray1.getJSONObject(i).getString("alias"));
						objCategories.put("title", jsonArray1.getJSONObject(i).getString("title"));
						categories.add(objCategories);
					}
					objBussiness.put("categories", categories);
									
					JSONObject jsonObject4 = jsonObject3.getJSONObject("coordinates");
					double latitude = jsonObject4.getDouble("latitude");
					double longitude = jsonObject4.getDouble("longitude");
					Map<String, Object> objCordinates = new HashMap<String,Object>();
					objCordinates.put("latitude", latitude);
					objCordinates.put("longitude", longitude);
					objBussiness.put("coordinates", objCordinates);
					
					Double distance =Commons.distFrom(jsonObject.getDouble("latitude"),
							jsonObject.getDouble("longitude"), latitude, longitude)/1000;
					distance = 0.621*distance;
					objBussiness.put("distance", distance);
					
					List<String> images = new ArrayList<String>();
				//	images.add(jsonObject3.getString("image_url"));
					JSONArray jsonArray = jsonObject3.getJSONArray("photos");
					for (int i = 0; i < jsonArray.length(); i++) {
						images.add(jsonArray.getString(i));
					}
					objBussiness.put("photos", images);
					
					
					Date date = new Date();
					JSONArray jsonArray2 = jsonObject3.getJSONArray("hours");
					DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
					DateFormat dateFormat1 = new SimpleDateFormat("HH:mm");
					
					for (int i = 0; i < jsonArray2.length();) {
						JSONObject jsonObject5 = jsonArray2.getJSONObject(i);
						JSONArray jsonArray3 = jsonObject5.getJSONArray("open");
						
						System.out.println("Day is >>>>>> "+date.getDay());
						JSONObject jsonObject6 =  jsonArray3.getJSONObject(date.getDay());
						String end = jsonObject6.getString("end");
						String timing = dateFormat.format(dateFormat1.parse(end.substring(0, 2)+":"+end.substring(2)));
						System.out.println("date sub string >>>> "+timing);
						objBussiness.put("closing", "untill "+timing);
						break;
					}
					
					objMap.put("bussiness", objBussiness); 
					objMap.put("message", "Venue details fetched successfully.");
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
	
	@RequestMapping(value = "addDeal", method = RequestMethod.POST,	produces = "application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<String> addDeal(@RequestHeader("secretKey") String secretKey ,
			@RequestHeader("version") String version ,
			@RequestHeader("deviceType") String deviceType ,
			@RequestParam("businessId") String bussinessId,
			@RequestParam("dealTitle") String dealTitle,
			@RequestPart(value="images",required=false) MultipartFile[] dealImages,
			@RequestParam("days") String days,
			@RequestParam("meals") String meals,
			HttpServletRequest request) 
			throws JSONException, JsonGenerationException, JsonMappingException, IOException {
		Map<String, Object> objMap = new HashMap<String,Object>();
		ObjectMapper mapper = new ObjectMapper();
		try{
			AppVersion appVersion = userServices.getAppVersion(deviceType);
			if(appVersion.getVersion().equals(version)){
				System.out.println("parameters >>>>>>> " +bussinessId+" ==== "+dealTitle);
				String[] days1 = days.split(",");
				String[] meals1 = meals.split(",");
				System.out.println("parameters >>>>>>> " +bussinessId+" ==== "+dealTitle);
				Users objUser= userServices.checkSecrteKey(secretKey);
				
				if(objUser == null ){
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
					List<Deals> listDeals = dealsServices.getDealsByUser(objUser.getId());
					
					Bussiness bussiness = addBussiness(bussinessId);
					
					/******** Deals Work Pending **********/
					
					Deals deals = new Deals();
					deals.setAddedBy(objUser);
					deals.setBussiness(bussiness);
					deals.setCreationTime(new Date());
					deals.setTitle(dealTitle);
					deals.setStatus(true);
					Deals deals2 = dealsServices.addDeals(deals);
					for (int i = 0; i < dealImages.length; i++) {
						DealImages dealImages2 = new DealImages();
						dealImages2.setDeal(deals2);
						DealImages dealImages3 = dealsServices.addDealImages(dealImages2);
						
						String test1 = request.getSession().getServletContext().getRealPath("");
						String dir = test1 + "/resources/dealImages/"+dealImages3.getId() + "/";
						if(!new File(dir).exists()){
							new File(dir).mkdirs();
						}else{
						      String[] myFiles = new File(dir).list();
				              for (int j=0; j<myFiles.length; j++) {
				                  File myFile = new File(new File(dir), myFiles[j]); 
				                  myFile.delete();
				              }
						}
						
						String fileName = dealImages[i].getOriginalFilename();
						String ext = FilenameUtils.getExtension(fileName);
						fileName = Commons.getFileName()+"."+ext;
						
						InputStream fileContent = dealImages[i].getInputStream();
						OutputStream outputStream = new FileOutputStream(new File(dir+"/"+fileName));
						int read = 0;
						byte[] bytes = new byte[1024];
						while ((read = fileContent.read(bytes)) != -1) {
							outputStream.write(bytes, 0, read);
						}
						
						String n = "/resources/dealImages/"+dealImages3.getId() + "/";
						n =n+fileName;
						dealImages3.setImage(n);
						dealsServices.updateDealImages(dealImages3);
						outputStream.close();
						fileContent.close();
					}
					
					
					for (int i = 0; i < days1.length; i++) {
						Days day = dealsServices.getDayById(Integer.parseInt(days1[i]));
						DealDays dealDays = new DealDays();
						dealDays.setDay(day);
						dealDays.setDeal(deals2);
						dealsServices.addDealDays(dealDays);
					}
					
					System.out.print("Meal ID "+meals1[0]);
					for (int i = 0; i < meals1.length; i++) {
						MealCategories mealCategories = dealsServices.getMealById(Integer.parseInt(meals1[i]));
						System.out.print("Meal Category >> "+mealCategories.getCategory());
						DealsCategories dealsCategories = new DealsCategories();
						dealsCategories.setMealCategories(mealCategories);
						dealsCategories.setDeal(deals2);
						dealsServices.addDealsCategories(dealsCategories);
					}
					
					if(listDeals ==null || listDeals.isEmpty()){
						objMap.put("firstDeal", true);
					}else{
						objMap.put("firstDeal", false);
					}
					
					objMap.put("message", "Deal added successfully.");
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
	
	@RequestMapping(value = "updateDeal", method = RequestMethod.POST,
			produces = "application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<String> updateDeal(@RequestHeader("secretKey") String secretKey ,
			@RequestHeader("version") String version ,
			@RequestHeader("deviceType") String deviceType ,
			@RequestParam("dealId") Long dealId,
			@RequestParam(value="dealTitle",required=false) String dealTitle,
			@RequestPart(value="images",required=false) MultipartFile[] dealImages,
			@RequestParam(value="days",required=false) Integer[] days,
			@RequestParam(value="meals",required=false) Integer[] meals,
			@RequestParam(value="deletedImages",required=false) String deletedImages,
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
					
					Deals deals = dealsServices.getDealById(dealId);
					deals.setUpdatedBy(objUser);
					deals.setUpdationTime(new Date());
					if(dealTitle != null)
						deals.setTitle(dealTitle);
					dealsServices.updateDeals(deals);
		
					
					
					if(!deletedImages.equals("")){
						String[] imagesIds = deletedImages.split(",");
						for (int i = 0; i < imagesIds.length; i++) {
							
							DealImages dealImages2 = dealsServices.getDealImagesById(Long.parseLong(imagesIds[i]));
							dealsServices.deleteDealImages(dealImages2);
						}
					}
					
					if(dealImages != null  && dealImages.length>0){
						for (int i = 0; i < dealImages.length; i++) {
							
							DealImages dealImages2 = new DealImages();
							dealImages2.setDeal(deals);
							DealImages dealImages3 = dealsServices.addDealImages(dealImages2);
							
							String test1 = request.getSession().getServletContext().getRealPath("");
							String dir = test1 + "/resources/dealImages/"+dealImages3.getId() + "/";
							if(!new File(dir).exists()){
								new File(dir).mkdirs();
							}else{
							      String[] myFiles = new File(dir).list();
					              for (int j=0; j<myFiles.length; j++) {
					                  File myFile = new File(new File(dir), myFiles[j]); 
					                  myFile.delete();
					              }
							}
							
							String fileName = dealImages[i].getOriginalFilename();
							String ext = FilenameUtils.getExtension(fileName);
							fileName = Commons.getFileName()+"."+ext;
							
							InputStream fileContent = dealImages[i].getInputStream();
							OutputStream outputStream = new FileOutputStream(new File(dir+"/"+fileName));
							int read = 0;
							byte[] bytes = new byte[1024];
							while ((read = fileContent.read(bytes)) != -1) {
								outputStream.write(bytes, 0, read);
							}
							
							String n = "/resources/dealImages/"+dealImages3.getId() + "/";
							n= n+fileName;
							dealImages3.setImage(n);
							dealsServices.updateDealImages(dealImages3);
							outputStream.close();
							fileContent.close();
						}
					}else{
						List<DealImages>   dealImages5 = dealsServices.getDealImagesByDeals(deals.getId());
						if(dealImages5 != null && dealImages5.size()>0 ){
	
						}else{
							DealImages dealImages2 = new DealImages();
							dealImages2.setDeal(deals);
							dealImages2.setImage("/resources/img/NoImage.jpg");
							dealsServices.addDealImages(dealImages2);
						}
					}
					 
					for (int j = 0; j < days.length; j++) {
						DealDays dealDays = dealsServices.getDaysbyDealAndDay(deals.getId(), days[j]);
						if(dealDays == null){
							Days days2 = dealsServices.getDayById(days[j]);
							DealDays dealDays2 = new DealDays();
							dealDays2.setDay(days2);
							dealDays2.setDeal(deals);
							dealsServices.addDealDays(dealDays2);
						}
					}
					
					List<Integer> days2 = new ArrayList<Integer>(Arrays.asList(days));
					List<DealDays> dealDays = dealsServices.getDays(deals.getId());
					for (DealDays dealDays2 : dealDays) {
						if (!days2.contains(dealDays2.getDay().getId())) {
							dealsServices.deleteDays(dealDays2);
						}
					}
					
					for (int i = 0; i < meals.length; i++) {
						DealsCategories dealsCategories = dealsServices.getDealsCategoriesByDealIDAndCategory(
								deals.getId(), meals[i]);
						if(dealsCategories == null){
							MealCategories mealCategories = dealsServices.getMealById(meals[i]);
							DealsCategories dealsCategories1 = new DealsCategories();
							dealsCategories1.setMealCategories(mealCategories);
							dealsCategories1.setDeal(deals);
							dealsServices.addDealsCategories(dealsCategories1);	
						}
					}
					
					
					List<Integer> categories = new ArrayList<Integer>(Arrays.asList(meals));	
					List<DealsCategories> dealsCategories = dealsServices.getDealsCategories(deals.getId());
					for (DealsCategories dealsCategories2 : dealsCategories) {
						if (!categories.contains(dealsCategories2.getMealCategories().getId())) {
							dealsServices.deleteDealsCategories(dealsCategories2);
						}
					}
					
					objMap.put("message", "Deal updated successfully.");
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
	
	/*** JSON Object 
	 *	{
			"dealId":1
		}
	***/
	@RequestMapping(value = "disableDeal", method = RequestMethod.POST,
			produces = "application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<String> disableDeal(@RequestHeader("secretKey") String secretKey ,
			@RequestHeader("version") String version ,
			@RequestHeader("deviceType") String deviceType ,
			@RequestBody String jsonStr,
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
					JSONObject  jsonObject = new JSONObject(jsonStr);
					
					Deals deals = dealsServices.getDealById(jsonObject.getLong("dealId"));
					deals.setStatus(false);
					dealsServices.updateDeals(deals);
					
					objMap.put("message", "Deal disabled successfully.");
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
	
	/*** JSON Object 
	 *	{
			"dealId":1
		}
	***/
	@RequestMapping(value = "getCategoriesAndDays", method = RequestMethod.GET,
			produces = "application/json;charset=utf-8")
	public ResponseEntity<String> getCategoriesAndDays(@RequestHeader("secretKey") String secretKey ,
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
				boolean notRegistered=false;
				if(objUser == null){
					if(secretKey.equals(Commons.secretString))
							notRegistered=true;
				}
				if(objUser == null && !notRegistered){			
					objMap.put("message", "Session has been expired. Please login again to procced.");
					String jsonInString = mapper.writeValueAsString(objMap);
					return new ResponseEntity<String>(jsonInString,HttpStatus.UNAUTHORIZED);	
				}else{
					List<Map<String,Object>> listCategories = new ArrayList<Map<String,Object>>();
					
					List<Map<String,Object>> listDays = new ArrayList<Map<String,Object>>();
					List<Days> days = dealsServices.getDays();
					
					for (Days days1 : days) {
						Map<String,Object> objDay = new HashMap<String, Object>();
						if(days1.getDay().equalsIgnoreCase("sunday"))
							objDay.put("day", "Sun");
						
						if(days1.getDay().equalsIgnoreCase("monday"))
							objDay.put("day", "M");
						
						if(days1.getDay().equalsIgnoreCase("Tuesday"))
							objDay.put("day", "Tu");
						
						if(days1.getDay().equalsIgnoreCase("Wednesday"))
							objDay.put("day", "W");
						
						if(days1.getDay().equalsIgnoreCase("Thursday"))
							objDay.put("day", "Th");
						
						if(days1.getDay().equalsIgnoreCase("Friday"))
							objDay.put("day", "Fr");
						
						if(days1.getDay().equalsIgnoreCase("Saturday"))
							objDay.put("day", "Sat");
						
						objDay.put("id", days1.getId());
						listDays.add(objDay);
					}
					
					Map<String,Object> objDay = new HashMap<String, Object>();
					objDay.put("day", "Every Day");
					objDay.put("id", 0);
					listDays.add(objDay);
					
					List<MealCategories> mealsCategories = dealsServices.getCategories();
					for (MealCategories mealCategories : mealsCategories) {
						Map<String,Object> objCategories = new HashMap<String, Object>();
						objCategories.put("day", mealCategories.getCategory());
						objCategories.put("id", mealCategories.getId());
						listDays.add(objCategories);
					}
					
					objMap.put("listDays", listDays);
					objMap.put("message", "Data fetched successfully.");
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
	
	/*****  
	 {
	 	"open" : 1,
	 	"distance" : 10,
	 	"day" : 1 , 
	 	 "meal" : 2,
	 	 "latitude" : 32.1454,
	 	 "longitude" : 76.2487,
	 	 "text" : "",
	 	 "timeZone" : ""
	 }
	 * *****/
	
	/*@SuppressWarnings("deprecation")
	@RequestMapping(value = "getBussinessesDeals", method = RequestMethod.POST,
			produces = "application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<String> getBussinessesDeals(@RequestHeader("secretKey") String secretKey,
			@RequestBody String jsonStr,HttpServletRequest request) 
			throws JSONException, JsonGenerationException, JsonMappingException, IOException {
		Map<String, Object> objMap = new HashMap<String,Object>();
		ObjectMapper mapper = new ObjectMapper();
		try{
			System.out.println("Secret Key >>>>> "+secretKey);
			Users objUser= userServices.checkSecrteKey(secretKey);
			boolean notRegistered=false;
			if(objUser == null){
				if(secretKey.equals(Commons.secretString))
						notRegistered=true;
			}
			if(objUser == null && !notRegistered){	
				objMap.put("message", "Session has been expired. Please login again to procced.");
				String jsonInString = mapper.writeValueAsString(objMap);
				return new ResponseEntity<String>(jsonInString,HttpStatus.UNAUTHORIZED);	
			}else{
				
				System.out.println("JSON Response >>>>>>>"+jsonStr);
				JSONObject jsonObject = new JSONObject(jsonStr);
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
				DateFormat dateFormat1 = new SimpleDateFormat("HH:mm");
				DateFormat dateFormat3 = new SimpleDateFormat("hh:mm a");
				boolean openNow=false;
				int meal=0;
				double distance=5;
				int day=0;
				String text="";
				if(jsonObject.has("distance")){
					distance = jsonObject.getInt("distance");
				}
				
				if(jsonObject.has("text")){
					text = jsonObject.getString("text").toUpperCase();
				}
				
				if(jsonObject.has("open")){
					if (jsonObject.getInt("open")==1) {
						openNow=true;
					}
				}
				
				if(jsonObject.has("day")){
					day = jsonObject.getInt("day");
				}
				
				if(jsonObject.has("meal")){
					meal = jsonObject.getInt("meal");
				}
				
				String condition="";
				double kilometers =distance * 1.609344;
			  	System.out.println("distance in km "+kilometers);
			    double distance1 = (kilometers / 40000) * 360;
			    System.out.println("Distance in killo meters >> "+distance1);
			    if(!text.equals(""))
			    condition = " where upper(name) like '%"+text+"%'  and latitude<("+jsonObject.getDouble("latitude")+" + "+distance1+")"
			    		+ " and latitude > ("+jsonObject.getDouble("latitude")+" - "+distance1+")"
			    				+ " and longitude < ("+jsonObject.getDouble("longitude")+" + "+distance1+")"
			    						+ " and longitude > ("+jsonObject.getDouble("longitude")+" - "+distance1+")";
			    else
			    	condition = " where latitude<("+jsonObject.getDouble("latitude")+" + "+distance1+")"
					    		+ " and latitude > ("+jsonObject.getDouble("latitude")+" - "+distance1+")"
					    				+ " and longitude < ("+jsonObject.getDouble("longitude")+" + "+distance1+")"
					    						+ " and longitude > ("+jsonObject.getDouble("longitude")+" - "+distance1+")";
			    
			    List<Map<String, Object>> listBussinesses = new ArrayList<Map<String,Object>>();
			    List<Bussiness> bussinesses = dealsServices.getBussiness(condition);
			    for (Bussiness bussiness : bussinesses) {
			    	if(openNow){
			    		boolean hasOpen=false;
			    		Map<String, Object> objBussiness = new HashMap<String,Object>();
						
			    		if(!notRegistered){
			    		FavouriteBussiness  favouriteBussiness=dealsServices.getFavouriteBussinessByUserAndBussiness(objUser.getId(),
			    				bussiness.getId());
				    		if(favouriteBussiness != null)
				    			objBussiness.put("isFavorite",true);
				    		else
				    			objBussiness.put("isFavorite",false);
			    		}else{
			    			objBussiness.put("isFavorite",false);
			    		}
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
						
						DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
						
						Date date = new Date();
						boolean isClosedNow=true;
						List<BussinessDays> bussinessDays = dealsServices.getBussinessDays(bussiness);
						for (BussinessDays bussinessDays2 : bussinessDays) {
							if(bussinessDays2.getDay() == date.getDay()){
								
								String end = bussinessDays2.getEnd();
								String start = bussinessDays2.getStart();
								
								String timing = dateFormat.format(dateFormat1.parse(end.substring(0, 2)+":"+end.substring(2)));
								
								objBussiness.put("closing", "untill "+timing);
								objBussiness.put("closingTime", timing);
								
								if(!TimeZone.getDefault().getID().equals(jsonObject.getString("timeZone"))){
									
									Calendar cal = Calendar.getInstance();
									cal.setTimeZone(TimeZone.getTimeZone(jsonObject.getString("timeZone")));

									String today2 = dateFormat2.format(new Date());
									
									Date endDate =sdf.parse(today2+" "+end.substring(0, 2)+":"+end.substring(2));
									Date today =sdf.parse(today2+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE));
									
									Date startDate =sdf.parse(today2+" "+start.substring(0, 2)+":"+start.substring(2));
									
									if(startDate.after(endDate)){
										System.out.println("Start time is greater then End");
										cal.setTime(startDate);
										cal.add(Calendar.DATE, -1);
										startDate = cal.getTime();
									}
									
									if(today.after(startDate) && today.before(endDate)){
										isClosedNow=false;
										System.out.println("startDate >>>> "+startDate+" endDate >>> "+endDate+" Current >>  "+today);
										System.out.println("startDate >>>> "+startDate+" endDate >>> "+endDate+" Current >>  "+today);
										System.out.println("startDate >>>> "+startDate+" endDate >>> "+endDate+" Current >>  "+today);
										hasOpen=true;
										if(jsonObject.has("open")){
											int open = jsonObject.getInt("open");
											if(open==1){
												openNow=true;
											}
										}
									}else{
										if(jsonObject.has("open")){
											int open = jsonObject.getInt("open");
											if(open==1){
												openNow=true;
											}
										}
									}
								}else{
									String today = dateFormat2.format(new Date());
									Date date2 = new Date();
									Date endDate =sdf.parse(today+" "+end.substring(0, 2)+":"+end.substring(2));
									
									Date startDate =sdf.parse(today+" "+start.substring(0, 2)+":"+start.substring(2));
									Calendar cal = Calendar.getInstance();
									if(startDate.after(endDate)){
										System.out.println("Start time is greater then End");
										cal.setTime(endDate);
										cal.add(Calendar.DATE, 1);
										endDate = cal.getTime();
									}
									
									if(date2.after(startDate) && date2.before(endDate)){
										isClosedNow=false;
										if(jsonObject.has("open")){
											int open = jsonObject.getInt("open");
											if(open==1){
												openNow=true;
											}
										}
									}else{
										if(jsonObject.has("open")){
											int open = jsonObject.getInt("open");
											if(open==1){
												openNow=true;
											}
										}
									}
								}
							}
						}
						objBussiness.put("is_closed", isClosedNow);
						
						List<Map<String, Object>> listDeals = new ArrayList<Map<String,Object>>();
						List<Deals> deals = dealsServices.getDealsByBussiness(bussiness.getId());
						boolean  hasDays = false;
						boolean hasCategories = false;
						boolean hasBoth=false;
						for (Deals deals2 : deals) {
							System.out.println("Deal is >> "+deals2.getTitle());
							List<DealImages> dealImages = dealsServices.getDealImagesByDeals(deals2.getId());
							for (DealImages dealImages2 : dealImages) {
								images.add("http://112.196.97.229:8080/Foofulu"+dealImages2.getImage());
							}
							System.out.println(" Out Side >>>> "+hasBoth);
							System.out.println("Out Side  >>>> "+hasBoth);
							System.out.println("Out Side  >>>> "+hasBoth);

							if(meal>0 && day>0){
								DealsCategories dealCategroy = dealsServices.getDealsCategoriesByDealIDAndCategory(
										deals2.getId(),meal);
								DealDays dealDays = dealsServices.getDealsbyDealAndDay(
										deals2.getId(), day);
								if(dealCategroy != null && dealDays != null){
									Map<String, Object> objDeals = new HashMap<String, Object>();
									objDeals.put("title", deals2.getTitle());
									objDeals.put("id", deals2.getId());
									hasBoth = true;
									listDeals.add(objDeals);
								}
							}else if(meal>0){
								DealsCategories dealCategroy = dealsServices.getDealsCategoriesByDealIDAndCategory(
										deals2.getId(),meal);
								if(dealCategroy != null){
									Map<String, Object> objDeals = new HashMap<String, Object>();
									objDeals.put("title", deals2.getTitle());
									objDeals.put("id", deals2.getId());
									hasCategories=true;
									listDeals.add(objDeals);
								}
							}else if(day>0){
								DealDays dealDays = dealsServices.getDealsbyDealAndDay(
										deals2.getId(), day);
								if(dealDays != null){
									Map<String, Object> objDeals = new HashMap<String, Object>();
									objDeals.put("title", deals2.getTitle());
									objDeals.put("id", deals2.getId());
									hasDays=true;
									listDeals.add(objDeals);
								}
							}else{
								Map<String, Object> objDeals = new HashMap<String, Object>();
								objDeals.put("title", deals2.getTitle());
								objDeals.put("id", deals2.getId());
								hasBoth=true;
								listDeals.add(objDeals);
							}
							
							
						}
						objBussiness.put("photos", images);
						System.out.println("Has opened  >>>>>>>>>> "+hasOpen +" Has Both >>>> "+hasBoth);
						System.out.println("Has opened  >>>>>>>>>> "+hasOpen+" Has Both >>>> "+hasBoth);
						System.out.println("Has opened  >>>>>>>>>> "+hasOpen+" Has Both >>>> "+hasBoth);
						if(hasBoth && hasOpen){
							objBussiness.put("deals", listDeals);
							listBussinesses.add(objBussiness);
						}else if(day<1 && hasCategories && hasOpen ){
							objBussiness.put("deals", listDeals);
							listBussinesses.add(objBussiness);
						}else if(meal<1 && hasDays && hasOpen){
							objBussiness.put("deals", listDeals);
							listBussinesses.add(objBussiness);
						}
					}else{
						Map<String, Object> objBussiness = new HashMap<String,Object>();
						
			    		if(!notRegistered){
			    		FavouriteBussiness  favouriteBussiness=dealsServices.getFavouriteBussinessByUserAndBussiness(objUser.getId(),
			    				bussiness.getId());
				    		if(favouriteBussiness != null)
				    			objBussiness.put("isFavorite",true);
				    		else
				    			objBussiness.put("isFavorite",false);
			    		}else{
			    			objBussiness.put("isFavorite",false);
			    		}
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
						
						
						
						Date date = new Date();
						List<BussinessDays> bussinessDays = dealsServices.getBussinessDays(bussiness);
						boolean isClosedNow=true;
						for (BussinessDays bussinessDays2 : bussinessDays) {
							if(bussinessDays2.getDay() == date.getDay()){
								String end = bussinessDays2.getEnd();
								String start = bussinessDays2.getStart();
								
								String timing = dateFormat3.format(dateFormat1.parse(end.substring(0, 2)+":"+end.substring(2)));
								objBussiness.put("closing", "untill "+timing);
								
								if(!TimeZone.getDefault().getID().equals(jsonObject.getString("timeZone"))){
									
									Calendar cal = Calendar.getInstance();
									cal.setTimeZone(TimeZone.getTimeZone(jsonObject.getString("timeZone")));

									String today2 = dateFormat2.format(new Date());
									int hours =Integer.parseInt(cal.get(Calendar.HOUR_OF_DAY)+"");
									int mintues = Integer.parseInt(cal.get(Calendar.MINUTE)+"");
									if(hours)
									
									String todayNum = cal.get(Calendar.HOUR_OF_DAY)+""+cal.get(Calendar.MINUTE);
									
									Date endDate =sdf.parse(today2+" "+end.substring(0, 2)+":"+end.substring(2));
									Date today =sdf.parse(today2+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE));
									
									System.out.println("Current date >>> "+cal.get(Calendar.DATE)+"-"+cal.get(Calendar.MONTH)+
											"-"+cal.get(Calendar.YEAR));
									
									Date startDate =sdf.parse(today2+" "+start.substring(0, 2)+":"+start.substring(2));
									
								    System.out.println("Current >>>>>> " + today);
								    System.out.println("Open >>>>>> " + startDate);
								    
									if(startDate.after(endDate)){
										System.out.println("Start time is greater then End");
										cal.setTime(startDate);
										cal.add(Calendar.DATE, -1);
										startDate = cal.getTime();
									}
									System.out.println("Close >>>>>> " + startDate);
									
									System.out.println(today.after(startDate)+" >>>>>>> "+today.before(endDate));
								    
									if(today.after(startDate) && today.before(endDate)){
										isClosedNow=false;
										System.out.println("startDate >>>> "+startDate+" endDate >>> "+endDate+" Current >>  "+today);
										System.out.println("startDate >>>> "+startDate+" endDate >>> "+endDate+" Current >>  "+today);
										System.out.println("startDate >>>> "+startDate+" endDate >>> "+endDate+" Current >>  "+today);
										if(jsonObject.has("open")){
											int open = jsonObject.getInt("open");
											if(open==1){
												openNow=true;
											}
										}
									}else{
										if(jsonObject.has("open")){
											int open = jsonObject.getInt("open");
											if(open==1){
												openNow=true;
											}
										}
									}
								}else{
									String today = dateFormat2.format(new Date());
									Date date2 = new Date();
									Date endDate =sdf.parse(today+" "+end.substring(0, 2)+":"+end.substring(2));
								
									Date startDate =sdf.parse(today+" "+start.substring(0, 2)+":"+start.substring(2));
									Calendar cal = Calendar.getInstance();
									if(startDate.after(endDate)){
										System.out.println("Start time is greater then End");
										cal.setTime(startDate);
										cal.add(Calendar.DATE, -1);
										startDate = cal.getTime();
									}
									
									if(date2.after(startDate) && date2.before(endDate)){
										isClosedNow=false;
										System.out.println("startDate >>>> "+startDate+" endDate >>> "+endDate+" Current >>  "+today);
										System.out.println("startDate >>>> "+startDate+" endDate >>> "+endDate+" Current >>  "+today);
										System.out.println("startDate >>>> "+startDate+" endDate >>> "+endDate+" Current >>  "+today);
										if(jsonObject.has("open")){
											int open = jsonObject.getInt("open");
											if(open==1){
												openNow=true;
											}
										}
									}else{
										if(jsonObject.has("open")){
											int open = jsonObject.getInt("open");
											if(open==1){
												openNow=true;
											}
										}
										objBussiness.put("is_closed", true);
									}
								}
							}
						}
						objBussiness.put("is_closed", isClosedNow);
						List<Map<String, Object>> listDeals = new ArrayList<Map<String,Object>>();
						List<Deals> deals = dealsServices.getDealsByBussiness(bussiness.getId());
						boolean  hasDays = false;
						boolean hasCategories = false;
						boolean hasBoth=false;
						for (Deals deals2 : deals) {
							List<DealImages> dealImages = dealsServices.getDealImagesByDeals(deals2.getId());
							for (DealImages dealImages2 : dealImages) {
								images.add("http://112.196.97.229:8080/Foofulu"+dealImages2.getImage());
							}
							
							if(meal>0 && day>0){
								DealsCategories dealCategroy = dealsServices.getDealsCategoriesByDealIDAndCategory(
										deals2.getId(),meal);
								DealDays dealDays = dealsServices.getDealsbyDealAndDay(
										deals2.getId(), day);
								if(dealCategroy != null && dealDays != null){
									Map<String, Object> objDeals = new HashMap<String, Object>();
									objDeals.put("title", deals2.getTitle());
									objDeals.put("id", deals2.getId());
									hasBoth= true;
									listDeals.add(objDeals);
								}
							}else if(meal>0){
								DealsCategories dealCategroy = dealsServices.getDealsCategoriesByDealIDAndCategory(
										deals2.getId(),meal);
								if(dealCategroy != null){
									Map<String, Object> objDeals = new HashMap<String, Object>();
									objDeals.put("title", deals2.getTitle());
									objDeals.put("id", deals2.getId());
									hasCategories=true;
									listDeals.add(objDeals);
								}
							}else if(day>0){
								DealDays dealDays = dealsServices.getDealsbyDealAndDay(
										deals2.getId(), day);
								if(dealDays != null){
									Map<String, Object> objDeals = new HashMap<String, Object>();
									objDeals.put("title", deals2.getTitle());
									objDeals.put("id", deals2.getId());
									hasDays=true;
									listDeals.add(objDeals);
								}
							}else{
								Map<String, Object> objDeals = new HashMap<String, Object>();
								objDeals.put("title", deals2.getTitle());
								objDeals.put("id", deals2.getId());
								hasBoth=true;
								listDeals.add(objDeals);
							}
						}
						
						objBussiness.put("photos", images);
						
						if(hasBoth){
							objBussiness.put("deals", listDeals);
							listBussinesses.add(objBussiness);	
						}else if(day<1 && hasCategories){
							objBussiness.put("deals", listDeals);
							listBussinesses.add(objBussiness);
						}else if(meal<1 && hasDays){
							objBussiness.put("deals", listDeals);
							listBussinesses.add(objBussiness);
						}
					}
				}
			    
			    Collections.sort(listBussinesses, new Comparator<Map<String, Object>>() {
					@Override
					public int compare(Map<String, Object> o1,
							Map<String, Object> o2) {
						if(o1.get("distance") instanceof Double){
							
							Double d1=(Double)o1.get("distance");
							Double d2=(Double)o2.get("distance");
							if (d1>d1){
							 return 1;
							}else if (d1<d2){
					            return -1;
							}else{
								return 0;
							}
						}else{
							return 0;
						}
							
					}
				});
			    
			    
			    
				objMap.put("message", "Venues list fetched successfully.");
				objMap.put("results", listBussinesses);
				String jsonInString = mapper.writeValueAsString(objMap);
				return new ResponseEntity<String>(jsonInString,HttpStatus.OK);
			}
		}catch(Exception e){
			e.printStackTrace();
			objMap.put("message", e.getMessage());
			String jsonInString = mapper.writeValueAsString(objMap);
			return new ResponseEntity<String>(jsonInString,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}*/
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "getBussinessesDeals", method = RequestMethod.POST,
			produces = "application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<String> getBussinessesDeals(@RequestHeader("secretKey") String secretKey,
			@RequestHeader("version") String version ,
			@RequestHeader("deviceType") String deviceType ,
			@RequestBody String jsonStr,HttpServletRequest request) 
			throws JSONException, JsonGenerationException, JsonMappingException, IOException {
		Map<String, Object> objMap = new HashMap<String,Object>();
		ObjectMapper mapper = new ObjectMapper();
		try{
			AppVersion appVersion = userServices.getAppVersion(deviceType);
			if(appVersion.getVersion().equals(version)){
				System.out.println("Secret Key >>>>> "+secretKey);
				Users objUser= userServices.checkSecrteKey(secretKey);
				boolean notRegistered=false;
				if(objUser == null){
					if(secretKey.equals(Commons.secretString))
							notRegistered=true;
				}
				if(objUser == null && !notRegistered){	
					objMap.put("message", "Session has been expired. Please login again to procced.");
					String jsonInString = mapper.writeValueAsString(objMap);
					return new ResponseEntity<String>(jsonInString,HttpStatus.UNAUTHORIZED);	
				}else{
					
					System.out.println("JSON Response >>>>>>>"+jsonStr);
					JSONObject jsonObject = new JSONObject(jsonStr);
					DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
					DateFormat dateFormat1 = new SimpleDateFormat("HH:mm");
					DateFormat dateFormat3 = new SimpleDateFormat("hh:mm a");
					boolean openNow=false;
					int meal=0;
					double distance=5;
					int day=0;
					String text="";
					if(jsonObject.has("distance")){
						distance = jsonObject.getInt("distance");
					}
					
					if(jsonObject.has("text")){
						text = jsonObject.getString("text").toUpperCase();
					}
					
					if(jsonObject.has("open")){
						if (jsonObject.getInt("open")==1) {
							openNow=true;
						}
					}
					
					if(jsonObject.has("day")){
						day = jsonObject.getInt("day");
					}
					
					if(jsonObject.has("meal")){
						meal = jsonObject.getInt("meal");
					}
					
					String condition="";
					double kilometers =distance * 1.609344;
				  	System.out.println("distance in km "+kilometers);
				    double distance1 = (kilometers / 40000) * 360;
				    System.out.println("Distance in killo meters >> "+distance1);
				    if(!text.equals(""))
				    condition = " where upper(name) like '%"+text+"%'  and latitude<("+jsonObject.getDouble("latitude")+" + "+distance1+")"
				    		+ " and latitude > ("+jsonObject.getDouble("latitude")+" - "+distance1+")"
				    				+ " and longitude < ("+jsonObject.getDouble("longitude")+" + "+distance1+")"
				    						+ " and longitude > ("+jsonObject.getDouble("longitude")+" - "+distance1+")";
				    else
				    	condition = " where latitude<("+jsonObject.getDouble("latitude")+" + "+distance1+")"
						    		+ " and latitude > ("+jsonObject.getDouble("latitude")+" - "+distance1+")"
						    				+ " and longitude < ("+jsonObject.getDouble("longitude")+" + "+distance1+")"
						    						+ " and longitude > ("+jsonObject.getDouble("longitude")+" - "+distance1+")";
				    
				    List<Map<String, Object>> listBussinesses = new ArrayList<Map<String,Object>>();
				    List<Bussiness> bussinesses = dealsServices.getBussiness(condition);
				    for (Bussiness bussiness : bussinesses) {
				    	if(openNow){
				    		boolean hasOpen=false;
				    		Map<String, Object> objBussiness = new HashMap<String,Object>();
							
				    		if(!notRegistered){
				    		FavouriteBussiness  favouriteBussiness=dealsServices.getFavouriteBussinessByUserAndBussiness(objUser.getId(),
				    				bussiness.getId());
					    		if(favouriteBussiness != null)
					    			objBussiness.put("isFavorite",true);
					    		else
					    			objBussiness.put("isFavorite",false);
				    		}else{
				    			objBussiness.put("isFavorite",false);
				    		}
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
							
							DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
							
							Date date = new Date();
							boolean isClosedNow=true;
							List<BussinessDays> bussinessDays = dealsServices.getBussinessDays(bussiness);
							for (BussinessDays bussinessDays2 : bussinessDays) {
								if(bussinessDays2.getDay() == date.getDay()){
									
									String end = bussinessDays2.getEnd();
									String start = bussinessDays2.getStart();
									
									String timing = dateFormat.format(dateFormat1.parse(end.substring(0, 2)+":"+end.substring(2)));
									
									objBussiness.put("closing", "untill "+timing);
									objBussiness.put("closingTime", timing);
									
									/*if(!TimeZone.getDefault().getID().equals(jsonObject.getString("timeZone"))){*/
										
									Calendar cal = Calendar.getInstance();
									cal.setTimeZone(TimeZone.getTimeZone(jsonObject.getString("timeZone")));
	
									int hours =Integer.parseInt(cal.get(Calendar.HOUR_OF_DAY)+"");
									int mintues = Integer.parseInt(cal.get(Calendar.MINUTE)+"");
									System.out.println("Hours and Mintues >>>>> "+cal.get(Calendar.HOUR_OF_DAY)+"    "+
											cal.get(Calendar.MINUTE));
									
									int todayNum=0;
									
									if(mintues<10){
										todayNum =Integer.parseInt(hours+"0"+mintues); 
									}else{
										todayNum =Integer.parseInt(hours+""+mintues);
									}
									
									System.out.println("Today >>>>> "+todayNum);
									
									System.out.println("starts and End >>>>> "+start+"  "+end);
									int end1=0;
									if(Integer.parseInt(start)>Integer.parseInt(end)){
										
										end1=Integer.parseInt(end)+2400;
										if((todayNum>Integer.parseInt(start) && todayNum<end1) ||
												(todayNum>0 && todayNum<Integer.parseInt(end) )){
											isClosedNow=false;
											if(jsonObject.has("open")){
												int open = jsonObject.getInt("open");
												if(open==1){
													hasOpen=true;
													openNow=true;
												}
											}
										}else{
											if(jsonObject.has("open")){
												int open = jsonObject.getInt("open");
												if(open==1){
													hasOpen=false;
													openNow=true;
												}
											}
										}
									
									}else{
										if((todayNum>Integer.parseInt(start) && todayNum<Integer.parseInt(end))){
											isClosedNow=false;
											if(jsonObject.has("open")){
												int open = jsonObject.getInt("open");
												if(open==1){
													hasOpen=true;
													openNow=true;
												}
											}
										}else{
											if(jsonObject.has("open")){
												int open = jsonObject.getInt("open");
												if(open==1){
													hasOpen=false;
													openNow=true;
												}
											}
										}
									}
									
									
										
								/*	}else{
										String today = dateFormat2.format(new Date());
										Date date2 = new Date();
										Date endDate =sdf.parse(today+" "+end.substring(0, 2)+":"+end.substring(2));
										
										Date startDate =sdf.parse(today+" "+start.substring(0, 2)+":"+start.substring(2));
										Calendar cal = Calendar.getInstance();
										if(startDate.after(endDate)){
											System.out.println("Start time is greater then End");
											cal.setTime(endDate);
											cal.add(Calendar.DATE, 1);
											endDate = cal.getTime();
										}
										
										if(date2.after(startDate) && date2.before(endDate)){
											isClosedNow=false;
											if(jsonObject.has("open")){
												int open = jsonObject.getInt("open");
												if(open==1){
													openNow=true;
												}
											}
										}else{
											if(jsonObject.has("open")){
												int open = jsonObject.getInt("open");
												if(open==1){
													openNow=true;
												}
											}
										}
									}*/
								}
							}
							objBussiness.put("is_closed", isClosedNow);
							
							List<Map<String, Object>> listDeals = new ArrayList<Map<String,Object>>();
							List<Deals> deals = dealsServices.getDealsByBussiness(bussiness.getId());
							boolean  hasDays = false;
							boolean hasCategories = false;
							boolean hasBoth=false;
							for (Deals deals2 : deals) {
								System.out.println("Deal is >> "+deals2.getTitle());
								List<DealImages> dealImages = dealsServices.getDealImagesByDeals(deals2.getId());
								for (DealImages dealImages2 : dealImages) {
									images.add("http://112.196.97.229:8080/Foofulu"+dealImages2.getImage());
								}
								System.out.println(" Out Side >>>> "+hasBoth);
								System.out.println("Out Side  >>>> "+hasBoth);
								System.out.println("Out Side  >>>> "+hasBoth);
	
								if(meal>0 && day>0){
									DealsCategories dealCategroy = dealsServices.getDealsCategoriesByDealIDAndCategory(
											deals2.getId(),meal);
									DealDays dealDays = dealsServices.getDealsbyDealAndDay(
											deals2.getId(), day);
									if(dealCategroy != null && dealDays != null){
										Map<String, Object> objDeals = new HashMap<String, Object>();
										objDeals.put("title", deals2.getTitle());
										objDeals.put("id", deals2.getId());
										hasBoth = true;
										listDeals.add(objDeals);
									}
								}else if(meal>0){
									DealsCategories dealCategroy = dealsServices.getDealsCategoriesByDealIDAndCategory(
											deals2.getId(),meal);
									if(dealCategroy != null){
										Map<String, Object> objDeals = new HashMap<String, Object>();
										objDeals.put("title", deals2.getTitle());
										objDeals.put("id", deals2.getId());
										hasCategories=true;
										listDeals.add(objDeals);
									}
								}else if(day>0){
									DealDays dealDays = dealsServices.getDealsbyDealAndDay(
											deals2.getId(), day);
									if(dealDays != null){
										Map<String, Object> objDeals = new HashMap<String, Object>();
										objDeals.put("title", deals2.getTitle());
										objDeals.put("id", deals2.getId());
										hasDays=true;
										listDeals.add(objDeals);
									}
								}else{
									Map<String, Object> objDeals = new HashMap<String, Object>();
									objDeals.put("title", deals2.getTitle());
									objDeals.put("id", deals2.getId());
									hasBoth=true;
									listDeals.add(objDeals);
								}
								
								
							}
							objBussiness.put("photos", images);
								if(hasBoth && hasOpen){
								objBussiness.put("deals", listDeals);
								listBussinesses.add(objBussiness);
							}else if(day<1 && hasCategories && hasOpen ){
								objBussiness.put("deals", listDeals);
								listBussinesses.add(objBussiness);
							}else if(meal<1 && hasDays && hasOpen){
								objBussiness.put("deals", listDeals);
								listBussinesses.add(objBussiness);
							}
						}else{
							Map<String, Object> objBussiness = new HashMap<String,Object>();
							
				    		if(!notRegistered){
				    		FavouriteBussiness  favouriteBussiness=dealsServices.getFavouriteBussinessByUserAndBussiness(objUser.getId(),
				    				bussiness.getId());
					    		if(favouriteBussiness != null)
					    			objBussiness.put("isFavorite",true);
					    		else
					    			objBussiness.put("isFavorite",false);
				    		}else{
				    			objBussiness.put("isFavorite",false);
				    		}
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
							
							
							
							Date date = new Date();
							List<BussinessDays> bussinessDays = dealsServices.getBussinessDays(bussiness);
							boolean isClosedNow=true;
							for (BussinessDays bussinessDays2 : bussinessDays) {
								if(bussinessDays2.getDay() == date.getDay()){
									String end = bussinessDays2.getEnd();
									String start = bussinessDays2.getStart();
									
									String timing = dateFormat3.format(dateFormat1.parse(end.substring(0, 2)+":"+end.substring(2)));
									objBussiness.put("closing", "untill "+timing);
					/*				
									if(!TimeZone.getDefault().getID().equals(jsonObject.getString("timeZone"))){*/
										
									Calendar cal = Calendar.getInstance();
									cal.setTimeZone(TimeZone.getTimeZone(jsonObject.getString("timeZone")));
	
									int hours =Integer.parseInt(cal.get(Calendar.HOUR_OF_DAY)+"");
									int mintues = Integer.parseInt(cal.get(Calendar.MINUTE)+"");
									System.out.println("Hours and Mintues >>>>> "+cal.get(Calendar.HOUR_OF_DAY)+"    "+
											cal.get(Calendar.MINUTE));
									
									int todayNum=0;
									
									if(mintues<10){
										todayNum =Integer.parseInt(hours+"0"+mintues); 
									}else{
										todayNum =Integer.parseInt(hours+""+mintues);
									}
									
									System.out.println("Today >>>>> "+todayNum);
									
									System.out.println("starts and End >>>>> "+start+"  "+end);
									int end1=0;
									if(Integer.parseInt(start)>Integer.parseInt(end)){
										
										end1=Integer.parseInt(end)+2400;
										if((todayNum>Integer.parseInt(start) && todayNum<end1) ||
												(todayNum>0 && todayNum<Integer.parseInt(end) )){
											isClosedNow=false;
											if(jsonObject.has("open")){
												int open = jsonObject.getInt("open");
												if(open==1){
													openNow=true;
												}
											}
										}else{
											if(jsonObject.has("open")){
												int open = jsonObject.getInt("open");
												if(open==1){
													openNow=true;
												}
											}
										}
									
									}else{
										if((todayNum>Integer.parseInt(start) && todayNum<Integer.parseInt(end))){
											isClosedNow=false;
											if(jsonObject.has("open")){
												int open = jsonObject.getInt("open");
												if(open==1){
													openNow=true;
												}
											}
										}else{
											if(jsonObject.has("open")){
												int open = jsonObject.getInt("open");
												if(open==1){
													openNow=true;
												}
											}
										}
									}
									
								/*	}else{
										String today = dateFormat2.format(new Date());
										Date date2 = new Date();
										Date endDate =sdf.parse(today+" "+end.substring(0, 2)+":"+end.substring(2));
									
										Date startDate =sdf.parse(today+" "+start.substring(0, 2)+":"+start.substring(2));
										Calendar cal = Calendar.getInstance();
										if(startDate.after(endDate)){
											System.out.println("Start time is greater then End");
											cal.setTime(startDate);
											cal.add(Calendar.DATE, -1);
											startDate = cal.getTime();
										}
										
										if(date2.after(startDate) && date2.before(endDate)){
											isClosedNow=false;
											System.out.println("startDate >>>> "+startDate+" endDate >>> "+endDate+" Current >>  "+today);
											System.out.println("startDate >>>> "+startDate+" endDate >>> "+endDate+" Current >>  "+today);
											System.out.println("startDate >>>> "+startDate+" endDate >>> "+endDate+" Current >>  "+today);
											if(jsonObject.has("open")){
												int open = jsonObject.getInt("open");
												if(open==1){
													openNow=true;
												}
											}
										}else{
											if(jsonObject.has("open")){
												int open = jsonObject.getInt("open");
												if(open==1){
													openNow=true;
												}
											}
											objBussiness.put("is_closed", true);
										}
									}*/
								}
							}
							objBussiness.put("is_closed", isClosedNow);
							List<Map<String, Object>> listDeals = new ArrayList<Map<String,Object>>();
							List<Deals> deals = dealsServices.getDealsByBussiness(bussiness.getId());
							boolean  hasDays = false;
							boolean hasCategories = false;
							boolean hasBoth=false;
							for (Deals deals2 : deals) {
								List<DealImages> dealImages = dealsServices.getDealImagesByDeals(deals2.getId());
								for (DealImages dealImages2 : dealImages) {
									images.add("http://112.196.97.229:8080/Foofulu"+dealImages2.getImage());
								}
								
								if(meal>0 && day>0){
									DealsCategories dealCategroy = dealsServices.getDealsCategoriesByDealIDAndCategory(
											deals2.getId(),meal);
									DealDays dealDays = dealsServices.getDealsbyDealAndDay(
											deals2.getId(), day);
									if(dealCategroy != null && dealDays != null){
										Map<String, Object> objDeals = new HashMap<String, Object>();
										objDeals.put("title", deals2.getTitle());
										objDeals.put("id", deals2.getId());
										hasBoth= true;
										listDeals.add(objDeals);
									}
								}else if(meal>0){
									DealsCategories dealCategroy = dealsServices.getDealsCategoriesByDealIDAndCategory(
											deals2.getId(),meal);
									if(dealCategroy != null){
										Map<String, Object> objDeals = new HashMap<String, Object>();
										objDeals.put("title", deals2.getTitle());
										objDeals.put("id", deals2.getId());
										hasCategories=true;
										listDeals.add(objDeals);
									}
								}else if(day>0){
									DealDays dealDays = dealsServices.getDealsbyDealAndDay(
											deals2.getId(), day);
									if(dealDays != null){
										Map<String, Object> objDeals = new HashMap<String, Object>();
										objDeals.put("title", deals2.getTitle());
										objDeals.put("id", deals2.getId());
										hasDays=true;
										listDeals.add(objDeals);
									}
								}else{
									Map<String, Object> objDeals = new HashMap<String, Object>();
									objDeals.put("title", deals2.getTitle());
									objDeals.put("id", deals2.getId());
									hasBoth=true;
									listDeals.add(objDeals);
								}
							}
							
							objBussiness.put("photos", images);
							
							if(hasBoth){
								objBussiness.put("deals", listDeals);
								listBussinesses.add(objBussiness);	
							}else if(day<1 && hasCategories){
								objBussiness.put("deals", listDeals);
								listBussinesses.add(objBussiness);
							}else if(meal<1 && hasDays){
								objBussiness.put("deals", listDeals);
								listBussinesses.add(objBussiness);
							}
						}
					}
				    
				    Collections.sort(listBussinesses, new Comparator<Map<String, Object>>() {
						@Override
						public int compare(Map<String, Object> o1,
								Map<String, Object> o2) {
							if(o1.get("distance") instanceof Double){
								
								Double d1=(Double)o1.get("distance");
								Double d2=(Double)o2.get("distance");
								if (d1>d1){
								 return 1;
								}else if (d1<d2){
						            return -1;
								}else{
									return 0;
								}
							}else{
								return 0;
							}
								
						}
					});
				    
				    
				    
					objMap.put("message", "Venues list fetched successfully.");
					objMap.put("results", listBussinesses);
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
	
	@RequestMapping(value = "getDealDetails", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<String> getDealDetails(@RequestHeader("secretKey") String secretKey,
			@RequestHeader("version") String version ,
			@RequestHeader("deviceType") String deviceType ,
			@RequestBody String jsonStr,HttpServletRequest request) 
			throws JSONException, JsonGenerationException, JsonMappingException, IOException {
		Map<String, Object> objMap = new HashMap<String,Object>();
		ObjectMapper mapper = new ObjectMapper();
		try{
			AppVersion appVersion = userServices.getAppVersion(deviceType);
			if(appVersion.getVersion().equals(version)){
				Users objUser= userServices.checkSecrteKey(secretKey);
				boolean notRegistered=false;
				if(objUser == null){
					if(secretKey.equals(Commons.secretString))
							notRegistered=true;
				}
				if(objUser == null && !notRegistered){	
					objMap.put("message", "Session has been expired. Please login again to procced.");
					String jsonInString = mapper.writeValueAsString(objMap);
					return new ResponseEntity<String>(jsonInString,HttpStatus.UNAUTHORIZED);	
				}else{
					System.out.println("JSON Response >>>>>>>"+jsonStr);
					JSONObject jsonObject = new JSONObject(jsonStr);
					Deals deals = dealsServices.getDealById(jsonObject.getLong("dealId"));
					
					Map<String, Object> objBussiness = new HashMap<String, Object>();
					objBussiness.put("name", deals.getBussiness().getName());
					objBussiness.put("id", deals.getBussiness().getId());
					
					
					List<DealDays>  dealDays = dealsServices.getDays(deals.getId());
					List<Map<String ,Object>> daysList = new ArrayList<Map<String,Object>>();
					for (DealDays dealDays2 : dealDays) {
						Map<String, Object> objDay = new HashMap<String, Object>();
						objDay.put("day", dealDays2.getDay().getDay());
						objDay.put("id", dealDays2.getDay().getId());
						daysList.add(objDay);
					}
					
					List<Map<String ,Object>> dealImages1 = new ArrayList<Map<String,Object>>();
					List<DealImages> dealImages = dealsServices.getDealImagesByDeals(jsonObject.getLong("dealId"));
					for (DealImages dealImages2 : dealImages) {
						Map<String, Object> objImage = new HashMap<String, Object>();
						objImage.put("image", dealImages2.getImage());
						objImage.put("id",  dealImages2.getId());
						dealImages1.add(objImage);
					}
					
					List<Map<String ,Object>> dealcategories1 = new ArrayList<Map<String,Object>>();
					List<DealsCategories> dealsCategories = dealsServices.getDealsCategories(jsonObject.getLong("dealId"));
					for (DealsCategories dealsCategories2 : dealsCategories) {
						Map<String, Object> objCategory = new HashMap<String, Object>();
						objCategory.put("id", dealsCategories2.getMealCategories().getId());
						objCategory.put("category",  dealsCategories2.getMealCategories().getCategory());
						dealcategories1.add(objCategory);
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
					}else{
						objMap.put("vrified", 0);
					}
					
					if(!notRegistered){
					VerifiedDeals verifiedDeals2 = dealsServices.getVerifiedDeals(deals.getId(), objUser.getId());
					
						if(verifiedDeals2 != null)
							objMap.put("verifiedByMe", true);
						else
							objMap.put("verifiedByMe", false);
					
					}else{
						objMap.put("verifiedByMe", false);
					}
					
					if(!verifiedDeals.isEmpty()){
						
						Map<String, Object> objLastVerified = new HashMap<String, Object>();
						String name =verifiedDeals.get((verifiedDeals.size()-1)).getUser().getName();
						long id = verifiedDeals.get((verifiedDeals.size()-1)).getUser().getId();
						objLastVerified.put("id", id);
						objLastVerified.put("name", name);
						if(verifiedDeals.get((verifiedDeals.size()-1)) != null)
							objLastVerified.put("time", prettyTime.format(verifiedDeals.get((verifiedDeals.size()-1)).getVerifiedAt()));
						else
							objLastVerified.put("time", "");
						
						objMap.put("lastVerifiedBy", objLastVerified);
						
					}else{
						Map<String, Object> objLastVerified = new HashMap<String, Object>();
						objMap.put("lastVerifiedBy", objLastVerified);
					}
					
					objMap.put("business", objBussiness);	
					objMap.put("dealcategories", dealcategories1);
					objMap.put("updatedBy", objupdatedBy);
					objMap.put("objAddedBy", objAddedBy);
					objMap.put("days", daysList);
					objMap.put("images", dealImages1);
					objMap.put("title",deals.getTitle());
					objMap.put("dealId",deals.getId());
					objMap.put("sharingLink", "#/DealSharing?dealId="+deals.getId());
					objMap.put("message", "Deal fetched successfully.");
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
	
	/***** 
	 {
	 	"dealId" : 2
	 }
	 ******/
	@RequestMapping(value = "verifyDeal", method = RequestMethod.POST,
			produces = "application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<String> verifyDeal(@RequestHeader("secretKey") String secretKey ,
			@RequestHeader("version") String version ,
			@RequestHeader("deviceType") String deviceType ,
			@RequestBody String jsonStr,HttpServletRequest request) 
			throws JSONException, JsonGenerationException, JsonMappingException, IOException {
		Map<String, Object> objMap = new HashMap<String,Object>();
		ObjectMapper mapper = new ObjectMapper();
		try{
			AppVersion appVersion = userServices.getAppVersion(deviceType);
			if(appVersion.getVersion().equals(version)){
				Users objUser= userServices.checkSecrteKey(secretKey);
				PrettyTime prettyTime = new PrettyTime();
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
					System.out.println("JSON Response >>>>>>>"+jsonStr);
					JSONObject jsonObject = new JSONObject(jsonStr);
					Deals deals = dealsServices.getDealById(jsonObject.getLong("dealId"));
					
					VerifiedDeals verifiedDeals = dealsServices.getVerifiedDeals(deals.getId(),objUser.getId());
					if(verifiedDeals == null){
						VerifiedDeals verifiedDeals2  = new VerifiedDeals();
						verifiedDeals2.setDeal(deals);
						verifiedDeals2.setUser(objUser);
						verifiedDeals2.setVerifiedAt(new Date());
						VerifiedDeals verifiedDeals3 = dealsServices.addVerifiedDeal(verifiedDeals2);
						if(verifiedDeals3 != null){
							
							List<VerifiedDeals> verifiedDeals1 = dealsServices.getVerifiedDealsByDeal(deals.getId());
							if(!verifiedDeals1.isEmpty()){	
								objMap.put("vrified", verifiedDeals1.size());
							}else{
								objMap.put("vrified", 0);
							}
							
							if(!verifiedDeals1.isEmpty()){
								
								Map<String, Object> objLastVerified = new HashMap<String, Object>();
								String name =verifiedDeals1.get((verifiedDeals1.size()-1)).getUser().getName();
								long id = verifiedDeals1.get((verifiedDeals1.size()-1)).getUser().getId();
								objLastVerified.put("id", id);
								objLastVerified.put("name", name);
								if(verifiedDeals1.get((verifiedDeals1.size()-1)).getVerifiedAt() != null)
									objLastVerified.put("time", prettyTime.format(verifiedDeals1.get((verifiedDeals1.size()-1)).getVerifiedAt()));
								else
									objLastVerified.put("time", "");
								objMap.put("lastVerifiedBy", objLastVerified);
								
							}else{
								Map<String, Object> objLastVerified = new HashMap<String, Object>();
								objMap.put("lastVerifiedBy", objLastVerified);
							}
							
							objMap.put("message", "Deal verified successfully.");
							objMap.put("status","1");
							String jsonInString = mapper.writeValueAsString(objMap);
							return new ResponseEntity<String>(jsonInString,HttpStatus.OK);
						}else{
							objMap.put("message", "Something went wrong.");
							String jsonInString = mapper.writeValueAsString(objMap);
							return new ResponseEntity<String>(jsonInString,HttpStatus.INTERNAL_SERVER_ERROR);
						}
					}else{
						int i = dealsServices.deleteVerifiedDeal(verifiedDeals);
						if(i==1){
							List<VerifiedDeals> verifiedDeals1 = dealsServices.getVerifiedDealsByDeal(deals.getId());
							if(!verifiedDeals1.isEmpty()){	
								objMap.put("vrified", verifiedDeals1.size());
							}else{
								objMap.put("vrified", 0);
							}
							
							if(!verifiedDeals1.isEmpty()){
								
								Map<String, Object> objLastVerified = new HashMap<String, Object>();
								String name =verifiedDeals1.get((verifiedDeals1.size()-1)).getUser().getName();
								long id = verifiedDeals1.get((verifiedDeals1.size()-1)).getUser().getId();
								objLastVerified.put("id", id);
								objLastVerified.put("name", name);
								if(verifiedDeals1.get((verifiedDeals1.size()-1)).getVerifiedAt() != null)
									objLastVerified.put("time", prettyTime.format(verifiedDeals1.get((verifiedDeals1.size()-1)).getVerifiedAt()));
								else
									objLastVerified.put("time", "");
								objMap.put("lastVerifiedBy", objLastVerified);
								
							}else{
								Map<String, Object> objLastVerified = new HashMap<String, Object>();
								objMap.put("lastVerifiedBy", objLastVerified);
							}
							
							objMap.put("message", "Deal unverified successfully.");
							objMap.put("status", "0");
							String jsonInString = mapper.writeValueAsString(objMap);
							return new ResponseEntity<String>(jsonInString,HttpStatus.OK);
						}else{
							objMap.put("message", "Something went wrong.");
							String jsonInString = mapper.writeValueAsString(objMap);
							return new ResponseEntity<String>(jsonInString,HttpStatus.INTERNAL_SERVER_ERROR);
						}
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
	
	
	/*@RequestMapping(value = "getBussinessDetails", method = RequestMethod.POST,
			produces = "application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<String> getBussinessDetails(@RequestHeader("secretKey") String secretKey ,@RequestBody String jsonStr,HttpServletRequest request) 
			throws JSONException, JsonGenerationException, JsonMappingException, IOException {
		Map<String, Object> objMap = new HashMap<String,Object>();
		ObjectMapper mapper = new ObjectMapper();
		try{
			Users objUser= userServices.checkSecrteKey(secretKey);
			System.out.println("Secret Key >>>>> "+secretKey);
			if(objUser == null){			
				objMap.put("message", "Session has been expired. Please login again to procced.");
				String jsonInString = mapper.writeValueAsString(objMap);
				return new ResponseEntity<String>(jsonInString,HttpStatus.UNAUTHORIZED);	
			}else{
				System.out.println("JSON Response >>>>>>>"+jsonStr);
				JSONObject jsonObject = new JSONObject(jsonStr);
				Bussiness bussiness = dealsServices.getBussinessById(
						jsonObject.getLong("bussinessId"));
				
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
				
				Set<Map<String, Object>> listDays = new HashSet<Map<String,Object>>();
				List<Deals> deals = dealsServices.getDealsByBussiness(bussiness.getId());
			
				for (Deals deals2 : deals) {
					for (int i = 1; i <= 7; i++) {
						DealDays dealDays = dealsServices.getDealsbyDealAndDay(
								deals2.getId(), i);
						if(dealDays != null){
							Map<String, Object> objDay = new HashMap<String, Object>();
							objDay.put("dayId", dealDays.getDay().getId());
							objDay.put("day", dealDays.getDay().getDay());
							listDays.add(objDay);
						}	
					}
				}
				objBussiness.put("days", listDays);
				
				objMap.put("business", objBussiness);
				objMap.put("message", "Deal fetched successfully.");
				String jsonInString = mapper.writeValueAsString(objMap);
				return new ResponseEntity<String>(jsonInString,HttpStatus.OK);
			}
		}catch(Exception e){
			e.printStackTrace();
			objMap.put("message", e.getMessage());
			String jsonInString = mapper.writeValueAsString(objMap);
			return new ResponseEntity<String>(jsonInString,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}*/
	
	@RequestMapping(value = "getBussinessDetails", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<String> getBussinessDetails(@RequestHeader("secretKey") String secretKey ,
			@RequestHeader("version") String version ,
			@RequestHeader("deviceType") String deviceType ,
			@RequestBody String jsonStr,HttpServletRequest request) 
			throws JSONException, JsonGenerationException, JsonMappingException, IOException {
		Map<String, Object> objMap = new HashMap<String,Object>();
		ObjectMapper mapper = new ObjectMapper();
		try{
			AppVersion appVersion = userServices.getAppVersion(deviceType);
			if(appVersion.getVersion().equals(version)){
				Users objUser= userServices.checkSecrteKey(secretKey);
				boolean notRegistered=false;
				if(objUser == null){
					if(secretKey.equals(Commons.secretString))
							notRegistered=true;
				}
				if(objUser == null && !notRegistered){		
					objMap.put("message", "Session has been expired. Please login again to procced.");
					String jsonInString = mapper.writeValueAsString(objMap);
					return new ResponseEntity<String>(jsonInString,HttpStatus.UNAUTHORIZED);	
				}else{
					DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
					System.out.println("JSON Response >>>>>>>"+jsonStr);
					JSONObject jsonObject = new JSONObject(jsonStr);
							
					Bussiness bussiness = dealsServices.getBussinessById(
							jsonObject.getLong("bussinessId"));
					
					Map<String, Object> objBussiness = new HashMap<String,Object>();
					
					if(!notRegistered){
					FavouriteBussiness  favouriteBussiness=dealsServices.getFavouriteBussinessByUserAndBussiness(objUser.getId(),
		    				bussiness.getId());
			    		if(favouriteBussiness != null)
			    			objBussiness.put("isFavorite",true);
			    		else
			    			objBussiness.put("isFavorite",false);
					}else{
						objBussiness.put("isFavorite",false);
					}
					
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
					
					Set<String> images = new HashSet<String>();
					List<BussinessImages> bussinessImages =  dealsServices.getBussinessImages(bussiness);
					for (int i = 0; i < bussinessImages.size(); i++) {
						images.add(bussinessImages.get(i).getImage());
					}
					
					boolean openNow= false;
					DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
					DateFormat dateFormat1 = new SimpleDateFormat("HH:mm");
					Date date = new Date();
					List<BussinessDays> bussinessDays = dealsServices.getBussinessDays(bussiness);
					boolean isClosedNow=true;
					for (BussinessDays bussinessDays2 : bussinessDays) {
						System.out.println("Days >>>>>> "+bussinessDays2.getDay()+" >>> "+date.getDay());
						if(bussinessDays2.getDay() == date.getDay()){
							String end = bussinessDays2.getEnd();
							String start = bussinessDays2.getStart();
							
							System.out.println("date sub string >>>> "+start+" >>>> "+end);
							
							String timing = dateFormat.format(dateFormat1.parse(end.substring(0, 2)+":"+end.substring(2)));
							System.out.println("date sub string >>>> "+timing);
							objBussiness.put("closing", "untill "+timing);
							
							Calendar cal = Calendar.getInstance();
							cal.setTimeZone(TimeZone.getTimeZone(jsonObject.getString("timeZone")));
	
							int hours =Integer.parseInt(cal.get(Calendar.HOUR_OF_DAY)+"");
							int mintues = Integer.parseInt(cal.get(Calendar.MINUTE)+"");
							System.out.println("Hours and Mintues >>>>> "+cal.get(Calendar.HOUR_OF_DAY)+"    "+
									cal.get(Calendar.MINUTE));
							
							int todayNum=0;
							
							if(mintues<10){
								todayNum =Integer.parseInt(hours+"0"+mintues); 
							}else{
								todayNum =Integer.parseInt(hours+""+mintues);
							}
							
							System.out.println("Today >>>>> "+todayNum);
							
							System.out.println("starts and End >>>>> "+start+"  "+end);
							int end1=0;
							if(Integer.parseInt(start)>Integer.parseInt(end)){
								
								end1=Integer.parseInt(end)+2400;
								if((todayNum>Integer.parseInt(start) && todayNum<end1) ||
										(todayNum>0 && todayNum<Integer.parseInt(end) )){
									isClosedNow=false;
									if(jsonObject.has("open")){
										int open = jsonObject.getInt("open");
										if(open==1){
											openNow=true;
										}
									}
								}else{
									if(jsonObject.has("open")){
										int open = jsonObject.getInt("open");
										if(open==1){
											openNow=true;
										}
									}
								}
							
							}else{
								if((todayNum>Integer.parseInt(start) && todayNum<Integer.parseInt(end))){
									isClosedNow=false;
									if(jsonObject.has("open")){
										int open = jsonObject.getInt("open");
										if(open==1){
											openNow=true;
										}
									}
								}else{
									if(jsonObject.has("open")){
										int open = jsonObject.getInt("open");
										if(open==1){
											openNow=true;
										}
									}
								}
							}
						}
					}
					
					objBussiness.put("is_closed", isClosedNow);
					List<Days> days=dealsServices.getDays();
					List<Map<String, Object>> listDaysDeals = new ArrayList<Map<String,Object>>();
					
					List<Map<String, Object>> listDeals1 = new ArrayList<Map<String,Object>>();
					List<DealDays> dealDays5 = dealsServices.getDaysByBussinesAndDay(bussiness.getId(),jsonObject.getInt("day"));		
					Days days3 = dealsServices.getDayById(jsonObject.getInt("day"));
					
					for(DealDays dealDays2 : dealDays5) {
						
						System.out.println("Deals Title >>> "+dealDays2.getDeal().getTitle());
						
						Map<String, Object> objDeal = new HashMap<String, Object>();
						Map<String, Object> objBussiness1 = new HashMap<String, Object>();
						objBussiness1.put("name", dealDays2.getDeal().getBussiness().getName());
						objBussiness1.put("id", dealDays2.getDeal().getBussiness().getId());
						
						
						List<DealDays>  dealDays1 = dealsServices.getDays(dealDays2.getDeal().getId());
						List<Map<String ,Object>> daysList = new ArrayList<Map<String,Object>>();
						for (DealDays dealDays3 : dealDays1) {
							Map<String, Object> objDay1 = new HashMap<String, Object>();
							objDay1.put("day", dealDays3.getDay().getDay());
							objDay1.put("id", dealDays3.getDay().getId());
							daysList.add(objDay1);
						}
						
						List<Map<String ,Object>> dealImages1 = new ArrayList<Map<String,Object>>();
						List<DealImages> dealImages = dealsServices.getDealImagesByDeals(
								dealDays2.getDeal().getId());
						for (DealImages dealImages2 : dealImages) {
							Map<String, Object> objImage = new HashMap<String, Object>();
							objImage.put("image", dealImages2.getImage());
							objImage.put("id",  dealImages2.getId());
							dealImages1.add(objImage);
						}
						
						List<Map<String ,Object>> dealcategories1 = new ArrayList<Map<String,Object>>();
						List<DealsCategories> dealsCategories = dealsServices.getDealsCategories(dealDays2.getDeal().getId());
						for (DealsCategories dealsCategories2 : dealsCategories) {
							Map<String, Object> objCategory = new HashMap<String, Object>();
							objCategory.put("id", dealsCategories2.getMealCategories().getId());
							objCategory.put("category",  dealsCategories2.getMealCategories().getCategory());
							dealcategories1.add(objCategory);
						}
						
						PrettyTime prettyTime = new PrettyTime();
						Map<String, Object> objAddedBy = new HashMap<String, Object>();
						objAddedBy.put("name", dealDays2.getDeal().getAddedBy().getName());
						objAddedBy.put("id", dealDays2.getDeal().getAddedBy().getId());
						objAddedBy.put("time", prettyTime.format(dealDays2.getDeal().getCreationTime()));
						
						Map<String, Object> objupdatedBy = new HashMap<String, Object>();
						if(dealDays2.getDeal().getUpdatedBy() != null){
							objupdatedBy.put("name", dealDays2.getDeal().getUpdatedBy().getName());
							objupdatedBy.put("id", dealDays2.getDeal().getUpdatedBy().getId());
							objupdatedBy.put("time", prettyTime.format(dealDays2.getDeal().getUpdationTime()));
						}
						
						List<VerifiedDeals> verifiedDeals = dealsServices.getVerifiedDealsByDeal(dealDays2.getDeal().getId());
						if(!verifiedDeals.isEmpty()){	
							objDeal.put("vrified", verifiedDeals.size());
						}else{
							objDeal.put("vrified", 0);
						}
						if(!notRegistered){
							VerifiedDeals verifiedDeals2 = dealsServices.getVerifiedDeals(dealDays2.getDeal().getId(), objUser.getId());
							
							if(verifiedDeals2 != null)
								objDeal.put("verifiedByMe", true);
							else
								objDeal.put("verifiedByMe", false);
						}else{
							objDeal.put("verifiedByMe", false);
						}
						
						if(!verifiedDeals.isEmpty()){
							
							Map<String, Object> objLastVerified = new HashMap<String, Object>();
							String name =verifiedDeals.get((verifiedDeals.size()-1)).getUser().getName();
							long id = verifiedDeals.get((verifiedDeals.size()-1)).getUser().getId();
							objLastVerified.put("id", id);
							objLastVerified.put("name", name);
							if(verifiedDeals.get((verifiedDeals.size()-1)).getVerifiedAt() != null)
								objLastVerified.put("time", prettyTime.format(verifiedDeals.get((verifiedDeals.size()-1)).getVerifiedAt()));
							else
								objLastVerified.put("time", "");
							objDeal.put("lastVerifiedBy", objLastVerified);
							
						}else{
							Map<String, Object> objLastVerified = new HashMap<String, Object>();
							objDeal.put("lastVerifiedBy", objLastVerified);
						}
						
						List<DealImages> dealImages6 = dealsServices.getDealImagesByDeals(dealDays2.getDeal().getId());
						for (DealImages dealImages2 : dealImages6) {
							images.add("http://112.196.97.229:8080/Foofulu"+dealImages2.getImage());
						}
						
						objDeal.put("business", objBussiness1);	
						objDeal.put("dealcategories", dealcategories1);
						objDeal.put("updatedBy", objupdatedBy);
						objDeal.put("objAddedBy", objAddedBy);
						objDeal.put("days", daysList);
						objDeal.put("images", dealImages1);
						objDeal.put("title",dealDays2.getDeal().getTitle());
						objDeal.put("dealId",dealDays2.getDeal().getId());
						listDeals1.add(objDeal);
					}
				
					if(listDeals1.size()>0){
						Map<String, Object> objDay = new HashMap<String, Object>();
						objDay.put("deals", listDeals1);
						objDay.put("day", days3.getDay());
						objDay.put("id", days3.getId());
						listDaysDeals.add(objDay);
					}
					
					
					
					for (Days days2 : days) {
						if(days2.getId()>jsonObject.getInt("day")){
							Map<String, Object> objDay = new HashMap<String, Object>();
							List<Map<String, Object>> listDeals = new ArrayList<Map<String,Object>>();
							List<DealDays> dealDays = dealsServices.getDaysByBussinesAndDay(bussiness.getId(),days2.getId());		
							
							for(DealDays dealDays2 : dealDays) {
								
								System.out.println("Deals Title >>> "+dealDays2.getDeal().getTitle());
								
								Map<String, Object> objDeal = new HashMap<String, Object>();
								Map<String, Object> objBussiness1 = new HashMap<String, Object>();
								objBussiness1.put("name", dealDays2.getDeal().getBussiness().getName());
								objBussiness1.put("id", dealDays2.getDeal().getBussiness().getId());
								
								
								List<DealDays>  dealDays1 = dealsServices.getDays(dealDays2.getDeal().getId());
								List<Map<String ,Object>> daysList = new ArrayList<Map<String,Object>>();
								for (DealDays dealDays3 : dealDays1) {
									Map<String, Object> objDay1 = new HashMap<String, Object>();
									objDay1.put("day", dealDays3.getDay().getDay());
									objDay1.put("id", dealDays3.getDay().getId());
									daysList.add(objDay1);
								}
								
								List<Map<String ,Object>> dealImages1 = new ArrayList<Map<String,Object>>();
								List<DealImages> dealImages = dealsServices.getDealImagesByDeals(
										dealDays2.getDeal().getId());
								for (DealImages dealImages2 : dealImages) {
									Map<String, Object> objImage = new HashMap<String, Object>();
									objImage.put("image", dealImages2.getImage());
									objImage.put("id",  dealImages2.getId());
									dealImages1.add(objImage);
								}
								
								List<Map<String ,Object>> dealcategories1 = new ArrayList<Map<String,Object>>();
								List<DealsCategories> dealsCategories = dealsServices.getDealsCategories(dealDays2.getDeal().getId());
								for (DealsCategories dealsCategories2 : dealsCategories) {
									Map<String, Object> objCategory = new HashMap<String, Object>();
									objCategory.put("id", dealsCategories2.getMealCategories().getId());
									objCategory.put("category",  dealsCategories2.getMealCategories().getCategory());
									dealcategories1.add(objCategory);
								}
								
								PrettyTime prettyTime = new PrettyTime();
								Map<String, Object> objAddedBy = new 	
										HashMap<String, Object>();
								objAddedBy.put("name", dealDays2.getDeal().getAddedBy().getName());
								objAddedBy.put("id", dealDays2.getDeal().getAddedBy().getId());
								objAddedBy.put("time", prettyTime.format(dealDays2.getDeal().getCreationTime()));
								
								Map<String, Object> objupdatedBy = new HashMap<String, Object>();
								if(dealDays2.getDeal().getUpdatedBy() != null){
									objupdatedBy.put("name", dealDays2.getDeal().getUpdatedBy().getName());
									objupdatedBy.put("id", dealDays2.getDeal().getUpdatedBy().getId());
									objupdatedBy.put("time", prettyTime.format(dealDays2.getDeal().getUpdationTime()));
								}
								
								List<VerifiedDeals> verifiedDeals = dealsServices.getVerifiedDealsByDeal(dealDays2.getDeal().getId());
								if(!verifiedDeals.isEmpty()){	
									objDeal.put("vrified", verifiedDeals.size());
								}else{
									objDeal.put("vrified", 0);
								}
								if(!notRegistered){
									VerifiedDeals verifiedDeals2 = dealsServices.getVerifiedDeals(dealDays2.getDeal().getId(), objUser.getId());
									
									if(verifiedDeals2 != null)
										objDeal.put("verifiedByMe", true);
									else
										objDeal.put("verifiedByMe", false);
								}else{
									objDeal.put("verifiedByMe", false);
								}
								
								if(!verifiedDeals.isEmpty()){
									
									Map<String, Object> objLastVerified = new HashMap<String, Object>();
									String name =verifiedDeals.get((verifiedDeals.size()-1)).getUser().getName();
									long id = verifiedDeals.get((verifiedDeals.size()-1)).getUser().getId();
									objLastVerified.put("id", id);
									objLastVerified.put("name", name);
									if(verifiedDeals.get((verifiedDeals.size()-1)).getVerifiedAt() != null)
										objLastVerified.put("time", prettyTime.format(verifiedDeals.get((verifiedDeals.size()-1)).getVerifiedAt()));
									else
										objLastVerified.put("time", "");
									objDeal.put("lastVerifiedBy", objLastVerified);
									
								}else{
									Map<String, Object> objLastVerified = new HashMap<String, Object>();
									objDeal.put("lastVerifiedBy", objLastVerified);
								}
								
								List<DealImages> dealImages6 = dealsServices.getDealImagesByDeals(dealDays2.getDeal().getId());
								for (DealImages dealImages2 : dealImages6) {
									images.add("http://112.196.97.229:8080/Foofulu"+dealImages2.getImage());
								}
								
								objDeal.put("business", objBussiness1);	
								objDeal.put("dealcategories", dealcategories1);
								objDeal.put("updatedBy", objupdatedBy);
								objDeal.put("objAddedBy", objAddedBy);
								objDeal.put("days", daysList);
								objDeal.put("images", dealImages1);
								objDeal.put("title",dealDays2.getDeal().getTitle());
								objDeal.put("dealId",dealDays2.getDeal().getId());
								listDeals.add(objDeal);
							}
						
							if(listDeals.size()>0){
								objDay.put("deals", listDeals);
								objDay.put("day", days2.getDay());
								objDay.put("id", days2.getId());
								listDaysDeals.add(objDay);
							}
					
						}
					}
					
					for (Days days2 : days) {
						if(days2.getId()<jsonObject.getInt("day")){
							Map<String, Object> objDay = new HashMap<String, Object>();
							List<Map<String, Object>> listDeals = new ArrayList<Map<String,Object>>();
							List<DealDays> dealDays = dealsServices.getDaysByBussinesAndDay(bussiness.getId(),days2.getId());		
							
							for(DealDays dealDays2 : dealDays) {
								
								System.out.println("Deals Title >>> "+dealDays2.getDeal().getTitle());
								
								Map<String, Object> objDeal = new HashMap<String, Object>();
								Map<String, Object> objBussiness1 = new HashMap<String, Object>();
								objBussiness1.put("name", dealDays2.getDeal().getBussiness().getName());
								objBussiness1.put("id", dealDays2.getDeal().getBussiness().getId());
								
								
								List<DealDays>  dealDays1 = dealsServices.getDays(dealDays2.getDeal().getId());
								List<Map<String ,Object>> daysList = new ArrayList<Map<String,Object>>();
								for (DealDays dealDays3 : dealDays1) {
									Map<String, Object> objDay1 = new HashMap<String, Object>();
									objDay1.put("day", dealDays3.getDay().getDay());
									objDay1.put("id", dealDays3.getDay().getId());
									daysList.add(objDay1);
								}
								
								List<Map<String ,Object>> dealImages1 = new ArrayList<Map<String,Object>>();
								List<DealImages> dealImages = dealsServices.getDealImagesByDeals(
										dealDays2.getDeal().getId());
								for (DealImages dealImages2 : dealImages) {
									Map<String, Object> objImage = new HashMap<String, Object>();
									objImage.put("image", dealImages2.getImage());
									objImage.put("id",  dealImages2.getId());
									dealImages1.add(objImage);
								}
								
								List<Map<String ,Object>> dealcategories1 = new ArrayList<Map<String,Object>>();
								List<DealsCategories> dealsCategories = dealsServices.getDealsCategories(dealDays2.getDeal().getId());
								for (DealsCategories dealsCategories2 : dealsCategories) {
									Map<String, Object> objCategory = new HashMap<String, Object>();
									objCategory.put("id", dealsCategories2.getMealCategories().getId());
									objCategory.put("category",  dealsCategories2.getMealCategories().getCategory());
									dealcategories1.add(objCategory);
								}
								
								PrettyTime prettyTime = new PrettyTime();
								Map<String, Object> objAddedBy = new 	
										HashMap<String, Object>();
								objAddedBy.put("name", dealDays2.getDeal().getAddedBy().getName());
								objAddedBy.put("id", dealDays2.getDeal().getAddedBy().getId());
								objAddedBy.put("time", prettyTime.format(dealDays2.getDeal().getCreationTime()));
								
								Map<String, Object> objupdatedBy = new HashMap<String, Object>();
								if(dealDays2.getDeal().getUpdatedBy() != null){
									objupdatedBy.put("name", dealDays2.getDeal().getUpdatedBy().getName());
									objupdatedBy.put("id", dealDays2.getDeal().getUpdatedBy().getId());
									objupdatedBy.put("time", prettyTime.format(dealDays2.getDeal().getUpdationTime()));
								}
								
								List<VerifiedDeals> verifiedDeals = dealsServices.getVerifiedDealsByDeal(dealDays2.getDeal().getId());
								if(!verifiedDeals.isEmpty()){	
									objDeal.put("vrified", verifiedDeals.size());
								}else{
									objDeal.put("vrified", 0);
								}
								if(!notRegistered){
									VerifiedDeals verifiedDeals2 = dealsServices.getVerifiedDeals(dealDays2.getDeal().getId(), objUser.getId());
									
									if(verifiedDeals2 != null)
										objDeal.put("verifiedByMe", true);
									else
										objDeal.put("verifiedByMe", false);
								}else{
									objDeal.put("verifiedByMe", false);
								}
								
								if(!verifiedDeals.isEmpty()){
									
									Map<String, Object> objLastVerified = new HashMap<String, Object>();
									String name =verifiedDeals.get((verifiedDeals.size()-1)).getUser().getName();
									long id = verifiedDeals.get((verifiedDeals.size()-1)).getUser().getId();
									objLastVerified.put("id", id);
									objLastVerified.put("name", name);
									if(verifiedDeals.get((verifiedDeals.size()-1)).getVerifiedAt() != null)
										objLastVerified.put("time", prettyTime.format(verifiedDeals.get((verifiedDeals.size()-1)).getVerifiedAt()));
									else
										objLastVerified.put("time", "");
									objDeal.put("lastVerifiedBy", objLastVerified);
									
								}else{
									Map<String, Object> objLastVerified = new HashMap<String, Object>();
									objDeal.put("lastVerifiedBy", objLastVerified);
								}
								
								List<DealImages> dealImages6 = dealsServices.getDealImagesByDeals(dealDays2.getDeal().getId());
								for (DealImages dealImages2 : dealImages6) {
									images.add("http://112.196.97.229:8080/Foofulu"+dealImages2.getImage());
								}
								
								objDeal.put("business", objBussiness1);	
								objDeal.put("dealcategories", dealcategories1);
								objDeal.put("updatedBy", objupdatedBy);
								objDeal.put("objAddedBy", objAddedBy);
								objDeal.put("days", daysList);
								objDeal.put("images", dealImages1);
								objDeal.put("title",dealDays2.getDeal().getTitle());
								objDeal.put("dealId",dealDays2.getDeal().getId());
								listDeals.add(objDeal);
							}
						
							if(listDeals.size()>0){
								objDay.put("deals", listDeals);
								objDay.put("day", days2.getDay());
								objDay.put("id", days2.getId());
								listDaysDeals.add(objDay);
							}
					
						}
					}
					
					
					objBussiness.put("photos", images);
					
					objBussiness.put("dealDays", listDaysDeals);
					objMap.put("business", objBussiness);
					objMap.put("message", "Deal fetched successfully.");
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
	
	public Bussiness addBussiness(String bussinessId) throws JSONException {
		Bussiness bussiness1 =  dealsServices.getBussinessByBussinesId(bussinessId);
		
		if(bussiness1 == null){
			/*String response = YelpApi.getAccessToken();
			JSONObject jsonObject2 = new JSONObject(response);
			String accessToken = jsonObject2.getString("access_token");*/
			String accessToken ="ameWBKnfh_me4DnzQ9Ak40nZjgdrKedUBMT9kKCYYxaIk63hi_Gzf1AecJAaL8M6_8G2qcMJJexWdwXHvqkTBZNhgiEq0CTMmQfTdSjyFsGkX5PZbKaI7HqTVrQEWnYx"; 
			System.out.println("Access Token  >>> "+accessToken);
			
			String venuesResponse = YelpApi.getVenuesDetails(bussinessId, accessToken);
			JSONObject jsonObject3  = new JSONObject(venuesResponse);
			System.out.println(venuesResponse);
			
			Bussiness bussiness = new Bussiness();
			bussiness.setBussinessId(jsonObject3.getString("id"));
	
			if(jsonObject3.has("name"))
				bussiness.setName(jsonObject3.getString("name"));
			
			if(jsonObject3.has("price"))
				bussiness.setCurrency(jsonObject3.getString("price"));
			
			if(jsonObject3.has("location")){
				JSONObject  location =jsonObject3.getJSONObject("location");
					bussiness.setLocation(location.getString("address1")+" , "+
						location.getString("city")+" , "+location.getString("country")+
						" , "+location.getString("zip_code"));
			}
			
			if(jsonObject3.has("rating"))
				bussiness.setRating(jsonObject3.getDouble("rating"));
			
			if(jsonObject3.has("review_count"))
				bussiness.setReviews(jsonObject3.getInt("review_count"));
			
			bussiness.setPhone(jsonObject3.getString("phone"));
	
			JSONObject jsonObject4 = jsonObject3.getJSONObject("coordinates");
			bussiness.setLatitude(jsonObject4.getDouble("latitude"));
			bussiness.setLongitude(jsonObject4.getDouble("longitude"));
			
			Bussiness bussiness2 =  dealsServices.addBussiness(bussiness);
						
			JSONArray jsonArray1 =  jsonObject3.getJSONArray("categories");
			for (int i = 0; i < jsonArray1.length(); i++) {
				BussinessCategories bussinessCategories = new BussinessCategories();
				bussinessCategories.setAlias(jsonArray1.getJSONObject(i).getString("alias"));
				bussinessCategories.setTitle(jsonArray1.getJSONObject(i).getString("title"));
				bussinessCategories.setBussiness(bussiness2);
				dealsServices.addBussinessCategories(bussinessCategories);
			}
							
			JSONArray jsonArray = jsonObject3.getJSONArray("photos");
			for (int i = 0; i < jsonArray.length(); i++) {
				BussinessImages bussinessImages = new BussinessImages();
				bussinessImages.setImage(jsonArray.getString(i));
				bussinessImages.setBussiness(bussiness2);
				dealsServices.addBussinessImages(bussinessImages);
			}
			
			JSONArray jsonArray4 = jsonObject3.getJSONArray("hours");
			for (int i = 0; i < jsonArray4.length();) {
				JSONObject jsonObject5 = jsonArray4.getJSONObject(i);
				JSONArray jsonArray3 = jsonObject5.getJSONArray("open");
				for (int j = 0; j < jsonArray3.length(); j++) {
					BussinessDays bussinessDays = new BussinessDays();
					bussinessDays.setDay(jsonArray3.getJSONObject(j).getInt("day"));
					bussinessDays.setEnd(jsonArray3.getJSONObject(j).getString("end"));
					bussinessDays.setStart(jsonArray3.getJSONObject(j).getString("start"));
					bussinessDays.setBussiness(bussiness2);
					dealsServices.addBussinessDays(bussinessDays);
				}
				break;
			}
			return bussiness2;
		}else{
			return bussiness1;
		}
	}
	
	@RequestMapping(value = "addFavouriteBussiness", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<String> addFavouriteBussiness(@RequestHeader("secretKey") String secretKey ,
			@RequestHeader("version") String version ,
			@RequestHeader("deviceType") String deviceType ,
			@RequestBody String jsonStr,HttpServletRequest request) 
			throws JSONException, JsonGenerationException, JsonMappingException, IOException {
		Map<String, Object> objMap = new HashMap<String,Object>();
		ObjectMapper mapper = new ObjectMapper();
		try{
			AppVersion appVersion = userServices.getAppVersion(deviceType);
			if(appVersion.getVersion().equals(version)){
				Users objUser= userServices.checkSecrteKey(secretKey);
				System.out.println("Secret Key >>>>> "+secretKey);
				if(objUser == null){			
					objMap.put("message", "Session has been expired. Please login again to procced.");
					String jsonInString = mapper.writeValueAsString(objMap);
					return new ResponseEntity<String>(jsonInString,HttpStatus.UNAUTHORIZED);	
				}else{
					System.out.println("JSON Response >>>>>>>"+jsonStr);
					JSONObject jsonObject = new JSONObject(jsonStr);
					Bussiness bussiness = dealsServices.getBussinessById(jsonObject.getLong("bussinessId"));
					
					FavouriteBussiness favouriteBussiness = dealsServices.getFavouriteBussinessByUserAndBussiness(
							objUser.getId(), bussiness.getId());
					if(favouriteBussiness == null){
						FavouriteBussiness favouriteBussiness2 = new FavouriteBussiness();
						favouriteBussiness2.setBussiness(bussiness);
						favouriteBussiness2.setUser(objUser);
						FavouriteBussiness favouriteBussiness3= dealsServices.addFavouriteBussiness(favouriteBussiness2);
						
						if(favouriteBussiness3 != null){
							objMap.put("message", "Bussiness added to favourites list.");
							String jsonInString = mapper.writeValueAsString(objMap);
							return new ResponseEntity<String>(jsonInString,HttpStatus.OK);
						}else{
							objMap.put("message", "Something went wrong.");
							String jsonInString = mapper.writeValueAsString(objMap);
							return new ResponseEntity<String>(jsonInString,HttpStatus.INTERNAL_SERVER_ERROR);
						}
					}else{
						
						int i = dealsServices.deleteFavouriteBussiness(favouriteBussiness);
						
						if(i==1){
							objMap.put("message", "Bussiness removed from favourites list.");
							String jsonInString = mapper.writeValueAsString(objMap);
							return new ResponseEntity<String>(jsonInString,HttpStatus.OK);
						}else{
							objMap.put("message", "Something went wrong.");
							String jsonInString = mapper.writeValueAsString(objMap);
							return new ResponseEntity<String>(jsonInString,HttpStatus.INTERNAL_SERVER_ERROR);
						}
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
	
	@RequestMapping(value = "searchDeals", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<String> searchDeals(@RequestHeader("secretKey") String secretKey ,
			@RequestHeader("version") String version ,
			@RequestHeader("deviceType") String deviceType ,
			@RequestBody String jsonStr,HttpServletRequest request) 
			throws JSONException, JsonGenerationException, JsonMappingException, IOException {
		Map<String, Object> objMap = new HashMap<String,Object>();
		ObjectMapper mapper = new ObjectMapper();
		try{
			AppVersion appVersion = userServices.getAppVersion(deviceType);
			if(appVersion.getVersion().equals(version)){
				Users objUser= userServices.checkSecrteKey(secretKey);
				System.out.println("Secret Key >>>>> "+secretKey);
				if(objUser == null){			
					if(!secretKey.equals(Commons.secretString)){
						objMap.put("message", "Session has been expired. Please login again to procced.");
						String jsonInString = mapper.writeValueAsString(objMap);
						return new ResponseEntity<String>(jsonInString,HttpStatus.UNAUTHORIZED);
					}else{
						JSONObject jsonObject = new JSONObject(jsonStr);
						String text = jsonObject.getString("text");
						text =text.replace("'", "");
						Set<Map<String, Object>> listBussinesses = new LinkedHashSet<Map<String,Object>>();
						String condition="";
						double kilometers =10 * 1.609344;
					  	System.out.println("distance in km"+kilometers);
					    double distance1 = (kilometers / 40000) * 360;
					   condition = " where (upper(REPLACE(b.name,'\\'','')) like '%"+text.toUpperCase()+"%' or upper(d.title) like '%"+text.toUpperCase()+"%') and latitude<("+jsonObject.getDouble("latitude")+" + "+distance1+")"
							    		+ " and latitude > ("+jsonObject.getDouble("latitude")+" - "+distance1+")"
							    				+ " and longitude < ("+jsonObject.getDouble("longitude")+" + "+distance1+")"
							    						+ " and longitude > ("+jsonObject.getDouble("longitude")+" - "+distance1+")";
	
					    List<Object> bussinesses = dealsServices.getBussinessesByNativeQuery(condition);
					    Iterator<Object> objects =	bussinesses.listIterator();
						while (objects.hasNext()) { 
							
							Bussiness bussiness = dealsServices.getBussinessById(((BigInteger)objects.next()).longValue());
	
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
							objBussiness.put("hasDeals", true);
							List<BussinessImages> bussinessImages =  dealsServices.getBussinessImages(bussiness);
							if(bussinessImages != null && bussinessImages.size()>0){
								for (int i = 0; i < bussinessImages.size(); i++) {
									if(i==0)
									objBussiness.put("photo",bussinessImages.get(i).getImage());
								}
							}else{
								objBussiness.put("photo","");
							}
							
							List<Map<String,Object>> listDeals = new ArrayList<Map<String,Object>>();
							
							List<Deals> deals = dealsServices.searchDealsByBussinessAndText(bussiness.getId(),text.toUpperCase());
							for (Deals deals2 : deals) {
								Map<String, Object> objDeal = new HashMap<String, Object>();
								objDeal.put("title", deals2.getTitle());
								objDeal.put("id", deals2.getId());
								listDeals.add(objDeal);
							}
							objBussiness.put("deals", listDeals);
							listBussinesses.add(objBussiness);
					    }
						objMap.put("deals", listBussinesses);
						objMap.put("message", "Businesses list fetched successfuly.");
						String jsonInString = mapper.writeValueAsString(objMap);
						return new ResponseEntity<String>(jsonInString,HttpStatus.OK);
					}
				}else{
					JSONObject jsonObject = new JSONObject(jsonStr);
					String text = jsonObject.getString("text");
					text =text.replace("'", "");
					Set<Map<String, Object>> listBussinesses = new LinkedHashSet<Map<String,Object>>();
					String condition="";
					double kilometers =10 * 1.609344;
				  	System.out.println("distance in km"+kilometers);
				    double distance1 = (kilometers / 40000) * 360;
				   condition = " where (upper(REPLACE(b.name,'\\'','')) like '%"+text.toUpperCase()+"%' or upper(d.title) like '%"+text.toUpperCase()+"%') and latitude<("+jsonObject.getDouble("latitude")+" + "+distance1+")"
						    		+ " and latitude > ("+jsonObject.getDouble("latitude")+" - "+distance1+")"
						    				+ " and longitude < ("+jsonObject.getDouble("longitude")+" + "+distance1+")"
						    						+ " and longitude > ("+jsonObject.getDouble("longitude")+" - "+distance1+")";
	
				    List<Object> bussinesses = dealsServices.getBussinessesByNativeQuery(condition);
				    Iterator<Object> objects =	bussinesses.listIterator();
					while (objects.hasNext()) { 
						
						Bussiness bussiness = dealsServices.getBussinessById(((BigInteger)objects.next()).longValue());
	
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
						objBussiness.put("hasDeals", true);
						List<BussinessImages> bussinessImages =  dealsServices.getBussinessImages(bussiness);
						if(bussinessImages != null && bussinessImages.size()>0){
							for (int i = 0; i < bussinessImages.size(); i++) {
								if(i==0)
								objBussiness.put("photo",bussinessImages.get(i).getImage());
							}
						}else{
							objBussiness.put("photo","");
						}
						List<Map<String,Object>> listDeals = new ArrayList<Map<String,Object>>();
						
						List<Deals> deals = dealsServices.searchDealsByBussinessAndText(bussiness.getId(),text.toUpperCase());
						for (Deals deals2 : deals) {
							Map<String, Object> objDeal = new HashMap<String, Object>();
							objDeal.put("title", deals2.getTitle());
							objDeal.put("id", deals2.getId());
							listDeals.add(objDeal);
						}
						objBussiness.put("deals", listDeals);
						listBussinesses.add(objBussiness);
				    }
				    	
					String accessToken ="ameWBKnfh_me4DnzQ9Ak40nZjgdrKedUBMT9kKCYYxaIk63hi_Gzf1AecJAaL8M6_8G2qcMJJexWdwXHvqkTBZNhgiEq0CTMmQfTdSjyFsGkX5PZbKaI7HqTVrQEWnYx";
					
					String venuesResponse = YelpApi.getVenuesListBySearch(jsonObject.getString("latitude")
							, jsonObject.getString("longitude"),accessToken,text);
				
					System.out.println("Data  >>> "+ venuesResponse);				
					JSONObject jsonObject3  = new JSONObject(venuesResponse);
					
					JSONArray jsonArray = jsonObject3.getJSONArray("businesses");
					for (int i = 0; i < jsonArray.length(); i++) {
						Map<String, Object> objBussinesses = new HashMap<String,Object>();
						JSONObject objBussiness= jsonArray.getJSONObject(i);
						objBussinesses.put("bussinessId",objBussiness.getString("id"));
						objBussinesses.put("name",objBussiness.getString("name"));
						if(objBussiness.has("price"))
							objBussinesses.put("price",objBussiness.getString("price"));
						else
							objBussinesses.put("price","");
						objBussinesses.put("phone",objBussiness.getString("phone"));
						objBussinesses.put("review_count",objBussiness.getString("review_count"));
						objBussinesses.put("rating",objBussiness.getInt("rating"));
						objBussinesses.put("photo",objBussiness.getString("image_url"));
	
						JSONObject  location =objBussiness.getJSONObject("location");
						objBussinesses.put("location",location.getString("address1")+" , "+
								location.getString("city")+" , "+location.getString("zip_code"));
						
						Double distance2 =objBussiness.getDouble("distance")/1000;
						distance2 = 0.621*distance2;
						objBussinesses.put("distance", distance2);
						
						objBussinesses.put("is_closed", true);
						
						List<Map<String, Object>> categories1 = new ArrayList<Map<String, Object>>();
						JSONArray jsonArray2 = objBussiness.getJSONArray("categories");
						for (int j = 0; j < jsonArray2.length(); j++) {
							Map<String, Object> objCategories = new HashMap<String,Object>();
							objCategories.put("alias", jsonArray2.getJSONObject(j).getString("alias"));
							objCategories.put("title", jsonArray2.getJSONObject(j).getString("title"));
							categories1.add(objCategories);
						}
						objBussinesses.put("categories", categories1);
						
						objBussinesses.put("deals", new ArrayList<Map<String,Object>>());
						
						JSONObject jsonObject4 = objBussiness.getJSONObject("coordinates");
						Map<String, Object> objCordinates = new HashMap<String,Object>();
						try{
							objCordinates.put("latitude", jsonObject4.getDouble("latitude"));
							objCordinates.put("longitude", jsonObject4.getDouble("longitude"));
							objBussinesses.put("coordinates", objCordinates);
						
						}catch(JSONException jsonException){
							
							objCordinates.put("latitude",0.0);
							objCordinates.put("longitude", 0.0);
							objBussinesses.put("coordinates", objCordinates);
						}
						
						objBussinesses.put("hasDeals", false);
						listBussinesses.add(objBussinesses);
					}
					
					objMap.put("deals", listBussinesses);
					objMap.put("message", "Businesses list fetched successfuly.");
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
	
	/*	Map<String, Object> objAddedBy = new HashMap<String, Object>();
	Map<String, Object> objupdatedBy = new HashMap<String, Object>();*/
	
	/*objAddedBy.put("name", deals2.getAddedBy().getName());
	objAddedBy.put("id", deals2.getAddedBy().getId());
	
	if(deals2.getUpdatedBy() != null){
		objupdatedBy.put("name", deals2.getUpdatedBy().getName());
		objupdatedBy.put("id", deals2.getUpdatedBy().getId());
	}*/

}
