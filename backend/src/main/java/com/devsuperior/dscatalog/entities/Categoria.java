package com.devsuperior.dscatalog.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "tb_category")
public class Categoria implements Serializable {
	/**
	 * É uma interface que faz com que o objeto java possa ser convertido em bytes
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant created_At;

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant updated_At;
	
	@ManyToMany(mappedBy = "categorias")
	private Set<Produto> products = new HashSet<>();

	public Categoria() {
	}

	public Categoria(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Instant getCreated_At() {
		return created_At;
	}

	public Instant getUpdated_At() {
		return updated_At;
	}

	// antes de salvar vamos fazer o atributo receber o momento
	@PrePersist
	public void prePersist() {
		created_At = Instant.now();
	}

	@PreUpdate
	public void preAtualizado() {
		updated_At = Instant.now();
	}	
	
	
	public Set<Produto> getProducts() {
		return products;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Categoria other = (Categoria) obj;
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
