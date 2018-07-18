<section data-ng-controller="ContactUsController" class="main_dashborad_area">

<div class="content_area" >	
      <div class="row">
		<section class="top_header">
		<ol class="breadcrumb">
			<li><a href="#users">Users</a></li>
			<li class="active">ContactUs</li>
		</ol>
	</section>
	
	<section class="inner_content1">
		<div class="box-body">
			<h3>Contact Us Details</h3>
			<div ng-hide="loginAlertMessage">
				<center><b>{{employeeMsg}}</b></center>
			</div>
			    <table class="table table-user-information">
                    <tbody>
                     <tr>
                        <td>Official Email : </td>
                        <td><input class="form-control AlphaNumericSpace" placeholder="Official Email ID *" type="text" name="name" ng-model="emailId"   ng-readonly="isReadonly" required/>
											<span class="validmsz" ng-show="addUsersForm.$submitted || addUsersForm.name.$touched">
					                          	<span ng-show="addUsersForm.name.$error.required"><font class="redFont">Email is required.</font></span>
					                        </span></td>
                      </tr>
                       <tr>
                        <td>Mobile No : </td>
                        <td><input class="form-control AlphaNumericSpace" placeholder="Mobile No *" type="text" name="name" ng-model="mobile"   ng-readonly="isReadonly" required/>
											<span class="validmsz" ng-show="addUsersForm.$submitted || addUsersForm.name.$touched">
					                          	<span ng-show="addUsersForm.name.$error.required"><font class="redFont">Mobile no is required.</font></span>
					                        </span></td>
                      </tr>
                       <tr>
                        <td></td>
                        <td>
                    <div class="pull-right">
                      <button type="submit" class="btn btn-default" ng-click="addContactUs()" style="background-color: #4fc1f9">UPDATE</button>
			</div>
                        </td>
                      </tr>
                    </tbody>
                  </table>

		</div>
		<!-- /.box-body -->
	</section>
	
	
	
	
	
	<!-- 
	<section>
       <div class="col-md-2  toppad  pull-right col-md-offset-3 ">
       <br><br>
       <br>
       <br>
      </div>
        <div class="col-xs-14 col-sm-14 col-md-8 col-lg-8 col-xs-offset-0 col-sm-offset-0 col-md-offset-3 col-lg-offset-3 toppad" >
          <div class="panel panel-info" >
            <div class="panel-heading" style="background-color: #4fc1f9">
              <h3 class="panel-title" style="color:white;">Contact Us Details</h3>
            </div>
            <div class="panel-body">
              <div class="row">
              <div class="col-xs-10 col-sm-10 hidden-md hidden-lg"> <br>
                  <dl>
                    <dt>DEPARTMENT:</dt>
                    <dd>Administrator</dd>
                    <dt>HIRE DATE</dt>
                    <dd>11/12/2013</dd>
                    <dt>DATE OF BIRTH</dt>
                       <dd>11/12/2013</dd>
                    <dt>GENDER</dt>
                    <dd>Male</dd>
                  </dl>
                </div>
                <div class=" col-md-10 col-lg-10 "> 
                  <table class="table table-user-information">
                    <tbody>
                     <tr>
                        <td>Official Email : </td>
                        <td><input class="form-control AlphaNumericSpace" placeholder="Official Email ID *" type="text" name="name" ng-model="emailId"   ng-readonly="isReadonly" required/>
											<span class="validmsz" ng-show="addUsersForm.$submitted || addUsersForm.name.$touched">
					                          	<span ng-show="addUsersForm.name.$error.required"><font class="redFont">Email is required.</font></span>
					                        </span></td>
                      </tr>
                       <tr>
                        <td>Mobile No : </td>
                        <td><input class="form-control AlphaNumericSpace" placeholder="Mobile No *" type="text" name="name" ng-model="mobile"   ng-readonly="isReadonly" required/>
											<span class="validmsz" ng-show="addUsersForm.$submitted || addUsersForm.name.$touched">
					                          	<span ng-show="addUsersForm.name.$error.required"><font class="redFont">Mobile no is required.</font></span>
					                        </span></td>
                      </tr>
                       <tr>
                        <td></td>
                        <td>
                    <div class="pull-right">
                      <button type="submit" class="btn btn-default" ng-click="addContactUs()" style="background-color: #4fc1f9">UPDATE</button>
			</div>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
        </section> -->
      </div>
   </div>
</section>  