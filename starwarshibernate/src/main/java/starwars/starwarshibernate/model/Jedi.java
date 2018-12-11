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

	@Override
	public String toString() {
		return "Jedi [id=" + id + ", name=" + name + ", firstname=" + firstname + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jedi other = (Jedi) obj;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
