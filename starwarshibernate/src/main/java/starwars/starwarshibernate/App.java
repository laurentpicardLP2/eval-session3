package starwars.starwarshibernate;
import java.time.LocalDate;

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
    private JediRepository jediRepository;

    public static void main(String[] args)  {
        SpringApplication.run(App.class, args);
    }

    public void run(String... args) throws Exception {
        // Clean up database tables
        armeRepository.deleteAllInBatch();
        jediRepository.deleteAllInBatch();


        Jedi jedi1 = new Jedi("Luc", "Skywalker");
        jediRepository.save(jedi1);
        
        Arme sabre = new Arme("sabre laser", 52, jedi1);
        
        jedi1.addArme(sabre);


    }

}

