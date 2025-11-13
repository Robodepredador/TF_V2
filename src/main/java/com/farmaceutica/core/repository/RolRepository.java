package com.farmaceutica.core.repository;

import com.farmaceutica.core.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RolRepository extends JpaRepository<Rol, Integer>, JpaSpecificationExecutor<Rol> {
}