package com.project.foofulu.utils;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class YelpApi {
	
	public final static String CLIENT_ID = "djMN5foS-I7IkHL2WxWE2Q";
	public final static String CLIENT_SECRET = "PgaojXN5XD1D2qCFAcJasG40G4K1lR5nQlYvu0Sk5u9u69aSkKDPzRVZb35tVuWp";
	
	public static String getAccessToken(){
		 try{
			 OkHttpClient client = new OkHttpClient();
			 // create new client
			 Request request;
			 FormEncodingBuilder builder = new FormEncodingBuilder();
			 builder.add("client_id", CLIENT_ID);
			 builder.add("client_secret", CLIENT_SECRET);
			 RequestBody body = builder.build();
			 	request = new Request.Builder()
			 	.url("https://api.yelp.com/oauth2/token")
			 	.addHeader("Content-Type", "application/x-www-form-urlencoded")
			 	.post(body)
			 	.build();
				Call call = client.newCall(request);
				Response response =   call.execute();
				String response1 = response.body().string();
				return response1;
		 }catch(Exception e){
			 e.printStackTrace();
			 return null;
		 }
	 }
	 
	public static String getVenuesList(String lat,String lon,String accessToken){
		 try{
			 OkHttpClient client = new OkHttpClient();
			 // create new client
			
			 Request request = new Request.Builder()
			 	.url("https://api.yelp.com/v3/businesses/search?latitude="+lat+"&"
			 			+ "longitude="+lon+"&sort_by=distance&limit=50&radius=4000&"
			 			+ "categories=bars,nightlife,restaurants")
			 	.addHeader("Authorization","Bearer "+ accessToken)
			 	.get()
			 	.build();
				Call call = client.newCall(request);
				Response response =   call.execute();
				String response1 = response.body().string();
				return response1;
		 }catch(Exception e){
			 e.printStackTrace();
			 return null;
		 }
	 }
	
	public static String getVenuesListBySearch(String lat,String lon,String accessToken,String text){
		 try{
			 OkHttpClient client = new OkHttpClient();
			 // create new client
			 String text1="";
			 
			 if(text != null && !text.trim().equals("")){
				 text1 ="https://api.yelp.com/v3/businesses/search?latitude="+lat+"&"
				 			+ "longitude="+lon+"&sort_by=distance&limit=50&radius=4000&"
				 			+ "categories=bars,nightlife,restaurants&term="+text;	 
			 }else{
				 text1 ="https://api.yelp.com/v3/businesses/search?latitude="+lat+"&"
				 			+ "longitude="+lon+"&sort_by=distance&limit=50&radius=4000&"
				 			+ "categories=bars,nightlife,restaurants";
			 }
			 
			 
			 Request request = new Request.Builder()
			 	.url(text1)
			 	.addHeader("Authorization","Bearer "+ accessToken)
			 	.get()
			 	.build();
				Call call = client.newCall(request);
				Response response =   call.execute();
				String response1 = response.body().string();
				return response1;
		 }catch(Exception e){
			 e.printStackTrace();
			 return null;
		 }
	 }
	
	public static String getVenuesDetails(String venueId,String accessToken){
		 try{
			 OkHttpClient client = new OkHttpClient();
			 // create new client
			
			 Request request = new Request.Builder()
			 	.url("https://api.yelp.com/v3/businesses/"+venueId)
			 	.addHeader("Authorization","Bearer "+ accessToken)
			 	.get()
			 	.build();
				Call call = client.newCall(request);
				Response response =   call.execute();
				String response1 = response.body().string();
				return response1;
		 }catch(Exception e){
			 e.printStackTrace();
			 return null;
		 }
	 }
}
