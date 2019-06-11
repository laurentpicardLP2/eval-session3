var categories; // recense les différentes catégories d'armes

$(document).ready(function(){
	
	// appelle la méthode pour charger la base données dans la datatable
	loadDatatable();
	//Remplissage check box
	loadCheckboxes();
	
	
	// déclaration d'une variable table;
	var table = $('#jediTable').DataTable();
	
	//Double click sur une ligne
	$('#jediTable tbody').on( 'dblclick', 'tr', function () {
	    let dataRow = table.row( this ).data();
	    console.log(dataRow);
	    $("#id").val(dataRow.id);
		$("#prenom").val(dataRow.prenom);
		$("#nom").val(dataRow.nom);
		
		//Vidage de la liste déroulante
		$("#listeDeroulanteArme").children().remove(); 
		
		//Remplissage liste déroulante
		var listeDeroulante = document.getElementById("listeDeroulanteArme");
		var armes = dataRow.armes;			
		var option = "";
		
		//alert("listeCategories = " + listeCategories);
		
		//Réinitialisation des check box
		for(var i = 0; i < categories.length ; i++){
			document.getElementById("checkboxes-" + i).checked = false;
		}

		console.log(categories);
		console.log(armes);
        for (var i = 0; i < armes.length; i++) {
            option = document.createElement("option");
            option.textContent = armes[i].model;
            listeDeroulante.appendChild(option);
            
            for(var j = 0; j < categories.length; j++) {
            	if (armes[i].model == categories[j].description){
                    document.getElementById("checkboxes-" + j).checked = true;
                }
            }
        }
	} );
	
	
	//Click sur Ajouter
	$("#btn-post").click(function() { 
		ajouterJedi($("#btn-post"), "GET", table);
	});
	
	//Click sur Modifier
	$("#btn-put").click(function() { 
		ajouterJedi($("#btn-post"), "PUT", table);
	});
	
	//click sur Supprimer
	$("#btn-delete").click(function() {
		supprimerJedi(table);
		resetForm();
	});
	
	//click sur Reset
	$("#btn-reset").click(function() {
		resetForm(); // méthode qui met les valeurs des zones de textes du formulaire à blanc
	});

});

/**
 * Charge les données dans la DataTable (JQuery)
 */
function loadDatatable() {
	$('#jediTable').DataTable({
		paging: false,
		"searching": false,
		"info": false,
		"columnDefs": [
	            {
	                "targets": [ 0 ],
	                "sortable" : true
	            },
	            {
	                "targets": [ 1 ],
	                "sortable" : true,
	                "visible": true
	            },
	            {
	                "targets": [ 2 ],
	                "sortable" : true,
	                "visible": true
	            }
	        ],
		"ajax" : {
			url : '/jedi/jedis',
			dataSrc : ''
		},
		"columns" : [ 
			{"data" : "id"},
			{"data" : "prenom"},
			{"data" : "nom"}, ]
	});
	
}



function loadCheckboxes() {
	// on lance la méthode ajax pour faire le lien avec les méthodes back du constructeur
	$.ajax({
		type : "GET",						    // méthode GET
		contentType : "application/json",		// type de données
		url : "/jedi/categories",				// url destinatrice
		data : {},		                        // tableau vide pour recevoir la reponse body du controleur
		dataType : 'json',						// on précise le mode de transfert
		cache : false,							// pas de cache sollicité
		timeout : 600000,						// délai d'attente
		success : function(data) {				// si ok

			categories = data;
			//Vidage des checkboxes
			$("#checkboxes").children().remove(); 
			
			//Remplissage checkboxes
			var divCheckboxes = document.getElementById("checkboxes");
			var div = "";
			var label = "";
			var input = "";
		
			for (var i = 0; i < categories.length; i++) {
				div = document.createElement("div");
				div.className = "form-check";
				divCheckboxes.appendChild(div);
				label = document.createElement("label");
				label.className = "form-check-label";
				label.setAttribute("for", "checkboxes-" + i);
				input = document.createElement("input");
				input.className = "form-check-input";
				input.id = "checkboxes-" + i;
				input.setAttribute("type", "checkbox");
				div.appendChild(input);
				div.appendChild(label);
				label.textContent = categories[i].description + " (puissance : " + categories[i].puissance + ")";
				console.log(document.getElementById("checkboxes-" + i));
			}
		},
		error : function(e) {
			console.log("ERROR : ", e);
		}
	});
}


function ajouterJedi(button, httpVerb, table) {
	
	// on récupère les valeurs saisies	
	var id = $("#id").val();
	var prenom = $("#prenom").val();
	var nom = $("#nom").val();
	
	var paramCategories = "";
	
	
	// on initialise l'url du back
	var url = "/jedi/addJedi";

	for(var i = 0; i < categories.length ; i++){
		paramCategories += "&" + categories[i].description + "=" + $('#checkboxes-' + i).is(':checked');
	}

	// si c'est une modification, on passe l'identifiant
	if(httpVerb == "PUT") {
		url += "/" + "prenom="+prenom+"&nom="+nom+"&id="+id+paramCategories;
	}
	
	if(httpVerb == "GET")
		url += "/" + "prenom="+prenom+"&nom="+nom+paramCategories;
	
	// on désactive le bouton en cours 
	button.prop("disabled", true);
	
	console.log(url);

	// on lance la méthode ajax pour faire le lien avec les méthodes back du constructeur
	$.ajax({
		type : httpVerb,						// méthode GET ou PUT
		contentType : "application/json",		
		url : url,								
		data : {},		
		dataType : 'json',						
		cache : false,							
		timeout : 600000,						
		success : function(data) {				

			console.log("SUCCESS : ", data);
			button.prop("disabled", false);

			resetForm()
			
		},
		error : function(e) {			

			console.log("ERROR : ", e);
			button.prop("disabled", false);

		}
	});
	
	// on recharge les données via la méthode reload()
	setTimeout( function () {
        table.ajax.reload();
	}, 300 ); 
}


function supprimerJedi(table) {
    var id = $("#id").val(); //on récupère la variable du formulaire 

    // on lance la méthode ajax pour faire le lien avec les méthodes back du constructeur
    $.ajax({
        type : "DELETE",                            // méthode GET
        contentType : "application/json",        // type de données
        url : "/jedi/deluser/" + id,                // url destinatrice
        data : {},                                // tableau vide pour recevoir la reponse body du controleur
        dataType : 'json',                        // on précise le mode de transfert
        cache : false,                            // pas de cache sollicité
        timeout : 600000,                        // délai d'attente
        success : function(data) {                // si ok

            console.log("SUCCESS : ", data);
            resetForm()
        },
        error : function(e) {
            console.log("ERROR : ", e);
        }
    });
    
    // on recharge les données via la méthode reload()
	setTimeout( function () {
	    table.ajax.reload();
	}, 300 ); 
}


function resetForm() {
	$('#jedi-form')[0].reset();
	$("#listeDeroulanteArme").children().remove(); 
	for(var i = 0; i < categories.length ; i++){
		document.getElementById("checkboxes-" + i).checked = false;
	}
}