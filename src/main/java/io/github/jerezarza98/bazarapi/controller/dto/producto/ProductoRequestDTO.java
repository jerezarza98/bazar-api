package io.github.jerezarza98.bazarapi.controller.dto.producto;

import io.github.jerezarza98.bazarapi.model.Producto;

public record ProductoRequestDTO(String nombre, String marca, Double precio, int stock) {
    public Producto aModelo() {
        return new Producto(nombre, marca, precio, stock);
    }
}
