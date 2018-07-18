<section data-ng-controller="SharingController" class="main_dashborad_area">
 	<h3>
 		<a style="font-size: 46px;padding: 5px 35px;" class="btn" onClick="javascript:try_to_open_app();"
			ng-href="chooseaside://192.168.2.85:8080/deal/{{dealId}}">Foofulu</a>
	</h3>
	</center>	
</section>
	<script language="javascript">
		    function open_appstore() {
		    	window.location='https://i0fb.app.link/chooseaside';
		    }

		    function try_to_open_app() {
		    	 setTimeout(function(){ open_appstore() }, 7000);
		    }
	</script>
<style>
	body,html{
		height: 100%;
		width:100%;
		display: table;
	}
</style>	