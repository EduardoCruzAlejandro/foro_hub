package com.forohub.ForoHub.service;

import com.forohub.ForoHub.models.Curso;
import com.forohub.ForoHub.models.Topico;
import com.forohub.ForoHub.models.Usuario;
import com.forohub.ForoHub.repository.CursoRepository;
import com.forohub.ForoHub.repository.TopicoRepository;
import com.forohub.ForoHub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicoService {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public List<Topico> listarTopico(){
        return topicoRepository.findAll();
    }
    // Guardar tópico
    public Topico guardar(Topico topico) {
        return topicoRepository.save(topico);
    }
    // Verificar si existe un tópico duplicado
    public boolean existeTopicoDuplicado(String titulo, String mensaje) {
        return topicoRepository.existsByTituloAndMensaje(titulo, mensaje);
    }

    public Topico obtenerPorId(Long id) {
        return topicoRepository.findById(id).orElse(null); // Retorna null si no existe
    }

    public void eliminar(Long id) {
        topicoRepository.deleteById(id); // Elimina el tópico por su ID
    }

    public Topico actualizar(Long id, Topico topicoActualizado) {
        Topico topicoExistente = obtenerPorId(id);
        if (topicoExistente == null) {
            return null; // Si no existe el tópico, retornar null
        }

        // Actualizar los campos del topico
        topicoExistente.setTitulo(topicoActualizado.getTitulo());
        topicoExistente.setMensaje(topicoActualizado.getMensaje());
        topicoExistente.setEstado(topicoActualizado.getEstado());

        // Actualizar autor y curso si es necesario
        if (topicoActualizado.getAutor() != null) {
            topicoExistente.setAutor(topicoActualizado.getAutor());
        }
        if (topicoActualizado.getCurso() != null) {
            topicoExistente.setCurso(topicoActualizado.getCurso());
        }

        return topicoRepository.save(topicoExistente); // Guardar el tópico con los cambios
    }

    public boolean existeTopico(Long id) {
        return topicoRepository.existsById(id);
    }

    public Topico crear(Topico topico) {
        // Obtener el Usuario desde la base de datos utilizando el ID
        Usuario autor = usuarioRepository.findById(topico.getAutor().getId()).orElse(null);

        // Obtener el Curso desde la base de datos utilizando el ID
        Curso curso = cursoRepository.findById(topico.getCurso().getId()).orElse(null);

        // Verificar que ambos existan antes de asignarlos
        if (autor != null && curso != null) {
            // Asignar el Usuario y Curso al Topico
            topico.setAutor(autor);
            topico.setCurso(curso);

            // Guardar el Topico con las relaciones correctas
            return topicoRepository.save(topico);
        } else {
            // Si el Usuario o Curso no existe, manejar el error de alguna forma (puedes lanzar una excepción o devolver un valor nulo)
            return null;
        }
    }
}
