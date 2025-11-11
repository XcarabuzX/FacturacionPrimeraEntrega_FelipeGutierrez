package com.coldflame.farmacia.service;

import com.coldflame.farmacia.dto.ClienteDTO;
import com.coldflame.farmacia.entity.Cliente;
import com.coldflame.farmacia.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Crear cliente desde DTO
    public ClienteDTO crearCliente(ClienteDTO clienteDTO) {
        Cliente cliente = dtoToEntity(clienteDTO);
        Cliente clienteGuardado = clienteRepository.save(cliente);
        return entityToDTO(clienteGuardado);
    }

    // Listar todos los clientes
    public List<ClienteDTO> obtenerTodosClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    // Obtener cliente por ID
    public ClienteDTO obtenerClientePorId(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        return clienteOptional.map(this::entityToDTO).orElse(null);
    }

    // Eliminar cliente por ID
    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    //Conversión Entity → DTO
    private ClienteDTO entityToDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setEmail(cliente.getEmail());
        dto.setTelefono(cliente.getTelefono());
        return dto;
    }

    //Conversión DTO → Entity
    private Cliente dtoToEntity(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setId(dto.getId());
        cliente.setNombre(dto.getNombre());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        return cliente;
    }
}