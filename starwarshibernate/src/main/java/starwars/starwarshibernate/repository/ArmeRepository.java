package starwars.starwarshibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import starwars.starwarshibernate.model.Arme;

@Repository
public interface ArmeRepository extends JpaRepository<Arme, Long>{

}
