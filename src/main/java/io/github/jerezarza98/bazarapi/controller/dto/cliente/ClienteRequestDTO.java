package io.github.jerezarza98.bazarapi.controller.dto.cliente;

import io.github.jerezarza98.bazarapi.model.Cliente;

public record ClienteRequestDTO(String nombre, String apellido, String dni) {

    public Cliente aModelo() {
        return new Cliente(nombre, apellido, dni);
    }
}
