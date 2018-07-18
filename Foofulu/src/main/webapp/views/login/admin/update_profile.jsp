<div class="content_area" >	
<div  ng-controller="UpdateAccountController">
<section class="top_header">
					<ol class="breadcrumb">
					  <li><a href="#users">Users</a></li>
					  <li><a href="#my_account">Admin Detail</a></li>
					  <li class="active">Edit Detail</li>
					</ol>		
					</section>
      <div class="row">
               <div class="panel panel-info">
            <div class="panel-heading" style="background-color: #4fc1f9">
              <h3 class="panel-title" style="color:white;">Edit Profile</h3>
            </div>
            <div class="panel-body">
              <div class="row">
 <div class="col-md-3 col-lg-3 media-container" align="center">  <img alt="User Pic" id="selectImage" src="<%=request.getContextPath() %>/{{userImage}}" alt="User Image" class="img-circle img-responsive">
              <label class="myLabel">  <input type="file" id="my_file" file-model="myFile" data-ng-model="image" onchange="angular.element(this).scope().fileNameChanged()" media-preview  /> <span>upload image</span> </label></div>                
                <!--<div class="col-xs-10 col-sm-10 hidden-md hidden-lg"> <br>
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
                </div>-->
                <div class=" col-md-9 col-lg-9 "> 
                  <table class="table table-user-information">
                    <tbody>
                     <tr>
                        <td>Name:</td>
                        <td><input class="form-control AlphaNumericSpace" placeholder="Name *" type="text" name="name" ng-model="fullName" ng-value="value.name"  ng-readonly="isReadonly" required/>
											<span class="validmsz" ng-show="addUsersForm.$submitted || addUsersForm.name.$touched">
					                          	<span ng-show="addUsersForm.name.$error.required"><font class="redFont">Name is required.</font></span>
					                        </span></td>
                      </tr>
                      <tr>
                        <td>Email:</td>
                        <td><input class="form-control AlphaNumericSpace" placeholder="Email *" type="text" name="email" ng-model="email" ng-value="value.name"  ng-readonly="isReadonly" required/>
											<span class="validmsz" ng-show="addUsersForm.$submitted || addUsersForm.email.$touched">
					                          	<span ng-show="addUsersForm.email.$error.required"><font class="redFont">Email is required.</font></span>
					                        </span></td>
                      </tr>
                       <tr>
                        <td>Gender</td>
                        <td>
							<select class="form-control numeric" ng-model="gender"  ng-options="option for option in genders">
										<option style="display:none" value="">{{gender}}</option>
						</td>
                      </tr>
                      <tr>
                        <td>Phone Number</td>
                        <td><input class="form-control numeric" placeholder="Mobile*" type="text" name="mobile" ng-model="mobile" ng-value="value.name"  ng-readonly="isReadonly" required/>
											<span class="validmsz" ng-show="addUsersForm.$submitted || addUsersForm.mobile.$touched">
					                          	<span ng-show="addUsersForm.mobile.$error.required"><font class="redFont">mobile number is required.</font></span>
					                        </span>
                             </td>
                           
                      </tr>
                       <tr>
                      <td>
                       <br>
                    <div class="pull-right">
                      <button type="submit" class="btn btn-default" ng-click="update()" style="background-color: #4fc1f9">UPDATE</button>
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
      
   </div>
    </div>