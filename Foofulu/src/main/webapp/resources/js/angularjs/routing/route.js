'use strict';

angular.module('routing', [ 'ngRoute' ]).config(
		[ '$routeProvider', function($routeProvider) {
			$routeProvider.when('/index', {                  //Employee Module
				templateUrl : 'ViewCalls/login.do',
			}).when('/dashboard', {
				templateUrl : 'ViewCalls/dashboardPage.do',
			}).when('/users', {
				templateUrl : 'ViewCalls/usersPage.do',
			}).when('/userDetail', {
				templateUrl : 'ViewCalls/userDetailPage.do',
			}).when('/bussiness', {
				templateUrl : 'ViewCalls/bussinessPage.do',
			}).when('/bussinessDetails', {
				templateUrl : 'ViewCalls/bussinessDetailsPage.do',
			}).when('/dealDetail', {
				templateUrl : 'ViewCalls/dealDetailPage.do',
			}).when('/profile', {
				templateUrl : 'ViewCalls/profilePage.do',
			}).when('/changePassword', {
				templateUrl : 'ViewCalls/changePasswordPage.do',
			}).when('/contactUs', {
				templateUrl : 'ViewCalls/contactUsPage.do',
			}).when('/my_account', {
				templateUrl : 'ViewCalls/my_accountPage.do',
			}).when('/update_profile', {
				templateUrl : 'ViewCalls/update_profilePage.do',
			}).when('/AboutUs', {
				templateUrl : 'ViewCalls/AboutUsPage.do',
			}).when('/DealSharing', {
				templateUrl : 'ViewCalls/DealSharingPage.do',
			}).when('/AppVersion', {
				templateUrl : 'ViewCalls/AppVersionPage.do',
			}).otherwise({
				redirectTo : '/index'
			});
		}]);

