package com.project.foofulu.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "bussiness")
@Proxy(lazy= false)
public class Bussiness {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
	
	private String bussinessId;
	private String name;
	private String location;
	private double latitude;
	private double longitude;
	private String currency;
	private String category;
	private double rating;
	private int reviews;
	private String phone;
	
	@JsonIgnore
	@OneToMany(mappedBy="bussiness",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	private Set<BussinessImages> bussinessImages = new HashSet<BussinessImages>();
	
	@JsonIgnore
	@OneToMany(mappedBy="bussiness",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	private Set<FavouriteBussiness> favouriteBussiness = new HashSet<FavouriteBussiness>();
	
	@JsonIgnore
	@OneToMany(mappedBy="bussiness",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	private Set<BussinessCategories> bussinessCategories = new HashSet<BussinessCategories>();

	@JsonIgnore
	@OneToMany(mappedBy="bussiness",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	private Set<BussinessDays> bussinessDays = new HashSet<BussinessDays>();
	
	@JsonIgnore
	@OneToMany(mappedBy="bussiness",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	private Set<Deals> deals = new HashSet<Deals>();
	
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

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public int getReviews() {
		return reviews;
	}

	public void setReviews(int reviews) {
		this.reviews = reviews;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBussinessId() {
		return bussinessId;
	}

	public void setBussinessId(String bussinessId) {
		this.bussinessId = bussinessId;
	}
}
