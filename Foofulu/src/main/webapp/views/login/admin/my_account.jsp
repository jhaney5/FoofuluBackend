<section class="main_dashborad_area">
<div class="row">
<aside class="content_area"
	data-ng-controller="MyAccountController">
	<section class="top_header">
		<ol class="breadcrumb">
			<li><a href="#dashboard">Home</a></li>
			<li><a href="#my_account">Admin Detail</a></li>
			<li class="active">Update Admin</li>
		</ol>
	</section>
	<section class="inner_content1">
		<div class="box-body">
			<h3>Update Admin</h3>
			<form class="form-horizontal tenant_form" name="addUsersForm">
				
				<div class="form-group">
					<label class="col-sm-3 control-label">Profile Image</label>
					<div class="controls col-sm-3">
						<img ng-if="userImage != null" src="<%=request.getContextPath()%>/{{userImage}}"
							class="img-circle" style="width: 75px; height: 75px;" alt="">
						<input type="file" accept="image/gif, image/jpeg,image/png" id="addUpdateUserImage" name="ProfilePic" data-ng-model="userImage" file-model="myFile" onchange="angular.element(this).scope().fileNameChanged()" media-preview ng-disabled="truefalse" ng-change="disablePhoto()"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">Full Name</label>
					<div class="col-sm-8">
						<input type="hidden" data-ng-model="id">
						<input type="text" class="form-control ForNames"  name="FullName" data-ng-model="name" ng-readonly="truefalse" required>
						<span class="validmsz" ng-show="addUsersForm.$submitted || addUsersForm.FullName.$touched">
                          	<span ng-show="addUsersForm.FullName.$error.required"><font class="redFont">Full Name is required.</font></span>
                        </span>
					</div>
				</div>
					
				<div class="form-group">
					<label for="Address" class="col-sm-2 control-label">Email</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" name="Email" data-ng-model="email" ng-readonly="truefalse" ng-pattern="/^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/" ng-change="verifyDuplicate1()" required>
						<span class="validmsz" ng-show="addUsersForm.$submitted || addUsersForm.Email.$touched">
							<span ng-show="addUsersForm.Email.$error.required"><font class="redFont">Email is required.</font></span>
                          	<span ng-show="addUsersForm.Email.$error.pattern"><font class="redFont">Please enter valid email.</font></span>
                        	<font class="redFont"><div class="error" ng-show='isDuplicate'>Email not avaialbe</div></font>
                        </span>
					</div>
				</div>
				
				<div class="form-group col-md-14">
					<div class="col-md-offset-5 col-md-9">
						<button type="button" class="btn btn-default" ng-click="cancelUser()">Cancel</button>
						<button type="button" class="btn btn-warning" style="margin-left: 20px;background-color: #fb8a00" ng-click="update()" >Submit</button>
					</div>
				</div>
				</form>
					</div>
		
			</form>

		</div>
		<!-- /.box-body -->
	</section>
</aside>
</div>
</section>