package io.github.jerezarza98.bazarapi.controller;

import io.github.jerezarza98.bazarapi.controller.dto.venta.VentaRequestDTO;
import io.github.jerezarza98.bazarapi.controller.dto.venta.VentaResponseDTO;
import io.github.jerezarza98.bazarapi.model.Cliente;
import io.github.jerezarza98.bazarapi.model.Producto;
import io.github.jerezarza98.bazarapi.model.Venta;
import io.github.jerezarza98.bazarapi.service.ClienteService;
import io.github.jerezarza98.bazarapi.service.ProductoService;
import io.github.jerezarza98.bazarapi.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/venta")
public class VentaController {
    @Autowired
    private VentaService ventaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProductoService productoService;

    @PostMapping
    public ResponseEntity<VentaResponseDTO> crearVenta(@RequestBody VentaRequestDTO ventaRequestDTO){
        Cliente cliente = clienteService.recuperarCliente(ventaRequestDTO.clienteId());
        List<Producto> productos = productoService.recuperarProductos(ventaRequestDTO.productosId());
        Venta venta = ventaService.crearVenta(ventaRequestDTO.aModelo(cliente, productos));

        URI location = URI.create("/venta/" + venta.getId());

        return ResponseEntity.created(location).body(VentaResponseDTO.desdeModelo(venta));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> recuperarVenta(@PathVariable Long id){
        Venta venta = ventaService.recuperarVenta(id);

        return ResponseEntity.ok(VentaResponseDTO.desdeModelo(venta));
    }

    @GetMapping
    public ResponseEntity<List<VentaResponseDTO>> recuperarVentas(){
        List<Venta> ventas = ventaService.recuperarTodasLasVentas();

        List<VentaResponseDTO> dtos = ventas.stream().map(VentaResponseDTO::desdeModelo).toList();

        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> actualizarVenta(@PathVariable Long id, @RequestBody VentaRequestDTO ventaRequestDTO){
        Cliente cliente = clienteService.recuperarCliente(ventaRequestDTO.clienteId());
        List<Producto> productos = productoService.recuperarProductos(ventaRequestDTO.productosId());
        ventaService.actualizarVenta(id, ventaRequestDTO.aModelo(cliente, productos));

        Venta venta = ventaService.recuperarVenta(id);

        return ResponseEntity.ok(VentaResponseDTO.desdeModelo(venta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id){
        ventaService.eliminarVenta(id);

        return ResponseEntity.noContent().build();
    }
}
