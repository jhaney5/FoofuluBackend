'use strict';

var cont = angular.module('logincontroller', ['ngMessages', 'material.svgAssetsCache', 'LocalStorageModule'])
cont.controller('LoginController', function($scope,$rootScope,$http,$location,$timeout,$filter,$route,$window,Idle,localStorageService) {
	$rootScope.isAuthorised=false;
	$rootScope.getAuthorisedRole = 0;
	$scope.employeeMsg = "";
	var url = $location.url();
	
	if(url != "/" ){
		if(url != '/index'){
			$location.path('/index');
		}
	}
	$scope.checkAuthentication = function() {
		if ($scope.loginForm.$valid) {
			var formData = {
				"email":$scope.email,
				"password":$scope.password
			};
			$http.post('checkAuthentication',formData)
			.then(function(_data) {
				if(_data.data != null && _data.data != undefined && _data.data != ""){
					$scope.loginForm.$setPristine();
                    $scope.loginForm.submitted = false;
                    $scope.loginForm.$setUntouched();
                    localStorageService.set('user',JSON.stringify(_data.data));
                    localStorageService.set('f_UserId',_data.data.id);
					localStorageService.set('f_Name',_data.data.name);
            		localStorageService.set('f_UserImage',_data.data.image);
            		localStorageService.set('f_UserRole',_data.data.role.id);
					if(_data.data.role.id == 1){
						$location.path('/dashboard');
	            	}
				}else{
					 $window.scrollTo(0, 0);
   					 $scope.employeeMsg = "Email or Password incorrect";
       				 $scope.loginAlertMessage=false; 
           	         $timeout(function () { $scope.loginAlertMessage = true; }, 6000);
				}
		    }, function(response) {
		    	 $window.scrollTo(0, 0);
				 $scope.employeeMsg = "Something went wrong. Try after some time.";
  				 $scope.loginAlertMessage=false; 
      	         $timeout(function () { $scope.loginAlertMessage = true; }, 6000);
		    });
		} else {
            $scope.loginForm.$submitted = true;
        }
	}
	
});
cont.controller('LogoutController', function($scope,$rootScope,$http,$location,$timeout,$filter,$route,$window,Idle,localStorageService) {
	$rootScope.isAuthorised=false;
	$rootScope.getAuthorisedRole = 0;
	$scope.employeeMsg = "";
	$rootScope.logout = function() {
		if(localStorageService.get('f_UserId') != undefined && localStorageService.get('f_UserId') != null){
			$rootScope.isAuthorised=false;
			$rootScope.getAuthorisedRole = 0;
			localStorageService.remove('user');
			localStorageService.remove('f_UserId');
			localStorageService.remove('f_Name');
			localStorageService.remove('f_UserImage');
			localStorageService.remove('f_UserRole');
			$http.post('logout');
			$location.path('/index');
		}
	}
});
