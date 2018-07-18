<section data-ng-controller="AdminDashboardController" class="main_dashborad_area">

<div class="row">
<aside class="content_area"  ng-init="setServerRole('${role}')">
	<section class="top_header">
		<ol class="breadcrumb">
		  <!-- <li><a href="#">Home</a></li> -->
		 
		  <li class="active">Dashboard</li>
		</ol>
	</section>
	
	<section class="inner_content1">
		 
		 <div class="row">
			 <div class="col-lg-11 graphh">
		   <canvas id="bar" class="chart chart-bar"
 				 chart-data="data" chart-labels="labels" style="height: 300px; width: 600px">
 				 </canvas>
			 </div>
</div>
<br>
		<div class="row">
			 <div class="col-lg-11 col-md-offset-1 col-lg-offset-1">
          <a href="" class="btn btn-sq-lg btn-primary">
                {{usersList.registeredUsers}}  <br>Registered Users
            </a>
            <a href="" class="btn btn-sq-lg btn-info">
                {{usersList.today}}  <br> Logged In (Today)
            </a>
            <a href="" class="btn btn-sq-lg btn-danger">
              {{usersList.sevendays}}  <br>Logged In (Last 7 days)
            </a>
             <a href="" class="btn btn-sq-lg btn-warning">
              {{usersList.thirtydays}} <br> Logged In (Last 30 days)
            </a>
        
        </div>
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