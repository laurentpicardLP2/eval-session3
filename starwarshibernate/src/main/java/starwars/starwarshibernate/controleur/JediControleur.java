package starwars.starwarshibernate.controleur;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import starwars.starwarshibernate.model.Arme;
import starwars.starwarshibernate.model.Jedi;
import starwars.starwarshibernate.repository.ArmeRepository;
import starwars.starwarshibernate.repository.JediRepository;

@RestController
@RequestMapping("/jedi")
public class JediControleur {

	@Autowired
	private JediRepository jediRepo;
	
	@Autowired
	private ArmeRepository armeRepo;
	
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
	 * Retourner toutes les armes
	 */
	@GetMapping("/armes")
	public ResponseEntity<?> getAllArmes(){
		List<Arme> listeArmes = null;
		try {
			listeArmes = armeRepo.findAll(); // Pour faire un "SELECT * FROM armes;"
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(listeArmes);
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
                if(subParts[0].equals("sabre laser")  && subParts[1].equals("true")) {
                    armes.add(new Arme("sabre laser", 52, newJedi));
                } 
                if(subParts[0].equals("force")  && subParts[1].equals("true")) {
                    armes.add(new Arme("force", 100, newJedi));
                }
                if(subParts[0].equals("colt")  && subParts[1].equals("true")){
                    armes.add(new Arme("colt", 34, newJedi));
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
		
        String prenom = "";
        String nom ="";
        Long id = 0L;
		
		String[] parts = newData.split("&");
        for (String part : parts) {
            String[] subParts = part.split("=");
            if(subParts[0].equals("prenom")) {
                prenom = subParts[1];
            } else if(subParts[0].equals("nom")) {
                nom = subParts[1];
            } else if(subParts[0].equals("id")) {
                id = Long.parseLong(subParts[1]);
            }
        }
        
        Optional<Jedi> jedi = jediRepo.findById(id);
		
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
	@GetMapping("/deluser/{delId}")
    public ResponseEntity<?> delUser(@PathVariable String delId) {
        jediRepo.deleteById(new Long(delId));
        return ResponseEntity.status(HttpStatus.CREATED).body(null);

    }
	
	
	@GetMapping("/byid/{prenom}")
	public ResponseEntity<?> byName(@PathVariable String prenom) {
		List<Jedi> jedis = null;
		jedis = jediRepo.findByPrenom(prenom);
		return ResponseEntity.status(HttpStatus.CREATED).body(jedis);
		// On renvoit un objet List apprenant transformé au format JSON
	}
}
