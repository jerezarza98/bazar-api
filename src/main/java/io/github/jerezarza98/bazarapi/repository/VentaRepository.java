package io.github.jerezarza98.bazarapi.repository;

import io.github.jerezarza98.bazarapi.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
}
