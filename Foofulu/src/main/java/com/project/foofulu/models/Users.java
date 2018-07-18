package com.project.foofulu.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name = "users")
@Proxy(lazy= false)
public class Users {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
	
	private String name;
	
	private String email;
	private String loginType;
	private String socialId;
	private boolean status;
	private String password;
	private String deviceType;
	private String deviceId;
	private String secrateKey;
	private double latitude;
	private double longitude;
	private boolean sendNotifications;
	private String image;
	
	
	public String getSecrateKey() {
		return secrateKey;
	}
	public void setSecrateKey(String secrateKey) {
		this.secrateKey = secrateKey;
	}
	@Column(name = "creationTime", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
	private Date creationTime;
	
	@JsonIgnore
	@OneToMany(mappedBy="user",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	private Set<UsersLogs> usersLogs = new HashSet<UsersLogs>();

	@JsonIgnore
	@OneToMany(mappedBy="user",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	private Set<VerifiedDeals> verifiedDeals = new HashSet<VerifiedDeals>();
	
	@JsonIgnore
	@OneToMany(mappedBy="user",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	private Set<FavouriteBussiness> favouriteDeals = new HashSet<FavouriteBussiness>();
	
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinColumn(name = "role")
    private Roles role;

	@JsonIgnore
	@OneToMany(mappedBy="user",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	private Set<ContactUs> contactUs = new HashSet<ContactUs>();
	
	@JsonIgnore
	@OneToMany(mappedBy="addedBy",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	private Set<Deals> deals = new HashSet<Deals>();
	
	@JsonIgnore
	@OneToMany(mappedBy="updatedBy",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	private Set<Deals> updatedBy = new HashSet<Deals>();
	
	

	public String getSocialId() {
		return socialId;
	}
	public void setSocialId(String socialId) {
		this.socialId = socialId;
	}
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public Roles getRole() {
		return role;
	}
	public void setRole(Roles role) {
		this.role = role;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public boolean isSendNotifications() {
		return sendNotifications;
	}
	public void setSendNotifications(boolean sendNotifications) {
		this.sendNotifications = sendNotifications;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
}
