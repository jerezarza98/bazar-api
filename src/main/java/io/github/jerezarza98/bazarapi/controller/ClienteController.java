package io.github.jerezarza98.bazarapi.controller;

import io.github.jerezarza98.bazarapi.controller.dto.cliente.ClienteRequestDTO;
import io.github.jerezarza98.bazarapi.controller.dto.cliente.ClienteResponseDTO;
import io.github.jerezarza98.bazarapi.model.Cliente;
import io.github.jerezarza98.bazarapi.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> crearCliente(@RequestBody ClienteRequestDTO clienteRequestDTO){
        Cliente cliente = clienteService.crearCliente(clienteRequestDTO.aModelo());

        URI location = URI.create("/cliente/" + cliente.getId());

        return ResponseEntity.created(location).body(ClienteResponseDTO.desdeModelo(cliente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> recuperarCliente(@PathVariable Long id) {
        Cliente cliente = clienteService.recuperarCliente(id);

        return ResponseEntity.ok(ClienteResponseDTO.desdeModelo(cliente));
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> recuperarClientes() {
        List<Cliente> clientes = clienteService.recuperarTodosLosClientes();

        List<ClienteResponseDTO> dtos = clientes.stream().map(ClienteResponseDTO::desdeModelo).toList();

        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> actualizarCliente(@PathVariable Long id, @RequestBody ClienteRequestDTO clienteRequestDTO) {
        clienteService.actualizarCliente(id, clienteRequestDTO.aModelo());

        Cliente clienteRecuperado = clienteService.recuperarCliente(id);

        return ResponseEntity.ok(ClienteResponseDTO.desdeModelo(clienteRecuperado));
    }
}
