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
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "deals")
@Proxy(lazy= false)
public class Deals {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
	
	@Type(type="text")
	private String title;
	
	private boolean status;
	
	@Column(name = "creationTime", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
	private Date creationTime;

	@Column(name = "updationTime", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
	private Date updationTime;
	
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinColumn(name = "addedBy")
    private Users addedBy;

	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinColumn(name = "updatedBy")
    private Users updatedBy;
	
	@JsonIgnore
	@OneToMany(mappedBy="deal",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	private Set<DealImages> dealImages = new HashSet<DealImages>();
	
	@JsonIgnore
	@OneToMany(mappedBy="deal",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	private Set<DealsCategories> dealsCategories = new HashSet<DealsCategories>();
	
	@JsonIgnore
	@OneToMany(mappedBy="deal",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	private Set<VerifiedDeals> verifiedDeals = new HashSet<VerifiedDeals>();
	
	@JsonIgnore
	@OneToMany(mappedBy="deal",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	private Set<DealDays> dealDays= new HashSet<DealDays>();
	
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinColumn(name = "bussiness")
    private Bussiness bussiness;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Users getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(Users addedBy) {
		this.addedBy = addedBy;
	}

	public Users getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Users updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Bussiness getBussiness() {
		return bussiness;
	}

	public void setBussiness(Bussiness bussiness) {
		this.bussiness = bussiness;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getUpdationTime() {
		return updationTime;
	}

	public void setUpdationTime(Date updationTime) {
		this.updationTime = updationTime;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
