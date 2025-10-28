package com.coldflame.farmacia.controller;

import com.coldflame.farmacia.entity.Cliente;
import com.coldflame.farmacia.repository.ClienteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // GET /clientes → listar todos
    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    // GET /clientes/{id} → buscar por id
    @GetMapping("/{id}")
    public Optional<Cliente> obtenerCliente(@PathVariable Long id) {
        return clienteRepository.findById(id);
    }

    // POST /clientes → crear nuevo
    @PostMapping
    public Cliente crearCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // PUT /clientes/{id} → actualizar existente
    @PutMapping("/{id}")
    public Cliente actualizarCliente(@PathVariable Long id, @RequestBody Cliente nuevoCliente) {
        Cliente clienteExistente = clienteRepository.findById(id).orElseThrow();
        clienteExistente.setNombre(nuevoCliente.getNombre());
        clienteExistente.setEmail(nuevoCliente.getEmail());
        clienteExistente.setTelefono(nuevoCliente.getTelefono());
        return clienteRepository.save(clienteExistente);
    }

    // DELETE /clientes/{id} → eliminar por id
    @DeleteMapping("/{id}")
    public void eliminarCliente(@PathVariable Long id) {
        clienteRepository.deleteById(id);
    }
}