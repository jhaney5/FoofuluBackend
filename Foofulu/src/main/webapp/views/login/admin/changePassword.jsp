<section class="main_dashborad_area">
<div class="row">
<aside class="content_area"
	data-ng-controller="ChangePasswordController">
	<section class="top_header">
		<ol class="breadcrumb">
			<li><a href="#users" >Users</a></li>
			<li class="active">Change Password</li>
		</ol>
	</section>
	<section class="inner_content1">
		<div class="box-body">
			<h3>Change Password</h3>
			<div ng-hide="loginAlertMessage">
				<center><b>{{employeeMsg}}</b></center>
			</div>
			<form class="form-horizontal tenant_form" name="addUsersForm">
				
				<div class="form-group">
					<label class="col-sm-2 control-label">Current Password</label>
					<div class="col-sm-8">
						<input type="text" class="form-control ForNames"  name="CurrentPassword" data-ng-model="currentPassword" ng-readonly="truefalse" required>
						<span class="validmsz" ng-show="addUsersForm.$submitted || addUsersForm.CurrentPassword.$touched">
                          	<span ng-show="addUsersForm.CurrentPassword.$error.required"><font class="redFont">Current Password is required.</font></span>
                        </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">New Password</label>
					<div class="col-sm-8">
						<input type="text" class="form-control ForNames" name="NewPassword" data-ng-model="newPassword" ng-readonly="truefalse" required>
						<span class="validmsz" ng-show="addUsersForm.$submitted || addUsersForm.NewPassword.$touched">
                          	<span ng-show="addUsersForm.NewPassword.$error.required"><font class="redFont">New Password is required.</font></span>
                        </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">Confirm Password</label>
					<div class="col-sm-8">
						<input type="text" class="form-control ForNames" name="ConfirmPassword" data-ng-model="confirmPassword" ng-readonly="truefalse" required>
						<span class="validmsz" ng-show="addUsersForm.$submitted || addUsersForm.ConfirmPassword.$touched">
                          	<span ng-show="addUsersForm.ConfirmPassword.$error.required"><font class="redFont">Confirm Password is required.</font></span>
                        </span>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="button" class="btn btn-default" ng-click="cancelPassword()" >Cancel</button>
						<button type="button" class="btn btn-warning" ng-click="changeSystemUserPassword()" style="background-color: #fb8a00">Change</button>
					</div>
				</div>
			</form>


		</div>
		<!-- /.box-body -->
	</section>
</aside>
</div>
</section>