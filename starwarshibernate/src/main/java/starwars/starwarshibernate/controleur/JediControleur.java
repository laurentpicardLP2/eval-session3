package starwars.starwarshibernate.controleur;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import starwars.starwarshibernate.model.Arme;
import starwars.starwarshibernate.model.Categorie;
import starwars.starwarshibernate.model.Jedi;
import starwars.starwarshibernate.repository.ArmeRepository;
import starwars.starwarshibernate.repository.CategorieRepository;
import starwars.starwarshibernate.repository.JediRepository;

@RestController
@RequestMapping("/jedi")
public class JediControleur {

	@Autowired
	private JediRepository jediRepo;
	
	@Autowired
	private ArmeRepository armeRepo;
	
	@Autowired
	private CategorieRepository categorieRepo;
	
	/**
	 * Retourner tous les jedis
	 */
	@GetMapping("/jedis")
	public ResponseEntity<?> getAllJedis(){
		List<Jedi> listeJedis = null;
		try {
			listeJedis = jediRepo.findAll(); // Pour faire un "SELECT * FROM jedi;"
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(listeJedis);
	}
	
	/**
	 * Retourner toutes les categories d'armes
	 */
	@GetMapping("/categories")
	public ResponseEntity<?> getAllArmes(){
		List<Categorie> listeCategories = null;
		try {
			listeCategories = categorieRepo.findAll(); // Pour faire un "SELECT * FROM categorie;"
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(listeCategories);
	}
	
	/**
	 * ajouter jedi
	 */
	@GetMapping("/addJedi/{newData}")
    public ResponseEntity<?> addJedi(@PathVariable String newData) {
        Jedi newJedi = null;
        String prenom = "";
        String nom ="";
        ArrayList<Arme> armes = new ArrayList<Arme>();

        String[] parts = newData.split("&");
        for (String part : parts) {
            String[] subParts = part.split("=");
            if(subParts[0].equals("prenom")) {
                prenom = subParts[1];
            } else if(subParts[0].equals("nom")) {
                nom = subParts[1];
                newJedi = new Jedi(prenom, nom);
            } else {
            	
            	for(Categorie categorie : categorieRepo.findAll()) {
            		if(subParts[0].equals(categorie.getDescription())  && subParts[1].equals("true")) {
            			armes.add(new Arme(categorie.getDescription(), categorie.getPuissance(), newJedi));
            		}
            	}
            }
        }

        newJedi.addArmes(armes);
        jediRepo.save(newJedi);
        armeRepo.saveAll(armes);

        return ResponseEntity.status(HttpStatus.CREATED).body(newJedi);
    }
	
	/**
	 * Mettre à jour
	 */
	@PutMapping(value = "/addJedi/{newData}")
	public ResponseEntity<?> updateApprenant(@PathVariable String newData) throws Exception {
		Jedi resultJedi = null;
		ArrayList<Arme> armes = new ArrayList<Arme>();
        String prenom = "";
        String nom ="";
        Long id = 0L;
		
        Optional<Jedi> jedi = null;

        String[] parts = newData.split("&");
        for (String part : parts) {
            String[] subParts = part.split("=");
            if(subParts[0].equals("prenom")) {
                prenom = subParts[1];
            } else if(subParts[0].equals("nom")) {
                nom = subParts[1];
            } else if(subParts[0].equals("id")) {
                id = Long.parseLong(subParts[1]);
                jedi = jediRepo.findById(id);
                
                Set<Arme> armesOfJedi = jediRepo.findById(id).get().getArmes();           
                for(Arme arme : armesOfJedi) {
                 		 armeRepo.deleteById(arme.getId());
                }
                jedi.get().removeArmes(armesOfJedi);
                
            } else {
            	for(Categorie categorie : categorieRepo.findAll()) {
            		if(subParts[0].equals(categorie.getDescription())  && subParts[1].equals("true")) {
            			armes.add(new Arme(categorie.getDescription(), categorie.getPuissance(), jedi.get()));
            		}
            	}
            } 
        }
        
        armeRepo.saveAll(armes);
		
		if((prenom == null) || (prenom.isEmpty())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Il manque le prénom !");
		}
		else {
			jedi.get().setPrenom(prenom);
			
		}				
		
		if((nom == null) || (nom.isEmpty())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Il manque le nom !");
		}
		else {
			jedi.get().setNom(nom);
		}	
		
		try {
		
			resultJedi = jediRepo.save(jedi.get());
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(resultJedi);
	}
	
	
	/**
	 * supprimer jedi
	 */
	@DeleteMapping("/deluser/{delId}")
    public ResponseEntity<?> delUser(@PathVariable String delId) {
        List<Jedi> listJedi = null;
        jediRepo.deleteById(Long.valueOf(delId));
        try {
            listJedi = jediRepo.findAll();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(listJedi);
    }
	
	
	@GetMapping("/byid/{prenom}")
	public ResponseEntity<?> byName(@PathVariable String prenom) {
		List<Jedi> jedis = null;
		jedis = jediRepo.findByPrenom(prenom);
		return ResponseEntity.status(HttpStatus.CREATED).body(jedis);
		// On renvoit un objet List apprenant transformé au format JSON
	}
}
