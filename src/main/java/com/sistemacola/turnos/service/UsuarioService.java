package com.sistemacola.turnos.service;

import com.sistemacola.turnos.model.Usuario;
import com.sistemacola.turnos.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    // Crear
    public Usuario crear(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Listar
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    // Obtener por ID
    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Actualizar
    public Usuario actualizar(Long id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id).map(usuario -> {

            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setRol(usuarioActualizado.getRol());
            usuario.setPasswordHash(usuarioActualizado.getPasswordHash());

            return usuarioRepository.save(usuario);
        }).orElse(null);
    }

    // Eliminar
    public boolean eliminar(Long id) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuarioRepository.delete(usuario);
            return true;
        }).orElse(false);
    }
}

