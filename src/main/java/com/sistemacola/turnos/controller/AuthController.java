package com.sistemacola.turnos.controller;

import com.sistemacola.turnos.model.Usuario;
import com.sistemacola.turnos.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("rol") String rol,
            HttpSession session
    ) {

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

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login.html";
    }
}
