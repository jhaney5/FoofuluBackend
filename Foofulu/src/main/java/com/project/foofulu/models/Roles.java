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
@Table(name = "roles")
@Proxy(lazy= false)
public class Roles {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String role;
    @JsonIgnore
    @OneToMany(mappedBy="role",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    private Set<Users> usersList = new HashSet<Users>();
   
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Set<Users> getUsersList() {
		return usersList;
	}
	public void setUsersList(Set<Users> usersList) {
		this.usersList = usersList;
	}
}
