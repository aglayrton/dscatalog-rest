package com.devsuperior.dscatalog.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.dscatalog.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	//so com isso ja faz a procura por email
	User findByEmail(String email);
	
}
