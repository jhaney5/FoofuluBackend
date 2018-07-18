<div  class="loading-spiner-holder" data-loading ><div class="loading-spiner"><img src="<%=request.getContextPath()%>/resources/images/ajax_loader.gif"/></div></div>
<header  class="header" ng-show="isAuthorised" style="background-color: #fb8a00">
	<div class="container-fluid" >
		<div class="row">
			<aside class="logo_area col-xs-2" >
				<img src="<%=request.getContextPath()%>/resources/images/logo.png" height="70">
			</aside>
			<aside class="right_header col-sm-4 pull-right" >
				 <span class="pull-right">
				<span>
					<img style="height : 55px; width : 55px" ng-show="f_UserImage==null" src="<%=request.getContextPath()%>/resources/images/user_img.png" class="profile_img">
					<img ng-show="user.image!=null" src="<%=request.getContextPath()%>/{{user.image}}">
				</span>
				<p>
					<strong><a href="#my_account" style="color: #ffffff;">Welcome {{user.name}}</a></strong><br> <!-- <strong>Rent Collection Management</strong> -->
				</p>
				</span>
			</aside>
		</div>
	</div>
</header>