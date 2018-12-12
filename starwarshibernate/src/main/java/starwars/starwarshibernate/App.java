package starwars.starwarshibernate;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import starwars.starwarshibernate.model.Arme;
import starwars.starwarshibernate.model.Jedi;
import starwars.starwarshibernate.repository.ArmeRepository;
import starwars.starwarshibernate.repository.JediRepository;


@SpringBootApplication
public class App implements CommandLineRunner {
	
    @Autowired
    private ArmeRepository armeRepository;
    
    @Autowired
    private JediRepository jediRepository;

    public static void main(String[] args)  {
        SpringApplication.run(App.class, args);
    }

    public void run(String... args) throws Exception {
    	
        // Clean up database tables
        armeRepository.deleteAllInBatch();;
        jediRepository.deleteAllInBatch();
        


        Jedi luc = new Jedi("Luc", "Skywalker");
        Jedi yoda = new Jedi("Maitre", "Yoda");
        
        
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
        
        //delete yoda => delete cascade colt & force
      	jediRepository.delete(yoda);
      	
       
    }

}

