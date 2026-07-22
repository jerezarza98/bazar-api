package io.github.jerezarza98.bazarapi.service.impl;

import io.github.jerezarza98.bazarapi.exception.ClienteNoEncontradoException;
import io.github.jerezarza98.bazarapi.model.Cliente;
import io.github.jerezarza98.bazarapi.repository.ClienteRepository;
import io.github.jerezarza98.bazarapi.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Cliente crearCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente recuperarCliente(Long id) {
        return clienteRepository.findById(id).orElseThrow(ClienteNoEncontradoException::new);
    }

    @Override
    public List<Cliente> recuperarTodosLosClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public void eliminarCliente(Long id) {
        if(!clienteRepository.existsById(id)) {
            throw new ClienteNoEncontradoException();
        }

        clienteRepository.deleteById(id);
    }

    @Override
    public void actualizarCliente(Long id, Cliente cliente) {
        Cliente clienteRecuperado = clienteRepository.findById(id).orElseThrow(ClienteNoEncontradoException::new);

        clienteRecuperado.setNombre(cliente.getNombre());
        clienteRecuperado.setApellido(cliente.getApellido());
        clienteRecuperado.setDni(cliente.getDni());

        clienteRepository.save(clienteRecuperado);
    }

    @Override
    public void eliminarTodosLosClientes() {
        clienteRepository.deleteAll();
    }
}
