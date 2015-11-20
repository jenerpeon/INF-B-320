  $( document ).ready(function() {
		var activeSite = parseInt(getQueryVariable(),10);
	  	var sites = document.getElementsByClassName("articlePages");
	  	sites[activeSite - 1].className = "articlePageActive";
  });
  
  function getQueryVariable()
  {
         var query = window.location.href;
         var vars = query.split("/");
         return vars[vars.length - 1];
  }
