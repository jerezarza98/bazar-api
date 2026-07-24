package io.github.jerezarza98.bazarapi.service;

import io.github.jerezarza98.bazarapi.exception.ProductoNoEncontradoException;
import io.github.jerezarza98.bazarapi.model.Producto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ProductoServiceTest {
    @Autowired
    private ProductoService productoService;

    private Producto producto;

    @BeforeEach
    void setup() {
        producto = new Producto("Mouse Gamer Pro", "Novatech", 45.99, 40);
    }

    @Test
    void crearProductoTest() {
        assertNull(producto.getId());
        productoService.crearProducto(producto);
        assertNotNull(producto.getId());
    }

    @Test
    void recuperarProductoTest() {
        Long id = productoService.crearProducto(producto).getId();

        Producto productoRecuperado = productoService.recuperarProducto(id);

        assertAll(
                () -> assertEquals(producto.getNombre(), productoRecuperado.getNombre()),
                () -> assertEquals(producto.getMarca(), productoRecuperado.getMarca()),
                () -> assertEquals(producto.getPrecio(), productoRecuperado.getPrecio()),
                () -> assertEquals(producto.getStock(), productoRecuperado.getStock())
        );
    }

    @Test
    void recuperarProductoQueNoExisteLanzaProductoNoEncontradoExceptionTest() {
        Long id = productoService.crearProducto(producto).getId();

        assertThrows(ProductoNoEncontradoException.class, () -> productoService.recuperarProducto(id+1));
    }

    @Test
    void recuperarTodosLosProductosCuandoNoExisteNingunProductoDevuelveUnaListaVaciaTest() {
        assertTrue(productoService.recuperarTodosLosProductos().isEmpty());
    }

    @Test
    void recuperarTodosLosProductosCuandoExisteProductosDevuelveUnaListaDeProductosTest() {
        Producto productoCreado = productoService.crearProducto(producto);

        List<Producto> productos = productoService.recuperarTodosLosProductos();

        assertFalse(productos.isEmpty());
        assertEquals(1, productos.size());
        assertTrue(productos.contains(productoCreado));
    }

    @Test
    void eliminarProductoTest() {
        Long id = productoService.crearProducto(producto).getId();

        productoService.eliminarProducto(id);

        assertTrue(productoService.recuperarTodosLosProductos().isEmpty());
    }

    @Test
    void eliminarProductoCuandoUnProductoNoExisteLanzaProductoNoEncontradoExceptionTest() {
        Long id = productoService.crearProducto(producto).getId();

        assertThrows(ProductoNoEncontradoException.class, () -> productoService.eliminarProducto(id+1));
    }

    @Test
    void actualizarProductoTest() {
        Long id = productoService.crearProducto(producto).getId();
        Producto productoAActualizar = new Producto("Auriculares Inalámbricos X200", "TechWave", 79.99, 25);

        productoService.actualizarProducto(id, productoAActualizar);
        Producto productoActualizado = productoService.recuperarProducto(id);

        assertAll(
                () -> assertEquals(productoAActualizar.getNombre(), productoActualizado.getNombre()),
                () -> assertEquals(productoAActualizar.getMarca(), productoActualizado.getMarca()),
                () -> assertEquals(productoAActualizar.getPrecio(), productoActualizado.getPrecio()),
                () -> assertEquals(productoAActualizar.getStock(), productoActualizado.getStock())
        );
    }

    @Test
    void actualizarProductoCuandoNoExisteLanzaProductoNoEncontradoExceptionTest() {
        Long id = productoService.crearProducto(producto).getId();

        Producto productoAActualizar = new Producto("Auriculares Inalámbricos X200", "TechWave", 79.99, 25);

        assertThrows(ProductoNoEncontradoException.class, () -> productoService.actualizarProducto(id+1, productoAActualizar));
    }

    @Test
    void recuperarProductosPorIdsCuandoNoExisteNingunProductoDevuelveUnaListaVaciaTest() {
        List<Long> ids = new ArrayList<>();

        assertTrue(productoService.recuperarProductos(ids).isEmpty());
    }

    @Test
    void recuperarProductosPorIdsCuandoExisteProductosDevuelveUnaListaDeProductosTest() {
        Producto productoCreado = productoService.crearProducto(producto);
        Producto otroProductoCreado = productoService.crearProducto(new Producto("Auriculares Inalámbricos X200", "TechWave", 79.99, 25));

        List<Producto> productos = productoService.recuperarProductos(List.of(otroProductoCreado.getId()));

        assertFalse(productos.isEmpty());
        assertEquals(1, productos.size());
        assertTrue(productos.contains(otroProductoCreado));
        assertFalse(productos.contains(productoCreado));
    }

    @Test
    void recuperarProductosPorIdsCuandoNoExisteAlgunProductoLanzaProductoNoEncontradoExceptionTest() {
        Long id = productoService.crearProducto(producto).getId();

        assertThrows(ProductoNoEncontradoException.class, () -> productoService.recuperarProductos(List.of(id+1)));
    }

    @AfterEach
    void tearDown() {
        productoService.eliminarTodosLosProductos();
    }
}
