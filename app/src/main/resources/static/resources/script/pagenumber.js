  $( document ).ready(function() {
		var activeSite = parseInt(getQueryVariable(1),10);
		var articlesPerPage = parseInt(getQueryVariable(2),10);
	  	var sites = document.getElementsByClassName("articlePages");
	  	var articlesPerPageInput = document.getElementsByName("total");
	  	var activeSite2 = activeSite + sites.length/2;
	  	sites[activeSite - 1].className = "articlePageActive";
	  	sites[activeSite2 -2].className = "articlePageActive";
	  	articlesPerPageInput[0].value = articlesPerPage;
	  	articlesPerPageInput[1].value = articlesPerPage;
  });
  
  function getQueryVariable(fromBehind)
  {
         var query = window.location.href;
         var vars = query.split("/");
         return vars[vars.length - fromBehind];
  }
