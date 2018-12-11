package starwars.starwarshibernate.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Jedi")
public class Jedi implements Serializable{

	private static final long serialVersionUID = -3777855243906542174L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "Jedi_ID")
	private Integer id;
	
	@Size(max = 30)
	@Column(name = "Name")
	private String name;
	
	@Size(max = 30)
	@Column(name = "Firstname")
	private String firstname;
	
	@OneToMany(mappedBy="jedi")
	private Set<Arme> armes = new HashSet<Arme>();
	
	public Jedi() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public Set<Arme> getArmes() {
		return armes;
	}

	public void setArmes(Set<Arme> armes) {
		this.armes = armes;
	}
	
	
}
