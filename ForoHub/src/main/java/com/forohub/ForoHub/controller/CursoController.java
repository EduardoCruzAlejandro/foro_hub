package com.forohub.ForoHub.controller;

import com.forohub.ForoHub.models.Curso;
import com.forohub.ForoHub.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {
    @Autowired
    private CursoService cursoService;

    // Método para crear un curso
    @PostMapping
    public ResponseEntity<Curso> crear(@Valid @RequestBody Curso curso) {
        Curso nuevoCurso = cursoService.guardar(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCurso);
    }

    // Método para obtener todos los cursos
    @GetMapping
    public List<Curso> listar() {
        return cursoService.listarCursos();
    }

    // Método para obtener un curso por ID
    @GetMapping("/{id}")
    public ResponseEntity<Curso> obtener(@PathVariable Long id) {
        Curso curso = cursoService.obtenerPorId(id);
        if (curso == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(curso);
    }
    // Método para actualizar un curso por ID
    @PutMapping("/{id}")
    public ResponseEntity<Curso> actualizar(@PathVariable Long id, @Valid @RequestBody Curso cursoActualizado) {
        Curso cursoExistente = cursoService.obtenerPorId(id);
        if (cursoExistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Si no se encuentra, devolver 404
        }

        // Actualizar los campos del curso
        cursoExistente.setNombre(cursoActualizado.getNombre());
        cursoExistente.setCategoria(cursoActualizado.getCategoria());

        // Guardar el curso actualizado
        Curso cursoGuardado = cursoService.guardar(cursoExistente);

        return ResponseEntity.ok(cursoGuardado); // Retornar con status 200 y el curso actualizado
    }

    // Método para eliminar un curso por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!cursoService.existeCurso(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Si no existe, devolver 404
        }
        cursoService.eliminar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Eliminar y devolver 204
    }

}
