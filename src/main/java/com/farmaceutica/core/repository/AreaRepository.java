package com.farmaceutica.core.repository;

import com.farmaceutica.core.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AreaRepository extends JpaRepository<Area, Integer>, JpaSpecificationExecutor<Area> {
}