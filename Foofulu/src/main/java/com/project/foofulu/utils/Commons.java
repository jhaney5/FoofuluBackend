package com.project.foofulu.utils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.project.foofulu.models.Users;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class Commons {
	
	public static final String secretString="foofulusakjjb1df5dcvfds6cvxb";
	
	//Genrate filename 
	public static String getFileName(){
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
	
	
	
	/*JSONObject json = new JSONObject();
	json.put("to", token); 
	JSONObject json1 = new JSONObject();  
	json1.put("title", "A request for item");
	json1.put("body", message);
	json1.put("tag", "rentalItemRequest");
	json1.put("sound", "default");
	json.put("notification", json1);
	JSONObject data = new JSONObject();
	data.put("tag", "rentalItemRequestHome");
	json.put("data", data);
	json.put("priority", "high");
	json.put("tag", "rentalItemRequestHome");
	pushFCMNotificationForIOS(json);  */
	
	///////////////////////////////////FCM Notification Method
	public final static String AUTH_KEY_FCM = "AAAAXtFILD0:APA91bEOJACel_i4zyvUQLDot3ZLcgAXoNqrGtccd-xgI3RwNHjSxIqJvnYrxCFpjPq0-sVgjJLMJ2VZRzLH1BGtar_3P5wHxVY7-rl6AnrG0U4Tv7mYlUMZOm85WvdBqVXgjb9Tigm6";
	public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

	public static void pushFCMNotification(JSONObject info) throws Exception{
		System.out.println("info = "+info);
		
		String authKey = AUTH_KEY_FCM; // You FCM AUTH key
		String FMCurl = API_URL_FCM; 
		
		URL url = new URL(FMCurl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization","key="+authKey);
		conn.setRequestProperty("Content-Type","application/json");
		
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(info.toString());
		wr.flush();
		conn.getInputStream();
		System.out.println("Notification sent..");
	}
	
	
	//Confirm Email Address
	public static boolean emailConfirmation(Users objUsers){
		String body="<body style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; box-sizing: border-box; font-size: 14px; -webkit-font-smoothing: antialiased; -webkit-text-size-adjust: none; width: 100% !important; height: 100%; line-height: 1.6; background-color: #f6f6f6; margin: 0; padding-left: 10px;\" bgcolor=\"#f6f6f6\">";	
		String content="<a href=\"http://112.196.97.229:8080/WhereWeDrink/verifyAppAccount?key="+objUsers.getEmail()+"\" style=\"font-family\": 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; box-sizing: border-box; font-size: 14px; color: #FFF; text-decoration: none; line-height: 2; font-weight: bold; text-align: center; cursor: pointer; display: inline-block; border-radius: 5px; text-transform: capitalize; background-color: #348eda; margin: 0; padding: 0; border-color: #348eda; border-style: solid; border-width: 10px 20px;\">Confirm email address</a>"
						+ "</table>";
		try{
			String from = "netsetsoftwareiso@gmail.com";
			final String username = "netsetsoftwareiso@gmail.com";//change accordingly
			final String password1 = "netsetiso1901";//change accordingly
			String host = "smtp.gmail.com";
		      
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", "25");
		 	 // Get the Session object.
			Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
	            	protected PasswordAuthentication getPasswordAuthentication() {
	        			return new PasswordAuthentication(username, password1);
	            	}
			});
			String to = objUsers.getEmail();
	      // Create a default MimeMessage object.
            Message message = new MimeMessage(session);
	   	   // Set From: header field of the header.
		   message.setFrom(new InternetAddress(from));
		   // Set To: header field of the header.
		   message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		   // Set Subject: header field
		   message.setSubject("Please verify your account with WhereWeDrink");
		   // Send the actual HTML message, as big as you like
		   message.setContent(
				   body+"<h3>Hello "+objUsers.getName()+" "+" , </h3>Thanks for creating account with WhereWeDrink !<br>Please click on link to activate your account: <br> "+content+" <br><br>Thank you",
		             "text/html");
		   // Send message
		   Transport.send(message);
		   return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	// Round off to given places
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();
	 
	    BigDecimal bd = new BigDecimal(Double.toString(value));
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	// DIstance between two points
	public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
		double earthRadius = 6371000; //meters
		double dLat = Math.toRadians(lat2-lat1);
		double dLng = Math.toRadians(lng2-lng1);
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
				Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
				Math.sin(dLng/2) * Math.sin(dLng/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double dist = (double) (earthRadius * c);
		return dist;
	}
	
	
	///////////////Base64 to Bytes
	public static byte[] convertToImg(String base64) throws IOException  
    {  
         return Base64.decodeBase64(base64);  
    } 
}
