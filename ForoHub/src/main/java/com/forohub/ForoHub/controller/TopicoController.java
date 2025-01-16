package com.forohub.ForoHub.controller;

import com.forohub.ForoHub.models.Topico;
import com.forohub.ForoHub.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/topicos")
public class TopicoController {
    @Autowired
    private TopicoService topicoService;

    @GetMapping
    public List<Topico> listar() {
        return topicoService.listarTopico();
    }

//    @PostMapping
//    public ResponseEntity<Topico> crear(@Valid @RequestBody Topico topico) {
//        // Verificación de tópicos duplicados
//        if (topicoService.existeTopicoDuplicado(topico.getTitulo(), topico.getMensaje())) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//        Topico nuevoTopico = topicoService.guardar(topico);
//        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTopico);
//    }

    @PostMapping
    public ResponseEntity<Topico> crearTopico(@RequestBody Topico topico) {
        // Llamar al servicio para crear el Topico
        Topico nuevoTopico = topicoService.crear(topico);

        if (nuevoTopico != null) {
            // Si la creación fue exitosa, devolver el Topico creado con código 201
            return new ResponseEntity<>(nuevoTopico, HttpStatus.CREATED);
        } else {
            // Si no se pudo crear el Topico (por ejemplo, si el autor o curso no existen), devolver un error 400
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Topico> obtener(@PathVariable Long id) {
        Topico topico = topicoService.obtenerPorId(id);
        if (topico == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(topico);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        // Verificar si el topico con el ID existe
        if (!topicoService.existeTopico(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Si no existe, devolver 404
        }

        // Eliminar el tópico
        topicoService.eliminar(id);

        // Retornar status 204 (sin contenido) para indicar que la eliminación fue exitosa
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Topico> actualizar(@PathVariable Long id, @Valid @RequestBody Topico topicoActualizado) {
        // Verificar si el topico con el ID existe
        Topico topicoExistente = topicoService.obtenerPorId(id);
        if (topicoExistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Si no se encuentra, devolver 404
        }

        // Verificar duplicados (si es necesario)
        if (topicoService.existeTopicoDuplicado(topicoActualizado.getTitulo(), topicoActualizado.getMensaje())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // Si el tópico es duplicado, devolver 400
        }

        // Actualizar los campos del tópico
        topicoExistente.setTitulo(topicoActualizado.getTitulo());
        topicoExistente.setMensaje(topicoActualizado.getMensaje());
        topicoExistente.setEstado(topicoActualizado.getEstado());
        topicoExistente.setAutor(topicoActualizado.getAutor());  // Asegúrate que el autor exista
        topicoExistente.setCurso(topicoActualizado.getCurso());  // Asegúrate que el curso exista

        // Guardar el tópico actualizado
        Topico topicoGuardado = topicoService.guardar(topicoExistente);

        // Retornar el tópico actualizado
        return ResponseEntity.ok(topicoGuardado); // Retorna con status 200 y el topico actualizado
    }

}
