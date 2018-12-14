package starwars.starwarshibernate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import starwars.starwarshibernate.model.Arme;
import starwars.starwarshibernate.model.Categorie;
import starwars.starwarshibernate.model.Jedi;
import starwars.starwarshibernate.repository.ArmeRepository;
import starwars.starwarshibernate.repository.CategorieRepository;
import starwars.starwarshibernate.repository.JediRepository;


@SpringBootApplication
public class App implements CommandLineRunner {
	
    @Autowired
    private ArmeRepository armeRepository;
    
    @Autowired
    private JediRepository jediRepository;
    
    @Autowired
    private CategorieRepository categorieRepository;

    public static void main(String[] args)  {
        SpringApplication.run(App.class, args);
    }

    public void run(String... args) throws Exception {
    	
        // Clean up database tables
        armeRepository.deleteAll();;
        jediRepository.deleteAll();
        categorieRepository.deleteAll();

        Jedi luc = new Jedi("Luc", "Skywalker");
        Jedi yoda = new Jedi("Maitre", "Yoda");
        Jedi leia = new Jedi("Leia", "Skywalker");
        Jedi anakin = new Jedi("Anakin", "Skywalker");
        
        
        Arme sabre = new Arme("sabre laser", 52, luc);
        luc.addArme(sabre);
        jediRepository.save(luc);
        armeRepository.save(sabre);
        
        Arme colt = new Arme("colt", 34, yoda);
        Arme force = new Arme("force", 100, yoda);
        Collection<Arme> armes_yoda = Arrays.asList(colt, force);
		yoda.addArmes(armes_yoda);
        jediRepository.save(yoda);
        armeRepository.saveAll(armes_yoda);
        jediRepository.save(leia);      	
        jediRepository.save(anakin);
        
        List <Categorie> categories = Arrays.asList(new Categorie("Vaisseau spatial", 745),new Categorie("sabre laser", 52), new Categorie("force", 100),new Categorie("colt", 34));
        for(Categorie categorie : categories) {
        	categorieRepository.save(categorie);
        }
        
        
       
    }

}

