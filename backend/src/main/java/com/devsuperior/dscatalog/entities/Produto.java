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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tb_product")
public class Produto implements Serializable{
	

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@Column(columnDefinition = "TEXT")
	private String description;
	
	private Double price;
	private String imgUrl;
	
	//pega o momento - date e hora
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE") //diz ao banco pra armazenar como UTC
	private Instant date;
	
	//Usando set para nao ter repetição
	@ManyToMany
	@JoinTable( 
			name = "tb_product_category",
			joinColumns = @JoinColumn(name = "product_id"), //relaciona a classe onde estou
			inverseJoinColumns = @JoinColumn(name = "category_id")//ele sabe referenciar devido o tipo ser categoria abaixo
	)
	Set<Categoria> categorias = new HashSet<>();
	
	public Produto() {
	}

	public Produto(Long id, String name, String description, Double price, String imgURL, Instant date) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgURL;
		this.date = date;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImgURL() {
		return imgUrl;
	}

	public void setImgURL(String imgURL) {
		this.imgUrl = imgURL;
	}

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}

	public Set<Categoria> getCategorias() {
		return categorias;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	/* pois nao vamos deixar ninguem mexer nas categorias
	public void setCategorias(Set<Categoria> categorias) {
		this.categorias = categorias;
	}*/

	
	
	
}
