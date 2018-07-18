<head>
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>${title} </title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="apple-touch-icon" href="<%=request.getContextPath()%>/resources/images/apple-touch-icon.png">
        <!-- Place favicon.ico in the root directory -->
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/vendor.css">
        <!-- Theme initialization -->
        <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/jquery.alert.css">
		<link rel="stylesheet" id="theme-style" href="<%=request.getContextPath()%>/resources/css/app-red.css">
		<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/jquery-te-1.4.0.css">
		<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/jquery-ui.css">
		<link rel="icon" href="<%=request.getContextPath() %>/resources/images/favicon.ico">
		
			<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/example.css">
			
			<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/sol.css">
			<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/simplePagination.css">
			  <!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.3.0/codemirror.min.css"> -->
					
		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/jquery.mCustomScrollbar.css">
		
		<script src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
		  <script src="<%=request.getContextPath()%>/resources/js/vendor.js"></script> 
		 <script src="<%=request.getContextPath() %>/resources/js/jquery-ui.js"></script>
		 
		<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/modernizr.2.5.3.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery.mCustomScrollbar.concat.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery-te-1.4.0.min.js"></script>
		<script src="<%=request.getContextPath() %>/resources/js/jquery.alert.js"></script>
        
	
        <script src="<%=request.getContextPath()%>/resources/js/app.js"></script>
      
		 
		  <script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/simplePagination.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/sol.js"></script>
		<script type="text/javascript">
	 $(document).ready(function () {
		 var pathname = window.location.pathname; // Returns path only
		 if((pathname == '<%=request.getContextPath()%>/usermanagement/') ){
			 $('#dashboardLi').removeClass('active');
				$('#usermanagementLi').addClass('active');
				 $('#bookLi').removeClass('active');
				 $('#categoryLi').removeClass('active');
				 $('#requestLi').removeClass('active');
				 $('#orderLi').removeClass('active');
				 $('#notificationLi').removeClass('active');
				 $('#authorLi').removeClass('active');
				 $('#publisherLi').removeClass('active');
				 $('#subjectLi').removeClass('active');
				 $('#prefLi').removeClass('active');
				 $('#faqLi').removeClass('active');
			}
		 else if(pathname == '<%=request.getContextPath()%>/user/dashboard/'){
				$('#dashboardLi').addClass('active');
				$('#usermanagementLi').removeClass('active');
				 $('#bookLi').removeClass('active');
				 $('#categoryLi').removeClass('active');
				 $('#requestLi').removeClass('active');
				 $('#orderLi').removeClass('active');
				 $('#notificationLi').removeClass('active');
				 $('#authorLi').removeClass('active');
				 $('#publisherLi').removeClass('active');
				 $('#subjectLi').removeClass('active');
				 $('#prefLi').removeClass('active');
				 $('#faqLi').removeClass('active');
			}
			
		 else if((pathname == '<%=request.getContextPath()%>/usermanagement/adduser/')){
			 $('#dashboardLi').removeClass('active');
			 $('#usermanagementLi').addClass('active');
			 $('#bookLi').removeClass('active');
			 $('#categoryLi').removeClass('active');
			 $('#requestLi').removeClass('active');
			 $('#orderLi').removeClass('active');
			 $('#notificationLi').removeClass('active');
			 $('#authorLi').removeClass('active');
			 $('#publisherLi').removeClass('active');
			 $('#subjectLi').removeClass('active');
			 $('#prefLi').removeClass('active');
			 $('#faqLi').removeClass('active');
			 }
		 else if((pathname == '<%=request.getContextPath()%>/bookmanagement/')){
			 $('#dashboardLi').removeClass('active');
			 $('#usermanagementLi').removeClass('active');
			 $('#bookLi').addClass('active');
			 $('#categoryLi').removeClass('active');
			 $('#requestLi').removeClass('active');
			 $('#orderLi').removeClass('active');
			 $('#notificationLi').removeClass('active');
			 $('#authorLi').removeClass('active');
			 $('#publisherLi').removeClass('active');
			 $('#subjectLi').removeClass('active');
			 $('#prefLi').removeClass('active');
			 $('#faqLi').removeClass('active');
			 }
		 else if((pathname == '<%=request.getContextPath()%>/bookmanagement/addbook/')){
			 $('#dashboardLi').removeClass('active');
			 $('#usermanagementLi').removeClass('active');
			 $('#bookLi').addClass('active');
			 $('#categoryLi').removeClass('active');
			 $('#requestLi').removeClass('active');
			 $('#orderLi').removeClass('active');
			 $('#notificationLi').removeClass('active');
			 $('#authorLi').removeClass('active');
			 $('#publisherLi').removeClass('active');
			 $('#subjectLi').removeClass('active');
			 $('#prefLi').removeClass('active');
			 $('#faqLi').removeClass('active');
			 }
		 else if((pathname == '<%=request.getContextPath()%>/viewBook/')||(pathname == '<%=request.getContextPath()%>/editBook/')){
			 $('#dashboardLi').removeClass('active');
			 $('#usermanagementLi').removeClass('active');
			 $('#bookLi').addClass('active');
			 $('#categoryLi').removeClass('active');
			 $('#requestLi').removeClass('active');
			 $('#orderLi').removeClass('active');
			 $('#notificationLi').removeClass('active');
			 $('#authorLi').removeClass('active');
			 $('#publisherLi').removeClass('active');
			 $('#subjectLi').removeClass('active');
			 $('#prefLi').removeClass('active');
			 $('#faqLi').removeClass('active');
			 }
		 else if((pathname == '<%=request.getContextPath()%>/categorymanagement/')){
			 $('#dashboardLi').removeClass('active');
			 $('#usermanagementLi').removeClass('active');
			 $('#bookLi').removeClass('active');
			 $('#categoryLi').addClass('active');
			 $('#requestLi').removeClass('active');
			 $('#orderLi').removeClass('active');
			 $('#notificationLi').removeClass('active');
			 $('#authorLi').removeClass('active');
			 $('#publisherLi').removeClass('active');
			 $('#subjectLi').removeClass('active');
			 $('#prefLi').removeClass('active');
			 $('#faqLi').removeClass('active');
			 }
		 else if((pathname == '<%=request.getContextPath()%>/book/chapters/') || (pathname == '<%=request.getContextPath()%>/book/notes/')){
			 $('#dashboardLi').removeClass('active');
			 $('#usermanagementLi').removeClass('active');
			 $('#bookLi').addClass('active');
			 $('#categoryLi').removeClass('active');
			 $('#requestLi').removeClass('active');
			 $('#orderLi').removeClass('active');
			 $('#notificationLi').removeClass('active');
			 $('#authorLi').removeClass('active');
			 $('#publisherLi').removeClass('active');
			 $('#subjectLi').removeClass('active');
			 $('#prefLi').removeClass('active');
			 $('#faqLi').removeClass('active');
			 }
		 else if((pathname == '<%=request.getContextPath()%>/add/bookchapter/')){
				 $('#dashboardLi').removeClass('active');
				 $('#usermanagementLi').removeClass('active');
				 $('#bookLi').addClass('active');
				 $('#categoryLi').removeClass('active');
				 $('#requestLi').removeClass('active');
				 $('#orderLi').removeClass('active');
				 $('#notificationLi').removeClass('active');
				 $('#authorLi').removeClass('active');
				 $('#publisherLi').removeClass('active');
				 $('#subjectLi').removeClass('active');
				 $('#prefLi').removeClass('active');
				 $('#faqLi').removeClass('active');
			 }
		 else if((pathname == '<%=request.getContextPath()%>/book/chapter/pages/')){
			 $('#dashboardLi').removeClass('active');
			 $('#usermanagementLi').removeClass('active');
			 $('#bookLi').addClass('active');
			 $('#categoryLi').removeClass('active');
			 $('#requestLi').removeClass('active');
			 $('#orderLi').removeClass('active');
			 $('#notificationLi').removeClass('active');
			 $('#authorLi').removeClass('active');
			 $('#publisherLi').removeClass('active');
			 $('#subjectLi').removeClass('active');
			 $('#prefLi').removeClass('active');
			 $('#faqLi').removeClass('active');
		 }
		 else if((pathname == '<%=request.getContextPath()%>/add/chapterpage/')){
			 $('#dashboardLi').removeClass('active');
			 $('#usermanagementLi').removeClass('active');
			 $('#bookLi').addClass('active');
			 $('#categoryLi').removeClass('active');
			 $('#requestLi').removeClass('active');
			 $('#orderLi').removeClass('active');
			 $('#notificationLi').removeClass('active');
			 $('#authorLi').removeClass('active');
			 $('#publisherLi').removeClass('active');
			 $('#subjectLi').removeClass('active');
			 $('#prefLi').removeClass('active');
			 $('#faqLi').removeClass('active');
		 }
		 else if((pathname == '<%=request.getContextPath()%>/book/chapter/viewpage/')){
			 $('#dashboardLi').removeClass('active');
			 $('#usermanagementLi').removeClass('active');
			 $('#bookLi').addClass('active');
			 $('#categoryLi').removeClass('active');
			 $('#requestLi').removeClass('active');
			 $('#orderLi').removeClass('active');
			 $('#notificationLi').removeClass('active');
			 $('#authorLi').removeClass('active');
			 $('#publisherLi').removeClass('active');
			 $('#subjectLi').removeClass('active');
			 $('#prefLi').removeClass('active');
			 $('#faqLi').removeClass('active');
		 }
		 else if((pathname == '<%=request.getContextPath()%>/requestsManagement/')){
			 $('#dashboardLi').removeClass('active');
			 $('#usermanagementLi').removeClass('active');
			 $('#bookLi').removeClass('active');
			 $('#requestLi').addClass('active');
			 $('#categoryLi').removeClass('active');
			 $('#notificationLi').removeClass('active');
			 $('#authorLi').removeClass('active');
			 $('#publisherLi').removeClass('active');
			 $('#subjectLi').removeClass('active');
			 $('#prefLi').removeClass('active');
			 $('#faqLi').removeClass('active');
		 }
		 else if((pathname == '<%=request.getContextPath()%>/ordermanagement/')){
			 $('#dashboardLi').removeClass('active');
			 $('#usermanagementLi').removeClass('active');
			 $('#bookLi').removeClass('active');
			 $('#requestLi').removeClass('active');
			 $('#orderLi').addClass('active');
			 $('#categoryLi').removeClass('active');
			 $('#notificationLi').removeClass('active');
			 $('#authorLi').removeClass('active');
			 $('#publisherLi').removeClass('active');
			 $('#subjectLi').removeClass('active');
			 $('#prefLi').removeClass('active');
			 $('#faqLi').removeClass('active');
		 }
		 else if((pathname == '<%=request.getContextPath()%>/view/orderDetail/')){
			 $('#dashboardLi').removeClass('active');
			 $('#usermanagementLi').removeClass('active');
			 $('#bookLi').removeClass('active');
			 $('#requestLi').removeClass('active');
			 $('#orderLi').addClass('active');
			 $('#categoryLi').removeClass('active');
			 $('#notificationLi').removeClass('active');
			 $('#authorLi').removeClass('active');
			 $('#publisherLi').removeClass('active');
			 $('#subjectLi').removeClass('active');
			 $('#prefLi').removeClass('active');
			 $('#faqLi').removeClass('active');
		 }
		 else if((pathname == '<%=request.getContextPath()%>/notification/management/')||(pathname == '<%=request.getContextPath()%>/send/notification/')){
			 $('#dashboardLi').removeClass('active');
			 $('#usermanagementLi').removeClass('active');
			 $('#bookLi').removeClass('active');
			 $('#requestLi').removeClass('active');
			 $('#orderLi').removeClass('active');
			 $('#categoryLi').removeClass('active');
			 $('#notificationLi').addClass('active');
			 $('#authorLi').removeClass('active');
			 $('#publisherLi').removeClass('active');
			 $('#subjectLi').removeClass('active');
			 $('#prefLi').removeClass('active');
			 $('#faqLi').removeClass('active');
		 }
		 else if((pathname == '<%=request.getContextPath()%>/author/management/')||(pathname == '<%=request.getContextPath()%>/add/author/')||(pathname == '<%=request.getContextPath()%>/view/author/')){
			 $('#dashboardLi').removeClass('active');
			 $('#usermanagementLi').removeClass('active');
			 $('#bookLi').removeClass('active');
			 $('#requestLi').removeClass('active');
			 $('#orderLi').removeClass('active');
			 $('#categoryLi').removeClass('active');
			 $('#notificationLi').removeClass('active');
			 $('#authorLi').addClass('active');
			 $('#publisherLi').removeClass('active');
			 $('#subjectLi').removeClass('active');
			 $('#prefLi').removeClass('active');
			 $('#faqLi').removeClass('active');
		 }
		 else if((pathname == '<%=request.getContextPath()%>/publisher/management/')||(pathname == '<%=request.getContextPath()%>/add/publisher/')||(pathname == '<%=request.getContextPath()%>/view/publisher/')){
			 $('#dashboardLi').removeClass('active');
			 $('#usermanagementLi').removeClass('active');
			 $('#bookLi').removeClass('active');
			 $('#requestLi').removeClass('active');
			 $('#orderLi').removeClass('active');
			 $('#categoryLi').removeClass('active');
			 $('#notificationLi').removeClass('active');
			 $('#authorLi').removeClass('active');
			 $('#publisherLi').addClass('active');
			 $('#subjectLi').removeClass('active');
			 $('#prefLi').removeClass('active');
			 $('#faqLi').removeClass('active');
		 }

		 else if((pathname == '<%=request.getContextPath()%>/subject/management/')||(pathname == '<%=request.getContextPath()%>/add/subject/')||(pathname == '<%=request.getContextPath()%>/view/subject/')){
			 $('#dashboardLi').removeClass('active');
			 $('#usermanagementLi').removeClass('active');
			 $('#bookLi').removeClass('active');
			 $('#requestLi').removeClass('active');
			 $('#orderLi').removeClass('active');
			 $('#categoryLi').removeClass('active');
			 $('#notificationLi').removeClass('active');
			 $('#authorLi').removeClass('active');
			 $('#publisherLi').removeClass('active');
			 $('#subjectLi').addClass('active');
			 $('#prefLi').removeClass('active');
			 $('#faqLi').removeClass('active');
		 }	
		 else if((pathname == '<%=request.getContextPath()%>/view/preferences/')){
			 $('#dashboardLi').removeClass('active');
			 $('#usermanagementLi').removeClass('active');
			 $('#bookLi').removeClass('active');
			 $('#requestLi').removeClass('active');
			 $('#orderLi').removeClass('active');
			 $('#categoryLi').removeClass('active');
			 $('#notificationLi').removeClass('active');
			 $('#authorLi').removeClass('active');
			 $('#publisherLi').removeClass('active');
			 $('#subjectLi').removeClass('active');
			 $('#prefLi').addClass('active');
			 $('#faqLi').removeClass('active');
		 }

		 else if((pathname == '<%=request.getContextPath()%>/faq/management/')||(pathname == '<%=request.getContextPath()%>/add/faq/')){
			 $('#dashboardLi').removeClass('active');
			 $('#usermanagementLi').removeClass('active');
			 $('#bookLi').removeClass('active');
			 $('#requestLi').removeClass('active');
			 $('#orderLi').removeClass('active');
			 $('#categoryLi').removeClass('active');
			 $('#notificationLi').removeClass('active');
			 $('#authorLi').removeClass('active');
			 $('#publisherLi').removeClass('active');
			 $('#subjectLi').removeClass('active');
			 $('#prefLi').removeClass('active');
			 $('#faqLi').addClass('active');
		 }
		 	 
	 });
     function openLoginEditModal(){
			$('#loginupdateUserProfile').modal('show');
		}
     $("input.numeric").keydown(function (e) {
	        // Allow: backspace, delete, tab, escape, enter and .
	        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
	             // Allow: Ctrl+A, Command+A
	            (e.keyCode === 65 && (e.ctrlKey === true || e.metaKey === true)) || 
	             // Allow: home, end, left, right, down, up
	            (e.keyCode >= 35 && e.keyCode <= 40)) {
	                 // let it happen, don't do anything
	                 return;
	        }
	        // Ensure that it is a number and stop the keypress
	        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
	            e.preventDefault();
	        }
	    });
	    $(document).on("keydown", ".numeric", function(e) {
	        // Allow: backspace, delete, tab, escape, enter and .
	        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
	             // Allow: Ctrl+A, Command+A
	            (e.keyCode === 65 && (e.ctrlKey === true || e.metaKey === true)) || 
	             // Allow: home, end, left, right, down, up
	            (e.keyCode >= 35 && e.keyCode <= 40)) {
	                 // let it happen, don't do anything
	                 return;
	        }
	        // Ensure that it is a number and stop the keypress
	        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
	            e.preventDefault();
	        }
	    });
	    $(document).on("keyup", ".urduText", function(event) {
	    	var regex = new RegExp("^[\u0600-\u06FF\u0750-\u077F\uFB50-\uFDFF\uFE70-\uFEFF ]+$");
	    	var key = $(this).val()+"";
	        if($(this).val().length == 1){
	        	if (regex.test(key) == false && event.keyCode != 8 && event.keyCode != 9 && event.keyCode != 46 && event.keyCode != 35 && event.keyCode != 36 && event.keyCode != 37 && event.keyCode != 39) {
		        	var prev = $(this).data('val')+"";
		        	if(prev != 'undefined' && prev.length > 0){
		        		$(this).val(prev);
		        	}else{
		        		$(this).val("");
	        		}
		        	return false;
	            }else{
	            	//var prev = $(this).data('val')+"";
	        		//if(prev != 'undefined' && prev.length > 0 && $(this).val().length > 0){
	            	//	$(this).data('val', prev+$(this).val());
	        		//}else if(prev != 'undefined' && prev.length > 0 && $(this).val().length == 0){
	        		//	$(this).data('val', prev);
	        		//}else if((prev != 'undefined' || prev.length == 0) && $(this).val().length > 0){
	        			$(this).data('val', $(this).val());
	        		//}else{
	        		//	$(this).data('val', "");
	        		//}
	            }
	        }else{
	        	var b = true;
	        	for(var i = 0; i < key.length; i++){
	        		if(regex.test(key[i]) == false){
	        			var prev = $(this).data('val')+"";
			        	if(prev != 'undefined' && prev.length > 0){
			        		$(this).val(prev);
			        	}else{
			        		$(this).val("");
		        		}
	        			b = false;
	        			break;
	        		}
	        	}
	        	if(b){
	        		//var prev = $(this).data('val')+"";
	        		//if(prev != 'undefined' && prev.length > 0 && $(this).val().length > 0){
	            	//	$(this).data('val', prev+$(this).val());
	        		//}else if(prev != 'undefined' && prev.length > 0 && $(this).val().length == 0){
	        		//	$(this).data('val', prev);
	        		//}else if((prev != 'undefined' || prev.length == 0) && $(this).val().length > 0){
	        			$(this).data('val', $(this).val());
	        		//}else{
	        		//	$(this).data('val', "");
	        		//}
	        	}
	        }
	    });
	  
	    /*$(document).on("focusin", ".urduText", function(event) {
	    	$(this).data('val', $(this).val());
	    });

	    $(document).on("change", ".urduText", function(event) {
	    	alert("Called");
	    	var prev = $(this).data('val')+"";
	        if(prev.length > 0){
	        	var current = $(this).val()+"";
	        	var regex = new RegExp("^[\u0600-\u06FF\u0750-\u077F\uFB50-\uFDFF\uFE70-\uFEFF ]+$");
	        	for(var i = 0; i < current.length; i++){
	        		if(regex.test(current[i]) == false){
	        			$(this).val(prev);
	        			break;
	        		}
	        	}
	        }
	    });*/
	    
	    /*$(document).on("propertychange", ".urduText", function(event) {
	        var urduRegex = /[\u0600-\u06FF]|[\u0750-\u077F]|[\uFB50-\uFDFF]|[\uFE70-\uFEFF]/;
	        var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	        var CharToTest = String.fromCharCode(key);
	        if (urduRegex.test(CharToTest) == false && event.keyCode != 8 && event.keyCode != 9 && event.keyCode != 46 && event.keyCode != 35 && event.keyCode != 36 && event.keyCode != 37 && event.keyCode != 39) {
                event.preventDefault();
                return false;
            }
	    }); */
</script>
    </head>