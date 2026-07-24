package io.github.jerezarza98.bazarapi.service.impl;

import io.github.jerezarza98.bazarapi.exception.VentaNoEncontradoException;
import io.github.jerezarza98.bazarapi.model.Producto;
import io.github.jerezarza98.bazarapi.model.Venta;
import io.github.jerezarza98.bazarapi.repository.ProductoRepository;
import io.github.jerezarza98.bazarapi.repository.VentaRepository;
import io.github.jerezarza98.bazarapi.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Venta crearVenta(Venta venta) {
        descontarStockDeProductos(venta);

        return ventaRepository.save(venta);
    }

    @Override
    public Venta recuperarVenta(Long id) {
        return ventaRepository.findById(id).orElseThrow(VentaNoEncontradoException::new);
    }

    @Override
    public List<Venta> recuperarTodasLasVentas() {
        return ventaRepository.findAll();
    }

    @Override
    public void eliminarVenta(Long id) {
        if(!ventaRepository.existsById(id)) {
            throw new VentaNoEncontradoException();
        }

        ventaRepository.deleteById(id);
    }

    @Override
    public void actualizarVenta(Long id, Venta venta) {
        Venta ventaRecuperado = ventaRepository.findById(id).orElseThrow(VentaNoEncontradoException::new);

        ventaRecuperado.setFechaVenta(venta.getFechaVenta());
        ventaRecuperado.setTotal(venta.getTotal());
        ventaRecuperado.setCliente(venta.getCliente());

        for(Producto producto : ventaRecuperado.getProductos()){
            producto.aumentarStock();
            productoRepository.save(producto);
        }

        descontarStockDeProductos(venta);
        ventaRecuperado.setProductos(venta.getProductos());

        ventaRepository.save(ventaRecuperado);
    }

    @Override
    public void eliminarTodasLasVentas() {
        ventaRepository.deleteAll();
    }

    private void descontarStockDeProductos(Venta venta) {
        for(Producto producto:venta.getProductos()) {
            producto.descontarStock();
            productoRepository.save(producto);
        }
    }
}
