<section data-ng-controller="BussinessController" class="main_dashborad_area">

<div class="row">
<aside class="content_area"  ng-init="setServerRole('${role}')">
				<section class="top_header">
					<ol class="breadcrumb">
					  
					  <li class="active">Bussiness</li>
					</ol>
				</section>
				<div ng-hide="loginAlertMessage">
					<center><b>{{employeeMsg}}</b></center>
				</div>
				<section class="inner_content1">
					 <div class="box-body table-responsive">
						<h3>Bussiness
						<form class="form-inline search_form pull-right" style="padding-right: 20px;">
													<div>
														<input type="text" ng-model="search" class="form-control"
															placeholder="Search">
													</div>
												</form></h3>
						
                        <table id="example1" class="table table-bordered">
                              <thead>
							  			<tr>
							  			<th>Image</th>
										<th ng-click="sort('name')">Bussiness Name<span
										class="glyphicon sort-icon" ng-show="sortKey=='name'"
										ng-class="{'glyphicon-chevron-up':reverse,'glyphicon-chevron-down':!reverse}"></span>
										</th>
                                        <th>Address</th>
										<th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr dir-paginate="bussiness in bussinessList|orderBy:sortKey:reverse|filter:search|itemsPerPage:10">
                                               <td class=""><img ng-if="bussiness.images[0].image != null"
																ng-src="{{bussiness.images[0].image}}"
																 style="width: 120px; height: 60px;"
																alt="User Image"> <img
																ng-if="bussiness.images[0].image == null"
																src="<%=request.getContextPath()%>/resources/images/user_img.png"
																style="width: 60px; height: 60px;"
																alt="User Image"></td>  
                                            <td>{{bussiness.name}}</td>
                                            <td>{{bussiness.address}}</td>
                                    		<td class="action_icons">
											<a href="" ng-click="viewBussinessDetails(bussiness)"><i  class="fa fa-eye" aria-hidden="true"></i></a>
										 </td>
                                           </tr>
                                    </tfoot>
                                </table>
								<dir-pagination-controls max-size="10" direction-links="true"
									boundary-links="true"> </dir-pagination-controls>
                            </div>


                            <!-- /.box-body -->
	</section>
		
</aside>
</div>
</section>
	
	
</aside>
	
	
</div>
</section>
<script src="<%=request.getContextPath()%>/resources/js/normaljs/js/tooltip.js"></script>
<script>
	$(function () {
	    $('[data-toggle="tooltip"]').tooltip()
	})
</script>
<script>
	$( ".user_profile" ).click(function() {
 	 	$( this ).toggleClass( "highlight" );
	});
</script>
