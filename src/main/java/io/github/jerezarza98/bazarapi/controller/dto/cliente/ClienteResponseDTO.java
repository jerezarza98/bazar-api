package io.github.jerezarza98.bazarapi.controller.dto.cliente;

import io.github.jerezarza98.bazarapi.model.Cliente;

public record ClienteResponseDTO(Long id, String nombre, String apellido, String dni) {

    public static ClienteResponseDTO desdeModelo(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getDni()
        );
    }
}
