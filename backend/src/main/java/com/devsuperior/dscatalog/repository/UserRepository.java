package com.devsuperior.dscatalog.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.dscatalog.entities.Categoria;

@Repository
public interface UserRepository extends JpaRepository<Categoria, Long>{

}
