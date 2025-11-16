package com.sistemacola.turnos.controller;

import com.sistemacola.turnos.model.Usuario;
import com.sistemacola.turnos.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // Crear usuario
    @PostMapping
    public ResponseEntity<Usuario> crear(@RequestBody Usuario usuario) {
        Usuario creado = usuarioService.crear(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // Listar todos
    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.listar();
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(
            @PathVariable Long id,
            @RequestBody Usuario usuarioActualizado) {

        Usuario actualizado = usuarioService.actualizar(id, usuarioActualizado);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(actualizado);
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boolean eliminado = usuarioService.eliminar(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String rol,
            HttpSession session) {

        Optional<Usuario> usuarioOpt = usuarioService.buscarPorUsername(username);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            if (usuario.getRol() != null &&
                    usuario.getRol().equalsIgnoreCase(rol)) {

                session.setAttribute("usuarioLogueado", usuario);

                if ("ADMIN".equalsIgnoreCase(rol)) {
                    return "redirect:/admin.html";
                } else if ("RECEPCION".equalsIgnoreCase(rol)) {
                    return "redirect:/recepcion.html";
                } else {
                    return "redirect:/index.html";
                }
            }
        }

        return "redirect:/login.html?error=true";
    }

}
