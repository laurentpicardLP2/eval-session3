package starwars.starwarshibernate.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Table(name = "Jedi")
public class Jedi implements Serializable{

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JoinColumn(name = "Jedi_ID")
	private Long id;
	
	@Size(max = 30)
	@Column(name = "Nom")
	private String nom;
	
	@Size(max = 30)
	@Column(name = "Prenom")
	private String prenom;
	
	@OneToMany(mappedBy="jedi")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<Arme> armes = new HashSet<Arme>();
	
	public Jedi() {
		
	}

	public Jedi(String prenom, String nom) {
		this.nom = nom;
		this.prenom = prenom;
		this.armes = new HashSet<Arme>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Set<Arme> getArmes() {
		return armes;
	}

	public void setArmes(Set<Arme> armes) {
		this.armes = armes;
	}
	
	public boolean addArmes(Collection<Arme> newArmes) {
		return this.armes.addAll(newArmes);
	}
	
	public boolean addArme(Arme newArme) {
		return this.armes.add(newArme);
	}
	
	public boolean removeArmes(Collection<Arme> removeArmes) {
		return this.armes.removeAll(removeArmes);
	}
	
	public boolean removeArme(Arme removeArme) {
		return this.armes.remove(removeArme);
	}

	@Override
	public String toString() {
		return "Jedi [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", armes=" + armes + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((armes == null) ? 0 : armes.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
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
		if (armes == null) {
			if (other.armes != null)
				return false;
		} else if (!armes.equals(other.armes))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (prenom == null) {
			if (other.prenom != null)
				return false;
		} else if (!prenom.equals(other.prenom))
			return false;
		return true;
	}

	
}
