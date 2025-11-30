package com.citasalud.demo.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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
        
        response.add(linkTo(methodOn(BloqueoOperativoController.class).obtenerPorId(response.getId())).withSelfRel());
        response.add(linkTo(methodOn(BloqueoOperativoController.class).listar()).withRel("bloqueos"));
        response.add(linkTo(methodOn(BloqueoOperativoController.class).eliminar(response.getId())).withRel("delete"));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BloqueoOperativoDTOResponse> obtenerPorId(@PathVariable Integer id) {
        BloqueoOperativoDTOResponse response = service.obtenerPorId(id);
        
        response.add(linkTo(methodOn(BloqueoOperativoController.class).obtenerPorId(id)).withSelfRel());
        response.add(linkTo(methodOn(BloqueoOperativoController.class).listar()).withRel("bloqueos"));
        response.add(linkTo(methodOn(BloqueoOperativoController.class).eliminar(id)).withRel("delete"));
        
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<BloqueoOperativoDTOResponse>> listar() {
        List<BloqueoOperativoDTOResponse> lista = service.listar();
        
        for (BloqueoOperativoDTOResponse bloqueo : lista) {
            bloqueo.add(linkTo(methodOn(BloqueoOperativoController.class).obtenerPorId(bloqueo.getId())).withSelfRel());
            bloqueo.add(linkTo(methodOn(BloqueoOperativoController.class).eliminar(bloqueo.getId())).withRel("delete"));
        }
        
        Link selfLink = linkTo(methodOn(BloqueoOperativoController.class).listar()).withSelfRel();
        CollectionModel<BloqueoOperativoDTOResponse> collectionModel = CollectionModel.of(lista, selfLink);
        
        return ResponseEntity.ok(collectionModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
