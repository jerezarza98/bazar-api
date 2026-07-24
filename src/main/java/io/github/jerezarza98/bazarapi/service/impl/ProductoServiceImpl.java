package io.github.jerezarza98.bazarapi.service.impl;

import io.github.jerezarza98.bazarapi.exception.ProductoNoEncontradoException;
import io.github.jerezarza98.bazarapi.model.Producto;
import io.github.jerezarza98.bazarapi.repository.ProductoRepository;
import io.github.jerezarza98.bazarapi.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Producto recuperarProducto(Long id) {
        return productoRepository.findById(id).orElseThrow(ProductoNoEncontradoException::new);
    }

    @Override
    public List<Producto> recuperarTodosLosProductos() {
        return productoRepository.findAll();
    }

    @Override
    public void eliminarProducto(Long id) {
        if(!productoRepository.existsById(id)) {
            throw new ProductoNoEncontradoException();
        }

        productoRepository.deleteById(id);
    }

    @Override
    public void actualizarProducto(Long id, Producto producto) {
        Producto productoRecuperado = productoRepository.findById(id).orElseThrow(ProductoNoEncontradoException::new);

        productoRecuperado.setNombre(producto.getNombre());
        productoRecuperado.setMarca(producto.getMarca());
        productoRecuperado.setPrecio(producto.getPrecio());
        productoRecuperado.setStock(producto.getStock());

        productoRepository.save(productoRecuperado);
    }

    @Override
    public void eliminarTodosLosProductos() {
        productoRepository.deleteAll();
    }

    @Override
    public List<Producto> recuperarProductos(List<Long> ids) {
        List<Producto> productos = new ArrayList<>();
        for(Long id : ids) {
            Producto producto = recuperarProducto(id);
            productos.add(producto);
        }
        return productos;
    }

    @Override
    public List<Producto> recuperarProductosConFaltaDeStock() {
        return productoRepository.productosConFaltaDeStock();
    }
}
