package com.clemhlrdt.recipeapp.repository;

import com.clemhlrdt.recipeapp.model.Role;
import com.clemhlrdt.recipeapp.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	Optional<Role> findByName(RoleName roleName);
}
