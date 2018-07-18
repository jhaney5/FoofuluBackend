<section class="main_dashborad_area">
<div class="row" >
 <aside class="content_area loginpagearea" data-ng-controller="LoginController">
	<!-- start login area here -->
	<div class="" >
		<div class="dashboard_cont">
			<div class="home_logo"><center><img src="<%=request.getContextPath()%>/resources/images/main_logo.png" height="100"></center></br></div>
			<div class="login_outerbox">
				<div class="login_heading">
					<h4>Admin Login</h4>
					<p><!-- Select your account type before login --></p>
				</div>
				<div ng-hide="loginAlertMessage">
					<center><b>{{employeeMsg}}</b></center>
				</div>
				<div class="inner_loginarea">
					<form name="loginForm">
			
						<div class="form-group">
							<label for="exampleInputPassword1">Email</label>
							<input type="text" class="form-control" id="email" name="Email" ng-model="email" placeholder="Email" required><i class="zmdi zmdi-email" style="color: #fb8a00"></i>
							<span class="validmsz" ng-show="loginForm.$submitted || loginForm.Email.$touched">
	                          	<span ng-show="loginForm.Email.$error.required"><font class="redFont">Email is required.</font></span>
	                        </span>
						</div>
						<div class="form-group">
							<label for="exampleInputPassword1">Password</label>
							<input type="password" class="form-control" id="password" name="Password" ng-model="password" placeholder="Password" required><i class="zmdi zmdi-lock-outline" style="color: #fb8a00"></i>
							<span class="validmsz" ng-show="loginForm.$submitted || loginForm.Password.$touched">
	                          	<span ng-show="loginForm.Password.$error.required"><font class="redFont">Password is required.</font></span>
	                        </span>
						</div>
						<div class="clearfix"></div>
						<a href="" class="btn btn-default btn-block" ng-click="checkAuthentication()">Login</a>
					</form>
				</div>

			</div>
		</div>
	</div>
	<!-- end login area here -->
</aside>
</div>
</section>
