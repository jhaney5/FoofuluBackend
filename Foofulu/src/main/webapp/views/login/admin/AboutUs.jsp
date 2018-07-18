<div class="content_area" >	
	<div  ng-controller="AboutUsController" >
		<div class="row">
   	   		<div class="col-md-2  toppad  pull-right col-md-offset-3 ">
   	    	<br><br><br><br>
      		</div>
        	<div class="col-xs-14 col-sm-14 col-md-8 col-lg-8 col-xs-offset-0 col-sm-offset-0 col-md-offset-3 col-lg-offset-3 toppad" >
     			<div class="panel panel-info">
            		<div class="panel-heading">
              		<h3 class="panel-title">About Us Details</h3>
            		</div>
            <div class="panel-body">
              <div class="row">
              <label class="col-sm-2 control-label">Details</label>
          				  <div class="controls col-sm-10">
         				  <wysiwyg textarea-id="question" 
		                    textarea-class="form-control"  
		                    textarea-height="180px" 
		                    textarea-name="textareaQuestion"
		                    textarea-required 
		                    ng-model="content" 
		                    enable-bootstrap-title="true"
		                    textarea-menu="menu"
		                    disabled="disabled">
		                  </wysiwyg>
		                  <!--  <input class="form-control AlphaNumericSpace" placeholder="Add About us*" type="text" name="name" ng-model="aboutus"   ng-readonly="isReadonly" required/> -->
           				  <span class="validmsz" ng-show="addSermonForm.$submitted || addSermonForm.content.$touched">
                                <span ng-show="addSermonForm.content.$error.required"><font class="redFont">This Field is required.</font></span>
                             </span>
                         </div>                  
              </div>
               <div class="row">
                      <div class="pull-right">
                      <button type="submit" class="btn btn-default" ng-click="addAboutUs()">UPDATE</button>
					</div>
            </div>
               </div>
	         </div>
	        </div>
      </div>
   </div>
  </div>
    
    
 
   
    