package com.citasalud.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.citasalud.demo.models.dtos.BloqueoOperativoDTORequest;
import com.citasalud.demo.models.dtos.BloqueoOperativoDTOResponse;
import com.citasalud.demo.service.BloqueoOperativoService;

@RestController
@RequestMapping("/api/bloqueos-operativos")
public class BloqueoOperativoController {

    private final BloqueoOperativoService service;

    public BloqueoOperativoController(BloqueoOperativoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<BloqueoOperativoDTOResponse> crear(@RequestBody BloqueoOperativoDTORequest dto) {
        BloqueoOperativoDTOResponse response = service.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BloqueoOperativoDTOResponse> obtenerPorId(@PathVariable Integer id) {
        BloqueoOperativoDTOResponse response = service.obtenerPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<BloqueoOperativoDTOResponse>> listar() {
        List<BloqueoOperativoDTOResponse> lista = service.listar();
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
