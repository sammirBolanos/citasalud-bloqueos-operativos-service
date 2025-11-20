package com.citasalud.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.citasalud.demo.client.DisponibilidadesClient;
import com.citasalud.demo.models.dtos.BloqueoOperativoDTORequest;
import com.citasalud.demo.models.dtos.BloqueoOperativoDTOResponse;
import com.citasalud.demo.models.jpaEntitys.BloqueoOperativo;
import com.citasalud.demo.repository.BloqueoOperativoRepository;

import jakarta.transaction.Transactional;

@Service
public class BloqueoOperativoService {

    private final BloqueoOperativoRepository repository;
    private final DisponibilidadesClient disponibilidadesClient;

    public BloqueoOperativoService(BloqueoOperativoRepository repository,
                                   DisponibilidadesClient disponibilidadesClient) {
        this.repository = repository;
        this.disponibilidadesClient = disponibilidadesClient;
    }
    
    @Transactional
    public BloqueoOperativoDTOResponse crear(BloqueoOperativoDTORequest dto) {

        // Validación de fechas
        if (dto.fechaFin().isBefore(dto.fechaInicio())) {
            throw new IllegalArgumentException("La fecha fin no puede ser anterior a la fecha inicio.");
        }

        // Map DTO → Entity
        var entidad = toEntity(dto);

        // Guardar en BD
        entidad = repository.save(entidad);

        // Llamar al microservicio para inhabilitar disponibilidades
        disponibilidadesClient.inactivarDisponibilidadesPorBloqueo(
            dto.idProfesional(),
            dto.fechaInicio(),
            dto.fechaFin()
        );

        return toResponse(entidad);
    }

    public BloqueoOperativoDTOResponse obtenerPorId(Integer id) {
        var entidad = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bloqueo operativo no encontrado"));

        return toResponse(entidad);
    }

    public List<BloqueoOperativoDTOResponse> listar() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public void eliminar(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("El bloqueo operativo no existe");
        }

        repository.deleteById(id);
    }

    // Métodos Mapper
    private BloqueoOperativo toEntity(BloqueoOperativoDTORequest dto) {
        BloqueoOperativo entidad = new BloqueoOperativo();
        entidad.setIdProfesional(dto.idProfesional());
        entidad.setFechaInicio(dto.fechaInicio());
        entidad.setFechaFin(dto.fechaFin());
        entidad.setDetalle(dto.detalle());
        return entidad;
    }

    private BloqueoOperativoDTOResponse toResponse(BloqueoOperativo entidad) {
        return new BloqueoOperativoDTOResponse(
            entidad.getId(),
            entidad.getIdProfesional(),
            entidad.getFechaInicio(),
            entidad.getFechaFin(),
            entidad.getDetalle()
        );
    }

}
