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

    // Crear usuario
    public Usuario crear(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Listar todos
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    // Obtener usuario por ID
    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Actualizar usuario
    public Usuario actualizar(Long id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id)
                .map(u -> {
                    u.setUsername(usuarioActualizado.getUsername());
                    u.setPassword(usuarioActualizado.getPassword());
                    u.setRol(usuarioActualizado.getRol());
                    return usuarioRepository.save(u);
                })
                .orElse(null);
    }

    // Eliminar usuario
    public boolean eliminar(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Buscar por username (para el login)
    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}