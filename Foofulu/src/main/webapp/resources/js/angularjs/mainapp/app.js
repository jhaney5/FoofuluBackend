var myInterceptor = function($q,$rootScope) {
  return {
    request: function(config) {
      console.log('requst started...'+config.url);
      if(config.url != "checkUsername" && config.url != "checkUserPassword"){
    	  if($rootScope.callCount == 0){
    		//  $('#mydiv').show();
    	  }
      }
      //$rootScope.start();
      return config;
    },

    response: function(result) {
      //console.log('data for ' + result.data.name + ' received');
      console.log('request completed');
      //$rootScope.deductCallCount();
      if($rootScope.callCount <= 0){
    	  //$('#mydiv').hide();
    	  if($rootScope.callCount < 0){
    		  $rootScope.callCount = 0;
    	  }
      }
      return result;
    },
    responseError: function(rejection) {
      console.log('Failed with', rejection.status, 'status');
      //$rootScope.deductCallCount();
      if($rootScope.callCount <= 0){
    	  //$('#mydiv').hide();
    	  if($rootScope.callCount < 0){
    		  $rootScope.callCount = 0;
    	  }
      }
      return $q.reject(rejection);
    }
  }
}
var mainApp = angular.module('mainApp', 
						['ui.bootstrap','routing','ngIdle','angularUtils.directives.dirPagination','admincontroller','adminservices','logincontroller','colorpicker.module', 'chart.js','wysiwyg.module','angular-flexslider']).config(function($httpProvider) {
							  $httpProvider.interceptors.push(myInterceptor);
						});
mainApp.directive('loading', ['$http', function ($http) {
    return {
        restrict: 'A',
        link: function (scope, elm, attrs) {
            scope.isLoading = function () {
                return $http.pendingRequests.length > 0;
            };

            scope.$watch(scope.isLoading, function (v) {
                if (v) {
                    elm.show();
                } else {
                    elm.hide();
                }
            });
        }
    };

}]);
mainApp.run(function ($timeout, $document, $window, $rootScope) {

    var TimeOutTimerValue = 1800000;
    //var TimeOutTimerValue = 30000;
    var TimeOut_Thread = $timeout(function () { LogoutByTimer() }, TimeOutTimerValue);
    var bodyElement = angular.element($document);

    angular.forEach(['keydown', 'keyup', 'click', 'mousemove', 'DOMMouseScroll', 'mousewheel', 'mousedown', 'touchstart', 'touchmove', 'scroll', 'focus'],
    function (EventName) {
        bodyElement.bind(EventName, function (e) { TimeOut_Resetter(e) });
    });

    function LogoutByTimer() {
        //$window.location.href = '#/index';
    	$rootScope.logout();
    }

    function TimeOut_Resetter(e) {
        $timeout.cancel(TimeOut_Thread);
        TimeOut_Thread = $timeout(function () { LogoutByTimer() }, TimeOutTimerValue);
    }
});