package com.devsuperior.dscatalog.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devsuperior.dscatalog.entities.Categoria;
import com.devsuperior.dscatalog.entities.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	/*busque todos os produtos da minha entidade
	 * 
	 * 
	 * */
	@Query("SELECT DISTINCT obj FROM Produto obj "
			+ "INNER JOIN obj.categorias cats WHERE " 
			+ "(:categorias IS NULL OR cats IN :categorias) AND "
			+ "(LOWER(obj.name) LIKE LOWER(CONCAT('%', :name, '%')) )"
			) 
	Page<Produto> find(List<Categoria> categorias, String name, Pageable pageable);
	
}
