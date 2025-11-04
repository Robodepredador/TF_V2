package com.farmaceutica.core.repository;

import com.farmaceutica.core.model.VwStockActual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VwStockActualRepository extends JpaRepository<VwStockActual, Integer> {

    /**
     * Busca todos los lotes disponibles para un producto específico,
     * ordenados por fecha de vencimiento (para lógica FEFO).
     */
    List<VwStockActual> findByIdProductoOrderByFechaVencimientoAsc(Integer idProducto);
}