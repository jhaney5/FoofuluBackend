'use strict';

var cont = angular.module('admincontroller', ['LocalStorageModule', 'ui.bootstrap', 'chart.js' ,'angularjs-datetime-picker','moment-picker','ngDropdowns'])

cont.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

cont.controller('AdminDashboardController', function($scope,$rootScope,$location,$filter,$http, localStorageService, AdminServices) {
	$scope.loginAlertMessage = true;
	$scope.employeeMsg = "";
	$rootScope.user = JSON.parse(localStorageService.get('user'));
	$rootScope.getAuthorisedRole = localStorageService.get('f_UserRole');
	$rootScope.isAuthorised=true;
	$rootScope.f_Name = localStorageService.get('f_Name');
	$rootScope.f_UserImage = localStorageService.get('f_UserImage');
	$rootScope.f_UserId = localStorageService.get('f_UserId');
	if($rootScope.f_UserId == null || $rootScope.f_UserId == undefined || $rootScope.f_UserId == ""){
		$location.path('/index');
	}
	localStorageService.remove('pageName');
	
	 $scope.labels=["Registered Users", "Users Logged In Today", "Users Logged In(Last 7 days)", "Users Logged In(Last 30 days)"];
	 $scope.data = [];
	 
	 if($scope.usersList == null){
		 getUsersList();
	}
	 
	function getUsersList(){
		AdminServices.getUsers()
		  .then(function(_data) {
			  if(_data.data != null){
			    $scope.usersList = _data.data;
			    $scope.data.push($scope.usersList.registeredUsers);
			    $scope.data.push($scope.usersList.today);
			    $scope.data.push($scope.usersList.sevendays);
			    $scope.data.push($scope.usersList.thirtydays);
			    $scope.data.push(1);
			    $scope.data.push(10);
			  }
		  },
		 function(response) {
		 });
	}
	 $scope.onClick = function (points, evt) {
		    console.log(points, evt);
		  };
});

cont.controller('ChangePasswordController', function($scope,$rootScope,$location,$filter, $window, $timeout, localStorageService, AdminServices) {
	$scope.loginAlertMessage = true;
	$scope.employeeMsg = "";
	$rootScope.user = JSON.parse(localStorageService.get('user'));
	$rootScope.getAuthorisedRole = localStorageService.get('f_UserRole');
	$rootScope.isAuthorised=true;
	$rootScope.f_Name = localStorageService.get('f_Name');
	$rootScope.f_UserImage = localStorageService.get('f_UserImage');
	$rootScope.f_UserId = localStorageService.get('f_UserId');
	if($rootScope.f_UserId == null || $rootScope.f_UserId == undefined || $rootScope.f_UserId == ""){
		$location.path('/index');
	}
	if($scope.changeSystemUserPassword!=null){
	}
	$scope.changeSystemUserPassword = function(user){
		if($scope.newPassword!=$scope.confirmPassword){
			alert("New password and confirm password are not matched!!!");
		}else{
			if($scope.currentPassword!=$rootScope.user.password){
				alert("Current password is not correct!!!");
			}else{
				$rootScope.user.password=$scope.newPassword;
				AdminServices.updateSystemUser($rootScope.user)
				.then(function(_data) {
					if(_data.data != null){
						alert("password changed sucessfully.\n you have to login with new password..");
						$location.path("/");
					}else{
						alert("data aya koi ni..");
					}
				},
				function(response) {
					alert("Error = "+JSON.stringify(response));
				});
			}
		}
	}
	$scope.cancelPassword = function(){
		$location.path("/users");
	}
});

cont.controller('MyAccountController', function($scope,$rootScope,$location,$filter,$route, $window, $timeout, localStorageService, AdminServices) {
	$scope.loginAlertMessage = true;
	$scope.employeeMsg = "";
	$rootScope.user = JSON.parse(localStorageService.get('user'));
	$rootScope.getAuthorisedRole = localStorageService.get('f_UserRole');
	$rootScope.isAuthorised=true;
	$rootScope.f_Name = localStorageService.get('f_Name');
	$rootScope.f_UserImage = localStorageService.get('f_UserImage');
	$rootScope.f_UserId = localStorageService.get('f_UserId');
	if($rootScope.f_UserId == null || $rootScope.f_UserId == undefined || $rootScope.f_UserId == ""){
		$location.path('/index');
	}
	$scope.name=$rootScope.user.name;
	$scope.id=$rootScope.user.id;
	$scope.userImage=$rootScope.user.image;
    $scope.email=$rootScope.user.email;
    
    $scope.update = function(){
    	AdminServices.editUser($scope)
		.then(function(_data) {
			if(_data.data != null){
				localStorageService.set('user',JSON.stringify(_data.data));
                localStorageService.set('f_Name',_data.data.name);
        		localStorageService.set('f_UserImage',_data.data.image);
        		
        		$route.reload();
			}
		},
		function(response) {
		});
    }
    $scope.cancelUser = function(){
		$location.path("/dashboard");
	}
    
});

cont.controller('UsersController', function($scope,$rootScope,$location,$filter, $window, $timeout, localStorageService, AdminServices) {
	$scope.loginAlertMessage = true;
	$scope.employeeMsg = "";
	$rootScope.user = JSON.parse(localStorageService.get('user'));
	$rootScope.getAuthorisedRole = localStorageService.get('f_UserRole');
	$rootScope.isAuthorised=true;
	$rootScope.f_Name = localStorageService.get('f_Name');
	$rootScope.f_UserImage = localStorageService.get('f_UserImage');
	$rootScope.f_UserId = localStorageService.get('f_UserId');
	if($rootScope.f_UserId == null || $rootScope.f_UserId == undefined || $rootScope.f_UserId == ""){
		$location.path('/index');
	}
	$scope.usersList1={};
	if($scope.usersList == null){
		getUsersList();
	}
	function getUsersList(){
		AdminServices.getUsersList()
		.then(function(_data) {
			if(_data.data != null){
				$scope.usersList = _data.data;	
			}
		},
		function(response) {
		});
	}
	$scope.disableUser= function(user) {
		var r;
		if(user.status==false){ 
			r = confirm("Do you want to enable user ?");
		}else{ 
			r = confirm("Do you want to disable user ?");
		} 
		if (r == true) {
			if(user.status==false){
				user.status=true;
			}else{
				user.status=false;
			}
		AdminServices.disableUser(user) .then(function(_data) { 
			if(_data.data != null){
				if(_data.data.status){
					alert("User enabled successfully."); 
				}else{ 
					alert("User disabled successfully."); 
				} 
			} 
		}, function(response) {
			alert(JSON.stringify(response));
			});
		}
	}
	$scope.viewUserDetails = function(user){
		localStorageService.set("userInfo",JSON.stringify(user));
		$location.path("/userDetail");
	}
	$scope.viewEditUserDetails=function(user){
		localStorageService.set("userInfo",JSON.stringify(user));
		$location.path("/updateUser");
	}
});

cont.controller('BussinessController', function($scope,$rootScope,$location,$filter, $window, $timeout, localStorageService, AdminServices) {
	$scope.loginAlertMessage = true;
	$scope.employeeMsg = "";
	$rootScope.user = JSON.parse(localStorageService.get('user'));
	$rootScope.getAuthorisedRole = localStorageService.get('f_UserRole');
	$rootScope.isAuthorised=true;
	$rootScope.f_Name = localStorageService.get('f_Name');
	$rootScope.f_UserImage = localStorageService.get('f_UserImage');
	$rootScope.f_UserId = localStorageService.get('f_UserId');
	if($rootScope.f_UserId == null || $rootScope.f_UserId == undefined || $rootScope.f_UserId == ""){
		$location.path('/index');
	}
	if($scope.bussinessList == null){
		getBussinessList();
	}
	function getBussinessList(){
		AdminServices.getBussinessList()
		.then(function(_data) {
			if(_data.data != null){
				$scope.data = _data.data;
				$scope.bussinessList = _data.data.businesses;
			}
		},
		function(response) {
		});
	}
	
	$scope.viewBussinessDetails = function(bussiness){
		localStorageService.set("bussinessInfo",JSON.stringify(bussiness));
		$location.path("/bussinessDetails");
	}
});

cont.controller('UserDetailController', function($scope,$rootScope,$location,$filter, $window, $timeout, localStorageService, AdminServices) {
	$scope.loginAlertMessage = true;
	$scope.employeeMsg = "";
	$rootScope.user = JSON.parse(localStorageService.get('user'));
	$rootScope.getAuthorisedRole = localStorageService.get('f_UserRole');
	$rootScope.isAuthorised=true;
	$rootScope.f_Name = localStorageService.get('f_Name');
	$rootScope.f_UserImage = localStorageService.get('f_UserImage');
	$rootScope.f_UserId = localStorageService.get('f_UserId');
	if($rootScope.f_UserId == null || $rootScope.f_UserId == undefined || $rootScope.f_UserId == ""){
		$location.path('/index');
	}
	
	$scope.editable=true;
	$scope.showImage=false;
	$scope.user1=JSON.parse(localStorageService.get('userInfo'));
	$scope.name = $scope.user1.name;
	$scope.id = $scope.user1.id;
	$scope.email = $scope.user1.email;
	$scope.image= $scope.user1.image;
		
	$scope.updateUser = function(){
		AdminServices.editUser($scope)
		.then(function(_data) {
			if(_data.data != null){
					
			}
		},
		function(response) {
		});
	}
	
	$scope.cancelUser = function(){
		if($scope.editable)
			$location.path("/users");
		else
			$scope.editable = true;
	}
});

cont.controller('BussinessDetailController', function($scope,$rootScope,$location,$filter, $window, $timeout, localStorageService, AdminServices) {
	$scope.loginAlertMessage = true;
	$scope.employeeMsg = "";
	$rootScope.user = JSON.parse(localStorageService.get('user'));
	$rootScope.getAuthorisedRole = localStorageService.get('f_UserRole');
	$rootScope.isAuthorised=true;
	$rootScope.f_Name = localStorageService.get('f_Name');
	$rootScope.f_UserImage = localStorageService.get('f_UserImage');
	$rootScope.f_UserId = localStorageService.get('f_UserId');
	if($rootScope.f_UserId == null || $rootScope.f_UserId == undefined || $rootScope.f_UserId == ""){
		$location.path('/index');
	}
	$scope.bussiness=JSON.parse(localStorageService.get('bussinessInfo'));
	$scope.name=$scope.bussiness.name;
	$scope.addr=$scope.bussiness.address;
	$scope.latDetail=$scope.bussiness.lat;
	$scope.longDetail=$scope.bussiness.long;
	/*$scope.imageList= $scope.bussiness.images;*/
	$scope.imagea=$scope.bussiness.images[0];
	$scope.imageb=$scope.bussiness.images[1];
	$scope.imagec=$scope.bussiness.images[2];
	

	if($scope.imageb == null && $scope.imagec == null) {
		 var slidesInSlideshow = 1;
		 var slidesTimeIntervalInMs = 2500; 
		  
		  $scope.slideshow3 = 1;
		  var slideTimer =
		    $timeout(function interval() {
		      $scope.slideshow3 = ($scope.slideshow3 % slidesInSlideshow) + 1;
		      slideTimer = $timeout(interval, slidesTimeIntervalInMs);
		    }, slidesTimeIntervalInMs);
		
	} else if($scope.imagec == null){
	
		 var slidesInSlideshow = 2;
		 var slidesTimeIntervalInMs = 2500; 
		  
		  $scope.slideshow2 = 1;
		  var slideTimer =
		    $timeout(function interval() {
		      $scope.slideshow2 = ($scope.slideshow2 % slidesInSlideshow) + 1;
		      slideTimer = $timeout(interval, slidesTimeIntervalInMs);
		    }, slidesTimeIntervalInMs);
		
	}else{
		
		 var slidesInSlideshow = 3;
		 var slidesTimeIntervalInMs = 2500; 
		  
		  $scope.slideshow = 1;
		  var slideTimer =
		    $timeout(function interval() {
		      $scope.slideshow = ($scope.slideshow % slidesInSlideshow) + 1;
		      slideTimer = $timeout(interval, slidesTimeIntervalInMs);
		    }, slidesTimeIntervalInMs);
	}
			
	//get Deals
	if($scope.dealdata==null){
		getDealsListData();
	}
	function getDealsListData() {
		AdminServices.getDealsListData($scope)
		.then(function(_data) {
			if(_data.data != null){
				$scope.data= _data.data;
				$scope.dealdata=$scope.data.dealData;
	
			}
		},
		function(response) {
		});
	}

	//Location on Map
	$('#us3').locationpicker(
			{
				location : {
					latitude : $scope.latDetail,
					longitude : $scope.longDetail
				},
				radius : 0,
				zoom : 18,
				inputBinding : {
					latitudeInput : $('#us3-lat'),
					longitudeInput : $('#us3-lon'),
					radiusInput : $('#us3-radius'),
					locationNameInput : $('#us3-address')
				},
				enableAutocomplete : true
			});

	$scope.viewDealDetail = function(deal){
		localStorageService.set("dealInfo",JSON.stringify(deal));
		$location.path("/dealDetail");
	}
	
});

cont.controller('DealDetailController', function($scope,$rootScope,$location,$filter, $window, $timeout, localStorageService, AdminServices) {
	$scope.loginAlertMessage = true;
	$scope.employeeMsg = "";
	$rootScope.user = JSON.parse(localStorageService.get('user'));
	$rootScope.getAuthorisedRole = localStorageService.get('f_UserRole');
	$rootScope.isAuthorised=true;
	$rootScope.f_Name = localStorageService.get('f_Name');
	$rootScope.f_UserImage = localStorageService.get('f_UserImage');
	$rootScope.f_UserId = localStorageService.get('f_UserId');
	if($rootScope.f_UserId == null || $rootScope.f_UserId == undefined || $rootScope.f_UserId == ""){
		$location.path('/index');
	}
	$scope.data=JSON.parse(localStorageService.get('dealInfo'));
	$scope.dealImage= $scope.data.images;

	$scope.name1=$scope.data.name;
	$scope.createdDate= $scope.data.creationTime;
});

cont.controller('SharingController', function($scope,$rootScope,$routeParams,$location,$filter,
		$window, $timeout, localStorageService, AdminServices) {
	$scope.dealId = $routeParams.dealId;
	
});

cont.controller('UpdateVersionController', function($scope,$rootScope,$location,$filter, $window, $timeout, localStorageService, AdminServices) {
	$scope.loginAlertMessage = true;
	$scope.employeeMsg = "";
	$rootScope.user = JSON.parse(localStorageService.get('user'));
	$rootScope.getAuthorisedRole = localStorageService.get('f_UserRole');
	$rootScope.isAuthorised=true;
	$rootScope.f_Name = localStorageService.get('f_Name');
	$rootScope.f_UserImage = localStorageService.get('f_UserImage');
	$rootScope.f_UserId = localStorageService.get('f_UserId');
	if($rootScope.f_UserId == null || $rootScope.f_UserId == undefined || $rootScope.f_UserId == ""){
		$location.path('/index');
	}
	AdminServices.getAppVersion()
	.then(function(_data) {
		if(_data.data != null){
			$scope.data= _data.data.appVersion;
		}
	},
	function(error) {
		alert(JSON.stringify(error.data))
	});
	
	$scope.updateAppVersion = function(){
		if($scope.data.version != undefined && $scope.data.version != "" ){
			AdminServices.updateAppVersion($scope.data)
			.then(function(_data) {
				if(_data.data != null){
					alert(_data.data.message)
				}
			},
			function(error) {
				alert(JSON.stringify(error.data))
			});
		}else{
			alert("Version should not be empty");
		}
	}
	
});

//////////////////////////////////////////////////////////////////////////////////////////////
cont.directive('fileModel', ['$parse', function ($parse) {
	return {
		restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            element.bind('change', function(){
            	scope.$apply(function(){
            		modelSetter(scope, element[0].files[0]);
            	});
            });
        }
	};
}]);

cont.directive("mediaPreview", function ($log, $document) {
	function _link(scope, elem, attrs, ngModel) {
		function addToContainer(element) {
			element.css('border-radius','50%');
			element.css('width', '50%');
			return element.addClass(previewClass),
			container.append(element)
		}
		function onChange(e) {	
			$("#selectImage").hide();
			var files = elem[0].files;
			attrs.multiple ? ngModel.$setViewValue(files) : ngModel.$setViewValue(files[0]),
					container.empty(),
					files && files.length && angular.forEach(files, function (data, index) {
						var result,
						$mediaElement,
						$reader = new FileReader;
						$reader.onloaderror = function (e) {
							result = fallbackImage
						},
						$reader.onload = function (e) {
							result = e.target.result
						},
						$reader.onloadend = function (e) {
							return result.indexOf("data:audio") > -1 ? ($mediaElement = angular.element(document.createElement("audio")), $mediaElement.attr("controls", "true")) : result.indexOf("data:video") > -1 ? ($mediaElement = angular.element(document.createElement("video")), $mediaElement.attr("controls", "true")) : $mediaElement = angular.element(document.createElement("img")),
									$mediaElement.attr("src", result),
									addToContainer($mediaElement)
						},
						$reader.readAsDataURL(data)
					})
		}
		if ("input" !== elem[0].nodeName.toLowerCase())
			return void $log.warn("mediaPreview:", "The directive will work only for input element, actual element is a", elem[0].nodeName.toLowerCase());
		if ("file" != attrs.type)
			return void $log.warn("mediaPreview:", "Expected input type file, received instead:", attrs.type, "on element:", elem);
		elem.attr("accept") || elem.attr("accept", "image/*,video/*,audio/*");
		var container,
		fallbackImage = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAQAAAD9CzEMAAAA00lEQVR4Ae2XwQqDQAxEveinFD9e2MUfq6Cep7GnrPAg1JVCu5OTvEwe9FLtWlpqR6OyVn2aXbNGdX6KB4OLrmbRyIKsGsksWKsINhbUShM0wVcEk43CnAVY722mMEfBhPWD9mGOAlvBepSDwK1gPc5LASp8fbCJ81KACl9PNkOYo8CfKOtHUpijwJ841y1xToJy5VxXnLPgvUL1OAeBW4F6kKPAnYB6jKPAnYA68PZ/8EOCJtjvfvmdqwjSvR8gTz1YcCiytgs/TvLnvaDi/J2gCV63ZgZdEb12DwAAAABJRU5ErkJggg==",
		previewClass = attrs.previewClass || "media-preview",
		containerClass = attrs.containerClass || "media-container";
		attrs.previewContainer && (document.getElementById(attrs.previewContainer) || angular.isElement(attrs.previewContainer)) ? container = angular.isElement(attrs.previewContainer) ? attrs.previewContainer : angular.element(document.getElementById(attrs.previewContainer)) : (container = angular.element(document.createElement("div")), elem.parent()[0].insertBefore(container[0], elem[0])),
		container.addClass(containerClass),
		elem.on("change", onChange),
		scope.$on("$destroy", function () {
			elem.off("change", onChange)
		})
	}
	var directive = {
			restrict : "EA",
			require : "ngModel",
			link : _link
	};
	return directive
});

/////////////////////////////mediaPrevieww
cont.directive("mediaPrevieww", function ($log, $document) {
	function _link(scope, elem, attrs, ngModel) {
		function addToContainer(element) {
			return element.addClass(previewClass),
			container.append(element)
		}
		function onChange(e) {	
			$("#selectImage").hide();
			var files = elem[0].files;
			attrs.multiple ? ngModel.$setViewValue(files) : ngModel.$setViewValue(files[0]),
					container.empty(),
					files && files.length && angular.forEach(files, function (data, index) {
						var result,
						$mediaElement,
						$reader = new FileReader;
						$reader.onloaderror = function (e) {
							result = fallbackImage
						},
						$reader.onload = function (e) {
							result = e.target.result
						},
						$reader.onloadend = function (e) {
							return result.indexOf("data:audio") > -1 ? ($mediaElement = angular.element(document.createElement("audio")), $mediaElement.attr("controls", "true")) : result.indexOf("data:video") > -1 ? ($mediaElement = angular.element(document.createElement("video")), $mediaElement.attr("controls", "true")) : $mediaElement = angular.element(document.createElement("img")),
									$mediaElement.attr("src", result),
									addToContainer($mediaElement)
						},
						$reader.readAsDataURL(data)
					})
		}
		if ("input" !== elem[0].nodeName.toLowerCase())
			return void $log.warn("mediaPreview:", "The directive will work only for input element, actual element is a", elem[0].nodeName.toLowerCase());
		if ("file" != attrs.type)
			return void $log.warn("mediaPreview:", "Expected input type file, received instead:", attrs.type, "on element:", elem);
		elem.attr("accept") || elem.attr("accept", "image/*,video/*,audio/*");
		var container,
		fallbackImage = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAQAAAD9CzEMAAAA00lEQVR4Ae2XwQqDQAxEveinFD9e2MUfq6Cep7GnrPAg1JVCu5OTvEwe9FLtWlpqR6OyVn2aXbNGdX6KB4OLrmbRyIKsGsksWKsINhbUShM0wVcEk43CnAVY722mMEfBhPWD9mGOAlvBepSDwK1gPc5LASp8fbCJ81KACl9PNkOYo8CfKOtHUpijwJ841y1xToJy5VxXnLPgvUL1OAeBW4F6kKPAnYB6jKPAnYA68PZ/8EOCJtjvfvmdqwjSvR8gTz1YcCiytgs/TvLnvaDi/J2gCV63ZgZdEb12DwAAAABJRU5ErkJggg==",
		previewClass = attrs.previewClass || "media-previeww",
		containerClass = attrs.containerClass || "media-containerr";
		attrs.previewContainer && (document.getElementById(attrs.previewContainer) || angular.isElement(attrs.previewContainer)) ? container = angular.isElement(attrs.previewContainer) ? attrs.previewContainer : angular.element(document.getElementById(attrs.previewContainer)) : (container = angular.element(document.createElement("div")), elem.parent()[0].insertBefore(container[0], elem[0])),
		container.addClass(containerClass),
		elem.on("change", onChange),
		scope.$on("$destroy", function () {
			elem.off("change", onChange)
		})
	}
	var directive = {
			restrict : "EA",
			require : "ngModel",
			link : _link
	};
	return directive
});