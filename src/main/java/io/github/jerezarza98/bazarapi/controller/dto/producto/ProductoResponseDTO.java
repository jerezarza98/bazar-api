package io.github.jerezarza98.bazarapi.controller.dto.producto;

import io.github.jerezarza98.bazarapi.model.Producto;

public record ProductoResponseDTO(Long id, String nombre, String marca, Double precio, int stock) {

    public static ProductoResponseDTO desdeModelo(Producto producto) {
        return new ProductoResponseDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getMarca(),
                producto.getPrecio(),
                producto.getStock()
        );
    }
}
