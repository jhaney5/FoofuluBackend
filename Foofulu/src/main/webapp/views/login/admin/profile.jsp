<section class="main_dashborad_area">
<div class="row">
<aside class="content_area"
	data-ng-controller="ProfileSalesDirectorController">
	<section class="top_header">
		<ol class="breadcrumb">
			<li><a href="#salesDirectorDashboard">Home</a></li>
			<li class="active">Profile</li>
		</ol>
	</section>
	<section class="inner_content1">
		<div class="box-body">
			<h3>Add/Update User</h3>
			<div ng-hide="loginAlertMessage">
				<center><b>{{employeeMsg}}</b></center>
			</div>
			<form class="form-horizontal tenant_form" name="addUsersForm">
				
				<input type="hidden" id="id" name="id" ng-model="id" value="" style="display: none;">
				<input type="hidden" id="logoImageName" name="logoImageName" ng-model="logoImageName" value="" style="display: none;">
				<input type="hidden" id="deviceId" name="id" ng-model="deviceId" value="" style="display: none;">
				<input type="hidden" id="username1" name="username1" ng-model="username1" value="" style="display: none;">
				<input type="hidden" id="status" name="id" ng-model="status" value="" style="display: none;">
				<input type="hidden" id="registeredFrom" name="id" ng-model="registeredFrom" value="" style="display: none;">
				<input type="hidden" id="registeredTime" name="registeredTime" ng-model="registeredTime" value="" style="display: none;">
				<input type="hidden" id="secretKey" name="secretKey" ng-model="secretKey" value="" style="display: none;">
				<input type="hidden" id="password" name="password" ng-model="password" value="" style="display: none;">
				<input type="hidden" id="supervisor" name="supervisor" ng-model="supervisor" value="" style="display: none;">
				<input type="hidden" id="role" name="role" ng-model="role" value="" style="display: none;">
				<div class="form-group">
					<label class="col-sm-3 control-label">Profile Image</label>
					<div class="controls col-sm-6">
						<img  ng-if="logoImageName != null" src="<%=request.getContextPath()%>/{{logoImageName}}"
												class="img-circle" style="width: 75px; height: 75px;" alt="User Image">
						<input type="file" id="addUpdateUserImage" name="ProfilePic" data-ng-model="image" file-model="myFile" onchange="angular.element(this).scope().fileNameChanged()" media-preview ng-disabled="truefalse" ng-change="disablePhoto()"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">First Name</label>
					<div class="col-sm-8">
						<input type="text" class="form-control ForNames"  name="FirstName" data-ng-model="firstName" ng-readonly="truefalse" ng-click="disablePhoto()" required>
						<span class="validmsz" ng-show="addUsersForm.$submitted || addUsersForm.FirstName.$touched">
                          	<span ng-show="addUsersForm.FirstName.$error.required"><font class="redFont">First Name is required.</font></span>
                        </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">Last Name</label>
					<div class="col-sm-8">
						<input type="text" class="form-control ForNames" name="LastName" data-ng-model="lastName" ng-readonly="truefalse" required>
						<span class="validmsz" ng-show="addUsersForm.$submitted || addUsersForm.LastName.$touched">
                          	<span ng-show="addUsersForm.LastName.$error.required"><font class="redFont">Last Name is required.</font></span>
                        </span>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">Username</label>
					<div class="col-sm-8">
						<input type="text" class="form-control ForUsernames"  name="Username" data-ng-model="username" ng-readonly="id > 0" ng-change="verifyDuplicate()" required>
						<span class="validmsz" ng-show="addUsersForm.$submitted || addUsersForm.Username.$touched">
                          	<span ng-show="addUsersForm.Username.$error.required"><font class="redFont">Username is required.</font></span>
                        	<font class="redFont"><div class="error" ng-show='isDuplicate'>Username not avaialbe</div></font>
                        </span>
					</div>
				</div>

				<!-- <div class="form-group">
					<label class="col-sm-2 control-label">Password</label>
					<div class="col-sm-8">
						<input type="password" class="form-control" name="Password" data-ng-model="password" ng-readonly="truefalse" required>
						<span class="validmsz" ng-show="addUsersForm.$submitted || addUsersForm.Password.$touched">
                          	<span ng-show="addUsersForm.Password.$error.required"><font class="redFont">Password is required.</font></span>
                        </span>
					</div>
				</div> -->

				<div class="form-group">
					<label for="Rent Deposite" class="col-sm-2 control-label">Gender</label>
					<div class="col-sm-8">
						 <input type="radio" name="male" value="m" ng-model="gender" ng-disabled="truefalse" ng-checked="{{male}}">Male
						 <input type="radio" name="female" value="f" ng-model="gender" ng-disabled="truefalse"  ng-checked="{{female}}">Female

					</div>
				</div>

				<div class="form-group">
					<label for="Address" class="col-sm-2 control-label">Address</label>
					<div class="col-sm-8">
						<input type="text" class="form-control"  name="Address" data-ng-model="address" ng-readonly="truefalse" required>
						<span class="validmsz" ng-show="addUsersForm.$submitted || addUsersForm.Address.$touched">
                          	<span ng-show="addUsersForm.Address.$error.required"><font class="redFont">Address is required.</font></span>
                        </span>
					</div>
				</div>

				<div class="form-group">
					<label for="Address" class="col-sm-2 control-label">Email</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" name="Email" data-ng-model="email" ng-readonly="truefalse" ng-pattern="/^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/" required>
						<span class="validmsz" ng-show="addUsersForm.$submitted || addUsersForm.Email.$touched">
							<span ng-show="addUsersForm.Email.$error.required"><font class="redFont">Email is required.</font></span>
                          	<span ng-show="addUsersForm.Email.$error.pattern"><font class="redFont">Please enter valid email.</font></span>
                        </span>
					</div>
				</div>

				<div class="form-group">
					<label for="State" class="col-sm-2 control-label">Phone</label>
					<div class="col-sm-8">
						<input type="text numeric" class="form-control"  name="Phone" data-ng-model="phone" ng-readonly="truefalse" ng-minlength="10" ng-maxlength="13" ng-change="verifyDuplicatePhone()" required>
						<span class="validmsz" ng-show="addUsersForm.$submitted || addUsersForm.Phone.$touched">
                          	<span ng-show="addUsersForm.Phone.$error.required"><font class="redFont">Phone is required.</font></span>
                        	<span ng-show="addUsersForm.Phone.$error.minlength"><font class="redFont">Phone must be over 10 numbers.</font></span>
                          	<span ng-show="addUsersForm.Phone.$error.maxlength"><font class="redFont">Phone must not exceed 13 numbers.</font></span>
                        	<font class="redFont"><div class="error" ng-show='isDuplicatePhone'>Phone Number already exist.</div></font>
                        </span>
					</div>
				</div>

				<div class="form-group" ng-show="showCustomerDivs">
					<label class="col-sm-2 control-label">Occupation</label>
					<div class="col-sm-8">
						<input type="text" class="form-control ForNames" name="Occupation" data-ng-model="occupation" ng-readonly="truefalse">
					</div>
				</div>
				<div class="form-group" ng-show="showCustomerDivs">
					<label class="col-sm-2 control-label">Annual Income</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" name="AnnualIncome" data-ng-model="annualIncome" ng-readonly="truefalse">
						<select ng-model="amountUnit">
							<option value="">--Select--</option>
							<option value="Thousand">Thousand</option>
							<option value="Lakh">Lakh</option>
							<option value="Crore">Crore</option>
							<option value="Million">Million</option>
							<option value="Billion">Billion</option>
							<option value="Trillion">Trillion</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="button" class="btn btn-default" ng-click="cancelUser()">Cancel</button>
						<button type="button" class="btn btn-warning" ng-click="addUser()">{{btnName}}</button>
					</div>
				</div>
			</form>


		</div>
		<!-- /.box-body -->
	</section>
</aside>
</div>
</section>