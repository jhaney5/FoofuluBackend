<section data-ng-controller="UsersController" class="main_dashborad_area">

<div class="row">
<aside class="content_area"  ng-init="setServerRole('${role}')">
				<section class="top_header">
					<ol class="breadcrumb">
					  
					  <li class="active">Users</li>
					</ol>
				</section>
				<div ng-hide="loginAlertMessage">
					<center><b>{{employeeMsg}}</b></center>
				</div>
				<section class="inner_content1" >
					 <div class="box-body table-responsive" >
						<h3>Users Listing
						<form class="form-inline search_form pull-right" style="padding-right: 20px;">
							<div>
								<input type="text" ng-model="search" class="form-control"
											placeholder="Search">
							</div>
						</form></h3>
						
                        <table id="example1" class="table table-bordered">
                              <thead>
						  			<tr>
						  			<th>Profile Image</th>
									<th ng-click="sort('name')">Name<span
									class="glyphicon sort-icon" ng-show="sortKey=='name'"
									ng-class="{'glyphicon-chevron-up':reverse,'glyphicon-chevron-down':!reverse}"></span>
									</th>
									<th>Email ID</th>
									<th>Action</th>
                                       </tr>
                                </thead>
                                <tbody>
                                     <tr dir-paginate="user in usersList|orderBy:sortKey:reverse|filter:search|itemsPerPage:10">
                                         <td class="">
                                         	<img ng-if="user.image != null"
												ng-src="<%=request.getContextPath()%>/{{user.image}}"
												class="img-circle" style="width: 45px; height: 45px;"
												alt="User Image"> 
											<img ng-if="user.image == null"
												src="<%=request.getContextPath()%>/resources/images/user_img.png"
												class="img-circle" style="width: 45px; height: 45px;"
												alt="User Image"></td> 
                                         <td>{{user.name}}</td>
                                         <td>{{user.email}}</td>
                                         <td class="action_icons">
											 <a href="" ng-click="viewUserDetails(user)"><i  class="fa fa-eye" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="View/Edit User"></i></a>
											 <a ng-show="user.status" href="" ng-click="disableUser(user)"><i style="color:green;" class="fa fa-dot-circle-o" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="Disable User"></i></a>
				                             <a ng-show="!user.status" href="" ng-click="disableUser(user)"><i style="color:red;" class="fa fa-dot-circle-o" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="Enable User"></i></a>
			                             </td>
                                     </tr>
                             </tbody>
                      </table>
								<dir-pagination-controls max-size="10" direction-links="true"
									boundary-links="true"> </dir-pagination-controls>
                            </div>
	</section>
</aside>
</div>
</section>
<script src="<%=request.getContextPath()%>/resources/js/normaljs/js/tooltip.js"></script>
<!-- <script>
	$(function () {
	    $('[data-toggle="tooltip"]').tooltip()
	})
</script> -->
<script>
	$( ".user_profile" ).click(function() {
 	 	$( this ).toggleClass( "highlight" );
	});
</script>