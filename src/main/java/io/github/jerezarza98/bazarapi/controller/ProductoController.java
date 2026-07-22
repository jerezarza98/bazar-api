package io.github.jerezarza98.bazarapi.controller;

import io.github.jerezarza98.bazarapi.controller.dto.producto.ProductoRequestDTO;
import io.github.jerezarza98.bazarapi.controller.dto.producto.ProductoResponseDTO;
import io.github.jerezarza98.bazarapi.model.Producto;
import io.github.jerezarza98.bazarapi.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/producto")
public class ProductoController {
    @Autowired
    protected ProductoService productoService;

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crearProducto(@RequestBody ProductoRequestDTO productoRequestDTO){
        Producto producto = productoService.crearProducto(productoRequestDTO.aModelo());

        URI location = URI.create("/producto/" + producto.getId());

        return ResponseEntity.created(location).body(ProductoResponseDTO.desdeModelo(producto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> recuperarProducto(@PathVariable Long id){
        Producto producto = productoService.recuperarProducto(id);

        return ResponseEntity.ok(ProductoResponseDTO.desdeModelo(producto));
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> recuperarProductos(){
        List<Producto> productos = productoService.recuperarTodosLosProductos();
        List<ProductoResponseDTO> dtos = productos.stream().map(ProductoResponseDTO::desdeModelo).toList();
        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id){
        productoService.eliminarProducto(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizarProducto(@PathVariable Long id, @RequestBody ProductoRequestDTO productoRequestDTO){
        productoService.actualizarProducto(id, productoRequestDTO.aModelo());

        Producto producto = productoService.recuperarProducto(id);

        return ResponseEntity.ok(ProductoResponseDTO.desdeModelo(producto));
    }

}
