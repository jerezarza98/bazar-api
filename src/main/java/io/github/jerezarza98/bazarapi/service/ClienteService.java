package io.github.jerezarza98.bazarapi.service;

import io.github.jerezarza98.bazarapi.model.Cliente;

import java.util.List;

public interface ClienteService {
    Cliente crearCliente(Cliente cliente);
    Cliente recuperarCliente(Long id);
    List<Cliente> recuperarTodosLosClientes();
    void eliminarCliente(Long id);
    void actualizarCliente(Long id, Cliente cliente);
    void eliminarTodosLosClientes();
}
