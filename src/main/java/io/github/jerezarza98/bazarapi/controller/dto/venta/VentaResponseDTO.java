package io.github.jerezarza98.bazarapi.controller.dto.venta;

import io.github.jerezarza98.bazarapi.controller.dto.cliente.ClienteResponseDTO;
import io.github.jerezarza98.bazarapi.controller.dto.producto.ProductoResponseDTO;
import io.github.jerezarza98.bazarapi.model.Venta;

import java.util.List;

public record VentaResponseDTO(Long id, List<ProductoResponseDTO> productos, ClienteResponseDTO cliente, Double total) {
    public static VentaResponseDTO desdeModelo(Venta venta) {
        return new VentaResponseDTO(
                venta.getId(),
                venta.getProductos().stream().map(ProductoResponseDTO::desdeModelo).toList(),
                ClienteResponseDTO.desdeModelo(venta.getCliente()),
                venta.getTotal()
        );
    }
}
