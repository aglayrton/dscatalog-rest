package com.devsuperior.dscatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dscatalog.entities.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
