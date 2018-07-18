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
@Table(name = "mealcategories")
@Proxy(lazy= false)
public class MealCategories {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
	
	private String category;

	@JsonIgnore
	@OneToMany(mappedBy="mealCategories",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	private Set<DealsCategories> dealsCategories = new HashSet<DealsCategories>();
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
