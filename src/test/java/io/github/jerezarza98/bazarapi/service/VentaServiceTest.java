package io.github.jerezarza98.bazarapi.service;

import io.github.jerezarza98.bazarapi.exception.ProductoSinStockException;
import io.github.jerezarza98.bazarapi.exception.VentaNoEncontradoException;
import io.github.jerezarza98.bazarapi.exception.VentaSinProductosException;
import io.github.jerezarza98.bazarapi.model.Cliente;
import io.github.jerezarza98.bazarapi.model.Producto;
import io.github.jerezarza98.bazarapi.model.Venta;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class VentaServiceTest {
    @Autowired
    private VentaService ventaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProductoService productoService;

    private Cliente cliente;
    private Producto unProducto;
    private Producto otroProducto;
    private Venta venta;

    @BeforeEach
    void setup() {
        cliente = crearCliente("Juan", "Pérez", "35123456");
        unProducto = crearProducto("Mouse Gamer Pro", "Novatech", 45.99, 40);
        otroProducto = crearProducto("Auriculares Inalámbricos X200", "TechWave", 79.99, 25);
        venta = new Venta(LocalDate.of(2026, 7, 23), new ArrayList<>(List.of(unProducto, otroProducto)), cliente);
    }

    private Cliente crearCliente(String nombre, String apellidos, String dni) {
        return clienteService.crearCliente(new Cliente(nombre, apellidos, dni));
    }

    private Producto crearProducto(String nombre, String marca, Double precio, int stock) {
        return productoService.crearProducto(new Producto(nombre, marca, precio, stock));
    }

    @Test
    void crearVentaTest() {
        assertNull(venta.getId());
        ventaService.crearVenta(venta);
        assertNotNull(venta.getId());
    }

    @Test
    void crearVentaCuandoNoTieneNingunProductoLanzaVentaSinProductosExceptionTest() {
        venta.setProductos(new ArrayList<>());

        assertThrows(VentaSinProductosException.class, () -> ventaService.crearVenta(venta));
    }

    @Test
    void crearVentaCuandoAlgunProductoNoTieneStockLanzaProductoSinStockExceptionTest() {
        unProducto.setStock(0);

        assertThrows(ProductoSinStockException.class, () -> ventaService.crearVenta(venta));
    }

    @Test
    void recuperarVentaTest() {
        Long id = ventaService.crearVenta(venta).getId();

        Venta ventaRecuperado = ventaService.recuperarVenta(id);

        assertAll(
                () -> assertTrue(venta.getFechaVenta().isEqual(ventaRecuperado.getFechaVenta())),
                () -> assertTrue(venta.getProductos().containsAll(ventaRecuperado.getProductos())),
                () -> assertEquals(venta.getCliente().getId(), ventaRecuperado.getCliente().getId()),
                () -> assertEquals(venta.getTotal(), ventaRecuperado.getTotal())
        );
    }

    @Test
    void recuperarVentaCuandoNoExisteLaVentaLanzaVentaNoEncontradoExceptionTest() {
        Long id = ventaService.crearVenta(venta).getId();

        assertThrows(VentaNoEncontradoException.class, () -> ventaService.recuperarVenta(id+1));
    }

    @Test
    void recuperarTodasLasVentasCuandoNoExisteNingunaVentaDevuelveUnaListaVaciaTest() {
        assertTrue(ventaService.recuperarTodasLasVentas().isEmpty());
    }

    @Test
    void recuperarTodasLasVentasCuandoExisteVentasDevuelveUnaListaDeVentasTest() {
        Venta ventaCreada = ventaService.crearVenta(venta);

        List<Venta> ventas = ventaService.recuperarTodasLasVentas();

        assertFalse(ventas.isEmpty());
        assertEquals(1, ventas.size());
        assertTrue(ventas.contains(ventaCreada));
    }

    @Test
    void eliminarVentaTest() {
        Long id = ventaService.crearVenta(venta).getId();

        ventaService.eliminarVenta(id);

        assertTrue(ventaService.recuperarTodasLasVentas().isEmpty());
    }

    @Test
    void eliminarVentaCuandoNoExisteLaVentaLanzaVentaNoEncontradoExceptionTest() {
        Long id = ventaService.crearVenta(venta).getId();

        assertThrows(VentaNoEncontradoException.class, () -> ventaService.eliminarVenta(id+1));
    }

    @Test
    void actualizarVentaCuandoNoExisteLaVentaLanzaVentaNoEncontradoExceptionTest() {
        Long id = ventaService.crearVenta(venta).getId();

        assertThrows(VentaNoEncontradoException.class, () -> ventaService.actualizarVenta(id+1, venta));
    }

    @Test
    void actualizarVentaTest() {
        Long id = ventaService.crearVenta(venta).getId();
        Cliente otroCliente = crearCliente("Lucas", "Fernández", "41234567");
        Venta ventaAActualizar = new Venta(LocalDate.of(2026, 7, 22), new ArrayList<>(List.of(otroProducto)), otroCliente);

        ventaService.actualizarVenta(id, ventaAActualizar);

        Venta ventaRecuperada = ventaService.recuperarVenta(id);

        assertAll(
                () -> assertTrue(ventaAActualizar.getFechaVenta().isEqual(ventaRecuperada.getFechaVenta())),
                () -> assertTrue(ventaRecuperada.getProductos().contains(otroProducto)),
                () -> assertFalse(ventaRecuperada.getProductos().contains(unProducto)),
                () -> assertEquals(ventaAActualizar.getCliente().getId(), ventaRecuperada.getCliente().getId()),
                () -> assertEquals(ventaAActualizar.getTotal(), ventaRecuperada.getTotal())
        );
    }

    @Test
    void recuperarProductosDeVentaDevuelveUnaListaDeProductosTest() {
        Long id = ventaService.crearVenta(venta).getId();

        List<Producto> productos = ventaService.recuperarProductosDeVenta(id);

        assertTrue(productos.containsAll(List.of(unProducto, otroProducto)));
        assertEquals(2, productos.size());
    }

    @AfterEach
    void tearDown() {
        ventaService.eliminarTodasLasVentas();
        clienteService.eliminarTodosLosClientes();
        productoService.eliminarTodosLosProductos();
    }
}
