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
@Table(name = "days")
@Proxy(lazy= false)
public class Days {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
	
	private String day;
	
	@JsonIgnore
	@OneToMany(mappedBy="day",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	private Set<DealDays> dealDays= new HashSet<DealDays>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
}