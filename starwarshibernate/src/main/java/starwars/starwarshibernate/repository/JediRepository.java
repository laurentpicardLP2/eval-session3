package starwars.starwarshibernate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import starwars.starwarshibernate.model.Jedi;

@Repository
public interface JediRepository extends CrudRepository<Jedi, Long>{

}
