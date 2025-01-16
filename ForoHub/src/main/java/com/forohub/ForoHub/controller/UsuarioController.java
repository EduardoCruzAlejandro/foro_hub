package com.forohub.ForoHub.controller;

import com.forohub.ForoHub.models.Usuario;
import com.forohub.ForoHub.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    // Método para crear un usuario
    @PostMapping
    public ResponseEntity<Usuario> crear(@Valid @RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.guardar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

    // Método para obtener todos los usuarios (opcional)
    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.listarUsuarios();
    }
    // Actualizar un usuario
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id, @Valid @RequestBody Usuario usuarioActualizado) {
        return usuarioService.obtenerPorId(id)
                .map(usuarioExistente -> {
                    usuarioExistente.setNombre(usuarioActualizado.getNombre());
                    usuarioExistente.setCorreoElectronico(usuarioActualizado.getCorreoElectronico());
                    usuarioExistente.setContrasena(usuarioActualizado.getContrasena());
                    Usuario usuarioGuardado = usuarioService.guardar(usuarioExistente);
                    return ResponseEntity.ok(usuarioGuardado);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Método para eliminar un usuario por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!usuarioService.existeUsuario(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Si no existe, devolver 404
        }
        usuarioService.eliminar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Eliminar y devolver 204
    }
}
