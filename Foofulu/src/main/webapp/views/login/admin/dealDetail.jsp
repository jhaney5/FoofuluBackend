<section data-ng-controller="DealDetailController" class="main_dashborad_area">


<div class="row">
<aside class="content_area"  ng-init="setServerRole('${role}')">
				<section class="top_header">
					<ol class="breadcrumb">
					  <li><a href="#bussiness">Business</a></li>
					  <li class="active">Deal Details</li>
					</ol>
				</section>
				<section class="inner_content1">
				 <div class="panel panel-info">
            <div class="panel-heading"  style="background-color: #fb8a00">
              <h2 class="panel-title" style="color:white;">Deal Details</h2>
            </div>
            <div class="panel-body">
              <div style="font-size: 18px;" class="row" align="center">
                 <div class="col-md-5 col-lg-5">
	                 <flex-slider  slide="images in dealImage track by images.id" animation="slide">
	 							<li>
					            <img  ng-src="<%=request.getContextPath()%>/{{images.image}}"/>
                  						
	    						</li>
	  						</flex-slider>
                 
                 </div>
                 
                <div style="padding-left: 10px;" align="left" class="col-md-5 col-lg-5">
	             <b>Deal : </b>   {{name1}}<br><br>
	             <b>Added By : </b>  {{data.addedBy.name}}<br><br>
	             <b>Added On : </b>  {{createdDate | date:"MMMM dd , yyyy"}}<br><br>
	             
	              </div>
                 
                  </div>
               
                 <%--  <table class="table table-user-information">
                    <tbody>
                    <tr>
                    	<td><b>Deal Images : </b></td>
		                <td>
							 
	  					</td>
						
                    </tr>
                    
                      <tr>
                        <td><b>Deal Title:</b></td>
                        <td>{{name1}}</td>
                      </tr>
                      <tr>
                        <td><b>Deal Creation Date:</b></td>
                        <td>{{createdDate | date:"MMMM dd , yyyy"}}</td>
                      </tr>
                      
                     </tbody>
                  </table> --%>
                  
              
            </div>
                 
          </div>
                 
	</section>
</aside>
	
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