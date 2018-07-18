<aside class="sidebar" ng-show="isAuthorised && getAuthorisedRole == 1">
<!-- Sales Director Menu -->
	<ul ng-show="getAuthorisedRole == 1">
	<li><a id="dashboard" href="#dashboard" class="active" ><i class="fa fa-dashboard"></i> <span>Dashboard</span></a></li>
	<li><a id="users" href="#users" ><i class="fa fa-users"></i> <span>Users</span></a></li>
	<li><a id="bussiness" href="#bussiness" ><i class="fa fa-users"></i> <span>Bussiness</span></a></li>
	<li><a id="changePassword" href="#changePassword"><i class="fa fa-cog"></i> <span>Change Password</span></a></li>
	<li><a id="AppVersion" href="#AppVersion"><i class="fa fa-cog"></i> <span>App Version</span></a></li>
	<li ng-controller="LogoutController"><a href="" ng-click="logout()"><i class="fa fa-sign-in"></i> <span>Logout</span></a></li>
	</ul>
	
	
</aside>
<script>
	$( "#users" ).click(function() {
		$("#dashboard").removeClass('active');
		$("#changePassword").removeClass('active');
		$("#bussiness").removeClass('active');
		$(this).addClass('active');
	});
	 $( "#dashboard" ).click(function() {
		$("#users").removeClass('active');
		$("#bussiness").removeClass('active');
		$("#changePassword").removeClass('active');
		$(this).addClass('active');
	});
	 $( "#changePassword" ).click(function() {
			$("#dashboard").removeClass('active');
			$("#users").removeClass('active');
			$("#bussiness").removeClass('active');
			$(this).addClass('active');
		});
	 $( "#bussiness" ).click(function() {
			$("#users").removeClass('active');
			$("#dashboard").removeClass('active');
			$("#changePassword").removeClass('active');
			$(this).addClass('active');
		});
</script>