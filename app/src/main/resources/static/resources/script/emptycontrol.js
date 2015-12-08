function checkForm() {
	var errorMessage = "";

	if (document.getElementById("namefield").value==""){
		errorMessage += "Name fehlt \n";
	}
	if (document.getElementById("pricefield").value==""){
		errorMessage += "Preis fehlt \n";
	}
	if (document.getElementById("imagefield").value==""){
		errorMessage += "Bild fehlt \n";
	}
	if (document.getElementById("descriptionfield").value==""){
		errorMessage += "Beschreibung fehlt \n";
	}

	if (errorMessage.length>0) {
		alert("Festgestellte Probleme: \n\n"+errorMessage);
		return(false);
	}
}