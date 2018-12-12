package starwars.starwarshibernate.controleur;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	
	@GetMapping("/byname/{nom}")
	public ResponseEntity<?> byName(@PathVariable String nom) {
		List<Jedi> jedis = null;
		jedis = jediRepo.findByName(nom);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(jedis);
		// On renvoit un objet List apprenant transformé au format JSON
	}
}
