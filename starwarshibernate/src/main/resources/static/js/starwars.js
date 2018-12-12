$(document).ready(function(){
	
	$("#btn-get").click(function() { 
		getByNom();
	});

}

function getByNom() {
	var nom = $("#nom").val(); //on récupère la variable du formulaire
	
	// on lance la méthode ajax pour faire le lien avec les méthodes back du constructeur
	$.ajax({
		type : "GET",						    // méthode GET
		contentType : "application/json",		// type de données
		url : "/jedi/byname/" + nom,				// url destinatrice
		data : {},		                        // tableau vide pour recevoir la reponse body du controleur
		dataType : 'json',						// on précise le mode de transfert
		cache : false,							// pas de cache sollicité
		timeout : 600000,						// délai d'attente
		success : function(data) {				// si ok

			var json = "<h3>Server Response au format JSON</h3><pre>Apprenant " + nom + " trouvé :<br>" + JSON.stringify(data, null, 4) + "</pre>";
			
			$('#resultat').html(json);
			$("#id").val(data.id);
			$("#prenom").val(data.prenom);
			$("#nom").val(data.nom);
			$("#arme").val(data.arme);
			console.log("SUCCESS : ", data);
		},
		error : function(e) {

			var json = "<h3>Server Response</h3><pre>" + e.responseText	+ "</pre>";
			
			$('#feedbackapprenant').html(json);

			console.log("ERROR : ", e);
		}
	});
}