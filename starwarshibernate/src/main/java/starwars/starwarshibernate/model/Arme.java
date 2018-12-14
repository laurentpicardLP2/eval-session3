package starwars.starwarshibernate.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "Armes")
public class Arme implements Serializable {

    // default serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Arme_ID")
    private Long id;

    @NotNull
    @Size(max = 65)
    @Column(name = "Model")
    private String model;


    @Column(name = "Puissance")
    private int puissance;
    
    @ManyToOne
	@JoinColumn(name="Jedi_ID")
    private Jedi jedi;

    
    public Arme() {

    }

    public Arme(@Size(max = 65) String model, int puissance, Jedi jedi) {
        this.model = model;
        this.puissance = puissance;
        this.jedi = jedi;
    }

    public Long getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public int getPuissance() {
        return puissance;
    }
    @JsonIgnore
    public Jedi getJedi() {
		return jedi;
	}

	public void setJedi(Jedi jedi) {
		this.jedi = jedi;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setPuissance(int puissance) {
		this.puissance = puissance;
	}

	@Override
    public String toString() {
        return "Arme [id=" + id + ", model=" + model + ", puissance=" + puissance + "]";
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + puissance;
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
		Arme other = (Arme) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (puissance != other.puissance)
			return false;
		return true;
	}
}
