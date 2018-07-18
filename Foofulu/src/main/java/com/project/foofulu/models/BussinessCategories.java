package com.project.foofulu.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "bussinesscategories")
@Proxy(lazy= false)
public class BussinessCategories {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
	
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinColumn(name = "bussiness")
    private Bussiness bussiness;
	
	private String title;
	private String alias;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Bussiness getBussiness() {
		return bussiness;
	}
	public void setBussiness(Bussiness bussiness) {
		this.bussiness = bussiness;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
}
