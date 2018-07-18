<section class="main_dashborad_area">
<div class="row">
<aside class="content_area"
	data-ng-controller="UpdateVersionController">
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
					<label class="col-sm-2 control-label">Device Type</label>
					<div class="col-sm-8">
						<input type="text" class="form-control ForNames"  name="CurrentPassword" data-ng-model="data.deviceType" ng-readonly="true" required>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">App Version</label>
					<div class="col-sm-8">
						<input type="text" class="form-control ForNames" name="NewPassword" data-ng-model="data.version" ng-readonly="truefalse" required>
						<span class="validmsz" ng-show="addUsersForm.$submitted || addUsersForm.NewPassword.$touched">
                          	<span ng-show="addUsersForm.NewPassword.$error.required"><font class="redFont">App version is required.</font></span>
                        </span>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="button" class="btn btn-default" ng-click="cancel()" >Cancel</button>
						<button type="button" class="btn btn-warning" ng-click="updateAppVersion()" style="background-color: #fb8a00">Change</button>
					</div>
				</div>
			</form>


		</div>
		<!-- /.box-body -->
	</section>
</aside>
</div>
</section>