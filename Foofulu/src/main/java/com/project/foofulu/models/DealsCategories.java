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
@Table(name = "dealscategories")
@Proxy(lazy= false)
public class DealsCategories {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
	
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinColumn(name = "deal")
    private Deals deal;
	
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinColumn(name = "mealCategories")
    private MealCategories mealCategories;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Deals getDeal() {
		return deal;
	}

	public void setDeal(Deals deal) {
		this.deal = deal;
	}

	public MealCategories getMealCategories() {
		return mealCategories;
	}

	public void setMealCategories(MealCategories mealCategories) {
		this.mealCategories = mealCategories;
	}
}
