$( document ).ready(function() {
		var activeSite = getQueryVariable();
		var catalog = isCatalog(activeSite);
		var navElements = document.getElementsByClassName("employeenavigationelement");
		if (catalog) {
			navElements[0].style.fontWeight = "bold";
		}
  });
  
  function getQueryVariable()
  {
         var query = window.location.href;
         var vars = query.split("/");
         return vars;
  }
  
  function isCatalog(activeSite) {
	  var catalog = false
	  for (i=0; i < (activeSite.length); i++) {
		  if (activeSite[i].includes('changecatalog')){
			  catalog = true;
		  }
	  }
	  return catalog;
  }