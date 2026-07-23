package io.github.jerezarza98.bazarapi.model;

import io.github.jerezarza98.bazarapi.exception.ProductoSinStockException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ProductoTest {
    private Producto producto;

    @BeforeEach
    void setup() {
        producto = new Producto("Mouse Gamer Pro", "Novatech", 45.99, 40);
    }

    @Test
    void descontarStockDeUnProductoTest() {
        producto.descontarStock();

        assertEquals(39, producto.getStock());
    }

    @Test
    void descontarStockDeProductoCuandoNoHayStockLanzaProductoSinStockExceptionTest() {
        producto.setStock(0);

        assertThrows(ProductoSinStockException.class, () -> producto.descontarStock());
    }

    @Test
    void aumentarStockDeUnProductoTest() {
        producto.aumentarStock();

        assertEquals(41, producto.getStock());
    }
}
