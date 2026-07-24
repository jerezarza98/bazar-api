package io.github.jerezarza98.bazarapi.controller.dto.venta;

import io.github.jerezarza98.bazarapi.model.Cliente;
import io.github.jerezarza98.bazarapi.model.Producto;
import io.github.jerezarza98.bazarapi.model.Venta;

import java.time.LocalDate;
import java.util.List;

public record VentaRequestDTO(LocalDate fecha, List<Long> productosId, Long clienteId ) {
    public Venta aModelo(Cliente cliente, List<Producto> productos) {
        return new Venta(fecha, productos, cliente);
    }
}
