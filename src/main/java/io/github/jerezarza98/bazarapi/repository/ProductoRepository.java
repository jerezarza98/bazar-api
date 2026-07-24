package io.github.jerezarza98.bazarapi.repository;

import io.github.jerezarza98.bazarapi.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    @Query("from Producto p where p.stock < 5")
    List<Producto> productosConFaltaDeStock();
}
