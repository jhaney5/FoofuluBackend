'use strict';

angular.module('adminservices', []).service('AdminServices', function($http, localStorageService){
	
	this.getUsers = function() {
		return $http.post('get_Users'); 
	}
	
	this.getAppVersion = function() {
		return $http.get('getAppVersion'); 
	}
	
	this.updateAppVersion = function(data) {
		return $http.post('updateAppVersion',data); 
	}
	
	this.getUsersList = function() {
		return $http.post('getUsersList'); 
	}
	this.getBussinessList = function() {
		return $http.post('getBussinessList'); 
	}
	this.getDealsListData = function(scope) {
		var formData = {
				"id" : scope.bussiness.id,		
		};
		return $http.post('getDealsListData',formData); 
	}
	this.getDealsByBussinessId = function(scope) {
		var formData = {
				"id" : scope.bussiness.id,		
		};
		return $http.post('getDealsByBussiness',formData); 
	}	
			
	this.getAdminProfile = function(user_id) {
		var formData = {
				"id" : user_id,						
		};
		return $http.post('getAdminProfile',formData);  
	}

	this.disableUser = function(user) {	
		var formData = {
				"id" : user.id,	
				"status": user.status,
		};
		
		return $http.post('disableUser',formData); 
	}
	
	this.editUser = function(scope) {
	    var formData = new FormData();
		 var formData1 = {
     		    "name" : scope.name,
     		    "image" : scope.image,
     		    "email" : scope.email,
     		    "id" : scope.id
      };
      formData.append('objUser', new Blob([angular.toJson(formData1)], {
     	 type: "application/json"
      }));
      formData.append("file", scope.myFile);
      return $http({
     	 method: 'POST',
     	 url: 'editUser',
     	 headers: {'Content-Type': undefined },
     	 data: formData
      });
	}
	
});