package io.github.jerezarza98.bazarapi.service;

import io.github.jerezarza98.bazarapi.model.Producto;

import java.util.List;

public interface ProductoService {
    Producto crearProducto(Producto producto);
    Producto recuperarProducto(Long id);
    List<Producto> recuperarTodosLosProductos();
    void eliminarProducto(Long id);
    void actualizarProducto(Long id, Producto producto);
    void eliminarTodosLosProductos();
}
