package starwars.starwarshibernate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import starwars.starwarshibernate.model.Jedi;

@Repository
public interface JediRepository extends JpaRepository<Jedi, Long>{

	public List<Jedi> findByPrenom(String prenom);
}
