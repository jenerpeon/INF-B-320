  $( document ).ready(function() {
		var activeSite = parseInt(getQueryVariable(1),10);
		var articlesPerPage = parseInt(getQueryVariable(2),10);
	  	var sites = document.getElementsByClassName("articlePages");
	  	var articlesPerPageInput = document.getElementsByName("total");
	  	sites[activeSite - 1].className = "articlePageActive";
	  	articlesPerPageInput[0].value = articlesPerPage;
	  	
  });
  
  function getQueryVariable(fromBehind)
  {
         var query = window.location.href;
         var vars = query.split("/");
         return vars[vars.length - fromBehind];
  }
