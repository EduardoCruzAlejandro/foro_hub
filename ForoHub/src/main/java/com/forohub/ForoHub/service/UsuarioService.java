package com.forohub.ForoHub.service;

import com.forohub.ForoHub.models.Usuario;
import com.forohub.ForoHub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Guardar un usuario
    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Obtener todos los usuarios (opcional)
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }
    // Obtener un usuario por ID
    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Verificar si existe un usuario
    public boolean existeUsuario(Long id) {
        return usuarioRepository.existsById(id);
    }

    // Eliminar un usuario
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }
}
