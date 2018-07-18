<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>FooFulu</title>
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/font-awesome.css" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/material-design-iconic-font.min.css" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/style.css">

<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/responsive.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/font-awesome.css" type="text/css" />
<link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">

	<%-- <link rel="stylesheet"  href="<%=request.getContextPath()%>/resources/css1/lightslider.css"/>
   	<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css1/style.css">
   	<link href="https://fonts.googleapis.com/css?family=Roboto:300,300i,400,400i,500" rel="stylesheet"> --%>

	<script src="<%=request.getContextPath()%>/resources/js/normaljs/js/jquery.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/resources/js/normaljs/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/normaljs/js/app.min.js"></script>

	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.js"></script><!--js for date angular  -->
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-animate.min.js"></script><!--js for date picker  -->
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular-animate.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular-aria.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-route.min.js"></script><!--js for routing  -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/ng-idle/1.2.2/angular-idle.js"></script><!--js for idle time  -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/ng-idle/1.2.2/angular-idle.map"></script><!--js for idle time  -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/1.3.3/ui-bootstrap.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/1.3.3/ui-bootstrap-tpls.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.19/angular-cookies.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.17/angular-sanitize.min.js"></script>
	<script src="<%=request.getContextPath() %>/resources/js/normaljs/angular-local-storage.min.js"></script>
	<script src="http://google-code-prettify.googlecode.com/svn/trunk/src/prettify.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/normaljs/dirPagination.js?1500"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/normaljs/clientPagination.js?1500"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-messages.min.js"></script><!-- js for ngMessages -->
	<script src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/t-114/svg-assets-cache.js"></script><!-- js for ngMessages -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/angular-xeditable/0.1.12/js/xeditable.js"></script>
	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.bundle.js"></script>
	<script src="<%=request.getContextPath() %>/resources/js/normaljs/angular-chart.js"></script>
	<script src="<%=request.getContextPath() %>/resources/js/normaljs/angular-chart.min.js"></script>
	
	<script data-require="bootstrap@*" data-semver="3.1.1" src="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
   <link href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
   <link href="<%=request.getContextPath() %>/resources/js/normaljs/js/editor.css" rel="stylesheet">
   <script src="<%=request.getContextPath() %>/resources/js/normaljs/js/bootstrap-colorpicker-module.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/normaljs/js/angular-wysiwyg.js"></script>
    
    <script src="//angular-ui.github.io/bootstrap/ui-bootstrap-tpls-1.3.2.js"></script>
    
    
<!--[if lt IE 9]>
			<script src="js/html5shiv.js"></script>
			<script src="http://ie7-js.googlecode.com/svn/version/2.1(beta4)/IE9.js"></script>
			<script src="js/respond.js"></script>
		<![endif]-->
	
	 <!--  Moment Picker  -->
	 <script src="//cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.6/moment-with-locales.js"></script>
	     <link rel="stylesheet"  href="<%=request.getContextPath()%>/resources/js/dist/angular-moment-picker.css"/>
	     <link rel="stylesheet"  href="<%=request.getContextPath()%>/resources/js/dist/angular-moment-picker.min.css"/>
	     <script src="<%=request.getContextPath() %>/resources/js/dist/angular-moment-picker.js"></script>
	     <script src="<%=request.getContextPath() %>/resources/js/dist/angular-moment-picker.min.js"></script>
		
	<script src="<%=request.getContextPath() %>/resources/js/angularjs/mainapp/app.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/angularjs/routing/route.js"></script>
	<script src="<%=request.getContextPath() %>/resources/js/angularjs/controllers/Login.js"></script>
	<script src="<%=request.getContextPath() %>/resources/js/angularjs/controllers/AdminController.js"></script>
	<script src="<%=request.getContextPath() %>/resources/js/angularjs/services/AdminServices.js"></script>
	 
	<script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
  	<script type="text/javascript" src='http://maps.google.com/maps/api/js?sensor=false&libraries=places&key=  AIzaSyDAoXX_NnO7iVp7ENOGX-plaQOa5-1AUfs '></script>
  	<script src="<%=request.getContextPath() %>/resources/js/normaljs/locationpicker.jquery.min.js"></script>
  	
  	<script src="<%=request.getContextPath() %>/resources/js/normaljs/angularjs-datetime-picker.js"></script>
	<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/js/normaljs/angularjs-datetime-picker.css" />

		 <script src="https://rawgit.com/wilsonwc/angular-flexslider/master/angular-flexslider.js"></script>
 	<script src="https://cdnjs.cloudflare.com/ajax/libs/flexslider/2.2.2/jquery.flexslider.js"></script>
	 <link href="https://cdnjs.cloudflare.com/ajax/libs/flexslider/2.2.2/flexslider.css" rel="stylesheet">
	<link rel="stylesheet" href="resources/dropdown/angular-dropdowns.min.css">
    <!-- <link rel="stylesheet" href="example/page.css"> -->
    <script type="text/javascript" src="resources/dropdown//angular-dropdowns.js"></script>

 <!-- <link href='http://fonts.googleapis.com/css?family=Advent+Pro' rel='stylesheet' type='text/css'> -->

</head>

<body class="hold-transition skin-blue sidebar-mini"  ng-app = "mainApp">

	<%@ include file="header.jsp" %>

	<%@ include file="leftMenu.jsp" %>
	<div ng-view></div>
	<script src="<%=request.getContextPath()%>/resources/js/normaljs/js/tooltip.js"></script>
	<script>
		$(function () {
	  		$('[data-toggle="tooltip"]').tooltip()
		})
	</script>

	<script type="text/javascript">

        function validation() {
        	$(".ForUsernames").bind('keypress', function (event) {
                var regex = new RegExp("^[a-zA-Z0-9._@]+$");
                var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
                if (regex.test(key) == false && event.keyCode != 8 && event.keyCode != 9 && event.keyCode != 46 && event.keyCode != 35 && event.keyCode != 36 && event.keyCode != 37 && event.keyCode != 39) {
                    event.preventDefault();
                    return false;
                }
            });
        	$(".ForNames").bind('keypress', function (event) {
                var regex = new RegExp("^[a-zA-Z .]+$");
                var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
                if (regex.test(key) == false && event.keyCode != 8 && event.keyCode != 9 && event.keyCode != 46 && event.keyCode != 35 && event.keyCode != 36 && event.keyCode != 37 && event.keyCode != 39) {
                    event.preventDefault();
                    return false;
                }
            });
        	$(".AlphaNumericSpace").bind('keypress', function (event) {
                var regex = new RegExp("^[a-zA-Z0-9 _-]+$");
                var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
                if (regex.test(key) == false && event.keyCode != 8 && event.keyCode != 9 && event.keyCode != 46 && event.keyCode != 35 && event.keyCode != 36 && event.keyCode != 37 && event.keyCode != 39) {
                    event.preventDefault();
                    return false;
                }
            });
            $(".NotSpecial").bind('keypress', function (event) {
                var regex = new RegExp("^[a-zA-Z0-9]+$");
                var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
                if (regex.test(key) == false && event.keyCode != 8 && event.keyCode != 9 && event.keyCode != 46 && event.keyCode != 35 && event.keyCode != 36 && event.keyCode != 37 && event.keyCode != 39) {
                    event.preventDefault();
                    return false;
                }
            });

            $(".CharacterOnly").bind('keypress', function (event) {
                var regex = new RegExp("^[a-zA-Z]+$");
                var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
                if (regex.test(key) == false && event.keyCode != 8 && event.keyCode != 9 && event.keyCode != 46 && event.keyCode != 35 && event.keyCode != 36 && event.keyCode != 37 && event.keyCode != 39) {
                    event.preventDefault();
                    return false;
                }
            });

            $(".numeric").bind('keypress', function (event) {
                var regex = new RegExp("^[0-9]+$");
                var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
                if (regex.test(key) == false && event.keyCode != 8 && event.keyCode != 9 && event.keyCode != 46 && event.keyCode != 35 && event.keyCode != 36 && event.keyCode != 37 && event.keyCode != 39) {
                    event.preventDefault();
                    return false;
                }
            });

            $(".numericFloat").bind('keypress', function (event) {
                var regex = new RegExp("^[0-9.]+$");
                var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
                if (regex.test(key) == false && event.keyCode != 8 && event.keyCode != 9 && event.keyCode != 46 && event.keyCode != 35 && event.keyCode != 36 && event.keyCode != 37 && event.keyCode != 39) {
                    event.preventDefault();
                    return false;
                }
            });

            $(".price").bind('keypress', function (event) {
                var regex = new RegExp("^[0-9.]+$");
                var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
                if (regex.test(key) == false && event.keyCode != 8 && event.keyCode != 9 && event.keyCode != 46 && event.keyCode != 35 && event.keyCode != 36 && event.keyCode != 37 && event.keyCode != 39) {
                    event.preventDefault();
                    return false;
                }
            });
        }

        function CheckPassword(inputtxt) {
            var paswd = /^(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{4,20}$/;
            if (inputtxt != "") {
                if (inputtxt.length > 3) {
                    //if (inputtxt.match(paswd)) {
                        return true;
                    //}
                    //else {
                    //    alert('Password must contain at least one special character and one capital letter.');
                    //    return false;
                    //}
                }
                else {
                    alert('New password must contain at least four character.');
                    return false;
                }
            }
        }
        
       </script>
</body>
</html>