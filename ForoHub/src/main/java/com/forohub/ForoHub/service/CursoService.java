package com.forohub.ForoHub.service;

import com.forohub.ForoHub.models.Curso;
import com.forohub.ForoHub.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {
    @Autowired
    private CursoRepository cursoRepository;

    // Guardar un curso
    public Curso guardar(Curso curso) {
        return cursoRepository.save(curso);
    }

    // Obtener todos los cursos
    public List<Curso> listarCursos() {
        return cursoRepository.findAll();
    }

    // Obtener un curso por ID
    public Curso obtenerPorId(Long id) {
        return cursoRepository.findById(id).orElse(null); // Retorna null si no existe
    }
    // Verificar si existe un curso
    public boolean existeCurso(Long id) {
        return cursoRepository.existsById(id);
    }

    // Eliminar un curso
    public void eliminar(Long id) {
        cursoRepository.deleteById(id);
    }
}
