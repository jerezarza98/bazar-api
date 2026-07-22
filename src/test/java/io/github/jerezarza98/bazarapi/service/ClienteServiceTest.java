package io.github.jerezarza98.bazarapi.service;

import io.github.jerezarza98.bazarapi.exception.ClienteNoEncontradoException;
import io.github.jerezarza98.bazarapi.model.Cliente;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ClienteServiceTest {
    @Autowired
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    void setup() {
        cliente = new Cliente("Juan", "Pérez", "35123456");
    }

    @Test
    void crearClienteTest() {
        assertNull(cliente.getId());
        clienteService.crearCliente(cliente);
        assertNotNull(cliente.getId());
    }

    @Test
    void recuperarClienteTest() {
        Long id = clienteService.crearCliente(cliente).getId();

        Cliente clienteRecuperado = clienteService.recuperarCliente(id);

        assertAll(
                () -> assertEquals(cliente.getNombre(), clienteRecuperado.getNombre()),
                () -> assertEquals(cliente.getApellido(), clienteRecuperado.getApellido()),
                () -> assertEquals(cliente.getDni(), clienteRecuperado.getDni())
        );
    }

    @Test
    void recuperarClienteCuandoNoExisteLanzaClienteNoEncontradoExceptionTest() {
        Long id = clienteService.crearCliente(cliente).getId();

        assertThrows(ClienteNoEncontradoException.class, () -> clienteService.recuperarCliente(id+1));
    }

    @Test
    void recuperarTodosLosClientesCuandoNoExisteNingunClienteDevuelveUnaListaVaciaTest() {
        assertTrue(clienteService.recuperarTodosLosClientes().isEmpty());
    }

    @Test
    void recuperarTodosLosClientesCuandoExisteClientesDevuelveUnaListaDeClientesTest() {
        Cliente clienteCreado = clienteService.crearCliente(cliente);

        List<Cliente> clientes = clienteService.recuperarTodosLosClientes();

        assertFalse(clientes.isEmpty());
        assertEquals(1, clientes.size());
        assertTrue(clientes.contains(clienteCreado));
    }

    @Test
    void eliminarClienteTest() {
        Long id = clienteService.crearCliente(cliente).getId();

        clienteService.eliminarCliente(id);

        assertTrue(clienteService.recuperarTodosLosClientes().isEmpty());
    }

    @Test
    void eliminarClienteCuandoNoExisteLanzaClienteNoEncontradoExceptionTest() {
        Long id = clienteService.crearCliente(cliente).getId();

        assertThrows(ClienteNoEncontradoException.class, () -> clienteService.eliminarCliente(id+1));
    }

    @Test
    void actualizarClienteTest() {
        Long id = clienteService.crearCliente(cliente).getId();
        Cliente clienteAActualizar = new Cliente("Lucas", "Fernández", "41234567");

        clienteService.actualizarCliente(id, clienteAActualizar);

        Cliente clienteRecuperado = clienteService.recuperarCliente(id);

        assertAll(
                () -> assertEquals(clienteAActualizar.getNombre(), clienteRecuperado.getNombre()),
                () -> assertEquals(clienteAActualizar.getApellido(), clienteRecuperado.getApellido()),
                () -> assertEquals(clienteAActualizar.getDni(), clienteRecuperado.getDni())
        );
    }

    @Test
    void actualizarClienteCuandoNoExisteLanzaClienteNoEncontradoExceptionTest() {
        Long id = clienteService.crearCliente(cliente).getId();
        Cliente clienteAActualizar = new Cliente("Lucas", "Fernández", "41234567");

        assertThrows(ClienteNoEncontradoException.class, () -> clienteService.actualizarCliente(id+1, clienteAActualizar));
    }

    @AfterEach
    void tearDown() {
        clienteService.eliminarTodosLosClientes();
    }
}
