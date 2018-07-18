<section class="main_dashborad_area">
<div class="row">
<aside class="content_area"
	data-ng-controller="BussinessDetailController">
	<section class="top_header">
		<ol class="breadcrumb">
			<li><a href="#dashboard">Home</a></li>
			<li><a href="#bussiness">Bussiness</a></li>
			<li class="active">Bussiness Detail</li>
		</ol>
	</section>
 	<section class="inner_content1">
		<div class="box-body">
		 <!-- <a href="" ng-click="addDeals(place)" class="pull-right add" style="padding-right:10px;"><i class="fa fa-plus-circle" aria-hidden="true"></i>
		 Add Deal</a> -->
			<h3>Bussiness Detail</h3>
				<form class="form-horizontal tenant_form" name="addUsersForm">
				
				<div class="form-group">
					<div class="col-sm-6">
					
						<div ng-if="slideshow3 != null" style="height: 280px;width: 100%;" class="imageslide" ng-switch='slideshow3' ng-animate="'animate'" >
							<div class="slider-content" ng-switch-when="1">
							<img src="{{imagea.image}}"/>
							</div>
						</div>
					    
					    <div ng-if="slideshow2 != null" style="height: 280px;width: 100%;" class="imageslide" ng-switch='slideshow2' ng-animate="'animate'" >
							<div class="slider-content" ng-switch-when="1">
							<img src="{{imagea.image}}" />
							</div>	
							<div class="slider-content" ng-switch-when="2">
							<img ng-src="{{imageb.image}}" />
							</div>
						</div>
											
						<div ng-if="slideshow != null" style="height: 280px; width: 100%" class="imageslide" ng-switch='slideshow' ng-animate="'animate'" >
							<div class="slider-content" ng-switch-when="1">
							<img  src="{{imagea.image}}" />
							</div>	
							<div class="slider-content" ng-switch-when="2">
							<img ng-src="{{imageb.image}}" />
							</div>
							<div class="slider-content" ng-switch-when="3">
								<img  ng-src="{{imagec.image}}" />
							</div>	
						</div>
					
				</div>

					<div class="col-sm-6">
						<div id="us3" style="height: 280px;width: 100%;" ng-readonly="truefalse"></div>
						<input type="hidden" class="form-control" style="width: 120px" id="us3-lat" />
						<input type="hidden" class="form-control" style="width: 120px" id="us3-lon" />
					</div>
				</div>

				<div class="col-sm-8" style="font-size: 18px;">
					<strong>{{name}}</strong>
				</div>
				<div class="col-sm-8" style="font-size: 15px;">
					<strong>{{addr}}</strong>
				</div>
			<br/><br/><br/>
				<div style="font-size: 16px;margin-left: 15px;">
				<strong ng-show="len>0">Deals on this Venue :</strong>
				</div>
				<div class="list" style="margin-top: 20px;">
				    <div class="form-group"  ng-repeat="deal in dealdata">
				       <div class="col-sm-3">
				       <img style="max-height:150px;" ng-src="<%=request.getContextPath()%>/{{deal.images[0].image}}"/> 
				       
				        <!-- <img ng-src="{{deal.images.image}}" /> --> 
				        </div>
				        <div class="col-sm-6" style="font-size: 15px;">
				        	<strong><b>{{deal.name}}</b></strong><br/>
				        	
				        	Created On : {{deal.creationTime | date:"MMMM dd , yyyy"}}
				        	<div >
				        		<button type="button" class="btn btn-warning" style="margin-left:20px;margin-top: 30px;background-color: #fb8a00;" ng-click="viewDealDetail(deal)">View Deal</button>
				        	</div>
				        </div>
				     </div>
				</div>
			</form>
		</div>
		<!-- /.box-body -->
	</section> 
</aside>
</div>
</section>