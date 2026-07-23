package io.github.jerezarza98.bazarapi.service;

import io.github.jerezarza98.bazarapi.model.Venta;

import java.util.List;

public interface VentaService {
    Venta crearVenta(Venta venta);
    Venta recuperarVenta(Long id);
    List<Venta> recuperarTodasLasVentas();
    void eliminarVenta(Long id);
    void actualizarVenta(Long id, Venta venta);
    void eliminarTodasLasVentas();
}
