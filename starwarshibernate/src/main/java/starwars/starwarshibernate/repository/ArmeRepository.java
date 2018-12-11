package starwars.starwarshibernate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArmeRepository extends CrudRepository<Arme, Long>{

}
